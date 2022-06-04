package com.example.finish1m.Presentation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.finish1m.Data.Firebase.EventRepositoryImpl;
import com.example.finish1m.Data.Firebase.ImageRepositoryImpl;
import com.example.finish1m.Data.Firebase.UserRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.User;
import com.example.finish1m.Domain.UseCases.GetEventByIdUseCase;
import com.example.finish1m.Domain.UseCases.GetImageByRefUseCase;
import com.example.finish1m.Domain.UseCases.GetUserByEmailUseCase;
import com.example.finish1m.Domain.UseCases.RefactorEventUseCase;
import com.example.finish1m.Domain.UseCases.RefactorUserUseCase;
import com.example.finish1m.Presentation.Adapters.ImageListAdapter;
import com.example.finish1m.Presentation.Dialogs.DialogLoading;
import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityRefactorProjectBinding;
import com.example.finish1m.databinding.ActivityRefactorUserBinding;

import java.util.ArrayList;

public class RefactorUserActivity extends AppCompatActivity {

    ActivityRefactorUserBinding binding;

    private UserRepositoryImpl userRepository;
    private ImageRepositoryImpl imageRepository;

    private GetUserByEmailUseCase getUserByEmailUseCase;
    private RefactorUserUseCase refactorUserUseCase;

    private Bitmap image; // картинка пользователя
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRefactorUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userRepository = new UserRepositoryImpl(this);
        imageRepository = new ImageRepositoryImpl(this);

        // получение и установка данных
        DialogLoading dialog = new DialogLoading(RefactorUserActivity.this, getString(R.string.loading_data));
        dialog.create(R.id.fragmentContainerView);
        try {
            getUserByEmailUseCase = new GetUserByEmailUseCase(userRepository, PresentationConfig.getUser().getEmail(), new OnGetDataListener<User>() {
                @Override
                public void onGetData(User data) {
                    user = data;
                    binding.etFirstname.setText(data.getFirstName());
                    binding.etLastname.setText(data.getLastName());
                    if (user.getIconRef() == null)
                        dialog.destroy();
                    GetImageByRefUseCase getImageByRefUseCase = new GetImageByRefUseCase(imageRepository, user.getIconRef(), new OnGetDataListener<Bitmap>() {
                        @Override
                        public void onGetData(Bitmap data) {
                            image = data;
                            binding.ivImage.setImageBitmap(image);
                            dialog.destroy();
                        }

                        @Override
                        public void onVoidData() {
                            Toast.makeText(RefactorUserActivity.this, R.string.get_data_failed, Toast.LENGTH_SHORT).show();
                            dialog.destroy();
                        }

                        @Override
                        public void onFailed() {
                            Toast.makeText(RefactorUserActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                            dialog.destroy();
                        }

                        @Override
                        public void onCanceled() {
                            Toast.makeText(RefactorUserActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                            dialog.destroy();
                        }
                    });
                    getImageByRefUseCase.execute();
                }

                @Override
                public void onVoidData() {
                    Toast.makeText(RefactorUserActivity.this, R.string.get_data_failed, Toast.LENGTH_SHORT).show();
                    dialog.destroy();
                }

                @Override
                public void onFailed() {
                    Toast.makeText(RefactorUserActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    dialog.destroy();
                }

                @Override
                public void onCanceled() {
                    Toast.makeText(RefactorUserActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                    dialog.destroy();
                }
            });
            getUserByEmailUseCase.execute();
        }catch (Exception e){
            Toast.makeText(RefactorUserActivity.this, R.string.data_load_error_try_again, Toast.LENGTH_SHORT).show();
            dialog.destroy();
        }



        // закрытие активности
        binding.btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // изменение пользователя
        binding.btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstName = binding.etFirstname.getText().toString();
                final String lastName = binding.etLastname.getText().toString();
                if(!TextUtils.isEmpty(firstName)){
                    if(!TextUtils.isEmpty(lastName)){
                        DialogLoading dialog = new DialogLoading(RefactorUserActivity.this, getString(R.string.loading_data));
                        dialog.create(R.id.fragmentContainerView);
                        user.setFirstName(binding.etFirstname.getText().toString());
                        user.setLastName(binding.etLastname.getText().toString());
                        user.setIconRef("");
                        refactorUserUseCase = new RefactorUserUseCase(userRepository, imageRepository, user, image, new OnSetDataListener() {
                            @Override
                            public void onSetData() {
                                Toast.makeText(RefactorUserActivity.this, R.string.user_refactor_success, Toast.LENGTH_SHORT).show();
                                dialog.destroy();
                                finish();
                            }

                            @Override
                            public void onFailed() {
                                Toast.makeText(RefactorUserActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                dialog.destroy();
                                finish();
                            }

                            @Override
                            public void onCanceled() {
                                Toast.makeText(RefactorUserActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                                dialog.destroy();
                                finish();
                            }
                        });
                        refactorUserUseCase.execute();
                    }
                    else {
                        binding.tvMessageErr.setVisibility(View.VISIBLE);
                        binding.tvMessageErr.setText(R.string.empty_edit_text_error);
                    }
                }
                else {
                    binding.tvTitleErr.setVisibility(View.VISIBLE);
                    binding.tvTitleErr.setText(R.string.empty_edit_text_error);
                }
            }
        });

        // добавление картинки
        binding.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getData() != null && requestCode == 1){
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            iv.setImageURI(data.getData());
            Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
            image = bitmap;
            binding.ivImage.setImageBitmap(image);
        }
    }
}