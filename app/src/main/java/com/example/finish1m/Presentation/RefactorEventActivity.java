package com.example.finish1m.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityRefactorEventBinding;
import com.example.finish1m.databinding.ActivityRefactorProjectBinding;

public class RefactorEventActivity extends AppCompatActivity {

    ActivityRefactorEventBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRefactorEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}