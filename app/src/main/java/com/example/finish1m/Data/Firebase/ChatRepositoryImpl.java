package com.example.finish1m.Data.Firebase;

import static com.example.finish1m.Data.Firebase.FirebaseConfig.DATABASE_LOCATE;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.finish1m.Domain.Interfaces.ChatRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Chat;
import com.example.finish1m.Domain.Models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatRepositoryImpl implements ChatRepository {

    private Context context;

    public ChatRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getChatById(String chatId, OnGetDataListener<Chat> listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_LOCATE).child(chatId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (context != null) {
                        Chat l = snapshot.getValue(Chat.class);
                        if (l != null) {
                            listener.onGetData(l);
                        }
                        else {
                            listener.onVoidData();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    if (context != null) {
                        listener.onCanceled();
                    }
                }
            });
        } catch (Exception e){
            listener.onFailed();
        }

    }

    @Override
    public void setChat(Chat chat, OnSetDataListener listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_LOCATE).child(chat.getId()).setValue(chat).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (context != null) {
                        if(task.isSuccessful())
                            listener.onSetData();
                        else if (task.isCanceled())
                            listener.onCanceled();
                        else
                            listener.onFailed();
                    }
                }
            });
        } catch (Exception e){
            listener.onFailed();
        }
    }
}
