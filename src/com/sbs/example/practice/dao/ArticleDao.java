package com.sbs.example.practice.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sbs.example.practice.dto.Article;
import com.sbs.example.practice.dto.Board;

public class ArticleDao {
	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // 드라이버
	private final String DB_URL = "jdbc:mysql://localhost:3306/testData?&serverTimezone=Asia/Seoul"; // 접속할 DB 서버

	private final String USER_NAME = "sbsst"; // DB에 접속할 사용자 이름을 상수로 정의
	private final String PASSWORD = "sbs123414"; // 사용자의 비밀번호를 상수로 정의

	public ArticleDao() {

	}

	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();
		Connection conn = null;
		Statement state = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			state = conn.createStatement();

			String sql; // SQL문을 저장할 String
			sql = "SELECT * FROM article";
			ResultSet rs = state.executeQuery(sql); // SQL문을 전달하여 실행

			while (rs.next()) {
				Article article = new Article();
				article.id = rs.getInt("id");
				article.regDate = rs.getString("regDate");
				article.updateDate = rs.getString("updateDate");
				article.title = rs.getString("title");
				article.body = rs.getString("body");
				article.memberId = rs.getInt("memberId");
				article.boardId = rs.getInt("boardId");
				articles.add(article);
			}

			rs.close();
			state.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Error" + e);
		} catch (SQLException e) {
			System.out.println("Error" + e);
		} finally { // 예외가 있든 없든 무조건 실행
			try {
				if (state != null)
					state.close();
			} catch (SQLException ex1) {
				//
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex1) {
				//
			}
		}
		Collections.reverse(articles);
		return articles;
	}

	public Article getArticle(int id) {
		Article article = null;
		Connection conn = null;
		Statement state = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			state = conn.createStatement();

			String sql = "SELECT * FROM article WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery(); // SQL문을 전달하여 실행
			while (rs.next()) {
				int articleId = rs.getInt("id");
				String regDate = rs.getString("regDate");
				String updateDate = rs.getString("updateDate");
				String title = rs.getString("title");
				String body = rs.getString("body");
				int memberId = rs.getInt("memberId");
				int boardId = rs.getInt("boardId");
				article = new Article(articleId, regDate, updateDate, title, body, memberId, boardId);
			}

			rs.close();
			state.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Error" + e);
		} catch (SQLException e) {
			System.out.println("Error" + e);
		} finally { // 예외가 있든 없든 무조건 실행
			try {
				if (state != null)
					state.close();
			} catch (SQLException ex1) {
				//
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex1) {
				//
			}
		}
		return article;
	}

	public void modify(String title, String body, int id) {
		Connection conn = null;
		Statement state = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			state = conn.createStatement();

			String sql = "UPDATE article";
			sql += " SET updateDate = NOW()";
			sql += ", title = ?";
			sql += ", `body`= ?";
			sql += "WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, body);
			pstmt.setInt(3, id);
			pstmt.executeUpdate(); // SQL문을 전달하여 실행

			state.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Error" + e);
		} catch (SQLException e) {
			System.out.println("Error" + e);
		} finally { // 예외가 있든 없든 무조건 실행
			try {
				if (state != null)
					state.close();
			} catch (SQLException ex1) {
				//
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex1) {
				//
			}
		}
	}

	public void remove(int id) {
		Connection conn = null;
		Statement state = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			state = conn.createStatement();

			String sql = "DELETE FROM article WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate(); // SQL문을 전달하여 실행

			state.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Error" + e);
		} catch (SQLException e) {
			System.out.println("Error" + e);
		} finally { // 예외가 있든 없든 무조건 실행
			try {
				if (state != null)
					state.close();
			} catch (SQLException ex1) {
				//
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex1) {
				//
			}
		}
	}

	public int add(int boardId, String name, int memberId,String title, String body) {
		int id = 0;
		Connection conn = null;
		Statement state = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			state = conn.createStatement();

			String sql = "INSERT INTO article";
			sql += " SET regDate = NOW()";
			sql += ", updateDate = NOW()";
			sql += ", title = ?";
			sql += ", `body` = ?";
			sql += ", nickname = ?";
			sql += ", memberId = ?";
			sql += ", boardId = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, title);
			pstmt.setString(2, body);
			pstmt.setString(3, name);
			pstmt.setInt(4, memberId);
			pstmt.setInt(5, boardId);
			pstmt.executeUpdate();// SQL문을 전달하여 실행

			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);

			rs.close();
			state.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Error" + e);
		} catch (SQLException e) {
			System.out.println("Error" + e);
		} finally { // 예외가 있든 없든 무조건 실행
			try {
				if (state != null)
					state.close();
			} catch (SQLException ex1) {
				//
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex1) {
				//
			}
		}
		return id;
	}

	public int makeBoard(String name) {
		int id = 0;
		Connection conn = null;
		Statement state = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			state = conn.createStatement();

			String sql = "INSERT INTO board";
			sql += " SET regDate = NOW()";
			sql += ", updateDate = NOW()";
			sql += ", name = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, name);
			pstmt.executeUpdate();// SQL문을 전달하여 실행

			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);

			rs.close();
			state.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Error" + e);
		} catch (SQLException e) {
			System.out.println("Error" + e);
		} finally { // 예외가 있든 없든 무조건 실행
			try {
				if (state != null)
					state.close();
			} catch (SQLException ex1) {
				//
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex1) {
				//
			}
		}
		return id;
	}

	public Board getBoard(int id) {
		Board board = null;
		Connection conn = null;
		Statement state = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			state = conn.createStatement();

			String sql = "SELECT * FROM board WHERE id = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int boardId=rs.getInt("id");
				String name = rs.getString("name");
				board = new Board(boardId,name);
			}
			rs.close();
			state.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Error" + e);
		} catch (SQLException e) {
			System.out.println("Error" + e);
		} finally { // 예외가 있든 없든 무조건 실행
			try {
				if (state != null)
					state.close();
			} catch (SQLException ex1) {
				//
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex1) {
				//
			}
		}
		return board;
	}
}
