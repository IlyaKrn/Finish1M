package com.example.finish1m.Data.Firebase;

import static com.example.finish1m.Data.Firebase.Database.FirebaseConfig.DATABASE_LOCATE;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.finish1m.Data.Firebase.Database.FirebaseConfig;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.Listeners.OnSetDataListener;
import com.example.finish1m.Domain.Interfaces.LocateRepository;
import com.example.finish1m.Domain.Models.Locate;
import com.example.finish1m.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocateRepositoryImpl implements LocateRepository {

    private Context context;
    private static final String LOG_TAG = "LocateRepositoryImpl";

    public LocateRepositoryImpl(Context context) {
        this.context = context;
    }

    // получение списка меток
    @Override
    public void getLocateList(OnGetDataListener<ArrayList<Locate>> listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_LOCATE).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Locate> temp = new ArrayList<>();
                    if (context != null) {
                        for (DataSnapshot s : snapshot.getChildren()){
                            Locate l = s.getValue(Locate.class);
                            assert l != null;
                            temp.add(l);
                        }
                        if(temp.size() > 0) {
                            Log.d(LOG_TAG, String.format("get locate list is success (size='%d')", temp.size()));
                            listener.onGetData(temp);
                        }
                        else{
                            Log.e(LOG_TAG, String.format("get locate list is void data (size='%d': no data in database)", 0));
                            listener.onVoidData();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    if (context != null) {
                        Log.e(LOG_TAG, String.format("get locate list is cancelled (size='%d'): %s", 0, error.getMessage()));
                        listener.onCanceled();
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("get event locate is failed (size='%d'): %s", 0, e.getMessage()));
            listener.onFailed();
        }
    }

    // получение метки по id
    @Override
    public void getLocateById(String locateId, OnGetDataListener<Locate> listener) {
        try {
            FirebaseDatabase.getInstance().getReference(DATABASE_LOCATE).child(locateId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (context != null) {
                        Locate l = snapshot.getValue(Locate.class);
                        if (l != null) {
                            Log.d(LOG_TAG, String.format("get locate is success (id='%s')", locateId));
                            listener.onGetData(l);
                        }
                        else {
                            Log.e(LOG_TAG, String.format("get locate is void data (id='%s': no data in database)", locateId));
                            listener.onVoidData();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    if (context != null) {
                        Log.e(LOG_TAG, String.format("get locate is cancelled (id='%s'): %s", locateId, error.getMessage()));
                        listener.onCanceled();
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("get locate is failed (id='%s'): %s", locateId, e.getMessage()));
            listener.onFailed();
        }
    }

    // запись данных в метку по id
    @Override
    public void setLocate(String id, Locate locate, OnSetDataListener listener) {
        try {
            String address = "";
            try {
                Geocoder geo = new Geocoder(context.getApplicationContext(), Locale.getDefault());
                List<Address> addresses = geo.getFromLocation(locate.getLatitude(), locate.getLongitude(), 1);
                if (addresses.isEmpty()) {
                    address = context.getString(R.string.no_locate_address_data);
                }
                else {
                    if (addresses.size() > 0) {
                        if(!addresses.get(0).getAdminArea().equals("null")) {
                            if(!addresses.get(0).getLocality().equals("null") || !addresses.get(0).getThoroughfare().equals("null") || !addresses.get(0).getFeatureName().equals("null"))
                                address += addresses.get(0).getAdminArea() + ", ";
                            else
                                address += addresses.get(0).getAdminArea();
                        }

                        if(!addresses.get(0).getLocality().equals("null")) {
                            if(!addresses.get(0).getThoroughfare().equals("null") || !addresses.get(0).getFeatureName().equals("null"))
                                address += addresses.get(0).getLocality() + ", ";
                            else
                                address += addresses.get(0).getLocality();
                        }

                        if(!addresses.get(0).getThoroughfare().equals("null")) {
                            if(!addresses.get(0).getFeatureName().equals("null"))
                                address += addresses.get(0).getThoroughfare() + ", ";
                            else
                                address += addresses.get(0).getThoroughfare();
                        }
                        if(!addresses.get(0).getFeatureName().equals("null")) {
                            address += addresses.get(0).getFeatureName();
                        }


                    }
                }
            } catch (Exception e){
                address = context.getString(R.string.no_locate_address_data);
            }
            if (locate != null) {
                locate.setAddress(address);
            }

            FirebaseDatabase.getInstance().getReference(DATABASE_LOCATE).child(id).setValue(locate).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (context != null) {
                        if(task.isSuccessful()) {
                            Log.d(LOG_TAG, String.format("write locate is success (id='%s')", id));
                            listener.onSetData();
                        }
                        else {
                            Log.e(LOG_TAG, String.format("write locate is cancelled (id='%s'): %s", id, task.getException().getMessage()));
                            listener.onCanceled();
                        }
                    }
                }
            });
        } catch (Exception e){
            Log.e(LOG_TAG, String.format("write locate is failed (id='%s'): %s", id, e.getMessage()));
            listener.onFailed();
        }
    }

    // получение нового id
    @Override
    public String getNewId() {
        return FirebaseDatabase.getInstance().getReference(FirebaseConfig.DATABASE_LOCATE).push().getKey();
    }
}
