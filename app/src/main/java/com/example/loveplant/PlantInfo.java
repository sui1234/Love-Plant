package com.example.loveplant;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plantInfos")
public class PlantInfo {

    //@PrimaryKey
    //private int id;

    @ColumnInfo(name = "plant_name")
    private String name;

    @ColumnInfo(name = "plant_day")
    private String day;

    //public int getId() {
    //    return id;
    //}

    public String getName() {
        return name;
    }

    public String getDay() {
        return day;
    }

    //public void setId(int id) {
    //    this.id = id;
    //}

    public void setName(String name) {
        this.name = name;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
