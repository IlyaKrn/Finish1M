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

import com.example.finish1m.Data.Firebase.EventRepositoryImpl;
import com.example.finish1m.Data.Firebase.ImageRepositoryImpl;
import com.example.finish1m.Data.Firebase.ProjectRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Project;
import com.example.finish1m.Domain.UseCases.GetEventByIdUseCase;
import com.example.finish1m.Domain.UseCases.GetImageByRefUseCase;
import com.example.finish1m.Domain.UseCases.GetProjectByIdUseCase;
import com.example.finish1m.Domain.UseCases.RefactorEventUseCase;
import com.example.finish1m.Domain.UseCases.RefactorProjectUseCase;
import com.example.finish1m.Presentation.Adapters.ImageListAdapter;
import com.example.finish1m.Presentation.Dialogs.DialogLoading;
import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityRefactorEventBinding;
import com.example.finish1m.databinding.ActivityRefactorProjectBinding;

import java.util.ArrayList;

public class RefactorEventActivity extends AppCompatActivity {

    ActivityRefactorEventBinding binding;

    private EventRepositoryImpl eventRepository;
    private ImageRepositoryImpl imageRepository;

    private GetEventByIdUseCase getEventByIdUseCase;
    private RefactorEventUseCase refactorEventUseCase;
    private ArrayList<Bitmap> images = new ArrayList<>();// картинки для сообщения
    private ImageListAdapter adapter;

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRefactorEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        eventRepository = new EventRepositoryImpl(this);
        imageRepository = new ImageRepositoryImpl(this);


        // получение и установка данных
        binding.btCreate.setClickable(false);
        DialogLoading dialog = new DialogLoading(RefactorEventActivity.this, getString(R.string.loading_data));
        dialog.create(R.id.fragmentContainerView);
        getEventByIdUseCase = new GetEventByIdUseCase(eventRepository, getIntent().getStringExtra("eventId"), new OnGetDataListener<Event>() {
            @Override
            public void onGetData(Event data) {
                event = data;
                binding.etMessage.setText(data.getMessage());
                binding.etTitle.setText(data.getTitle());
                final int[] count = {0};
                if(data.getImageRefs() != null) {
                    binding.btCreate.setClickable(false);
                    for (String s : data.getImageRefs()){
                        GetImageByRefUseCase getImageByRefUseCase = new GetImageByRefUseCase(imageRepository, s, new OnGetDataListener<Bitmap>() {
                            @Override
                            public void onGetData(Bitmap data1) {
                                count[0]++;
                                images.add(data1);
                                adapter.notifyDataSetChanged();
                                if (count[0] == data.getImageRefs().size()) {
                                    binding.btCreate.setClickable(true);
                                    dialog.destroy();
                                }
                            }

                            @Override
                            public void onVoidData() {
                                count[0]++;
                                Toast.makeText(RefactorEventActivity.this, R.string.get_data_failed, Toast.LENGTH_SHORT).show();
                                if (count[0] == data.getImageRefs().size()) {
                                    binding.btCreate.setClickable(true);
                                    dialog.destroy();
                                }
                            }

                            @Override
                            public void onFailed() {
                                count[0]++;
                                Toast.makeText(RefactorEventActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                if (count[0] == data.getImageRefs().size()) {
                                    binding.btCreate.setClickable(true);
                                    dialog.destroy();
                                }
                            }

                            @Override
                            public void onCanceled() {
                                count[0]++;
                                Toast.makeText(RefactorEventActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                                if (count[0] == data.getImageRefs().size()) {
                                    binding.btCreate.setClickable(true);
                                    dialog.destroy();
                                }
                            }
                        });
                        getImageByRefUseCase.execute();
                    }
                }

            }

            @Override
            public void onVoidData() {
                Toast.makeText(RefactorEventActivity.this, R.string.get_data_failed, Toast.LENGTH_SHORT).show();
                dialog.destroy();
            }

            @Override
            public void onFailed() {
                Toast.makeText(RefactorEventActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                dialog.destroy();
            }

            @Override
            public void onCanceled() {
                Toast.makeText(RefactorEventActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                dialog.destroy();
            }
        });
        getEventByIdUseCase.execute();


        // закрытие активности
        binding.btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // изменение события
        binding.btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title = binding.etTitle.getText().toString();
                final String message = binding.etMessage.getText().toString();
                if(!TextUtils.isEmpty(title)){
                    if(!TextUtils.isEmpty(title)){
                        DialogLoading dialog = new DialogLoading(RefactorEventActivity.this, getString(R.string.loading_data));
                        dialog.create(R.id.fragmentContainerView);
                        int type = Event.NEWS;
                        switch(binding.rgType.getCheckedRadioButtonId()){
                            case R.id.rbt_news:
                                type = Event.NEWS;
                                break;
                            case R.id.rbt_event:
                                type = Event.EVENT;
                                break;
                        }
                        event.setType(type);
                        event.setTitle(binding.etTitle.getText().toString());
                        event.setMessage(binding.etMessage.getText().toString());
                        event.setImageRefs(new ArrayList<>());
                        refactorEventUseCase = new RefactorEventUseCase(eventRepository, imageRepository, event, images, new OnSetDataListener() {
                            @Override
                            public void onSetData() {
                                Toast.makeText(RefactorEventActivity.this, R.string.event_refactor_success, Toast.LENGTH_SHORT).show();
                                dialog.destroy();
                                finish();
                            }

                            @Override
                            public void onFailed() {
                                Toast.makeText(RefactorEventActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                dialog.destroy();
                                finish();
                            }

                            @Override
                            public void onCanceled() {
                                Toast.makeText(RefactorEventActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                                dialog.destroy();
                                finish();
                            }
                        });
                        refactorEventUseCase.execute();
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