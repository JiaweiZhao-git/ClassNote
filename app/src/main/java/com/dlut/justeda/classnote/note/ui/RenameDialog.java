package com.dlut.justeda.classnote.note.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dlut.justeda.classnote.R;

/**
 * 自定义控件——renameDialog
 * 用于重命名时弹出方便修改item的名字
 * Created by 赵佳伟 on 2016/11/10.
 */
public class RenameDialog extends Dialog {

    private EditText reName;
    private String titleName;
    private Button submit;
    private OnOKListener mOkListener;
    public interface OnOKListener {
        public void getDialogValue(String str);
    }

    public RenameDialog(Context context , String titleName, OnOKListener mOkListener){
        super(context);
        this.titleName = titleName;
        this.mOkListener = mOkListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_rename_dialog);

        reName = (EditText) findViewById(R.id.text_name);
        submit = (Button) findViewById(R.id.save_name);
        setTitle(titleName);
        submit.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String text = reName.getText().toString();
            mOkListener.getDialogValue(text);
            dismiss();
        }
    };
}
