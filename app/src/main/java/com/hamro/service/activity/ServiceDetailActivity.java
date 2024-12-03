package com.hamro.service.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.google.type.TimeOfDay;
import com.hamro.service.R;
import com.hamro.service.adapter.ImageSliderAdapter;
import com.hamro.service.data.HomeData;
import com.hamro.service.data.MainData;
import com.hamro.service.data.ServiceData;

public class ServiceDetailActivity extends AppCompatActivity {

    HomeData data;
    ViewPager2 pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        data = MainData.getData();

        SharedPreferences preferences = getSharedPreferences("lang",MODE_PRIVATE);
        boolean isEnglish = preferences.getBoolean("isEnglish",true);

        int position = getIntent().getIntExtra("service",0);
        ServiceData service = data.getServices().get(position);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("");
        }
        pager = findViewById(R.id.viewpager);
        pager.setAdapter(new ImageSliderAdapter(this,service.getImages()));
        autoSlideImages();

        TextView price = findViewById(R.id.price);
        TextView description = findViewById(R.id.descriptionText);
        TextView phone1 = findViewById(R.id.phone1);
        TextView phone2 = findViewById(R.id.phone2);
        LinearLayout phoneHide = findViewById(R.id.phoneHide);
        LinearLayout messenger = findViewById(R.id.messenger);

        MaterialButton call = findViewById(R.id.call);
        MaterialButton chat = findViewById(R.id.chat);
        TextView name = findViewById(R.id.name);


        if(isEnglish){
            price.setText(service.getPrice());
            description.setText(service.getDescription());
            name.setText(service.getName());
        }
        else{

            if( service.getNameNepali().length() > 2 ){
                name.setText(service.getNameNepali());
            }
            else{
                name.setText(service.getName());
            }


            if( service.getPriceNepali().length() > 2 ){
                price.setText(service.getPriceNepali());
            }
            else{
                price.setText(service.getPrice());
            }

            if( service.getDescriptionNepali().length() > 2 ){
                description.setText(service.getDescriptionNepali() );
            }
            else{

                description.setText(service.getDescription());
            }





        }



        phone1.setText(service.getPhones().get(0));

        messenger.setOnClickListener(v -> {
            openMessengerChat(service.getMessenger());
        });

//        call.setText(service.getPhones().get(0));
        if(service.getPhones().size()>1){
            phoneHide.setVisibility(View.VISIBLE);
            String phoneSecond = service.getPhones().get(1);
            phone2.setText( phoneSecond );
            phoneHide.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + phoneSecond ));

                // Check for permission
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // Request permission if not granted
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                startActivity(intent);
            });
        }



        phone1.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_CALL);

            intent.setData(Uri.parse("tel:" + service.getPhones().get(0)));

            // Check for permission
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // Request permission if not granted
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return;
            }
            startActivity(intent);
        });

        call.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_CALL);

            intent.setData(Uri.parse("tel:" + service.getPhones().get(0)));

            // Check for permission
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // Request permission if not granted
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return;
            }
            startActivity(intent);
        });

//        chat.setText(service.getWhatsapp());
        chat.setOnClickListener(v -> {
            openWhatsAppChat(service.getWhatsapp());
        });


    }

    public void openWhatsAppChat(String phoneNumber) {
        // Replace "91" with the country code of the number
        String countryCode = "977"; // Example for India
        String fullNumber = countryCode + phoneNumber;

        try {
            // Create the WhatsApp URI
            Uri uri = Uri.parse("https://wa.me/" + fullNumber);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.whatsapp"); // Ensure only WhatsApp handles this intent
            startActivity(intent);
        } catch (Exception e) {
            // Handle case where WhatsApp is not installed
            Toast.makeText(this, "WhatsApp is not installed on your device.", Toast.LENGTH_SHORT).show();
        }
    }

    private void autoSlideImages() {
        final int delay = 3000; // milliseconds
        pager.postDelayed(new Runnable() {
            int currentPage = 0;

            @Override
            public void run() {
                if (pager.getAdapter() != null) {
                    currentPage = (currentPage + 1) % pager.getAdapter().getItemCount();
                    pager.setCurrentItem(currentPage, true);
                    pager.postDelayed(this, delay);
                }
            }
        }, delay);
    }


    public void openMessengerChat(String userId) {
        try {
            // Create the Messenger URI
            Uri uri = Uri.parse("fb-messenger://user/" + userId);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.facebook.orca"); // Ensures it opens in Messenger
            startActivity(intent);
        } catch (Exception e) {
            // Handle case where Messenger is not installed
            Toast.makeText(this, "Messenger is not installed on your device.", Toast.LENGTH_SHORT).show();
        }
    }


}