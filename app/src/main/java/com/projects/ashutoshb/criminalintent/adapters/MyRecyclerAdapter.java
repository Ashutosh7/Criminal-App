package com.projects.ashutoshb.criminalintent.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.ashutoshb.criminalintent.R;
import com.projects.ashutoshb.criminalintent.activities.CrimeActivity;
import com.projects.ashutoshb.criminalintent.activities.CrimePagerActivity;
import com.projects.ashutoshb.criminalintent.fragments.CrimeFragment;
import com.projects.ashutoshb.criminalintent.models.Crime;
import com.projects.ashutoshb.criminalintent.models.CustomViewHolder;

import java.util.List;

/**
 * Created by ashutosh.b on 8/28/15.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<Crime> CrimeList;
    private Context mContext;

    public MyRecyclerAdapter(Context context, List<Crime> CrimeList) {
        this.CrimeList = CrimeList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        Crime crime = CrimeList.get(i);
        customViewHolder.titleTextView.setText(crime.getmTitle());
        customViewHolder.dateTextView.setText(crime.getDate().toString());
        customViewHolder.solvedCheckBox.setChecked(crime.isSolved());

        View.OnClickListener clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getAdapterPosition();

                Crime crime = CrimeList.get(position);
                Intent i = new Intent(mContext, CrimePagerActivity.class);
                i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getmId());
                mContext.startActivity(i);
            }
        };

        customViewHolder.titleTextView.setOnClickListener(clickListener);
        customViewHolder.dateTextView.setOnClickListener(clickListener);
        customViewHolder.titleTextView.setTag(customViewHolder);
        customViewHolder.dateTextView.setTag(customViewHolder);

    }

    @Override
    public int getItemCount() {
        return (null != CrimeList ? CrimeList.size() : 0);
    }
}
