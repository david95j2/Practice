package com.sbs.example.practice.dto;

public class Board {
	public int id;
	public String name;
	public String regDate;
	public String updateDate;

	
	public Board(int boardId, String name) {
		this.id=boardId;
		this.name=name;
	}

}
