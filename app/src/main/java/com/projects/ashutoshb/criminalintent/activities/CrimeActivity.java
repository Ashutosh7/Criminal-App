package com.projects.ashutoshb.criminalintent.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.projects.ashutoshb.criminalintent.R;
import com.projects.ashutoshb.criminalintent.fragments.CrimeFragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID)getIntent()
                .getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);

    }
}
