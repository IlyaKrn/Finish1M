package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.LocateRepository;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;
import com.example.finish1m.Domain.Models.Locate;
import com.example.finish1m.Domain.Models.Project;

public class GetLocateByIdUseCase {

    private LocateRepository repository;
    private String id;
    private OnGetDataListener<Locate> listener;

    public GetLocateByIdUseCase(LocateRepository repository, String id, OnGetDataListener<Locate> listener) {
        this.repository = repository;
        this.id = id;
        this.listener = listener;
    }

    public void execute(){
        repository.getLocateById(id, listener);
    }
}
