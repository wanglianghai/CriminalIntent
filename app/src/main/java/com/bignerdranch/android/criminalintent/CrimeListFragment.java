package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private static final String AGR_CRIME_SUBTITLE = "crime_subtitle";
    private RecyclerView mRecyclerView;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private CrimeAdapter mCrimeAdapter;
    private boolean mBooleanClick;
    int mPosition;

    public static CrimeListFragment newInstance(boolean booleanClick) {
        Bundle arg = new Bundle();
        arg.putSerializable(AGR_CRIME_SUBTITLE, booleanClick);

        CrimeListFragment crimeListFragment = new CrimeListFragment();
        crimeListFragment.setArguments(arg);
        return crimeListFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycler_view);
        upDateUI();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (savedInstanceState != null) {
            mBooleanClick = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_SUBTITLE_VISIBLE, mBooleanClick);
    }

    private void upDateUI() {
        CrimeLab crimeLab = CrimeLab.getCrimeLab();
        //重新创建太浪费资源，有就直接更新
        if (mCrimeAdapter == null) {
            mCrimeAdapter = new CrimeAdapter(crimeLab.getCrimeList());
            mRecyclerView.setAdapter(mCrimeAdapter);
        } else {
            mCrimeAdapter.notifyItemChanged(mPosition);
        }
        upDateSubtitle();
        mBooleanClick = (boolean) getArguments().getSerializable(AGR_CRIME_SUBTITLE);
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
            mPosition = mRecyclerView.getChildAdapterPosition(v);
            Toast.makeText(getActivity(), "Click", Toast.LENGTH_LONG).show();
            Intent intent = CriminalActivity.newIntent(getActivity(), mCrime.getId(), mBooleanClick);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem subtitle = menu.findItem(R.id.menu_item_subtitle);
        if (mBooleanClick) {
            subtitle.setTitle(R.string.hide_subtitle);
        } else {
            subtitle.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab  crimeLab = CrimeLab.getCrimeLab();
                crimeLab.addCrime(crime);
                Intent intent = CriminalActivity.newIntent(getActivity(), crime.getId(), mBooleanClick);
                startActivity(intent);
                return true;
            case R.id.menu_item_subtitle:
                mBooleanClick = !mBooleanClick;
                getActivity().invalidateOptionsMenu();
                upDateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void upDateSubtitle() {
        int  crimeLabNum = CrimeLab.getCrimeLab().getCrimeList().size();
        String subtitle = "宝贝数量" + crimeLabNum;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (!mBooleanClick) {
            subtitle = null;
        }
        activity.getSupportActionBar().setSubtitle(subtitle);
    }
}
