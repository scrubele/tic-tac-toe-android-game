package com.example.tictactoegame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tictactoegame.R;
import com.example.tictactoegame.activities.ChoosePlayerActivity;
import com.example.tictactoegame.activities.OnlinePlayerActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.Objects;

public class ChooseGameSizeActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game_size);

        MaterialCardView relativeLayout = (MaterialCardView) findViewById(R.id.layout_choose_game_size);

        Button buttonOfflineGame = relativeLayout.findViewById(R.id._3x3GameSize);
        buttonOfflineGame.setOnClickListener(this);
        Button buttonOnlineGame = relativeLayout.findViewById(R.id._5x5GameSize);
        buttonOnlineGame.setOnClickListener(this);
    }

    public void start3x3Game(View view) {
        Intent intent = new Intent(Objects.requireNonNull(this).getApplicationContext(), ChoosePlayerActivity.class);
        intent.putExtra("gameSize", "3x3");
        startActivity(intent);
    }


    public void start5x5Game(View view) {
        Intent intent = new Intent(Objects.requireNonNull(this).getApplicationContext(), ChoosePlayerActivity.class);
        intent.putExtra("gameSize", "5x5");
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._3x3GameSize:
                start3x3Game(v);
                break;
            case R.id._5x5GameSize:
                start5x5Game(v);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }

    }
}
