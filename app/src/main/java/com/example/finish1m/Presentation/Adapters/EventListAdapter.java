package com.example.finish1m.Presentation.Adapters;

import android.annotation.SuppressLint;
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
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finish1m.Data.Firebase.EventRepositoryImpl;
import com.example.finish1m.Data.Firebase.ImageRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.UseCases.GetEventByIdUseCase;
import com.example.finish1m.Domain.UseCases.GetImageByRefUseCase;
import com.example.finish1m.Domain.UseCases.RefactorEventUseCase;
import com.example.finish1m.Presentation.ChatActivity;
import com.example.finish1m.Presentation.Dialogs.DialogConfirm;
import com.example.finish1m.Presentation.Dialogs.OnConfirmListener;
import com.example.finish1m.Presentation.PresentationConfig;
import com.example.finish1m.Presentation.RefactorEventActivity;
import com.example.finish1m.Presentation.UserListActivity;
import com.example.finish1m.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventListAdapter extends Adapter<Event, EventListAdapter.ViewHolder> {

    private ImageRepositoryImpl imageRepository;
    private EventRepositoryImpl eventRepository;

    public EventListAdapter(Activity activity, Context context, ArrayList<Event> items) {
        super(activity, context, items);
        this.imageRepository = new ImageRepositoryImpl(context);
        this.eventRepository = new EventRepositoryImpl(context);
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
            btMenu = itemView.findViewById(R.id.bt_menu);
        }

        @Override
        public void bind(int position) {
            item = getItem(position);
            glImages.removeAllViews();
            btReg.setVisibility(View.VISIBLE);
            btChat.setVisibility(View.VISIBLE);
            btUsers.setVisibility(View.VISIBLE);
            btMenu.setVisibility(View.VISIBLE);
            btReg.setText("Вы записаны");

            boolean isRegistered = false;
            if (item.getMembers() != null) {
                for (String s : item.getMembers()) {
                    if (s.equals(PresentationConfig.user.getEmail()))
                        isRegistered = true;
                    break;
                }
            }

            if(!isRegistered){
                btChat.setVisibility(View.GONE);
                btUsers.setVisibility(View.GONE);
                btReg.setText("Записаться");
            }
            if (!PresentationConfig.user.isAdmin()){
                btMenu.setVisibility(View.GONE);
            }


            tvTitle.setText(item.getTitle());
            tvMessage.setText(item.getMessage());

            boolean finalIsRegistered = isRegistered;
            btReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!finalIsRegistered) {
                        DialogConfirm dialog = new DialogConfirm((AppCompatActivity) activity, "Запись", "Да", "Вы действительно хотите участвовать в мероприятии?", new OnConfirmListener() {
                            @Override
                            public void onConfirm(DialogConfirm d) {
                                d.freeze();
                                ArrayList<String> m = item.getMembers();
                                if (m == null)
                                    m = new ArrayList<>();
                                m.add(PresentationConfig.user.getEmail());
                                item.setMembers(m);
                                RefactorEventUseCase refactorEventUseCase = new RefactorEventUseCase(eventRepository, item, new OnSetDataListener() {
                                    @Override
                                    public void onSetData() {
                                        Toast.makeText(activity, ";ifyuldydtul", Toast.LENGTH_SHORT).show();
                                        d.destroy();
                                    }

                                    @Override
                                    public void onFailed() {
                                        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                                        d.destroy();
                                    }

                                    @Override
                                    public void onCanceled() {
                                        Toast.makeText(context, R.string.access_denied, Toast.LENGTH_SHORT).show();
                                        d.destroy();
                                    }
                                });
                                refactorEventUseCase.execute();
                            }

                        });
                        dialog.create(R.id.fragmentContainerView);
                    }
                }
            });
            btChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, ChatActivity.class);
                    intent.putExtra("chatId", item.getChatId());
                    activity.startActivity(intent);
                }
            });
            btUsers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(activity, UserListActivity.class);
                    intent.putExtra("users", item.getMembers());
                    activity.startActivity(intent);
                }
            });
            btMenu.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(View view) {
                    PopupMenu menu = new PopupMenu(context, view);
                    menu.inflate(R.menu.popup_menu_chat_list_item);
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch(menuItem.getItemId()) {
                                case R.id.refactor:
                                    Intent intent = new Intent(activity, RefactorEventActivity.class);
                                    intent.putExtra("eventId", item.getId());
                                    activity.startActivity(intent);
                                    break;
                            }

                            return false;
                        }
                    });
                    menu.show();
                }
            });


            GetEventByIdUseCase getEventListUseCase = new GetEventByIdUseCase(eventRepository, item.getId(), new OnGetDataListener<Event>() {
                @Override
                public void onGetData(Event data) {
                    if (item.getImageRefs() != null) {
                        for (int i = 0; i < item.getImageRefs().size(); i++) {
                            GetImageByRefUseCase getImageByRefUseCase = new GetImageByRefUseCase(imageRepository, item.getImageRefs().get(i), new OnGetDataListener<Bitmap>() {
                                @Override
                                public void onGetData(Bitmap data1) {
                                    if(item.getId().equals(data.getId()));{
                                        ImageView iv = new ImageView(context);
                                        iv.setImageBitmap(data1);
                                        glImages.addView(iv);
                                    }
                                }

                                @Override
                                public void onVoidData() {

                                }

                                @Override
                                public void onFailed() {
                                    Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCanceled() {
                                    Toast.makeText(context, R.string.access_denied, Toast.LENGTH_SHORT).show();
                                }
                            });
                            getImageByRefUseCase.execute();
                        }
                    }
                }

                @Override
                public void onVoidData() {
                    Toast.makeText(context, R.string.get_data_failed, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed() {
                    Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCanceled() {
                    Toast.makeText(context, R.string.access_denied, Toast.LENGTH_SHORT).show();
                }
            });
            getEventListUseCase.execute();
        }
    }
}
