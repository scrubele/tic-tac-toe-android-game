package com.example.tictactoegame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.tictactoegame.R;
import com.google.android.material.card.MaterialCardView;

public class ScorePageActivity extends AppCompatActivity {


    String NAME1;
    String NAME2;
    private TextView PLAYER1;
    private TextView PLAYER2;
    private TextView TextViewScore1;
    private TextView TextViewScore2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_score);

        String scor1 = getIntent().getStringExtra("SCORE1");
        String scor2 = getIntent().getStringExtra("SCORE2");

        NAME1 = getIntent().getStringExtra("name1");
        NAME2 = getIntent().getStringExtra("name2");
        setupViews();
        setupFields(scor1, scor2);
    }

    public void setupViews() {
        MaterialCardView relativeLayout = (MaterialCardView) findViewById(R.id.layout_final_score);
        TextViewScore1 = (TextView) relativeLayout.findViewById(R.id.score_1);
        TextViewScore2 = (TextView) relativeLayout.findViewById(R.id.score_2);
        PLAYER1 = relativeLayout.findViewById(R.id.Player1Points);
        PLAYER2 = relativeLayout.findViewById(R.id.Player2Points);
    }

    public void setupFields(String scor1, String scor2) {
        TextViewScore1.setText(scor1);
        TextViewScore2.setText(scor2);

        PLAYER1.setText(NAME1);
        PLAYER2.setText(NAME2);
    }

    public void home(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}