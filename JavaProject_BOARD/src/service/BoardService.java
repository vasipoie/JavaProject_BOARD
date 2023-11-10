package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.BoardDAO;
import vo.Board;

public class BoardService {
	static public Map<String, Object> sessionStorage = Controller.sessionStorage;
	
	// 싱글톤 패턴을 만든다.
	 private static BoardService singleTon = null;
	
	private BoardService(){};
	
	public static BoardService getInstance() {
		if(singleTon == null) {
			singleTon = new BoardService();
		}
		return singleTon;
	}

	
	public static int loginCount = 0;
	
	// Dao를 부른다
	BoardDAO boardDao = BoardDAO.getInstance();
	
	//admin
	public List<Board> adminBoardList() {
		return boardDao.adminBoardList();
	}
	
	public List<Object> boardBanSelect(int input) {
		return boardDao.boardBanSelect(input);
	}
	
	public List<Board> boardBanList() {
		return boardDao.boardBanList();
	}

	public List<Board> boardBanListSelect() {
		return boardDao.boardBanListSelect();
	}
	
	//member
	public List<Board> memberBoardList() {
		return boardDao.memberBoardList();
	}
	
	public boolean memberBoardInsert(List<Object> param) {
		if(boardDao.memberBoardInsert(param)!=0) {
			return true;
		}
		return false;
	}
	
	public Board memberShowNew(List<Object> param) {
		return boardDao.memberShowNew(param);
	}

	public List<Board> memberBoardShow(List<Object> param) {
		return boardDao.memberBoardShow(param);
	}

	public Board memberBoardDetail(int choice) {
		Board detail = boardDao.memberBoardDetail(choice);
		List<Object> views = new ArrayList<Object>();
		views.add(detail.getBod_views()+1);
		views.add(choice);
		boardDao.memberBoardViewsUp(views);
		return detail;
	}
	
	
	
}
