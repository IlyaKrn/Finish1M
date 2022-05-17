package com.example.finish1m.Domain.Interfaces;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Event;

import java.util.ArrayList;

public interface EventRepository {

    void getEventList(OnGetDataListener<ArrayList<Event>> listener);    // получение списка событий
    void getEventById(String eventId, OnGetDataListener<Event> listener);    // получение события по id
    void setEvent(String id, Event event, OnSetDataListener listener);    // запись данных в событие по id
    String getNewId();    // получение нового id
}
