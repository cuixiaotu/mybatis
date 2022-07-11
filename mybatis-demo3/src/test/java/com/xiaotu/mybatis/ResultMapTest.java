package com.xiaotu.mybatis;

import com.xiaotu.mybatis.mapper.DeptMapper;
import com.xiaotu.mybatis.mapper.EmpMapper;
import com.xiaotu.mybatis.utils.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class ResultMapTest {

    @Test
    public void testLazy(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        System.out.println(mapper.getDeptAndEmpByStepOne(1).getDeptName());
    }

    @Test
    public void testGetDeptAndEmpByStep(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        System.out.println(mapper.getDeptAndEmpByStepOne(1));
    }

    @Test
    public void testGetDeptAndEmp(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        System.out.println(mapper.getDeptAndEmp(1));
    }

    @Test
    public void testGetEmpAndDeptByStep(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        System.out.println(mapper.getEmpAndDeptByStepOne(1));
    }

    @Test
    public void testGetEmpAndDept(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        System.out.println(mapper.getEmpAndDept(1));
    }

    @Test
    public void testGetAllEmp(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        System.out.println(mapper.getAllEmp());
    }
}
