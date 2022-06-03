package com.example.finish1m.Data.Firebase;

import static com.example.finish1m.Data.Firebase.Database.FirebaseConfig.DATABASE_LOCATE;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

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
                        if(temp.size() > 0)
                            listener.onGetData(temp);
                        else
                            listener.onVoidData();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    if (context != null) {
                        listener.onCanceled();
                    }
                }
            });
        } catch (Exception e){
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
                            listener.onGetData(l);
                        }
                        else {
                            listener.onVoidData();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    if (context != null) {
                        listener.onCanceled();
                    }
                }
            });
        } catch (Exception e){
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

            locate.setAddress(address);

            FirebaseDatabase.getInstance().getReference(DATABASE_LOCATE).child(id).setValue(locate).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (context != null) {
                        if(task.isSuccessful())
                            listener.onSetData();
                        else
                            listener.onCanceled();
                    }
                }
            });
        } catch (Exception e){
            listener.onFailed();
        }
    }

    // получение нового id
    @Override
    public String getNewId() {
        return FirebaseDatabase.getInstance().getReference(FirebaseConfig.DATABASE_LOCATE).push().getKey();
    }
}
