package com.xiaotu.mybatis.pojo;

import com.xiaotu.mybatis.mapper.SQLMapper;
import com.xiaotu.mybatis.utils.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class SQLMapperTest {

    @Test
    public void testGetUserByLike(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        SQLMapper mapper = sqlSession.getMapper(SQLMapper.class);
        System.out.println(mapper.getUserByLike("xiaotu"));
    }


    @Test
    public void testDeleteMany(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        SQLMapper mapper = sqlSession.getMapper(SQLMapper.class);
        System.out.println(mapper.deleteMany("2"));
    }

    @Test
    public void testGetUserByTableName(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        SQLMapper mapper = sqlSession.getMapper(SQLMapper.class);
        System.out.println(mapper.getUserByTableName("user"));
    }

    @Test
    public void testInsertUser(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        SQLMapper mapper = sqlSession.getMapper(SQLMapper.class);
        User user = new User(null,"cuixiaotu","123456",17,"男","616364596@qq.com");
        mapper.insertUser(user);
        System.out.println(user);
    }

}
