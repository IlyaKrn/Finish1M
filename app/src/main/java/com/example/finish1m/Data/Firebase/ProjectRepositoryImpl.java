package com.example.finish1m.Data.Firebase;

import android.content.Context;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;
import com.example.finish1m.Domain.Models.Project;

import java.util.ArrayList;

public class ProjectRepositoryImpl implements ProjectRepository {

    private Context context;

    public ProjectRepositoryImpl(Context context) {
        this.context = context;
    }

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
