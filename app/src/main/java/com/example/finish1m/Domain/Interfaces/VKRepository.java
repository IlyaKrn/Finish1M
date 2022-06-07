package com.example.finish1m.Domain.Interfaces;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Models.WallModels.WallModel;

public interface VKRepository {

    void getMainWall(OnGetDataListener<WallModel> listener);
    void getWallById(String id, OnGetDataListener<WallModel> listener);

}
