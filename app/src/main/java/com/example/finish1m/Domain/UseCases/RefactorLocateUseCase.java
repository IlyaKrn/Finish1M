package com.example.finish1m.Domain.UseCases;

import android.graphics.Bitmap;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.ImageRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetImageListener;
import com.example.finish1m.Domain.Interfaces.LocateRepository;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Locate;

import java.util.ArrayList;

public class RefactorLocateUseCase {


    private LocateRepository repository;
    private ImageRepository imageRepository;
    private Locate locate;
    private ArrayList<Bitmap> images;
    private OnSetDataListener listener;

    public RefactorLocateUseCase(LocateRepository repository, Locate locate, OnSetDataListener listener) {
        this.repository = repository;
        this.locate = locate;
        this.listener = listener;
        this.images = null;
        this.imageRepository = null;
    }

    public RefactorLocateUseCase(LocateRepository repository, ImageRepository imageRepository, Locate locate, ArrayList<Bitmap> images, OnSetDataListener listener) {
        this.repository = repository;
        this.imageRepository = imageRepository;
        this.locate = locate;
        this.images = images;
        this.listener = listener;
    }

    public void execute(){
        if(images != null && images.size() > 0) {
            final int[] count = {0};
            for (Bitmap b : images) {
                imageRepository.setImage(b, new OnSetImageListener() {
                    @Override
                    public void onSetData(String ref) {
                        count[0]++;
                        locate.getImageRefs().add(ref);
                        if (count[0] == images.size()) {
                            repository.setLocate(locate.getId(), locate, listener);
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
            repository.setLocate(locate.getId(), locate, listener);
        }
    }
}
