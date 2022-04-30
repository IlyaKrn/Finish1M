package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.LocateRepository;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;
import com.example.finish1m.Domain.Models.Locate;
import com.example.finish1m.Domain.Models.Project;

public class RefactorProjectUseCase {

    private ProjectRepository repository;
    private Project project;
    private OnSetDataListener listener;

    public RefactorProjectUseCase(ProjectRepository repository, Project project, OnSetDataListener listener) {
        this.repository = repository;
        this.project = project;
        this.listener = listener;
    }

    public void execute(){
        repository.setProject(project.getId(), project, listener);
    }
}
