package com.example.finish1m.Domain.UseCases;

import android.widget.Toast;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.VKRepository;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Presentation.PresentationConfig;
import com.example.finish1m.R;

import java.util.ArrayList;
import java.util.Comparator;

public class GetEventsByUserIdUseCase {

    private EventRepository eventRepository;
    private String userId;
    private OnGetDataListener<ArrayList<Event>> listener;

    public GetEventsByUserIdUseCase(EventRepository eventRepository, String userId, OnGetDataListener<ArrayList<Event>> listener) {
        this.eventRepository = eventRepository;
        this.userId = userId;
        this.listener = listener;
    }

    public void execute(){
        eventRepository.getEventList(new OnGetDataListener<ArrayList<Event>>() {
            @Override
            public void onGetData(ArrayList<Event> data) {
                ArrayList<Event> temp = new ArrayList<>();
                for(Event e : data){
                    if(e.getMembers() != null) {
                        for (String s : e.getMembers()) {
                            if (s.equals(userId)) {
                                temp.add(e);
                                break;
                            }
                        }
                    }
                }
                if(temp.size() > 0){
                    listener.onGetData(temp);
                }
                else{
                    listener.onVoidData();
                }

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
