package com.example.finish1m.Domain.Interfaces.Listeners;

public interface OnGetDataListener<T> {
    void onGetData(T data);
    void onVoidData();
    void onFailed();
    void onCanceled();
}
