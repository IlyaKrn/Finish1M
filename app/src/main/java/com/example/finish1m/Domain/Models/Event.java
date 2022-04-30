package com.example.finish1m.Domain.Models;

import java.util.ArrayList;

public class Event {

    public static final int NEWS = 1;
    public static final int EVENT = 2;

    private String id;
    private int type;

    private String title;
    private String message;
    private String chatId;
    private ArrayList<String> imageRefs;
    private ArrayList<String> members;

    public Event(String id, int type, String title, String message, String chatId, ArrayList<String> imageRefs, ArrayList<String> members) {
        this.id = id;
        this.type = type;
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
        if (imageRefs == null)
            return new ArrayList<>();
        return imageRefs;
    }

    public void setImageRefs(ArrayList<String> imageRefs) {
        this.imageRefs = imageRefs;
    }

    public ArrayList<String> getMembers() {
        if (members == null)
            return new ArrayList<>();
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

