package com.example.finish1m.Domain.UseCases;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.finish1m.Domain.Interfaces.ChatRepository;
import com.example.finish1m.Domain.Interfaces.ImageRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetImageListener;
import com.example.finish1m.Domain.Models.Chat;
import com.example.finish1m.Domain.Models.Message;

import java.util.ArrayList;

public class CreateNewMessageUseCase {

    private ChatRepository chatRepository;
    private ImageRepository imageRepository;
    private String id;
    private Message message;
    private ArrayList<Bitmap> images;
    private OnSetDataListener listener;
    private boolean isAdd;

    public CreateNewMessageUseCase(ChatRepository chatRepository, ImageRepository imageRepository, String id, Message message, ArrayList<Bitmap> images, OnSetDataListener listener) {
        this.chatRepository = chatRepository;
        this.imageRepository = imageRepository;
        this.id = id;
        this.message = message;
        this.images = images;
        this.listener = listener;
        this.isAdd = true;
    }

    public void execute(){
        isAdd = true;
        chatRepository.getChatById(id, new OnGetDataListener<Chat>() {
            @Override
            public void onGetData(Chat data) {
                if(isAdd) {
                    isAdd = false;
                    if (images.size() > 0) {
                        final int[] count = {0};
                        message.setImageRefs(new ArrayList<>());
                        for (Bitmap b : images) {
                            imageRepository.setImage(b, new OnSetImageListener() {
                                @Override
                                public void onSetData(String ref) {
                                    count[0]++;
                                    message.getImageRefs().add(ref);
                                    if (count[0] == images.size()) {
                                        data.getMessages().add(message);
                                        chatRepository.setChat(id, data, listener);
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
                        message.setImageRefs(new ArrayList<>());
                        data.getMessages().add(message);
                        chatRepository.setChat(id, data, listener);
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
