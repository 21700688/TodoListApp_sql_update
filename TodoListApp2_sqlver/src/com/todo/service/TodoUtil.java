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
		

		System.out.printf("ī�װ��� �Է��ϼ���: ");
		String category = sc.nextLine();
		System.out.printf("������ �з��ϼ���: ");
		title = sc.next();
		sc.nextLine();
		
		if (list.isDuplicate(title)) {
			System.out.println("Error: �׸� �߰��� �����Ͽ����ϴ�.");
			System.out.printf("�ߺ��� ������ �����մϴ�. �׸��� �ߺ��� �� �����ϴ�.\n");
			return;
		}
		
		System.out.printf("������ �Է��ϼ���: ");
		memo = sc.nextLine();
		
		System.out.printf("�������� �Է��ϼ���(yyyy/MM/dd): ");
		String due_date = sc.nextLine();
		
		TodoItem t = new TodoItem(title, memo);
		t.setCategory(category);
		t.setDuedate(due_date);
		
		if(list.addItem(t)>0) {
			System.out.println("�׸��� �߰��Ǿ����ϴ�.");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		

		System.out.printf("������ �׸��� ��ȣ�� �Է��Ͽ� �ֽʽÿ�: ");
		int n=sc.nextInt();
		sc.nextLine();
			
		if(l.deleteItem(n)>0)
		System.out.println("�׸� "+n+"���� �����Ͽ����ϴ�.");
			
	}


	public static void updateItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		String title,memo,category,due_date;
		int input;
		System.out.printf("������ �׸��� �Է��ϼ���: ");
		input = sc.nextInt();
		sc.nextLine();
		
		System.out.printf("���ο� ������ �Է��ϼ���: ");
		title = sc.next();
		sc.nextLine();
		System.out.printf("���ο� ������ �Է��ϼ���: ");
		memo = sc.nextLine();
		System.out.printf("���ο� ī�װ��� �Է��ϼ���: ");
		category = sc.nextLine();
		System.out.printf("���ο� �������� �Է��ϼ���: ");
		due_date = sc.nextLine();
		
		TodoItem updated = new TodoItem(title,memo);
		updated.setCategory(category);
		updated.setDuedate(due_date);
		if(l.editItem(updated,input)>0)
			System.out.println("�׸��� �����Ͽ����ϴ�.");
		}

	
	public static void listAll(TodoList l) {
		System.out.printf("[��ü ��� �� %d��]\n",l.getCount());
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
			System.out.println(record+"���� �����͸� �ҷ��Խ��ϴ�.");
			fread.close();
			br.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[��ü ��� �� %d��]\n",l.getCount());
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
		System.out.println("�� "+cnt+"���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void listCateAll(TodoList l) {
		int i = 0;
		for(String cate: l.getCategories()) {
			System.out.printf("%s ",cate);
			i++;
		}
		System.out.println("\n�� "+i+"���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.");
	}
	
	public static void findCate(TodoList l,String cate) {
		int i=0;
		for(TodoItem item : l.getCategoryList(cate)) {
			System.out.printf("[%d]",item.getID());
			System.out.println(item.toString());
			i++;
		}
		System.out.println("�� "+i+"���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void complete(TodoList l,int input) {
		if(l.comp(input)>0)
			System.out.println("������ �Ϸ��Ͽ����ϴ�.");
		
	}
	
	public static void listAllCompleted(TodoList l) {
		int i=0;
		for(TodoItem item: l.listComp()) {
			System.out.printf("[%d]",item.getID());
			System.out.println(item.toString());
			i++;
		}
		System.out.println("�� "+i+"���� �׸��� ã�ҽ��ϴ�.");
	}
	
}
