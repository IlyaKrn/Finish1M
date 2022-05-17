package com.example.finish1m.Domain.UseCases;

import android.graphics.Bitmap;

import com.example.finish1m.Domain.Interfaces.ImageRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetImageListener;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;
import com.example.finish1m.Domain.Models.Follow;
import com.example.finish1m.Domain.Models.Project;

import java.util.ArrayList;

// создание заявки для проекта

public class CreateNewFollowUseCase {

    private ProjectRepository projectRepository;
    private ImageRepository imageRepository;
    private String projectId;
    private Follow follow;
    private ArrayList<Bitmap> images;
    private OnSetDataListener listener;

    public CreateNewFollowUseCase(ProjectRepository projectRepository, ImageRepository imageRepository, String projectId, Follow follow, ArrayList<Bitmap> images, OnSetDataListener listener) {
        this.projectRepository = projectRepository;
        this.imageRepository = imageRepository;
        this.projectId = projectId;
        this.follow = follow;
        this.images = images;
        this.listener = listener;
    }

    public void execute(){
        final boolean[] isAdd = {true};
        projectRepository.getProjectById(projectId, new OnGetDataListener<Project>() {
            @Override
            public void onGetData(Project data) {
                // загрузка картинок
                if(isAdd[0]) {
                    isAdd[0] = false;
                    final int[] count = {0};
                    ArrayList<String> imageRefs = new ArrayList<>();
                    if (images.size() > 0) {
                        for (int i = 0; i < images.size(); i++) {
                            imageRepository.setImage(images.get(i), new OnSetImageListener() {
                                @Override
                                public void onSetData(String ref) {
                                    count[0]++;
                                    imageRefs.add(ref);
                                    if (count[0] == images.size()) {
                                        follow.setImageRefs(imageRefs);
                                        if (data.getFollows() == null)
                                            data.setFollows(new ArrayList<>());
                                        data.getFollows().add(follow);
                                        projectRepository.setProject(data.getId(), data, listener);
                                    }
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
                    } else {
                        follow.setImageRefs(imageRefs);
                        if (data.getFollows() == null)
                            data.setFollows(new ArrayList<>());
                        data.getFollows().add(follow);
                        projectRepository.setProject(data.getId(), data, listener);
                    }
                }
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
