package com.example.finish1m.Data.Firebase;

import static com.example.finish1m.Data.Firebase.Database.FirebaseConfig.DATABASE_PROJECT;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.finish1m.Data.Firebase.Database.FirebaseConfig;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;
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
    private static final String LOG_TAG = "ProjectRepositoryImpl";

    public ProjectRepositoryImpl(Context context) {
        this.context = context;
    }

    // получение списка проектов
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
                        if(temp.size() > 0) {
                            Log.d(LOG_TAG, String.format("get project list is success (size='%d')", temp.size()));
                            listener.onGetData(temp);
                        }
                        else{
                            Log.e(LOG_TAG, String.format("get project list is void data (size='%d': no data in database)", 0));
                            listener.onVoidData();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    if (context != null) {
                        Log.e(LOG_TAG, String.format("get project list is cancelled (size='%d'): &s", 0, error.getMessage()));
                        listener.onCanceled();
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("get project list is failed (size='%d'): &s", 0, e.getMessage()));
            listener.onFailed();
        }
    }

    // получение проекта по id
    @Override
    public void getProjectById(String eventId, OnGetDataListener<Project> listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_PROJECT).child(eventId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (context != null) {
                        Project l = snapshot.getValue(Project.class);
                        if (l != null) {
                            Log.d(LOG_TAG, String.format("get project is success (id='%s')", eventId));
                            listener.onGetData(l);
                        }
                        else {
                            Log.e(LOG_TAG, String.format("get project is void data (id='%s': no data in database)", eventId));
                            listener.onVoidData();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    if (context != null) {
                        Log.e(LOG_TAG, String.format("get project is cancelled (id='%s'): &s", eventId, error.getMessage()));
                        listener.onCanceled();
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("get project is failed (id='%s'): &s", eventId, e.getMessage()));
            listener.onFailed();
        }
    }

    // запись данных в проект по id
    @Override
    public void setProject(String id, Project project, OnSetDataListener listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_PROJECT).child(id).setValue(project).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (context != null) {
                        if(task.isSuccessful()) {
                            Log.d(LOG_TAG, String.format("write project is success (id='%s')", id));
                            listener.onSetData();
                        }
                        else {
                            Log.e(LOG_TAG, String.format("write project is cancelled (id='%s'): &s", id, task.getException().getMessage()));
                            listener.onCanceled();
                        }
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("write project is failed (id='%s'): &s", id, e.getMessage()));
            listener.onFailed();
        }
    }

    // получение нового id
    @Override
    public String getNewId() {
        return FirebaseDatabase.getInstance().getReference(FirebaseConfig.DATABASE_PROJECT).push().getKey();
    }
}
