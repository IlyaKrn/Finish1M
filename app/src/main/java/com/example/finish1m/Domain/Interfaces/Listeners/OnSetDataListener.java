package com.example.finish1m.Domain.Interfaces.Listeners;

public interface OnSetDataListener {
    void onSetData(); // данные записаны
    void onFailed(); // ошибка
    void onCanceled(); // доступ запрещен
}
