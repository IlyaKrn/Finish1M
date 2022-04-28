package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.LocateRepository;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Locate;

import java.util.ArrayList;

public class GetEventListUseCase {

    private EventRepository repository;
    private OnGetDataListener<ArrayList<Event>> listener;

    public GetEventListUseCase(EventRepository repository, OnGetDataListener<ArrayList<Event>> listener) {
        this.repository = repository;
        this.listener = listener;
    }

    public void execute(){
        repository.getEventList(listener);
    }
}
