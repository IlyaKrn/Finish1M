package com.example.finish1m.Domain.Models;

import java.util.ArrayList;
import java.util.HashMap;

// проект

public class Project {

    private String id;

    private String title; // заголовок
    private String message; // описание
    private String chatId; // ссылка на чат
    private ArrayList<String> imageRefs; // ссылки на изображения
    private ArrayList<Follow> follows; // заявки от пользователей

    public Project(String id, String title, String message, String chatId, ArrayList<String> imageRefs, ArrayList<Follow> follows) {
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

    public ArrayList<Follow> getFollows() {
        return follows;
    }

    public void setFollows(ArrayList<Follow> follows) {
        this.follows = follows;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", chatId='" + chatId + '\'' +
                ", imageRefs=" + imageRefs +
                ", follows=" + follows +
                '}';
    }
}
