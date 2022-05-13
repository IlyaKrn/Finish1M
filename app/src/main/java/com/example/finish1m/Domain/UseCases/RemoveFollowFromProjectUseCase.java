package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;
import com.example.finish1m.Domain.Models.Follow;
import com.example.finish1m.Domain.Models.Project;

public class RemoveFollowFromProjectUseCase {

    private ProjectRepository projectRepository;
    private String projectId;
    private String followId;
    private OnSetDataListener listener;

    public RemoveFollowFromProjectUseCase(ProjectRepository projectRepository, String projectId, String followId, OnSetDataListener listener) {
        this.projectRepository = projectRepository;
        this.projectId = projectId;
        this.followId = followId;
        this.listener = listener;
    }

    public void execute(){
        projectRepository.getProjectById(projectId, new OnGetDataListener<Project>() {
            @Override
            public void onGetData(Project data) {
                if(data.getFollows() != null) {
                    for (Follow f : data.getFollows()) {
                        if(f.getId().equals(followId)){
                            data.getFollows().remove(f);
                            projectRepository.setProject(data.getId(), data, listener);
                            break;
                        }
                    }
                }
                else
                    listener.onSetData();
            }

            @Override
            public void onVoidData() {
                listener.onCanceled();
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
