package com.example.finish1m.Data.Firebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.finish1m.Data.Firebase.Database.FirebaseConfig;
import com.example.finish1m.Domain.Interfaces.ImageRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetImageListener;
import com.example.finish1m.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ImageRepositoryImpl implements ImageRepository {

    private Context context;
    private static final String LOG_TAG = "ImageRepositoryImpl";

    private static final int MAX_IMAGE_SIZE = 13_000_000;

    public ImageRepositoryImpl(Context context) {
        this.context = context;
    }

    // получение изображения по ссылке
    @Override
    public void getImageByRef(String ref, OnGetDataListener<Bitmap> listener) {
        try {
            FirebaseStorage.getInstance().getReference().child(ref).getBytes(1024*1024 * 1024).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    if (context != null) {
                        if (task.isSuccessful())
                            if (task.getResult() != null) {
                                Log.d(LOG_TAG, String.format("get image is success (ref='%s')", ref));
                                listener.onGetData(BitmapFactory.decodeByteArray(task.getResult(), 0, task.getResult().length));
                            }
                            else {
                                Log.e(LOG_TAG, String.format("get image is void data (ref='%s': no data in database)", ref));
                                getDefaultImage(listener);
                            }
                        else {
                            Log.e(LOG_TAG, String.format("get image is failed (ref='%s'): %s", ref, task.getException().getMessage()));
                            getDefaultImage(listener);
                        }
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("get image is failed (ref='%s'): %s", ref, e.getMessage()));
            getDefaultImage(listener);
        }
    }

    // загрузка изображения
    @Override
    public void setImage(Bitmap bitmap, OnSetImageListener listener) {

        try {

            if (bitmap.getAllocationByteCount() > MAX_IMAGE_SIZE){
                int coeff = bitmap.getAllocationByteCount()/MAX_IMAGE_SIZE;
                bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/coeff, bitmap.getHeight()/coeff, false);
            }


            String name = FirebaseDatabase.getInstance().getReference().push().getKey();
            FirebaseStorage.getInstance().getReference().child(FirebaseConfig.STORAGE_IMAGES_ALL).child(name).putBytes(getBytes(bitmap)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (context != null) {
                        if (task.isSuccessful()) {
                            Log.d(LOG_TAG, String.format("write image is success (ref='%s')", FirebaseConfig.STORAGE_IMAGES_ALL + "/" + name));
                            listener.onSetData(FirebaseConfig.STORAGE_IMAGES_ALL + "/" + name);
                        }
                        else {
                            Log.e(LOG_TAG, String.format("write image is cancelled (ref='%s'): %s", FirebaseConfig.STORAGE_IMAGES_ALL + "/" + name, task.getException().getMessage()));
                            listener.onCanceled();
                        }
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("write image is failed (ref='null'): %s", e.getMessage()));
            listener.onFailed();
        }
    }

    @Override
    public String getRoot6Chars() {
        return "images";
    }

    // получуние стандартного изображения
    @Override
    public void getDefaultImage(OnGetDataListener<Bitmap> listener){
        try {
            FirebaseStorage.getInstance().getReference().child(FirebaseConfig.STORAGE_DEFAULT_ICON).getBytes(1024 * 1024 * 1024).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    if (context != null) {
                        if (task.isSuccessful())
                            if (task.getResult() != null) {
                                Log.d(LOG_TAG, String.format("get default image is success (ref='%s')", FirebaseConfig.STORAGE_DEFAULT_ICON));
                                listener.onGetData(BitmapFactory.decodeByteArray(task.getResult(), 0, task.getResult().length));
                            }
                            else {
                                Log.d(LOG_TAG, String.format("get default image is void data (ref='%s'): no data in database", FirebaseConfig.STORAGE_DEFAULT_ICON));
                                listener.onGetData(((BitmapDrawable)context.getDrawable(R.drawable.default_image)).getBitmap());
                            }
                        else {
                            Log.d(LOG_TAG, String.format("get default image is cancelled (ref='%s'): %s", FirebaseConfig.STORAGE_DEFAULT_ICON, task.getException().getMessage()));
                            listener.onGetData(((BitmapDrawable)context.getDrawable(R.drawable.default_image)).getBitmap());
                        }
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("get default image is failed (ref='null'): %s", e.getMessage()));
            listener.onGetData(((BitmapDrawable)context.getDrawable(R.drawable.default_image)).getBitmap());
        }
    }

    // преобразование изображения в массив
    private byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
