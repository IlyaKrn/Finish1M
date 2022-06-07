package com.example.finish1m.Data.VK.Database;

import com.example.finish1m.Domain.Models.WallModels.WallModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VKApiService {

    @GET("method/wall.get?v=5.131")
    Call<WallModel> getWallById(@Query("owner_id") String owner_id, @Query("access_token")String access_token);

}
