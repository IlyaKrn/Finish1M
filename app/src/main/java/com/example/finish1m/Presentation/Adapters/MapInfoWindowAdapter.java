package com.example.finish1m.Presentation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Map;

public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final User user;
    private final View rootView;
    private final Context context;
    private final ArrayList<Locate> locates;
    private boolean isAdmin;
    private Map<Locate, Bitmap> cache;
    private TextView title;
    private TextView snippet;
    private ImageView icon;
    private ProgressBar progress;
    private FrameLayout ivContainer;
    private ImageButton btMenu;
    private Activity activity;



    public MapInfoWindowAdapter(Context context, boolean isAdmin, User user, Activity activity, ArrayList<Locate> locates) {
        this.user = user;
        this.context = context;
        this.rootView = LayoutInflater.from(context).inflate(R.layout.map_info_window, null);
        this.locates = locates;
        this.activity = activity;
        this.isAdmin = isAdmin;
    }


    public void loadCache(Map<Locate, Bitmap> c){
        this.cache = c;
    }

    private void setData(Marker marker){
        title = rootView.findViewById(R.id.tv_title);
        snippet = rootView.findViewById(R.id.tv_snippet);
        icon = rootView.findViewById(R.id.icon);
        btMenu = rootView.findViewById(R.id.bt_menu);
        progress = rootView.findViewById(R.id.progress);
        ivContainer = rootView.findViewById(R.id.ll_iv_container);
        ivContainer.getLayoutParams().width = ivContainer.getHeight();

        /*
        btMenu.setVisibility(View.GONE);
        for(Locate l : locates){
            if (marker.getPosition().equals(l.locate())){
                title.setText(l.name);
                snippet.setText(l.description);
                rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(context, v);
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
                                                l.setNewData(context, null, new OnSetDataListener<Locate>() {
                                                    @Override
                                                    public void onSetData(Locate data) {
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
                                        DialogRefactorLocate d = new DialogRefactorLocate((AppCompatActivity) activity, l);
                                        d.create(R.id.fragmentContainerView);
                                        break;
                                }
                                return false;
                            }
                        });
                        popup.show();
                    }
                });
                if (cache != null && cache.get(l) != null)
                    icon.setImageBitmap(cache.get(l));
            }
        }

         */
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
