package com.xiaotu.mybatis.mapper;

import com.xiaotu.mybatis.pojo.User;

import java.util.List;

public interface ParameterMapper {

    public int insertUser();

    public void updateUser();

    public int deleteUser();

    public User findUserById();

    public List<User> findUserList();

}
