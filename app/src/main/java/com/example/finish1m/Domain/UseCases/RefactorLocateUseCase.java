package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.LocateRepository;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Locate;

public class RefactorLocateUseCase {


    private LocateRepository repository;
    private Locate locate;
    private OnSetDataListener listener;

    public RefactorLocateUseCase(LocateRepository repository, Locate locate, OnSetDataListener listener) {
        this.repository = repository;
        this.locate = locate;
        this.listener = listener;
    }

    public void execute(){
        repository.setLocate(locate, listener);
    }
}
