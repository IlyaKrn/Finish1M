package com.example.finish1m.Domain.Models;

import java.util.ArrayList;

public class Follow {

    private String id;
    private String userEmail;
    private String message;
    private ArrayList<String> imageRefs;


    public Follow(String id, String userEmail, String message, ArrayList<String> imageRefs) {
        this.id = id;
        this.userEmail = userEmail;
        this.message = message;
        this.imageRefs = imageRefs;
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
