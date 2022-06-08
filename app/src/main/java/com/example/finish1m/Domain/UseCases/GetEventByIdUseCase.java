package com.example.finish1m.Domain.UseCases;

import android.util.Log;

import com.example.finish1m.Data.VK.VKRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Models.Event;

// получение события

public class GetEventByIdUseCase {

    private EventRepository eventRepository;
    private VKRepositoryImpl vkRepository;

    private String id;
    private OnGetDataListener<Event> listener;

    public GetEventByIdUseCase(EventRepository eventRepository, VKRepositoryImpl vkRepository, String id, OnGetDataListener<Event> listener) {
        this.eventRepository = eventRepository;
        this.vkRepository = vkRepository;
        this.id = id;
        this.listener = listener;
    }

    public void execute(){
        try {
            if (Integer.parseInt(String.valueOf(id.charAt(0))) == Event.DATA_SOURCE_FIREBASE) {
                eventRepository.getEventById(id, listener);
            } else if (Integer.parseInt(String.valueOf(id.charAt(0))) == Event.DATA_SOURCE_VK) {
                vkRepository.getEventMainWallById(id, listener);
            }
        }catch (NumberFormatException e){
            listener.onFailed();
            Log.e("GetEventByIdUseCase", "id:" + id + e.getMessage());
        }
    }
}
