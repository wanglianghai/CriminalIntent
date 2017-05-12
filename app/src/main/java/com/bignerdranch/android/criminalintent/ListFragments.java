package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/12/012.
 */

public class ListFragments {
    private static ListFragments sFragments;
    private List<Fragment> mFragments;
    private ListFragments(){
        mFragments = new ArrayList<>();
    }
    public static List<Fragment> get() {
        if (sFragments == null) {
            sFragments = new ListFragments();
        }
        return sFragments.mFragments;
    }
}
