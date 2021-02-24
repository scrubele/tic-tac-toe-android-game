package com.example.tictactoegame.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tictactoegame.R;
import com.example.tictactoegame.activities.ChooseGameSizeActivity;
import com.example.tictactoegame.activities.ChoosePlayerActivity;
import com.example.tictactoegame.activities.OnlinePlayerActivity;

import java.util.Objects;

public class PlayGameFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_play_game, container, false);
        Button buttonOfflineGame = v.findViewById(R.id.offlineGame);
        buttonOfflineGame.setOnClickListener(this);
//        Button buttonOnlineGame = v.findViewById(R.id.onlineGame);
//        buttonOnlineGame.setOnClickListener(this);
        Button buttonRank = v.findViewById(R.id.rank);
        buttonRank.setOnClickListener(this);
        return v;
    }

    public void startOffLineGame(View view) {
        Intent intent = new Intent(Objects.requireNonNull(this.getActivity()).getApplicationContext(), ChooseGameSizeActivity.class);
        startActivity(intent);
    }


    public void startOnLineGame(View view) {
        Intent intent = new Intent(Objects.requireNonNull(this.getActivity()).getApplicationContext(), ChooseGameSizeActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.offlineGame:
                startOffLineGame(v);
                break;
//            case R.id.onlineGame:
//                startOnLineGame(v);
//                break;
            case R.id.rank:
                //startRank(v);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }

    }
}
