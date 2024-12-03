package com.hamro.service.data;

import com.hamro.service.R;

public class MainData {

    static HomeData homeData;
    static ServiceData service;

    public static HomeData getData(){
        if(homeData!=null) return homeData;


        homeData = new HomeData();


        homeData.addImage(R.drawable.house);
        homeData.addImage(R.drawable.plubing);

        //Create service here
        //plumbing
        service = new ServiceData();
        service.setName("Plumber");
        service.setNameNepali("PPlumber");
        service.setLogo(R.drawable.plubing);
        service.setPrice("Rs. 500 per session");
        service.setDescription("This is the description of plumbing ");
        service.setDescriptionNepali("Yo plumbing ko description ho");
        service.setMessenger("486122771253891");
        service.setWhatsapp("9804457491");
        service.addPhone("9804457491");
        service.addPhone("9824486107");
        service.addImage(R.drawable.plubing);
        service.addImage(R.drawable.house);
        service.addImage(R.drawable.house);
        service.addImage(R.drawable.house);

        homeData.addService(service);
        //added the service in homeData as main

        service = new ServiceData();
        service.setName("Electricity");
        service.setNameNepali("vidhut");
        service.setLogo(R.drawable.house);
        service.setPrice("Rs. 600 per session");
        service.setDescription("This is the description of electric ity ");
        service.setDescriptionNepali("Yo plumbing ko description ho");
        service.setMessenger("486122771253891");
        service.setWhatsapp("9804457491");
        service.addPhone("9804457491");
        service.addPhone("9824486107");
        service.addImage(R.drawable.plubing);
        service.addImage(R.drawable.house);
        service.addImage(R.drawable.house);
        service.addImage(R.drawable.house);

        homeData.addService(service);




        return homeData;

    }//GETdata
}
