package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/4/27/027.
 */
//模型
//单例，私有构造，对象私有get返回（没有创建）,一直在内存中,内部都唯一
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimeList;

    public static CrimeLab getCrimeLab() {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab();
        }
        return sCrimeLab;
    }

    public List<Crime> getCrimeList() {
        return mCrimeList;
    }

    public Crime getCrime(UUID uuid) {
        for (Crime crime : mCrimeList) {
            if (crime.getId().equals(uuid)) {
                return crime;
            }
        }
        return null;
    }

    //context有什么用？
    private CrimeLab() {
        mCrimeList = new ArrayList<>();
    }

    public void addCrime(Crime c) {
        mCrimeList.add(c);
    }
}
