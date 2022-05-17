package com.example.finish1m.Domain.Models;

import java.util.ArrayList;

// сообщение в чате

public class Message {

    private String message; // текст сообщения
    private String userEmail; // отправитель

    private ArrayList<String> imageRefs; // ссылки на изображения

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
        return imageRefs;
    }

    public void setImageRefs(ArrayList<String> imageRefs) {
        this.imageRefs = imageRefs;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", imageRefs=" + imageRefs +
                '}';
    }
}
