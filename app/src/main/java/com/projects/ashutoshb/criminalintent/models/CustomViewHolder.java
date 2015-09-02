package com.projects.ashutoshb.criminalintent.models;

import android.content.Context;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.ashutoshb.criminalintent.R;

import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ashutosh.b on 8/28/15.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder { //implements View.OnCreateContextMenuListener {

    Context context;
    @Bind(R.id.crime_list_item_titleTextView) public TextView titleTextView;
    @Bind(R.id.crime_list_item_dateTextView) public TextView dateTextView;
    @Bind(R.id.crime_list_item_solvedCheckBox) public CheckBox solvedCheckBox;

    public CustomViewHolder(View view, Context c) {
        super(view);
        context = c;
        ButterKnife.bind(this,view);
       // view.setOnCreateContextMenuListener(this);
    }


//       @Override
//       public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
//           View v = LayoutInflater.from(parent.get)
//       }



}
