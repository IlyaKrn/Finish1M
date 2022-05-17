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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finish1m.Data.Firebase.ImageRepositoryImpl;
import com.example.finish1m.Data.Firebase.ProjectRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Chat;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.Message;
import com.example.finish1m.Domain.Models.Project;
import com.example.finish1m.Domain.UseCases.CreateNewEventUseCase;
import com.example.finish1m.Domain.UseCases.GetImageByRefUseCase;
import com.example.finish1m.Domain.UseCases.GetProjectByIdUseCase;
import com.example.finish1m.Domain.UseCases.RefactorProjectUseCase;
import com.example.finish1m.Presentation.Adapters.ImageListAdapter;
import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityRefactorProjectBinding;

import java.util.ArrayList;

public class RefactorProjectActivity extends AppCompatActivity {

    ActivityRefactorProjectBinding binding;

    private ProjectRepositoryImpl projectRepository;
    private ImageRepositoryImpl imageRepository;

    private GetProjectByIdUseCase getProjectByIdUseCase;
    private RefactorProjectUseCase refactorProjectUseCase;
    private ArrayList<Bitmap> images = new ArrayList<>();// картинки для проекта
    private ImageListAdapter adapter;

    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRefactorProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        projectRepository = new ProjectRepositoryImpl(this);
        imageRepository = new ImageRepositoryImpl(this);

        // получение и установка данных
        getProjectByIdUseCase = new GetProjectByIdUseCase(projectRepository, getIntent().getStringExtra("projectId"), new OnGetDataListener<Project>() {
            @Override
            public void onGetData(Project data) {
                project = data;
                binding.etMessage.setText(data.getMessage());
                binding.etTitle.setText(data.getTitle());
                final int[] count = {0};
                if(data.getImageRefs() != null) {
                    for (String s : data.getImageRefs()){
                        GetImageByRefUseCase getImageByRefUseCase = new GetImageByRefUseCase(imageRepository, s, new OnGetDataListener<Bitmap>() {
                            @Override
                            public void onGetData(Bitmap data) {
                                images.add(data);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onVoidData() {
                                Toast.makeText(RefactorProjectActivity.this, R.string.get_data_failed, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailed() {
                                Toast.makeText(RefactorProjectActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCanceled() {
                                Toast.makeText(RefactorProjectActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                            }
                        });
                        getImageByRefUseCase.execute();
                    }
                }

            }

            @Override
            public void onVoidData() {
                Toast.makeText(RefactorProjectActivity.this, R.string.get_data_failed, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(RefactorProjectActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled() {
                Toast.makeText(RefactorProjectActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
            }
        });
        getProjectByIdUseCase.execute();



        // изменение проекта
        binding.btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title = binding.etTitle.getText().toString();
                final String message = binding.etMessage.getText().toString();
                if(!TextUtils.isEmpty(title)){
                    if(!TextUtils.isEmpty(title)){
                        project.setTitle(binding.etTitle.getText().toString());
                        project.setMessage(binding.etMessage.getText().toString());
                        project.setImageRefs(new ArrayList<>());
                        refactorProjectUseCase = new RefactorProjectUseCase(projectRepository, imageRepository, project, images, new OnSetDataListener() {
                            @Override
                            public void onSetData() {
                                Toast.makeText(RefactorProjectActivity.this, R.string.project_refactor_success, Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailed() {
                                Toast.makeText(RefactorProjectActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onCanceled() {
                                Toast.makeText(RefactorProjectActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                        refactorProjectUseCase.execute();
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
        // закрытие активности
        binding.btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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