package com.example.loveplant;

public class Plant {
    private String image;
    private String name;
    private String days;
    private String daysLeft;

    public Plant() {

    }

    public Plant(String image, String name, String days, String daysLeft) {
        this.image = image;
        this.name = name;
        this.days = days;
        this.daysLeft = daysLeft;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public void setWateringLastTime(String wateringLastTime) {
        this.daysLeft = wateringLastTime;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDays() {
        return days;
    }

    public String getWateringLastTime() {
        return daysLeft;
    }
}
