package com.example.finish1m.Domain.Interfaces;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Chat;

public interface ChatRepository {

    void getChatById(String chatId, OnGetDataListener<Chat> listener);    // получение чата по id
    void setChat(String id, Chat chat, OnSetDataListener listener);    // запись данных в чат по id
    String getNewId();    // получение нового id

}
