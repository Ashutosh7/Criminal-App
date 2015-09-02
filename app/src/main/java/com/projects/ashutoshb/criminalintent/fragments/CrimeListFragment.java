package com.projects.ashutoshb.criminalintent.fragments;

import android.support.v7.widget.RecyclerView;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.projects.ashutoshb.criminalintent.R;
import com.projects.ashutoshb.criminalintent.activities.CrimePagerActivity;
import com.projects.ashutoshb.criminalintent.fragments.CrimeFragment;
import com.projects.ashutoshb.criminalintent.models.Crime;
import com.projects.ashutoshb.criminalintent.models.CrimeLab;

import java.util.ArrayList;

public class CrimeListFragment extends BaseFragment {

    private static final String TAG = "crimeListFragment";
    private RecyclerView mRecyclerView;
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
    private ArrayList<Crime> mCrimes;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.crimes_title);
        mSubtitleVisible = false;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        if (mMultiSelector != null) {
            Bundle bundle = savedInstanceState;
            if (bundle != null) {
                mMultiSelector.restoreSelectionStates(bundle.getBundle(TAG));
            }

            if (mMultiSelector.isSelectable()) {
                if (mDeleteMode != null) {
                    mDeleteMode.setClearOnPrepare(false);
                    ((AppCompatActivity) getActivity()).startSupportActionMode(mDeleteMode);
                }

            }
        }

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBundle(TAG, mMultiSelector.saveSelectionStates());
        super.onSaveInstanceState(outState);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view, parent, false);

        if (mSubtitleVisible) {
            getActionBar().setSubtitle(R.string.subtitle);
        }

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCrimes = CrimeLab.get(getActivity()).getCrimes();
        mRecyclerView.setAdapter(new CrimeAdapter());

        return v;
    }

    private void selectCrime(Crime c) {
        // start an instance of CrimePagerActivity
        Intent i = new Intent(getActivity(), CrimePagerActivity.class);
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getmId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int index = mCrimes.indexOf(c);
            CrimeHolder holder = (CrimeHolder) mRecyclerView
                    .findViewHolderForLayoutPosition(index);

            ActivityOptions options = CrimePagerActivity.getTransition(
                    getActivity(), holder.itemView);

            i.putExtras(options.toBundle());
            startActivityForResult(i, 0);
        } else {
            startActivityForResult(i, 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==  R.id.menu_item_new_crime) {
            final Crime crime = new Crime();
            CrimeLab.get(getActivity()).addCrime(crime);
            mRecyclerView.getAdapter().notifyItemInserted(mCrimes.indexOf(crime));
            return true;
        }
        else if(item.getItemId()==R.id.menu_item_show_subtitle) {
            ActionBar actionBar = getActionBar();
            if (actionBar.getSubtitle() == null) {
                actionBar.setSubtitle(R.string.subtitle);
                mSubtitleVisible = true;
                item.setTitle(R.string.hide_subtitle);
            } else {
                actionBar.setSubtitle(null);
                mSubtitleVisible = false;
                item.setTitle(R.string.show_subtitle);
            }
            return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item, menu);
    }


    private class CrimeHolder extends SwappingHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView mTitleTextView;
        private final TextView mDateTextView;
        private final CheckBox mSolvedCheckBox;
        private Crime mCrime;

        public CrimeHolder(View itemView) {
            super(itemView, mMultiSelector);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_list_item_titleTextView);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_list_item_dateTextView);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.crime_list_item_solvedCheckBox);
            itemView.setOnClickListener(this);
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(this);
        }

        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(crime.getmTitle());
            mDateTextView.setText(crime.getDate().toString());
            mSolvedCheckBox.setChecked(crime.isSolved());
        }

        @Override
        public void onClick(View v) {

            if (mCrime == null) {
                return;
            }
            if (!mMultiSelector.tapSelection(this)) {
                selectCrime(mCrime);
            }

        }


        @Override
        public boolean onLongClick(View v) {

            ((AppCompatActivity) getActivity()).startSupportActionMode(mDeleteMode);
            mMultiSelector.setSelected(this, true);
            return true;
        }


    }


    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int pos) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_view_item, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int pos) {
            Crime crime = mCrimes.get(pos);
            holder.bindCrime(crime);
            Log.d(TAG, "binding crime" + crime + "at position" + pos);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}