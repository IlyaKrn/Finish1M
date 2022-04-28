package com.example.finish1m.Domain.Models;

import java.util.ArrayList;

public class Event {

    private String id;
    private int type;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    private String title;
    private String message;
    private String chatId;
    private ArrayList<String> imageRefs;
    private ArrayList<String> members;

    public Event(String id, int type, int year, int month, int day, int hour, int minute, int second, String title, String message, String chatId, ArrayList<String> imageRefs, ArrayList<String> members) {
        this.id = id;
        this.type = type;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.title = title;
        this.message = message;
        this.chatId = chatId;
        this.imageRefs = imageRefs;
        this.members = members;
    }

    public Event() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public ArrayList<String> getImageRefs() {
        return imageRefs;
    }

    public void setImageRefs(ArrayList<String> imageRefs) {
        this.imageRefs = imageRefs;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

