package com.zhaolei.model;

import java.util.List;

/**
 * Created by zhaolei on 2017/4/14.
 */
public class Text {
    private int id;
    private int scenarioId;
    private String labels;
    private String content;

    private String[] sentences;
    //未完成
    public  void seperateToSentences(){
        StringBuilder sb=new StringBuilder(content);
        int k=1;
        for(int i=0;i<content.length();i++)
        {
            switch (content.charAt(i)){
                case '。': case '？': case '！':case '；':case '.': case '?': case '!':case ';':
                    sb.insert(i+k++,' ');
                    break;
                default:
                    break;
            }

        }
        String tempContent = sb.toString();
        sentences = tempContent.trim().split("\\s+");

    }

    public String[] getSentences() {
        return sentences;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(int scenarioId) {
        this.scenarioId = scenarioId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "Text{" +
                "id=" + id +
                ", scenarioId=" + scenarioId +
                ", labels='" + labels + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
