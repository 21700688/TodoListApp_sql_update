package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

	public class TodoItem {
		private int id;
	    private String title;
	    private String memo;
	    private String simpledate;
	    private String category;
	    private String due_date;
	    private int is_completed;

	    public TodoItem(String title, String memo){
	    	this.id = 0;
	        this.title=title;
	        this.memo = memo;
	        Date current_date = new Date();
	        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	        this.simpledate = format.format(current_date);
	    }
	    
	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public String getMemo() {
	        return memo;
	    }

	    public void setMemo(String memo) {
	        this.memo =memo;
	    }

	    public String getCurrent_date() {
	        return simpledate
	        		;
	    }

	    public void setCurrent_date(String simpledate) {
	    	this.simpledate = simpledate;
	    }
	    
	    public String getCategory() {
	    	return category;
	    }
	    
	    public void setCategory(String category) {
	    	this.category = category;
	    }
	    
	    public 	String getDuedate() {
	    	return due_date;
	    }
	    
	    public void setDuedate(String due_date) {
	    	this.due_date = due_date;
	    }
	    public int getID() {
	    	return id;
	    }
	    public void setID(int id) {
	    	this.id = id;
	    }
	    public int getIs_completed() {
	    	return is_completed;
	    }
	    public void setIs_completed(int is_completed) {
	    	this.is_completed = is_completed;
	    }
	    
	    public String toSaveString() {
	    	return category+"##"+title+"##"+memo+"##"+simpledate+"##"+due_date+"\n";
	    }
	    
	    public String toString() {
	    	if(is_completed == 0)
	    		return "["+category+"]"+" "+title+" - "+memo+" - "+simpledate+" - "+due_date;
	    	else
	    		return "["+category+"]"+" "+title+"[V]"+" - "+memo+" - "+simpledate+" - "+due_date;
	    }

}
