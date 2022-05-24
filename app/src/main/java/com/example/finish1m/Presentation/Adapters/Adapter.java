package com.example.finish1m.Presentation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// абстрактный адаптер для списка

public abstract class Adapter<T, VH extends Adapter.Holder> extends RecyclerView.Adapter<VH> {

    protected Activity activity;
    protected Context context;
    protected ArrayList<T> items = new ArrayList<T>();
    protected OnStateClickListener<T> onItemClickListener;

    public Adapter(Activity activity, Context context, ArrayList<T> items) {
        this.activity = activity;
        this.context = context;
        this.items = items;
        this.onItemClickListener = new OnStateClickListener<T>() {
            @Override
            public void onClick(T item, int position) {

            }

            @Override
            public void onLongClick(T item, int position) {

            }
        };
    }

    public void setOnItemClickListener(OnStateClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return onCreateHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(items.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemClickListener.onLongClick(items.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    protected abstract VH onCreateHolder(@NonNull ViewGroup parent, int viewType);
    protected final T getItem(int position){
        return items.get(position);
    }

    // абстрактный холдер для элемента списка
    public abstract static class Holder<T> extends RecyclerView.ViewHolder {

        protected T item;

        public Holder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void bind(int position);
    }

    // слушатель нажатия на элемент списка
    public interface OnStateClickListener<T> {
        void onClick(T item, int position);
        void onLongClick(T item, int position);
    }

}
