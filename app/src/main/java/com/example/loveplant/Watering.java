package com.example.loveplant;

public class Watering {
    private String image_plant;
    private int image_water;
    private String name;


    public Watering(String image_plant, int image_water, String name) {
        this.image_plant = image_plant;
        this.image_water = image_water;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getImage_plant() {
        return image_plant;
    }

    public void setImage_water(int image_water) {
        this.image_water = image_water;
    }

    public int getImage_water() {
        return image_water;
    }

    public void setImage_plant(String image_plant) {
        this.image_plant = image_plant;
    }
}
