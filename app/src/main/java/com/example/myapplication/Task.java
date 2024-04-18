package com.example.myapplication;

public class Task {
    private String date; // 任务日期，格式为 "x月xx日"
    private String title; // 任务标题
   private String content;//任务内容
    private String person;//指派人员

    public Task(String date, String title,String content,String person) {
        this.date = date;
        this.title = title;
        this.content =content ;
        this.title = person;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public void setPerson(String person) {
        this.person = person;
    }
    // 可以添加其他getter和setter方法
}