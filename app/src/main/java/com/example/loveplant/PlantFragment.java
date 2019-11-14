package com.example.loveplant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlantFragment extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private List<Plant> listPlants;
    int spacingInPixels = 8;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.my_plants_fragment, container, false);
        recyclerView = v.findViewById(R.id.my_plants_recyclerview);
        RecyclerViewAdopterP recyclerViewAdopter = new RecyclerViewAdopterP(getContext(), listPlants);

        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdopter);

        Log.d("Sui plantrecycleView","is loading");
        return v;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPlants = new ArrayList<>();
        Log.d("Sui getdata","from database");

        List<PlantInfo>plantInfos = MainActivity.myAppDatabase.myDao().getPlantInfo();

        //Log.d("Sui getdata",plantInfos.size());
        System.out.println("Size of list = " + plantInfos.size());

        for(int i = 0; i<plantInfos.size(); i++)
        {
            Log.d("Sui getdata","for loop");
            listPlants.add(new Plant(plantInfos.get(i).getImage(),plantInfos.get(i).getName(),plantInfos.get(i).getDay(),plantInfos.get(i).getDay()));
        }

    }


    public void getDataFromRoom(){
        //get data from Room database
        List<PlantInfo>plantInfos = MainActivity.myAppDatabase.myDao().getPlantInfo();

        for(PlantInfo pI : plantInfos)
        {
            String name = pI.getName();
            String days = pI.getDay();
            String imageUri = pI.getImage();
        }

    }

    public Bitmap stringToBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
