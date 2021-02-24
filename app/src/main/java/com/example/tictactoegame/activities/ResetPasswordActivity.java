package com.example.tictactoegame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tictactoegame.R;
import com.example.tictactoegame.helpers.ConfigHelper;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import static com.example.tictactoegame.R.string.failed_to_send_email;
import static com.example.tictactoegame.R.string.sent_intstructions_to_reset_password;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText email;
    private Button resetPasswordButton;
    private ImageButton backButton;
    //    private Button linkSignIn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        setupViews();

        mAuth = FirebaseAuth.getInstance();
        mBase = FirebaseDatabase.getInstance(Objects.requireNonNull(ConfigHelper.getConfigValue(this, "firebase_url")));

        resetPasswordButton.setOnClickListener(v -> {
                    String emailString = email.getText().toString();
                    resetPassword(emailString);
                }
        );

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        });

    }

    public void setupViews() {
        MaterialCardView relativeLayout = (MaterialCardView) findViewById(R.id.reset_password_layout);
        email = relativeLayout.findViewById(R.id.email_reset_password);
        resetPasswordButton = relativeLayout.findViewById(R.id.button_reset_password);
        backButton = findViewById(R.id.button_back);
        progressBar = findViewById(R.id.progress_bar_sign_up);
    }

    private void resetPassword(final String email) {
        if (!validateEmail(email))
            return;
        progressBar.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, sent_intstructions_to_reset_password, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, failed_to_send_email, Toast.LENGTH_SHORT).show();
                    }

                    progressBar.setVisibility(View.GONE);
                });
    }


    private boolean validateEmail(final String mEmail) {
        if (mEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            String emailError = getString(R.string.email_error);
            email.setError(emailError);
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
}

