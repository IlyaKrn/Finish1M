package com.example.finish1m.Domain.Models;

import java.util.ArrayList;

// заявка на участие в проекте

public class Follow {

    private String id;
    private String userEmail; // пользователь
    private String message; // сообщение  от пользователя
    private ArrayList<String> imageRefs; // ссалки на изображения от пользователя


    public Follow(String id, String userEmail, String message, ArrayList<String> imageRefs) {
        this.id = id;
        this.userEmail = userEmail;
        this.message = message;
        this.imageRefs = imageRefs;
    }

    public Follow() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getImageRefs() {
        return imageRefs;
    }

    public void setImageRefs(ArrayList<String> imageRefs) {
        this.imageRefs = imageRefs;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "id='" + id + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", message='" + message + '\'' +
                ", imageRefs=" + imageRefs +
                '}';
    }
}
