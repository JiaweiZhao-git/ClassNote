package com.dlut.justeda.classnote.justpublic.activity;

import android.os.Bundle;
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
import com.dlut.justeda.classnote.justpublic.fragment.NoteFragment;
import com.dlut.justeda.classnote.justpublic.fragment.ShareFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 最重要的主界面
 *bug——切换时有时候会重叠，或者不响应——已解决
 * Created by 赵佳伟 on 2016/11/9.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private ImageButton noteImageButton;
    private ImageButton cameraImageButton;
    private ImageButton shareImageButton;

    private RelativeLayout noteRelativeLayout;
    private RelativeLayout shareRelativeLayout;

    private ArrayList<Fragment> fragmentArrayList;

    private int currentIndex = 1;
    private Fragment currentFragment;

    private SlidingMenu mSlidingMenu;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_main);

        initViews();

        initEvents();
    }

    private void initViews(){
        noteImageButton = (ImageButton) findViewById(R.id.note_buttom);
       // noteImageButton.setFocusable(false);
        noteRelativeLayout = (RelativeLayout) findViewById(R.id.note_relativeLayout);
        noteRelativeLayout.setOnClickListener(this);
        noteRelativeLayout.setTag(0);

        cameraImageButton = (ImageButton) findViewById(R.id.camera_buttom);
        cameraImageButton.setOnClickListener(this);
        cameraImageButton.setTag(1);

        shareImageButton = (ImageButton) findViewById(R.id.share_buttom);
     //   shareImageButton.setFocusable(false);
        shareRelativeLayout = (RelativeLayout) findViewById(R.id.share_relativeLayout);
        shareRelativeLayout.setOnClickListener(this);
        shareRelativeLayout.setTag(2);

        mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingmenu);
    }

    private void initEvents() {
        fragmentArrayList = new ArrayList<Fragment>(3);
        fragmentArrayList.add(new NoteFragment());
        fragmentArrayList.add(new CameraFragment());
        fragmentArrayList.add(new ShareFragment());

        cameraImageButton.setSelected(true);

        changeTab(1);

    }



    @Override
    public void onClick(View view) {
        resetImage();

        changeTab((Integer)view.getTag());
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
        }
        if (index == 1) {
            cameraImageButton.setSelected(true);
            //设置一个动态回缩放弹性效果
        }
        if (index == 2) {
            shareRelativeLayout.setSelected(true);
            shareImageButton.setBackgroundResource(R.drawable.share_pressed);
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
}
