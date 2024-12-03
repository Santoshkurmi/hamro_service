package com.hamro.service.activity.admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hamro.service.R;
import com.hamro.service.activity.admin.adapter.UserListAdapter;
import com.hamro.service.activity.admin.dao.UserDetail;

import java.util.ArrayList;

public class UsersPanelActivity extends AppCompatActivity {

    UserListAdapter adapter;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_users_panel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("");
        }
        TextView user_text = findViewById(R.id.user_text);
        ProgressBar progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        ArrayList<UserDetail> users = new ArrayList<>();
        adapter = new UserListAdapter(this,users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for(DataSnapshot user:snapshot.getChildren()){
                    UserDetail detail = user.getValue(UserDetail.class);
                    if(detail !=null) users.add(detail);
                }
                progressBar.setVisibility(View.GONE);
                user_text.setText(users.size()+" users");
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                UserDetail detail = new UserDetail();
                SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                detail.setName(preferences.getString("name",""));
                detail.setName(preferences.getString("phone",""));
                users.add(detail);
                adapter.notifyDataSetChanged();
//                Toast.makeText(UsersPanelActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });


    }
}