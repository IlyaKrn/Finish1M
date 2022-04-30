package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;

public class DeleteProjectByIdUseCase {

    private ProjectRepository repository;
    private String id;
    private OnSetDataListener listener;

    public DeleteProjectByIdUseCase(ProjectRepository repository, String id, OnSetDataListener listener) {
        this.repository = repository;
        this.id = id;
        this.listener = listener;
    }

    public void execute(){
        repository.setProject(id, null, listener);
    }
}
