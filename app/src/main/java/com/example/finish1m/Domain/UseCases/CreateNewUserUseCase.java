package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.UserRepository;
import com.example.finish1m.Domain.Models.User;

public class CreateNewUserUseCase {

    private UserRepository userRepository;
    private User user;

    private OnSetDataListener listener;

    public CreateNewUserUseCase(UserRepository userRepository, User user, OnSetDataListener onSetDataListener) {
        this.userRepository = userRepository;
        this.user = user;
        this.listener = onSetDataListener;
    }

    public void execute(){
        userRepository.setUser(user, listener);
    }
}
