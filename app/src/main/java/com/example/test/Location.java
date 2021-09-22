package com.example.test;

public class Location {
    private int id;
    private int img;
    private String address;
    private long lat;
    private long longit;

    public Location(int id, int img, String address, long lat, long longit) {
        this.id = id;
        this.img = img;
        this.address = address;
        this.lat = lat;
        this.longit = longit;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getLongit() {
        return longit;
    }

    public void setLongit(long longit) {
        this.longit = longit;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }
}
