package com.example.finish1m.Presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.finish1m.Data.Firebase.UserRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.UserRepository;
import com.example.finish1m.Domain.Models.User;
import com.example.finish1m.Domain.UseCases.GetUserByEmailUseCase;
import com.example.finish1m.Presentation.Adapters.UserListAdapter;
import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityUserListBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class UserListActivity extends AppCompatActivity {

    private ActivityUserListBinding binding;

    private UserListAdapter adapter;
    private ArrayList<User> users = new ArrayList<>(); // пользователи
    private ArrayList<String> userEmails = new ArrayList<>(); // ссылки на пользователей
    private UserRepositoryImpl userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // получение данных из предыдущей активности
        userRepository = new UserRepositoryImpl(this);

        for (int i = 0; i < getIntent().getIntExtra("user_size", 0); i++) {
            userEmails.add(getIntent().getStringExtra("user" + i));
        }
        if (userEmails.size() == 0)
            binding.noElements.setVisibility(View.VISIBLE);

        // получение и установка данных
        for(String s : userEmails){
            GetUserByEmailUseCase getUserByEmailUseCase = new GetUserByEmailUseCase(userRepository, s, new OnGetDataListener<User>() {
                @Override
                public void onGetData(User data) {
                    users.add(data);
                    adapter.notifyDataSetChanged();
                    binding.noElements.setVisibility(View.GONE);
                }

                @Override
                public void onVoidData() {

                }

                @Override
                public void onFailed() {

                }

                @Override
                public void onCanceled() {

                }
            });
            getUserByEmailUseCase.execute();
        }

        // адаптер
        adapter = new UserListAdapter(this, this, users);
        binding.rvUsers.setAdapter(adapter);
        binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));

        // закрытие активности
        binding.btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}