package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

public class CriminalActivity extends FragmentActivity {  //支持fragment的activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置activity的视图,fragment的开始，R.layout.activity_criminal是留给fragment的视图
        setContentView(R.layout.activity_criminal);

        FragmentManager fm = getSupportFragmentManager();
        //fragment加到哪个里面
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        //开始fragment没有
        if (fragment == null) {
            fragment = new CrimeFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)  //添加fragment到要放的位置
                    .commit();
        }
    }
}
