package com.hamro.service.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.hamro.service.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextInputLayout phoneL,passwordL;
    TextInputEditText phone,password;
    ProgressBar progress;
    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() !=null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_login);

        passwordL = findViewById(R.id.passwordlayout);
        phoneL = findViewById(R.id.phonelayout);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);

        phone.setText(getPhoneIfExists());
        progress = findViewById(R.id.progress);

        login_btn = findViewById(R.id.login_btn);
        TextView register_btn = findViewById(R.id.register_btn);


        handleListener();

        login_btn.setOnClickListener(v->handleLogin());

        register_btn.setOnClickListener(v ->{
            v.setAlpha(0.5f); // Make it semi-transparent

            // Optionally, reset it back after some time for a feedback effect
            v.postDelayed(() -> v.setAlpha(1.0f), 50); // Reset
            startActivity(new Intent(this,RegisterActivity.class));
        }
        );

    }//oncreate

    void handleLogin(){

        login_btn.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.VISIBLE);


        String phoneT,passwordT;

        phoneT = String.valueOf(phone.getText());
        passwordT = String.valueOf(password.getText());

        boolean isAllGood = true;


        if(phoneT.length()!=10){
            isAllGood = false;
            phoneL.setError("it must be 10 chars");
        }

        if(passwordT.length()<5 || passwordT.length()>30){
            isAllGood = false;
            passwordL.setError("it must be 5 to 30 chars");
        }


        if(!isAllGood) {
            login_btn.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            return;
        };

        auth.signInWithEmailAndPassword(phoneT+"@hamroservice.com",passwordT).addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                Exception error =  task.getException();
                if(error!=null) {

                    phoneL.setError(Objects.requireNonNull(error.getMessage()).replace("email", "phone"));
                    Toast.makeText(this, Objects.requireNonNull(error.getMessage()).replace("email", "phone"), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }


            }//error
            else{
                clearShared();
                Toast.makeText(this,"Login Successfully...",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(this, MainActivity.class));
                finish();
            }


            login_btn.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);

        });


    }//handleRegister

    String getPhoneIfExists(){
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        return preferences.getString("phone","");
    }

    void clearShared(){
        SharedPreferences.Editor editor = getSharedPreferences("user", Context.MODE_PRIVATE).edit();
       editor.remove("phone");
       editor.remove("name");
       editor.apply();
    }


    void handleListener(){

        phone.setOnFocusChangeListener((v, hasFocus) -> {
            phoneL.setError("");
        });
        password.setOnFocusChangeListener((v, hasFocus) -> {
            passwordL.setError("");
        });

    }//handle

}