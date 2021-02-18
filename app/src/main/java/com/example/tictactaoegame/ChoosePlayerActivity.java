package com.example.tictactaoegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class ChoosePlayerActivity extends AppCompatActivity {

    private EditText NAME1;
    private EditText NAME2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_offline_players);

        NAME1 = (EditText) findViewById(R.id.PLAYER1);
        NAME2 = (EditText) findViewById(R.id.PLAYER2);

    }


    public void Submit(View view) {
        String nam1 = NAME1.getText().toString();
        String nam2 = NAME2.getText().toString();

        Intent intent = new Intent(getApplicationContext(), OfflinePageActivity.class);
        intent.putExtra("name1", nam1);
        intent.putExtra("name2", nam2);
        startActivity(intent);
    }
}