package com.zhaolei.dao;

import org.apache.ibatis.annotations.*;

import com.zhaolei.model.User;

import java.util.List;

/**
 * Created by zhaolei on 2017/4/14.
 */

@Mapper
public interface UserDao {
    String table = "user";
    String id= "id";
    String selectField = "id, account, password";
    String insertField = "account, password";

    @Select({"SELECT ", selectField, " FROM ", table})
    List<User> getAll();

    @Select({"SELECT ", selectField, " FROM ", table, "WHERE account = #{account}"})
    User getUserByAccount(@Param("account")  String account);

    @Insert({"INSERT INTO ", table, "(", insertField, ")", "VALUES(#{account}, #{password})" })
    int insert(User user);

    @Update({"UPDATE",table,"set password = #{password} where id = #{id}"})
    int update(User user);

}
