package com.sbs.example.practice.controller;

import com.sbs.example.practice.container.Container;
import com.sbs.example.practice.dto.Member;
import com.sbs.example.practice.service.MemberService;

public class MemberController extends Controller {
	private MemberService memberService;
	public MemberController() {
		memberService=Container.memberService;
	}

	public void run(String cmd) {
		if (cmd.equals("member join")) {
			doJoin();
		} else if (cmd.equals("member login")) {
			doLogin();
		} else if (cmd.equals("member whoami")) {
			showMember();
		} else if (cmd.equals("member logout")) {
			doLogout();
		}
	}

	private void doLogout() {
		if(Container.session.logout()) {
			System.out.println("이미 로그아웃 상태입니다.");
			return;
		}
		int id = Container.session.loginedMemberId;
		Member member = memberService.getMember(id);
		System.out.printf("[%s님] : 로그아웃되었습니다.\n",member.name);
		Container.session.loginedMemberId=0;
	}

	private void showMember() {
		if(Container.session.logout()) {
			System.out.println("로그인 후 이용가능");
			return;
		}
		int id = Container.session.loginedMemberId;
		Member member = memberService.getMember(id);
		System.out.println("== 회원정보 ==");
		System.out.printf("번호 : %d\n",member.id);
		System.out.printf("아이디 : %s\n",member.userId);
		System.out.printf("이름 : %s\n",member.name);
		System.out.printf("가입날짜 : %s\n",member.regDate);
	}

	private void doLogin() {
		if(Container.session.login()) {
			System.out.println("로그인 상태입니다.");
			return;
		}
		System.out.println("== 로그인 ==");
		String userId = "";
		String passwd;
		int loginCount = 0;
		int loginMaxCount = 3;
		boolean isFineId = false;
		Member member = null;
		while (true) {
			if (loginCount >= loginMaxCount) {
				System.out.println("로그인 취소");
				break;
			}
			System.out.printf("Id : ");
			userId = Container.sc.nextLine().trim();
			if (userId.length() == 0) {
				System.out.println("아이디를 입력하세요.");
				loginCount++;
				continue;
			}
			member = memberService.isIdExists(userId);
			if(member==null) {
				System.out.println("아이디가 존재하지 않습니다.");
				loginCount++;
				continue;
			}
			isFineId = true;
			break;
		}
		if (isFineId == false) {
			return;
		}
		int loginPwCount = 0;
		int loginPwMaxCount = 3;
		boolean isFinePw = false;
		while (true) {
			if(loginPwCount>=loginPwMaxCount) {
				System.out.println("로그인 취소");
				break;
			}
			System.out.printf("Pw : ");
			passwd = Container.sc.nextLine().trim();
			if (passwd.length() == 0) {
				System.out.println("비밀번호를 입력하세요.");
				continue;
			}
			if(member.passwd.equals(passwd)==false) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				loginPwCount++;
				continue;
			}
			isFinePw=true;
			break;
		}
		if(isFinePw==false) {
			return;
		}
		System.out.printf("[%s님] : 로그인되었습니다.\n",member.name);
		Container.session.loginedMemberId=member.id;
	}

	private void doJoin() {
		System.out.println("== 회원가입 ==");
		String userId = "";
		String passwd;
		String name;
		int loginCount = 0;
		int loginMaxCount = 3;
		boolean isFineId = false;
		while (true) {
			if (loginCount >= loginMaxCount) {
				System.out.println("가입취소");
				break;
			}
			System.out.printf("Id : ");
			userId = Container.sc.nextLine().trim();
			if (userId.length() == 0) {
				System.out.println("아이디를 입력하세요.");
				loginCount++;
				continue;
			}
			if(memberService.beDuplicate(userId)==true) {
				System.out.println("이미 사용중인 아이디");
				loginCount++;
				continue;
			}
			isFineId = true;
			break;
		}
		if (isFineId == false) {
			return;
		}
		while (true) {
			System.out.printf("Pw : ");
			passwd = Container.sc.nextLine().trim();
			if (passwd.length() == 0) {
				System.out.println("비밀번호를 입력하세요.");
				continue;
			}
			break;
		}
		while (true) {
			System.out.printf("name : ");
			name = Container.sc.nextLine().trim();
			if (name.length() == 0) {
				System.out.println("이름을 입력하세요.");
				continue;
			}
			break;
		}
		memberService.join(userId, passwd, name);
		System.out.printf("[%s님] : 가입되었습니다.\n",name);
	}
}