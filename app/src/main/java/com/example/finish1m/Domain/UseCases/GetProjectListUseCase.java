package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.LocateRepository;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;
import com.example.finish1m.Domain.Models.Locate;
import com.example.finish1m.Domain.Models.Project;

import java.util.ArrayList;

public class GetProjectListUseCase {

    private ProjectRepository repository;
    private OnGetDataListener<ArrayList<Project>> listener;

    public GetProjectListUseCase(ProjectRepository repository, OnGetDataListener<ArrayList<Project>> listener) {
        this.repository = repository;
        this.listener = listener;
    }

    public void execute(){
        repository.getProjectList(listener);
    }
}
