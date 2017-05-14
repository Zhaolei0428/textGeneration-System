package com.zhaolei.model;

import com.hankcs.hanlp.suggest.Suggester;
import com.hankcs.hanlp.suggest.scorer.BaseScorer;
import com.hankcs.hanlp.suggest.scorer.editdistance.EditDistanceScorer;
import com.hankcs.hanlp.suggest.scorer.lexeme.IdVectorScorer;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaolei on 2017/4/28.
 */
public class TextGeneration {
    private List<Text> texts;
    private String framework = null;

    public TextGeneration(List<Text> texts,String framework){
        this.texts=texts;
        if(framework != null)
             this.framework=framework;
    }

    public String generate(String labels,String replaces){
        List<BaseScorer> baseScorers = new ArrayList<BaseScorer>(2);
        EditDistanceScorer editDistanceScorer = new EditDistanceScorer();
        IdVectorScorer idVectorScorer = new IdVectorScorer();
        baseScorers.add(editDistanceScorer);
        baseScorers.add(idVectorScorer);

        Suggester suggester = new Suggester(baseScorers);

        for(Text text:texts){
            text.seperateToSentences();
            String[] sentences = text.getSentences();
            for(String sentence:sentences){
                suggester.addSentence(sentence);
            }
        }

        String[] inputLabels = labels.trim().split("，|,");
        List<String> sentences = new ArrayList<>();
        for(String label:inputLabels){
            //可根据需求文本的多少改变size大小以获得适量的语句
            List<String> suggests= suggester.suggest(label, 1);

            //添加suggest语句至sentences并去重
            for(String suggest:suggests){
                boolean flag=false;
                for(String sentence:sentences)
                {
                    if(suggest.equals(sentence))
                    {
                        flag=true;
                        break;
                    }
                }
                if(!flag)
                    sentences.add(suggest);
            }
        }

        //List转化为String
        String generationText="";
        StringBuffer sb = new StringBuffer(generationText);
        for(String sentence:sentences){
            sb.append(sentence);
        }
        if(framework.equals("不使用"))
            return sb.toString();
        else
            return replace(sb.toString(),replaces);
    }

    public String replace(String content,String replaces){
        //替换内容
        String hasContent;
        if(framework.indexOf("$$")!= -1)
        {
            hasContent = framework.replace("$$",content);
        }
        else
            hasContent = framework+content;
        if(replaces.isEmpty())
            return hasContent;
        else{
            String[] replaces1 =replaces.split(",|，");
            for(String replace1:replaces1)
            {
                if(hasContent.indexOf("%s")!=-1)
                {
                    String temp = hasContent.replaceFirst("%s",replace1);
                    hasContent = temp;
                }
                else
                    break;
            }
            return hasContent;
        }
    }
}
