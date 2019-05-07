package com.example.barmsss;


public class Clothes {

    private int id;
    private String name;
    private String type;
    private byte[] image;

    public Clothes(String name, String type, byte[] image, int id) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
