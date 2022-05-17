package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;
import com.example.finish1m.Domain.Interfaces.UserRepository;
import com.example.finish1m.Domain.Models.Project;
import com.example.finish1m.Domain.Models.User;

// получение пользователя

public class GetUserByEmailUseCase {

    private UserRepository repository;
    private String email;
    private OnGetDataListener<User> listener;

    public GetUserByEmailUseCase(UserRepository repository, String email, OnGetDataListener<User> listener) {
        this.repository = repository;
        this.email = email;
        this.listener = listener;
    }

    public void execute(){
        repository.getUserByEmail(email, listener);
    }
}
