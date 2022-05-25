package com.example.finish1m.Presentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.finish1m.Data.Firebase.ChatRepositoryImpl;
import com.example.finish1m.Data.Firebase.ImageRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Chat;
import com.example.finish1m.Domain.Models.Message;
import com.example.finish1m.Domain.UseCases.CreateNewMessageUseCase;
import com.example.finish1m.Domain.UseCases.GetChatByIdUseCase;
import com.example.finish1m.Domain.UseCases.RemoveMessageUseCase;
import com.example.finish1m.Presentation.Adapters.Adapter;
import com.example.finish1m.Presentation.Adapters.MessageListAdapter;
import com.example.finish1m.Presentation.Dialogs.DialogConfirm;
import com.example.finish1m.Presentation.Dialogs.OnConfirmListener;
import com.example.finish1m.R;
import com.example.finish1m.databinding.ActivityChatBinding;

import java.util.ArrayList;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;

    private ChatRepositoryImpl chatRepository;
    private ImageRepositoryImpl imageRepository;

    private GetChatByIdUseCase getChatByIdUseCase;
    private CreateNewMessageUseCase createNewMessageUseCase;
    private RemoveMessageUseCase removeMessageUseCase;

    private MessageListAdapter adapter;

    private ArrayList<Message> messages = new ArrayList<>(); // сообщения
    private ArrayList<Bitmap> images = new ArrayList<>(); // картинки для сообщения


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatRepository = new ChatRepositoryImpl(this);
        imageRepository = new ImageRepositoryImpl(this);

        // получение и установка данных
        getChatByIdUseCase = new GetChatByIdUseCase(chatRepository, getIntent().getStringExtra("chatId"), new OnGetDataListener<Chat>() {
            @Override
            public void onGetData(Chat data) {
                messages.clear();
                messages.addAll(data.getMessages());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onVoidData() {
                messages.clear();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed() {
                Toast.makeText(ChatActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled() {
                Toast.makeText(ChatActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
            }
        });
        getChatByIdUseCase.execute();

        // установка адаптера
        adapter = new MessageListAdapter(this, this, messages);
        binding.rvMessages.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMessages.setAdapter(adapter);
        adapter.setOnItemClickListener(new Adapter.OnStateClickListener<Message>() {
            @Override
            public void onClick(Message item, int position) {

            }

            @Override
            public void onLongClick(Message item, int position) {
                if(PresentationConfig.user.isAdmin()){
                    DialogConfirm dialog = new DialogConfirm(ChatActivity.this, "Удалнение сообщения", "Удалить", "Вы действительно хотите удалить сообщение?", new OnConfirmListener() {
                        @Override
                        public void onConfirm(DialogConfirm d) {
                            d.freeze();
                            removeMessageUseCase = new RemoveMessageUseCase(chatRepository, getIntent().getStringExtra("chatId"), position, new OnSetDataListener() {
                                @Override
                                public void onSetData() {
                                    Toast.makeText(ChatActivity.this, R.string.message_delete_success, Toast.LENGTH_SHORT).show();
                                    d.destroy();
                                }

                                @Override
                                public void onFailed() {
                                    Toast.makeText(ChatActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                    d.destroy();
                                }

                                @Override
                                public void onCanceled() {
                                    Toast.makeText(ChatActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                                    d.destroy();
                                }
                            });
                            removeMessageUseCase.execute();
                        }
                    });
                    dialog.create(R.id.fragmentContainerView);
                }
            }
        });

        // закрытие активности
        binding.btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // добавление картинки
        binding.btAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        // новое сообщение
        binding.btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(binding.etSend.getText()) || images.size() > 0) {
                    Message message = new Message(Objects.requireNonNull(binding.etSend.getText().toString()), PresentationConfig.getUser().getEmail(), null);
                    createNewMessageUseCase = new CreateNewMessageUseCase(chatRepository, imageRepository, getIntent().getStringExtra("chatId"), message, images, new OnSetDataListener() {
                        @Override
                        public void onSetData() {
                            images.clear();
                            binding.glImages.removeAllViews();
                            binding.etSend.setText("");
                        }

                        @Override
                        public void onFailed() {
                            Toast.makeText(ChatActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                            images.clear();
                            binding.glImages.removeAllViews();
                            binding.etSend.setText("");

                        }

                        @Override
                        public void onCanceled() {
                            Toast.makeText(ChatActivity.this, R.string.access_denied, Toast.LENGTH_SHORT).show();
                            images.clear();
                            binding.glImages.removeAllViews();
                            binding.etSend.setText("");
                        }
                    });
                    createNewMessageUseCase.execute();
                }
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
            iv.getLayoutParams().width = 300;
            iv.getLayoutParams().height = 300;
            Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
            images.add(bitmap);
            binding.glImages.addView(iv);
        }
    }
}