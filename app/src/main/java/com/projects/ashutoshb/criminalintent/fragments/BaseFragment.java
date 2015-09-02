package com.projects.ashutoshb.criminalintent.fragments;

/**
 * Created by ashutosh.b on 9/2/15.
 */
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class BaseFragment extends Fragment {

    protected ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }


}
