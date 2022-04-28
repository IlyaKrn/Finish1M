package com.example.finish1m.Presentation.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.finish1m.Data.Firebase.ProjectRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.ProjectRepository;
import com.example.finish1m.Domain.Models.Project;
import com.example.finish1m.Domain.UseCases.GetProjectListUseCase;
import com.example.finish1m.Presentation.Adapters.ProjectListAdapter;
import com.example.finish1m.Presentation.CreateNewProjectActivity;
import com.example.finish1m.Presentation.PresentationConfig;
import com.example.finish1m.R;
import com.example.finish1m.databinding.FragmentGalleryBinding;

import java.util.ArrayList;


public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    private ProjectRepositoryImpl projectRepository;
    private GetProjectListUseCase getProjectListUseCase;

    private ProjectListAdapter adapter;
    private ArrayList<Project> projects = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);

        projectRepository = new ProjectRepositoryImpl(getContext());
        getProjectListUseCase = new GetProjectListUseCase(projectRepository, new OnGetDataListener<ArrayList<Project>>() {
            @Override
            public void onGetData(ArrayList<Project> data) {
                projects.clear();
                projects.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onVoidData() {
                projects.clear();
                adapter.notifyDataSetChanged();
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

        adapter = new ProjectListAdapter(getActivity(), getContext(), projects);
        binding.rvProjects.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvProjects.setAdapter(adapter);

        binding.btAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateNewProjectActivity.class);
                startActivity(intent);
            }
        });

        if(!PresentationConfig.user.isAdmin())
            binding.btAddProject.setVisibility(View.GONE);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}