package com.example.finish1m.Data.Firebase;

import static com.example.finish1m.Data.Firebase.FirebaseConfig.DATABASE_EVENT;
import static com.example.finish1m.Data.Firebase.FirebaseConfig.DATABASE_LOCATE;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Locate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventRepositoryImpl implements EventRepository {

    private Context context;

    public EventRepositoryImpl(Context context) {
        this.context = context;
    }

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
                    }
                    if(temp.size() > 0)
                        listener.onGetData(temp);
                    else
                        listener.onVoidData();
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
    public void getEventById(String eventId, OnGetDataListener<Event> listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_LOCATE).child(eventId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (context != null) {
                        Event l = snapshot.getValue(Event.class);
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
    public void setEvent(Event event, OnSetDataListener listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_LOCATE).child(event.getId()).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (context != null) {
                        if(task.isSuccessful())
                            listener.onSetData();
                        else
                            listener.onCanceled();
                    }
                }
            });
        } catch (Exception e){
            listener.onFailed();
        }
    }
}
