package com.xiaotu.mybatis.mapper;

public interface UserMapper {

    /* 两个一致
    *  1.映射文件的namespace要和mapper里package一致
    *  2.映射文件的SQL语句的ID要和mapper方法名一致
    * */
    int insertUser();

}
