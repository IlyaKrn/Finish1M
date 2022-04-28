package com.example.finish1m.Data.Firebase;

import static com.example.finish1m.Data.Firebase.FirebaseConfig.DATABASE_USER;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.UserRepository;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserRepositoryImpl implements UserRepository {

    private static final String PATH_POINT = "point";
    private Context context;

    public UserRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getUserList(OnGetDataListener<ArrayList<User>> listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_USER).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (context != null) {

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
    public void getUserByEmail(String email, OnGetDataListener<User> listener) {
        try {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(DATABASE_USER);
            for (Character c : email.toCharArray()){
                String s = String.valueOf(c);
                if (s.equals("."))
                    s = PATH_POINT;
                userRef = userRef.child(s);
            }

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (context != null) {
                        User c = snapshot.getValue(User.class);
                        assert c != null;
                        listener.onGetData(c);
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
    public void setUser(User user, OnSetDataListener listener) {
        try {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(DATABASE_USER);
            for (Character c : user.getEmail().toCharArray()){
                String s = String.valueOf(c);
                if (s.equals("."))
                    s = PATH_POINT;
                userRef = userRef.child(s);
            }

            userRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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
