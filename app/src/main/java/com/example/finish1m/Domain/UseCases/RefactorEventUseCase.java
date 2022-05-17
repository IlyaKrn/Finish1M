package com.example.finish1m.Domain.UseCases;

import android.graphics.Bitmap;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.ImageRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetImageListener;
import com.example.finish1m.Domain.Models.Event;

import java.util.ArrayList;

// изменение события

public class RefactorEventUseCase {

    private EventRepository repository;
    private ImageRepository imageRepository;
    private Event event;
    private ArrayList<Bitmap> images;
    private OnSetDataListener listener;

    public RefactorEventUseCase(EventRepository repository, Event event, OnSetDataListener listener) {
        this.repository = repository;
        this.event = event;
        this.listener = listener;
        this.images = null;
        this.imageRepository = null;
    }

    public RefactorEventUseCase(EventRepository repository, ImageRepository imageRepository, Event event, ArrayList<Bitmap> images, OnSetDataListener listener) {
        this.repository = repository;
        this.imageRepository = imageRepository;
        this.event = event;
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
                        count[0]++;
                        event.getImageRefs().add(ref);
                        if(count[0] == images.size()){
                            repository.setEvent(event.getId(), event, listener);
                        }
                    }

                    @Override
                    public void onFailed() {
                        count[0]++;
                        listener.onFailed();
                    }

                    @Override
                    public void onCanceled() {
                        count[0]++;
                        listener.onCanceled();
                    }
                });
            }

        }
        else {
            repository.setEvent(event.getId(), event, listener);
        }
    }

}
