package com.example.finish1m.Presentation;

import android.app.Application;

import com.example.finish1m.Data.Firebase.EventRepositoryImpl;
import com.example.finish1m.Data.Other.NotificationRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.NotificationRepository;
import com.example.finish1m.Domain.UseCases.StartListeningEventsUseCase;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        NotificationRepositoryImpl notificationRepository = new NotificationRepositoryImpl(this);
        EventRepositoryImpl eventRepository = new EventRepositoryImpl(this);
        StartListeningEventsUseCase startListeningEventsUseCase = new StartListeningEventsUseCase(eventRepository, notificationRepository);
        startListeningEventsUseCase.execute();
    }
}
