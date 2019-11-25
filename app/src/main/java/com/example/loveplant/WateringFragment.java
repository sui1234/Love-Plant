package com.example.loveplant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
            Integer daysInt = Integer.parseInt(days);

            if( daysInt == diff +1){

                listWat.add(new Watering(plantInfos.get(i).getImage(), R.drawable.ic_watering_can));

                //listWat.add(new Watering(R.drawable.tree,R.drawable.ic_watering_can));
                //Integer.parseInt(plantInfos.get(i).getImage()
                // && diff < (Integer.parseInt(timeDiffT))
            }

        }

    }
}

