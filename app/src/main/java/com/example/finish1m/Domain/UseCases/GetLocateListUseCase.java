package com.example.finish1m.Domain.UseCases;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.LocateRepository;
import com.example.finish1m.Domain.Models.Locate;

import java.util.ArrayList;

public class GetLocateListUseCase {

    private LocateRepository repository;
    private OnGetDataListener<ArrayList<Locate>> listener;

    public GetLocateListUseCase(LocateRepository repository, OnGetDataListener<ArrayList<Locate>> listener) {
        this.repository = repository;
        this.listener = listener;
    }

    public void execute(){
        repository.getLocateList(listener);
    }
}
