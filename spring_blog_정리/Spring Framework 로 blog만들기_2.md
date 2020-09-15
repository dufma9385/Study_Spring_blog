#### 테이블 만들기(게시물을 저장할)

```mysql
create table tbl_board(

  bid      int auto_increment comment '일련번호' primary key,
  cate_cd  varchar(20)   not null comment '게시글 카테고리',
  title    varchar(200)  not null comment '제목',
  content  text          not null comment '게시글',
  tag      varchar(1000) null comment '태그',
  view_cnt int default 0 not null comment '카운트',
  reg_id   varchar(45)   not null comment '작성자',
  reg_dt   date          not null comment '작성일',
  edit_dt  date          not null comment '수정일'
); 

```

프로그램에서 정상적으로 DB와 데이터를 주고 받는지 확인하는 테스트 작업

DB부터 구성하며 화면 만들기 

VO먼저 생성 DAO -> Service -> Controller -> View 순서로 개발



##### Controller, Service, DAO, Model 등의 자바파일이 위치할 패키지들 생성하기(src/main/java밑에)

```
com.min.web.board.controller
com.min.web.board.dao
com.min.web.board.model
com.min.web.board.service
```

com.min.web.board.model에 BoardVO.java

```java
package com.min.web.board.model;

public class BoardVO {

	public int bid;

	public String cate_cd;

	public String title;

	public String content;

	public String tag;

	public int view_cnt;

	public String reg_id;

	public String reg_dt;

	public String edit_dt;

}
```



##### boardMapper 수정

```java
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE mapper

    PUBLIC "-//mybatis.org/DTD Mapper 3.0//EN"

    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.freehoon.web.board.boardMapper">

	<select id="getBoardList" resultType="com.freehoon.web.board.model.BoardVO">

		SELECT

			BID, CATE_CD, TITLE, CONTENT, TAG, VIEW_CNT, REG_ID, REG_DT, EDIT_DT

		FROM

			TBL_BOARD

	</select>
	

	<select id="getBoardContent" resultType="com.freehoon.web.board.model.BoardVO" parameterType="com.freehoon.web.board.model.BoardVO">

		SELECT

			BID, CATE_CD, TITLE, CONTENT, TAG, VIEW_CNT, REG_ID, REG_DT, EDIT_DT

		FROM

			TBL_BOARD

		WHERE

			BID = #{bid}

	</select>


	<insert id="insertBoard" parameterType="com.freehoon.web.board.model.BoardVO">

		INSERT INTO TBL_BOARD (CATE_CD, TITLE, CONTENT, TAG, VIEW_CNT, REG_ID, REG_DT, EDIT_DT)

		VALUES (

			#{cate_cd}

			, #{title}

			, #{content}

			, #{tag}

			, 0

			, #{reg_id}

			, now()

			, now()

		)

	</insert>
        
	<update id="updateBoard" parameterType="com.freehoon.web.board.model.BoardVO">

		UPDATE TBL_BOARD SET

			CATE_CD = #{cate_cd}

			, TITLE = #{title}

			, CONTENT = #{content}

			, TAG = #{tag}

			, EDIT_DT = now()

		WHERE

			BID = ${bid}

	</update>

	<delete id="deleteBoard" parameterType="int">

		DELETE FROM TBL_BOARD

		WHERE BID = #{bid}

	</delete>

	<update id="updateViewCnt" parameterType="com.freehoon.web.board.model.BoardVO">

		UPDATE TBL_BOARD SET

			VIEW_CNT = VIEW_CNT + 1

		WHERE

			BID = #{bid}

	</update>

</mapper>

```

** namespace는 다음에 작성할 DAO 구현체 쪽에서 원하는 mapper를 찾기위해 사용됨
따라서 다른 mapper들과 겹치지 않도록 작성한다.
보통 패키지명(com.min.web)+프로그램명(board)+파일명(boardMapper)의 조합 많이 사용



##### DAO작성하기 

com.min.web.board.dao에 작성

BoardDAO.java

```java
package com.min.web.board.dao;



import java.util.List;



import com.min.web.board.model.BoardVO;



public interface BoardDAO {



	public List<BoardVO> getBoardList() throws Exception;

	

	public BoardVO getBoardContent(int bid) throws Exception;

	

	public int insertBoard(BoardVO boardVO) throws Exception;


 
	

	public int updateBoard(BoardVO boardVO) throws Exception;

	

	public int deleteBoard(int bid) throws Exception;

	

	public int updateViewCnt(int bid) throws Exception;

}
```

##### BoardDAOImpl.java

```java
package com.min.web.board.dao;



import java.util.List;



import javax.inject.Inject;



import org.apache.ibatis.session.SqlSession;

import org.springframework.stereotype.Repository;



import com.freehoon.web.board.model.BoardVO;



@Repository

public class BoardDAOImpl implements BoardDAO {

	

	@Inject

	private SqlSession sqlSession;



	@Override

	public List<BoardVO> getBoardList() throws Exception {

		return sqlSession.selectList("com.freehoon.web.board.boardMapper.getBoardList");

	}



	@Override

	public BoardVO getBoardContent(int bid) throws Exception {

		return sqlSession.selectOne("com.freehoon.web.board.boardMapper.getBoardContent", bid);

	}



	@Override

	public int insertBoard(BoardVO boardVO) throws Exception {

		return sqlSession.insert("com.freehoon.web.board.boardMapper.insertBoard", boardVO);

	}



	@Override

	public int updateBoard(BoardVO boardVO) throws Exception {

		return sqlSession.update("com.freehoon.web.board.boardMapper.updateBoard", boardVO);

	}



	@Override

	public int deleteBoard(int bid) throws Exception {

		return sqlSession.insert("com.freehoon.web.board.boardMapper.deleteBoard", bid);

	}



	@Override

	public int updateViewCnt(int bid) throws Exception {

		return sqlSession.update("com.freehoon.web.board.boardMapper.updateViewCnt", bid);

	}

	

}
```

SqlSession객체를 통해 boardMapper에 작성해 놓ㅇ은 SQL문 실행



##### BoardDAOTest.java

```java
package com.min.web;

import java.util.List;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.min.web.board.dao.BoardDAO;
import com.min.web.board.model.BoardVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/root-context.xml",
		"classpath:spring/dataSource-context.xml"
	})

public class BoardDAOTest{
	private static final Logger logger = LoggerFactory.getLogger(BoardDAOTest.class);
	
	@Inject
	private BoardDAO boardDAO;
	
	@Test
	public void testGetBoardList() throws Exception{
		List<BoardVO> boardList = boardDAO.getBoarList();
		logger.info("\n Board List \n");
		if(boardList.size() > 0) {
			for(BoardVO list : boardList) {
				logger.info(list.title);
			}
		}else {
			logger.info("데이터가 없습니다");
		}
	}
	
	@Test @Ignore

	public void testGetBoardContent() throws Exception {

		BoardVO boardVO = boardDAO.getBoardContent(1);

		logger.info("\n Board List \n ");

		if(boardVO != null) {

			logger.info("글번호 : " + boardVO.getBid() );

			logger.info("글제목 : " + boardVO.getTitle() );

			logger.info("글내용 : " + boardVO.getContent() );

			logger.info("글태그 : " + boardVO.getTag() );

			logger.info("조회수 : " + boardVO.getView_cnt() );

			logger.info("작성자 : " + boardVO.getReg_id() );

			logger.info("작성일 : " + boardVO.getReg_dt() );

			logger.info("수정일 : " + boardVO.getEdit_dt() );

		} else {

			logger.info("데이터가 없습니다.");

		}

	}

	

	@Test @Ignore 

	public void testInsertBoard() throws Exception {

		BoardVO boardVO = new BoardVO();

		boardVO.setCate_cd("1");

		boardVO.setTitle("첫번째 게시물 입니다.");

		boardVO.setContent("첫번째 게시물입니다.");

		boardVO.setTag("1");

		boardVO.setReg_id("1");

		

		int result = boardDAO.insertBoard(boardVO);

		logger.info("\n Insert Board Result " +result);

		if(result == 1) {

			logger.info("\n 게시물 등록 성공 ");

		} else {

			logger.info("\n 게시물 등록 실패");

		}

	}

	

	@Test @Ignore 

	public void testUpdateBoard() throws Exception {

		BoardVO boardVO = new BoardVO();

		boardVO.setBid(1);

		boardVO.setCate_cd("1");

		boardVO.setTitle("첫번째 게시물 입니다-수정 합니다.");

		boardVO.setContent("첫번째 게시물입니다-수정합니다.");

		boardVO.setTag("1-1");

		

		int result = boardDAO.updateBoard(boardVO);

		logger.info("\n Update Board Result \n ");

		if(result > 0) {

			logger.info("\n 게시물 수정 성공 ");

		} else {

			logger.info("\n 게시물 수정 실패");

		}

	}

	

	@Test   @Ignore

	public void tesDeleteBoard() throws Exception {

		

		int result = boardDAO.deleteBoard(1);

		logger.info("\n Delete Board Result \n ");

		if(result > 0) {

			logger.info("\n 게시물 삭제 성공 ");

		} else {

			logger.info("\n 게시물 삭제 실패");

		}

	}



	@Test @Ignore

	public void testUpdateViewCnt() throws Exception {

		

		int result = boardDAO.updateViewCnt(1);

		logger.info("\n Update View Count Result \n ");

		if(result > 0) {

			logger.info("\n 게시물 조회수 업데이트 성공 ");

		} else {

			logger.info("\n 게시물 조회수 업데이트 실패");

		}

	}
}
```

testGetBoardList 부분에서 계속 에러가 뜬다...