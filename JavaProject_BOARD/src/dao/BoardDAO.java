package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ConvertUtils;
import util.JDBCUtil;
import vo.Board;

// 데이터베이스로 쿼리를 날려서 결과를 얻는다.
public class BoardDAO {
	static public Map<String, Object> sessionStorage = Controller.sessionStorage;
	
	// 싱글톤 패턴을 만든다.
	 private static BoardDAO singleTon = null;
	
	private BoardDAO(){};
	
	public static BoardDAO getInstance() {
		if(singleTon == null) {
			singleTon = new BoardDAO();
		}
		return singleTon;
	}
	
	// JDBC를 부른다.
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	
	public Board boardView(int board_no) {
		String sql = "select board_no, board_name, board_writer, "
				+    "TO_CHAR(BOARD_DATE,'YYYY-MM-DD') BOARD_DATE, board_content "
				+    "from free_board "
				+    "where board_no = ?";
		List l = new ArrayList<>();
		l.add(board_no);
		Map<String, Object>	map = jdbc.selectOne(sql, l);
		return ConvertUtils.convertToVo(map, Board.class);
	}
	public Object boardInsert(List<Object>param) {
		String sql = "insert into free_board"
				+     "(board_no, board_name, board_writer, board_content)\r\n"
				+     "values ((select max(board_no) from free_board)+1, "
				+     "?, ?, ?)";
		jdbc.update(sql, param);
		return null;
	}
	public void boardDelete(int board_no) {
		String sql = "update free_board\r\n" + 
					 "set board_delyn = 'y'\r\n" + 
					 "where board_no = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(board_no);
		jdbc.update(sql,param);
	}
	public void boardUpdate(List<Object> param) {
		String sql = "update free_board\r\n" + 
					  "set board_name = ?,\r\n" + 
					  "board_writer = ?,\r\n" + 
					  "board_content = ?\r\n" + 
					  "where board_no = ?";
		jdbc.update(sql,param);
	}
	
	
	//admin
	public List<Board> adminBoardList() {
		String sql = "select bod_no, bod_title, bod_content, \r\n" + 
							"to_char(bod_date) bod_date,\r\n" + 
							"bod_views, bod_mem_no, bod_badyn\r\n" + 
					"from board";
		List<Map<String, Object>> list = jdbc.selectList(sql);
		return ConvertUtils.convertToList(list, Board.class);
	}
	
	public List<Object> boardBanSelect(int input) {
		String sql = "update board\r\n" + 
					"set bod_badyn = 'y'\r\n" + 
					"where bod_no ="+input;
		jdbc.update(sql);
		return null;
	}

	public List<Board> boardBanList() {
		String sql = "select bod_no, bod_title, bod_content, \r\n" + 
							"to_char(bod_date) bod_date,\r\n" + 
							"bod_views, bod_mem_no, bod_badyn\r\n" + 
					"from board\r\n" + 
					"where bod_badyn = 'y'";
		List<Map<String, Object>> list = jdbc.selectList(sql);
		return ConvertUtils.convertToList(list, Board.class);
	}

	public List<Board> boardBanListSelect() {
		String sql = "select bod_no, bod_title, bod_content, \r\n" + 
							"to_char(bod_date) bod_date,\r\n" + 
							"bod_views, bod_mem_no, bod_badyn\r\n" + 
					"from board";
		List<Map<String, Object>> list = jdbc.selectList(sql);
		return ConvertUtils.convertToList(list, Board.class);
	}
	
	//member
	public List<Board> memberBoardList() {
		String sql = "select bod_no, bod_title, bod_content, \r\n" + 
							"to_char(bod_date) bod_date,\r\n" + 
							"bod_views, bod_mem_no\r\n" + 
					"from board\r\n" +
					"where bod_badyn is null";
		List<Map<String, Object>> mbList = jdbc.selectList(sql);
		return ConvertUtils.convertToList(mbList, Board.class);
	}
	
	public int memberBoardInsert(List<Object> param) {
		String sql = "INSERT INTO board(bod_no, bod_title \r\n" + 
								",bod_content, bod_views, bod_mem_no)\r\n" + 
					"VALUES ((select max(bod_no)+1 bod_no from board)\r\n" + 
								", ?, ?, 1, ?)";
		return jdbc.update(sql,param);
	}

	public Board memberShowNew(List<Object> param) {
		String sql = "select bod_no, bod_title, bod_content\r\n" + 
							",to_char(bod_date) bod_date\r\n" + 
							",bod_views, bod_mem_no\r\n" + 
					"from board\r\n" + 
					"where bod_title = ?\r\n" + 
					"and bod_content = ?\r\n" +
					"and bod_mem_no = ?";
		Map map = jdbc.selectOne(sql, param);
		if(map == null) return null;
		return ConvertUtils.convertToVo(map, Board.class);
	}

	public List<Board> memberBoardShow(List<Object> param) {
		String sql = "select b.bod_no, b.bod_title, b.bod_content"+
							", to_char(b.bod_date) \"b.bod_date\", b.bod_views\r\n" + 
					"from board b, member m\r\n" + 
					"where b.bod_mem_no = m.mem_no\r\n"+
					"and mem_no=?";
		List<Map<String, Object>> list = jdbc.selectList(sql, param);
		return ConvertUtils.convertToList(list, Board.class);
	}

	public Board memberBoardDetail(int choice) {
		String sql = "select bod_no, bod_title, bod_content,\r\n" + 
							"to_char(bod_date) bod_date, (bod_views+1) bod_views\r\n" + 
					"from board\r\n" + 
					"where bod_no = "+choice;
		Map map = jdbc.selectOne(sql);
		return ConvertUtils.convertToVo(map, Board.class);
	}

	public void memberBoardViewsUp(List<Object> views) {
		String sql = "UPDATE board SET bod_views = ?\r\n" + 
					"where bod_no = ?";
		jdbc.update(sql, views);
	}

	
	

	
}
