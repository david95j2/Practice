package com.sbs.example.practice.service;

import java.util.List;

import com.sbs.example.practice.container.Container;
import com.sbs.example.practice.dao.ArticleDao;
import com.sbs.example.practice.dto.Article;
import com.sbs.example.practice.dto.Board;

public class ArticleService {
	private ArticleDao articleDao;
	public ArticleService() {
		articleDao=Container.articleDao;
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}

	public void modify(String title, String body, int id) {
		articleDao.modify(title,body,id);
	}

	public void delete(int id) {
		articleDao.remove(id);
	}

	public int write(int boardId, int memberId, String title, String body) {
		return articleDao.add(boardId,memberId,title,body);
	}

	public int makeBoard(String name) {
		return articleDao.makeBoard(name);
	}

	public Board getBoard(int id) {
		return articleDao.getBoard(id);
	}
}
