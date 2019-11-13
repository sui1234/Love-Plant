package com.example.loveplant;


import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface MyDao {

    @Insert
    public void addPlant(PlantInfo plantInfo);
}
