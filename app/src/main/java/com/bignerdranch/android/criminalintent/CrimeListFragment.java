package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 2017/4/27/027.
 */

public class CrimeListFragment extends Fragment {
    RecyclerView mRecyclerView;
    CrimeAdapter mCrimeAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycler_view);
        upDateUI();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void upDateUI() {
        CrimeLab crimeLab = CrimeLab.getCrimeLab();
        //重新创建太浪费资源，有就直接更新
        if (mCrimeAdapter == null) {
            mCrimeAdapter = new CrimeAdapter(crimeLab.getCrimeList());
            mRecyclerView.setAdapter(mCrimeAdapter);
        } else {
            mCrimeAdapter.notifyDataSetChanged();
        }
    }

    //holder 视图展示 配点击事件
    //点击 1 实现接口 2 item setOnclickListener
    public class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTextView;
        private TextView mDate;
        private CheckBox mCheckBox;
        private Crime mCrime;
        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextView = (TextView) itemView.findViewById(R.id.list_item_title);
            mDate = (TextView) itemView.findViewById(R.id.list_item_date);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_check_box);
        }

        public void bind(final Crime crime) {
            mCrime = crime;
            mTextView.setText(crime.getTitle());
            mDate.setText(crime.getDate());
            mCheckBox.setChecked(crime.isSolved());
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mCrime.setSolved(isChecked);
                }
            });
        }
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Click", Toast.LENGTH_LONG).show();
            Intent intent = CriminalActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }

    //recyclerView的adapter处理一堆要集合
    //调整员工
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
            holder.bind(itemCrime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    //恢复时跟新
    @Override
    public void onResume() {
        super.onResume();
        upDateUI();
    }
}
