package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Event;

public class RefactorEventUseCase {

    private EventRepository repository;
    private Event event;
    private OnSetDataListener listener;

    public RefactorEventUseCase(EventRepository repository, Event event, OnSetDataListener listener) {
        this.repository = repository;
        this.event = event;
        this.listener = listener;
    }

    public void execute(){
        repository.setEvent(event.getId(), event, listener);
    }

}
