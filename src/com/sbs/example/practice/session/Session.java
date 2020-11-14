package com.sbs.example.practice.session;

public class Session {
	public int loginedMemberId;
	public int selectedBoardId;
	
	public Session() {
		loginedMemberId=0;
	}
	public boolean login() {
		return loginedMemberId !=0;
	}
	public boolean logout() {
		return !login();
	}
}
