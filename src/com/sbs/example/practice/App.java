package com.sbs.example.practice;

import com.sbs.example.practice.container.Container;
import com.sbs.example.practice.controller.ArticleController;
import com.sbs.example.practice.controller.Controller;
import com.sbs.example.practice.controller.MemberController;

public class App {
	private MemberController memberController;
	private ArticleController articleController;
	public App() {
		memberController=Container.memberController;
		articleController=Container.articleController;
	}

	public void run() {
		while(true) {
			System.out.printf("명령어 : ");
			String cmd = Container.sc.nextLine();
			
			if(cmd.equals("system exit")) {
				System.out.println("종료");
				return;
			}
			Controller controller=whatKindOfController(cmd);
			try {
				controller.run(cmd);
			}
			catch (NullPointerException e) {
				System.out.println("알 수 없는 명령어");
				return;
			}
		}
	}

	private Controller whatKindOfController(String cmd) {
		if(cmd.startsWith("member ")) {
			return memberController;
		}
		else if (cmd.startsWith("article ")) {
			return articleController;
		}
		return null;
	}
	
}
