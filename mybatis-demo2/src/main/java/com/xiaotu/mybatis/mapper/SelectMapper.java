package com.xiaotu.mybatis.mapper;

import com.xiaotu.mybatis.pojo.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SelectMapper {

    /*根据ID查询用户信息*/
    User getUserById(@Param("id") Integer id);

    /*查询用户列表*/
    List<User> getUserList();

    /*查询用户总记录数*/
    int getCount();

    /*查询一条数据为map集合*/
    Map<String,Object> getUserToMap(@Param("id") int id);

    /*查询多条数据为map集合*/
    //    List<Map<String,Object>> getAllUserToMap();
    @MapKey("id")
    Map<String,Object> getAllUserToMap();

}
