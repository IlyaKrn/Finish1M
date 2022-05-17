package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Project;

// получение проекта

public class GetProjectByIdUseCase {

    private ProjectRepository repository;
    private String id;
    private OnGetDataListener<Project> listener;

    public GetProjectByIdUseCase(ProjectRepository repository, String id, OnGetDataListener<Project> listener) {
        this.repository = repository;
        this.id = id;
        this.listener = listener;
    }

    public void execute(){
        repository.getProjectById(id, listener);
    }
}
