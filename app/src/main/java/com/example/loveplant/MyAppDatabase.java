package com.example.loveplant;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {PlantInfo.class},version = 5)
public abstract class MyAppDatabase extends RoomDatabase {

    public abstract MyDao myDao();

}
