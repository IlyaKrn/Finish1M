package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Models.Event;

// получение события

public class GetEventByIdUseCase {

    private EventRepository repository;
    private String id;
    private OnGetDataListener<Event> listener;

    public GetEventByIdUseCase(EventRepository repository, String id, OnGetDataListener<Event> listener) {
        this.repository = repository;
        this.id = id;
        this.listener = listener;
    }

    public void execute(){
        repository.getEventById(id, listener);
    }
}
