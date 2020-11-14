package com.sbs.example.practice.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.example.practice.container.Container;
import com.sbs.example.practice.dto.Article;
import com.sbs.example.practice.dto.Board;
import com.sbs.example.practice.dto.Member;
import com.sbs.example.practice.service.ArticleService;
import com.sbs.example.practice.service.MemberService;

public class ArticleController extends Controller {
	private Scanner sc;
	private ArticleService articleService;
	private MemberService memberService;
	public ArticleController() {
		sc = Container.sc;
		articleService = Container.articleService;
		memberService = Container.memberService;
	}

	public void run(String cmd) {
		if(cmd.equals("article list")) {
			showList();
		} else if (cmd.startsWith("article detail ")) {
			showDetail(cmd);
		} else if (cmd.startsWith("article modify ")) {
			doModify(cmd);
		} else if (cmd.startsWith("article delete ")) {
			doDelete(cmd);
		} else if (cmd.equals("article add")) {
			doWrite();
		} else if (cmd.equals("article makeBoard")) {
			doMakeBoard();
		} else if (cmd.startsWith("article selectBoard ")) {
			doSelect(cmd);
		}
	}

	private void doSelect(String cmd) {
		int inputedId = 1;
		try {
		inputedId = Integer.parseInt(cmd.split(" ")[2]);
		}
		catch (NumberFormatException e) {
			System.out.println("[오류메세지] : 게시판 번호는 숫자로만 입력");
			return;
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("[오류메세지] : 게시판 번호 입력");
			return;
		}
		System.out.println("== 게시판 선택 모드 ==");
		Board board = articleService.getBoard(inputedId);
		if(board == null) {
			System.out.println("해당 게시판이 존재하지 않습니다.");
			return;
		}
		System.out.printf("%s 게시판 모드가 선택되었습니다.\n",board.name);
		Container.session.selectedBoardId=board.id;
	}

	private void doMakeBoard() {
		System.out.println("== 게시판 생성 ==");
		System.out.printf("게시판 이름 : ");
		String boardName = Container.sc.nextLine();
		int id = articleService.makeBoard(boardName);
		System.out.printf("%s(%d번) 게시판이 생성되었습니다.\n",boardName,id);
		Container.session.selectedBoardId=id;
	}

	private void doWrite() {
		if(Container.session.logout()) {
			System.out.println("로그인 후 이용가능");
			return;
		}
		int id = Container.session.selectedBoardId;
		Board board = articleService.getBoard(id);
		System.out.printf("== %s 게시판 글 작성 ==\n",board.name);
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();
		
		int boardId = Container.session.selectedBoardId;
		int memberId = Container.session.loginedMemberId;
		int num=articleService.write(boardId,memberId,title,body);
		System.out.printf("%d번 게시물이 등록되었습니다.\n",num);
	}

	private void doDelete(String cmd) {
		int inputedId = 1;
		try {
		inputedId = Integer.parseInt(cmd.split(" ")[2]);
		}
		catch (NumberFormatException e) {
			System.out.println("[오류메세지] : 게시물 번호는 숫자로만 입력");
			return;
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("[오류메세지] : 게시물 번호 입력");
			return;
		}
		int id = Container.session.loginedMemberId;
		Article article = articleService.getArticle(inputedId);
		if(article== null) {
			System.out.println("게시물 없음");
			return;
		}
		else if(article.memberId != id) {
			System.out.println("다른 사용자의 게시물입니다.");
			return;
		}
		System.out.println("== 게시물 삭제 ==");
		articleService.delete(inputedId);
		System.out.printf("%d번 게시물이 삭제되었습니다.\n",article.id);
	}

	private void doModify(String cmd) {
		if(Container.session.logout()) {
			System.out.println("로그인 후 이용가능");
			return;
		}
		int inputedId = 1;
		try {
		inputedId = Integer.parseInt(cmd.split(" ")[2]);
		}
		catch (NumberFormatException e) {
			System.out.println("[오류메세지] : 게시물 번호는 숫자로만 입력");
			return;
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("[오류메세지] : 게시물 번호 입력");
			return;
		}
		int id = Container.session.loginedMemberId;
		Article article = articleService.getArticle(inputedId);
		if(article== null) {
			System.out.println("게시물 없음");
			return;
		}
		else if(article.memberId != id) {
			System.out.println("다른 사용자의 게시물입니다.");
			return;
		}
		System.out.println("== 게시물 수정 ==");
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();
		articleService.modify(title,body,inputedId);
		System.out.printf("%d번 게시물이 수정되었습니다.\n",article.id);
	}

	private void showDetail(String cmd) {
		int inputedId = 1;
		try {
		inputedId = Integer.parseInt(cmd.split(" ")[2]);
		}
		catch (NumberFormatException e) {
			System.out.println("[오류메세지] : 게시물 번호는 숫자로만 입력");
			return;
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("[오류메세지] : 게시물 번호 입력");
			return;
		}
		System.out.println("== 게시물 상세 ==");
		Article article = articleService.getArticle(inputedId);
		if(article== null) {
			System.out.println("게시물 없음");
			return;
		}
		Member member = memberService.getMember(article.memberId);
		System.out.printf("번호 : %d\n",article.id);
		System.out.printf("등록일 : %s\n",article.regDate);
		System.out.printf("수정일 : %s\n",article.updateDate);
		System.out.printf("작성자 : %s\n",member.name);
		System.out.printf("제목 : %s\n",article.title);
		System.out.printf("내용 : %s\n",article.body);
	}

	private void showList() {
		System.out.println("== 게시물 리스트 ==");
		System.out.println("번호 / 등록일 / 수정일 / 작성자 / 제목");
		List<Article> articles = articleService.getArticles();
		
		for (Article article : articles) {
			Member member = memberService.getMember(article.memberId);
			System.out.printf("%d / %s / %s / %s / %s\n",article.id,article.regDate,article.updateDate,member.name,article.title);
		}
	}
}
