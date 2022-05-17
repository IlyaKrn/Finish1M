package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.LocateRepository;

// удаление метки


public class DeleteLocateByIdUseCase {

    private LocateRepository repository;
    private String id;
    private OnSetDataListener listener;

    public DeleteLocateByIdUseCase(LocateRepository repository, String id, OnSetDataListener listener) {
        this.repository = repository;
        this.id = id;
        this.listener = listener;
    }

    public void execute(){
        repository.setLocate(id, null, listener);
    }
}
