package com.example.finish1m.Domain.Interfaces;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.WallModels.WallModel;

import java.util.ArrayList;

public interface VKRepository {

    void getMainWall(OnGetDataListener<ArrayList<Event>> listener);
    void getWallById(String id, OnGetDataListener<ArrayList<Event>> listener);
    void getEventMainWallById(String eventId, OnGetDataListener<Event> listener);
    void getEventById(String wallId, String eventId, OnGetDataListener<Event> listener);
}
