package com.example.finish1m.Domain.Interfaces;

import android.graphics.Bitmap;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetImageListener;

public interface ImageRepository {

    void getImageByRef(String ref, OnGetDataListener<Bitmap> listener);    // получение изображения по ссылке
    void setImage(Bitmap bitmap, OnSetImageListener listener);    // загрузка изображения
    void getDefaultImage(OnGetDataListener<Bitmap> listener);
    String getRoot6Chars();
}
