package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/1/001.
 */

public class TreasureBottomFragment extends Fragment {
    private ImageView mImageViewFile;
    private ImageView mImageViewPhoto;
    private ImageView mImageViewPicture;
    private ImageView mImageViewPhone;
    private ImageView mImageViewMan;
    private List<ImageView> mViewList;
    private Map<ImageView, Integer> mViewIntegerMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_image, container, false);

        mViewList = new ArrayList<>();
        mViewIntegerMap = new HashMap<>();

        mImageViewMan = (ImageView) v.findViewById(R.id.primary_image_man);
        mImageViewPicture = (ImageView) v.findViewById(R.id.primary_image_picture);
        mImageViewPhoto = (ImageView) v.findViewById(R.id.primary_image_photo);
        mImageViewFile = (ImageView) v.findViewById(R.id.primary_image_file);
        mImageViewPhone = (ImageView) v.findViewById(R.id.primary_image_phone);
        clickImage(v, mImageViewMan, R.id.primary_image_man, R.drawable.ic_action_man_click);
        clickImage(v, mImageViewPicture, R.id.primary_image_picture, R.drawable.ic_action_picture_click);
        clickImage(v, mImageViewPhoto, R.id.primary_image_photo, R.drawable.ic_action_photo_click);
        clickImage(v, mImageViewFile, R.id.primary_image_file, R.drawable.ic_action_file_click);
        clickImage(v, mImageViewPhone, R.id.primary_image_phone, R.drawable.ic_action_phone_click);
        setViewList();
        setMap();
        return v;
    }

    private void setMap() {
        mViewIntegerMap.put(mImageViewMan, R.drawable.ic_action_man);
        mViewIntegerMap.put(mImageViewPicture, R.drawable.ic_action_picture);
        mViewIntegerMap.put(mImageViewPhoto, R.drawable.ic_action_photo);
        mViewIntegerMap.put(mImageViewFile, R.drawable.ic_action_file);
        mViewIntegerMap.put(mImageViewPhone, R.drawable.ic_action_phone);
    }

    private void setViewList() {
        mViewList.add(mImageViewMan);
        mViewList.add(mImageViewPicture);
        mViewList.add(mImageViewPhoto);
        mViewList.add(mImageViewFile);
        mViewList.add(mImageViewPhone);
    }

    private void clickImage(View v, ImageView image, int clickBefore, final int clicked) {
        final ImageView finalImage = image;
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ImageView i: mViewList) {
                    if (i.equals(finalImage)) {
                        i.setImageResource(clicked);
                    } else {
                        i.setImageResource(mViewIntegerMap.get(i));
                    }
                }
            }
        });
    }
}
