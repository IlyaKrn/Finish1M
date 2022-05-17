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
import com.example.finish1m.Data.Firebase.ProjectRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Chat;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Message;
import com.example.finish1m.Domain.Models.Project;
import com.example.finish1m.Domain.UseCases.CreateNewEventUseCase;
import com.example.finish1m.Domain.UseCases.CreateNewProjectUseCase;
import com.example.finish1m.Presentation.Adapters.ImageListAdapter;
import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityCreateNewEventBinding;
import com.example.finish1m.databinding.ActivityCreateNewProjectBinding;

import java.util.ArrayList;

public class CreateNewProjectActivity extends AppCompatActivity {

    private ActivityCreateNewProjectBinding binding;

    private ProjectRepositoryImpl projectRepository;
    private ChatRepositoryImpl chatRepository;
    private ImageRepositoryImpl imageRepository;
    private CreateNewProjectUseCase createNewProjectUseCase;
    private ArrayList<Bitmap> images = new ArrayList<>();// картинки для проекта
    private ImageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNewProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        projectRepository = new ProjectRepositoryImpl(this);
        chatRepository = new ChatRepositoryImpl(this);
        imageRepository = new ImageRepositoryImpl(this);

        // закрытие активности
        binding.btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // создание нового проекта
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
                        Project p = new Project(projectRepository.getNewId(), title, message, c.getId(), null, null);
                        createNewProjectUseCase = new CreateNewProjectUseCase(projectRepository, chatRepository, imageRepository, p, c, images, new OnSetDataListener() {
                            @Override
                            public void onSetData() {
                                Toast.makeText(CreateNewProjectActivity.this, R.string.event_create_success, Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailed() {
                                Toast.makeText(CreateNewProjectActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onCanceled() {
                                Toast.makeText(CreateNewProjectActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                        createNewProjectUseCase.execute();
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
        // добавление картинки
        binding.btAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        // адаптер картинок
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