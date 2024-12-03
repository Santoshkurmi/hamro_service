package com.hamro.service.activity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hamro.service.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout nameL,phoneL,emailL,passwordL,confirmPasswordL;
    TextInputEditText name,phone,email,password,confirmPassword;
    ProgressBar progress;
    Button register_btn;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() !=null){
            startActivity(new Intent(this,MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_register);


        register_btn = findViewById(R.id.register_btn);
        TextView login_btn = findViewById(R.id.login_btn);

        progress = findViewById(R.id.progress);

        nameL = findViewById(R.id.namelayout);
        phoneL = findViewById(R.id.phonelayout);
        emailL = findViewById(R.id.emaillayout);
        passwordL = findViewById(R.id.passwordlayout);
        confirmPasswordL = findViewById(R.id.confirmpasswordlayout);



        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmpassword);

        handleListener();
        register_btn.setOnClickListener(v->handleRegister());

        login_btn.setOnClickListener(v -> {
                        v.setAlpha(0.5f); // Make it semi-transparent
                        v.postDelayed(() -> v.setAlpha(1.0f), 50); // Reset
                        finish();
        });

    }//Oncreate

    void handleListener(){

        name.setOnFocusChangeListener((v, hasFocus) -> {
            nameL.setError("");
        });
        phone.setOnFocusChangeListener((v, hasFocus) -> {
            phoneL.setError("");
        });
        password.setOnFocusChangeListener((v, hasFocus) -> {
            passwordL.setError("");
        });
        confirmPassword.setOnFocusChangeListener((v, hasFocus) -> {
            confirmPasswordL.setError("");
        });
    }

    void handleRegister(){

        register_btn.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.VISIBLE);


        String nameT,emailT,phoneT,passwordT,confirmPasswordT;
        nameT = String.valueOf(name.getText());
        emailT = String.valueOf(email.getText());
        phoneT = String.valueOf(phone.getText());
        passwordT = String.valueOf(password.getText());
        confirmPasswordT = String.valueOf(confirmPassword.getText());

        boolean isAllGood = true;

        if(nameT.length()<5 || nameT.length()>30){
            isAllGood = false;
            nameL.setError("it must be 5 to 30 chars");
        }
        if(phoneT.length()!=10){
            isAllGood = false;
            phoneL.setError("it must be 10 chars");
        }

        if(passwordT.length()<5 || passwordT.length()>30){
            isAllGood = false;
            passwordL.setError("it must be 5 to 30 chars");
        }
        if(!passwordT.equals(confirmPasswordT)){
            isAllGood = false;
            confirmPasswordL.setError("password and confirm password should match");
        }


        if(!isAllGood) {
            register_btn.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            return;
        };

        auth.createUserWithEmailAndPassword(phoneT+"@hamroservice.com",passwordT).addOnCompleteListener(task -> {
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

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference userRef = database.getReference("users");
                HashMap<String,String> details = new HashMap<>();

                details.put("name",nameT);
                details.put("email",emailT);
                details.put("phone",phoneT);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String date =  LocalDateTime.now().format(formatter);

                details.put("register_date",date);

                if(auth.getUid()!=null)
                    userRef.child(auth.getUid()).setValue(details).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(this, "All details are saved successfully",Toast.LENGTH_SHORT).show();
                        }
                    });

                saveDetails(phoneT,nameT);
                Toast.makeText(this,"Register Successfully...",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }




            register_btn.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        });
//        Toast.makeText(this,"Everything is good",Toast.LENGTH_SHORT).show();


    }//handleRegister

    void saveDetails(String phone,String name){
        SharedPreferences.Editor editor = getSharedPreferences("user",MODE_PRIVATE).edit();
        editor.putString("phone",phone);
        editor.putString("name",name);
        editor.apply();

    }
}