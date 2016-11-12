package com.dlut.justeda.classnote.justpublic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlut.justeda.classnote.R;

/**
 * 用于显示share主界面——之后可以跳转到其它activity
 * Created by 赵佳伟 on 2016/11/9.
 */
public class ShareFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.share_fragment_main, container, false);
        return view;
    }
}
