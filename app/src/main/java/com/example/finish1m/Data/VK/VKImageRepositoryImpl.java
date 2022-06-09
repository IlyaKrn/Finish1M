package com.example.finish1m.Data.VK;

import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.VKImageRepository;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Arrays;

public class VKImageRepositoryImpl implements VKImageRepository {

    private Context context;
    private static final String LOG_TAG = "VKImageRepositoryImpl";

    public VKImageRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getImageByRef(String ref, OnGetDataListener<Bitmap> listener) {
        try {
            Picasso.get().load(ref).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    if (context != null) {
                        if (bitmap != null) {
                            Log.d(LOG_TAG, String.format("get image from vk is success (ref='%s')", ref));
                            listener.onGetData(bitmap);
                        } else {
                            Log.e(LOG_TAG, String.format("get image from vk list is void data (ref='%s': no data in database)", ref));
                            listener.onVoidData();
                        }
                    }
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    if (context != null) {
                        Log.e(LOG_TAG, String.format("get image from vk is failed (ref='%s'): %s", ref, e.getMessage()));
                        listener.onFailed();
                    }
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });


        } catch (Exception e) {
            if (context != null) {
                Log.e(LOG_TAG, String.format("get image from vk is failed (ref='%s'): %s", ref, Arrays.toString(Arrays.stream(e.getStackTrace()).toArray())));
                listener.onFailed();
            }
        }

    }

    @Override
    public String getRoot6Chars() {
        return "https:";
    }
}
