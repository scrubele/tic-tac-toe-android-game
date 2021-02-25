package com.example.tictactoegame.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tictactoegame.R;
import com.example.tictactoegame.activities.ItemDetailsActivity;
import com.example.tictactoegame.entities.TicTacToe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TicTacToesAdapter extends RecyclerView.Adapter<TicTacToesAdapter.TicTacToeViewHolder> {
    private static final int TARGET_WIDTH = 88;
    private static final int TARGET_HEIGHT = 86;

    private List<TicTacToe> tictactoes;
    private Context mContext;

    public TicTacToesAdapter(Context context, List<TicTacToe> tictactoes) {
        this.mContext = context;
        this.tictactoes = tictactoes;
    }

    @NonNull
    @Override
    public TicTacToeViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                                  final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_tictactoe, parent, false);
        return new TicTacToeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TicTacToeViewHolder holder,
                                 final int position) {
        holder.ticTacToeName.setText(tictactoes.get(position).getName());
        holder.ticTacToeGameType.setText(tictactoes.get(position).getGameType());
        holder.ticTacToeDescription.setText(tictactoes.get(position).getDescription());
        Picasso.get()
                .load(tictactoes.get(position).getPicture())
                .placeholder(R.drawable.tic_tac_toe_placeholder)
                .resize(TARGET_WIDTH, TARGET_HEIGHT)
                .centerCrop()
                .into(holder.ticTacToeImageUrl);
        holder.parentLayout.setOnClickListener(view -> {
            openItemDetails(position);
        });
    }

    public void openItemDetails(int position) {
        Log.d("FROM", tictactoes.toString());

        Intent intent = new Intent(mContext, ItemDetailsActivity.class);
        intent.putExtra("tic_tac_toe_name", tictactoes.get(position).getName());
        intent.putExtra("tic_tac_toe_game_type", tictactoes.get(position).getGameType());
        intent.putExtra("tic_tac_toe_description", tictactoes.get(position).getDescription());
        intent.putExtra("tic_tac_toe_img_url", tictactoes.get(position).getPicture());

        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return tictactoes.size();
    }

    public List<TicTacToe> getTicTacToes() {
        return tictactoes;
    }

    class TicTacToeViewHolder extends RecyclerView.ViewHolder {
        private final TextView ticTacToeName;
        private final TextView ticTacToeGameType;
        private final TextView ticTacToeDescription;
        private final ImageView ticTacToeImageUrl;
        private ConstraintLayout parentLayout;

        TicTacToeViewHolder(final View itemView) {
            super(itemView);
            ticTacToeName = itemView.findViewById(R.id.item_tic_tac_toe_name);
            ticTacToeGameType = itemView.findViewById(R.id.item_tic_tac_toe_game_type);
            ticTacToeDescription = itemView.findViewById(R.id.item_tic_tac_toe_description);
            ticTacToeImageUrl = itemView.findViewById(R.id.item_tic_tac_toe_img);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
