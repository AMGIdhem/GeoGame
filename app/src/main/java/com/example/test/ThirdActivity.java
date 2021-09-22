package com.example.test;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        TextView T_distance = (TextView) findViewById(R.id.T_distance);
        findViewById(R.id.B_pagain).setOnClickListener(this);

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

        T_distance.setText("You were " + distance + " Km far from the exact location. You got " + points + "points !");

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.B_pagain:
                Log.i("BUTTON : ", "PLAY AGAIN !");
                //intent = new Intent(this, SecondActivity.class);
                //startActivity(intent);
                Intent openMainActivity = new Intent(this, SecondActivity.class);
                openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(openMainActivity, 0);
                break;
        }
    }
}
