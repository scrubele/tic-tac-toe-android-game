package com.example.tictactoegame.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tictactoegame.R;
import com.example.tictactoegame.activities.SignInActivity;
import com.example.tictactoegame.adapters.TicTacToesAdapter;
import com.example.tictactoegame.api.TicTacToeApiClient;
import com.example.tictactoegame.entities.TicTacToe;
import com.example.tictactoegame.utils.ApplicationEx;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataListFragment extends Fragment {
    private RecyclerView recyclerView;
    private TicTacToesAdapter tictactoesAdapter;
    private SwipeRefreshLayout refreshLayout;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private View content;
    private List<TicTacToe> responseList;

    public List<TicTacToe> getResponseList() {
        return responseList;
    }

    public TicTacToesAdapter getTicTacToesAdapter() {
        return tictactoesAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        setupViews(rootView);
//        checkConnection();
        loadTicTacToes();
        setSwipeToRefresh();
        return rootView;
    }

//    private void checkConnection() {
//        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
//        ConnectionChangeReceiver receiver = new ConnectionChangeReceiver(content);
//        Objects.requireNonNull(getActivity()).registerReceiver(receiver, filter);
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signOut();
        progressBar.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);
        return true;
    }

    private void setSwipeToRefresh() {
        refreshLayout.setOnRefreshListener(() -> {
            loadTicTacToes();
            new Handler().postDelayed(() -> refreshLayout.setRefreshing(false), 0);
        });
    }

    private void setupViews(View rootView) {
        recyclerView = rootView.findViewById(R.id.recycler_view);
        refreshLayout = rootView.findViewById(R.id.swipe_refresher);
        progressBar = rootView.findViewById(R.id.progress_bar_main);
        content = rootView.findViewById(R.id.content_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAuth = getApplicationEx().getAuth();
    }

    public void loadTicTacToes() {
//        progressBar.setVisibility(View.VISIBLE);
        final TicTacToeApiClient apiService = getApplicationEx().getTicTacToeApiClient();
        final Call<List<TicTacToe>> call = apiService.getTicTacToes();

        call.enqueue(new Callback<List<TicTacToe>>() {
            @Override
            public void onResponse(final Call<List<TicTacToe>> call,
                                   final Response<List<TicTacToe>> response) {
                responseList = response.body();
                tictactoesAdapter = new TicTacToesAdapter(content.getContext(), responseList);
                recyclerView.setAdapter(tictactoesAdapter);
//                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<TicTacToe>> call, Throwable t) {
//                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private ApplicationEx getApplicationEx() {
        return ((ApplicationEx) Objects.requireNonNull(getActivity()).getApplication());
    }
}
