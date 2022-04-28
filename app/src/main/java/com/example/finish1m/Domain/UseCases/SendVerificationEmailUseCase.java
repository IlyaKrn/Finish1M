package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.AuthRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;

public class SendVerificationEmailUseCase {

    private AuthRepository repository;
    private String email;
    private OnSetDataListener listener;

    public SendVerificationEmailUseCase(AuthRepository repository, String email, OnSetDataListener listener) {
        this.repository = repository;
        this.email = email;
        this.listener = listener;
    }

    public void execute(){
        repository.sendVerificationEmail(email, listener);
    }
}
