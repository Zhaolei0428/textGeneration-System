package com.zhaolei.dao;

import com.zhaolei.model.Framework;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhaolei on 2017/4/14.
 */
@Mapper
public interface FrameworkDao {
    String table = "framework";
    String selectField = "id, scenarioId, content";
    String insertField = "scenarioId, content";

    @Select({"SELECT ", selectField, " FROM ", table, "WHERE scenarioId = #{scenarioId}"})
    List<Framework> getFrameworkByScenarioId(@Param("scenarioId") int scenarioId);

    @Select({"SELECT ", selectField, " FROM ", table, "WHERE scenarioId = #{scenarioId} and content = #{content}"})
    Framework getFrameworkByName(@Param("scenarioId") int scenarioId, @Param("content") String content);

    @Insert({"INSERT INTO ", table, "(", insertField, ")", "VALUES(#{scenarioId},#{content})" })
    int insert(Framework framework);
}
