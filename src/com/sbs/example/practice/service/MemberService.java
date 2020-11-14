package com.sbs.example.practice.service;

import com.sbs.example.practice.container.Container;
import com.sbs.example.practice.dao.MemberDao;
import com.sbs.example.practice.dto.Member;

public class MemberService {
	private MemberDao memberDao;
	public MemberService() {
		memberDao=Container.memberDao;
	}

	public void join(String userId, String passwd, String name) {
		memberDao.add(userId,passwd,name);
	}

	public boolean beDuplicate(String userId) {
		return memberDao.examine(userId);
	}

	public Member isIdExists(String userId) {
		return memberDao.search(userId);
	}

	public Member getMember(int id) {
		return memberDao.getMember(id);
	}
}
