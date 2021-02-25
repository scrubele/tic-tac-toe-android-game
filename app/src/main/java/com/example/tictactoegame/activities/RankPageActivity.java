package com.example.tictactoegame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tictactoegame.R;
import com.example.tictactoegame.helpers.ConfigHelper;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Objects;

public class RankPageActivity extends AppCompatActivity {
    private TextView rankScore;

    private Button userSignUp;
    private ImageButton backButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rank_page);

        setupViews();

        mAuth = FirebaseAuth.getInstance();
        mBase = FirebaseDatabase.getInstance(Objects.requireNonNull(ConfigHelper.getConfigValue(this, "firebase_url")));

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(this, SignInActivity.class));
        });

    }

    public void setupViews() {
        rankScore = findViewById(R.id.rankScore);
        backButton = findViewById(R.id.button_back);

    }

}
