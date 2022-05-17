package com.example.finish1m.Domain.UseCases;

import android.util.Log;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.NotificationRepository;
import com.example.finish1m.Domain.Models.Event;

import java.util.ArrayList;

// отправка уведомлений о новах событиях

public class StartListeningEventsUseCase {

    private EventRepository eventRepository;
    private NotificationRepository notificationRepository;

    private ArrayList<Event> events = new ArrayList<>();

    public StartListeningEventsUseCase(EventRepository eventRepository, NotificationRepository notificationRepository) {
        this.eventRepository = eventRepository;
        this.notificationRepository = notificationRepository;
    }

    public void execute(){
        final boolean[] isFirst = {true};
        eventRepository.getEventList(new OnGetDataListener<ArrayList<Event>>() {
            @Override
            public void onGetData(ArrayList<Event> data) {
                if (isFirst[0]){
                    isFirst[0] = false;
                    events = data;
                    eventRepository.getEventList(new OnGetDataListener<ArrayList<Event>>() {
                        @Override
                        public void onGetData(ArrayList<Event> data){
                            for (Event e : data){
                                boolean isEquals = false;
                                for (Event e1 : events) {
                                    if (e1.getId().equals(e.getId())){
                                        isEquals = true;
                                    }
                                }
                                if (!isEquals){
                                    notificationRepository.sendNotification(e.getTitle(), e.getMessage());
                                }
                            }
                            events = data;
                        }

                        @Override
                        public void onVoidData() {
                            events.clear();
                        }

                        @Override
                        public void onFailed() {

                        }

                        @Override
                        public void onCanceled() {

                        }
                    });
                }
            }

            @Override
            public void onVoidData() {
                if (isFirst[0]){
                    isFirst[0] = false;
                    events.clear();
                    eventRepository.getEventList(new OnGetDataListener<ArrayList<Event>>() {
                        @Override
                        public void onGetData(ArrayList<Event> data){
                            for (Event e : data){
                                boolean isEquals = false;
                                for (Event e1 : events) {
                                    if (e1.getId().equals(e.getId())){
                                        isEquals = true;
                                    }
                                }
                                if (!isEquals){
                                    notificationRepository.sendNotification(e.getTitle(), e.getMessage());
                                    Log.e(";ojlihougliugi", "lioioih");
                                }
                            }
                            events = data;
                        }

                        @Override
                        public void onVoidData() {
                            events.clear();
                        }

                        @Override
                        public void onFailed() {

                        }

                        @Override
                        public void onCanceled() {

                        }
                    });
                }
            }

            @Override
            public void onFailed() {

            }

            @Override
            public void onCanceled() {

            }
        });
    }
}
