package com.dlut.justeda.classnote.justpublic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dlut.justeda.classnote.R;

import io.values.camera.widget.CameraView;
import io.values.camera.widget.FocusView;

/**
 * 相机加载不出来——莫名bug
 * 利用接口调用onWindowFocusChanged实现相机
 * bug——偶尔后台再次进入时切换不会加载
 * Created by 赵佳伟 on 2016/11/9.
 */
public class CameraFragment extends Fragment implements IOnFocusListenable,View.OnClickListener{

    private CameraView cameraView;
    private ImageButton camera_flush;
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.camera_fragment_main, container, false);
        camera_flush = (ImageButton) view.findViewById(R.id.camera_flash);
        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cameraView = new CameraView(getContext());
        cameraView.setFocusView((FocusView) view.findViewById(R.id.sf_focus));
        cameraView.setCameraView((SurfaceView) view.findViewById(R.id.sf_camera), CameraView.MODE4T3);
        camera_flush.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera_flash:
                cameraView.changeFlash();
                Log.e("camaraFragment", "flush");
                break;
        }
    }

    public CameraView getCameraView(){
        return cameraView;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            cameraView.onResume();//must
        }else{
            cameraView.onPause();
        }
    }
}
