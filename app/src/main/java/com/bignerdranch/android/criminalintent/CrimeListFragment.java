package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/27/027.
 */

public class CrimeListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycler_view);
        CrimeLab crimeLab = CrimeLab.getCrimeLab(getActivity());
        CrimeAdapter crimeAdapter = new CrimeAdapter(crimeLab.getCrimeList());
        recyclerView.setAdapter(crimeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    //holder 视图展示
    public class CrimeHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;
        private TextView mDate;
        private CheckBox mCheckBox;
        public CrimeHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.list_item_title);
            mDate = (TextView) itemView.findViewById(R.id.list_item_date);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_check_box);
        }
    }

    //recyclerView的adapter处理一堆要集合
    public class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            //item的layout
            View view = inflater.inflate(R.layout.crime_list_item, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime itemCrime = mCrimes.get(position);
            holder.mCheckBox.setChecked(itemCrime.isSolved());
            holder.mDate.setText(itemCrime.getDate().toString());
            holder.mTextView.setText(itemCrime.getTitle());
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
