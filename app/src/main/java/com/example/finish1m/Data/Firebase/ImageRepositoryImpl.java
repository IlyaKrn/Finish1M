package com.example.finish1m.Data.Firebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.example.finish1m.Data.Firebase.Database.FirebaseConfig;
import com.example.finish1m.Domain.Interfaces.ImageRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetImageListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ImageRepositoryImpl implements ImageRepository {

    private Context context;

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
                            if (task.getResult() != null)
                                listener.onGetData(BitmapFactory.decodeByteArray(task.getResult(), 0, task.getResult().length));
                            else {
                                getDefaultImage(listener);
                            }
                        else
                            listener.onCanceled();
                    }
                }
            });
        } catch (Exception e){
            getDefaultImage(listener);
        }
    }

    // загрузка изображения
    @Override
    public void setImage(Bitmap bitmap, OnSetImageListener listener) {
        try {
            String name = FirebaseDatabase.getInstance().getReference().push().getKey();
            FirebaseStorage.getInstance().getReference().child(FirebaseConfig.STORAGE_IMAGES_ALL).child(name).putBytes(getBytes(bitmap)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (context != null) {
                        if (task.isSuccessful())
                            listener.onSetData(FirebaseConfig.STORAGE_IMAGES_ALL + "/" + name);
                        else
                            listener.onCanceled();
                    }
                }
            });
        } catch (Exception e){
            listener.onFailed();
        }
    }

    // получуние стандартного изображения
    private void getDefaultImage(OnGetDataListener<Bitmap> listener){
        try {
            FirebaseStorage.getInstance().getReference().child(FirebaseConfig.STORAGE_DEFAULT_ICON).getBytes(1024 * 1024 * 1024).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    if (context != null) {
                        if (task.isSuccessful())
                            if (task.getResult() != null)
                                listener.onGetData(BitmapFactory.decodeByteArray(task.getResult(), 0, task.getResult().length));
                            else {
                                listener.onVoidData();
                            }
                        else
                            listener.onCanceled();
                    }
                }
            });
        } catch (Exception e){
            listener.onFailed();
        }
    }

    // преобразование изображения в массив
    private byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
