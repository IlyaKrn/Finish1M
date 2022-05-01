package com.example.finish1m.Presentation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.finish1m.Data.Firebase.EventRepositoryImpl;
import com.example.finish1m.Data.Firebase.ImageRepositoryImpl;
import com.example.finish1m.Data.Firebase.UserRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.UserRepository;
import com.example.finish1m.Domain.Models.Message;
import com.example.finish1m.R;

import java.util.ArrayList;

public class MessageListAdapter extends Adapter<Message, MessageListAdapter.ViewHolder>{

    private ImageRepositoryImpl imageRepository;
    private UserRepository userRepository;

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

        private TextView tvMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);
        }

        @Override
        public void bind(int position) {
            item = getItem(position);
            tvMessage.setText(item.getMessage());
        }
    }
}
