package com.example.finish1m.Domain.Interfaces.Listeners;

public interface OnSetImageListener {
    void onSetData(String ref); // изображение загружено по ссылке ref
    void onFailed(); // ошибка
    void onCanceled(); // доступ запрещен
}
