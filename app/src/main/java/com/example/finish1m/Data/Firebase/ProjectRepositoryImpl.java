package com.example.finish1m.Data.Firebase;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;
import com.example.finish1m.Domain.Models.Project;

import java.util.ArrayList;

public class ProjectRepositoryImpl implements ProjectRepository {
    @Override
    public void getProjectList(OnGetDataListener<ArrayList<Project>> listener) {

    }

    @Override
    public void getProjectById(String eventId, OnGetDataListener<Project> listener) {

    }

    @Override
    public void setProject(Project project, OnSetDataListener listener) {

    }
}
