# Spring Framework 로 blog만들기

## 1. 프로젝트 생성

STS시작 - 프로젝트 생성 위치 설정

STS가 시작되면 package Explorer에 new -> Spring Legacy Project 선택

-> Spring MVC Project 선택

-> 프로젝트 패키지 이름 설정 ex) com.min.SpringWeb

top-level package설정하라고 하면 아무거나 ㅇ입력 ex) com.mycompany.myapp

#### 서버창 확인 = > 아무것도 존재하지 않는다

No server~~~ 클릭하여 설치된 서버 선택 ex) Apache -> Tomcat version

설치된 서버의 경로찾아서 선택해준다

해당 서버로 어떤 프로젝트를 구동할지 선택! ex) com.min.SpringWeb



#### => 웹브라우저에서 확인 localhost:8080/SpringWeb (프로젝트명의 마지막)

한글이 깨져서 나올때 

utf-8설정

```xml
<!-- Character Set Filter -->
  <filter>
	<filter-name>encodingFilter</filter-name>
		<filter-class>
			org.springframework.web.filter.CharacterEncodingFilter
		</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>
		<init-param>
			<param-name>forceEncoding</param-name>
			<param-value>true</param-value>
		</init-param>
  </filter>
  <filter-mapping>
	<filter-name>encodingFilter</filter-name>
	<url-pattern>/*</url-pattern>
  </filter-mapping>
```



## 2. DB 셋팅 및 연결(MySQL)

스프링 환경 설정하기 (web.xml을 참조하여 스프링 설정과 관련된 파일들의 위치를 파악하고 그 파일들을 참조한다)

> 실무에서는 설정파일을 보통 하나의 디렉토리 안에 모아 관리를 합니다.
>
> 규모가 큰 프로젝트 에서는 설정 파일이 여기 저기 다른 디렉토리 안에 있으면 관리가 힘들기 때문입니다.
>
> [ src/main/resources/ ] 아래 관리를 할 예정입니다.
>
> [ src/main/resources/ ] 아래에 spring 라는 디렉토리를 생성합니다.
>
> 아래 그램과 같이 spring 디렉토리 안에 root-context.xml을 옮겨 놓습니다.
>
> 그리고 servlet-context.xml 은 [ src/main/resources ] 에 아래에 옮겨놓습니다.
>
> 
>
> 출처: https://freehoon.tistory.com/101 [훈잇 블로그]

*** 스프링 프로젝트에서 src/main/resource 에 폴더 생성시 패키지로 보이는 문제

1. 해당 프로젝트에서 Properties선택
2.  Java Build Path -> Source 선택
3. /src/main/resources -> Excluded -> Edit
4. Exclusion patterns의 Add선택 **입력 후 적용 



**src/views의 web.xml 수정**

context-param 의 param-value

servlet의 param-value

root-context를 *-context로 수정하면 앞으로 추가할 설정파일을 000-context.xml처럼 저장하면 스프링에서 자동인식 가능

```xml
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:spring/*-context.xml</param-value>
  </context-param>
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <servlet>
    <servlet-name>appServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:servlet-context.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
```



### 데이터베이스 및 사용자 추가

cmd = mysql -u root -p

```sql
mysql > CREATE DATABASE 'MESS';  //데이터베이스 생성

mysql> CREATE USER 'mess'@'%'; //mess사용자 추가

mysql> GRANT ALL PRIVILEGES ON MESS.* TO 'mess'@'%' IDENTIFIED BY 'mess';
//mess디비의 모든테이블에 대해 mess라는 비밀번호로 접근가능

mysql> FLUSH PRIVILEGES; //설정반영
```



### 스프링과 데이터베이스 접속 Mybatis 셋팅

#### Dependency 추가

pom.xml에  추가

```xml
<!-- mysql -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.13</version>
</dependency>

<!-- mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.4.6</version>
</dependency>


<!-- mybatis-spring -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>1.3.2</version>
</dependency>

<!-- spring-jdbc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>${org.springframework-version}</version>
</dependency>

<!-- spring-test -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>${org.springframework-version}</version>
    <scope>test</scope>
</dependency>
```

스프링 버전 수정하기 

```xml
<properties>

	<java-version>1.8</java-version>

	<org.springframework-version>5.1.4.RELEASE</org.springframework-version>

	<org.aspectj-version>1.9.2</org.aspectj-version>

	<org.slf4j-version>1.7.25</org.slf4j-version>

</properties>
```

**(JRE System Library - properties에서 버전을 1.8로 바꿔주기**)

#### 데이터베이스 설정 파일 추가

/src/main/resource/spring 에 dataSource-context.xml파일 추가

dataSource-context.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"

	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 
	xmlns:mybatis-spring="http://mybatis.org/schema/mybatis-spring"

	xsi:schemaLocation="http://mybatis.org/schema/mybatis-spring 		
                        http://mybatis.org/schema/mybatis-spring.xsd
						http://www.springframework.org/schema/beans 								http://www.springframework.org/schema/beans/spring-beans.xsd">	

<!--dataSource 객체 설정 -->

<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">

<property name="driverClassName" value="com.mysql.cj.jdbc.Driver" />

<property name="url" value="jdbc:mysql://127.0.0.1:3306/mess?useSSL=false&amp;serverTimezone=Asia/Seoul" />       

        <property name="username" value="mess"></property>

        <property name="password" value="mess"></property>

</bean>  



<!-- SqlSessionFactory 객체 설정 -->

<bean id="SqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">

<property name="dataSource" ref="dataSource" />       

<property name="mapperLocations" value="classpath:/mappers/**/*Mapper.xml" />

</bean>

	

<!-- SqlSession Template 설정 -->

<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate" destroy-method="clearCache">

<constructor-arg name="sqlSessionFactory" ref="SqlSessionFactory" />

</bean>

</beans>
```

*** classpath = /src/main/resources/main 아래 모든 하위 디렉토리 포함한 Mapper.xml로 끝나는 ㅁ모든 파일 참조



##### 데이터베이스 접속 확인

MysqlConnectionTest.java

```java
package com.mycompany.myapp;

import java.sql.Connection;

import javax.inject.*;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/dataSource-context.xml" })

public class MysqlConnectionTest {

	private static final Logger logger = LoggerFactory.getLogger(MysqlConnectionTest.class);

	@Inject
	private DataSource ds;
	
	@Test
	public void testConnection() {
		Connection con; //jre-1.7 이상에서 사용가능
		try{
			con = ds.getConnection();
			logger.info("\n MySQL 연결 : "+ con);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
```

 아래와 같이 나오면 성공

```
INFO : com.mycompany.myapp.MybatisTest - 
 Sql Session : org.apache.ibatis.session.defaults.DefaultSqlSession@7dc0f706
```



##### Mybatis 관련 설정 테스트

Mybatis.java

```java
package com.mycompany.myapp;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = { "classpath:spring/dataSource-context.xml" })

public class MybatisTest {
	private static final Logger logger = LoggerFactory.getLogger(MybatisTest.class);

	@Inject
	private SqlSessionFactory sessionFactory;

	@Test
	public void testSessionFactory() {
		logger.info("\n Session Factory : " + sessionFactory);
	}

	@Test
	public void testSqlSession() {

		try (SqlSession session = sessionFactory.openSession()){
			logger.info("\n Sql Session : " + session);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
```

아래와 같이 나오면 성공

```
INFO : com.mycompany.myapp.MybatisTest - 
 Session Factory : org.apache.ibatis.session.defaults.DefaultSqlSessionFactory@158a8276
```

