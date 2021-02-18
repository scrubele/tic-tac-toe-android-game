package com.example.tictactaoegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class ScorePageActivity extends AppCompatActivity {


    String NAME1;
    String NAME2;
    private TextView PLAYER1;
    private TextView PLAYER2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_score);

        TextView TextViewScore1 = (TextView) findViewById(R.id.score_1);
        TextView TextViewScore2 = (TextView) findViewById(R.id.score_2);

        PLAYER1 = findViewById(R.id.Player1Points);
        PLAYER2 = findViewById(R.id.Player2Points);

        String scor1 = getIntent().getStringExtra("SCORE1");
        String scor2 = getIntent().getStringExtra("SCORE2");

        String NAME1 = getIntent().getStringExtra("name1");
        String NAME2 = getIntent().getStringExtra("name2");

        TextViewScore1.setText(scor1);
        TextViewScore2.setText(scor2);

        PLAYER1.setText(NAME1);
        PLAYER2.setText(NAME2);

    }

    public void home(View view) {

        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
        startActivity(intent);
    }
}