package com.example.finish1m.Domain.Interfaces;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Project;

import java.util.ArrayList;

public interface ProjectRepository {
    void getProjectList(OnGetDataListener<ArrayList<Project>> listener);    // получение списка проектов
    void getProjectById(String eventId, OnGetDataListener<Project> listener);    // получение проекта по id
    void setProject(String id, Project project, OnSetDataListener listener);    // запись данных в проект по id
    String getNewId();    // получение нового id
}
