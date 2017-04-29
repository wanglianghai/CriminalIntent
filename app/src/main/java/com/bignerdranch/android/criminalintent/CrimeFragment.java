package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by Administrator on 2017/4/26/026.
 */
//一个需要的fragment
public class CrimeFragment extends Fragment {
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String ARG_CRIME_SUBTITLE = "crime_subtitle";
    private static final String TAG_CRIME_DATE = "Dialog_date";
    private static final int REQUEST_CODE = 0;
    private Crime mCrime;   //crime放这fragment中在这里设置要用crime的对象
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolveCheckBox;
    private Button mFinishButton;
    private Button mButtonDelete;
    public static CrimeFragment newInstance(UUID uuid, boolean click) {
        Bundle arg = new Bundle();
        arg.putSerializable(ARG_CRIME_ID, uuid);
        arg.putSerializable(ARG_CRIME_SUBTITLE, click);
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(arg);
        return crimeFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //关闭时保存下来，重开时用保存的
        UUID uuid = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        //一串中（特定的）
        mCrime = CrimeLab.getCrimeLab().getCrime(uuid);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        mDateButton = (Button) view.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDateString());
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                //直接用this找不到fragment
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_CODE);
                dialog.show(getFragmentManager(), TAG_CRIME_DATE);
            }
        });

        mSolveCheckBox = (CheckBox) view.findViewById(R.id.crime_solve);
        mSolveCheckBox.setChecked(mCrime.isSolved());
        mSolveCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        mFinishButton = (Button) view.findViewById(R.id.crime_finish);

        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mButtonDelete = (Button) view.findViewById(R.id.crime_delete);
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrimeLab crimeLab = CrimeLab.getCrimeLab();
                crimeLab.delete(mCrime);
                finish();
            }
        });
        mTitleField = (EditText) view.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void finish() {
        boolean click = (boolean) getArguments().getSerializable(ARG_CRIME_SUBTITLE);
        Intent intent = CrimeListActivity.newIntent(getActivity(), click);
        startActivity(intent);
    }

}
