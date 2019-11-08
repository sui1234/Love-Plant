package com.example.loveplant;

public class Watering {
    private int image_plant;
    private int image_water;


    public Watering() {
    }

    public Watering(int image_plant, int image_water) {
        this.image_plant = image_plant;
        this.image_water = image_water;
    }

    public int getImage_plant() {
        return image_plant;
    }

    public void setImage_water(int image_water) {
        this.image_water = image_water;
    }

    public int getImage_water() {
        return image_water;
    }

    public void setImage_plant(int image_plant) {
        this.image_plant = image_plant;
    }
}
