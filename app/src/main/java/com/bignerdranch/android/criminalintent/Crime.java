package com.bignerdranch.android.criminalintent;

import android.text.format.DateFormat;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2017/4/26/026.
 */

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mContent;

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {

        mContent = content;
    }

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
        //用Id便于区分不同的
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDateString() {
        return (String) DateFormat.format("yyyy年MM月dd日",mDate);
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

}
