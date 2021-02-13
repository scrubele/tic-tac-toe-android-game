package com.example.tto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn =true;
    private int roundCount;
    private int Player1Points;
    private int Player2Points;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1=findViewById(R.id.text_view_p1);
        textViewPlayer2=findViewById(R.id.text_view_p2);

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                String buttonID="button_" + i + j;
                int resID= getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);

            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                resetGame();
            }
        });


    }

    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals("")){
            return;
        }
        if(player1Turn){
            ((Button) v).setText("X");
        }else {
            ((Button) v).setText("O");
        }

        roundCount++;
        if(checkForWin()){
            if(player1Turn){
                player1Wins();
            }else{
                player2Wins();
            }

        }else if(roundCount ==9){
            draw();
        }else{
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin(){
        String[][] field = new String[3][3];

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                field[i][j]= buttons[i][j].getText().toString();
            }
        }

        for (int i=0; i<3; i++){
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("") ){
                return true;
            }

        }

        for (int i=0; i<3; i++){
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("") ){
                return true;
            }

        }

        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("") ){
            return true;
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("") ){
            return true;
        }

        return false;

    }

    private void player1Wins(){
        Player1Points++;
        Toast.makeText(this, "PLAYER 1 WINS!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins(){
        Player2Points++;
        Toast.makeText(this, "PLAYER 2 WINS!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw(){
        Toast.makeText(this, "DRAW!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText(){
        textViewPlayer1.setText("Player 1: "+ Player1Points);
        textViewPlayer2.setText("Player 2 :" + Player2Points);
    }

    private void resetBoard(){
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount=0;
        player1Turn=true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("Player1Points", Player1Points);
        outState.putInt("Player2Points", Player2Points);
        outState.putBoolean("player1Turn",player1Turn);

    }

    private void resetGame(){
        Player1Points=0;
        Player2Points=0;
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
    public void EndGame(View view){

        Intent intent = new Intent(getApplicationContext(), Score.class);
        startActivity(intent);
    }


}