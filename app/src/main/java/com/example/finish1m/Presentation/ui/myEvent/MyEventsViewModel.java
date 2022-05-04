package com.example.finish1m.Presentation.ui.myEvent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyEventsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyEventsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}