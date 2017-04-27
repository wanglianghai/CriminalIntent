package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import java.util.UUID;

//newIntent  UUID  URL区分
public class CriminalActivity extends SingleFragmentActivity {
    public static final String EXTRA_CRIME_ID = "criminalIntent.crime_id";
    public static Intent newIntent(Context context, UUID crimeId) {
        Intent intent = new Intent(context, CriminalActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }
    @Override
    public Fragment createFragment() {
        return new CrimeFragment();
    }  //支持fragment的activity

}
