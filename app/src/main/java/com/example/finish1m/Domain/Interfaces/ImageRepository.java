package com.example.finish1m.Domain.Interfaces;

import android.graphics.Bitmap;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetImageListener;

public interface ImageRepository {

    void getImageByRef(String ref, OnGetDataListener<Bitmap> listener);
    void setImage(Bitmap bitmap, OnSetImageListener listener);
}
