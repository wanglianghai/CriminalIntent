package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeDbSchema;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeDbSchema.CrimeTable;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.TreasureBaseHelper;

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
    private SQLiteDatabase mDatabase;

    public static CrimeLab getCrimeLab(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public List<Crime> getCrimeList() {
        return new ArrayList<>();
    }

    public Crime getCrime(UUID uuid) {
        return null;
    }

    //context有什么用？
    private CrimeLab(Context context) {
        mDatabase = new TreasureBaseHelper(context.getApplicationContext()).getWritableDatabase();

    }

    public void addCrime(Crime c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void upDate(Crime c) {
        ContentValues values = getContentValues(c);
        String uuidString = c.getId().toString();

        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " = ? ", new String[]{uuidString});
    }

    public void delete(Crime c) {
        String uuidString = c.getId().toString();
        mDatabase.delete(CrimeTable.NAME, CrimeTable.Cols.UUID + " = ? ", new String[] {uuidString});
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.Content, crime.getContent());
        //会自己定义类型的吧
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        //true和false是1和0
        values.put(CrimeTable.Cols.SOLVE, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        return values;
    }
}
