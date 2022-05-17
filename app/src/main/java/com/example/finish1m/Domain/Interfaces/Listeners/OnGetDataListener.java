package com.example.finish1m.Domain.Interfaces.Listeners;

public interface OnGetDataListener<T> {
    void onGetData(T data); // данные получены
    void onVoidData(); // данные отсутствуют
    void onFailed(); // ошибка
    void onCanceled(); // доступ запрещен
}
