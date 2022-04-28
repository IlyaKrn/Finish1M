package com.example.finish1m.Domain.Interfaces;

import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;

public interface AuthRepository {

    void EnterWithEmailAndPassword(String email, String password, OnSetDataListener listener);
    void RegisterWithEmailAndPassword(String email, String password, OnSetDataListener listener);
    void sendVerificationEmail(String email, OnSetDataListener listener);
    void ResetPassword(String email, OnSetDataListener listener);

    boolean isCurrentUserVerified();
}
