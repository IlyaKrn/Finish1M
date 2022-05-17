package com.example.finish1m.Presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.finish1m.Data.Firebase.ProjectRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Models.Follow;
import com.example.finish1m.Domain.Models.Project;
import com.example.finish1m.Domain.UseCases.GetImageByRefUseCase;
import com.example.finish1m.Domain.UseCases.GetProjectByIdUseCase;
import com.example.finish1m.Presentation.Adapters.FollowListAdapter;
import com.example.finish1m.databinding.ActivityFollowsListBinding;

import java.util.ArrayList;

public class FollowsListActivity extends AppCompatActivity {

    ActivityFollowsListBinding binding;

    private FollowListAdapter adapter;
    private ArrayList<Follow> follows = new ArrayList<>(); // список заявок
    private ProjectRepositoryImpl projectRepository;

    private GetProjectByIdUseCase getProjectByIdUseCase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFollowsListBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        projectRepository = new ProjectRepositoryImpl(this);

       /* for (int i = 0; i < 3; i++){
            ArrayList<String> ir = new ArrayList<>();
            ir.add("images/all/-N1TEqCF_sJVqEq9E7F4");
            Follow f = new Follow("fgdfgdfgdfg0", "ilyakornoukhov@gmail.com", "dfgdfgdfgdfgdfgdf", ir);
            follows.add(f);
        }

        */

        // получение и установка данных
         getProjectByIdUseCase = new GetProjectByIdUseCase(projectRepository, getIntent().getStringExtra("projectId"), new OnGetDataListener<Project>() {
            @Override
            public void onGetData(Project data) {
                follows.clear();
                binding.noElements.setVisibility(View.VISIBLE);
                if(data.getFollows() != null) {
                    follows.addAll(data.getFollows());
                    binding.noElements.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onVoidData() {
                follows.clear();
                adapter.notifyDataSetChanged();
                binding.noElements.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailed() {

            }

            @Override
            public void onCanceled() {

            }
        });
        getProjectByIdUseCase.execute();







        // адаптер
        adapter = new FollowListAdapter(this, this, follows);
        binding.rvFollows.setAdapter(adapter);
        binding.rvFollows.setLayoutManager(new LinearLayoutManager(this));

        binding.btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}