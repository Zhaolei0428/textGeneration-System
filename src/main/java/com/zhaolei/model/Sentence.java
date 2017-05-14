package com.zhaolei.model;

/**
 * Created by zhaolei on 2017/4/14.
 */
@Deprecated
public class Sentence {
    private int number;
    private String content;

    //判断标签是否在句子中，未完成
    public boolean isCompeted(String label){return true;}

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
