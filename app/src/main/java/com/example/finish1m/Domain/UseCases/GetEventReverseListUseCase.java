package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.EventRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.VKRepository;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.WallModels.Attachment;
import com.example.finish1m.Domain.Models.WallModels.CopyHistory;
import com.example.finish1m.Domain.Models.WallModels.Item;
import com.example.finish1m.Domain.Models.WallModels.WallModel;

import java.util.ArrayList;
import java.util.Comparator;

// получение перевернутого списка событий

public class GetEventReverseListUseCase {

    private static final int TITLE_FROM_VK_SIZE = 50;
    private EventRepository eventRepository;
    private VKRepository vkRepository;
    private OnGetDataListener<ArrayList<Event>> listener;

    public GetEventReverseListUseCase(EventRepository repository, VKRepository vkRepository, OnGetDataListener<ArrayList<Event>> listener) {
        this.eventRepository = repository;
        this.vkRepository = vkRepository;
        this.listener = listener;
    }

    public void execute(){
        eventRepository.getEventList(new OnGetDataListener<ArrayList<Event>>() {
            @Override
            public void onGetData(ArrayList<Event> data) {
                ArrayList<Event> temp = data;
                vkRepository.getMainWall(new OnGetDataListener<ArrayList<Event>>() {
                    @Override
                    public void onGetData(ArrayList<Event> data) {
                        temp.addAll(data);
                        temp.sort(new Comparator<Event>() {
                            @Override
                            public int compare(Event event, Event t1) {
                                if (event.getDate() > t1.getDate())
                                    return 1;
                                else if (event.getDate() < t1.getDate())
                                    return -1;
                                else
                                    return 0;
                            }
                        });
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
