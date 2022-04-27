package com.example.finish1m.Domain.Interfaces;

import com.example.finish1m.Domain.Models.SQLiteUser;

public interface SQLiteRepository {

    void getSQLiteUser();
    void setSQLiteUser(SQLiteUser user);
}
