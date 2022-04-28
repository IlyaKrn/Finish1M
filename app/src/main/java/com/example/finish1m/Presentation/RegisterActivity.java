package com.example.finish1m.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.finish1m.Data.Firebase.AuthRepositoryImpl;
import com.example.finish1m.Data.Firebase.UserRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.AuthRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.UserRepository;
import com.example.finish1m.Domain.Models.User;
import com.example.finish1m.Domain.UseCases.CreateNewUserUseCase;
import com.example.finish1m.Domain.UseCases.EnterWithEmailAndPasswordUseCase;
import com.example.finish1m.Domain.UseCases.RegisterWithEmailAndPasswordUseCase;
import com.example.finish1m.Domain.UseCases.SendVerificationEmailUseCase;
import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityEnterBinding;
import com.example.finish1m.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private UserRepository userRepository;
    private AuthRepository authRepository;

    private RegisterWithEmailAndPasswordUseCase registerWithEmailAndPasswordUseCase;
    private EnterWithEmailAndPasswordUseCase enterWithEmailAndPasswordUseCase;
    private SendVerificationEmailUseCase sendVerificationEmailUseCase;
    private CreateNewUserUseCase createNewUserUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userRepository = new UserRepositoryImpl(this);
        authRepository = new AuthRepositoryImpl(this);

        sendVerificationEmailUseCase = new SendVerificationEmailUseCase(authRepository, binding.etEmail.getText().toString(), new OnSetDataListener(){
            @Override
            public void onSetData() {

            }

            @Override
            public void onFailed() {

            }

            @Override
            public void onCanceled() {

            }
        });




        binding.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = binding.etEmail.getText().toString();
                final String password = binding.etPassword.getText().toString();
                if(!TextUtils.isEmpty(email)) {
                    if (!TextUtils.isEmpty(email)){
                        registerWithEmailAndPasswordUseCase = new RegisterWithEmailAndPasswordUseCase(authRepository, userRepository, email, password, new OnSetDataListener() {
                            @Override
                            public void onSetData() {
                                final String firstName = binding.etUserName.getText().toString();
                                final String lastName = binding.etUserFamily.getText().toString();
                                final String email = binding.etEmail.getText().toString();
                                final String password = binding.etPassword.getText().toString();
                                final String passwordSec = binding.etSecondPassword.getText().toString();
                                binding.btRegister.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(!TextUtils.isEmpty(firstName)) {
                                            if(!TextUtils.isEmpty(lastName)) {
                                                if(!TextUtils.isEmpty(email)) {
                                                    if(!TextUtils.isEmpty(password)) {
                                                        if(!TextUtils.isEmpty(passwordSec)) {
                                                            if (passwordSec.equals(password)) {
                                                                if (password.length() >= 6) {
                                                                    createNewUserUseCase = new CreateNewUserUseCase(userRepository, new User(firstName, lastName, email, false, false, null), new OnSetDataListener(){
                                                                        @Override
                                                                        public void onSetData() {
                                                                            Intent intent = new Intent(RegisterActivity.this, HubActivityActivity.class);
                                                                            startActivity(intent);
                                                                            finish();
                                                                        }

                                                                        @Override
                                                                        public void onFailed() {

                                                                        }

                                                                        @Override
                                                                        public void onCanceled() {

                                                                        }
                                                                    });
                                                                    createNewUserUseCase.execute();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onFailed() {

                            }

                            @Override
                            public void onCanceled() {

                            }
                        });
                        registerWithEmailAndPasswordUseCase.execute();
                    }
                }
            }
        });

        binding.btResendVerificationEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = binding.etEmail.getText().toString();
                if(!TextUtils.isEmpty(email))
                    sendVerificationEmailUseCase.execute();
            }
        });

    }
}