package com.dlut.justeda.classnote.share.adapter;

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
 * Created by chaomaer on 2016/11/7.
 */

public class ShareImageAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Sharecontent> list;

    public ShareImageAdapter(Context context, List<Sharecontent> list) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.share_layout_item,null);
            viewHolder=new ViewHolder();
            viewHolder.textView= (TextView) convertView.findViewById(R.id.share_share_content);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.share_share_image);
            convertView.setTag(viewHolder);
        }else {
            convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position).getContent()+"*****");
        viewHolder.imageView.setImageBitmap(list.get(position).getImageView());
        return convertView;
    }
    public static class ViewHolder {
        public TextView textView;
        public ImageView imageView;
    }
}
