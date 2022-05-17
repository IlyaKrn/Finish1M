package com.example.finish1m.Domain.Interfaces;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Locate;

import java.util.ArrayList;

public interface LocateRepository {

    void getLocateList(OnGetDataListener<ArrayList<Locate>> listener);    // получение списка меток
    void getLocateById(String eventId, OnGetDataListener<Locate> listener);    // получение метки по id
    void setLocate(String id, Locate locate, OnSetDataListener listener);    // запись данных в метку по id
    String getNewId();    // получение нового id
}
