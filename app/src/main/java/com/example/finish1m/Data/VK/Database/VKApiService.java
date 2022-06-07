package com.example.finish1m.Data.VK.Database;

import com.example.finish1m.Domain.Models.WallModels.WallModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VKApiService {

    @GET("method/wall.get?owner_id={owner_id}&v=5.131&access_token={access_token}")
    Call<WallModel> getWallById(@Path("owner_id")String owner_id, @Path("access_token")String access_token);

}
