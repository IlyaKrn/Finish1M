package com.example.finish1m.Data.SQLite.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Models.SQLiteUser;

import java.util.ArrayList;

public class SQLiteDbManager {

    private Context context;  // контекст для SQLiteDbHelper
    private SQLiteDbHelper myDbHelper; // объект для взаимодейсевия с бд
    private static SQLiteDatabase db; // база данных

    // конструктор
    public SQLiteDbManager(Context context){
        this.context = context;
        myDbHelper = new SQLiteDbHelper(context);
    }

    // запись пользователя
    public void setUser(SQLiteUser sqlUser){
        clear();
        if (sqlUser != null) {
            ContentValues cv = new ContentValues();
            cv.put(SQLiteDbConstants.EMAIL, sqlUser.getEmail());
            cv.put(SQLiteDbConstants.PASSWORD, sqlUser.getPassword());
            openDb();
            db.insert(SQLiteDbConstants.TABLE_NAME, null, cv);
            closeDb();
        }
    }

    // получение последней записи из бд
    public SQLiteUser getUser(){
        if (!isEmpty()) {
            openDb();
            Cursor cursor = db.query(SQLiteDbConstants.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String e = cursor.getString(cursor.getColumnIndex(SQLiteDbConstants.EMAIL));
                @SuppressLint("Range") String p = cursor.getString(cursor.getColumnIndex(SQLiteDbConstants.PASSWORD));
                cursor.close();
                closeDb();
                return new SQLiteUser(e, p);
            }
        }
        return null;
    }


    // открытие и закрытие бд
    private void openDb(){
        db = myDbHelper.getWritableDatabase();
    }
    private void closeDb(){
        myDbHelper.close();
    }
    // проверка наличия содежимого в бд
    private boolean isEmpty(){
        openDb();
        Cursor cursor = db.query(SQLiteDbConstants.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            closeDb();
            return false;
        }
        cursor.close();
        return true;

    }
    // удаление всех записей
    public void clear() {
        openDb();
        db.delete(SQLiteDbConstants.TABLE_NAME, null, null);
        closeDb();
    }
}
