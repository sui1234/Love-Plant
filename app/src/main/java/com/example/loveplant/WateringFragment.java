package com.example.loveplant;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static com.example.loveplant.PlantFragment.dayDiff;

public class WateringFragment extends Fragment implements RecyclerViewAdopterW.OnItemListener {


    View v;
    private RecyclerView recyclerView;
    private List<Watering> listWat;
    int spacingInPixels = 8;
    private int positionItemClick;
    private int positionW;

    public WateringFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.watering_fragment,container,false);
        recyclerView = v.findViewById(R.id.watering_recyclerview);
        RecyclerViewAdopterW recyclerViewAdopter = new RecyclerViewAdopterW(getContext(), listWat,this);

        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdopter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showPlanInfo();
    }

    @Override
    public void onItemClick(int position) {

        positionItemClick = position;
        alertDialog();
    }

    public void alertDialog(){

        new AlertDialog.Builder(getContext())
                .setTitle("Watering")
                .setMessage("Have you watered this plant?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        updateWateringTime();

                        showPlanInfo();

                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
    public void updateWateringTime(){

        List<PlantInfo> plantInfos = MainActivity.myAppDatabase.myDao().getPlantInfo();

        for(int i = 0 ; i < plantInfos.size(); i++){

            if(plantInfos.get(i).getName().equals(listWat.get(positionItemClick).getName())){
                positionW = i;
            }
        }

        PlantInfo plant = plantInfos.get(positionW);

        String timeNow = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

        plant.setTimeStampe(timeNow);

        MainActivity.myAppDatabase.myDao().updateTimeStamp(plant);
        Toast.makeText(getActivity(),"timeStamp updated..",Toast.LENGTH_SHORT).show();

    }

    public void showPlanInfo(){

        List<PlantInfo>plantInfos = MainActivity.myAppDatabase.myDao().getPlantInfo();
        listWat = new ArrayList<>();
        for(int i = 0 ; i < plantInfos.size(); i++){

            String timeNow = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            String timeStamp = plantInfos.get(i).getTimeStampe();
            String days = plantInfos.get(i).getDay();
            Integer diff = dayDiff(timeNow,timeStamp,"yyyyMMdd_HHmmss");
            Integer daysInt = Integer.valueOf(days);

            if( daysInt <= diff){
                listWat.add(new Watering(plantInfos.get(i).getImage(), R.drawable.ic_watering_can,plantInfos.get(i).getName()));
                Log.d("sui getImage"," is" + plantInfos.get(i).getImage());

                //listWat.add(new Watering(R.drawable.tree,R.drawable.ic_watering_can));
            }
        }
    }


   /* private void addNotification(){
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

    }*/
}

