package com.projects.ashutoshb.criminalintent.models;

import android.content.Context;
import android.util.Log;

import com.projects.ashutoshb.criminalintent.utils.CriminalIntentJSONSerializer;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ashutosh.b on 8/28/15.
 */
public class CrimeLab {


    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";

    private CriminalIntentJSONSerializer mSerializer;

    private ArrayList<Crime> mCrimes;
    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mSerializer = new CriminalIntentJSONSerializer(mAppContext,FILENAME);
        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (Exception e) {
            mCrimes = new ArrayList<Crime>();
            Log.e(TAG, "Error loading crimes: ", e);
        }
    }

    public static CrimeLab get(Context c) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public void deleteCrime(Crime c) {
        mCrimes.remove(c);
    }


    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public void addCrime(Crime c) {
        mCrimes.add(c);
    }


    public Crime getCrime(UUID id) {
        for (Crime c : mCrimes) {
            if (c.getmId().equals(id))
                return c;
        }
        return null;
    }

    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving crimes: ", e);
            return false;
        } }

}
