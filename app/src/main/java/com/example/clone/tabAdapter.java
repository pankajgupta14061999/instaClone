package com.example.clone;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class tabAdapter extends FragmentPagerAdapter {

    public tabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                profile profile=new profile();
                return profile;
            case 1:
                return new user();
            case 2:
                return new image();
                default:
                    return null;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Profile";
            case 1:
                return "User";
            case 2:
                return "images";
                default:
                    return null;
        }
    }
}
