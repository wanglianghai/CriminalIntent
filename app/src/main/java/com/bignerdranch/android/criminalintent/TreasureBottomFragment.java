package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    private Map<Integer, ImageView> mMapImageView;
    private Map<ImageView, Integer> mViewClicked;
    private Map<ImageView, Integer> mViewClick;
    private Map<ImageView, Integer> mPosition;
    private CallBacksB mCallBacksB;

    public interface CallBacksB{
        void update(int i);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBacksB = (CallBacksB) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_image, container, false);

        mViewList = new ArrayList<>();
        mMapImageView = new HashMap<>();
        mViewClick = new HashMap<>();
        mViewClicked = new HashMap<>();
        mPosition = new HashMap<>();

        mImageViewMan = (ImageView) v.findViewById(R.id.primary_image_man);
        mImageViewPicture = (ImageView) v.findViewById(R.id.primary_image_picture);
        mImageViewPhoto = (ImageView) v.findViewById(R.id.primary_image_photo);
        mImageViewFile = (ImageView) v.findViewById(R.id.primary_image_file);
        mImageViewPhone = (ImageView) v.findViewById(R.id.primary_image_phone);
        clickImage(v, mImageViewMan, R.drawable.ic_action_man_click);
        clickImage(v, mImageViewPicture, R.drawable.ic_action_picture_click);
        clickImage(v, mImageViewPhoto, R.drawable.ic_action_photo_click);
        clickImage(v, mImageViewFile, R.drawable.ic_action_file_click);
        clickImage(v, mImageViewPhone, R.drawable.ic_action_phone_click);
        mImageViewFile.setImageResource(R.drawable.ic_action_file_click);
        setViewList();
        setMapClick();
        setMapClicked();
        setMapImageView();
        setMapPosition();
        return v;
    }

    private void setMapPosition() {
        mPosition.put(mImageViewFile, 0);
        mPosition.put(mImageViewPhoto, 1);
        mPosition.put(mImageViewPhone, 2);
        mPosition.put(mImageViewPicture, 3);
        mPosition.put(mImageViewMan, 4);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBacksB = null;
    }

    private void setMapImageView() {
        mMapImageView.put(0, mImageViewFile);
        mMapImageView.put(1, mImageViewPhoto);
        mMapImageView.put(2, mImageViewPhone);
        mMapImageView.put(3, mImageViewPicture);
        mMapImageView.put(4, mImageViewMan);
    }

    private void setMapClicked() {
        mViewClicked.put(mImageViewMan, R.drawable.ic_action_man_click);
        mViewClicked.put(mImageViewPicture, R.drawable.ic_action_picture_click);
        mViewClicked.put(mImageViewPhoto, R.drawable.ic_action_photo_click);
        mViewClicked.put(mImageViewFile, R.drawable.ic_action_file_click);
        mViewClicked.put(mImageViewPhone, R.drawable.ic_action_phone_click);
    }

    private void setMapClick() {
        mViewClick.put(mImageViewMan, R.drawable.ic_action_man);
        mViewClick.put(mImageViewPicture, R.drawable.ic_action_picture);
        mViewClick.put(mImageViewPhoto,R.drawable.ic_action_photo);
        mViewClick.put(mImageViewFile,R.drawable.ic_action_file);
        mViewClick.put(mImageViewPhone,R.drawable.ic_action_phone);
    }

    private void setViewList() {
        mViewList.add(0, mImageViewMan);
        mViewList.add(1, mImageViewPicture);
        mViewList.add(2, mImageViewPhoto);
        mViewList.add(3, mImageViewFile);
        mViewList.add(4, mImageViewPhone);
    }

    private void clickImage(View v, ImageView image, final int clicked) {
        final ImageView finalImage = image;
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelect(finalImage, clicked);
            }
        });
    }

    private void imageSelect(ImageView finalImage, int clicked) {
        for (ImageView i: mViewList) {
            if (i.equals(finalImage)) {
                i.setImageResource(clicked);
                mCallBacksB.update(mPosition.get(i));
            } else {
                i.setImageResource(mViewClick.get(i));
            }
        }
    }

  /*  private void imageSelect(ImageView finalImage, int clicked) {
        for (int i = 0; i < 5; i++) {
            if (mViewList.get(i).equals(finalImage)) {
                mViewList.get(i).setImageResource(clicked);
            } else {
                mViewList.get(i).setImageResource(mViewClick.get(i));
            }
        }
    }
*/

    public void updateUI(int position){
        for (int i = 0; i < 5; i++) {
            if (i == position) {
                ImageView image = mMapImageView.get(i);
                imageSelect(image, mViewClicked.get(image));
            }
        }
    }
}
