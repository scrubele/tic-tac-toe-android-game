package com.example.tictactoegame.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tictactoegame.R;
import com.example.tictactoegame.fragments.DataListFragment;
import com.example.tictactoegame.fragments.PlayGameFragment;
import com.example.tictactoegame.fragments.ProfileFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public PagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case (1):
                return new DataListFragment();
            case (0):
                return new PlayGameFragment();
            default:
                return new ProfileFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case (1):
                return mContext.getString(R.string.tictactoes);
            case (0):
                return mContext.getString(R.string.play_game);
            default:
                return mContext.getString(R.string.profile);
        }
    }
}
