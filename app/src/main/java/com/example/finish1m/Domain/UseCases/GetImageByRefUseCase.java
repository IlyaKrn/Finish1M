package com.example.finish1m.Domain.UseCases;

import android.graphics.Bitmap;

import com.example.finish1m.Domain.Interfaces.ImageRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;

import java.util.HashMap;

// получение изображения

public class GetImageByRefUseCase {

    private ImageRepository repository;
    private String ref;
    private OnGetDataListener listener;

    private static HashMap<String, Bitmap> cache = new HashMap<>();

    public GetImageByRefUseCase(ImageRepository repository, String ref, OnGetDataListener<Bitmap> listener) {
        this.repository = repository;
        this.ref = ref;
        this.listener = listener;
    }

    public void execute(){
        if(cache.get(ref) != null) {
            listener.onGetData(cache.get(ref));
        }
        else {
            repository.getImageByRef(ref, new OnGetDataListener<Bitmap>() {
                @Override
                public void onGetData(Bitmap data) {
                    cache.put(ref, data);
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
    }
}
