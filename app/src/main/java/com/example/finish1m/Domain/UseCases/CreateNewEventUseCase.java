package com.example.finish1m.Domain.UseCases;

import android.graphics.Bitmap;

import com.example.finish1m.Domain.Interfaces.ChatRepository;
import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.ImageRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetImageListener;
import com.example.finish1m.Domain.Models.Chat;
import com.example.finish1m.Domain.Models.Event;

import java.util.ArrayList;

public class CreateNewEventUseCase {

    private EventRepository eventRepository;
    private ChatRepository chatRepository;
    private ImageRepository imageRepository;
    private Event event;
    private Chat chat;
    private ArrayList<Bitmap> images;
    private OnSetDataListener listener;

    public CreateNewEventUseCase(EventRepository eventRepository, ChatRepository chatRepository, ImageRepository imageRepository, Event event, Chat chat, ArrayList<Bitmap> images, OnSetDataListener listener) {
        this.eventRepository = eventRepository;
        this.chatRepository = chatRepository;
        this.imageRepository = imageRepository;
        this.event = event;
        this.chat = chat;
        this.images = images;
        this.listener = listener;
    }

    public void execute(){
        chatRepository.setChat(chat.getId(), chat, new OnSetDataListener() {
            @Override
            public void onSetData() {
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
                                    event.setImageRefs(imageRefs);
                                    eventRepository.setEvent(event.getId(), event, listener);
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
                    event.setImageRefs(imageRefs);
                    eventRepository.setEvent(event.getId(), event, listener);
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
