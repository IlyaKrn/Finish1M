package com.example.finish1m.Presentation;

import android.app.Application;

import com.example.finish1m.Data.Firebase.EventRepositoryImpl;
import com.example.finish1m.Data.Other.NotificationRepositoryImpl;
import com.example.finish1m.Domain.UseCases.StartListeningEventsUseCase;

public class App extends Application {

    private NotificationRepositoryImpl notificationRepository;
    private EventRepositoryImpl eventRepository;

    private StartListeningEventsUseCase startListeningEventsUseCase;

    @Override
    public void onCreate() {
        super.onCreate();

        // уведомления о событиях
        notificationRepository = new NotificationRepositoryImpl(this);
        eventRepository = new EventRepositoryImpl(this);
        startListeningEventsUseCase = new StartListeningEventsUseCase(eventRepository, notificationRepository);
        startListeningEventsUseCase.execute();


    }
}
