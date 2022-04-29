package com.example.finish1m.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.finish1m.Data.Firebase.ChatRepositoryImpl;
import com.example.finish1m.Data.Firebase.EventRepositoryImpl;
import com.example.finish1m.Data.Firebase.ImageRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.ImageRepository;
import com.example.finish1m.Domain.Models.Chat;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Message;
import com.example.finish1m.Domain.UseCases.CreateNewEventUseCase;
import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityCreateNewEventBinding;
import com.example.finish1m.databinding.ActivityEnterBinding;

import java.util.ArrayList;

public class CreateNewEventActivity extends AppCompatActivity {

    private ActivityCreateNewEventBinding binding;

    private EventRepositoryImpl eventRepository;
    private ChatRepositoryImpl chatRepository;
    private ImageRepositoryImpl imageRepository;
    private CreateNewEventUseCase createNewUserUseCase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNewEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        eventRepository = new EventRepositoryImpl(this);

        binding.btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title = binding.etTitle.getText().toString();
                final String message = binding.etMessage.getText().toString();
                if(!TextUtils.isEmpty(title)){
                    if(!TextUtils.isEmpty(title)){
                        ArrayList<Message> ms = new ArrayList<>();
                        Message msg = new Message(getString(R.string.message_start_chat), null, null);
                        ms.add(msg);
                        ArrayList<String> mms = new ArrayList<>();
                        mms.add(PresentationConfig.user.getEmail());
                        Chat c = new Chat(chatRepository.getNewId(), ms, mms);
                        Event e = new Event();
                        //
                        //
                        //
                        //
                        //
                    }
                    else {
                        binding.etTitle.setVisibility(View.VISIBLE);
                        binding.etTitle.setText(R.string.empty_edit_text_error);
                    }
                }
                else {
                    binding.etTitle.setVisibility(View.VISIBLE);
                    binding.etTitle.setText(R.string.empty_edit_text_error);
                }
            }
        });


    }
}