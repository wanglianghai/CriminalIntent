package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2017/4/29/029.
 */

public class TimePickerFragment extends DialogFragment {
    private static final String ARG_TIME = "time";
    public static final String EXTRA_TIME = "com.treasure.time";
    private Date mDate;
    private TimePicker mTimePicker;
    private int mYear, mMonth, mDay;
    public static TimePickerFragment getInstance(Date date) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    private void sendResult(int requestCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), requestCode, intent);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments().getSerializable(ARG_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int hours = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date_detail, null);
        mTimePicker = (TimePicker) view.findViewById(R.id.select_dialog_date_time);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setHour(hours);
            mTimePicker.setMinute(minute);
        }
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_time_title)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date date = new Date();
                        int hours,minter;
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        hours = calendar.get(Calendar.HOUR);
                        minter = calendar.get(Calendar.MINUTE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            hours = mTimePicker.getHour();
                            minter = mTimePicker.getMinute();
                        }
                        calendar.setTime(mDate);

                        date = new GregorianCalendar(mYear, mMonth, mDay, hours, minter).getTime();
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }
}
