package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.ChatRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Models.Chat;

public class GetChatByIdUseCase {

    private ChatRepository repository;
    private String id;
    private OnGetDataListener<Chat> listener;

    public GetChatByIdUseCase(ChatRepository repository, String id, OnGetDataListener<Chat> listener) {
        this.repository = repository;
        this.id = id;
        this.listener = listener;
    }

    public void execute(){
        repository.getChatById(id, listener);
    }

}
