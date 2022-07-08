package com.xiaotu.mybatis.mapper;

import com.xiaotu.mybatis.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ParameterMapper {

    int insertUser(User user);

    void updateUser();

    int deleteUser();

    User findUserById(String id);
    User findUserByUsername(String username);
    User checkLogin(String username,String password);
    User checkLoginByMap(Map<String,Object> map);

    List<User> findUserList();

    User checkLoginByParam(@Param("username") String username,@Param("password") String password);

}
