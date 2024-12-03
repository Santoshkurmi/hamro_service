package com.hamro.service.data;

import java.util.ArrayList;

public class HomeData {
    private ArrayList<Integer> images = new ArrayList<>();
    private ArrayList<ServiceData> services = new ArrayList<>();

    public ArrayList<Integer> getImages() {
        return images;
    }

    public void addImage(Integer images) {
        this.images.add( images);
    }

    public ArrayList<ServiceData> getServices() {
        return services;
    }

    public void addService(ServiceData services) {
        this.services.add(services);
    }
}
