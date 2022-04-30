package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;

public class DeleteEventByIdUseCase {

    private EventRepository repository;
    private String id;
    private OnSetDataListener listener;

    public DeleteEventByIdUseCase(EventRepository repository, String id, OnSetDataListener listener) {
        this.repository = repository;
        this.id = id;
        this.listener = listener;
    }

    public void execute(){
        repository.setEvent(id, null, listener);
    }
}
