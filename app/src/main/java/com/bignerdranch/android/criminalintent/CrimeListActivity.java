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
    private static final String EXTRA_SUBTITLE = "crimeList.subtitle";

    public static Intent newIntent(Context context, boolean subtitle) {
        Intent intent = new Intent(context, CrimeListActivity.class);
        intent.putExtra(EXTRA_SUBTITLE, subtitle);
        return intent;
    }

    @Override
    protected int getLayoutResID() {
      // return R.layout.activity_two_pan;
        //用别名资源  ID依然是R.layout内部类
        return R.layout.activity_master_detail;
    }

    @Override
    public Fragment createFragment() {
        boolean click = false;
        if (getIntent().getSerializableExtra(EXTRA_SUBTITLE) != null) {
            click = (boolean) getIntent().getSerializableExtra(EXTRA_SUBTITLE);
        }
        return CrimeListFragment.newInstance(click);
    }
}
