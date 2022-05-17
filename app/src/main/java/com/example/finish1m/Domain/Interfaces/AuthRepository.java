package com.example.finish1m.Domain.Interfaces;

import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;

public interface AuthRepository {

    void EnterWithEmailAndPassword(String email, String password, OnSetDataListener listener);    // вход с почтой и паролем
    void RegisterWithEmailAndPassword(String email, String password, OnSetDataListener listener);    // регистрация с почтой и паролем
    void sendVerificationEmail(String email, OnSetDataListener listener);// отправка письма для верификации
    void ResetPassword(String email, OnSetDataListener listener);    // отправка письма для сброса пароля
    boolean isCurrentUserVerified();    // проверка верификации текущего пользователя
}
