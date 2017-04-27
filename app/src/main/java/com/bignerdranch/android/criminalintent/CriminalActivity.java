package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

public class CriminalActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new CrimeFragment();
    }  //支持fragment的activity

}
