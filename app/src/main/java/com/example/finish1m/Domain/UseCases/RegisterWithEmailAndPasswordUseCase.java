package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.AuthRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.UserRepository;
import com.example.finish1m.Domain.Models.User;

public class RegisterWithEmailAndPasswordUseCase {

    private AuthRepository authRepository;
    private UserRepository userRepository;

    private String email;
    private String password;
    private OnSetDataListener listener;

    public RegisterWithEmailAndPasswordUseCase(AuthRepository authRepository, UserRepository userRepository, String email, String password, OnSetDataListener listener) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.email = email;
        this.password = password;
        this.listener = listener;
    }

    public void execute(){
        authRepository.RegisterWithEmailAndPassword(email, password, new OnSetDataListener() {
            @Override
            public void onSetData() {
                authRepository.sendVerificationEmail(email, listener);
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
