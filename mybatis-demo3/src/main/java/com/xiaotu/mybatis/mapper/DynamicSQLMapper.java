package com.xiaotu.mybatis.mapper;

import com.xiaotu.mybatis.pojo.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DynamicSQLMapper {


    int insertMoreByList(@Param("emps") List<Emp> emps);

    int deleteMoreByArray(@Param("eids") Integer[] eids);

    List<Emp> getEmpByChoose(Emp emp);

    List<Emp> getEmpByCondition(Emp emp);

    List<Emp> getEmpByConditionTwo(Emp emp);

    List<Emp> getEmpByConditionOne(Emp emp);


}
