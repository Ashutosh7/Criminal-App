package com.projects.ashutoshb.criminalintent.activities;

import android.support.v4.app.Fragment;

import com.projects.ashutoshb.criminalintent.fragments.CrimeListFragment;

/**
 * Created by ashutosh.b on 8/28/15.
 */
public class CrimeListActivity extends SingleFragmentActivity{


    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
