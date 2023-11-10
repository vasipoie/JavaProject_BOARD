package print;

import java.util.List;

import vo.Board;

public class Print {
	

	public void printVar() {
		System.out.println("----------------------------------");
	}
	
	public void printHome() {
		printVar();
		System.out.println("1. 관리자");
		System.out.println("2. 일반회원");
		printVar();
	}
	
	public void printMember() {
		printVar();	
		System.out.println("1. 일반회원 로그인");
		System.out.println("2. 전체 게시판 조회");//게시판 검색 기능, 제목,내용,시간
		System.out.println("3. 신규 게시판 등록");//게시판 등록 완료 후 내 게시글 조회로 이동
		System.out.println("4. 내 게시글 조회");
		System.out.println("5. 내 게시글 수정");
		System.out.println("6. 홈");
		printVar();
	}
	
	public void printAdmin() {
		printVar();
		System.out.println("1. 관리자 로그인");
		System.out.println("2. 전체 게시판 조회");//2.전체 게시판 조회에서 광고글 등을 선택해 불량 게시글 처리
		System.out.println("3. 불량 게시글 조회");
		System.out.println("4. 홈");
		printVar();
	}
	
	public void printAdminBoardList(List<Board> list) {
		// 게시판 내용 출력
		printVar();
		System.out.println("게시번호\t제목\t내용\t작성일\t조회수\t회원번호\t불량게시글");
		for (Board board : list) {
			System.out.println(board);
//			System.out.println(freeBoard.getBoard_no()+"\t"+freeBoard.getBoard_name()+"\t"+freeBoard.getBoard_writer()+"\t"+freeBoard.getBoard_date()+"\t"+freeBoard.getBoard_content());
		}
		printVar();
		System.out.println("1. 불량 게시글 선택");
		System.out.println("2. 불량 게시글 조회");
		System.out.println("3. 홈");
		printVar();
	}
	
	public void boardBanListSelect(List<Board> list) {
		printVar();
		System.out.println("게시번호\t제목\t내용\t작성일\t조회수\t회원번호\t불량게시글");
		for (Board board : list) {
			System.out.println(board);
		}
		printVar();
	}
	
	public void printBoardBanList(List<Board> list) {
		printVar();
		System.out.println("게시번호\t제목\t내용\t작성일\t조회수\t회원번호\t불량게시글");
		for(Board banlist : list) {
			System.out.println(banlist);
		}
		printVar();
		System.out.println("1. 전체 게시판 조회");
		System.out.println("2. 불량 게시글 선택");
		System.out.println("3. 홈");
		printVar();
	}
	
	//member
	//일반회원 로그인 후 선택화면
	public void printMemberLoginAfter() {
		printVar();
		System.out.println("1. 전체 게시판 조회");
		System.out.println("2. 신규 게시판 등록");
		System.out.println("3. 내 게시글 조회");
		System.out.println("4. 내 게시글 수정");
		System.out.println("5. 홈");
		printVar();
	}
	
	//회원용 전체 게시판 조회
	public void printMemberBoardList(List<Board> mbList) {
		printVar();
		System.out.println("게시번호\t제목\t내용\t작성일\t조회수\t회원번호");
		for(Board mblist : mbList) {
			System.out.println(mblist);
		}
		printVar();
		System.out.println("1. 게시판 검색");
		System.out.println("2. 홈");
		printVar();
	}
	
	public void printNewBoardInsert(Board showNew) {
		printVar();
		System.out.println("게시번호\t제목\t내용\t작성일\t조회수\t회원번호");
		System.out.println(showNew);
		printVar();
	}
	
//	public void printMemberBoardListAll(List<Board> mbList) {
//		printVar();
//		System.out.println("게시번호\t제목\t내용\t작성일\t조회수\t회원번호");
//		for(Board mblist : mbList) {
//			System.out.println(mblist);
//		}
//		printVar();
//		System.out.println("1. ");
//	}
	
	//내 게시글 조회
	public void printMemberBoardShow(List<Board> myBoard) {
		printVar();
		for(Board myBoardList : myBoard) {
			System.out.println(myBoardList);
		}
		printVar();
		System.out.println("1. 상세페이지 보기");
		System.out.println("2. 신규 게시판 등록");
		System.out.println("3. 내 게시글 수정");
		System.out.println("4. 홈");
		printVar();
	}
	
	public void printMemberBoardDetail(Board detailOne) {
		printVar();
		System.out.println("게시번호\t제목\t내용\t작성일\t조회수");
		System.out.println(detailOne);
		printVar();
		System.out.println("1. 지금 게시글 수정");//만들어야됨
		System.out.println("2. 뒤로가기");
		System.out.println("3. 홈");
		printVar();
	}
	
	public void printMemberBoardUpdateBefore(List<Board> myBoard) {
		printVar();
		for(Board myBoardList : myBoard) {
			System.out.println(myBoardList);
		}
		printVar();
	}
	
	public void printMemberBoardUpdate(Board selectbn) {
		printVar();
		System.out.println("게시번호\t제목\t내용\t작성일\t조회수");
		System.out.println(selectbn);
		printVar();
		System.out.println("1. 제목 ");
		System.out.println("2. 내용");
		System.out.println("3. 전체");
		printVar();
	}
	
}
