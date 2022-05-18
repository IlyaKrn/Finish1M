package com.example.finish1m.Domain.UseCases;

// проверка подключения к итнернету

import com.example.finish1m.Domain.Interfaces.ChatRepository;
import com.example.finish1m.Domain.Interfaces.ConnectionRepository;

public class CheckConnectionUseCase {

    private ConnectionRepository repository;

    public CheckConnectionUseCase(ConnectionRepository repository) {
        this.repository = repository;
    }

    public boolean execute(){
        return repository.isConnected();
    }
}
