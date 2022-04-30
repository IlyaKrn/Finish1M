package com.example.finish1m.Domain.Models;

import java.util.ArrayList;

public class Locate {

    private String id;

    private double longitude;
    private double latitude;
    private String title;
    private String message;
    private String chatId;

    private ArrayList<String> imageRefs;

    public Locate(String id, double longitude, double latitude, String title, String message, String chatId, ArrayList<String> imageRefs) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.title = title;
        this.message = message;
        this.chatId = chatId;
        this.imageRefs = imageRefs;
    }

    public Locate() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
