package com.example.loveplant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlantFragment extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private List<Plant> listPlants;
    int spacingInPixels = 8;
    private RecyclerViewAdopterP recyclerViewAdopterP;
    private int position;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.my_plants_fragment, container, false);
        recyclerView = v.findViewById(R.id.my_plants_recyclerview);
        recyclerViewAdopterP = new RecyclerViewAdopterP(getContext(), listPlants);

        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdopterP);

        //swipe to remove items
        itemTouchHelper();

        Log.d("Sui plantrecycleView","is loading");
        return v;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPlants = new ArrayList<>();
        Log.d("Sui getdata","from database");

        //get data from database and show them in Plant.
        List<PlantInfo>plantInfos = MainActivity.myAppDatabase.myDao().getPlantInfo();

        System.out.println("Size of list = " + plantInfos.size());

        for(int i = 0; i<plantInfos.size(); i++)
        {

            String timeStamp = plantInfos.get(i).getTimeStampe();
            String timeNow = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

            Log.d("sui timeStamp"," is " + timeStamp);
            Log.d("sui timeNow"," is " + timeNow);

            Integer dayD = dayDiff(timeNow,timeStamp,"yyyyMMdd_HHmmss");

            Log.d("sui dayDiff", " is " + dayD );

            int daysLeft = Integer.parseInt(plantInfos.get(i).getDay()) - dayDiff(timeNow,timeStamp,"yyyyMMdd_HHmmss");
            if(daysLeft == 0)
            {

                // update timestampe is timeNow.
                //timeStamp = timeNow;
                PlantInfo plantInfo = new PlantInfo();
                plantInfo.setTimeStampe(timeNow);
                MainActivity.myAppDatabase.myDao().updateTimeStamp(plantInfo);
                Toast.makeText(getActivity(),"timeStamp updated..",Toast.LENGTH_SHORT).show();



                //send this plant image to wateringfragment and show it.


            }

            Log.d("Sui getdata","for loop");
            Log.d("sui daysLeft" ," is " +daysLeft);

            listPlants.add(new Plant(plantInfos.get(i).getImage(),plantInfos.get(i).getName(),
                    plantInfos.get(i).getDay(),String.valueOf(daysLeft)));
        }

    }

    public void itemTouchHelper(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                position = viewHolder.getAdapterPosition();
                Log.d("sui delete position ","is " + position);
                String name = recyclerViewAdopterP.getPlantAtPosition(position).getName();
                recyclerViewAdopterP.removeItem(position);

                Log.d("sui deleteName ","is " + name);

                //get name from database and find the same name to delete it in database
                int plantDbSize = MainActivity.myAppDatabase.myDao().getPlantInfo().size();

                for(int i = 0 ; i < plantDbSize; i++){

                    PlantInfo plantInfo = MainActivity.myAppDatabase.myDao().getPlantInfo().get(i);
                    if(plantInfo != null)
                    {
                        if(plantInfo.getName().equals(name))
                        {
                            MainActivity.myAppDatabase.myDao().deletePlantInfo(plantInfo);
                        }
                    }

                }
            }
        }).attachToRecyclerView(recyclerView);
        Log.d("sui attachtorecycler","view");
    }

    public static int dayDiff(String date1,String date2,String format){
        SimpleDateFormat formater = new SimpleDateFormat(format);
        int diff = 0;
        try{
            int d1 = (int) formater.parse(date1).getTime();
            int d2 = (int) formater.parse(date2).getTime();
            diff = (d1-d2)/(1000*60*60*24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff;
    }

}
