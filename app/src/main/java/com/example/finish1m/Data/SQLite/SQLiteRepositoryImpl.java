package com.example.finish1m.Data.SQLite;

import android.content.Context;

import com.example.finish1m.Data.SQLite.Database.SQLiteDbManager;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.SQLiteRepository;
import com.example.finish1m.Domain.Models.SQLiteUser;

public class SQLiteRepositoryImpl implements SQLiteRepository {

    private Context context;

    public SQLiteRepositoryImpl(Context context) {
        this.context = context;
    }

    // получение пользователя, сохраненного в бд
    @Override
    public void getSQLiteUser(OnGetDataListener<SQLiteUser> listener) {
        try{
            SQLiteUser user = new SQLiteDbManager(context).getUser();
            if(user != null)
                listener.onGetData(user);
            else
                listener.onVoidData();
        } catch (Exception e){
            listener.onFailed();
        }
    }

    // запись пользователя в бд
    @Override
    public void setSQLiteUser(SQLiteUser user, OnSetDataListener listener) {
        try{
            new SQLiteDbManager(context).setUser(user);
            listener.onSetData();
        } catch (Exception e){
            listener.onFailed();
        }
    }
}
