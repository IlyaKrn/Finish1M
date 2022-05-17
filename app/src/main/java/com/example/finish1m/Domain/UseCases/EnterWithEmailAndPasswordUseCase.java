package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.AuthRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.UserRepository;
import com.example.finish1m.Domain.Models.User;

// вход через почту и пароль и получение пользователя из бд

public class EnterWithEmailAndPasswordUseCase {

    private UserRepository userRepository;
    private AuthRepository authRepository;
    private String email;
    private String password;
    private OnGetDataListener<User> listener;

    public EnterWithEmailAndPasswordUseCase(UserRepository userRepository, AuthRepository authRepository, String email, String password, OnGetDataListener<User> listener) {
        this.userRepository = userRepository;
        this.authRepository = authRepository;
        this.email = email;
        this.password = password;
        this.listener = listener;
    }

    public void execute(){
        authRepository.EnterWithEmailAndPassword(email, password, new OnSetDataListener() {
            @Override
            public void onSetData() {
                if (authRepository.isCurrentUserVerified()) {
                    userRepository.getUserByEmail(email, new OnGetDataListener<User>() {
                        @Override
                        public void onGetData(User data) {
                            listener.onGetData(data);
                        }

                        @Override
                        public void onVoidData() {
                            listener.onVoidData();
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
                else {
                    listener.onFailed();
                }
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
