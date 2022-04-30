package com.example.finish1m.Domain.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class Project {

    private String id;

    private String title;
    private String message;
    private String chatId;
    private ArrayList<String> imageRefs;
    private ArrayList<HashMap<String, String>> follows;

    public Project(String id, String title, String message, String chatId, ArrayList<String> imageRefs, ArrayList<HashMap<String, String>> follows) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.chatId = chatId;
        this.imageRefs = imageRefs;
        this.follows = follows;
    }

    public Project() {
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

    public ArrayList<HashMap<String, String>> getFollows() {
        return follows;
    }

    public void setFollows(ArrayList<HashMap<String, String>> follows) {
        this.follows = follows;
    }
}
