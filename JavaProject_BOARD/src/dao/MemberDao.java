package dao;

import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ConvertUtils;
import util.JDBCUtil;
import vo.Member;

public class MemberDao {
	static public Map<String, Object> sessionStorage = Controller.sessionStorage;

	private static MemberDao singleTon = null;
	
	private MemberDao(){};
	
	public static MemberDao getInstance() {
		if(singleTon == null) {
			singleTon = new MemberDao();
		}
		return singleTon;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();

	public Member memberLogin(List<Object> param) {
		String sql = "select * \r\n" + 
				"from member \r\n" + 
				"where mem_id=?\r\n" + 
				"and mem_pw=?\r\n" + 
				"and delyn is null";
		Map map = jdbc.selectOne(sql, param);
		if(map == null) return null;
		return ConvertUtils.convertToVo(map, Member.class);
	}

	public int memberJoin(List<Object> param) {
		String sql = "insert into member(mem_no, mem_id, mem_pw, mem_name, mem_old)"
				+ "values ((select max(mem_no)+1 from member), ?,?,?,?)";
		return jdbc.update(sql, param);
	}

	public void memberBoardUpdate(List<Object> updateInfo, int select) {
		String sql_front = "update board set";
		if(select == 1 || select == 3) {
			sql_front += " bod_title = ? ";
		}
		if(select == 2 || select == 3) {
			if(select == 5) sql_front +=",";
			sql_front += " bod_content = ? ";
		}
		String sql_where = " where bod_mem_no=?";
		String sql = sql_front + sql_where;
		System.out.println(sql);
		jdbc.update(sql, updateInfo);
	}
	
	
	
	
	
}
