package com.sbs.example.practice.dto;

public class Member {
	
	public int id;
	public String userId;
	public String passwd;
	public String name;
	public String regDate;
	
	public Member(int id, String userId, String passwd, String name, String regDate) {
		this.id=id;
		this.userId=userId;
		this.passwd=passwd;
		this.name=name;
		this.regDate=regDate;
	}
}
