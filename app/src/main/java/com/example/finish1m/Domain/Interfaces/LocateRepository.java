package com.example.finish1m.Domain.Interfaces;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Locate;

import java.util.ArrayList;

public interface LocateRepository {

    void getLocateList(OnGetDataListener<ArrayList<Locate>> listener);
    void getLocateById(String eventId, OnGetDataListener<Locate> listener);
    void setLocate(Locate locate, OnSetDataListener listener);
    String getNewId();
}
