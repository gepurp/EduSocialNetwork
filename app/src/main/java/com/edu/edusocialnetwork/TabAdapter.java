package com.edu.edusocialnetwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

// I use it but don't really understand how it works ???

public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm); // Have to figure out how to use this constructor !!!
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            // Instantiating the profile tab when user pick the position '0' on the toolbar
            case 0:
                ProfileTab profileTab = new ProfileTab();
                return profileTab;
            // Instantiating the users tab when user pick the position '1' on the toolbar
            case 1:
                UsersTab usersTab = new UsersTab();
                return usersTab;
            // Instantiating the share tab when user pick the position '2' on the toolbar
            case 2:
                ShareTab shareTab = new ShareTab();
                return shareTab;
            default:
                return null; // Can I return null value in this method ???
        }
    }

    @Override
    public int getCount() {
        // Returning total count of tabs
        return 3;
    }

    @Nullable
    @Override
    // Returning the tab title depends on position of tab
    public CharSequence getPageTitle(int position) {
        // return super.getPageTitle(position);
        switch (position) {
            // Return title for profile tab
            case 0:
                return "Profile";
            // Return title for users tab
            case 1:
                return "Users";
            // Return title for share tab
            case 2:
                return "Share";
            default:
                return null;
        }
    }
}
