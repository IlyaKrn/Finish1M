package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.ChatRepository;
import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Chat;
import com.example.finish1m.Domain.Models.Event;

public class CreateNewEventUseCase {

    private EventRepository eventRepository;
    private ChatRepository chatRepository;
    private Event event;
    private Chat chat;
    private OnSetDataListener listener;

    public CreateNewEventUseCase(EventRepository eventRepository, ChatRepository chatRepository, Event event, Chat chat, OnSetDataListener listener) {
        this.eventRepository = eventRepository;
        this.chatRepository = chatRepository;
        this.event = event;
        this.chat = chat;
        this.listener = listener;
    }

    public void execute(){
        chatRepository.setChat(chat, new OnSetDataListener() {
            @Override
            public void onSetData() {
                eventRepository.setEvent(event, listener);
            }

            @Override
            public void onFailed() {
                listener.onFailed();
            }

            @Override
            public void onCanceled() {
                listener.onCanceled();
            }
        });
    }
}
