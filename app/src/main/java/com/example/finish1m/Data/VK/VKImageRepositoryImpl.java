package com.example.finish1m.Data.VK;

import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.VKImageRepository;

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
            java.net.URL url = new java.net.URL(ref);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            if (bitmap != null){
                Log.d(LOG_TAG, String.format("get image from vk is success (ref='%s')", ref));
                listener.onGetData(bitmap);
            }
            else{
                Log.e(LOG_TAG, String.format("get image from vk list is void data (ref='%s': no data in database)", ref));
                listener.onVoidData();
            }
            /*
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://wwwns.akamai.com/media_resources/globe_emea.png")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(LOG_TAG, String.format("get image from vk is failed (ref='%s'): %s", ref, e.getMessage()));
                    listener.onFailed();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(response.body().bytes(), 0, response.body().bytes().length);

                    if (bitmap != null){
                        Log.d(LOG_TAG, String.format("get image from vk is success (ref='%s')", ref));
                        listener.onGetData(bitmap);
                    }
                    else{
                        Log.e(LOG_TAG, String.format("get image from vk list is void data (ref='%s': no data in database)", ref));
                        listener.onVoidData();
                    }
                }
            });

             */
            /*

             Picasso.Builder builder = new Picasso.Builder(context);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso p, Uri u, Exception e){
                    e.printStackTrace();
                }
            });

            Picasso pic = builder.build();
            pic.load(new File(ref)).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    if (bitmap != null){
                        Log.d(LOG_TAG, String.format("get image from vk is success (ref='%s')", ref));
                        listener.onGetData(bitmap);
                    }
                    else{
                        Log.e(LOG_TAG, String.format("get image from vk is void data (ref='%s': no data in database)", ref));
                        listener.onVoidData();
                    }
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Log.e(LOG_TAG, String.format("get image from vk is failed (ref='%s'): %s", ref, e.getMessage()));
                    listener.onFailed();
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });

             */
            /*

            Picasso.get().load(ref.substring(1)).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    if (bitmap != null){
                        Log.d(LOG_TAG, String.format("get image from vk is success (ref='%s')", ref));
                        listener.onGetData(bitmap);
                    }
                    else{
                        Log.e(LOG_TAG, String.format("get image from vk list is void data (ref='%s': no data in database)", ref));
                        listener.onVoidData();
                    }
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Log.e(LOG_TAG, String.format("get image from vk is failed (ref='%s'): %s", ref, e.getMessage()));
                    listener.onFailed();
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });

 */
        } catch (Exception e) {
            Log.e(LOG_TAG, String.format("get image from vk is failed (ref='%s'): %s", ref, Arrays.toString(Arrays.stream(e.getStackTrace()).toArray())));
            listener.onFailed();
        }

    }

    @Override
    public String getRoot6Chars() {
        return "https:";
    }
}
