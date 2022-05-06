package com.example.finish1m.Presentation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.finish1m.R;

import java.util.ArrayList;

public class ImageListAdapter extends Adapter<Bitmap, ImageListAdapter.ViewHolder>{

    private OnItemRemoveListener onItemRemoveListener;

    public ImageListAdapter(Activity activity, Context context, ArrayList<Bitmap> items) {
        super(activity, context, items);
        this.onItemRemoveListener = new OnItemRemoveListener() {
            @Override
            public void onRemove(int position) {

            }
        };
    }

    public void setOnItemRemoveListener(OnItemRemoveListener onItemRemoveListener) {
        this.onItemRemoveListener = onItemRemoveListener;
    }

    @Override
    protected ViewHolder onCreateHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_image, parent, false));
    }

    public class ViewHolder extends Holder<Bitmap> {

        private ImageButton btRemove;
        private ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btRemove = itemView.findViewById(R.id.bt_remove);
            imageView = itemView.findViewById(R.id.iv_image);
        }

        @Override
        public void bind(int position) {
            item = getItem(position);

            imageView.setImageBitmap(item);
            btRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemRemoveListener.onRemove(position);
                }
            });
        }
    }

    public interface OnItemRemoveListener{
        void onRemove(int position);
    }
}
