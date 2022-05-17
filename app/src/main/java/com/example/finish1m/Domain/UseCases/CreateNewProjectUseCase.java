package com.example.finish1m.Domain.UseCases;

import android.graphics.Bitmap;

import com.example.finish1m.Data.Firebase.ChatRepositoryImpl;
import com.example.finish1m.Data.Firebase.ImageRepositoryImpl;
import com.example.finish1m.Data.Firebase.ProjectRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.ChatRepository;
import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.ImageRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetImageListener;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;
import com.example.finish1m.Domain.Models.Chat;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Project;

import java.util.ArrayList;

// создание проекта

public class CreateNewProjectUseCase {

    private ProjectRepository projectRepository;
    private ChatRepository chatRepository;
    private ImageRepository imageRepository;
    private Project project;
    private Chat chat;
    private ArrayList<Bitmap> images;
    private OnSetDataListener listener;

    public CreateNewProjectUseCase(ProjectRepository projectRepository, ChatRepository chatRepository, ImageRepository imageRepository, Project project, Chat chat, ArrayList<Bitmap> images, OnSetDataListener listener) {
        this.projectRepository = projectRepository;
        this.chatRepository = chatRepository;
        this.imageRepository = imageRepository;
        this.project = project;
        this.chat = chat;
        this.images = images;
        this.listener = listener;
    }


    public void execute(){
        // создание чата
        chatRepository.setChat(chat.getId(), chat, new OnSetDataListener() {
            @Override
            public void onSetData() {
                // загрузка картинок
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
                                    project.setImageRefs(imageRefs);
                                    // запись
                                    projectRepository.setProject(project.getId(), project, listener);
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
                else{
                    project.setImageRefs(imageRefs);
                    // запись
                    projectRepository.setProject(project.getId(), project, listener);
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
