package com.example.finish1m.Domain.Models;

import java.util.ArrayList;

public class Message {

    private String message;
    private String userEmail;

    private ArrayList<String> imageRefs;

    public Message(String message, String userEmail, ArrayList<String> imageRefs) {
        this.message = message;
        this.userEmail = userEmail;
        this.imageRefs = imageRefs;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public ArrayList<String> getImageRefs() {
        if (imageRefs == null)
            this.imageRefs = new ArrayList<>();
        return imageRefs;
    }

    public void setImageRefs(ArrayList<String> imageRefs) {
        if (imageRefs == null)
            this.imageRefs = new ArrayList<>();
        else
            this.imageRefs = imageRefs;
    }
}
