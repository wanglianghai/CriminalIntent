package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/5/11/011.
 */

public class PhotoFragment extends Fragment {
    private static final String AGR_PAGE_POSITION = "page_position";
    public PhotoFragment newInstance() {
        Bundle args = new Bundle();
        args.putSerializable(AGR_PAGE_POSITION, 1);

        PhotoFragment p = new PhotoFragment();
        p.setArguments(args);
        return p;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo, container, false);
        return v;
    }

}
