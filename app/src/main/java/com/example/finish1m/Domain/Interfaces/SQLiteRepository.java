package com.example.finish1m.Domain.Interfaces;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.SQLiteUser;

public interface SQLiteRepository {

    void getSQLiteUser(OnGetDataListener<SQLiteUser> listener);
    void setSQLiteUser(SQLiteUser user, OnSetDataListener listener);
}
