package com.example.finish1m.Presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.finish1m.Data.Firebase.ProjectRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Follow;
import com.example.finish1m.Domain.Models.Project;
import com.example.finish1m.Domain.UseCases.GetProjectByIdUseCase;
import com.example.finish1m.Domain.UseCases.RemoveFollowFromProjectUseCase;
import com.example.finish1m.Presentation.Adapters.Adapter;
import com.example.finish1m.Presentation.Adapters.FollowListAdapter;
import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityRemoveFollowBinding;

import java.util.ArrayList;

public class RemoveFollowActivity extends AppCompatActivity {

    private ActivityRemoveFollowBinding binding;

    private FollowListAdapter adapter;
    private ArrayList<Follow> follows = new ArrayList<>();
    private ProjectRepositoryImpl projectRepository;

    private GetProjectByIdUseCase getProjectByIdUseCase;
    private RemoveFollowFromProjectUseCase removeFollowFromProjectUseCase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRemoveFollowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        projectRepository = new ProjectRepositoryImpl(this);
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







        adapter = new FollowListAdapter(this, this, follows);
        adapter.setOnItemClickListener(new Adapter.OnStateClickListener<Follow>() {
            @Override
            public void onClick(Follow item) {

            }

            @Override
            public void onLongClick(Follow item) {
                removeFollowFromProjectUseCase = new RemoveFollowFromProjectUseCase(projectRepository, getIntent().getStringExtra("projectId"), item.getId(), new OnSetDataListener() {
                    @Override
                    public void onSetData() {
                        Toast.makeText(RemoveFollowActivity.this, R.string.follow_delete_success, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed() {
                        Toast.makeText(RemoveFollowActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCanceled() {
                        Toast.makeText(RemoveFollowActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                    }
                });
                removeFollowFromProjectUseCase.execute();
            }
        });
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