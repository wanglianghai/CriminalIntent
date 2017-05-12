package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;

/**
 * Created by Administrator on 2017/4/27/027.
 */
//一个xml的布局可以被许多activity使用
//在manifest中配置
//大框架搭好后运行下
public class CrimeListActivity extends AppCompatActivity
        implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks, TreasureBottomFragment.CallBacksB {
    private static final String TAG = "CrimeListActivity";
    private static final String EXTRA_SUBTITLE = "crimeList.subtitle";
    private ViewPager mViewPager;
    private Fragment mBottomFragment;
    private TreasureBottomFragment mTreasureBottomFragment;

    public static Intent newIntent(Context context, boolean subtitle) {
        Intent intent = new Intent(context, CrimeListActivity.class);
        intent.putExtra(EXTRA_SUBTITLE, subtitle);
        return intent;
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());

        final FragmentManager fm = getSupportFragmentManager();
        mBottomFragment = fm.findFragmentById(R.id.fragment_bottom);

        if (mBottomFragment == null) {
            mBottomFragment = new TreasureBottomFragment();
            mTreasureBottomFragment = (TreasureBottomFragment) mBottomFragment;
            Log.i(TAG, "addMoreFragment: " + 0);
            fm.beginTransaction()
                    .add(R.id.fragment_bottom, mBottomFragment)
                    .commit();
        }

        mViewPager = (ViewPager) findViewById(R.id.fragment_container);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {

                switch (position) {
                    case 0:
                        return createFragment();
                    case 1:
                        PhotoFragment photoFragment = new PhotoFragment();
                        ListFragments.get().add(photoFragment);
                        return photoFragment;
                    case 2:
                        return new PhoneFragment();
                    case 3:
                        return new PictureFragment();
                    case 4:
                        return new ManFragment();
                    default:
                        return createFragment();
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTreasureBottomFragment.updateUI(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: " + mViewPager.getCurrentItem());
    }

    protected int getLayoutResID() {
      // return R.layout.activity_two_pan;
        //用别名资源  ID依然是R.layout内部类
        return R.layout.activity_master_detail;
    }

    public Fragment createFragment() {
        boolean click = false;
        if (getIntent().getSerializableExtra(EXTRA_SUBTITLE) != null) {
            click = (boolean) getIntent().getSerializableExtra(EXTRA_SUBTITLE);
        }
        return CrimeListFragment.newInstance(click);
    }

    @Override
    public void onCrimeSelected(Crime crime, boolean click) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent i = CriminalActivity.newIntent(this, crime.getId(), click);
            startActivity(i);
        } else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getId(), click);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    @Override
    public void updateSolve(Crime crime) {
        CrimeFragment crimeFragment = (CrimeFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_fragment_container);
        if (crimeFragment == null) {
            return;
        }
        crimeFragment.updateSolves(crime);
    }



    @Override
    public void onCrimeUpdate() {
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.upDateUI();
    }

    @Override
    public void update(int i) {
        mViewPager.setCurrentItem(i);
    }
}
