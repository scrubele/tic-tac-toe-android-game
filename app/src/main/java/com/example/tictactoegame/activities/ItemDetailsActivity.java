package com.example.tictactoegame.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tictactoegame.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

public class ItemDetailsActivity extends AppCompatActivity {
    private static final int TARGET_WIDTH = 100;
    private static final int TARGET_HEIGHT = 100;
    private AlertDialog dialog;

    private TextView name;
    private TextView gameType;
    private TextView description;
    private ImageView imageView;
    private ImageButton backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        FirebaseMessaging.getInstance().subscribeToTopic("NEWS");

        displayTicTacToeFromIntent();
    }

    private void displayTicTacToeFromIntent() {
        String ticTacToeName = getIntent().getStringExtra("tic_tac_toe_name");
        String ticTacToeGoods = getIntent().getStringExtra("tic_tac_toe_description");
        String ticTacToeCompany = getIntent().getStringExtra("tic_tac_toe_game_type");
        String imageName = getIntent().getStringExtra("tic_tac_toe_img_url");

        setupViews();
        setupFields(ticTacToeName, ticTacToeGoods, ticTacToeCompany, imageName);
        if (getIntent().hasExtra("ticTacToe_err_mes")) {
            showDialog(getIntent().getStringExtra("ticTacToe_err_mes"));
        }
        backButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void setupViews() {
        name = findViewById(R.id.item_tic_tac_toe_name);
        gameType = findViewById(R.id.item_tic_tac_toe_description);
        description = findViewById(R.id.item_tic_tac_toe_game_type);
        imageView = findViewById(R.id.item_tic_tac_toe_img);
        backButton = findViewById(R.id.button_back);
    }

    private void setupFields(String ticTacToeName, String ticTacToeGoods,
                             String ticTacToeCompany, String imageName) {
        Picasso.get()
                .load(imageName)
                .placeholder(R.drawable.tic_tac_toe_placeholder)
                .resize(TARGET_WIDTH, TARGET_HEIGHT)
                .into(imageView);
        name.setText(ticTacToeName);
        gameType.setText(ticTacToeGoods);
        description.setText(ticTacToeCompany);
    }

    private void showDialog(String message) {
        dialog = new AlertDialog.Builder(this).create();

        dialog.setTitle(message);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Зберегти",
                (dialogInterface, i) -> {

                });
        dialog.show();
    }
}
