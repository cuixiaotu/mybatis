package com.xiaotu.mybatis.mapper;

import com.xiaotu.mybatis.pojo.Emp;
import org.apache.ibatis.annotations.Param;

public interface CacheMapper {

    void insertEmp(Emp emp);

    Emp getEmpByEid(@Param("eid") Integer eid);

}
