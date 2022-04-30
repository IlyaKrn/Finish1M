package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.LocateRepository;
import com.example.finish1m.Domain.Interfaces.UserRepository;
import com.example.finish1m.Domain.Models.Locate;
import com.example.finish1m.Domain.Models.User;

public class RefactorUserUseCase {

    private UserRepository repository;
    private User user;
    private OnSetDataListener listener;

    public void execute(){
        repository.setUser(user, listener);
    }
}
