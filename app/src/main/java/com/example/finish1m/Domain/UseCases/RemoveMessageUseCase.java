package com.example.finish1m.Domain.UseCases;

import android.graphics.Bitmap;

import com.example.finish1m.Domain.Interfaces.ChatRepository;
import com.example.finish1m.Domain.Interfaces.ImageRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetImageListener;
import com.example.finish1m.Domain.Models.Chat;
import com.example.finish1m.Domain.Models.Message;

import java.util.ArrayList;

public class RemoveMessageUseCase {

    private ChatRepository chatRepository;
    private String id;
    private int messageId;
    private OnSetDataListener listener;
    private boolean isAdd;

    public RemoveMessageUseCase(ChatRepository chatRepository, String id, int messageId, OnSetDataListener listener) {
        this.chatRepository = chatRepository;
        this.id = id;
        this.messageId = messageId;
        this.listener = listener;
    }

    public void execute(){
        // isAdd нужен чтобы при записи данных в чат код при обновлении данных не выполнялся заново
        // можно предавать не ссылку на чат, а объект чата, чтобы збежать использования isAdd
        isAdd = true;
        // получение чата
        if(messageId != 0) {
            chatRepository.getChatById(id, new OnGetDataListener<Chat>() {
                @Override
                public void onGetData(Chat data) {
                    if (isAdd) {
                        isAdd = false;
                        // запись
                        data.getMessages().remove(messageId);
                        chatRepository.setChat(id, data, listener);
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
        else {
            listener.onCanceled();
        }

    }

}
