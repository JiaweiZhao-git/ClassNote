package com.dlut.justeda.classnote.justpublic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.justpublic.contralwidget.SlidingMenu;
import com.dlut.justeda.classnote.justpublic.fragment.CameraFragment;
import com.dlut.justeda.classnote.justpublic.fragment.IOnFocusListenable;
import com.dlut.justeda.classnote.justpublic.fragment.NoteFragment;
import com.dlut.justeda.classnote.justpublic.fragment.ShareFragment;
import com.dlut.justeda.classnote.justpublic.util.FileUtil;
import com.dlut.justeda.classnote.note.util.BitmapUtil;
import com.dlut.justeda.classnote.note.util.ClassTime;

import java.util.ArrayList;
import java.util.List;

/**
 * 最重要的主界面
 *bug——切换时有时候会重叠，或者不响应——已解决
 * 小bug——相机的zoom事件会和主界面的侧滑手势冲突——chaomaer解决
 * changetab(n)返回的界面
 * 需要相機按鍵動畫效果實現
 * 最後將各個activity的finish()事件處理好
 * Created by 赵佳伟 on 2016/11/9.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private ImageButton noteImageButton;
    private ImageButton cameraImageButton;
    private ImageButton shareImageButton;

    private RelativeLayout noteRelativeLayout;
    private RelativeLayout shareRelativeLayout;

    private ArrayList<Fragment> fragmentArrayList;

    private NoteFragment noteFragment = new NoteFragment();
    private CameraFragment cameraFragment = new CameraFragment();
    private ShareFragment shareFragment = new ShareFragment();

    private int currentIndex = 1;
    private Fragment currentFragment;

    private boolean onCameraFragment=false;

    private SlidingMenu mSlidingMenu;
    private TextView user_name;

    private FileUtil fileUtil;
    private ClassTime classTime;

    private String className = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_main);

        initViews();

        initEvents();

    }

    private void initViews(){
        noteImageButton = (ImageButton) findViewById(R.id.note_buttom);
        noteRelativeLayout = (RelativeLayout) findViewById(R.id.note_relativeLayout);
        noteRelativeLayout.setOnClickListener(this);
        noteRelativeLayout.setTag(0);

        cameraImageButton = (ImageButton) findViewById(R.id.camera_buttom);
        cameraImageButton.setOnClickListener(this);
        cameraImageButton.setTag(1);

        shareImageButton = (ImageButton) findViewById(R.id.share_buttom);
        shareRelativeLayout = (RelativeLayout) findViewById(R.id.share_relativeLayout);
        shareRelativeLayout.setOnClickListener(this);
        shareRelativeLayout.setTag(2);

        mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingmenu);
        user_name = (TextView) mSlidingMenu.findViewById(R.id.main_left_user_name);
        user_name.setOnClickListener(this);

        classTime = new ClassTime();
        fileUtil = new FileUtil();
        fileUtil.createInitFiles();
    }

    private void initEvents() {
        fragmentArrayList = new ArrayList<Fragment>(3);
        fragmentArrayList.add(noteFragment);
        fragmentArrayList.add(cameraFragment);
        fragmentArrayList.add(shareFragment);

        //cameraView = cameraFragment.getCameraView();
        //cameraImageButton.setSelected(true);

        changeTab(1);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_left_user_name:
                Intent intent = new Intent(MainActivity.this, LeftLogin.class);
                startActivity(intent);
                break;
            default:
                resetImage();
                changeTab((Integer)view.getTag());
                break;
        }

    }

    /**
     * 切换fragment
     * @param index
     */
    private void changeTab(int index) {
        currentIndex = index;
        if (index == 0) {
            noteRelativeLayout.setSelected(true);
            noteImageButton.setBackgroundResource(R.drawable.note_pressed);
            onCameraFragment = false;
            if (className != null) {
                BitmapUtil bitmapUtil = new BitmapUtil();
                bitmapUtil.renamePictures(className,Environment.getExternalStorageDirectory() + "/ClassNote/"+className+"/");
            }
            mSlidingMenu.setInteruptAble(true);
        }
        if (index == 1) {
            mSlidingMenu.setInteruptAble(false);
            if (!onCameraFragment) {
                cameraImageButton.setSelected(true);
                //设置一个动态回缩放弹性效果

                onCameraFragment = true;
            }else{
                String date = classTime.getDate();
                String name = classTime.getClassName(this,date);
                cameraFragment.getCameraView().setDirPath(Environment.getExternalStorageDirectory() + "/ClassNote/"+name+"/");
                cameraFragment.getCameraView().setPicQuality(100);
                cameraFragment.getCameraView().takePicture(onCameraFragment);
                /*
                不知道为什么这里有时候比拍照要晚一点
                需要在notelist界面上加刷新，重新执行改名、压缩
                暂时将这段代码放到其他按键上，其实应该设置延迟一段时间再执行
                bug
                 */
                className = name;
//                BitmapUtil bitmapUtil = new BitmapUtil();
//                bitmapUtil.renamePictures(Environment.getExternalStorageDirectory() + "/ClassNote/"+className+"/");
            }


        }
        if (index == 2) {
            mSlidingMenu.setInteruptAble(false);
            shareRelativeLayout.setSelected(true);
            shareImageButton.setBackgroundResource(R.drawable.share_pressed);
            onCameraFragment = false;
        }

        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        //
        if (null != currentFragment) {
            ft.hide(currentFragment);
        }

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(
                fragmentArrayList.get(currentIndex).getClass().getName());
        if (null == fragment) {
            fragment = fragmentArrayList.get(index);
        }
        currentFragment = fragment;

        if (!fragment.isAdded()) {
            ft.add(R.id.frameLayout, fragment, fragment.getClass().getName());
        }else {
            ft.show(fragment);
        }
        ft.commit();

    }

    /**
     * 切换时先把所有图标置为灰色
     */
    private void resetImage() {
        noteImageButton.setBackgroundResource(R.drawable.note_normal);
        shareImageButton.setBackgroundResource(R.drawable.share_normal);
    }

    /**
     * 用于判断当前界面是哪个fragment
     * @return
     */
    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    /**
     * 相机fragment非常重要的调用方法
     * 用于时刻显示相机的预览界面
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (currentFragment instanceof IOnFocusListenable) {
            ((IOnFocusListenable) currentFragment).onWindowFocusChanged(hasFocus);
        }
    }



}
