package com.projects.ashutoshb.criminalintent.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.projects.ashutoshb.criminalintent.R;
import com.projects.ashutoshb.criminalintent.activities.CrimeActivity;
import com.projects.ashutoshb.criminalintent.activities.CrimePagerActivity;
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
public class CrimeListFragmentBackup extends Fragment {

    private ArrayList<Crime> mCrimes;
    private List<Crime> crimeList;
    private boolean mSubtitleVisible;
    @Bind(R.id.recycler_view)RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    AppCompatActivity activity;
    private MultiSelector mMultiSelector = new MultiSelector();


    private ModalMultiSelectorCallback mDeleteMode = new ModalMultiSelectorCallback(mMultiSelector) {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            super.onCreateActionMode(actionMode, menu);
            getActivity().getMenuInflater().inflate(R.menu.crime_list_item, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId()==  R.id.menu_item_delete_crime){
                // Need to finish the action mode before doing the following,
                // not after. No idea why, but it crashes.
                actionMode.finish();

                for (int i = mCrimes.size(); i >= 0; i--) {
                    if (mMultiSelector.isSelected(i, 0)) {
                        Crime crime = mCrimes.get(i);
                        CrimeLab.get(getActivity()).deleteCrime(crime);
                        mRecyclerView.getAdapter().notifyItemRemoved(i);
                    }
                }

                mMultiSelector.clearSelections();
                return true;

            }
            return false;
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMultiSelector.setSelectable(true);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        activity = (AppCompatActivity) getActivity();
        getActivity().setTitle(R.string.crimes_title);
        mCrimes = CrimeLab.get(getActivity()).getCrimes();
        mSubtitleVisible = false;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.activity_feed_list, container, false);
        ButterKnife.bind(this, v);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyRecyclerAdapter(getActivity(), mCrimes);
        mRecyclerView.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            if (mSubtitleVisible) {
                activity.getSupportActionBar().setSubtitle(R.string.subtitle);
            }
        registerForContextMenu(mRecyclerView);
        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible && showSubtitle != null) {
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent i = new Intent(getActivity(), CrimePagerActivity.class);
                i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getmId());
                startActivityForResult(i, 0);
                return true;
            case R.id.menu_item_show_subtitle:
                if(activity.getSupportActionBar().getSubtitle() == null) {
                    activity.getSupportActionBar().setSubtitle(R.string.subtitle);
                    mSubtitleVisible = true;
                    item.setTitle(R.string.hide_subtitle);
                } else {
                    activity.getSupportActionBar().setSubtitle(null);
                    mSubtitleVisible = false;
                    item.setTitle(R.string.show_subtitle);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        getActivity().getMenuInflater().inflate(R.menu.crime_list_item, menu);
//}

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterViewCompat.AdapterContextMenuInfo info = (AdapterViewCompat.AdapterContextMenuInfo)item.getMenuInfo();
//        int position = info.position;
//        Crime crime = mCrimes.get(position);
//        switch (item.getItemId()) {
//            case R.id.menu_item_delete_crime:
//                CrimeLab.get(getActivity()).deleteCrime(crime);
//                adapter.notifyDataSetChanged();
//                return true;
//        }
//        return super.onContextItemSelected(item);
//    }


    private class CrimeHolder extends SwappingHolder
            implements View.OnClickListener, View.OnLongClickListener {

        private final CheckBox mSolvedCheckBox;
        private Crime mCrime;

        public CrimeHolder(View itemView) {
            super(itemView, mMultiSelector);

            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.crime_list_item_solvedCheckBox);
            itemView.setOnClickListener(this);
            itemView.setOnClickListener(this);
            itemView.setLongClickable(true);
        }

        @Override
        public void onClick(View v) {
            if (mCrime == null) {
                return;
            }
            if (!mMultiSelector.tapSelection(this)) {
                // start an instance of CrimePagerActivity
                Intent i = new Intent(getActivity(), CrimePagerActivity.class);
                i.putExtra(CrimeFragment.EXTRA_CRIME_ID, mCrime.getmId());
                startActivity(i);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            AppCompatActivity activity = (AppCompatActivity)getActivity();
            activity.startSupportActionMode(mDeleteMode);
            mMultiSelector.setSelected(this, true);
            return false;
        }
    }

}
