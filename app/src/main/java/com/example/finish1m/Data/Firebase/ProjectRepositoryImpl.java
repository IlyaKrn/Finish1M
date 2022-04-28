package com.example.finish1m.Data.Firebase;

import static com.example.finish1m.Data.Firebase.FirebaseConfig.DATABASE_EVENT;
import static com.example.finish1m.Data.Firebase.FirebaseConfig.DATABASE_LOCATE;
import static com.example.finish1m.Data.Firebase.FirebaseConfig.DATABASE_PROJECT;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Project;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProjectRepositoryImpl implements ProjectRepository {

    private Context context;

    public ProjectRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getProjectList(OnGetDataListener<ArrayList<Project>> listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_PROJECT).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Project> temp = new ArrayList<>();
                    if (context != null) {
                        for (DataSnapshot s : snapshot.getChildren()){
                            Project l = s.getValue(Project.class);
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
    public void getProjectById(String eventId, OnGetDataListener<Project> listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_PROJECT).child(eventId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (context != null) {
                        Project l = snapshot.getValue(Project.class);
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
    public void setProject(Project project, OnSetDataListener listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_LOCATE).child(project.getId()).setValue(project).addOnCompleteListener(new OnCompleteListener<Void>() {
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
