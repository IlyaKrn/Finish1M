package com.example.finish1m.Presentation.ui.projects;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.finish1m.Data.Firebase.ProjectRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Models.Project;
import com.example.finish1m.Domain.UseCases.GetProjectReverseListUseCase;
import com.example.finish1m.Presentation.Adapters.ProjectListAdapter;
import com.example.finish1m.Presentation.ChatActivity;
import com.example.finish1m.Presentation.CreateNewProjectActivity;
import com.example.finish1m.Presentation.PresentationConfig;
import com.example.finish1m.R;
import com.example.finish1m.databinding.FragmentProjectsBinding;

import java.util.ArrayList;


public class ProjectsFragment extends Fragment {

    private FragmentProjectsBinding binding;

    private ProjectRepositoryImpl projectRepository;
    private GetProjectReverseListUseCase getProjectListUseCase;

    private ProjectListAdapter adapter;
    private ArrayList<Project> projects = new ArrayList<>(); // список проектов

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProjectsBinding.inflate(inflater, container, false);

        // получение и установка данных
        projectRepository = new ProjectRepositoryImpl(getContext());
        getProjectListUseCase = new GetProjectReverseListUseCase(projectRepository, new OnGetDataListener<ArrayList<Project>>() {
            @Override
            public void onGetData(ArrayList<Project> data) {
                projects.clear();
                projects.addAll(data);
                adapter.notifyDataSetChanged();
                binding.noElements.setVisibility(View.GONE);
            }

            @Override
            public void onVoidData() {
                projects.clear();
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
        getProjectListUseCase.execute();

        // установка адаптера
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


        try {
            if(!PresentationConfig.getUser().isAdmin())
                binding.btAddProject.setVisibility(View.GONE);
        }catch (Exception e){
            binding.btAddProject.setVisibility(View.GONE);
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