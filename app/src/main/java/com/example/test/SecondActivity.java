package com.example.test;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import co.gofynd.gravityview.GravityView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDatabaseHelper myDB = new MyDatabaseHelper(SecondActivity.this);
    private ImageView img;
    private GravityView gravityView;
    private boolean esSoportado = false;
    private int number;
    public static final String SHARED_PREFS = "SharedPrefs";
    private android.app.AlertDialog.Builder dialogBuilder;
    private android.app.AlertDialog dialog;
    private List<Integer> randomNumbers = new ArrayList<Integer>(7);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("SecondActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        init();

        for (int i = 1; i < 8; i++)
            randomNumbers.add(i);
        Collections.shuffle(randomNumbers);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < randomNumbers.size(); i++) {
            str.append(randomNumbers.get(i)).append(",");
        }
        Log.i("RANDOM NUMBERS", String.valueOf(str));

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("randomNumbers", str.toString());
        editor.putInt("img", 0);
        editor.putInt("score", 0);
        editor.commit();


        Cursor cursor = myDB.getLocation(randomNumbers.get(0));
        cursor.moveToNext();
        Log.i("SecondActivity", "ID = " + Double.parseDouble(cursor.getString(3)));

        img.setImageResource(cursor.getInt(1));

        ((Button)findViewById(R.id.B_guess)).setOnClickListener(this);
        ((Button)findViewById(R.id.B_map)).setOnClickListener(this);

    }

    private void init() {
        this.img = findViewById(R.id.imageView);
        this.gravityView = GravityView.getInstance(getBaseContext());
        this.esSoportado = gravityView.deviceSupported();
    }

    @Override
    protected void onResume() {
        Log.i("SecondActivity", "onResume");
        super.onResume();
        gravityView.unRegisterListener();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        number = sharedPreferences.getInt("img", -1);
        String savedRandomNumbers = sharedPreferences.getString("randomNumbers", "");
        StringTokenizer st = new StringTokenizer(savedRandomNumbers, ",");
        int[] randomNumbers = new int[7];
        for (int i = 0; i < 7; i++) {
            randomNumbers[i] = Integer.parseInt(st.nextToken());
        }
        Cursor cursor = myDB.getLocation(randomNumbers[number]);
        cursor.moveToNext();
        img.setImageResource(cursor.getInt(1));

    }

    @Override
    protected void onStop() {
        Log.i("SecondActivity", "onStop");
        super.onStop();

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()) {
            case R.id.B_guess:
                intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.B_map:
                createNewContactDialog();
        }

    }

    private void createNewContactDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.activity_maps, null);
        //newcontactpopup_firstname = (EditText) contactPopupView.findViewById(R.id.newcontactpopup_firstname);

        //newcontactpopup_save = (Button) contactPopupView.findViewById(R.id.saveButton);

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        /*newcontactpopup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }); */
    }
}
