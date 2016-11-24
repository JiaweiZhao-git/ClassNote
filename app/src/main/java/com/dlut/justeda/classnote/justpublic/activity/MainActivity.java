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
import android.widget.LinearLayout;
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
import com.dlut.justeda.classnote.note.util.OpenPhotoAlbum;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 最重要的主界面
 *bug——切换时有时候会重叠，或者不响应——已解决
 * 小bug——相机的zoom事件会和主界面的侧滑手势冲突——chaomaer解决
 * changetab(n)返回的界面
 * 需要相機按鍵動畫效果實現
 * 最後將各個activity的finish()事件處理好
 *
 *
 *<>important thing</>
 * tuChaomaer
 * 换完头像后可以在分享界面同步——本地同样的图片，bitmap加载
 * 分享界面获取url后加载图片的代码
 * 点赞的奇怪逻辑
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
    private RoundedImageView user_image;
    private LinearLayout user_pop;
    private LinearLayout user_favourite;
    private LinearLayout user_zip;
    private LinearLayout user_related;
    private LinearLayout user_settings;
    private LinearLayout user_feedback;

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

        user_image = (RoundedImageView) mSlidingMenu.findViewById(R.id.main_left_user_default_image);
        user_image.setOnClickListener(this);

        user_pop = (LinearLayout) mSlidingMenu.findViewById(R.id.main_left_pop_file);
        user_pop.setOnClickListener(this);

        user_favourite = (LinearLayout) mSlidingMenu.findViewById(R.id.main_left_user_favorite);
        user_favourite.setOnClickListener(this);

        user_zip = (LinearLayout) mSlidingMenu.findViewById(R.id.main_left_user_zip_file);
        user_zip.setOnClickListener(this);

        user_related = (LinearLayout) mSlidingMenu.findViewById(R.id.main_left_user_related);
        user_related.setOnClickListener(this);

        user_settings = (LinearLayout) mSlidingMenu.findViewById(R.id.main_left_settings);
        user_settings.setOnClickListener(this);

        user_feedback = (LinearLayout) mSlidingMenu.findViewById(R.id.main_left_feedback);
        user_feedback.setOnClickListener(this);

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
            case R.id.main_left_user_default_image:
                //进入相册选择照片然后可以压缩一下，存在一个叫default的文件夹下
                //requestcode==250
                //名字一样，每次操作算是覆盖
                //开个线程让他自己把压缩后的图片加载出来
                Intent cameraIntent=new Intent(
                        "android.intent.action.GET_CONTENT");
                cameraIntent.setType("image/*");
                startActivityForResult(cameraIntent, 250);
                break;
            case R.id.main_left_pop_file:
                //开个接口用来选择课程，然后执行异步压缩操作
                break;
            case R.id.main_left_user_favorite:
                startActivity(new Intent(MainActivity.this,FailLoadingActivity.class));
                break;
            case R.id.main_left_user_zip_file:

                break;
            case R.id.main_left_user_related:
                startActivity(new Intent(MainActivity.this,FailLoadingActivity.class));
                break;
            case R.id.main_left_settings:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                break;
            case R.id.main_left_feedback:
                Intent intentFeedBack = new Intent(MainActivity.this, FeedBackActivity.class);
                startActivity(intentFeedBack);
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
                mSlidingMenu.setInteruptAble(false);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 250:
                OpenPhotoAlbum openPhotoAlbum = new OpenPhotoAlbum(MainActivity.this, data);
                openPhotoAlbum.handleImageOnKitKat();
                break;
        }
    }
}
