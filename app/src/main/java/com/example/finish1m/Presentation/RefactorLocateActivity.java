package com.example.finish1m.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityRefactorLocateBinding;
import com.example.finish1m.databinding.ActivityRefactorProjectBinding;

public class RefactorLocateActivity extends AppCompatActivity {

    ActivityRefactorLocateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRefactorLocateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}