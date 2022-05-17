package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Data.Firebase.AuthRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.AuthRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;

// сброс пароля

public class ResetPasswordUseCase {

    private AuthRepositoryImpl repository;
    private String email;
    private OnSetDataListener listener;

    public ResetPasswordUseCase(AuthRepositoryImpl repository, String email, OnSetDataListener listener) {
        this.repository = repository;
        this.email = email;
        this.listener = listener;
    }

    public void  execute(){
        repository.ResetPassword(email, listener);
    }
}
