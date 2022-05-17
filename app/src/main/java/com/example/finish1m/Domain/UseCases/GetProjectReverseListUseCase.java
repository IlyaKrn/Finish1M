package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.LocateRepository;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Locate;
import com.example.finish1m.Domain.Models.Project;

import java.util.ArrayList;

public class GetProjectReverseListUseCase {

    private ProjectRepository repository;
    private OnGetDataListener<ArrayList<Project>> listener;

    public GetProjectReverseListUseCase(ProjectRepository repository, OnGetDataListener<ArrayList<Project>> listener) {
        this.repository = repository;
        this.listener = listener;
    }

    public void execute(){
        repository.getProjectList(new OnGetDataListener<ArrayList<Project>>() {
            @Override
            public void onGetData(ArrayList<Project> data) {
                ArrayList<Project> temp = new ArrayList<>();
                for (int i = data.size()-2; i >= 0; i--) {
                    temp.add(data.get(i));
                }
                listener.onGetData(temp);
            }

            @Override
            public void onVoidData() {
                listener.onVoidData();
            }

            @Override
            public void onFailed() {
                listener.onFailed();
            }

            @Override
            public void onCanceled() {
                listener.onCanceled();
            }
        });
    }
}
