package com.example.finish1m.Presentation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.finish1m.Data.Firebase.ImageRepositoryImpl;
import com.example.finish1m.Data.Firebase.UserRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Models.Follow;
import com.example.finish1m.Domain.UseCases.GetImageByRefUseCase;
import com.example.finish1m.R;

import java.util.ArrayList;

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
        private TextView tvMessage;
        private GridLayout glImages;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvMessage = itemView.findViewById(R.id.tv_message);
            glImages = itemView.findViewById(R.id.gl_images);
        }

        @Override
        public void bind(int position) {
            item = getItem(position);
            tvEmail.setText(item.getUserEmail());
            tvMessage.setText(item.getMessage());

            if (item.getImageRefs() != null){
                for (String ref : item.getImageRefs()){
                    GetImageByRefUseCase getImageByRefUseCase = new GetImageByRefUseCase(imageRepository, ref, new OnGetDataListener<Bitmap>() {
                        @Override
                        public void onGetData(Bitmap data) {
                            ImageView iv = new ImageView(context);
                            iv.setImageBitmap(data);
                            glImages.addView(iv);
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
    }
}
