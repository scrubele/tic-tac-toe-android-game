package com.example.tictactoegame.api;


import com.example.tictactoegame.entities.TicTacToe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TicTacToeApiClient {
    @GET("1/")
    Call<List<TicTacToe>> getTicTacToes();
}
