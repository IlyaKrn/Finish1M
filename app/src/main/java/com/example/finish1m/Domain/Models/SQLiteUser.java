package com.example.finish1m.Domain.Models;

public class SQLiteUser {

    private String email;
    private String password;

    public SQLiteUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
