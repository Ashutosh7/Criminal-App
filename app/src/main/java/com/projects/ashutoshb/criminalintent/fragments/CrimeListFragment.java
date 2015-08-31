package com.projects.ashutoshb.criminalintent.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.ashutoshb.criminalintent.R;
import com.projects.ashutoshb.criminalintent.activities.CrimeActivity;
import com.projects.ashutoshb.criminalintent.adapters.MyRecyclerAdapter;
import com.projects.ashutoshb.criminalintent.models.Crime;
import com.projects.ashutoshb.criminalintent.models.CrimeLab;
import com.projects.ashutoshb.criminalintent.models.CustomViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ashutosh.b on 8/28/15.
 */
public class CrimeListFragment extends Fragment {

    private ArrayList<Crime> mCrimes;
    private List<Crime> crimeList;
    @Bind(R.id.recycler_view)RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crimes_title);
        mCrimes = CrimeLab.get(getActivity()).getCrimes();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_feed_list, container, false);
        ButterKnife.bind(this, v);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyRecyclerAdapter(getActivity(), mCrimes);
        mRecyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
