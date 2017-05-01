package com.bignerdranch.android.criminalintent.database.CrimeDbSchema;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.criminalintent.Crime;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeDbSchema.CrimeTable.Cols;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2017/5/1/001.
 */
//包装了cursor
public class CrimeCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(Cols.UUID));
        String title = getString(getColumnIndex(Cols.TITLE));
        String content = getString(getColumnIndex(Cols.Content));
        //can not store boolean values ?
        int isSolved = getInt(getColumnIndex(Cols.SOLVE));
        long date = getLong(getColumnIndex(Cols.DATE));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setSolved(isSolved != 0);         //return true is isSolved not equal 0
        crime.setContent(content);
        crime.setDate(new Date(date));
        crime.setTitle(title);

        return crime;
    }
}
