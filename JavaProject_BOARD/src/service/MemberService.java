package service;

import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.MemberDao;
import vo.Member;

public class MemberService {
	static public Map<String, Object> sessionStorage = Controller.sessionStorage;
	
	private static MemberService singleTon = null;
	
	private MemberService(){};
	
	public static MemberService getInstance() {
		if(singleTon == null) {
			singleTon = new MemberService();
		}
		return singleTon;
	}

	MemberDao mbDao = MemberDao.getInstance();

	public Member memberLogin(List<Object> param) {
		return mbDao.memberLogin(param);
	}

	public boolean memberJoin(List<Object> param) {
		if(mbDao.memberJoin(param) !=0) {
			return true;
		}
		return false;
	}

	public void memberBoardUpdate(List<Object> updateInfo, int select) {
		mbDao.memberBoardUpdate(updateInfo, select);
	}
}
