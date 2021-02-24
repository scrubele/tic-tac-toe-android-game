package com.example.tictactoegame.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tictactoegame.R;

public class FByFGameActivity extends AppCompatActivity implements View.OnClickListener {

    String NAME1;
    String NAME2;
    private Button[][] buttons = new Button[5][5];
    private boolean player1Turn = true;
    private int roundCount;
    private int Player1Points;
    private int Player2Points;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_f_by_f_game);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        textViewPlayer1.setTextColor(getResources().getColor(R.color.Aqua));
        textViewPlayer2.setTextColor(getResources().getColor(R.color.Black));

        NAME1 = getIntent().getStringExtra("name1");
        NAME2 = getIntent().getStringExtra("name2");
        textViewPlayer1.setText(NAME1);
        textViewPlayer2.setText(NAME2);


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resetGame();
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player1Turn) {
            ((Button) v).setText("X");
            textViewPlayer1.setTextColor(getResources().getColor(R.color.Black));
            textViewPlayer2.setTextColor(getResources().getColor(R.color.Aqua));
        } else {
            ((Button) v).setText("O");
            textViewPlayer1.setTextColor(getResources().getColor(R.color.Aqua));
            textViewPlayer2.setTextColor(getResources().getColor(R.color.Black));
        }

        roundCount++;
        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }

        } else if (roundCount == 25) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 5; i++) {
            for(int j=0;j<2;j++){
                if (field[i][j].equals(field[i][j+1]) && field[i][j].equals(field[i][j+2])&& field[i][j].equals(field[i][j+3]) && !field[i][j].equals("")) {
                    return true;
                 }
            }

        }

        for (int i = 0; i < 2; i++) {
            for(int j=0;j<5;j++){
                if (field[i][j].equals(field[i+1][j]) && field[i][j].equals(field[i+2][j])&& field[i][j].equals(field[i+3][j]) && !field[i][j].equals("")) {
                    return true;
                }
            }

        }


        for (int i=0; i<2;i++){
            if (field[i][i].equals(field[i+1][i+1]) && field[i][i].equals(field[i+2][i+2])&& field[i][i].equals(field[i+3][i+3]) && !field[i][i].equals("")) {
                return true;
            }
        }


        for (int i=4; i>2;i--){
            for(int j=0; j<2;j++)
                {if (field[i][j].equals(field[i-1][j+1]) && field[i][j].equals(field[i-2][j+2])&& field[i][j].equals(field[i-3][j+3]) && !field[i][j].equals("")) {
                return true;
                }
            }
        }

        return false;

    }

    private void player1Wins() {
        Player1Points++;
        Toast.makeText(this, NAME1 + " WINS!", Toast.LENGTH_SHORT).show();
        updatePointsText();

        resetBoard();
    }

    private void player2Wins() {
        Player2Points++;
        Toast.makeText(this, NAME2 + " WINS!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "DRAW!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText(NAME1 + " : " + Player1Points);
        textViewPlayer2.setText(NAME2 + " : " + Player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
        textViewPlayer2.setTextColor(getResources().getColor(R.color.Black));
        textViewPlayer1.setTextColor(getResources().getColor(R.color.Aqua));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("Player1Points", Player1Points);
        outState.putInt("Player2Points", Player2Points);
        outState.putBoolean("player1Turn", player1Turn);

    }

    private void resetGame() {
        Player1Points = 0;
        Player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        Player1Points = savedInstanceState.getInt("Player1Points");
        Player2Points = savedInstanceState.getInt("Player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }

    public void EndGame(View view) {
        String scor1 = String.valueOf(Player1Points);
        String scor2 = String.valueOf(Player2Points);
        Intent intent = new Intent(getApplicationContext(), ScorePageActivity.class);
        intent.putExtra("SCORE1", scor1);
        intent.putExtra("SCORE2", scor2);
        intent.putExtra("name1", NAME1);
        intent.putExtra("name2", NAME2);
        startActivity(intent);
    }
}
