package com.example.timer.util;

public class Fruit {

    private String name;
    private int button ;
    private int imageId;

    public Fruit(String name, int imageId, int tag){
        this.name = name;
        this.imageId = imageId;
        this.button = tag;
    }

    public String getName() {
        return name;
    }
    public int getBtnName() {return button;}
    public int getImageId() {
        return imageId;
    }
}
