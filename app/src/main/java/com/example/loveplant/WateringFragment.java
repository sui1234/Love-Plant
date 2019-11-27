package com.example.loveplant;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.loveplant.PlantFragment.dayDiff;

public class WateringFragment extends Fragment {


    View v;
    private RecyclerView recyclerView;
    private List<Watering> listWat;
    int spacingInPixels = 8;

    public WateringFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.watering_fragment,container,false);
        recyclerView = v.findViewById(R.id.watering_recyclerview);
        RecyclerViewAdopterW recyclerViewAdopter = new RecyclerViewAdopterW(getContext(), listWat);

        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdopter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<PlantInfo>plantInfos = MainActivity.myAppDatabase.myDao().getPlantInfo();
        listWat = new ArrayList<>();


        for(int i = 0 ; i < plantInfos.size(); i++){

            String timeNow = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            String timeStamp = plantInfos.get(i).getTimeStampe();
            String days = plantInfos.get(i).getDay();
            Integer diff = dayDiff(timeNow,timeStamp,"yyyyMMdd_HHmmss");
            Integer daysInt = Integer.valueOf(days);

            if( daysInt == diff){
                listWat.add(new Watering(plantInfos.get(i).getImage(), R.drawable.ic_watering_can,plantInfos.get(i).getName()));
                Log.d("sui getImage"," is" + plantInfos.get(i).getImage());

                addNotification();
                //listWat.add(new Watering(R.drawable.tree,R.drawable.ic_watering_can));
            }

        }

    }

    private void addNotification(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
        //Add channelId
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = "default";
            String channelName = "default channel";
            manager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH));
        }

        //set TaskStackBuilder
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(getActivity(), "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Watering")
                .setContentText("Please water your plants")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .build();

        manager.notify(1, notification);

    }
}

