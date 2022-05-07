package com.example.finish1m.Domain.UseCases;

import android.graphics.Bitmap;

import com.example.finish1m.Domain.Interfaces.ImageRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetImageListener;
import com.example.finish1m.Domain.Interfaces.LocateRepository;
import com.example.finish1m.Domain.Interfaces.UserRepository;
import com.example.finish1m.Domain.Models.Locate;
import com.example.finish1m.Domain.Models.User;

public class RefactorUserUseCase {

    private UserRepository repository;
    private ImageRepository imageRepository;
    private User user;
    private Bitmap icon;
    private OnSetDataListener listener;

    public RefactorUserUseCase(UserRepository repository, User user, OnSetDataListener listener) {
        this.repository = repository;
        this.user = user;
        this.listener = listener;
        this.icon = null;
        this.imageRepository = null;
    }

    public RefactorUserUseCase(UserRepository repository, ImageRepository imageRepository, User user, Bitmap icon, OnSetDataListener listener) {
        this.repository = repository;
        this.imageRepository = imageRepository;
        this.user = user;
        this.icon = icon;
        this.listener = listener;
    }

    public void execute(){
        if(icon != null){
            imageRepository.setImage(icon, new OnSetImageListener() {
                @Override
                public void onSetData(String ref) {
                    user.setIconRef(ref);
                    repository.setUser(user.getEmail(), user, listener);
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
            repository.setUser(user.getEmail(), user, listener);
        }
    }
}
