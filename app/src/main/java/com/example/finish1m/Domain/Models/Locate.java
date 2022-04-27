package com.example.finish1m.Domain.Models;

import java.util.ArrayList;

public class Locate {

    private String id;

    private double longitude;
    private double latitude;
    private String message;
    private String userEmail;

    private ArrayList<String> imageRefs;

    public Locate(String id, double longitude, double latitude, String message, String userEmail, ArrayList<String> imageRefs) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.message = message;
        this.userEmail = userEmail;
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
}
