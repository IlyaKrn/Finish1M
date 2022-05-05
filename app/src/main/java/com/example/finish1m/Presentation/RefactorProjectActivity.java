package com.example.finish1m.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityRefactorProjectBinding;

public class RefactorProjectActivity extends AppCompatActivity {

    ActivityRefactorProjectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRefactorProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}