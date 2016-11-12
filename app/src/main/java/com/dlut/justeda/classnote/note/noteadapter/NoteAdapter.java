package com.dlut.justeda.classnote.note.noteadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlut.justeda.classnote.R;

import java.util.List;

/**
 * Created by 赵佳伟 on 2016/11/9.
 */
public class NoteAdapter extends BaseAdapter {

    private List<NoteItem> mlist;
    private LayoutInflater mLayoutInflater;

    public NoteAdapter(Context context, List<NoteItem> list) {
        mLayoutInflater = LayoutInflater.from(context);
        mlist = list;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.note_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.noteitem_image);
            holder.title = (TextView) convertView.findViewById(R.id.noteitem_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //判断item里应该放resourse还是bitmap
        NoteItem noteItem = mlist.get(position);
        if (noteItem.getImageID() == 0) {
            holder.img.setImageBitmap(noteItem.getBitmap());
        }else{
            holder.img.setImageResource(noteItem.getImageID());
        }
        holder.title.setText(noteItem.getName());
        return convertView;
    }

    class ViewHolder{
        public ImageView img;
        public TextView title;
    }


}
