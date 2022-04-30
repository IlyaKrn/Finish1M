package com.example.finish1m.Data.SQLite.Database;

/**
 * класс с константами базы данных
 * */

public class SQLiteDbConstants {

    // название базы данных и таблицы
    public static final String DB_NAME = "last_user_database.db";
    public static final String TABLE_NAME ="last_user";
    // столбцы таблицы
    public static final String _ID = "_id";
    public static final String EMAIL ="current_login";
    public static final String PASSWORD ="current_password";

    // версия бд
    public static final int DB_VERSION = 4;
    // структура и сброс таблицы
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String TABLE_STRUCTURE =
    "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," +
            EMAIL + " TEXT," +
            PASSWORD + " TEXT)";


}
