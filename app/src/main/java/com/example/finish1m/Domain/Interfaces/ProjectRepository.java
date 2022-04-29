package com.example.finish1m.Domain.Interfaces;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Project;

import java.util.ArrayList;

public interface ProjectRepository {
    void getProjectList(OnGetDataListener<ArrayList<Project>> listener);
    void getProjectById(String eventId, OnGetDataListener<Project> listener);
    void setProject(Project project, OnSetDataListener listener);
    String getNewId();
}
