package com.example.finish1m.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityRefactorProjectBinding;
import com.example.finish1m.databinding.ActivityRefactorUserBinding;

public class RefactorUserActivity extends AppCompatActivity {

    ActivityRefactorUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRefactorUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}