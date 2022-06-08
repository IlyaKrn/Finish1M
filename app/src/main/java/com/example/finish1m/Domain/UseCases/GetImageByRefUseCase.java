package com.example.finish1m.Domain.UseCases;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.finish1m.Data.VK.VKImageRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.ImageRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.VKImageRepository;

import java.util.HashMap;

// получение изображения

public class GetImageByRefUseCase {

    private ImageRepository imageRepository;
    private VKImageRepository vkImageRepository;
    private String ref;
    private OnGetDataListener listener;

    private static HashMap<String, Bitmap> cache = new HashMap<>();

    public GetImageByRefUseCase(ImageRepository imageRepository, VKImageRepository vkImageRepository, String ref, OnGetDataListener<Bitmap> listener) {
        this.imageRepository = imageRepository;
        this.vkImageRepository = vkImageRepository;
        this.ref = ref;
        this.listener = listener;
    }

    public void execute(){
        if(cache.get(ref) != null) {
            listener.onGetData(cache.get(ref));
        }
        else if(ref.length() >= 6) {
            if (ref.substring(0, 6).equals(imageRepository.getRoot6Chars())) {
                imageRepository.getImageByRef(ref, new OnGetDataListener<Bitmap>() {
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
            else if(ref.substring(0, 6).equals(vkImageRepository.getRoot6Chars())) {
                vkImageRepository.getImageByRef(ref, new OnGetDataListener<Bitmap>() {
                    @Override
                    public void onGetData(Bitmap data) {
                        cache.put(ref, data);
                        listener.onGetData(data);
                    }

                    @Override
                    public void onVoidData() {
                        imageRepository.getDefaultImage(listener);
                    }

                    @Override
                    public void onFailed() {
                        imageRepository.getDefaultImage(listener);
                    }

                    @Override
                    public void onCanceled() {
                        listener.onCanceled();
                        imageRepository.getDefaultImage(listener);
                    }
                });
            }
            else{
                 imageRepository.getDefaultImage(listener);
            }
        }
        else{
            imageRepository.getDefaultImage(listener);
        }
    }
}
