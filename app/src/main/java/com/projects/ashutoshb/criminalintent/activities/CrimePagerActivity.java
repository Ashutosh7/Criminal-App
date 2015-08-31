package com.projects.ashutoshb.criminalintent.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.projects.ashutoshb.criminalintent.R;
import com.projects.ashutoshb.criminalintent.fragments.CrimeFragment;
import com.projects.ashutoshb.criminalintent.models.Crime;
import com.projects.ashutoshb.criminalintent.models.CrimeLab;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ashutosh.b on 8/30/15.
 */
public class CrimePagerActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);
        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getmId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Crime crime = mCrimes.get(position);
                if (crime.getmTitle() != null) {
                    setTitle(crime.getmTitle());
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        UUID crimeId = (UUID)getIntent()
                .getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getmId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break; }
        }
    }
}
