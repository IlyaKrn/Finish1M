package com.example.finish1m.Data.Firebase;

import static com.example.finish1m.Data.Firebase.Database.FirebaseConfig.DATABASE_CHAT;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.finish1m.Domain.Interfaces.ChatRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Chat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatRepositoryImpl implements ChatRepository {

    private Context context;
    private static final String LOG_TAG = "ChatRepositoryImpl";

    public ChatRepositoryImpl(Context context) {
        this.context = context;
    }

    // получение чата по id
    @Override
    public void getChatById(String chatId, OnGetDataListener<Chat> listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_CHAT).child(chatId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (context != null) {
                        Chat l = snapshot.getValue(Chat.class);
                        if (l != null) {
                            Log.d(LOG_TAG, String.format("get chat is success (id='%s')", chatId));
                            listener.onGetData(l);
                        }
                        else {
                            Log.e(LOG_TAG, String.format("get chat is void data (id='%s': no data in database)", chatId));
                            listener.onVoidData();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    if (context != null) {
                        Log.e(LOG_TAG, String.format("get chat is cancelled (id='%s'): &s", chatId, error.getMessage()));
                        listener.onCanceled();
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("get chat is failed (id='%s'): &s", chatId, e.getMessage()));
            listener.onFailed();
        }

    }

    // запись данных в чат по id
    @Override
    public void setChat(String id, Chat chat, OnSetDataListener listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_CHAT).child(id).setValue(chat).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (context != null) {
                        if(task.isSuccessful()) {
                            Log.d(LOG_TAG, String.format("write chat is success (id='%s')", id));
                            listener.onSetData();
                        }
                        else if (task.isCanceled()) {
                            Log.e(LOG_TAG, String.format("write chat is cancelled (id='%s'): &s", id, task.getException().getMessage()));
                            listener.onCanceled();
                        }
                        else {
                            Log.e(LOG_TAG, String.format("write chat is failed (id='%s'): &s", id, task.getException().getMessage()));
                            listener.onFailed();
                        }
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("write chat is failed (id='%s'): &s", id, e.getMessage()));
            listener.onFailed();
        }
    }

    // получение нового id
    @Override
    public String getNewId() {
        return FirebaseDatabase.getInstance().getReference(DATABASE_CHAT).push().getKey();
    }
}
