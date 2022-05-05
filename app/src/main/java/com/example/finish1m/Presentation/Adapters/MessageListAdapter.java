package com.example.finish1m.Presentation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.finish1m.Data.Firebase.EventRepositoryImpl;
import com.example.finish1m.Data.Firebase.ImageRepositoryImpl;
import com.example.finish1m.Data.Firebase.UserRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.UserRepository;
import com.example.finish1m.Domain.Models.Message;
import com.example.finish1m.Domain.Models.User;
import com.example.finish1m.Presentation.PresentationConfig;
import com.example.finish1m.Presentation.Views.IconView;
import com.example.finish1m.Presentation.Views.TableMessageImages;
import com.example.finish1m.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageListAdapter extends Adapter<Message, MessageListAdapter.ViewHolder>{

    private ImageRepositoryImpl imageRepository;
    private UserRepository userRepository;

    private Map<String, Bitmap> savedIcons = new HashMap<>(); // кэш картинок
    private Map<String, ArrayList<Bitmap>> savedImages = new HashMap<>(); // кэш картинок из сообщений


    public MessageListAdapter(Activity activity, Context context, ArrayList<Message> items) {
        super(activity, context, items);
        this.imageRepository = new ImageRepositoryImpl(context);
        this.userRepository = new UserRepositoryImpl(context);
    }

    @Override
    protected ViewHolder onCreateHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_message, parent, false));
    }

    public class ViewHolder extends Holder<Message> {


        private final TableMessageImages notMy_tlImages;
        private final ProgressBar notMy_progressImage;
        private final TextView notMy_tvMessage;
        private final TextView notMy_tvName;
        private final IconView notMy_ivIcon;
        private final TextView notMy_tvDate;
        private final View notMy_itemBody;

        private final TableMessageImages my_tlImages;
        private final View my_itemBody;
        private final TextView my_tvMessage;
        private final TextView my_tvName;
        private final TextView my_tvDate;

        private final View system_itemBody;
        private final TextView system_tvMessage;
        private final TableMessageImages system_tlImages;

        User u; // пользователь

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notMy_tvMessage = this.itemView.findViewById(R.id.not_my_tv_message);
            notMy_tvName = this.itemView.findViewById(R.id.not_my_tv_user_name);
            notMy_ivIcon = this.itemView.findViewById(R.id.not_my_user_icon);
            notMy_tvDate = this.itemView.findViewById(R.id.not_my_tv_date);
            notMy_itemBody = this.itemView.findViewById(R.id.not_my_item_body);
            notMy_progressImage = this.itemView.findViewById(R.id.not_my_progress);
            notMy_tlImages = itemView.findViewById(R.id.not_my_lv_images);

            my_itemBody = this.itemView.findViewById(R.id.my_item_body);
            my_tvMessage = this.itemView.findViewById(R.id.my_tv_message);
            my_tvName = this.itemView.findViewById(R.id.my_tv_user_name);
            my_tvDate = this.itemView.findViewById(R.id.my_tv_date);
            my_tlImages = itemView.findViewById(R.id.my_lv_images);

            system_itemBody = this.itemView.findViewById(R.id.system_item_body);
            system_tvMessage = this.itemView.findViewById(R.id.system_tv_message);
            system_tlImages = itemView.findViewById(R.id.system_lv_images);

        }

        @Override
        public void bind(int position) {
            item = getItem(position);

            // установка данных
            system_tlImages.removeBitmaps();
            notMy_tlImages.removeBitmaps();
            my_tlImages.removeBitmaps();
            if (item.getImageRefs() != null){
                if (savedImages.get(item.getMessage()) != null){
                    ArrayList<Bitmap> bitmaps = savedImages.get(item.getMessage());
                    if (item.getUserEmail() != null){
                        if (item.getUserEmail().equals(PresentationConfig.user.getEmail())){
                            my_tlImages.setBitmaps(bitmaps);
                        }
                        else {
                            notMy_tlImages.setBitmaps(bitmaps);
                        }
                    }
                    else {
                        system_tlImages.setBitmaps(bitmaps);
                    }
                }
                else {
                    /*
                    item.getIconsAsync(context, new OnGetIcons() {
                        @Override
                        public void onGet(ArrayList<Bitmap> bitmaps, Message message) {
                            savedImages.put(message.id, bitmaps);
                            if (item.equals(message)) {
                                if (item.userId != null){
                                    if (item.userId.equals(user.id)){
                                        my_tlImages.setBitmaps(bitmaps);
                                    }
                                    else {
                                        notMy_tlImages.setBitmaps(bitmaps);
                                    }
                                }
                                else {
                                    system_tlImages.setBitmaps(bitmaps);
                                }
                            }
                        }
                    });

                     */

                }
            }
            else {
                system_tlImages.removeBitmaps();
                notMy_tlImages.removeBitmaps();
                my_tlImages.removeBitmaps();
            }



            if (item.getUserEmail() != null) {
                if (item.getUserEmail().equals(PresentationConfig.user.getEmail())) {
                    showMyMessage();
                    my_tvMessage.setText(item.getMessage());
                    my_tvName.setText(R.string.my_message_name);

                }
                else {
                    showNotMyMessage();
                    notMy_ivIcon.setVisibility(View.GONE);
                    notMy_progressImage.setVisibility(View.VISIBLE);
                    notMy_tvMessage.setText(item.getMessage());
                    /*
                    User.getUserById(context, item.userId, new OnGetDataListener<User>() {
                        @Override
                        public void onGetData(User data) {
                            u = data;
                            if (u.id.equals(item.userId))
                                notMy_tvName.setText(user.name);
                            if (savedIcons.get(u.id) != null){
                                if (u.id.equals(item.userId)) {
                                    notMy_ivIcon.setImageBitmap(savedIcons.get(u.id));
                                    notMy_ivIcon.setVisibility(View.VISIBLE);
                                    notMy_progressImage.setVisibility(View.GONE);
                                }
                            }
                            else {
                                user.getIconAsync(context, new OnGetIcon() {
                                    @Override
                                    public void onLoad(Bitmap bitmap) {
                                        savedIcons.put(u.id, bitmap);
                                        if (u.id.equals(item.userId)) {
                                            notMy_ivIcon.setImageBitmap(bitmap);
                                            notMy_ivIcon.setVisibility(View.VISIBLE);
                                            notMy_progressImage.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onVoidData() {

                        }

                        @Override
                        public void onNoConnection() {

                        }

                        @Override
                        public void onCanceled() {

                        }
                    });*/
                }



            }
            else {
                showSystemMessage();
                system_tvMessage.setText(item.getMessage());
            }
        }


        private void showNotMyMessage(){
            notMy_itemBody.setVisibility(View.VISIBLE);
            my_itemBody.setVisibility(View.GONE);
            system_itemBody.setVisibility(View.GONE);
        }
        private void showMyMessage(){
            notMy_itemBody.setVisibility(View.GONE);
            my_itemBody.setVisibility(View.VISIBLE);
            system_itemBody.setVisibility(View.GONE);
        }
        private void showSystemMessage(){
            notMy_itemBody.setVisibility(View.GONE);
            my_itemBody.setVisibility(View.GONE);
            system_itemBody.setVisibility(View.VISIBLE);
        }
    }
}
