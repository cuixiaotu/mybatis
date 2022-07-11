package com.xiaotu.mybatis.mapper;

import com.xiaotu.mybatis.pojo.Dept;
import org.apache.ibatis.annotations.Param;

public interface DeptMapper {

    /*多步根据人员查询部门*/
    Dept getEmpAndDeptByStepTwo(@Param("did") int did);

    /*联表根据部门查询人员*/
    Dept getDeptAndEmp(@Param("did") int did);

    /*多步根据部门查询人员*/
    Dept getDeptAndEmpByStepOne(@Param("did") int did);
}
