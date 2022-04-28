package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Data.Firebase.AuthRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.AuthRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;

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
        repository.ResetPassword(email, new OnSetDataListener() {
            @Override
            public void onSetData() {
                listener.onSetData();
            }

            @Override
            public void onFailed() {
                listener.onFailed();
            }

            @Override
            public void onCanceled() {
                listener.onCanceled();
            }
        });
    }
}
