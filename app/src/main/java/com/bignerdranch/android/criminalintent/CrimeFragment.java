package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Context;
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

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2017/4/26/026.
 */
//一个需要的fragment
public class CrimeFragment extends Fragment {
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String ARG_CRIME_SUBTITLE = "crime_subtitle";
    private static final String TAG_CRIME_DATE = "Dialog_date";
    private static final String TAG_CRIME_TIME = "DialogTime";
    private static final int REQUEST_CODE = 0;
    private static final int REQUEST_TIME = 1;
    private Crime mCrime;   //crime放这fragment中在这里设置要用crime的对象
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolveCheckBox;
    private Button mFinishButton;
    private Button mButtonDelete;
    private EditText mEditTextDetail;
    private Button mTimeEditorButton;
    private Callbacks mCallbacks;
    public interface Callbacks{
        void onCrimeUpdate();
    }
    public static CrimeFragment newInstance(UUID uuid, boolean click) {
        Bundle arg = new Bundle();
        arg.putSerializable(ARG_CRIME_ID, uuid);
        arg.putSerializable(ARG_CRIME_SUBTITLE, click);
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(arg);
        return crimeFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //关闭时保存下来，重开时用保存的
        UUID uuid = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        //一串中（特定的）
        mCrime = CrimeLab.getCrimeLab(getContext()).getCrime(uuid);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        mTimeEditorButton = (Button) view.findViewById(R.id.crime_select_time);
        mTimeEditorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment timePicker = TimePickerFragment.getInstance(mCrime.getDate());
                timePicker.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                timePicker.show(manager, TAG_CRIME_TIME);
            }
        });
        mEditTextDetail = (EditText) view.findViewById(R.id.crime_editor_detail);
        mEditTextDetail.setText(mCrime.getContent());
        mEditTextDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setContent(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDateButton = (Button) view.findViewById(R.id.crime_date);
        updateDateButtonText();
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
        updateSolves(mCrime);
        mSolveCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
                updateCrime();
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
                CrimeLab crimeLab = CrimeLab.getCrimeLab(getContext());
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
                updateCrime();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    public void updateSolves(Crime crime) {
   //     mSolveCheckBox.setChecked(CrimeLab.getCrimeLab(getActivity()).getCrime(mCrime.getId()).isSolved());
        mSolveCheckBox.setChecked(crime.isSolved());
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.getCrimeLab(getContext()).upDate(mCrime);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateCrime();
            updateDateButtonText();
        }

        if (requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mCrime.setDate(date);
            mTimeEditorButton.setText(mCrime.getTimeString());
        }
    }

    private void updateCrime() {
        CrimeLab.getCrimeLab(getActivity()).upDate(mCrime);
        mCallbacks.onCrimeUpdate();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    private void updateDateButtonText() {
        mDateButton.setText(mCrime.getDateString());
    }

    private void finish() {
        boolean click = (boolean) getArguments().getSerializable(ARG_CRIME_SUBTITLE);
        Intent intent = CrimeListActivity.newIntent(getActivity(), click);
        startActivity(intent);
    }

}
