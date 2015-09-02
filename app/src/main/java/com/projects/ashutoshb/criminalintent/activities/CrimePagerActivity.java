package com.projects.ashutoshb.criminalintent.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.projects.ashutoshb.criminalintent.R;
import com.projects.ashutoshb.criminalintent.fragments.CrimeFragment;
import com.projects.ashutoshb.criminalintent.models.Crime;
import com.projects.ashutoshb.criminalintent.models.CrimeLab;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ashutosh.b on 8/30/15.
 */
public class CrimePagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;

    public static ActivityOptions getTransition(Activity activity, View crimeView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            crimeView.setTransitionName("crime");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity,
                    crimeView, "crime");

            return options;
        } else {
            return null;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mViewPager.setTransitionName("crime");
        }
        setContentView(mViewPager);

        final ArrayList<Crime> crimes = CrimeLab.get(this).getCrimes();

        FragmentManager fm = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return crimes.size();
            }

            @Override
            public Fragment getItem(int pos) {
                UUID crimeId = crimes.get(pos).getmId();
                return CrimeFragment.newInstance(crimeId);
            }
        });

        UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        for (int i = 0; i < crimes.size(); i++) {
            if (crimes.get(i).getmId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

}
