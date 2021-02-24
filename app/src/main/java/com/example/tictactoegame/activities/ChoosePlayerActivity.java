package com.example.tictactoegame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.tictactoegame.R;

public class ChoosePlayerActivity extends AppCompatActivity {

    String gameSize;
    private EditText NAME1;
    private EditText NAME2;
    private ImageButton backButton;
    private Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_offline_players);


        gameSize = getIntent().getStringExtra("gameSize");

        setupViews();

        backButton.setOnClickListener(v -> {
            finish();
        });

        startGameButton.setOnClickListener(v -> {
            launchGame(v);
        });


    }

    public void setupViews() {
        NAME1 = (EditText) findViewById(R.id.PLAYER1);
        NAME2 = (EditText) findViewById(R.id.PLAYER2);
        startGameButton = findViewById(R.id.startGameButton);
        backButton = findViewById(R.id.button_back);
    }

    public Intent getGameTypeIntent() {
        switch (gameSize) {
            case "3x3":
                return new Intent(getApplicationContext(), OfflinePageActivity.class);
            case "5x5":
                return new Intent(getApplicationContext(), FByFGameActivity.class);
        }
        return null;
    }

    public void launchGame(View view) {
        String nam1 = NAME1.getText().toString();
        String nam2 = NAME2.getText().toString();

        Intent intent = getGameTypeIntent();
        intent.putExtra("name1", nam1);
        intent.putExtra("name2", nam2);
        startActivity(intent);
    }
}