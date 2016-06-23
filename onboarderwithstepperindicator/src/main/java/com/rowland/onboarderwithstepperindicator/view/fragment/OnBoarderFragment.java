package com.rowland.onboarderwithstepperindicator.view.fragment;


import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rowland.onboarderwithstepperindicator.R;


/**
 *
 */
public class OnBoarderFragment extends ABaseFragment {

    // Class log identifier
    public final static String LOG_TAG = OnBoarderFragment.class.getSimpleName();

    private static final String ONBOARDER_FRAGMENT_TITLE = "onboarder_fragment_title";
    private static final String ONBOARDER_FRAGMENT_TITLE_RES_ID = "onboarder_fragment_title_res_id";
    private static final String ONBOARDER_FRAGMENT_TITLE_COLOR = "onboarder_fragment_title_color";
    private static final String ONBOARDER_FRAGMENT_TITLE_TEXT_SIZE = "onboarder_fragment_title_text_size";
    private static final String ONBOARDER_FRAGMENT_DESCRIPTION = "onboarder_fragment_description";
    private static final String ONBOARDER_FRAGMENT_DESCRIPTION_RES_ID = "onboarder_fragment_description_res_id";
    private static final String ONBOARDER_FRAGMENT_DESCRIPTION_COLOR = "onborader_fragment_description_color";
    private static final String ONBOARDER_FRAGMENT_DESCRIPTION_TEXT_SIZE = "onboarder_fragment_description_text_size";
    private static final String ONBOARDER_FRAGMENT_IMAGE_RES_ID = "onboarder_fragment_iamge_res_id";

    private String onboarderTitle;
    private String onboarderDescription;
    private float onboarderTitleTextSize;
    private float onboarderDescriptionTextSize;

    private ImageView ivOnboarderImage;
    private TextView tvOnboarderTitle;
    private TextView tvOnboarderDescription;

    @StringRes
    private int onboarderTitleResId;
    @ColorRes
    private int onboarderTitleColor;
    @StringRes
    private int onboarderDescriptionResId;
    @ColorRes
    private int onboarderDescriptionColor;
    @DrawableRes
    private int onboarderImageResId;


    public OnBoarderFragment() {

    }

    // Actual method to use to create new fragment instance externally
    public static OnBoarderFragment newInstance(OnBoarder onBoarder) {

        Bundle args = new Bundle();
        args.putString(ONBOARDER_FRAGMENT_TITLE, onBoarder.getTitle());
        args.putString(ONBOARDER_FRAGMENT_DESCRIPTION, onBoarder.getDescription());
        args.putInt(ONBOARDER_FRAGMENT_TITLE_RES_ID, onBoarder.getTitleResourceId());
        args.putInt(ONBOARDER_FRAGMENT_DESCRIPTION_RES_ID, onBoarder.getDescriptionResourceId());
        args.putInt(ONBOARDER_FRAGMENT_TITLE_COLOR, onBoarder.getTitleColor());
        args.putInt(ONBOARDER_FRAGMENT_DESCRIPTION_COLOR, onBoarder.getDescriptionColor());
        args.putInt(ONBOARDER_FRAGMENT_IMAGE_RES_ID, onBoarder.getImageResourceId());
        args.putFloat(ONBOARDER_FRAGMENT_TITLE_TEXT_SIZE, onBoarder.getTitleTextSize());
        args.putFloat(ONBOARDER_FRAGMENT_DESCRIPTION_TEXT_SIZE, onBoarder.getDescriptionTextSize());

        OnBoarderFragment fragmentInstance = new OnBoarderFragment();
        if (args != null) {
            fragmentInstance.setArguments(args);
        }
        return fragmentInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View fragmentView = inflater.inflate(R.layout.fragment_onboarder, container, false);

        ivOnboarderImage = (ImageView) fragmentView.findViewById(R.id.iv_onboarder_image);
        tvOnboarderTitle = (TextView) fragmentView.findViewById(R.id.tv_onboarder_title);
        tvOnboarderDescription = (TextView) fragmentView.findViewById(R.id.tv_onboarder_description);

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        onboarderTitle = bundle.getString(ONBOARDER_FRAGMENT_TITLE, null);
        onboarderTitleResId = bundle.getInt(ONBOARDER_FRAGMENT_TITLE_RES_ID, 0);
        onboarderTitleColor = bundle.getInt(ONBOARDER_FRAGMENT_TITLE_COLOR, 0);
        onboarderTitleTextSize = bundle.getFloat(ONBOARDER_FRAGMENT_TITLE_TEXT_SIZE, 0f);
        onboarderDescription = bundle.getString(ONBOARDER_FRAGMENT_DESCRIPTION, null);
        onboarderDescriptionResId = bundle.getInt(ONBOARDER_FRAGMENT_DESCRIPTION_RES_ID, 0);
        onboarderDescriptionColor = bundle.getInt(ONBOARDER_FRAGMENT_DESCRIPTION_COLOR, 0);
        onboarderDescriptionTextSize = bundle.getFloat(ONBOARDER_FRAGMENT_DESCRIPTION_TEXT_SIZE, 0f);
        onboarderImageResId = bundle.getInt(ONBOARDER_FRAGMENT_IMAGE_RES_ID, 0);


        if (onboarderTitle != null) {
            tvOnboarderTitle.setText(onboarderTitle);
        }

        if (onboarderTitleResId != 0) {
            tvOnboarderTitle.setText(getResources().getString(onboarderTitleResId));
        }

        if (onboarderDescription != null) {
            tvOnboarderDescription.setText(onboarderDescription);
        }

        if (onboarderDescriptionResId != 0) {
            tvOnboarderDescription.setText(getResources().getString(onboarderDescriptionResId));
        }

        if (onboarderTitleColor != 0) {
            tvOnboarderTitle.setTextColor(ContextCompat.getColor(getActivity(), onboarderTitleColor));
        }

        if (onboarderDescriptionColor != 0) {
            tvOnboarderDescription.setTextColor(ContextCompat.getColor(getActivity(), onboarderDescriptionColor));
        }

        if (onboarderImageResId != 0) {
            ivOnboarderImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), onboarderImageResId));
        }

        if (onboarderTitleTextSize != 0f) {
            tvOnboarderTitle.setTextSize(onboarderTitleTextSize);
        }

        if (onboarderDescriptionTextSize != 0f) {
            tvOnboarderDescription.setTextSize(onboarderDescriptionTextSize);
        }
    }
}
