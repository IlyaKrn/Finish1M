package com.example.finish1m.Presentation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.finish1m.Data.Firebase.ImageRepositoryImpl;
import com.example.finish1m.Data.Firebase.UserRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Models.Follow;
import com.example.finish1m.Domain.Models.User;
import com.example.finish1m.Domain.UseCases.GetImageByRefUseCase;
import com.example.finish1m.Domain.UseCases.GetUserByEmailUseCase;
import com.example.finish1m.Presentation.Views.TableMessageImages;
import com.example.finish1m.R;

import java.util.ArrayList;

// адаптер списка заявок

public class FollowListAdapter extends Adapter<Follow, FollowListAdapter.ViewHolder> {

    private ImageRepositoryImpl imageRepository;
    private UserRepositoryImpl userRepository;
    public FollowListAdapter(Activity activity, Context context, ArrayList<Follow> items) {
        super(activity, context, items);
        imageRepository = new ImageRepositoryImpl(context);
        userRepository = new UserRepositoryImpl(context);
    }

    @Override
    protected ViewHolder onCreateHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_folow, parent, false));
    }

    public class ViewHolder extends Holder<Follow> {


        private TextView tvEmail;
        private TextView tvName;
        private TextView tvMessage;
        private TableMessageImages glImages;

        private ImageView ivImage;
        private ProgressBar progressImage;

        private User u;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_user_name);
            tvEmail = itemView.findViewById(R.id.tv_user_email);
            tvMessage = itemView.findViewById(R.id.tv_message);
            glImages = itemView.findViewById(R.id.gl_images);
            ivImage = itemView.findViewById(R.id.icon);
            progressImage = itemView.findViewById(R.id.progress);

        }

        @Override
        public void bind(int position) {
            item = getItem(position);
            tvEmail.setText(item.getUserEmail());
            tvMessage.setText(item.getMessage());

            glImages.removeBitmaps();

            // получение и установка данных

            if (item.getImageRefs() != null){
                GetUserByEmailUseCase getUserByEmailUseCase = new GetUserByEmailUseCase(userRepository, item.getUserEmail(), new OnGetDataListener<User>() {
                    @Override
                    public void onGetData(User data) {
                        if(item.getUserEmail().equals(data.getEmail())) {
                            for (String ref : item.getImageRefs()) {
                                GetImageByRefUseCase getImageByRefUseCase = new GetImageByRefUseCase(imageRepository, ref, new OnGetDataListener<Bitmap>() {
                                    @Override
                                    public void onGetData(Bitmap data) {
                                        glImages.addImage(data);
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

            }

            ivImage.setVisibility(View.GONE);
            progressImage.setVisibility(View.VISIBLE);
            tvMessage.setText(item.getMessage());


            if (item.getMessage().length() > 200)
                tvMessage.setText(item.getMessage().substring(0, 200) + " \nЧитать дальше");
            final boolean[] isHide = {true};
            tvMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isHide[0]){
                        isHide[0] = false;
                        if (item.getMessage().length() > 200)
                            tvMessage.setText(item.getMessage() + " \nСкрыть");
                    }
                    else {
                        isHide[0] = true;
                        if (item.getMessage().length() > 200)
                            tvMessage.setText(item.getMessage().substring(0, 200) + " \nЧитать дальше");
                    }
                }
            });

            GetUserByEmailUseCase getUserByEmailUseCase = new GetUserByEmailUseCase(userRepository, item.getUserEmail(), new OnGetDataListener<User>() {
                @Override
                public void onGetData(User data) {
                    u = data;
                    if (u.getEmail().equals(item.getUserEmail())) {
                        tvName.setText(data.getFirstName());
                        GetImageByRefUseCase getImageByRefUseCase = new GetImageByRefUseCase(imageRepository, data.getIconRef(), new OnGetDataListener<Bitmap>() {
                            @Override
                            public void onGetData(Bitmap data) {
                                if (u.getEmail().equals(item.getUserEmail())) {
                                    ivImage.setImageBitmap(data);
                                    ivImage.setVisibility(View.VISIBLE);
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


        }
    }
}
