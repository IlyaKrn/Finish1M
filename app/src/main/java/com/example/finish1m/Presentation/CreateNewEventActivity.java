package com.example.finish1m.Presentation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.finish1m.Data.Firebase.ChatRepositoryImpl;
import com.example.finish1m.Data.Firebase.EventRepositoryImpl;
import com.example.finish1m.Data.Firebase.ImageRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Chat;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Message;
import com.example.finish1m.Domain.UseCases.CreateNewEventUseCase;
import com.example.finish1m.Presentation.Adapters.ImageListAdapter;
import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityCreateNewEventBinding;

import java.util.ArrayList;

public class CreateNewEventActivity extends AppCompatActivity {

    private ActivityCreateNewEventBinding binding;

    private EventRepositoryImpl eventRepository;
    private ChatRepositoryImpl chatRepository;
    private ImageRepositoryImpl imageRepository;
    private CreateNewEventUseCase createNewEventUseCase;
    private ArrayList<Bitmap> images = new ArrayList<>();
    private ImageListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNewEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        eventRepository = new EventRepositoryImpl(this);
        chatRepository = new ChatRepositoryImpl(this);
        imageRepository = new ImageRepositoryImpl(this);


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
                        int type = Event.NEWS;
                        switch(binding.rgType.getCheckedRadioButtonId()){
                            case R.id.rbt_news:
                                type = Event.NEWS;
                                break;
                            case R.id.rbt_event:
                                type = Event.EVENT;
                                break;
                        }
                        ArrayList<Message> ms = new ArrayList<>();
                        Message msg = new Message(getString(R.string.message_start_chat), null, null);
                        ms.add(msg);
                        ArrayList<String> mms = new ArrayList<>();
                        mms.add(PresentationConfig.user.getEmail());
                        Chat c = new Chat(chatRepository.getNewId(), ms, mms);
                        Event e = new Event(eventRepository.getNewId(), type, title, message, c.getId(), null, null);
                        createNewEventUseCase = new CreateNewEventUseCase(eventRepository, chatRepository, imageRepository, e, c, images, new OnSetDataListener() {
                            @Override
                            public void onSetData() {
                                Toast.makeText(CreateNewEventActivity.this, R.string.event_create_success, Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailed() {
                                Toast.makeText(CreateNewEventActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onCanceled() {
                                Toast.makeText(CreateNewEventActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                        createNewEventUseCase.execute();
                    }
                    else {
                        binding.tvTitleErr.setVisibility(View.VISIBLE);
                        binding.tvTitleErr.setText(R.string.empty_edit_text_error);
                    }
                }
                else {
                    binding.tvTitleErr.setVisibility(View.VISIBLE);
                    binding.tvTitleErr.setText(R.string.empty_edit_text_error);
                }
            }
        });

        binding.btAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        adapter = new ImageListAdapter(this, this, images);
        adapter.setOnItemRemoveListener(new ImageListAdapter.OnItemRemoveListener() {
            @Override
            public void onRemove(int position) {
                images.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        binding.rvImages.setLayoutManager(new LinearLayoutManager(this));
        binding.rvImages.setAdapter(adapter);





    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getData() != null && requestCode == 1){
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            iv.setImageURI(data.getData());
            Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
            images.add(bitmap);
            adapter.notifyDataSetChanged();
        }
    }
}