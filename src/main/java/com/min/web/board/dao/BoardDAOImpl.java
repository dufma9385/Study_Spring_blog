package com.min.web.board.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.min.web.board.model.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO{

	@Inject
	private SqlSession sqlSession;
	
	@Override
	public List<BoardVO> getBoarList() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.min.web.board.boardMapper.getBoardList");
	}

	@Override
	public BoardVO getBoardContent(int bid) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.min.web.board.boardMapper.getBoardContent",bid);
	}

	@Override
	public int insertBoard(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.min.web.board.boardMapper.insertBoard", boardVO);
	}

	@Override
	public int updateBoard(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update("com.min.web.board.boardMapper.updateBoard",boardVO);
	}

	@Override
	public int deleteBoard(int bid) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.min.web.board.boardMapper.deleteBoard",bid);
	}

	@Override
	public int updateViewCnt(int bid) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update("com.min.web.board.boardMapper.updateViewCnt",bid);
	}
}