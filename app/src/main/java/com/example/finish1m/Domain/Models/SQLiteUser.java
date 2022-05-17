package com.example.finish1m.Domain.Models;

// пользователь для сожранения во внутренней бд

public class SQLiteUser {

    private String email; // почта
    private String password; // пароль

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

    @Override
    public String toString() {
        return "SQLiteUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
