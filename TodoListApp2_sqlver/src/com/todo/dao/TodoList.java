package com.todo.dao;
import com.todo.service.TodoConnection;
import com.todo.dao.TodoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;

public class TodoList {
	Connection con;
	
	private List<TodoItem> list;
	
	public TodoList() {
		this.con = TodoConnection.getConnection();
		this.list = new ArrayList<TodoItem>();
	}

	public int addItem(TodoItem t) {
		String sql = "insert into Lists(title,memo,category,current_date,due_date)"+" values(?,?,?,?,?)";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getMemo());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDuedate());
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int input) {
		String sql = "delete from Lists Where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,input);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int editItem(TodoItem updated, int input) {
		String sql = "Update Lists set title=?, memo=?, category=?, current_date=?, due_date=?"+" where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,updated.getTitle());
			pstmt.setString(2,updated.getMemo());
			pstmt.setString(3,updated.getCategory());
			pstmt.setString(4,updated.getCurrent_date());
			pstmt.setString(5,updated.getDuedate());
			pstmt.setInt(6,input);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list =  new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "Select * from Lists order by "+ orderby;
			if(ordering == 0)
				sql+=" desc";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title,memo);
				t.setID(id);
				t.setCategory(category);
				t.setCurrent_date(current_date);
				t.setDuedate(due_date);
				t.setIs_completed(is_completed);
				list.add(t);
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("Select Distinct category from Lists");
			while(rs.next()) {
				list.add(rs.getString(1));
			}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return list;
		}
	
	public ArrayList<TodoItem> getCategoryList(String cate){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		String sql = "Select * from Lists where category = '"+cate+"'";
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title,memo);
				t.setID(id);
				t.setCategory(category);
				t.setCurrent_date(current_date);
				t.setDuedate(due_date);
				t.setIs_completed(is_completed);
				list.add(t); 
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	

	public ArrayList<TodoItem> listAll() {
		String sql = "Select * from Lists";
		Statement stmt;
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		TodoItem k = new TodoItem("title","memo");
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(sql);
			while(rs1.next()) {
				int id = rs1.getInt("id");
				String title = rs1.getString("title");
				String memo = rs1.getString("memo");
				String category = rs1.getString("category");
				String current_date = rs1.getString("current_date");
				String due_date = rs1.getString("due_date");
				int is_completed = rs1.getInt("is_completed");
				TodoItem item = new TodoItem(title,memo);
				item.setCategory(category);
				item.setCurrent_date(current_date);
				item.setDuedate(due_date);
				item.setID(id);
				item.setIs_completed(is_completed);
				list.add(item);
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getCount() {
		String sql = "Select count(id) from Lists";
		Statement stmt;
		int i=0;
		try {
			
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
				i = rs.getInt(1);
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return i;
		
	}
	
	public ArrayList<TodoItem> getList(String keyword){
		ArrayList<TodoItem> l = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";
		String sql = "Select * from Lists where title like ? or memo like ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem item = new TodoItem(title,memo);
				item.setCategory(category);
				item.setCurrent_date(current_date);
				item.setDuedate(due_date);
				item.setID(id);
				item.setIs_completed(is_completed);
				l.add(item);
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	public Boolean isDuplicate(String title) {
		
		String sql = "Select * from Lists where title = ?";
		PreparedStatement pstmt;
		int i=0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,title);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				i++;
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		if(i==0) return false;
		else return true;
				
	}
	
	public int comp(int input) {
		PreparedStatement pstmt;
		String sql = "Update Lists set is_completed = ?"+" where id = ?";
		int count = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,1);
			pstmt.setInt(2, input);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<TodoItem> listComp(){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		String sql = "Select * from Lists where is_completed = ?";
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, 1);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem item = new TodoItem(title,memo);
				item.setCategory(category);
				item.setCurrent_date(current_date);
				item.setDuedate(due_date);
				item.setID(id);
				item.setIs_completed(is_completed);
				list.add(item);
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
