package com.sbs.example.practice.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sbs.example.practice.dto.Member;

public class MemberDao {
	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // 드라이버
	private final String DB_URL = "jdbc:mysql://localhost:3306/testData?&serverTimezone=Asia/Seoul"; // 접속할 DB 서버

	private final String USER_NAME = "sbsst"; // DB에 접속할 사용자 이름을 상수로 정의
	private final String PASSWORD = "sbs123414"; // 사용자의 비밀번호를 상수로 정의
	
	public MemberDao() {
		
	}

	public void add(String userId, String passwd, String name) {
		Connection conn = null;
		Statement state = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			state = conn.createStatement();

			String sql = "INSERT INTO member";
			sql += " SET regDate = NOW()";
			sql += ", userId = ?";
			sql += ", passwd = ?";
			sql += ", name = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, userId);
			pstmt.setString(2, passwd);
			pstmt.setString(3, name);
			pstmt.executeUpdate();// SQL문을 전달하여 실행

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

	public boolean examine(String userId) {
		boolean isItDuplicate = false;
		Connection conn = null;
		Statement state = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			state = conn.createStatement();
			
			String sql = "SELECT userId FROM `member` WHERE userId = ?";
			ResultSet rs = null;
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.executeQuery();// SQL문을 전달하여 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				isItDuplicate = true;
			}
			else {
				isItDuplicate = false;
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
		return isItDuplicate;
	}

	public Member search(String userId) {
		Member member = null;
		Connection conn = null;
		Statement state = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			state = conn.createStatement();
			
			String sql = "SELECT * FROM `member` WHERE userId = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.executeQuery();// SQL문을 전달하여 실행
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String loginId=rs.getString("userId");
				String passwd=rs.getString("passwd");
				String name=rs.getString("name");
				String regDate =rs.getString("regDate");
				member = new Member(id,loginId,passwd,name,regDate);
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
		return member;
	}

	public Member getMember(int id) {
		Member member = null;
		Connection conn = null;
		Statement state = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			state = conn.createStatement();
			
			String sql = "SELECT * FROM `member` WHERE id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeQuery();// SQL문을 전달하여 실행
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int primaryKey = rs.getInt("id");
				String loginId=rs.getString("userId");
				String passwd=rs.getString("passwd");
				String name=rs.getString("name");
				String regDate =rs.getString("regDate");
				member = new Member(primaryKey,loginId,passwd,name,regDate);
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
		return member;
	}
}
