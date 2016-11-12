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
public class NoteListAdapter extends BaseAdapter{

    private List<NoteItem> mlist;
    private LayoutInflater mLayoutInflater;

    public NoteListAdapter(Context context, List<NoteItem> list) {
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
            convertView = mLayoutInflater.inflate(R.layout.notelist_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.notelistitem_image);
            holder.title = (TextView) convertView.findViewById(R.id.notelistitem_text_big);
            holder.smallTitle = (TextView) convertView.findViewById(R.id.notelistitem_text_small);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //判断item里应该放resourse还是bitmap
        NoteItem noteListItem = mlist.get(position);
        if (noteListItem.getImageID() == 0) {
            holder.img.setImageBitmap(noteListItem.getBitmap());
        }else{
            holder.img.setImageResource(noteListItem.getImageID());
        }
        holder.title.setText(noteListItem.getName());
        holder.smallTitle.setText(noteListItem.getLittleName());
        return convertView;
    }

    class ViewHolder{
        public ImageView img;
        public TextView title;
        public TextView smallTitle;
    }

}
