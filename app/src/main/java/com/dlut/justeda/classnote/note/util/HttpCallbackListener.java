package com.dlut.justeda.classnote.note.util;

/**
 * Created by 赵佳伟 on 2016/11/20.
 */
public interface HttpCallbackListener {

    void onFinish(String reponse);

    void onError(String e);
}
