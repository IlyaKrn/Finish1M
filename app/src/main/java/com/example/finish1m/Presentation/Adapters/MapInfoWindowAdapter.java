package com.example.finish1m.Presentation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.finish1m.Domain.Models.Locate;
import com.example.finish1m.Domain.Models.User;
import com.example.finish1m.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View rootView;
    private final Context context;
    private final ArrayList<Locate> locates;
    private Map<Locate, ArrayList<Bitmap>> cache = new HashMap<>();

    private GridLayout glImages;
    private TextView title;
    private TextView snippet;
    private Activity activity;



    public MapInfoWindowAdapter(Activity activity, Context context, ArrayList<Locate> locates) {
        this.context = context;
        this.rootView = LayoutInflater.from(context).inflate(R.layout.map_info_window, null);
        this.locates = locates;
        this.activity = activity;
    }


    public void loadCache(Map<Locate, ArrayList<Bitmap>> c){
        this.cache = c;
    }

    private void setData(Marker marker){
        title = rootView.findViewById(R.id.tv_title);
        snippet = rootView.findViewById(R.id.tv_snippet);
        glImages = rootView.findViewById(R.id.gl_images);

        glImages.removeAllViews();

        for(Locate l : locates){
            if (marker.getPosition().equals(new LatLng(l.getLatitude(), l.getLongitude()))){
                title.setText(l.getTitle());
                snippet.setText(l.getMessage());
                if (l.getImageRefs() != null) {
                    for (String s : l.getImageRefs()){
                        if (cache != null && cache.get(l) != null) {
                            for (Bitmap b : cache.get(l)) {
                                ImageView iv = new ImageView(context);
                                iv.setImageBitmap(b);
                                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(300, 300);
                                iv.setLayoutParams(p);
                                glImages.addView(iv);
                            }
                        }
                    }
                }


            }
        }

    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        setData(marker);
        return rootView;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        setData(marker);
        return rootView;
    }
}
