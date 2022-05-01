package com.example.finish1m.Domain.Models;

public class User {

    private String firstName;
    private String lastName;

    private String email;

    private boolean isBanned;
    private boolean isAdmin;
    private String iconRef;

    public User(String firstName, String lastName, String email, boolean isBanned, boolean isAdmin, String iconRef) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isBanned = isBanned;
        this.isAdmin = isAdmin;
        this.iconRef = iconRef;
    }

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getIconRef() {
        return iconRef;
    }

    public void setIconRef(String iconRef) {
        this.iconRef = iconRef;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", isBanned=" + isBanned +
                ", isAdmin=" + isAdmin +
                ", iconRef='" + iconRef + '\'' +
                '}';
    }
}
