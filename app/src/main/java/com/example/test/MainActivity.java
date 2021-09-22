package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.B_start).setOnClickListener(this);
        MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
        myDB.addLocation(1, R.drawable.img_lo1, "Jassani inezgane agadir", 30.362188, -9.542420);
        myDB.addLocation(2, R.drawable.img_lo2,"Plage Taghazout Agadir", 30.526572, -9.693156);
        myDB.addLocation(3, R.drawable.img_lo3,"TEST 3", 30.526572, -9.693156);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.B_start:
                intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
                break;
        }
    }
}
