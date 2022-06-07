package com.example.finish1m.Data.VK;

import android.content.Context;
import android.util.Log;

import com.example.finish1m.Data.VK.Database.VKApiService;
import com.example.finish1m.Data.VK.Database.VKConfig;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.VKRepository;
import com.example.finish1m.Domain.Models.WallModels.WallModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VKRepositoryImpl implements VKRepository {

    private Context context;
    private static final String LOG_TAG = "VKRepositoryImpl";

    public VKRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getMainWall(OnGetDataListener<WallModel> listener) {
        try {
            Gson gson = new GsonBuilder().create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(VKConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            VKApiService service = retrofit.create(VKApiService.class);
            service.getWallById(VKConfig.MAIN_WALL_ID, VKConfig.ACCESS_TOKEN).enqueue(new Callback<WallModel>() {
                @Override
                public void onResponse(Call<WallModel> call, Response<WallModel> response) {
                    if(response.isSuccessful()){
                        if(response.body() != null && response.body().response != null){
                            Log.d(LOG_TAG, String.format("get main wall is success (id='%s')", VKConfig.MAIN_WALL_ID));
                            listener.onGetData(response.body());
                        }
                        else{
                            Log.e(LOG_TAG, String.format("get main wall is void data (id='%s': no data in database)", VKConfig.MAIN_WALL_ID));
                            listener.onVoidData();
                        }
                    }
                    else{
                        listener.onFailed();
                    }
                }

                @Override
                public void onFailure(Call<WallModel> call, Throwable throwable) {
                    Log.e(LOG_TAG, String.format("get main wall is failed (id='%s'): %s", VKConfig.MAIN_WALL_ID, throwable.getMessage()));
                    listener.onFailed();
                }
            });
        } catch (Exception e) {
            Log.e(LOG_TAG, String.format("get main wall is failed (id='%s'): %s", VKConfig.MAIN_WALL_ID, e.getMessage()));
            listener.onFailed();
        }

    }

    @Override
    public void getWallById(String id, OnGetDataListener<WallModel> listener) {
        try {
            Gson gson = new GsonBuilder().create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(VKConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            VKApiService service = retrofit.create(VKApiService.class);
            service.getWallById(id, VKConfig.ACCESS_TOKEN).enqueue(new Callback<WallModel>() {
                @Override
                public void onResponse(Call<WallModel> call, Response<WallModel> response) {
                    if(response.isSuccessful()){
                        if(response.body() != null && response.body().response != null){
                            Log.d(LOG_TAG, String.format("get wall is success (id='%s')", id));
                            listener.onGetData(response.body());
                        }
                        else{
                            Log.e(LOG_TAG, String.format("get wall is void data (id='%s': no data in database)", id));
                            listener.onVoidData();
                        }
                    }
                    else{
                        listener.onFailed();
                    }
                }

                @Override
                public void onFailure(Call<WallModel> call, Throwable throwable) {
                    Log.e(LOG_TAG, String.format("get wall is failed (id='%s'): %s", id, throwable.getMessage()));
                    listener.onFailed();
                }
            });
        } catch (Exception e) {
            Log.e(LOG_TAG, String.format("get wall is failed (id='%s'): %s", id, e.getMessage()));
            listener.onFailed();
        }
    }
}
