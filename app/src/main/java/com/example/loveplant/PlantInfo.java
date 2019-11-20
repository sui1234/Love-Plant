package com.example.loveplant;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"name", "day"}, tableName = "plantinfo")
public class PlantInfo {



    //@ColumnInfo(name = "name")
    @NonNull
    public String name;

   // @ColumnInfo(name = "day")
    @NonNull
    public String day;

   // @ColumnInfo(name = "image")
    @NonNull
    public String image;

    @NonNull
    public String timeStampe;

    public void setImageUri(@NonNull String image) {
        this.image = image;
    }

    @NonNull
    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDay() {
        return day;
    }

    public String getTimeStampe() {
        return timeStampe;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTimeStampe(@NonNull String timeStampe) {
        this.timeStampe = timeStampe;
    }
}
