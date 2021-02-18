package com.example.tictactaoegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class HomePageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
    }


    public void startOffLineGame(View view) {
        Intent intent = new Intent(getApplicationContext(), ChoosePlayerActivity.class);
        startActivity(intent);
    }



   /* public void startOnLineGame(View view){
        Intent intent = new Intent(getApplicationContext(), OnlineSignin.class);
        startActivity(intent);
    }

    public void ShowRank(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }      */
}