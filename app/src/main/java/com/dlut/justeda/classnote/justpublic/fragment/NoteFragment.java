package com.dlut.justeda.classnote.justpublic.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.justpublic.contralwidget.SlidingMenu;
import com.dlut.justeda.classnote.note.activity.NoteListActivity;
import com.dlut.justeda.classnote.note.db.ClassDatabaseHelper;
import com.dlut.justeda.classnote.note.noteadapter.NoteAdapter;
import com.dlut.justeda.classnote.note.noteadapter.NoteItem;
import com.dlut.justeda.classnote.note.util.OpenPhotoAlbum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 本地笔记的界面
 * Created by 赵佳伟 on 2016/11/9.
 */
public class NoteFragment extends Fragment {

    private SlidingMenu slidingMenu;

    private ListView listView;
    public static NoteAdapter noteAdapter;
    private List<NoteItem> noteList = new ArrayList<>();
    private static final int CHOOSE_PHOTO=3;

    private ImageButton title_button;
    private TextView title_text;

    private ClassDatabaseHelper dbHelper;

    private int IMAGE01 = R.drawable.note_class01;
    private int IMAGE02 = R.drawable.note_class02;
    private int IMAGE03 = R.drawable.note_class03;
    private int IMAGE04 = R.drawable.note_class04;
    private int IMAGE05 = R.drawable.note_class05;

    private int IMAGE[] = {IMAGE01, IMAGE02, IMAGE03, IMAGE04, IMAGE05};

    private boolean isLeft = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.note_fragment_main, container,false);

        slidingMenu = (SlidingMenu) getActivity().findViewById(R.id.slidingmenu);

        listView = (ListView) view.findViewById(R.id.note_main_courselist);
        title_button = (ImageButton) view.findViewById(R.id.title_left_image);
        title_text = (TextView) view.findViewById(R.id.title_middle_text);
        initViews();

        initEvents();

        return view;
    }


    private void initViews() {

        title_text.setText("课堂笔记");
        noteList.add(new NoteItem("相册管理", R.drawable.note_album));
        noteList.add(new NoteItem("其他",R.drawable.note_item));
        //需要添加其它课程信息
        dbHelper = new ClassDatabaseHelper(getContext(), "Courses.db", null, 2);
        addFromDB();

    }

    private void addFromDB() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Courses", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Random ran = new Random();
                int index = ran.nextInt(5);
                noteList.add(new NoteItem(name,IMAGE[index]));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void initEvents() {

        title_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingMenu.toLeft(isLeft);
                isLeft = !isLeft;
            }
        });

        noteAdapter = new NoteAdapter(getContext(), noteList);
        listView.setAdapter(noteAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /**
                 * 1、第一个是相册管理
                 * 2、第二个是qq文件管理——暂时去掉
                 * 3、第三个是其他文件夹
                 */
                if (position == 0) {
                    Intent intent=new Intent(
                            "android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, CHOOSE_PHOTO);
                } else{
                    Intent intent = new Intent(getContext(), NoteListActivity.class);
                    intent.putExtra("name",noteList.get(position).getName());
                    startActivity(intent);
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
