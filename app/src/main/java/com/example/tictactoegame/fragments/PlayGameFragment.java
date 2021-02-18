package com.example.tictactoegame.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tictactoegame.R;
import com.example.tictactoegame.activities.ChoosePlayerActivity;

import java.util.Objects;

public class PlayGameFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_play_game, container, false);
        Button b = v.findViewById(R.id.offlineGame);
        b.setOnClickListener(this);
        return v;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_home);
//    }


    public void startOffLineGame(View view) {
        Intent intent = new Intent(Objects.requireNonNull(this.getActivity()).getApplicationContext(), ChoosePlayerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.offlineGame:
                startOffLineGame(v);
                break;
        }
    }
}
