package com.dlut.justeda.classnote.share.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.share.activity.ShareCommentActivity;
import com.dlut.justeda.classnote.share.data.Data;
import com.dlut.justeda.classnote.share.message.TopicMeg;
import com.dlut.justeda.classnote.share.util.Constant;
import com.dlut.justeda.classnote.share.util.Network;

import java.util.List;

/**
 * Created by chaomaer on 2016/11/7.
 */

public class ShareImageAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private LayoutInflater layoutInflater;
    private List<TopicMeg> list;
    public ShareImageAdapter(Context context, List<TopicMeg> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.share_layout_item,null);
            viewHolder=new ViewHolder();
            viewHolder.sharecontent= (TextView) convertView.findViewById(R.id.share_share_content);
            viewHolder.shareimg= (ImageView) convertView.findViewById(R.id.share_share_image);
            viewHolder.myavatar= (ImageView) convertView.findViewById(R.id.share_my_image);
            viewHolder.myname= (TextView) convertView.findViewById(R.id.share_my_name);
            viewHolder.praise_imag= (ImageView) convertView.findViewById(R.id.share_imag_praise);
            viewHolder.comment_imag= (ImageView) convertView.findViewById(R.id.share_imag_comment);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.sharecontent.setText(list.get(position).topicContent);
        viewHolder.myname.setText(list.get(position).username);
        viewHolder.comment_imag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ShareCommentActivity.class);
                intent.putExtra("postId",list.get(position).id);
                Log.e("=====postid=====","=========="+list.get(position).id+"=========");
                Data.getcommentMegList().clear();
                Network.getcommentlist(list.get(position).id);
                context.startActivity(intent);
            }
        });
        viewHolder.praise_imag.setOnClickListener(this);
        //对头像和照片的处理
       // Constant.imageLoader.displayImage(list.get(position).avatarurl,viewHolder.myavatar,Constant.options);
        Constant.imageLoader.displayImage(list.get(position).imageUrl,viewHolder.shareimg,Constant.options);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_imag_praise:
                break;
        }
    }
    public static class ViewHolder {
      public ImageView myavatar;
      public TextView  myname;
      public TextView  sharecontent;
      public ImageView shareimg;
      public ImageView praise_imag;
      public ImageView comment_imag;
    }
}
