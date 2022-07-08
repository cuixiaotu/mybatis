package com.xiaotu2.mybatis.pojo;

import com.xiaotu.mybatis.mapper.ParameterMapper;
import com.xiaotu.mybatis.pojo.User;
import com.xiaotu.mybatis.utils.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class ParameterMapperTest {

    @Test
    public void testGetUserList(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
        List<User> userList = mapper.findUserList();
        userList.forEach(user -> System.out.println(user));
    }
}
