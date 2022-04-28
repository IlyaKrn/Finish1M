package com.example.finish1m.Presentation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Presentation.Dialogs.DialogConfirm;
import com.example.finish1m.Presentation.Dialogs.OnConfirmListener;
import com.example.finish1m.Presentation.PresentationConfig;
import com.example.finish1m.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventListAdapter extends Adapter<Event, EventListAdapter.ViewHolder> {
    private Map<String, Bitmap> cache = new HashMap<>();

    public EventListAdapter(Activity activity, Context context, ArrayList<Event> items) {
        super(activity, context, items);
    }

    @Override
    protected ViewHolder onCreateHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_event, parent, false));
    }

    public class ViewHolder extends Adapter.Holder<Event> {

        private Button btReg;
        private TextView tvTitle;
        private TextView tvMessage;
        private GridLayout glImages;
        private ProgressBar progressBar;
        private Button btUsers;
        private ImageButton btMenu;
        private Button btChat;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btReg = itemView.findViewById(R.id.bt_reg);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvMessage = itemView.findViewById(R.id.tv_message);
            glImages = itemView.findViewById(R.id.gl_images);
            progressBar = itemView.findViewById(R.id.progress);
            btUsers = itemView.findViewById(R.id.bt_users);
            btChat = itemView.findViewById(R.id.bt_chat);
        }

        @Override
        public void bind(int position) {
            item = getItem(position);
            tvTitle.setText(item.getTitle());
            btReg.setVisibility(View.VISIBLE);
            btUsers.setVisibility(View.VISIBLE);
            tvMessage.setText(item.getMessage());
            if (item.getType() == Event.NEWS) {
                btReg.setVisibility(View.GONE);
                btUsers.setVisibility(View.GONE);
            }
            btReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogConfirm dialog = new DialogConfirm((AppCompatActivity) activity, "Запись", "Да", "Вы действительно хотите участвовать в мероприятии?", new OnConfirmListener() {
                        @Override
                        public void onConfirm(DialogConfirm d) {
                            d.freeze();
                            item.getMembers().add(PresentationConfig.user.getEmail());
                        }

                    });
                    dialog.create(R.id.fragmentContainerView);

                }
            });
       /*     ivImage.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            Event.getEventById(context, item.id, new OnGetDataListener<Event>() {
                @Override
                public void onGetData(Event data) {
                    if (cache.get(item.id) != null) {
                        if (data.id.equals(item.id)) {
                            ivImage.setImageBitmap(cache.get(item.id));
                            ivImage.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        data.getIconAsync(context, new OnGetIcon() {
                            @Override
                            public void onLoad(Bitmap bitmap) {
                                cache.put(data.id, bitmap);
                                if (data.id.equals(item.id)) {
                                    ivImage.setImageBitmap(bitmap);
                                    ivImage.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    if (data.iconRef == null){
                                        ivImage.setVisibility(View.GONE);
                                    }
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
            });


            btReg.setText("Записаться");
            for (String s : item.userIds){
                if (s.equals(user.id)){
                    btReg.setText("Вы записаны");
                    btReg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            }

            btUsers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, UserListActivity.class);
                    intent.putExtra("users", item.userIds);
                    intent.putExtra("user", user);
                    activity.startActivity(intent);
                }
            });

            btChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(activity, ChatActivity.class);
                    i.putExtra(CHAT_ID, item.chatId);
                    i.putExtra(USER_INTENT, user);

                    activity.startActivity(i);
                }
            });
            itemView.findViewById(R.id.bt_menu).setVisibility(View.GONE);

            btChat.setVisibility(View.GONE);
            for (String s : item.userIds){
                if (s.equals(user.id)){
                    btChat.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void bindAdmin(int position) {
            btMenu = itemView.findViewById(R.id.bt_menu);
            itemView.findViewById(R.id.bt_menu).setVisibility(View.VISIBLE);
            btMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(activity, v);
                    popup.inflate(R.menu.popup_menu_event_admin);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                // ямы на дорогах
                                case R.id.delete:
                                    DialogConfirm dialog = new DialogConfirm((AppCompatActivity) activity, "Удалить", "Удалить", "Вы действительно хотите удалить ", new OnConfirmListener() {
                                        @Override
                                        public void onConfirm(DialogConfirm d) {
                                            d.freeze();
                                            item.setNewData(context, null, new OnSetDataListener<Event>() {
                                                @Override
                                                public void onSetData(Event data) {
                                                    d.destroy();
                                                }

                                                @Override
                                                public void onNoConnection() {

                                                }

                                                @Override
                                                public void onCanceled() {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancel(DialogConfirm d) {
                                            d.destroy();
                                        }
                                    });
                                    dialog.create(R.id.fragmentContainerView);
                                    break;
                                // лужи
                                case R.id.refactor:
                                    DialogRefactorEvent d = new DialogRefactorEvent((AppCompatActivity) activity, item);
                                    d.create(R.id.fragmentContainerView);
                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();
                }
            });

        */
        }
    }
}
