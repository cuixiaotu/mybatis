package com.xiaotu.mybatis.mapper;

import com.xiaotu.mybatis.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SQLMapper {

    /*根据username模糊查询*/
    List<User> getUserByLike(@Param("username") String username);

    /*根据IDS批量删除*/
    int deleteMany(@Param("ids") String ids);

    /*查询指定表中的数据*/
    List<User> getUserByTableName(@Param("tableName") String tableName);

    /*添加用户信息*/
    void insertUser(User user);

}
