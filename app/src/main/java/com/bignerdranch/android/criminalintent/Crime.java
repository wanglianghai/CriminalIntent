package com.bignerdranch.android.criminalintent;

import java.util.UUID;

/**
 * Created by Administrator on 2017/4/26/026.
 */

public class Crime {
    private UUID mId;
    private String mTitle;
    public Crime() {
        mId = UUID.randomUUID();    //用Id便于区分不同的
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
}
