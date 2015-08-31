package com.projects.ashutoshb.criminalintent.models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.ashutoshb.criminalintent.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ashutosh.b on 8/28/15.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.crime_list_item_titleTextView) public TextView titleTextView;
    @Bind(R.id.crime_list_item_dateTextView) public TextView dateTextView;
    @Bind(R.id.crime_list_item_solvedCheckBox) public CheckBox solvedCheckBox;

    public CustomViewHolder(View view) {
        super(view);
        ButterKnife.bind(this,view);
    }
}
