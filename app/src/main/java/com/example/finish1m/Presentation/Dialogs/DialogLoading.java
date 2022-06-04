package com.example.finish1m.Presentation.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.finish1m.R;

public class DialogLoading extends Dialog{

    private String message;

    private TextView tvMessage;
    private ProgressBar progressBar;

    public DialogLoading(AppCompatActivity activity, String message) {
        super(activity);
        this.message = message;
    }

    public DialogLoading(Fragment fragment, String message) {
        super(fragment);
        this.message = message;
    }

    public DialogLoading(AppCompatActivity activity) {
        super(activity);
        this.message = null;
    }

    public DialogLoading(Fragment fragment) {
        super(fragment);
        this.message = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_loading, container, false);
        tvMessage = rootView.findViewById(R.id.tv_message);
        progressBar = rootView.findViewById(R.id.progress);
        if (message != null){
            tvMessage.setText(message);
            tvMessage.setVisibility(View.VISIBLE);
        }


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar.getLayoutParams().height = progressBar.getLayoutParams().width;
    }
}
