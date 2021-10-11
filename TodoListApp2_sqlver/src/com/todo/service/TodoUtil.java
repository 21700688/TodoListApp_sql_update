package com.todo.service;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;



public class TodoUtil {
public static void createItem(TodoList list) {
	
		String title, memo;
		Scanner sc = new Scanner(System.in);
		

		System.out.printf("카테고리를 입력하세요: ");
		String category = sc.nextLine();
		System.out.printf("제목을 압력하세요: ");
		title = sc.next();
		sc.nextLine();
		
		if (list.isDuplicate(title)) {
			System.out.println("Error: 항목 추가에 실패하였습니다.");
			System.out.printf("중복된 제목이 존재합니다. 항목은 중복될 수 없습니다.\n");
			return;
		}
		
		System.out.printf("내용을 입력하세요: ");
		memo = sc.nextLine();
		
		System.out.printf("마감일을 입력하세요(yyyy/MM/dd): ");
		String due_date = sc.nextLine();
		
		TodoItem t = new TodoItem(title, memo);
		t.setCategory(category);
		t.setDuedate(due_date);
		
		if(list.addItem(t)>0) {
			System.out.println("항목이 추가되었습니다.");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		

		System.out.printf("삭제할 항목의 번호을 입력하여 주십시오: ");
		int n=sc.nextInt();
		sc.nextLine();
			
		if(l.deleteItem(n)>0)
		System.out.println("항목 "+n+"번을 삭제하였습니다.");
			
	}


	public static void updateItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		String title,memo,category,due_date;
		int input;
		System.out.printf("수정할 항목을 입력하세요: ");
		input = sc.nextInt();
		sc.nextLine();
		
		System.out.printf("새로운 제목을 입력하세요: ");
		title = sc.next();
		sc.nextLine();
		System.out.printf("새로운 내용을 입력하세요: ");
		memo = sc.nextLine();
		System.out.printf("새로운 카테고리를 입력하세요: ");
		category = sc.nextLine();
		System.out.printf("새로운 마감일을 입력하세요: ");
		due_date = sc.nextLine();
		
		TodoItem updated = new TodoItem(title,memo);
		updated.setCategory(category);
		updated.setDuedate(due_date);
		if(l.editItem(updated,input)>0)
			System.out.println("항목을 수정하였습니다.");
		}

	
	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록 총 %d개]\n",l.getCount());
		for(TodoItem item : l.listAll()) {
			System.out.printf("[%d]",item.getID());
			System.out.println(item.toString());
		}
		
	}
	
	public static void importData(TodoList l, String Filename) {
		try {
			FileReader fread= new FileReader(Filename);
			BufferedReader br = new BufferedReader(fread);
			String s;
			int record = 0;
			while((s=br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(s,"##");
				String category = st.nextToken();
				String title= st.nextToken();
				String desc = st.nextToken();
				TodoItem it = new TodoItem(title,desc);
				String date = st.nextToken();
				String duedate = st.nextToken();
				it.setCurrent_date(date);
				it.setCategory(category);
				it.setDuedate(duedate);
				l.addItem(it);
				record++;
				
			}
			System.out.println(record+"개의 데이터를 불러왔습니다.");
			fread.close();
			br.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록 총 %d개]\n",l.getCount());
		for(TodoItem item: l.getOrderedList(orderby, ordering)) {
			System.out.printf("[%d]",item.getID());
			System.out.println(item.toString());
		}
	}
	
	public static void find(TodoList l, String keyword) {
		int cnt=0;
		for(TodoItem item : l.getList(keyword)) {
			System.out.printf("[%d]",item.getID());
			System.out.println(item.toString());
			cnt++;
		}
		System.out.println("총 "+cnt+"개의 항목을 찾았습니다.");
	}
	
	public static void listCateAll(TodoList l) {
		int i = 0;
		for(String cate: l.getCategories()) {
			System.out.printf("%s ",cate);
			i++;
		}
		System.out.println("\n총 "+i+"개의 카테고리가 등록되어 있습니다.");
	}
	
	public static void findCate(TodoList l,String cate) {
		int i=0;
		for(TodoItem item : l.getCategoryList(cate)) {
			System.out.printf("[%d]",item.getID());
			System.out.println(item.toString());
			i++;
		}
		System.out.println("총 "+i+"개의 항목을 찾았습니다.");
	}
	
	public static void complete(TodoList l,int input) {
		if(l.comp(input)>0)
			System.out.println("일정을 완료하였습니다.");
		
	}
	
	public static void listAllCompleted(TodoList l) {
		int i=0;
		for(TodoItem item: l.listComp()) {
			System.out.printf("[%d]",item.getID());
			System.out.println(item.toString());
			i++;
		}
		System.out.println("총 "+i+"개의 항목을 찾았습니다.");
	}
	
}
