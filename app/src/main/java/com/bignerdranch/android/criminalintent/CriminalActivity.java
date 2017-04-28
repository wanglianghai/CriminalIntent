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
    private static final String EXTRA_CRIME_ID = "criminalIntent.crime_id";
    public static Intent newIntent(Context context, UUID crimeId) {
        Intent intent = new Intent(context, CriminalActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    public String setString() {
        return "Criminal detail";
    }

    @Override
    public Fragment createFragment() {
        UUID uuid = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(uuid);
    }  //支持fragment的activity

}
