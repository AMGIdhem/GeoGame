package com.example.test;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        findViewById(R.id.B_start).setOnClickListener(this);
        findViewById(R.id.B_changeMyLanguage).setOnClickListener(this);
        MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
        myDB.addLocation(1, R.drawable.img_lo1, "Casablanca - Mosquée Hassan-II", "33.60919286253977", "-7.632933330683119");
        myDB.addLocation(2, R.drawable.img_lo2,"Marrakech - Place Jemaa el-Fna", "31.626497369844447", "-7.98901283068312");
        myDB.addLocation(3, R.drawable.img_lo3,"Rabat - Tour Hassan", "34.02486009996575", "-6.822659307950644");
        myDB.addLocation(4, R.drawable.img_lo4,"Agadir - Souk El Had", "30.412251", "-9.577498");
        myDB.addLocation(5, R.drawable.img_lo5,"Agadir - Place Al Amal", "30.4188143", "-9.6008797");
        myDB.addLocation(6, R.drawable.img_lo6,"Agadir - Corniche Agadir", "30.4172485", "-9.60569");
        myDB.addLocation(7, R.drawable.img_lo7,"Agadir - Boulevard 20 Août", "30.4145526", "-9.6020831");
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.B_start:
                intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
                break;
            case R.id.B_changeMyLanguage:
                showChangeLanguageDialog();
        }
    }

    private void showChangeLanguageDialog() {
        final String[] listItems = {"French", "English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Choose Language ...");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0) {
                    setLocale("fr");
                    recreate();
                } else if (i == 1) {
                    setLocale("en");
                    recreate();
                }

                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }
}
