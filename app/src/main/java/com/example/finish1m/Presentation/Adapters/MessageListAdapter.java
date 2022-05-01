package com.example.finish1m.Presentation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.finish1m.Domain.Models.Message;

import java.util.ArrayList;

public class MessageListAdapter extends Adapter<Message, MessageListAdapter.ViewHolder>{
    public MessageListAdapter(Activity activity, Context context, ArrayList<Message> items) {
        super(activity, context, items);
    }

    @Override
    protected ViewHolder onCreateHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class ViewHolder extends Holder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bind(int position) {

        }
    }
}
