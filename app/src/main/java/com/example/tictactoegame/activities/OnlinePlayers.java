package com.example.tictactoegame.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.view.View;

import com.example.tictactoegame.R;
import com.example.tictactoegame.fragments.PlayGameFragment;
import com.example.tictactoegame.fragments.ProfileFragment;
import com.example.tictactoegame.helpers.ConfigHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OnlinePlayers extends AppCompatActivity  {

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private DatabaseReference nameRef;
    private FirebaseDatabase databaseReference;
    private DatabaseReference usersReference;
    private DatabaseReference onlineRanksReference;
    private DatabaseReference currentUserReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_players);
        setupViews("XXXXXXXX");


    }

    public void home() {
        Intent intent = new Intent(getApplicationContext(), PlayGameFragment.class);
        startActivity(intent);
    }
    private void setupViews(String blabla) {
        storageReference = FirebaseStorage.getInstance().getReference("users");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance(ConfigHelper.getConfigValue(this, "firebase_url"));
        usersReference = databaseReference.getReference("users");
        onlineRanksReference = databaseReference.getReference("onlineRanks");
        currentUserReference = usersReference.child(firebaseUser.getUid());
        nameRef = currentUserReference.child("name");

        nameRef.child("name").setValue(blabla);
    }
}