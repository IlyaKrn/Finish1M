package com.example.finish1m.Data.Firebase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.finish1m.Domain.Interfaces.AuthRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthRepositoryImpl implements AuthRepository {

    private Context context;
    private static final String LOG_TAG = "AuthRepositoryImpl";

    public AuthRepositoryImpl(Context context) {
        this.context = context;
    }

    // вход с почтой и паролем
    @Override
    public void EnterWithEmailAndPassword(String email, String password, OnSetDataListener listener) {
        try {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (context != null) {
                        if(task.isSuccessful()) {
                            Log.d(LOG_TAG, String.format("enter is success (email='%s', password='%s')", email, password));
                            listener.onSetData();
                        }
                        else{
                            Log.e(LOG_TAG, String.format("enter is cancelled (email='%s', password='%s'): %s", email, password, task.getException().getMessage()));
                            listener.onCanceled();
                        }
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("enter is failed (email='%s', password='%s'): %s", email, password, e.getMessage()));
            listener.onFailed();
        }
    }

    // регистрация с почтой и паролем
    @Override
    public void RegisterWithEmailAndPassword(String email, String password, OnSetDataListener listener) {
        try {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (context != null) {
                        if(task.isSuccessful()) {
                            Log.d(LOG_TAG, String.format("register is success (email='%s', password='%s')", email, password));
                            listener.onSetData();
                        }
                        else {
                            Log.e(LOG_TAG, String.format("enter is cancelled (email='%s', password='%s'): %s", email, password, task.getException().getMessage()));
                            listener.onCanceled();
                        }
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("enter is failed (email='%s', password='%s'): %s", email, password, e.getMessage()));
            listener.onFailed();
        }
    }

    // отправка письма для верификации
    @Override
    public void sendVerificationEmail(String email, OnSetDataListener listener) {
        try {
            FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (context != null) {
                        if(task.isSuccessful()) {
                            Log.d(LOG_TAG, String.format("verification email is success (email='%s')", email));
                            listener.onSetData();
                        }
                        else {
                            Log.e(LOG_TAG, String.format("verification email is cancelled (email='%s'): %s", email, task.getException().getMessage()));
                            listener.onCanceled();
                        }
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("verification email is failed (email='%s'", email, e.getMessage()));
            listener.onFailed();
        }
    }

    // отправка письма для сброса пароля
    @Override
    public void ResetPassword(String email, OnSetDataListener listener) {
        try {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (context != null) {
                        if(task.isSuccessful()) {
                            listener.onSetData();
                            Log.d(LOG_TAG, String.format("reset password email is success (email='%s')", email));
                        }
                        else {
                            Log.e(LOG_TAG, String.format("reset password email is cancelled (email='%s'): %s", email, task.getException().getMessage()));
                            listener.onCanceled();
                        }
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("reset password email is failed (email='%s'): %s", email, e.getMessage()));
            listener.onFailed();
        }
    }

    // проверка верификации текущего пользователя
    @Override
    public boolean isCurrentUserVerified() {
        try {
            return FirebaseAuth.getInstance().getCurrentUser().isEmailVerified();
        } catch (Exception e){
            return false;
        }
    }
}
