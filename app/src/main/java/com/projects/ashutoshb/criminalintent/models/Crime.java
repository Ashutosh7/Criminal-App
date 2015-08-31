package com.projects.ashutoshb.criminalintent.models;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ashutosh.b on 8/27/15.
 */
public class Crime {

    private UUID mId;
    private String mTitle;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    private Date mDate;
    private boolean mSolved;

    public UUID getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }


    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }
}
