package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Event;

import java.util.ArrayList;

// удаление заявки из мероприятия

public class RemoveUserFromEventUseCase {

    private EventRepository repository;;
    private Event event;
    private String userEmail;
    OnSetDataListener listener;

    public RemoveUserFromEventUseCase(EventRepository repository, Event event, String userEmail, OnSetDataListener listener) {
        this.repository = repository;
        this.event = event;
        this.userEmail = userEmail;
        this.listener = listener;
    }

    public void execute(){
        if (event.getMembers() == null) {
            event.setMembers(new ArrayList<>());
        }
        event.getMembers().remove(userEmail);
        repository.setEvent(event.getId(), event, listener);
    }

}
