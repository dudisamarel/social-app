package com.colman.social_app.intro;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.colman.social_app.R;

public class IntroAdapter extends FragmentPagerAdapter {

    private final Data[] data = {
            new Data("Register and set profile",
                    R.drawable.intro1),
            new Data("Be updated by posts on your feed", R.drawable.intro2),
            new Data("Create new post", R.drawable.intro3),
            new Data("Edit your profile", R.drawable.intro4),
            new Data("Personal Settings", R.drawable.intro5),
    };

    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return IntroFragment.newInstance(data[position], position);
    }

    @Override
    public int getCount() {
        return data.length;
    }

}