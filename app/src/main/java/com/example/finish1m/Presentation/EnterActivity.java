package com.example.finish1m.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.finish1m.Data.Firebase.AuthRepositoryImpl;
import com.example.finish1m.Data.Firebase.UserRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.AuthRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.UserRepository;
import com.example.finish1m.Domain.Models.User;
import com.example.finish1m.Domain.UseCases.EnterWithEmailAndPasswordUseCase;
import com.example.finish1m.Domain.UseCases.ResetPasswordUseCase;
import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityEnterBinding;

public class EnterActivity extends AppCompatActivity {

    private ActivityEnterBinding binding;

    private UserRepositoryImpl userRepository;
    private AuthRepositoryImpl authRepository;

    private EnterWithEmailAndPasswordUseCase enterWithEmailAndPasswordUseCase;
    private ResetPasswordUseCase resetPasswordUseCase;

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
                final String email = binding.etEmail.getText().toString();
                final String password = binding.etPassword.getText().toString();
                if(!TextUtils.isEmpty(email)){
                    if(!TextUtils.isEmpty(password)){
                        enterWithEmailAndPasswordUseCase = new EnterWithEmailAndPasswordUseCase(userRepository, authRepository, email, password, new OnGetDataListener<User>() {
                        @Override
                        public void onGetData(User data) {
                            Intent intent = new Intent(EnterActivity.this, HubActivityActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onVoidData() {
                            Toast.makeText(EnterActivity.this, R.string.get_data_failed, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed() {
                            Toast.makeText(EnterActivity.this, R.string.get_data_failed, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCanceled() {
                            Toast.makeText(EnterActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                        }
                    });
                        enterWithEmailAndPasswordUseCase.execute();
                    }
                }
            }
        });
        binding.btResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = binding.etEmail.getText().toString();
                if(!TextUtils.isEmpty(email)){
                    resetPasswordUseCase = new ResetPasswordUseCase(authRepository, email, new OnSetDataListener() {
                        @Override
                        public void onSetData() {

                        }

                        @Override
                        public void onFailed() {

                        }

                        @Override
                        public void onCanceled() {
                            Toast.makeText(EnterActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                        }
                    });
                    resetPasswordUseCase.execute();
                }
            }
        });
    }
}