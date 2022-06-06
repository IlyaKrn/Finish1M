package com.example.finish1m.Data.Firebase;

import static com.example.finish1m.Data.Firebase.Database.FirebaseConfig.DATABASE_EVENT;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventRepositoryImpl implements EventRepository {

    private Context context;
    private static final String LOG_TAG = "EventRepositoryImpl";

    public EventRepositoryImpl(Context context) {
        this.context = context;
    }

    // получение списка событий
    @Override
    public void getEventList(OnGetDataListener<ArrayList<Event>> listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_EVENT).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Event> temp = new ArrayList<>();
                    if (context != null) {
                        for (DataSnapshot s : snapshot.getChildren()){
                            Event l = s.getValue(Event.class);
                            assert l != null;
                            temp.add(l);
                        }
                        if(temp.size() > 0) {
                            Log.d(LOG_TAG, String.format("get event list is success (size='%d')", temp.size()));
                            listener.onGetData(temp);
                        }
                        else{
                            Log.e(LOG_TAG, String.format("get event list is void data (size='%d': no data in database)", 0));
                            listener.onVoidData();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    if (context != null) {
                        Log.e(LOG_TAG, String.format("get event list is cancelled (size='%d'): %s", 0, error.getMessage()));
                        listener.onCanceled();
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("get event list is failed (size='%d'): %s", 0, e.getMessage()));
            listener.onFailed();
        }
    }

    // получение события по id
    @Override
    public void getEventById(String eventId, OnGetDataListener<Event> listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_EVENT).child(eventId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (context != null) {
                        Event l = snapshot.getValue(Event.class);
                        if (l != null) {
                            Log.d(LOG_TAG, String.format("get event is success (id='%s')", eventId));
                            listener.onGetData(l);
                        }
                        else {
                            Log.e(LOG_TAG, String.format("get event is void data (id='%s': no data in database)", eventId));
                            listener.onVoidData();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    if (context != null) {
                        Log.e(LOG_TAG, String.format("get event is cancelled (id='%s'): %s", eventId, error.getMessage()));
                        listener.onCanceled();
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("get event is failed (id='%s'): %s", eventId, e.getMessage()));
            listener.onFailed();
        }
    }

    // запись данных в событие по id
    @Override
    public void setEvent(String id, Event event, OnSetDataListener listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_EVENT).child(id).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (context != null) {
                        if(task.isSuccessful()) {
                            Log.d(LOG_TAG, String.format("write event is success (id='%s')", id));
                            listener.onSetData();
                        }
                        else {
                            Log.e(LOG_TAG, String.format("write event is cancelled (id='%s'): %s", id, task.getException().getMessage()));
                            listener.onCanceled();
                        }
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("write event is failed (id='%s'): %s", id, e.getMessage()));
            listener.onFailed();
        }
    }

    // получение нового id
    @Override
    public String getNewId() {
        return FirebaseDatabase.getInstance().getReference(DATABASE_EVENT).push().getKey();
    }
}
