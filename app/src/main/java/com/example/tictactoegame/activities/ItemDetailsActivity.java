package com.example.tictactoegame.activities;

import android.content.DialogInterface;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        FirebaseMessaging.getInstance().subscribeToTopic("NEWS");

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.ven_details));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displayTicTacToeFromIntent();
    }

    private void displayTicTacToeFromIntent() {
        String ticTacToeName = getIntent().getStringExtra("ticTacToe_name");
        String ticTacToeGoods = getIntent().getStringExtra("ticTacToe_goods");
        String ticTacToeAddress = getIntent().getStringExtra("ticTacToe_address");
        String ticTacToeCompany = getIntent().getStringExtra("ticTacToe_company");
        String imageName = getIntent().getStringExtra("ticTacToe_img_url");

        setupFields(ticTacToeName, ticTacToeGoods, ticTacToeAddress, ticTacToeCompany, imageName);
        if (getIntent().hasExtra("ticTacToe_err_mes")) {
            showDialog(getIntent().getStringExtra("ticTacToe_err_mes"));
        }
    }

    private void setupFields(String ticTacToeName, String ticTacToeGoods, String ticTacToeAddress,
                             String ticTacToeCompany, String imageName) {
        TextView name = findViewById(R.id.ven_name_detailed);
        TextView goods = findViewById(R.id.ven_goods_detailed);
        TextView address = findViewById(R.id.ven_address_detailed);
        TextView company = findViewById(R.id.ven_company_detailed);
        ImageView imageView = findViewById(R.id.ven_img_detailed);
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
