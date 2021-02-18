package com.example.tictactoegame.utils;

import android.app.Application;

import com.example.tictactoegame.api.TicTacToeApiClient;
import com.example.tictactoegame.entities.TicTacToe;
import com.example.tictactoegame.helpers.ConfigHelper;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationEx extends Application {

    private FirebaseAuth mAuth;
    private TicTacToeApiClient ticTacToeApiClient;
    private List<TicTacToe> listOfTicTacToes;

    @Override
    public void onCreate() {
        super.onCreate();

//        makeNotification();
        mAuth = FirebaseAuth.getInstance();
        ticTacToeApiClient = createTicTacToeApiClient();
    }

    public List<TicTacToe> getListOfTicTacToes() {
        return listOfTicTacToes;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public TicTacToeApiClient getTicTacToeApiClient() {
        return ticTacToeApiClient;
    }

    private TicTacToeApiClient createTicTacToeApiClient() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConfigHelper.getConfigValue(this, "json_url"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(TicTacToeApiClient.class);
    }

//
//    public void makeNotification() {
////        progressBar.setVisibility(View.VISIBLE);
////        final TicTacToeApiClient apiService = getApplicationEx().getTicTacToeApiClient();
//        final Call<List<TicTacToe>> call = ticTacToeApiClient.getTicTacToes();
//
//        call.enqueue(new Callback<List<TicTacToe>>() {
//            @Override
//            public void onResponse(final Call<List<TicTacToe>> call,
//                                   final Response<List<TicTacToe>> response) {
//                listOfTicTacToes = response.body();
////                tictactoesAdapter = new TicTacToesAdapter(content.getContext(), responseList);
////                recyclerView.setAdapter(tictactoesAdapter);
////                progressBar.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onFailure(Call<List<TicTacToe>> call, Throwable t) {
////                progressBar.setVisibility(View.INVISIBLE);
//            }
//        });
//    }
}
