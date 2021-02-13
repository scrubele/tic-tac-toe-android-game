package com.example.tto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.tto.MainActivity;

public class Score extends AppCompatActivity {
String P1;
int P2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

    }

    public void home(View view){

        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
    }
}