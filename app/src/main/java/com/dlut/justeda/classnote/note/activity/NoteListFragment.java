package com.dlut.justeda.classnote.note.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.note.noteadapter.NoteItem;
import com.dlut.justeda.classnote.note.noteadapter.NoteListAdapter;
import com.dlut.justeda.classnote.note.ui.NoteArcMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 赵佳伟 on 2016/11/10.
 */
public class NoteListFragment extends Fragment {

    private NoteArcMenu noteArcMenu;
    private ListView listView;
    private NoteListAdapter noteListAdapter;
    private List<NoteItem> noteItemsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_fragment_main, container, false);

        noteArcMenu = (NoteArcMenu) view.findViewById(R.id.note_arcmenu);
        listView = (ListView) view.findViewById(R.id.note_fragment_pictures_list);
        initViews();

        initEvents();
        return view;
    }

    private void initViews() {
        noteItemsList.add(new NoteItem("...", R.drawable.note_item));
    }

    private void initEvents() {
        /**
         * 监听listview的滑动，如果滑动将菜单收起
         */
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (noteArcMenu.isOpen()) {
                    noteArcMenu.toggleMenu(600);
                }

            }
        });

        /**
         * 监听listview的长按事件，长按菜单弹出，更符合用户选中操作的顺序
         * bug:view值传错、导致选中item的内容会转（应该修改view的值）
         * 状态：待修复
         */
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                noteArcMenu.onClick(view);
                return false;
            }
        });

        /**
         * 1、...代表返回键
         * 2、其它的点击进入笔记界面
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });

        /**
         * 根据选中的不同位置pos
         * 来进行相应的操作
         */
        noteArcMenu.setOnMenuItemClickListener(new NoteArcMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {

            }
        });
    }
}
