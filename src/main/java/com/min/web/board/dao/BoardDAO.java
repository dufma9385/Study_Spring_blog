/**
 * 
 */
package com.min.web.board.dao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.min.web.board.model.BoardVO;

/**
 * @author minkyeong
 *
 */
public interface BoardDAO {
	
	public List<BoardVO> getBoarList() throws Exception;
	
	public BoardVO getBoardContent(int bid) throws Exception;
	
	public int insertBoard(BoardVO boardVO) throws Exception;
	
	public int updateBoard(BoardVO boardVO) throws Exception;
	
	public int deleteBoard(int bid) throws Exception;
	
	public int updateViewCnt(int bid) throws Exception;
}
