package com.example.finish1m.Presentation.ui.myEvent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.finish1m.Data.Firebase.EventRepositoryImpl;
import com.example.finish1m.Data.VK.VKRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.VKRepository;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.UseCases.GetEventReverseListUseCase;
import com.example.finish1m.Presentation.Adapters.EventListAdapter;
import com.example.finish1m.Presentation.CreateNewEventActivity;
import com.example.finish1m.Presentation.PresentationConfig;
import com.example.finish1m.R;
import com.example.finish1m.databinding.FragmentMyEventsBinding;

import java.util.ArrayList;


public class MyEventsFragment extends Fragment {

    private FragmentMyEventsBinding binding;

    private EventRepositoryImpl eventRepository;

    private GetEventReverseListUseCase getEventListUseCase;

    private EventListAdapter adapter;
    private ArrayList<Event> events = new ArrayList<>(); // список мероприятий
    private VKRepositoryImpl vkRepository;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyEventsBinding.inflate(inflater, container, false);

        eventRepository = new EventRepositoryImpl(getContext());
        vkRepository = new VKRepositoryImpl(getContext());

        // получение списка моих мероприятий
        getEventListUseCase = new GetEventReverseListUseCase(eventRepository, vkRepository, new OnGetDataListener<ArrayList<Event>>() {
            @Override
            public void onGetData(ArrayList<Event> data) {
                events.clear();
                for(Event e : data){
                    if(e.getMembers() != null) {
                        for (String s : e.getMembers()) {
                            try {
                                if (s.equals(PresentationConfig.getUser().getEmail())) {
                                    events.add(e);
                                    break;
                                }
                            }catch (Exception e1){
                                Toast.makeText(getContext(), R.string.data_load_error_try_again, Toast.LENGTH_SHORT).show();
                                events.clear();
                            }

                        }
                    }
                }
                adapter.notifyDataSetChanged();
                binding.noElements.setVisibility(View.GONE);
            }

            @Override
            public void onVoidData() {
                events.clear();
                adapter.notifyDataSetChanged();
                binding.noElements.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailed() {
                Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled() {
                Toast.makeText(getContext(), R.string.access_denied, Toast.LENGTH_SHORT).show();
            }
        });
        getEventListUseCase.execute();

        // установка адаптера
        adapter = new EventListAdapter(getActivity(), getContext(), events);
        binding.rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvEvents.setAdapter(adapter);
        binding.btAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateNewEventActivity.class);
                startActivity(intent);
            }
        });


        try {
            if(!PresentationConfig.getUser().isAdmin())
                binding.btAddEvent.setVisibility(View.GONE);
        }catch (Exception e){
            binding.btAddEvent.setVisibility(View.GONE);
            Toast.makeText(getContext(), R.string.data_load_error_try_again, Toast.LENGTH_SHORT).show();
        }


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}