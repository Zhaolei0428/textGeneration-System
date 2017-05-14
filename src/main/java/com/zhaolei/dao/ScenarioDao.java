package com.zhaolei.dao;

import com.zhaolei.model.Scenario;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * Created by zhaolei on 2017/4/14.
 */
@Mapper
public interface ScenarioDao {
    String table = "scenario";
    String selectField = "id, userId, name";
    String insertField = "userId, name";

    @Select({"SELECT ", selectField, " FROM ", table, "WHERE userId = #{userId}"})
    List<Scenario> getScenarioByUserId(@Param("userId")  int userId);

    @Select({"SELECT ", selectField, " FROM ", table, "WHERE userId = #{userId} and name = #{name}"})
    Scenario getScenarioByName(@Param("userId")  int userId,@Param("name") String name);

    @Insert({"INSERT INTO ", table, "(", insertField, ")", "VALUES(#{userId}, #{name})" })
    int insert(Scenario scenario);

    @Delete({"DELETE FROM ", table, " WHERE id = #{id}"})
    int deleteScenarioById(@Param("id") int id);
}
