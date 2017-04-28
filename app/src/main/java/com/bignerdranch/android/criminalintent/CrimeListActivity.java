package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;

/**
 * Created by Administrator on 2017/4/27/027.
 */
//一个xml的布局可以被许多activity使用
//在manifest中配置
//大框架搭好后运行下
public class CrimeListActivity extends SingleFragmentActivity {
    public static final String EXTRA_SUBTITLE = "crimeList.subtitle";

    public static Intent newIntent(Context context, boolean subtitle) {
        Intent intent = new Intent(context, CrimeListActivity.class);
        intent.putExtra(EXTRA_SUBTITLE, subtitle);
        return intent;
    }
    @Override
    public Fragment createFragment() {
        return new CrimeListFragment();
    }
}
