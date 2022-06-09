package com.example.finish1m.Data.VK;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.finish1m.Data.VK.Database.VKApiService;
import com.example.finish1m.Data.VK.Database.VKConfig;
import com.example.finish1m.Domain.Interfaces.Listeners.OnGetDataListener;
import com.example.finish1m.Domain.Interfaces.VKRepository;
import com.example.finish1m.Domain.Models.Event;
import com.example.finish1m.Domain.Models.WallModels.Attachment;
import com.example.finish1m.Domain.Models.WallModels.CopyHistory;
import com.example.finish1m.Domain.Models.WallModels.Item;
import com.example.finish1m.Domain.Models.WallModels.PostModel;
import com.example.finish1m.Domain.Models.WallModels.WallModel;
import com.example.finish1m.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VKRepositoryImpl implements VKRepository {

    private Context context;
    private static final String LOG_TAG = "VKRepositoryImpl";

    public VKRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getMainWall(OnGetDataListener<ArrayList<Event>> listener) {
        try {
            Gson gson = new GsonBuilder().create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(VKConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            VKApiService service = retrofit.create(VKApiService.class);
            service.getWallById(VKConfig.MAIN_WALL_ID, VKConfig.ACCESS_TOKEN).enqueue(new Callback<WallModel>() {
                @Override
                public void onResponse(@NonNull Call<WallModel> call, @NonNull Response<WallModel> response) {
                    if (context != null) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().response != null) {
                                Log.d(LOG_TAG, String.format("get wall is success (id='%s')", VKConfig.MAIN_WALL_ID));
                                ArrayList<Event> temp = new ArrayList<>();
                                if (response.body().response.items != null) {
                                    for (Item item : response.body().response.items) {
                                        String title = item.text.split("\n")[0];
                                        Event e = new Event(Event.DATA_SOURCE_VK + "" + item.id, Event.NEWS, Event.DATA_SOURCE_VK, title, item.text, null, item.date, null, null);

                                        ArrayList<String> iRefs = new ArrayList<>();
                                        if (item.attachments != null) {
                                            int position = 0;
                                            for (Attachment a : item.attachments) {
                                                if (a.type.equals("photo"))
                                                    iRefs.add(a.photo.sizes.get(a.photo.sizes.size() - 1).url);
                                                else {
                                                    Log.e(LOG_TAG, String.format("unsupported type of attachment '%s' (wallId=%s, eventId=%s, position=%d)", a.type, VKConfig.MAIN_WALL_ID, item.id, position));
                                                }
                                                position++;
                                            }
                                        }
                                        if (item.copyHistory != null) {
                                            for (CopyHistory copyHistory : item.copyHistory) {
                                                e.setMessage(e.getMessage() + "\n\n" + context.getString(R.string.copy_from_history) + copyHistory.fromId + ":\n\n" + copyHistory.text);
                                                if (copyHistory.attachments != null) {
                                                    int position = 0;
                                                    for (Attachment a : copyHistory.attachments) {
                                                        if (a.type.equals("photo"))
                                                            iRefs.add(a.photo.sizes.get(a.photo.sizes.size() - 1).url);
                                                        else {
                                                            Log.e(LOG_TAG, String.format("unsupported type of attachment '%s' (wallId=%s, eventId=%s, position=%d)", a.type, VKConfig.MAIN_WALL_ID, item.id, position));
                                                        }
                                                        position++;
                                                    }
                                                }
                                            }
                                        }

                                        e.setImageRefs(iRefs);
                                        temp.add(e);
                                    }
                                    if (context != null) {
                                        listener.onGetData(temp);
                                    }
                                } else {
                                    if (context != null) {
                                        Log.e(LOG_TAG, String.format("get wall is void data (id='%s': no data in database)", VKConfig.MAIN_WALL_ID));
                                        listener.onVoidData();
                                    }
                                }
                            } else {
                                if (context != null) {
                                    Log.e(LOG_TAG, String.format("get wall is void data (id='%s': no data in database)", VKConfig.MAIN_WALL_ID));
                                    listener.onVoidData();
                                }
                            }
                        } else {
                            if (context != null) {
                                listener.onFailed();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<WallModel> call, @NonNull Throwable throwable) {
                    if (context != null) {
                        Log.e(LOG_TAG, String.format("get wall is failed (id='%s'): %s", VKConfig.MAIN_WALL_ID, throwable.getMessage()));
                        listener.onFailed();
                    }
                }
            });
        } catch (Exception e) {
            if (context != null) {
                Log.e(LOG_TAG, String.format("get wall is failed (id='%s'): %s", VKConfig.MAIN_WALL_ID, e.getMessage()));
                listener.onFailed();
            }
        }

    }

    @Override
    public void getWallById(String id, OnGetDataListener<ArrayList<Event>> listener) {
        try {
            Gson gson = new GsonBuilder().create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(VKConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            VKApiService service = retrofit.create(VKApiService.class);
            service.getWallById(id, VKConfig.ACCESS_TOKEN).enqueue(new Callback<WallModel>() {
                @Override
                public void onResponse(@NonNull Call<WallModel> call, @NonNull Response<WallModel> response) {
                    if (context != null) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().response != null) {
                                Log.d(LOG_TAG, String.format("get wall is success (id='%s')", id));
                                ArrayList<Event> temp = new ArrayList<>();
                                if (response.body().response.items != null) {
                                    for (Item item : response.body().response.items) {
                                        String title = item.text.split("\n")[0];
                                        Event e = new Event(Event.DATA_SOURCE_VK + "" + item.id, Event.NEWS, Event.DATA_SOURCE_VK, title, item.text, null, item.date, null, null);

                                        ArrayList<String> iRefs = new ArrayList<>();
                                        if (item.attachments != null) {
                                            int position = 0;
                                            for (Attachment a : item.attachments) {
                                                if (a.type.equals("photo"))
                                                    iRefs.add(a.photo.sizes.get(a.photo.sizes.size() - 1).url);
                                                else {
                                                    Log.e(LOG_TAG, String.format("unsupported type of attachment '%s' (wallId=%s, eventId=%s, position=%d)", a.type, id, item.id, position));
                                                }
                                                position++;
                                            }
                                        }
                                        if (item.copyHistory != null) {
                                            for (CopyHistory copyHistory : item.copyHistory) {
                                                e.setMessage(e.getMessage() + "\n\n" + context.getString(R.string.copy_from_history) + copyHistory.fromId + ":\n\n" + copyHistory.text);
                                                if (copyHistory.attachments != null) {
                                                    int position = 0;
                                                    for (Attachment a : copyHistory.attachments) {
                                                        if (a.type.equals("photo"))
                                                            iRefs.add(a.photo.sizes.get(a.photo.sizes.size() - 1).url);
                                                        else {
                                                            Log.e(LOG_TAG, String.format("unsupported type of attachment '%s' (wallId=%s, eventId=%s, position=%d)", a.type, id, item.id, position));
                                                        }
                                                        position++;
                                                    }
                                                }
                                            }
                                        }

                                        e.setImageRefs(iRefs);
                                        temp.add(e);
                                    }
                                    listener.onGetData(temp);
                                } else {
                                    listener.onVoidData();
                                }
                            } else {
                                Log.e(LOG_TAG, String.format("get wall is void data (id='%s': no data in database)", id));
                                listener.onVoidData();
                            }
                        } else {
                            listener.onFailed();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<WallModel> call, Throwable throwable) {
                    if (context != null) {
                        Log.e(LOG_TAG, String.format("get wall is failed (id='%s'): %s", id, throwable.getMessage()));
                        listener.onFailed();
                    }
                }
            });
        } catch (Exception e) {
            if (context != null) {
                Log.e(LOG_TAG, String.format("get wall is failed (id='%s'): %s", id, e.getMessage()));
                listener.onFailed();
            }
        }
    }

    @Override
    public void getEventMainWallById(String eventId, OnGetDataListener<Event> listener) {
        try {

            eventId = eventId.substring(1);

            Gson gson = new GsonBuilder().create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(VKConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            VKApiService service = retrofit.create(VKApiService.class);
            String finalEventId = eventId;
            String finalEventId1 = eventId;
            service.getEventById(VKConfig.MAIN_WALL_ID, VKConfig.MAIN_WALL_ID + "_" + eventId, VKConfig.ACCESS_TOKEN).enqueue(new Callback<PostModel>() {
                @Override
                public void onResponse(@NonNull Call<PostModel> call, @NonNull Response<PostModel> response) {
                    if(context != null) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().response != null) {
                                if (response.body().response.items != null) {
                                    Event e = null;
                                    if (response.body().response.items.get(0) != null) {
                                        String title = response.body().response.items.get(0).text.split("\n")[0];
                                        e = new Event(Event.DATA_SOURCE_VK + "" + response.body().response.items.get(0).id, Event.NEWS, Event.DATA_SOURCE_VK, title, response.body().response.items.get(0).text, null, response.body().response.items.get(0).date, null, null);

                                        ArrayList<String> iRefs = new ArrayList<>();
                                        if (response.body().response.items.get(0).attachments != null) {
                                            int position = 0;
                                            for (Attachment a : response.body().response.items.get(0).attachments) {
                                                if (a.type.equals("photo"))
                                                    iRefs.add(a.photo.sizes.get(a.photo.sizes.size() - 1).url);
                                                else {
                                                    Log.e(LOG_TAG, String.format("unsupported type of attachment '%s' (wallId=%s, eventId=%s, position=%d)", a.type, VKConfig.MAIN_WALL_ID, finalEventId1, position));
                                                }
                                                position++;
                                            }
                                        }
                                        if (response.body().response.items.get(0).copyHistory != null) {
                                            for (CopyHistory copyHistory : response.body().response.items.get(0).copyHistory) {
                                                e.setMessage(e.getMessage() + "\n\n" + context.getString(R.string.copy_from_history) + copyHistory.fromId + ":\n\n" + copyHistory.text);
                                                if (copyHistory.attachments != null) {
                                                    int position = 0;
                                                    for (Attachment a : copyHistory.attachments) {
                                                        if (a.type.equals("photo"))
                                                            iRefs.add(a.photo.sizes.get(a.photo.sizes.size() - 1).url);
                                                        else {
                                                            Log.e(LOG_TAG, String.format("unsupported type of attachment '%s' (wallId=%s, eventId=%s, position=%d)", a.type, VKConfig.MAIN_WALL_ID, finalEventId1, position));
                                                        }
                                                        position++;
                                                    }
                                                }
                                            }
                                        }

                                        e.setImageRefs(iRefs);
                                        Log.d(LOG_TAG, String.format("get event from main wall is success (wallId='%s', eventId='%s')", VKConfig.MAIN_WALL_ID, finalEventId));
                                        listener.onGetData(e);

                                    }
                                    if (e == null) {
                                        Log.e(LOG_TAG, String.format("get event from main wall is void data (wallId='%s', eventId='%s'): no data in wall)", VKConfig.MAIN_WALL_ID, finalEventId));
                                        listener.onVoidData();
                                    }
                                }
                            } else {
                                Log.e(LOG_TAG, String.format("get event from main wall is void data (wallId='%s', eventId='%s'): no data in wall)", VKConfig.MAIN_WALL_ID, finalEventId));
                                listener.onVoidData();
                            }
                        } else {
                            Log.e(LOG_TAG, String.format("get event from main wall is cancelled (wallId='%s', eventId='%s'): Call not success", VKConfig.MAIN_WALL_ID, finalEventId));
                            listener.onCanceled();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PostModel> call, @NonNull Throwable throwable) {
                    if (context != null) {
                        Log.e(LOG_TAG, String.format("get event from main wall is failed (wallId='%s', eventId='%s'): %s", VKConfig.MAIN_WALL_ID, finalEventId, throwable.getMessage()));
                        listener.onFailed();
                    }
                }
            });
        } catch (Exception e) {
            if (context != null) {
                Log.e(LOG_TAG, String.format("get event from main wall is failed (wallId='%s', eventId='%s'): %s", VKConfig.MAIN_WALL_ID, eventId, e.getMessage()));
                listener.onFailed();
            }
        }
    }

    @Override
    public void getEventById(String wallId, String eventId, OnGetDataListener<Event> listener) {
        try {

            eventId = eventId.substring(1);
            Gson gson = new GsonBuilder().create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(VKConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            VKApiService service = retrofit.create(VKApiService.class);
            String finalEventId = eventId;
            String finalEventId1 = eventId;
            service.getEventById(wallId, wallId + "_" + eventId, VKConfig.ACCESS_TOKEN).enqueue(new Callback<PostModel>() {
                @Override
                public void onResponse(@NonNull Call<PostModel> call, @NonNull Response<PostModel> response) {
                    if (context != null) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().response != null) {
                                if (response.body().response.items != null) {
                                    Event e = null;
                                    if (response.body().response.items.get(0) != null) {
                                        String title = response.body().response.items.get(0).text.split("\n")[0];
                                        e = new Event(Event.DATA_SOURCE_VK + "" + response.body().response.items.get(0).id, Event.NEWS, Event.DATA_SOURCE_VK, title, response.body().response.items.get(0).text, null, response.body().response.items.get(0).date, null, null);

                                        ArrayList<String> iRefs = new ArrayList<>();
                                        if (response.body().response.items.get(0).attachments != null) {
                                            int position = 0;
                                            for (Attachment a : response.body().response.items.get(0).attachments) {
                                                if (a.type.equals("photo"))
                                                    iRefs.add(a.photo.sizes.get(a.photo.sizes.size() - 1).url);
                                                else {
                                                    Log.e(LOG_TAG, String.format("unsupported type of attachment '%s' (wallId=%s, eventId=%s, position=%d)", a.type, wallId, finalEventId1, position));
                                                }
                                                position++;
                                            }
                                        }
                                        if (response.body().response.items.get(0).copyHistory != null) {
                                            for (CopyHistory copyHistory : response.body().response.items.get(0).copyHistory) {
                                                e.setMessage(e.getMessage() + "\n\n" + context.getString(R.string.copy_from_history) + copyHistory.fromId + ":\n\n" + copyHistory.text);
                                                if (copyHistory.attachments != null) {
                                                    int position = 0;
                                                    for (Attachment a : copyHistory.attachments) {
                                                        if (a.type.equals("photo"))
                                                            iRefs.add(a.photo.sizes.get(a.photo.sizes.size() - 1).url);
                                                        else {
                                                            Log.e(LOG_TAG, String.format("unsupported type of attachment '%s' (wallId=%s, eventId=%s, position=%d)", a.type, wallId, finalEventId1, position));
                                                        }
                                                        position++;
                                                    }
                                                }
                                            }
                                        }

                                        e.setImageRefs(iRefs);
                                        Log.d(LOG_TAG, String.format("get event from main wall is success (wallId='%s', eventId='%s')", VKConfig.MAIN_WALL_ID, finalEventId));
                                        listener.onGetData(e);

                                    }
                                    if (e == null) {
                                        Log.e(LOG_TAG, String.format("get event from wall is void data (wallId='%s', eventId='%s'): no data in wall)", wallId, finalEventId));
                                        listener.onVoidData();
                                    }
                                }
                            } else {
                                Log.e(LOG_TAG, String.format("get event from wall is void data (wallId='%s', eventId='%s'): no data in wall)", wallId, finalEventId));
                                listener.onVoidData();
                            }
                        } else {
                            Log.e(LOG_TAG, String.format("get event from wall is cancelled (wallId='%s', eventId='%s'): Call not success", wallId, finalEventId));
                            listener.onCanceled();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PostModel> call, @NonNull Throwable throwable) {
                    if (context != null) {
                        Log.e(LOG_TAG, String.format("get event from wall is failed (wallId='%s', eventId='%s'): %s", wallId, finalEventId, throwable.getMessage()));
                        listener.onFailed();
                    }
                }
            });
        } catch (Exception e) {
            if (context != null) {
                Log.e(LOG_TAG, String.format("get event from wall is failed (wallId='%s', eventId='%s'): %s", wallId, eventId, e.getMessage()));
                listener.onFailed();
            }
        }
    }

}
