package com.zhaolei.model;

import java.util.List;

/**
 * Created by zhaolei on 2017/4/14.
 */
@Deprecated
public class Graph {
    private int number;
    private String content;

    private List<Sentence> sentences;
    //未完成
    public void seperateToSentences(){

    }

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
