package com.dlut.justeda.classnote.justpublic.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.justpublic.contralwidget.SlidingMenu;
import com.dlut.justeda.classnote.justpublic.fragment.CameraFragment;
import com.dlut.justeda.classnote.justpublic.fragment.NoteFragment;
import com.dlut.justeda.classnote.justpublic.fragment.ShareFragment;

/**
 * Created by 赵佳伟 on 2016/11/9.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private ImageButton noteImageButton;
    private ImageButton cameraImageButton;
    private ImageButton shareImageButton;

    private Fragment noteFragment;
    private Fragment cameraFragment;
    private Fragment shareFragment;

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
        cameraImageButton = (ImageButton) findViewById(R.id.camera_buttom);
        shareImageButton = (ImageButton) findViewById(R.id.share_buttom);

        mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingmenu);
    }

    private void initEvents() {
        noteImageButton.setOnClickListener(this);
        cameraImageButton.setOnClickListener(this);
        shareImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //重置所有buttom按钮的颜色
        resetImage();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);

        switch (view.getId()) {
            case R.id.note_buttom:
                if (noteFragment == null) {
                    noteFragment = new NoteFragment();
                    transaction.add(R.id.frameLayout, noteFragment);
                }else{
                    transaction.show(noteFragment);
                }
                //此处应设置按压后的按钮图片

                break;
            case R.id.camera_buttom:
                if (cameraFragment == null) {
                    cameraFragment = new CameraFragment();
                    transaction.add(R.id.frameLayout, cameraFragment);
                }else{
                    transaction.show(cameraFragment);
                }
                //此处应设置按压后的按钮图片

                break;
            case R.id.share_buttom:
                if (shareFragment == null) {
                    shareFragment = new ShareFragment();
                    transaction.add(R.id.frameLayout, shareFragment);
                }else{
                    transaction.show(shareFragment);
                }
                //此处应设置按压后的按钮图片

                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (noteFragment != null) {
            transaction.hide(noteFragment);
        }
        if (cameraFragment != null) {
            transaction.hide(cameraFragment);
        }
        if (shareFragment != null) {
            transaction.hide(shareFragment);
        }
    }

    private void resetImage() {
    }
}
