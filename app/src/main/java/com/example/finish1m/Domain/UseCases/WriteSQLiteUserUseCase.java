package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.SQLiteRepository;
import com.example.finish1m.Domain.Models.SQLiteUser;

// запись пользователя во внутреннюю бд

public class WriteSQLiteUserUseCase {

    private SQLiteRepository repository;
    private SQLiteUser sqLiteUser;
    private OnSetDataListener listener;

    public WriteSQLiteUserUseCase(SQLiteRepository repository, SQLiteUser sqLiteUser, OnSetDataListener listener) {
        this.repository = repository;
        this.sqLiteUser = sqLiteUser;
        this.listener = listener;
    }

    public void execute(){
        repository.setSQLiteUser(sqLiteUser, listener);
    }

}
