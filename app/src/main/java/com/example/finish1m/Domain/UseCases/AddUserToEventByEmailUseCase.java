package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Event;

import java.util.ArrayList;

public class AddUserToEventByEmailUseCase {

    private EventRepository repository;;
    private Event event;
    private String userEmail;
    OnSetDataListener listener;

    public AddUserToEventByEmailUseCase(EventRepository repository, Event event, String userEmail, OnSetDataListener listener) {
        this.repository = repository;
        this.event = event;
        this.userEmail = userEmail;
        this.listener = listener;
    }

    public void execute(){
        if (event.getMembers() == null) {
            event.setMembers(new ArrayList<>());
        }
        event.getMembers().add(userEmail);
        repository.setEvent(event.getId(), event, listener);
    }

}
