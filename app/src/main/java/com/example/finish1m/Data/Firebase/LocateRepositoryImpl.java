package com.example.finish1m.Data.Firebase;

import static com.example.finish1m.Data.Firebase.FirebaseConfig.DATABASE_LOCATE;
import static com.example.finish1m.Data.Firebase.FirebaseConfig.DATABASE_USER;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.LocateRepository;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Locate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LocateRepositoryImpl implements LocateRepository {

    private Context context;

    public LocateRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getLocateList(OnGetDataListener<ArrayList<Locate>> listener) {
        FirebaseDatabase.getInstance().getReference(DATABASE_LOCATE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Locate> temp = new ArrayList<>();
                if (context != null) {
                    for (DataSnapshot s : snapshot.getChildren()){
                        Locate l = s.getValue(Locate.class);
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
                    listener.onFailed();
                }
            }
        });
    }

    @Override
    public void getLocateById(String locateId, OnGetDataListener<Locate> listener) {
        FirebaseDatabase.getInstance().getReference(DATABASE_LOCATE).child(locateId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (context != null) {
                    Locate l = snapshot.getValue(Locate.class);
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
    }

    @Override
    public void setLocate(Locate locate, OnSetDataListener listener) {
        FirebaseDatabase.getInstance().getReference(DATABASE_LOCATE).child(locate.getId()).setValue(locate).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    }
}
