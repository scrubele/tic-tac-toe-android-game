package com.example.tictactoegame.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tictactoegame.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ItemDetailsActivity extends AppCompatActivity {
    private static final int TARGET_WIDTH = 100;
    private static final int TARGET_HEIGHT = 100;
    private AlertDialog dialog;

    private TextView name;
    private TextView goods;
    private TextView address;
    private TextView company;
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
        String ticTacToeName = getIntent().getStringExtra("ticTacToe_name");
        String ticTacToeGoods = getIntent().getStringExtra("ticTacToe_goods");
        String ticTacToeAddress = getIntent().getStringExtra("ticTacToe_address");
        String ticTacToeCompany = getIntent().getStringExtra("ticTacToe_company");
        String imageName = getIntent().getStringExtra("ticTacToe_img_url");

        setupViews();
        setupFields(ticTacToeName, ticTacToeGoods, ticTacToeAddress, ticTacToeCompany, imageName);
        if (getIntent().hasExtra("ticTacToe_err_mes")) {
            showDialog(getIntent().getStringExtra("ticTacToe_err_mes"));
        }
        backButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void setupViews() {
        name = findViewById(R.id.item_tic_tac_toe_name);
        goods = findViewById(R.id.item_tic_tac_toe_good);
        address = findViewById(R.id.item_tic_tac_toe_address);
        company = findViewById(R.id.item_tic_tac_toe_company);
        imageView = findViewById(R.id.item_tic_tac_toe_img);
        backButton = findViewById(R.id.button_back);
    }

    private void setupFields(String ticTacToeName, String ticTacToeGoods, String ticTacToeAddress,
                             String ticTacToeCompany, String imageName) {
        Picasso.get()
                .load(imageName)
                .placeholder(R.drawable.tic_tac_toe_placeholder)
                .resize(TARGET_WIDTH, TARGET_HEIGHT)
                .into(imageView);
        name.setText(ticTacToeName);
        goods.setText(ticTacToeGoods);
        address.setText(ticTacToeAddress);
        company.setText(ticTacToeCompany);
    }

    private void showDialog(String message) {
        dialog = new AlertDialog.Builder(this).create();

        dialog.setTitle(message);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Зберегти",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        dialog.show();
    }
}
