package com.example.finish1m.Domain.UseCases;

import android.graphics.Bitmap;

import com.example.finish1m.Domain.Interfaces.ImageRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetImageListener;
import com.example.finish1m.Domain.Interfaces.LocateRepository;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;
import com.example.finish1m.Domain.Models.Locate;
import com.example.finish1m.Domain.Models.Project;

import java.util.ArrayList;

public class RefactorProjectUseCase {

    private ProjectRepository repository;
    private ImageRepository imageRepository;
    private Project project;
    private ArrayList<Bitmap> images;
    private OnSetDataListener listener;

    public RefactorProjectUseCase(ProjectRepository repository, Project project, OnSetDataListener listener) {
        this.repository = repository;
        this.project = project;
        this.listener = listener;
        this.images = null;
        this.imageRepository = null;
    }

    public RefactorProjectUseCase(ProjectRepository repository, ImageRepository imageRepository, Project project, ArrayList<Bitmap> images, OnSetDataListener listener) {
        this.repository = repository;
        this.imageRepository = imageRepository;
        this.project = project;
        this.images = images;
        this.listener = listener;
    }

    public void execute(){
        if(images != null && images.size() > 0){
            final int[] count = {0};
            for(Bitmap b : images){
                imageRepository.setImage(b, new OnSetImageListener() {
                    @Override
                    public void onSetData(String ref) {
                        project.getImageRefs().add(ref);
                        if(count[0] == images.size()){
                            repository.setProject(project.getId(), project, listener);
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

        }
        else {
            repository.setProject(project.getId(), project, listener);
        }
    }
}
