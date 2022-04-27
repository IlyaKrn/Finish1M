package com.example.finish1m.Domain.Interfaces;

import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.User;

import java.util.ArrayList;

public interface UserRepository {

    void getUserListeners(OnGetDataListener<ArrayList<User>> listener);
    void getUserById(String eventId, OnGetDataListener<Event> listener);
    void setUser(User user, OnSetDataListener listener);
}
