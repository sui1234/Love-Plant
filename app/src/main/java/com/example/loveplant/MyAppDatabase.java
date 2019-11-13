package com.example.loveplant;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PlantInfo.class},version = 1)
public abstract class MyAppDatabase extends RoomDatabase {

    public abstract MyDao myDao();
}
