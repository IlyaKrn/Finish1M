package com.example.finish1m.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finish1m.Domain.Interfaces.AuthRepository;
import com.example.finish1m.Domain.Interfaces.UserRepository;
import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityEnterBinding;
import com.example.finish1m.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private UserRepository userRepository;
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}