package com.example.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener {

    MyDatabaseHelper myDB = new MyDatabaseHelper(this);
    public static final String SHARED_PREFS = "SharedPrefs";
    public int score;
    public static final int DBRows = 7;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        TextView T_distance = (TextView) findViewById(R.id.T_distance);
        Button button = (Button) findViewById(R.id.B_pagain);
        button.setOnClickListener(this);

        Float distance = getIntent().getExtras().getFloat("distance");
        int points = 0;
        Toast.makeText(getApplicationContext(), "distance = " + distance, Toast.LENGTH_SHORT).show();
        if (distance > 50) { points = 0;
        } else if (20 < distance && distance < 50) {
            points = 1;
        } else if (10 < distance && distance < 20) {
            points = 2;
        } else if (distance<10) {
            points = 3;
        }
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        score = sharedPreferences.getInt("score", -1);

        T_distance.setText("You were " + distance + " Km far from the exact location. \n You got " + points + "points !");
        int number = sharedPreferences.getInt("img", -1);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("img", number+1);
        editor.putInt("score", score+points);
        editor.apply();
        if(number==DBRows) {
            score = sharedPreferences.getInt("score", -1);
            T_distance.append("\n Your final score is : " + score);
            button.setText("Quit");
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.B_pagain:
                Button button = (Button) findViewById(R.id.B_pagain);

                if(button.getText().equals("Quit")) {
                    Intent openMainActivity = new Intent(this, MainActivity.class);
                    openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivityIfNeeded(openMainActivity, 0);
                } else {
                    Log.i("BUTTON : ", "PLAY AGAIN !");
                    //intent = new Intent(this, SecondActivity.class);
                    //startActivity(intent);
                    Intent openMainActivity = new Intent(this, SecondActivity.class);
                    openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivityIfNeeded(openMainActivity, 0);
                }

                break;
        }
    }
}
