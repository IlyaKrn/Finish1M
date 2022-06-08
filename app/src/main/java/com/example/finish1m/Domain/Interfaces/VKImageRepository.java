package com.example.finish1m.Domain.Interfaces;

import android.graphics.Bitmap;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;

public interface VKImageRepository {
    void getImageByRef(String ref, OnGetDataListener<Bitmap> listener);    // получение изображения по ссылке
    String getRoot6Chars();
}
