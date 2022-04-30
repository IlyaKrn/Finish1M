package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.UserRepository;

public class DeleteUserByEmailUseCase {

    private UserRepository repository;
    private String email;
    private OnSetDataListener listener;

    public DeleteUserByEmailUseCase(UserRepository repository, String email, OnSetDataListener listener) {
        this.repository = repository;
        this.email = email;
        this.listener = listener;
    }

    public void execute(){
        repository.setUser(email, null, listener);
    }
}
