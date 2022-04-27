package com.example.finish1m.Domain.Interfaces;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Event;

import java.util.ArrayList;

public interface EventRepository {

    void getEventListeners(OnGetDataListener<ArrayList<Event>> listener);
    void getEventById(String eventId, OnGetDataListener<Event> listener);
    void setEvent(Event event, OnSetDataListener listener);
}
