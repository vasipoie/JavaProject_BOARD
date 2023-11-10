package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import print.Print;
import service.BoardService;
import service.MemberService;
import util.ScanUtil;
import util.View;
import vo.Board;
import vo.Member;

public class Controller extends Print {
	// 세션
	static public Map<String, Object> sessionStorage = new HashMap<>();

	BoardService boardService = BoardService.getInstance();
	MemberService mbService = MemberService.getInstance();

	public static void main(String[] args) {
//		Controller c = new Controller();
//		c.start();
		new Controller().start();
	}

	 void start() {
		View view = View.HOME;
		while (true) {
			switch (view) {
			case HOME:
				view = home();
				break;
			case ADMIN:			//관리자
				view = admin();
				break;
			case MEMBER:		//일반회원
				view = member();
				break;			
			case MEMBER_LOGIN:	//일반회원 로그인
				view = memberLogin();
				break;
			case MEMBER_LOGIN_AFTER: //일반회원 로그인 누르고 나서 보여줄 프린트
				view = memberLoginAfter();
				break;
			case MEMBER_JOIN:	//일반회원 회원가입
				view = memberJoin();
				break;
			case ADMIN_BOARD_LIST:	//관리자용 전체 게시판 조회
				view = adminBoardList();
				break;
			case BOARD_BAN_SELECT: //불량 게시글 선택
				view = boardBanSelect();
				break;
			case BOARD_BAN_LIST:	//불량 게시글 조회
				view = boardBanList();
				break;
			case MEMBER_BOARD_LIST:	//회원용 전체 게시판 조회
				view = memberBoardList();
				break;
			case MEMBER_BOARD_INSERT://회원용 신규 게시판 등록
				view = memberBoardInsert();
				break;
//			case MEMBER_BOARD_LIST_ALL:	//회원용 전체 게시물 조회용
//				view = memberBoardListAll();
//				break;
			case MEMBER_MYBOARD_SHOW:	//내 게시글 조회
				view = memberMyBoardShow();
				break;
			case MEMBER_BOARD_DETAIL: //상세페이지 보기
				view = memberBoardDetail();
				break;
			case MEMBER_BOARD_UPDATE_BEFORE: //내 게시글 수정 전
				view = memberBoardUpdateBefore();
				break;
			case MEMBER_BOARD_UPDATE_CHECK: //내 게시글 수정 체크
				view = memberBoardUpdateCheck();
				break;
			case MEMBER_MYBOARD_UPDATE://내 게시글 수정
				view = memberMyBoardUpdate();
				break;
			case MEMBER_BOARD_UPDATE_SHOW: //수정한 내 게시글 한 개 보여주기
				view = memberBoardUpdateShow();
				break;
			}
		}
	}
	//수정한 내 게시글 한 개 보여주기
	private View memberBoardUpdateShow() {
		
		return null;
	}

	//내 게시글 수정(상세페이지 거치지 않고)
	private View memberMyBoardUpdate() {
		Member mb = (Member) sessionStorage.get("member");
		if(mb == null) {
			sessionStorage.put("view", View.MEMBER_MYBOARD_UPDATE);
			return View.MEMBER_LOGIN;
		}
		List<Object> param = new ArrayList<Object>();
		param.add(mb.getMem_no());
		List<Board> myBoard = boardService.memberBoardShow(param);
		printMemberBoardUpdateBefore(myBoard);
		int bn = ScanUtil.nextInt("수정하고 싶은 게시물의 게시번호를 선택하세요\s");
		Board selectbn = boardService.memberBoardDetail(bn);
		printMemberBoardUpdate(selectbn);
		int select = ScanUtil.nextInt("수정하고싶은 정보를 선택하세요\s");
		List<Object> updateInfo = new ArrayList<>();
		if(select == 1 || select == 3) {
			param.add(ScanUtil.nextLine("제목>> "));
		}
		if(select == 2 || select == 3) {
			param.add(ScanUtil.nextLine("내용>> "));
		}
		param.add(mb.getMem_id());
		mbService.memberBoardUpdate(updateInfo, select);
		return View.MEMBER_BOARD_UPDATE_SHOW;
	}
	
	//내 게시글 수정 전
	private View memberBoardUpdateBefore() {
		Member mb = (Member) sessionStorage.get("member");
		if(mb == null) {
			sessionStorage.put("view", View.MEMBER_BOARD_UPDATE_BEFORE);
			return View.MEMBER_LOGIN;
		}
		List<Object> param = new ArrayList<Object>();
		param.add(mb.getMem_no());
		List<Board> myBoard = boardService.memberBoardShow(param);
		printMemberBoardUpdateBefore(myBoard);
		int select = ScanUtil.nextInt("수정하고 싶은 게시물의 게시번호를 선택하세요\s");
		
		return null;
	}

	//내 게시글 수정 체크
	private View memberBoardUpdateCheck() {
		String selyn = ScanUtil.nextLine("수정하시겠습니까?(y/n)");
		if(selyn.equalsIgnoreCase("y")) {
			return View.MEMBER_MYBOARD_UPDATE;
		}else {
			return View.MEMBER_BOARD_DETAIL;
		}
	}

	//상세페이지 보기
	private View memberBoardDetail() {
		int choice = ScanUtil.nextInt("상세페이지를 보고싶은 게시번호를 입력하세요\s");
		Board detailOne = boardService.memberBoardDetail(choice);
		printMemberBoardDetail(detailOne);
		int select = ScanUtil.nextInt("메뉴를 선택하세요\s");
		switch (select) {
		case 1:
			return View.MEMBER_MYBOARD_UPDATE;//상세페이지에서 지금 보고있는 게시물 수정으로 들어갔을때로 바꾸기
		case 2:
			return View.MEMBER_MYBOARD_SHOW;
		case 3:
			return View.MEMBER_LOGIN_AFTER;
		default :
			return View.MEMBER_BOARD_DETAIL;
		}
	}
	
	//내 게시글 조회
	 private View memberMyBoardShow() {
		Member mb = (Member) sessionStorage.get("member");
		if(mb == null) {
			sessionStorage.put("view", View.MEMBER_MYBOARD_SHOW);
			return View.MEMBER_LOGIN;
		}
		List<Object> param = new ArrayList<Object>();
		param.add(mb.getMem_no());
		List<Board> myBoard = boardService.memberBoardShow(param);
		printMemberBoardShow(myBoard);
		int select = ScanUtil.nextInt("메뉴를 선택하세요\s");
		switch (select) {
		case 1:
			return View.MEMBER_BOARD_DETAIL;
		case 2:
			return View.MEMBER_BOARD_INSERT;
		case 3:
			return View.MEMBER_MYBOARD_UPDATE;
		case 4:
			return View.MEMBER_LOGIN_AFTER;
		default :
			return View.MEMBER_MYBOARD_SHOW;
		}
	 }

//	//회원용 신규 게시판 등록 후 보여줄 전체 게시물 목록	
//	 private View memberBoardListAll() {
//		List<Board> mbList = boardService.memberBoardList();
//		printMemberBoardListAll(mbList);
//		int select = ScanUtil.nextInt("메뉴를 선택하세요\s");
//		switch (select) {
//		case 1:
//			return View.BOARD_BAN_SELECT;
//		case 2:
//			return View.BOARD_BAN_LIST;
//		case 3:
//			return View.ADMIN;
//		default :
//			return View.ADMIN_BOARD_LIST;
//		}
//	 }

	//회원용 신규 게시판 등록
	private View memberBoardInsert() {
		Member mb = (Member) sessionStorage.get("member");
		if(mb == null) {
			sessionStorage.put("view", View.MEMBER_BOARD_INSERT);
			return View.MEMBER_LOGIN;
		}
		List<Object> param = new ArrayList<Object>();
		param.add(ScanUtil.nextLine("제목>>> "));
		param.add(ScanUtil.nextLine("내용>>> "));
		param.add(mb.getMem_no()); //bod_mem_no에 넣을 member테이블의 mem_no
		boolean chk = boardService.memberBoardInsert(param);
		Board showNew = boardService.memberShowNew(param);	//새로 등록한 게시글 한 개
		sessionStorage.put("showNew", showNew);
		if(chk) {
			printVar();
			System.out.println("아래 내용의 새로운 게시글이 등록되었습니다");
			System.out.println(sessionStorage.get("showNew"));
			printVar();
			System.out.println("---------회원님이 작성한 게시글 목록----------");
			return View.MEMBER_MYBOARD_SHOW;
		}else {
			System.out.println("게시글 등록에 실패했습니다. 다시 등록해주세요");
			return View.MEMBER_BOARD_INSERT;
		}
	}

	//회원용 전체 게시판 조회
	private View memberBoardList() {
		Member mb = (Member) sessionStorage.get("member");
		if(mb == null) {
			sessionStorage.put("view", View.MEMBER_BOARD_LIST);
			return View.MEMBER_LOGIN;
		}
		List<Board> mbList = boardService.memberBoardList();
		printMemberBoardList(mbList);
		int select = ScanUtil.nextInt("메뉴를 선택하세요\s");
		switch (select) {
		case 1:
			return View.HOME; //게시판 검색
		case 2:
			return View.MEMBER_LOGIN_AFTER; //뒤로가기
		default :
			return View.MEMBER_BOARD_LIST;
		}
	}
	
	//로그인 후에 나오는 목록
	private View memberLoginAfter() {
		printMemberLoginAfter();
		int select = ScanUtil.nextInt("메뉴를 선택하세요\s");
		switch (select) {
		case 1:
			return View.MEMBER_BOARD_LIST;
		case 2:
			return View.MEMBER_BOARD_INSERT;
		case 3:
			return View.MEMBER_MYBOARD_SHOW;
		case 4:
			return View.MEMBER_MYBOARD_UPDATE;
		case 5 :
			return View.HOME;
		default :
			return View.MEMBER_LOGIN_AFTER;
		}
	}
	
	//일반회원 로그인
	private View memberLogin() {
		List<Object> param = new ArrayList<Object>();
		System.out.println("----- 로그인 -------- ");
		param.add(ScanUtil.nextLine("id>>"));
		param.add(ScanUtil.nextLine("pass>>"));
		Member mb = mbService.memberLogin(param);
		if(mb == null) {
			System.out.println("회원 정보가 없습니다.");
			String selyn = ScanUtil.nextLine("회원가입 하시겠습니까?(y/n)");
			if(selyn.equalsIgnoreCase("y")) {
				return View.MEMBER_JOIN;
			}else {
				return View.MEMBER;
			}
		}
		sessionStorage.put("member",mb);
		View v = (View) sessionStorage.get("view");
		if(v==null) {
			return View.MEMBER_LOGIN_AFTER;
		}
		return v;
	}
	
	//일반회원 회원가입
	private View memberJoin() {
		List<Object> param = new ArrayList<Object>();
		param.add(ScanUtil.nextLine("id>>"));
		param.add(ScanUtil.nextLine("pass>>"));
		param.add(ScanUtil.nextLine("name>>"));
		param.add(ScanUtil.nextLine("age>>"));
		boolean chk = mbService.memberJoin(param);
		if(chk) {
//			Member ad = (Member) sessionStorage.get("member");
			System.out.println("가입을 환영합니다.");
			return View.MEMBER;
		}else {
			System.out.println("회원 가입에 실패하였습니다.");
			return View.MEMBER_JOIN;
		}
	}
	
	private View member() {
		printMember();
		int select = ScanUtil.nextInt("메뉴를 선택하세요\s");
		switch (select) {
		case 1:
			return View.MEMBER_LOGIN;
		case 2:
			return View.MEMBER_BOARD_LIST;
		case 3:
			return View.MEMBER_BOARD_INSERT;
		case 4:
			return View.MEMBER_MYBOARD_SHOW;
		case 5 :
			return View.MEMBER_MYBOARD_UPDATE;
		case 6:
			return View.HOME;
		default :
			return View.MEMBER;
		}
	}

	//불량 게시글 조회
	private View boardBanList() {
		List<Board> list = boardService.boardBanList();
		printBoardBanList(list);
		int select = ScanUtil.nextInt("메뉴를 선택하세요\s");
		switch (select) {
		case 1:
			return View.ADMIN_BOARD_LIST;
		case 2:
			return View.BOARD_BAN_SELECT;
		case 3:
			return View.ADMIN;
		default :
			return View.BOARD_BAN_LIST;
		}
	}
	
	//불량 게시글 선택
	private View boardBanSelect() {
		List<Board> boardList = boardService.boardBanListSelect();
		boardBanListSelect(boardList);
		List<Board> list = boardService.adminBoardList();
		int input = ScanUtil.nextInt("선택할 불량 게시글의 게시번호를 입력하세요\s");
		List<Object> banSelect = boardService.boardBanSelect(input);
		printVar();
		System.out.println("선택한 "+input+"번의 게시글이 불량 게시글로 변경되었습니다");
		return View.ADMIN_BOARD_LIST;
	}

	//관리자용 전체 게시판 조회
	private View adminBoardList() {
		List<Board> list = boardService.adminBoardList();
		printAdminBoardList(list);
		int select = ScanUtil.nextInt("메뉴를 선택하세요\s");
		switch (select) {
		case 1:
			return View.BOARD_BAN_SELECT;
		case 2:
			return View.BOARD_BAN_LIST;
		case 3:
			return View.ADMIN;
		default :
			return View.ADMIN_BOARD_LIST;
		}
	}

	private View admin() {
		printAdmin();
		int select = ScanUtil.nextInt("메뉴를 선택하세요\s");
		switch (select) {
		case 1:
			return View.ADMIN_LOGIN;
		case 2:
			return View.ADMIN_BOARD_LIST;
		case 3:
			return View.BOARD_BAN_LIST;
		case 4:
			return View.HOME;
		default :
			return View.ADMIN;
		}
	}

	 View home() {
		printHome();
		int select = ScanUtil.nextInt("메뉴를 선택하세요.");
		switch (select) {
		case 1:
			return View.ADMIN;
		case 2:
			return View.MEMBER;
		default :
			return View.HOME;
		}
	}
}
