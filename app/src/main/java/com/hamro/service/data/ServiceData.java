package com.hamro.service.data;

import java.util.ArrayList;

public class ServiceData {
    private ArrayList<Integer> images = new ArrayList<>();
    private int logo;
    private String name="",price="",description="",facebook="",messenger="",whatsapp="";
    private String nameNepali="", priceNepali="", descriptionNepali="";
    private ArrayList<String> phones = new ArrayList<>();

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public void setImages(ArrayList<Integer> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getNameNepali() {
        return nameNepali;
    }

    public void setNameNepali(String nameNepali) {
        this.nameNepali = nameNepali;
    }

    public String getPriceNepali() {
        return priceNepali;
    }

    public void setPriceNepali(String priceNepali) {
        this.priceNepali = priceNepali;
    }

    public String getDescriptionNepali() {
        return descriptionNepali;
    }

    public void setDescriptionNepali(String descriptionNepali) {
        this.descriptionNepali = descriptionNepali;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }

    public ArrayList<Integer> getImages() {
        return images;
    }

    public void addImage(Integer images) {
        this.images.add( images);
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void addPhone(String phone) {
        this.phones.add( phone);
    }




}
