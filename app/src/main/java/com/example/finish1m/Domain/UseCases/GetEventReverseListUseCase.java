package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.LocateRepository;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Locate;
import com.example.finish1m.Domain.Models.Project;

import java.util.ArrayList;

// получение перевернутого списка событий

public class GetEventReverseListUseCase {

    private EventRepository repository;
    private OnGetDataListener<ArrayList<Event>> listener;

    public GetEventReverseListUseCase(EventRepository repository, OnGetDataListener<ArrayList<Event>> listener) {
        this.repository = repository;
        this.listener = listener;
    }

    public void execute(){
        repository.getEventList(new OnGetDataListener<ArrayList<Event>>() {
            @Override
            public void onGetData(ArrayList<Event> data) {
                ArrayList<Event> temp = new ArrayList<>();
                for (int i = data.size()-1; i >= 0; i--) {
                    temp.add(data.get(i));
                }
                listener.onGetData(temp);
            }

            @Override
            public void onVoidData() {
                listener.onVoidData();
            }

            @Override
            public void onFailed() {
                listener.onFailed();
            }

            @Override
            public void onCanceled() {
                listener.onCanceled();
            }
        });
    }
}
