package com.example.finish1m.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.finish1m.Data.Firebase.AuthRepositoryImpl;
import com.example.finish1m.Data.Firebase.UserRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.AuthRepository;
import com.example.finish1m.Domain.Interfaces.UserRepository;
import com.example.finish1m.Domain.UseCases.EnterWithEmailAndPasswordUseCase;
import com.example.finish1m.databinding.ActivityEnterBinding;

public class EnterActivity extends AppCompatActivity {

    private ActivityEnterBinding binding;

    private UserRepository userRepository;
    private AuthRepository authRepository;

    private EnterWithEmailAndPasswordUseCase enterWithEmailAndPasswordUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userRepository = new UserRepositoryImpl(this);
        authRepository = new AuthRepositoryImpl(this);


        binding.btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.btResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}