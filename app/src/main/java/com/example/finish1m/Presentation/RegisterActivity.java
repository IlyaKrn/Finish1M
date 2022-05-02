package com.example.finish1m.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.finish1m.Data.Firebase.AuthRepositoryImpl;
import com.example.finish1m.Data.Firebase.UserRepositoryImpl;
import com.example.finish1m.Data.SQLite.SQLiteRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.AuthRepository;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.UserRepository;
import com.example.finish1m.Domain.Models.SQLiteUser;
import com.example.finish1m.Domain.Models.User;
import com.example.finish1m.Domain.UseCases.CreateNewUserUseCase;
import com.example.finish1m.Domain.UseCases.EnterWithEmailAndPasswordUseCase;
import com.example.finish1m.Domain.UseCases.RegisterWithEmailAndPasswordUseCase;
import com.example.finish1m.Domain.UseCases.SendVerificationEmailUseCase;
import com.example.finish1m.Domain.UseCases.WriteSQLiteUserUseCase;
import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private UserRepository userRepository;
    private AuthRepository authRepository;
    private SQLiteRepositoryImpl sqLiteRepository;

    private WriteSQLiteUserUseCase writeSQLiteUserUseCase;
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
        sqLiteRepository = new SQLiteRepositoryImpl(this);

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
                                                                            enterWithEmailAndPasswordUseCase = new EnterWithEmailAndPasswordUseCase(userRepository, authRepository, email, password, new OnGetDataListener<User>() {
                                                                                @Override
                                                                                public void onGetData(User data) {
                                                                                    PresentationConfig.user = data;
                                                                                    if (binding.cbAlwaysUse.isChecked()) {
                                                                                        writeSQLiteUserUseCase = new WriteSQLiteUserUseCase(sqLiteRepository, new SQLiteUser(email, password), new OnSetDataListener() {
                                                                                            @Override
                                                                                            public void onSetData() {

                                                                                            }

                                                                                            @Override
                                                                                            public void onFailed() {
                                                                                                Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                                                                            }

                                                                                            @Override
                                                                                            public void onCanceled() {
                                                                                                Toast.makeText(RegisterActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                    else {
                                                                                        writeSQLiteUserUseCase = new WriteSQLiteUserUseCase(sqLiteRepository, null, new OnSetDataListener() {
                                                                                            @Override
                                                                                            public void onSetData() {

                                                                                            }

                                                                                            @Override
                                                                                            public void onFailed() {
                                                                                                Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                                                                            }

                                                                                            @Override
                                                                                            public void onCanceled() {
                                                                                                Toast.makeText(RegisterActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                    writeSQLiteUserUseCase.execute();
                                                                                    Intent intent = new Intent(RegisterActivity.this, HubActivity.class);
                                                                                    startActivity(intent);
                                                                                    finish();
                                                                                }

                                                                                @Override
                                                                                public void onVoidData() {
                                                                                    Toast.makeText(RegisterActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                                                                                }

                                                                                @Override
                                                                                public void onFailed() {
                                                                                    Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                                                                }

                                                                                @Override
                                                                                public void onCanceled() {
                                                                                    Toast.makeText(RegisterActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                        }

                                                                        @Override
                                                                        public void onFailed() {
                                                                            Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                                                        }

                                                                        @Override
                                                                        public void onCanceled() {
                                                                            Toast.makeText(RegisterActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                    createNewUserUseCase.execute();
                                                                }
                                                                else {
                                                                    binding.tvPasswordErr.setVisibility(View.VISIBLE);
                                                                    binding.tvPasswordErr.setText(R.string.short_password);
                                                                }
                                                            }
                                                            else {
                                                                binding.tvPasswordErr.setVisibility(View.VISIBLE);
                                                                binding.tvPasswordErr.setText(R.string.diffrent_password);
                                                                binding.tvSecondPasswordErr.setVisibility(View.VISIBLE);
                                                                binding.tvSecondPasswordErr.setText(R.string.diffrent_password);
                                                            }
                                                        }
                                                        else {
                                                            binding.tvSecondPasswordErr.setVisibility(View.VISIBLE);
                                                            binding.tvSecondPasswordErr.setText(R.string.empty_edit_text_error);
                                                        }
                                                    }
                                                    else {
                                                        binding.tvNameErr.setVisibility(View.VISIBLE);
                                                        binding.tvNameErr.setText(R.string.empty_edit_text_error);
                                                    }
                                                }
                                                else {
                                                    binding.tvEmailErr.setVisibility(View.VISIBLE);
                                                    binding.tvEmailErr.setText(R.string.empty_edit_text_error);
                                                }
                                            }
                                            else {
                                                binding.tvFamilyErr.setVisibility(View.VISIBLE);
                                                binding.tvFamilyErr.setText(R.string.empty_edit_text_error);
                                            }
                                        }
                                        else {
                                            binding.tvNameErr.setVisibility(View.VISIBLE);
                                            binding.tvNameErr.setText(R.string.empty_edit_text_error);
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onFailed() {
                                Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCanceled() {
                                Toast.makeText(RegisterActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
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
                    sendVerificationEmailUseCase = new SendVerificationEmailUseCase(authRepository, email, new OnSetDataListener(){
                        @Override
                        public void onSetData() {
                            Toast.makeText(RegisterActivity.this, R.string.email_verification_email_sended, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed() {
                            Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCanceled() {
                            Toast.makeText(RegisterActivity.this, R.string.you_not_registred, Toast.LENGTH_SHORT).show();
                        }
                    });

                sendVerificationEmailUseCase.execute();
            }
        });

    }
}