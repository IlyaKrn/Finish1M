package com.example.finish1m.Presentation.ui.slideshow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finish1m.Data.Firebase.LocateRepositoryImpl;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Models.Locate;
import com.example.finish1m.Domain.UseCases.GetLocateListUseCase;
import com.example.finish1m.Domain.UseCases.RefactorLocateUseCase;
import com.example.finish1m.Presentation.Adapters.MapInfoWindowAdapter;
import com.example.finish1m.Presentation.CreateNewLocateAciActivity;
import com.example.finish1m.Presentation.Dialogs.DialogConfirm;
import com.example.finish1m.Presentation.Dialogs.OnConfirmListener;
import com.example.finish1m.Presentation.PresentationConfig;
import com.example.finish1m.R;
import com.example.finish1m.databinding.FragmentSlideshowBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SlideshowFragment extends Fragment implements OnMapReadyCallback {

    private FragmentSlideshowBinding binding;

    private GoogleMap googleMap;
    private LocateRepositoryImpl locateRepository;
    private MapInfoWindowAdapter adapter;
    private GetLocateListUseCase getLocateListUseCase;

    private ArrayList<Locate> locates = new ArrayList<>();
    private boolean isAdd = false;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);

        locateRepository = new LocateRepositoryImpl(getContext());
        getLocateListUseCase = new GetLocateListUseCase(locateRepository, new OnGetDataListener<ArrayList<Locate>>() {
            @Override
            public void onGetData(ArrayList<Locate> data) {
                Map<Locate, Bitmap> cache = new HashMap<>();
                final int[] count = {0};
                for (Locate l : locates){
                    /*
                    l.getIconAsync(getContext(), new OnGetIcon() {
                        @Override
                        public void onLoad(Bitmap bitmap) {
                            count[0]++;
                            cache.put(l, bitmap);
                            Log.e("ffff", "fffffff");
                            if (locateMainList.size() == count[0])
                                mapAdapter.loadCache(cache);
                        }
                    });

                     */
                }
            }

            @Override
            public void onVoidData() {
                locates.clear();
                googleMap.clear();
                for (Locate l : locates) {
                    if (googleMap != null) {
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(l.getLatitude(), l.getLongitude())));
                    }
                }
            }


            @Override
            public void onFailed () {

            }

            @Override
            public void onCanceled () {

            }

        });
        getLocateListUseCase.execute();


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap gMap) {
        googleMap = gMap;
        googleMap.setInfoWindowAdapter(adapter);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(55.400135, 43.828324), 11));
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                if (isAdd) {
                    Intent intent = new Intent(getActivity(), CreateNewLocateAciActivity.class);
                    startActivity(intent);

                    isAdd = false;
                    binding.fabCancel.setVisibility(View.GONE);
                    binding.fabAddLocate.setVisibility(View.VISIBLE);
                }
            }
        });
        googleMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(@NonNull Marker marker) {
                if(!PresentationConfig.user.isAdmin()) {
                    DialogConfirm dialog = new DialogConfirm((AppCompatActivity) getActivity(), "Удаление метки", "Удалить", "Вы действительно хотите удалить метку?", new OnConfirmListener() {
                        @Override
                        public void onConfirm(DialogConfirm d) {
                            for (Locate l : locates) {
                                if (new LatLng(l.getLatitude(), l.getLongitude()).equals(marker.getPosition())) {

                                }
                            }
                        }
                    });
                    dialog.create(R.id.fragmentContainerView);
                }
            }
        });
    }
}