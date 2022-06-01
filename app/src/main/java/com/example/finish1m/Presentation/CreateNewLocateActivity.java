package com.example.finish1m.Presentation;

import androidx.annotation.NonNull;
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
import com.example.finish1m.Data.Firebase.ImageRepositoryImpl;
import com.example.finish1m.Data.Firebase.LocateRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Chat;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Locate;
import com.example.finish1m.Domain.Models.Message;
import com.example.finish1m.Domain.UseCases.CreateNewEventUseCase;
import com.example.finish1m.Domain.UseCases.CreateNewLocateUseCase;
import com.example.finish1m.Presentation.Adapters.ImageListAdapter;
import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityCreateNewLocateBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;

public class CreateNewLocateActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityCreateNewLocateBinding binding;

    private LocateRepositoryImpl locateRepository;
    private ChatRepositoryImpl chatRepository;
    private ImageRepositoryImpl imageRepository;
    private CreateNewLocateUseCase createNewEventUseCase;
    private ArrayList<Bitmap> images = new ArrayList<>();// картинки для метки
    private ImageListAdapter adapter;

    private LatLng coordinate; // координата метки
    private GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNewLocateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        coordinate = new LatLng(getIntent().getDoubleExtra("latitude", 0), getIntent().getDoubleExtra("longitude", 0));

        locateRepository = new LocateRepositoryImpl(this);
        chatRepository = new ChatRepositoryImpl(this);
        imageRepository = new ImageRepositoryImpl(this);

        // закрытие активности
        binding.btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // создание новой метки
        binding.btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title = binding.etTitle.getText().toString();
                final String message = binding.etMessage.getText().toString();
                if(!TextUtils.isEmpty(title)){
                    if(!TextUtils.isEmpty(title)){
                        try {
                            ArrayList<Message> ms = new ArrayList<>();
                            Message msg = new Message(getString(R.string.message_start_chat), null, null);
                            ms.add(msg);
                            ArrayList<String> mms = new ArrayList<>();
                            mms.add(PresentationConfig.getUser().getEmail());
                            Chat c = new Chat(chatRepository.getNewId(), ms, mms);
                            Locate l = new Locate(locateRepository.getNewId(), coordinate.longitude, coordinate.latitude, title,  message, c.getId(), null);
                            createNewEventUseCase = new CreateNewLocateUseCase(locateRepository, chatRepository, imageRepository, l, c, images, new OnSetDataListener() {
                                @Override
                                public void onSetData() {
                                    Toast.makeText(CreateNewLocateActivity.this, R.string.locate_create_success, Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onFailed() {
                                    Toast.makeText(CreateNewLocateActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onCanceled() {
                                    Toast.makeText(CreateNewLocateActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                            createNewEventUseCase.execute();
                        }catch (Exception e){
                            Toast.makeText(ChatActivity.this, R.string.try_again, Toast.LENGTH_SHORT).show();
                        }

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




        SupportMapFragment mapView = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapView.getMapAsync(this);

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

    @Override
    public void onMapReady(@NonNull GoogleMap gMap) {
        googleMap = gMap;
        // установка маркера на карту
        googleMap.addMarker(new MarkerOptions().position(coordinate));
        // установка камеры на координаты
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 11));

    }
}