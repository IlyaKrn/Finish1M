package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.ImageRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;

public class GetImageByRefUseCase {

    private ImageRepository repository;
    private String ref;
    private OnGetDataListener listener;

    public GetImageByRefUseCase(ImageRepository repository, String ref, OnGetDataListener listener) {
        this.repository = repository;
        this.ref = ref;
        this.listener = listener;
    }

    public void execute(){
        repository.getImageByRef(ref, listener);
    }
}
