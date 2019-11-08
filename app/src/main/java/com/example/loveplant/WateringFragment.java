package com.example.loveplant;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

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
        RecyclerViewAdopter recyclerViewAdopter = new RecyclerViewAdopter(getContext(), listWat);

        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdopter);

        return v;



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listWat = new ArrayList<>();
        listWat.add(new Watering(R.drawable.tree,R.drawable.tree));
        listWat.add(new Watering(R.drawable.tree,R.drawable.tree));
    }
}

