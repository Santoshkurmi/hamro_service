package com.hamro.service.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hamro.service.R;
import com.hamro.service.activity.admin.UsersPanelActivity;
import com.hamro.service.adapter.ImageSliderAdapter;
import com.hamro.service.adapter.ServiceListAdapter;
import com.hamro.service.data.HomeData;
import com.hamro.service.data.MainData;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    SharedPreferences preferences;
    boolean isEnglish = true;
    HomeData data;
    ViewPager2 pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = MainData.getData();

//        if(true){
//            startActivity(new Intent(this, AdminPanelActivity.class));
//            finish();
//            return;
//        }

        preferences = getSharedPreferences("lang",MODE_PRIVATE);
        changeLanguage();
        if(FirebaseApp.getApps(this).isEmpty()){
            FirebaseApp.initializeApp(this);
        }

        auth = FirebaseAuth.getInstance();

        if(!preferences.contains("isEnglish")){
            startActivity(new Intent(this, LanguageChangeActivity.class));
            finish();
            return;
        }
        if(auth.getCurrentUser() ==null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }


//        startActivity(new Intent(this, LoginActivity.class));
//        finish();
//        setActionBar();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("");
        }

        pager = findViewById(R.id.viewpager);
        TabLayout tab = findViewById(R.id.tab);
        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        RecyclerView recyclerView = findViewById(R.id.recycler);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(new ServiceListAdapter(this, data,isEnglish));
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if(itemId == R.id.logout){
                auth.signOut();
                SharedPreferences.Editor editor = getSharedPreferences("user",0).edit();
                editor.remove("name");
                editor.apply();
                startActivity(new Intent(this,LoginActivity.class));
                finish();
            }
            else if(itemId == R.id.admin){
                startActivity(new Intent(this, UsersPanelActivity.class));
            }
            else if(itemId==R.id.lang){
                startActivity(new Intent(this,LanguageChangeActivity.class));
                finish();
            }
            return  false;
        });

        View view =  navigationView.getHeaderView(0);
        TextView name = view.findViewById(R.id.name);
        handleName(name);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        pager.setAdapter(new ImageSliderAdapter(this,data.getImages()));

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
//                pager.setCurrentItem(position % Objects.requireNonNull(pager.getAdapter()).getItemCount() );
//                Toast.makeText(MainActivity.this,position+"",Toast.LENGTH_SHORT).show();
            }
        });

        new TabLayoutMediator(tab, pager, (tab1, position) -> {

        }).attach();

        autoSlideImages();



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

    void changeLanguage(){
         isEnglish = preferences.getBoolean("isEnglish",true);
        if(isEnglish) return;
        Locale locale = new Locale("ne");
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);

        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    void saveDetails(String phone,String name){
//        SharedPreferences.Editor editor = getSharedPreferences("user",MODE_PRIVATE).edit();
//        editor.putString("phone",phone);
//        editor.putString("name",name);
//        editor.apply();

    }


    void handleName(TextView nameText){
        database = FirebaseDatabase.getInstance();
        SharedPreferences preferences1 = getSharedPreferences("user",0);
        String name = preferences1.getString("name","");

        if(name.length()>4){
            nameText.setText(name);
            return;
        }



        String uid = auth.getUid();
        if(uid==null) return;
        DatabaseReference user = database.getReference("users").child(auth.getUid());
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name2 = snapshot.child("name").getValue(String.class);
                String phone2 = snapshot.child("phone").getValue(String.class);

                SharedPreferences.Editor editor = preferences1.edit();
                if (phone2 != null) {
                    editor.putString("phone", phone2);
                }
                if (name2 == null) {
                    nameText.setText(R.string.unknown);
                    editor.putString("name", "Unknown");
                }
                else {
                    nameText.setText(name2);
                    editor.putString("name", name2);
                }
                editor.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Something went wrong fetching details",0).show();
            }
        });
    }


}