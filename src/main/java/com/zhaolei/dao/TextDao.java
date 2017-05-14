package com.zhaolei.dao;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.*;
import com.zhaolei.model.Text;

import java.util.List;

/**
 * Created by zhaolei on 2017/4/14.
 */
@Mapper
public interface TextDao {
    String table = "text";
    String selectField = "id, scenarioId, labels,content";
    String insertField = "scenarioId, labels,content";

    @Select({"SELECT ", selectField, " FROM ", table, "WHERE scenarioId = #{scenarioId}"})
    List<Text> getTextByScenarioId(@Param("scenarioId")  int scenarioId);

    @Insert({"INSERT INTO ", table, "(", insertField, ")", "VALUES(#{scenarioId},#{labels},#{content})" })
    int insert(Text text);

}
