package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.SQLiteRepository;
import com.example.finish1m.Domain.Models.SQLiteUser;

// получение сохраненного пользователя из внутренней бд

public class GetSQLiteUserUseCase {

    private SQLiteRepository repository;
    private OnGetDataListener listener;

    public GetSQLiteUserUseCase(SQLiteRepository repository, OnGetDataListener<SQLiteUser> listener) {
        this.repository = repository;
        this.listener = listener;
    }

    public void execute(){
        repository.getSQLiteUser(listener);
    }
}
