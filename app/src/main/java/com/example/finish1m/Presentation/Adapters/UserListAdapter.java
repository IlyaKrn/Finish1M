package com.example.finish1m.Presentation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.finish1m.Data.Firebase.ImageRepositoryImpl;
import com.example.finish1m.Data.Firebase.UserRepositoryImpl;
import com.example.finish1m.Data.VK.VKImageRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.User;
import com.example.finish1m.Domain.UseCases.GetImageByRefUseCase;
import com.example.finish1m.Domain.UseCases.GetUserByEmailUseCase;
import com.example.finish1m.Domain.UseCases.RefactorUserUseCase;
import com.example.finish1m.Presentation.PresentationConfig;
import com.example.finish1m.Presentation.Views.IconView;
import com.example.finish1m.R;

import java.util.ArrayList;

// адаптер списка пользователей

public class UserListAdapter extends Adapter<User, UserListAdapter.ViewHolder> {

    private UserRepositoryImpl userRepository;
    private VKImageRepositoryImpl vkImageRepository;
    private ImageRepositoryImpl imageRepository;

    public UserListAdapter(Activity activity, Context context, ArrayList<User> items) {
        super(activity, context, items);
        userRepository = new UserRepositoryImpl(context);
        imageRepository = new ImageRepositoryImpl(context);
        vkImageRepository = new VKImageRepositoryImpl(context);
    }

    // создание холдера
    @Override
    protected ViewHolder onCreateHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_user, parent, false));
    }

    // холдер
    public class ViewHolder extends Adapter.Holder<User> {

        // элементы разметки
        private final ImageButton btMenu;
        private final ProgressBar progressImage;
        private final TextView tvName;
        private final TextView tvEmail;
        private final IconView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btMenu = itemView.findViewById(R.id.bt_item_menu);
            tvName = itemView.findViewById(R.id.tv_user_name);
            tvEmail = itemView.findViewById(R.id.tv_user_email);
            ivIcon = itemView.findViewById(R.id.user_icon);
            progressImage = itemView.findViewById(R.id.progress);
        }

        @Override
        public void bind(int position) {
            item = getItem(position); // получение элемента
            // установка данных
            ivIcon.setVisibility(View.GONE);
            progressImage.setVisibility(View.VISIBLE);
            btMenu.setVisibility(View.GONE);
            tvName.setText(item.getFirstName());
            tvEmail.setText(item.getEmail());


            // получение и установка картинок
            GetUserByEmailUseCase getUserByEmailUseCase = new GetUserByEmailUseCase(userRepository, item.getEmail(), new OnGetDataListener<User>() {
                @Override
                public void onGetData(User data) {
                    if (data.getEmail().equals(item.getEmail())){
                        GetImageByRefUseCase getImageByRefUseCase = new GetImageByRefUseCase(imageRepository, vkImageRepository, data.getIconRef(), new OnGetDataListener<Bitmap>() {
                            @Override
                            public void onGetData(Bitmap data1) {
                                if (data.getEmail().equals(item.getEmail())){
                                    ivIcon.setImageBitmap(data1);
                                    ivIcon.setVisibility(View.VISIBLE);
                                    progressImage.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onVoidData() {

                            }

                            @Override
                            public void onFailed() {

                            }

                            @Override
                            public void onCanceled() {

                            }
                        });
                        getImageByRefUseCase.execute();
                    }

                }

                @Override
                public void onVoidData() {

                }

                @Override
                public void onFailed() {

                }

                @Override
                public void onCanceled() {

                }
            });
            getUserByEmailUseCase.execute();

            // кнопка меню
            try {
                if(PresentationConfig.getUser().isAdmin()){
                    if (!PresentationConfig.getUser().getEmail().equals(item.getEmail())) {
                        btMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                PopupMenu popup = new PopupMenu(context, view);
                                popup.inflate(R.menu.popup_menu_user_list_item);
                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {
                                        switch (menuItem.getItemId()) {
                                            case R.id.set_admin:
                                                item.setAdmin(true);
                                                RefactorUserUseCase refactorUserUseCase = new RefactorUserUseCase(userRepository, item, new OnSetDataListener() {
                                                    @Override
                                                    public void onSetData() {
                                                        Toast.makeText(context, "Пользователь " + item.getEmail() + " стал администратором", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onFailed() {

                                                    }

                                                    @Override
                                                    public void onCanceled() {

                                                    }
                                                });
                                                refactorUserUseCase.execute();
                                                break;
                                            case R.id.remove_admin:
                                                item.setAdmin(false);
                                                RefactorUserUseCase refactorUserUseCase1 = new RefactorUserUseCase(userRepository, item, new OnSetDataListener() {
                                                    @Override
                                                    public void onSetData() {
                                                        Toast.makeText(context, "Пользователь " + item.getEmail() + " стал администратором", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onFailed() {

                                                    }

                                                    @Override
                                                    public void onCanceled() {

                                                    }
                                                });
                                                refactorUserUseCase1.execute();
                                                break;
                                            case R.id.ban:
                                                item.setBanned(true);
                                                RefactorUserUseCase refactorUserUseCase3 = new RefactorUserUseCase(userRepository, item, new OnSetDataListener() {
                                                    @Override
                                                    public void onSetData() {
                                                        Toast.makeText(context, "Пользователь " + item.getEmail() + " стал администратором", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onFailed() {

                                                    }

                                                    @Override
                                                    public void onCanceled() {

                                                    }
                                                });
                                                refactorUserUseCase3.execute();
                                                break;
                                            case R.id.unban:
                                                item.setBanned(false);
                                                RefactorUserUseCase refactorUserUseCase4 = new RefactorUserUseCase(userRepository, item, new OnSetDataListener() {
                                                    @Override
                                                    public void onSetData() {
                                                        Toast.makeText(context, "Пользователь " + item.getEmail() + " стал администратором", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onFailed() {

                                                    }

                                                    @Override
                                                    public void onCanceled() {

                                                    }
                                                });
                                                refactorUserUseCase4.execute();
                                                break;
                                        }
                                        return false;
                                    }
                                });
                                popup.show();
                            }
                        });
                        btMenu.setVisibility(View.VISIBLE);
                    }

                }
            } catch (Exception e) {
                Toast.makeText(context, R.string.data_load_error_try_again, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
