package com.hamro.service.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.hamro.service.R;

import java.util.Locale;

public class LanguageChangeActivity extends AppCompatActivity {

    boolean isEnglish;
    SharedPreferences preferences;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_language_change);
        Button button = findViewById(R.id.submit);
        RelativeLayout english = findViewById(R.id.english_card);
        RelativeLayout nepali = findViewById(R.id.nepali_card);

        preferences = getSharedPreferences("lang",MODE_PRIVATE);
        isEnglish = preferences.getBoolean("isEnglish",true);
        if (isEnglish) changeCardSelectionUI(english,nepali);
        else changeCardSelectionUI(nepali,english);

        english.setOnClickListener(v -> {
            isEnglish = true;
            changeCardSelectionUI(english,nepali);
            changeLanguage("en");
            finish();
            startActivity(getIntent());
        });
        nepali.setOnClickListener(v -> {
            isEnglish = false;
            changeCardSelectionUI(nepali,english);
            changeLanguage("ne");

            finish();
            startActivity(getIntent());

        });

        button.setOnClickListener(v -> {
            if (isEnglish) changeLanguage("en");
            else changeLanguage("ne");
            if(auth.getCurrentUser() ==null)
                startActivity(new Intent(this,LoginActivity.class));
            else
                startActivity(new Intent(this,MainActivity.class));

        });

    }//onCreate

    private void changeCardSelectionUI(RelativeLayout selected,RelativeLayout other){
        selected.setBackgroundResource(R.drawable.lang_selectable);
        other.setBackgroundResource(R.drawable.lang_unselectable);
    }

    private void changeLanguage(String languageCode) {
        // Set new locale
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);

        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isEnglish",isEnglish);
        editor.apply();

    }

}