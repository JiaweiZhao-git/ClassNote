package com.dlut.justeda.classnote.share.message;

import java.util.List;

/**
 * Created by chaomaer on 2016/11/13.
 */

public class JsonCommentlist {
    public int code;
    public List<JsonComment>  list;
    public class JsonComment{
        public int id;
        public String text;
        public String createdAt;
        public String updatedAt;
        public int PostId;
        public int UserId;
        public JsonTopiclist.UserInfo User;
    }
}
