package com.dlut.justeda.classnote.share.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.share.message.CommentMeg;

import java.util.List;

/**
 * Created by chaomaer on 2016/11/8.
 */

public class ShareCommentAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<CommentMeg> list;
    public ShareCommentAdapter(Context context, List<CommentMeg> list) {
        this.context = context;
        this.list = list;
        layoutInflater=LayoutInflater.from(context);
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
            convertView=layoutInflater.inflate(R.layout.share_conment_item,null);
            viewHolder=new ViewHolder();
            viewHolder.commentcontent= (TextView) convertView.findViewById(R.id.share_comment_content);
            viewHolder.commentername= (TextView) convertView.findViewById(R.id.share_other_name);
            viewHolder.commentavatar= (ImageView) convertView.findViewById(R.id.share_other_image);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.commentcontent.setText(list.get(position).commentcontent);
        viewHolder.commentername.setText(list.get(position).commentername);
        Log.e("commenteravatarurl",list.get(position).commenteravatarurl);
        return convertView;
    }
    public  static class ViewHolder{
        public TextView commentcontent;
        public TextView commentername;
        public ImageView commentavatar;
    }
}
