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
        this(UUID.randomUUID());
    }       //use Crime(UUID id)

    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
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
        return (String) DateFormat.format("入宝时间：yyyy年MM月dd日",mDate);
    }

    public String getTimeString() {
        return (String) DateFormat.format("hh:mm", mDate);
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
