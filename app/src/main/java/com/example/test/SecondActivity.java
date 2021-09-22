package com.example.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

import co.gofynd.gravityview.GravityView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    Location loc;
    MyDatabaseHelper myDB = new MyDatabaseHelper(SecondActivity.this);
    private ImageView img;
    private GravityView gravityView;
    private boolean esSoportado = false;
    private int number;
    Integer[] images = {
            R.drawable.img_lo1,
            R.drawable.img_lo2,
            R.drawable.img_lo3
    };

    public static final String SHARED_PREFS = "SharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("SecondActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        init();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        //editor.putInt("img", 1);
        //editor.commit();


        Cursor cursor = myDB.getLocation(3);
        cursor.moveToNext();
        Log.i("SecondActivity", "ID = " + cursor.getLong(3));
        //Cursor cursor = myDB.readAllData();
        //cursor.moveToNext();
        //Log.i("SecondActivity", "ID = " + cursor.getString(0));

        //Log.i("SecondActivity", "ADDRESS = " + loc.getAddress());


        if(esSoportado) {
            this.gravityView.setImage(img, cursor.getInt(1)).center();
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), cursor.getInt(1));
            img.setImageBitmap(bitmap);
        }

        ((Button)findViewById(R.id.B_guess)).setOnClickListener(this);

    }

    private void init() {
        this.img = findViewById(R.id.imageView);
        this.gravityView = GravityView.getInstance(getBaseContext());
        this.esSoportado = gravityView.deviceSupported();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("SecondActivity", "onStart");
    }

    @Override
    protected void onResume() {
        Log.i("SecondActivity", "onResume");
        super.onResume();
        gravityView.registerListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("SecondActivity", "onPause");
    }

    @Override
    protected void onStop() {
        Log.i("SecondActivity", "onStop");
        super.onStop();
        gravityView.unRegisterListener();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        number = sharedPreferences.getInt("img", -1);
        //loc = myDB.getLocation(number+1);
        if(esSoportado) {
            this.gravityView.setImage(img, loc.getImg()).center();
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), loc.getImg());
            img.setImageBitmap(bitmap);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("img", number+1);
        editor.apply();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()) {
            case R.id.B_guess:
                intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
