package com.example.finish1m.Domain.Models;

import java.util.ArrayList;

// событие (новость/мероприятие)

public class Event {

    public static final int NEWS = 1; // новость
    public static final int EVENT = 2; // мероприятие

    private String id;
    private int type; // тип

    private String title; // заголовок
    private String message; // описание
    private String chatId; // id чата для события
    private long date;
    private ArrayList<String> imageRefs; // ссылки на изображения
    private ArrayList<String> members; // заявки на участие

    public Event(String id, int type, String title, String message, String chatId, long date, ArrayList<String> imageRefs, ArrayList<String> members) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.message = message;
        this.chatId = chatId;
        this.date = date;
        this.imageRefs = imageRefs;
        this.members = members;
    }

    public Event() {
    }

    public static int getNEWS() {
        return NEWS;
    }

    public static int getEVENT() {
        return EVENT;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", chatId='" + chatId + '\'' +
                ", imageRefs=" + imageRefs +
                ", members=" + members +
                '}';
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}

