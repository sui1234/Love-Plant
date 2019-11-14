package com.example.loveplant;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    public void addPlant(PlantInfo plantInfo);

    @Query("select * from plantinfo")
    public List<PlantInfo> getPlantInfo();
}
