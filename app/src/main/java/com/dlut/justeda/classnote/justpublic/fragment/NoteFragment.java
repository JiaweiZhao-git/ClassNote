package com.dlut.justeda.classnote.justpublic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.note.noteadapter.NoteAdapter;
import com.dlut.justeda.classnote.note.noteadapter.NoteItem;
import com.dlut.justeda.classnote.note.util.OpenPhotoAlbum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 赵佳伟 on 2016/11/9.
 */
public class NoteFragment extends Fragment {

    private ListView listView;
    private NoteAdapter noteAdapter;
    private List<NoteItem> noteList = new ArrayList<>();
    private static final int CHOOSE_PHOTO=3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_fragment_main, container,false);
        listView = (ListView) view.findViewById(R.id.note_main_courselist);
        initViews();

        initEvents();
        return view;
    }

    private void initViews() {
        noteList.add(new NoteItem("相册管理", R.drawable.note_album));
        noteList.add(new NoteItem("QQ文件管理",R.drawable.note_qq));
        noteList.add(new NoteItem("其它",R.drawable.note_item));
    }

    private void initEvents() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /**
                 * 1、第一个是相册管理
                 * 2、第二个是qq文件管理——这个真的还要吗？qq文件和本身的文件格式不一样
                 * 3、第三个是其他文件夹
                 */
                if (position == 0) {
                    Intent intent=new Intent(
                            "android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, CHOOSE_PHOTO);
                } else if (position == 1) {
                    //暂时不会处理qq，应该是找响应的文件夹
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                OpenPhotoAlbum openPhotoAlbum = new OpenPhotoAlbum(getContext(), data);
                openPhotoAlbum.handleImageOnKitKat();
                break;
            default:
                break;
        }
    }
}
