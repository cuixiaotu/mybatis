# 第一章 MyBatis简介

## 1、MyBatis历史

MyBatis最初是Apache的一个开源项目iBatis, 2010年6月这个项目由Apache Software Foundation迁移到了Google Code。随着开发团队转投Google Code旗下，iBatis3.x正式更名为MyBatis。代码于2013年11月迁移到Github
iBatis一词来源于“internet”和“abatis”的组合，是一个基于Java的持久层框架。iBatis提供的持久层框架包括SQL Maps和Data Access Objects（DAO）

## 2、MyBatis特性

1. MyBatis 是支持定制化 SQL、存储过程以及高级映射的优秀的持久层框架
2. MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集
3. MyBatis可以使用简单的XML或注解用于配置和原始映射，将接口和Java的POJO（Plain Old Java Objects，普通的Java对象）映射成数据库中的记录
4. MyBatis 是一个 半自动的ORM（Object Relation Mapping）框架

## 3、MyBatis下载

https://github.com/mybatis/mybatis-3

##  4、和其它持久化层技术对比

- JDBC
  SQL 夹杂在Java代码中耦合度高，导致硬编码内伤
  维护不易且实际开发需求中 SQL 有变化，频繁修改的情况多见
  代码冗长，开发效率低
- Hibernate 和 JPA
  操作简便，开发效率高
  程序中的长难复杂 SQL 需要绕过框架
  内部自动生产的 SQL，不容易做特殊优化
  基于全映射的全自动框架，大量字段的 POJO 进行部分映射时比较困难。
  反射操作太多，导致数据库性能下降
- MyBatis
  轻量级，性能出色
  SQL 和 Java 编码分开，功能边界清晰。Java代码专注业务、SQL语句专注数据
  开发效率稍逊于HIbernate，但是完全能够接受

# 第二章 搭建Mybatis

- IDE：idea 2020.2
- 构建工具：maven 3.8.6
- MySQL版本：MySQL 8.0.12
- MyBatis版本：MyBatis 3.5.7

## 创建Maven工厂

- 打包方式:jar

- 引入依赖

  ```xml
  <dependencies>
      <dependency>
          <groupId>org.mybatis</groupId>
          <artifactId>mybatis</artifactId>
          <version>3.5.3</version>
      </dependency>
      <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>4.13.1</version>
          <scope>test</scope>
      </dependency>
      <!--mysql驱动-->
      <dependency>
          <groupId>mysql</groupId>
          <artifactId>mysql-connector-java</artifactId>
          <version>8.0.28</version>
      </dependency>
  </dependencies>
  ```



## 创建MyBatis的核心配置文件

>习惯上命名为mybatis-config.xml，这个文件名仅仅只是建议，并非强制要求。将来整合Spring之后，这个配置文件可以省略，所以大家操作时可以直接复制、粘贴。
>核心配置文件主要用于配置连接数据库的环境以及MyBatis的全局配置信息
>核心配置文件存放的位置是src/main/resources目录下

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//com.xaiotu.mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--设置连接数据库的环境-->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <!--高版本驱动com.mysql.cj.jdbc.Drive-->
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="mappers/UserMapper.xml"/>
    </mappers>
</configuration>
```

## 创建mapper接口

> MyBatis的mapper接口相当于以前的dao.区别在于，mapper仅仅是接口，我们不需要提供实现类

```java
public interface UserMapper {

    /* 两个一致
    *  1.映射文件的namespace要和mapper里package一致
    *  2.映射文件的SQL语句的ID要和mapper方法名一致
    * */
    int insertUser();

}
```





## 创建MyBatis的映射文件

相关概念：ORM（Object Relationship Mapping）对象关系映射。

- 对象：Java的实体类对象
- 关系：关系型数据库
- 映射：二者之间的对应关系

| Java概念 | 数据库概念 |
| :------- | ---------- |
| 类       | 表         |
| 属性     | 字段/列    |
| 对象     | 记录/行    |

- 映射文件的命名规则
  - 表所对应的实体类的类名+Mapper.xml
  - 例如：表t_user，映射的实体类为User，所对应的映射文件为UserMapper.xml
  - 因此一个映射文件对应一个实体类，对应一张表的操作
  - MyBatis映射文件用于编写SQL，访问以及操作表中的数据
  - MyBatis映射文件存放的位置是src/main/resources/mappers目录下

- MyBatis中可以面向接口操作数据，要保证两个一致
  - mapper接口的全类名和映射文件的命名空间（namespace）保持一致
  - mapper接口中方法的方法名和映射文件中编写SQL的标签的id属性保持一致

```xml-dtd
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//com.xaiotu.mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.xiaotu.mybatis.mapper.UserMapper">
    <insert id="insertUser">
        insert into user values (null ,'admin','123456',18,'男','123124@qq.com')
    </insert>
</mapper>
```



## 通过junit测试功能

- SqlSession：代表Java程序和数据库之间的会话。（HttpSession是Java程序和浏览器之间的会话）
- SqlSessionFactory：是“生产”SqlSession的“工厂”
- 工厂模式：如果创建某一个对象，使用的过程基本固定，那么我们就可以把创建这个对象的相关代码封装到一个“工厂类”中，以后都使用这个工厂类来“生产”我们需要的对象

```java
public class MyBatisTest {

    @Test
    public void testMyBatis() throws IOException {
        //加载核心配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        //获取SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory =sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        //获取mapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //测试功能
        int res = mapper.insertUser();
        //提交事务
        sqlSession.commit();
        System.out.println("res:"+res);
    }
}
```

- 此时需要手动提交事务，如果要自动提交事务，则在获取sqlSession对象时，使用`SqlSession sqlSession = sqlSessionFactory.openSession(true);`，传入一个Boolean类型的参数，值为true，这样就可以自动提交 



## 加入log4j日志功能

1.加入依赖

```xml
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>2.17.2</version>
</dependency>
```

2.加入log4j的配置文件

- log4j的配置文件名为log4j.xml，存放的位置是src/main/resources目录下
- 日志的级别：FATAL(致命)>ERROR(错误)>WARN(警告)>INFO(信息)>DEBUG(调试) 从左到右打印的内容越来越详细

```xml-dtd
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">
    <appender name="STDOUT" class="org.apache.log4j.ConsoleAppender">
        <param name="Encoding" value="UTF-8"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%-5p %d{MM-dd HH:mm:ss,SSS} %m (%F:%L) \n"/>
        </layout>
    </appender>
    <logger name="java.sql">
        <level value="debug" />
    </logger>
    <logger name="org.apache.ibatis">
        <level value="info" />
    </logger>
    <root>
        <level value="debug"/>
        <appender-ref ref="STDOUT"/>
    </root>
</log4j:configuration>
```



## MyBatis的增删改查

```xml-dtd
<insert id="insertUser">
    insert into user values (null ,'admin','123456',18,'男','123124@qq.com')
</insert>
<update id="updateUser">
    update user set username = "张三" where id = 2;
</update>
<delete id="deleteUser">
    delete from user where id = 3;
</delete>
<!--
     查询功能必须设置 resultType或resultMap
     resultType 设置默认的映射关系
     resultMap  设置自定义的映射关系
-->
<select id="getUserById" resultType="com.xiaotu.mybatis.pojo.User">
    select * from user where id = 1;
</select>
<select id="getUserList" resultType="com.xiaotu.mybatis.pojo.User">
    select * from user;
</select>
```

注意：
查询的标签select必须设置属性resultType或resultMap，用于设置实体类和数据库表的映射关系

- resultType：自动映射，用于属性名和表中字段名一致的情况
- resultMap：自定义映射，用于一对多或多对一或字段名和属性名不一致的情况

当查询的数据为多条时，不能使用实体类作为返回值，只能使用集合，否则会抛出异常TooManyResultsException；但是若查询的数据只有一条，可以使用实体类或集合作为返回值



## 核心配置文件详解

> 核心配置文件中的标签必须按照固定的顺序(**有的标签可以不写，但顺序一定不能乱**)：
> properties、settings、typeAliases、typeHandlers、objectFactory、objectWrapperFactory、reflectorFactory、plugins、environments、databaseIdProvider、mappers

```xml-dtd
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//com.xaiotu.mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--设置连接数据库的环境-->
    <properties resource="jdbc.properties" />

    <!-- typeAliases:设置某个类型的别名
         type：设置需要设置别名的类型
         alias:设置某个类型的别名，若不设置改属性，则为类名
    -->
    <typeAliases>
<!--        <typeAlias type="com.xiaotu.mybatis.pojo"/>-->
        <!--以包为单位时,即设置包下的所有类型为默认的别名-->
        <package name="com.xiaotu.mybatis.pojo"/>
    </typeAliases>

    <!-- environments:配置多个连接数据库的环境
            default:设置默认使用的环境ID
    -->
    <environments default="development">
    <!-- environment：配置某个具体的环境
            id：表示连接数据库的环境的唯一标识，不能重复
    -->
        <environment id="development">
            <!-- transactionManager: 设置事务管理方式
                    type:"JDBC|MANAGED"
                    JDBC：当前环境中，执行SQL时，使用的是JDBC中原生的事务管理
                    MANAGED：被管理 spring
            -->
            <transactionManager type="JDBC"/>
            <!-- dataSource：配置数据源
                    type:设置数据源类型
                    type="POOLED|UNPOOLED|JNDI"
                    POOLED:表示使用数据库连接池缓存数据库连接
                    UNPOOLED:表示不使用数据库连接池
                    JNDI:表示使用上下文中的数据源
            -->
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <!--<mapper resource="mappers/UserMapper.xml"/>-->
        <!--以包为单位，将包下的所有映射文件引入核心配置文件
            1.此方式必须保证mapper接口和mapper的映射文件在相同的包下
            2.mapper要和映射文件的名字一致
        -->
        <package name="com.xiaotu.mybatis.mapper"/>
    </mappers>
</configuration>
```



创建mybatis-config.xml模板

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//com.xaiotu.mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <properties resource="jdbc.properties"/>
    <typeAliases>
        <package name=""/>
    </typeAliases>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <package name=""/>
    </mappers>
</configuration>
```



创建映射文件模板

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//com.xaiotu.mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="">

</mapper>
```





# 第三章 MyBatis获取参数值的两种方式（重点）

- MyBatis获取参数值的两种方式：${}和#{}
- ${}的本质就是字符串拼接，#{}的本质就是占位符赋值
- ${}使用字符串拼接的方式拼接sql，若为字符串类型或日期类型的字段进行赋值时，需要手动加单引号；但是#{}使用占位符赋值的方式拼接sql，此时为字符串类型或日期类型的字段进行赋值时，可以自动添加单引号



## 单个字面量类型的参数

- 若mapper接口中的方法参数为单个的字面量类型，此时可以使用${}和#{}以任意的名称（最好见名识意）获取参数的值，注意${}需要手动加单引号

```xml
<select id="findUserById" resultType="User">
    select * from user where id = '${id}';
</select>

<select id="findUserByUsername" resultType="User">
    select * from user where username = #{username};
</select>
```



## 多个字面量类型的参数

- 若mapper接口中的方法参数为多个时，此时MyBatis会自动将这些参数放在一个map集合中
  以arg0,arg1…为键，以参数为值；
- 以param1,param2…为键，以参数为值；
  因此只需要通过${}和#{}访问map集合的键就可以获取相对应的值，注意${}需要手动加单引号。
- 使用arg或者param都行，要注意的是，arg是从arg0开始的，param是从param1开始的

```xml
<select id="checkLogin" resultType="user">
    select * from user where usernanme = #{arg0} and password = #{arg1};
</select>
<select id="checkLogin2" resultType="user">
    select * from user where usernanme = '${arg0}' and password = '${arg1}';
</select>
```



## map集合类型的参数

- 若mapper接口中的方法需要的参数为多个时，此时可以手动创建map集合，将这些数据放在map中只需要通过${}和#{}访问map集合的键就可以获取相对应的值，注意${}需要手动加单引号

```java
    User checkLoginByMap(Map<String,Object> map);
```

```xml
<select id="checkLoginByMap" resultType="user">
    select * from user  where username = #{username} and password = #{password} limit 1;
</select>
```

```java
public void testCheckLoginByMap(){
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();
    ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
    Map<String,Object> map = new HashMap<>();
    map.put("username","admin");
    map.put("password","123456");
    User user = mapper.checkLoginByMap(map);
    System.out.println(user);
}
```

## 实体类类型的参数

- 若mapper接口中的方法参数为实体类对象时此时可以使用${}和#{}，通过访问实体类对象中的属性名获取属性值，注意${}需要手动加单引号

```java
 int insertUser(User user);
```

``` xml
<insert id="insertUser">
    insert into user values (null ,#{username},#{password},#{age},#{sex},#{email});
</insert>
```

```java
public void testInsertUser(){
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();
    ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
    User user = new User(null,"theodore","123456",12,"男","124124@qq.com");
    mapper.insertUser(user);
}
```



## 使用@Param标识参数

- 可以通过@Param注解标识mapper接口中的方法参数，此时，会将这些参数放在map集合中
  - 以@Param注解的value属性值为键，以参数为值；
  - 以param1,param2…为键，以参数为值；
- 只需要通过${}和#{}访问map集合的键就可以获取相对应的值，注意${}需要手动加单引号

```java
User checkLoginByParam(@Param("username") String username,@Param("password") String password);
```

```xml
<select id="checkLoginByParam" resultType="user">
    select * from user  where username = #{username} and password = #{password} limit 1;
</select>
```

```java
@Test
public void testCheckLoginByParam(){
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();
    ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
    User user =mapper.checkLoginByParam("theodore","123456");
    System.out.println(user);
}
```

## 总结

- 建议分成两种情况进行处理
  1. 实体类类型的参数
  2. 使用@Param标识参数



## @param源码解析



![image-20220708182006873](images/readme/image-20220708182006873.png)



![image-20220708182645419](images/readme/image-20220708182645419.png)

![image-20220708183303259](images/readme/image-20220708183303259.png)



# 第四章 各种查询功能

1. 如果查询出的数据只有一条，可以通过
   - 实体类对象接收
   - List集合接收
   - Map集合接收，结果{password=123456, sex=男, id=1, age=23, username=admin

2. 如果查询出的数据有多条，一定不能用实体类对象接收，会抛异常TooManyResultsException，可以通过
   - 实体类类型的LIst集合接收
   - Map类型的LIst集合接收
   - 在mapper接口的方法上添加@MapKey注解

## 查询一个实体类对象

```java
User getUserById(@Param("id") int id)
```

```xml
<select id="getUserById" resultType="user">
    select * from user where id = #{id}
</select>
```

## 查询一个List集合

```java
/*查询用户列表*/
List<User> getUserList();
```

```xml
<select id="getUserList" resultType="user">
    select * from user;
</select>
```

## 查询单个数据

```java
/*查询用户总记录数*/
int getCount();
```

```xml
<select id="getCount" resultType="int">
    select count(1) from user;
</select>
```

## 查询一条数据为map集合

```java
/*查询一条数据为map集合*/
Map<String,Object> getUserToMap(@Param("id") int id);
```

```xml
<select id="getUserToMap" resultType="map">
    select * from user where id = #{id}
</select>
```

## 查询多条数据为map集合

```java
/*查询多条数据为map集合*/
//List<Map<String,Object>> getAllUserToMap();
@MapKey("id")
Map<String,Object> getAllUserToMap();
```

```xml
<select id="getAllUserToMap" resultType="map">
    select * from user;
</select>
```

## 模糊查询

```java
/* 根据用户名进行模糊查询
 * @param username 
 * @return java.util.List<com.atguigu.mybatis.pojo.User>
 */
List<User> getUserByLike(@Param("username") String username);
<!--List<User> getUserByLike(@Param("username") String username);-->
```

```xml
<select id="getUserByLike" resultType="User">
	<!--select * from t_user where username like '%${username}%'-->  
	<!--select * from t_user where username like concat('%',#{username},'%')-->  
	select * from t_user where username like "%"#{username}"%"
</select>
```



## 批量删除

```java
int deleteMore(@Param("ids") String ids);
```

```xml
<delete id="deleteMore">
delete from t_user where id in (${ids})
</delete>
```



## 动态设置表名

只能使用${}，因为表名不能加单引号

```java
List<User> getUserByTable(@Param("tableName") String tableName);
```



```xml
<select id="getUserByTable" resultType="User">
	select * from ${tableName}
</select>
```


## 添加功能获取自增的主键

- 使用场景

  - t_clazz(clazz_id,clazz_name)
  - t_student(student_id,student_name,clazz_id)

  1. 添加班级信息
  2. 获取新添加的班级的id
  3. 为班级分配学生，即将某学的班级id修改为新添加的班级的id

- 在mapper.xml中设置两个属性
  - useGeneratedKeys：设置使用自增的主键
  - keyProperty：因为增删改有统一的返回值是受影响的行数，因此只能将获取的自增的主键放在传输的参数user对象的某个属性中

```java
void insertUser(User user)
```

```xml
<insert id="insertUser" useGeneratedKeys="true" keyProperty="id">
    insert into user values(null,#{username},#{password},#{age},#{sex},#{email})
</insert>
```




自定义映射resultMap
resultMap处理字段和属性的映射关系
resultMap：设置自定义映射
属性：
id：表示自定义映射的唯一标识，不能重复
type：查询的数据要映射的实体类的类型
子标签：
id：设置主键的映射关系
result：设置普通字段的映射关系
子标签属性：
property：设置映射关系中实体类中的属性名
column：设置映射关系中表中的字段名
若字段名和实体类中的属性名不一致，则可以通过resultMap设置自定义映射，即使字段名和属性名一致的属性也要映射，也就是全部属性都要列出来
<resultMap id="empResultMap" type="Emp">
<id property="eid" column="eid"></id>
<result property="empName" column="emp_name"></result>
<result property="age" column="age"></result>
<result property="sex" column="sex"></result>
<result property="email" column="email"></result>
</resultMap>
<!--List<Emp> getAllEmp();-->

<select id="getAllEmp" resultMap="empResultMap">
	select * from t_emp
</select>
1
2
3
4
5
6
7
8
9
10
11
若字段名和实体类中的属性名不一致，但是字段名符合数据库的规则（使用_），实体类中的属性名符合Java的规则（使用驼峰）。此时也可通过以下两种方式处理字段名和实体类中的属性的映射关系
可以通过为字段起别名的方式，保证和实体类中的属性名保持一致
<!--List<Emp> getAllEmp();-->
<select id="getAllEmp" resultType="Emp">
	select eid,emp_name empName,age,sex,email from t_emp
</select>
1
2
3
4
可以在MyBatis的核心配置文件中的setting标签中，设置一个全局配置信息mapUnderscoreToCamelCase，可以在查询表中数据时，自动将_类型的字段名转换为驼峰，例如：字段名user_name，设置了mapUnderscoreToCamelCase，此时字段名就会转换为userName。核心配置文件详解
```
