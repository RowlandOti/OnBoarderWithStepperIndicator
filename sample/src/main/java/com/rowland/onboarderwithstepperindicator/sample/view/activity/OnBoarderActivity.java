package com.rowland.onboarderwithstepperindicator.sample.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.rowland.onboarderwithstepperindicator.sample.R;
import com.rowland.onboarderwithstepperindicator.view.activity.AOnBoarderWithStepperIndicatorActivity;
import com.rowland.onboarderwithstepperindicator.view.fragment.OnBoarder;

import java.util.ArrayList;
import java.util.List;


public class OnBoarderActivity extends AOnBoarderWithStepperIndicatorActivity {

    // Class log identifier
    public final static String LOG_TAG = OnBoarderActivity.class.getSimpleName();

    private List<OnBoarder> onBoarderPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusbarTransparent(true);
        onBoarderPages = new ArrayList<>();

        OnBoarder intro1 = new OnBoarder("Welcome", "Your Two Steps Away from Making Farming Easier", R.drawable.herdy_logo_125px);
        intro1.setBackgroundColor(R.color.app_color_white);
        intro1.setDescriptionColor(R.color.app_color_secondary_text_grey);
        intro1.setTitleColor(R.color.app_color_accent_teal);

        onBoarderPages.add(intro1);
        onBoarderPages.add(intro1);
        onBoarderPages.add(intro1);
        onBoarderPages.add(intro1);
        setOnboardPagesReady(onBoarderPages);
        shouldDarkenButtonsLayout(true);
    }

    @Override
    public void onSkipButtonPressed() {
        super.onSkipButtonPressed();
        loadActivity();
    }

    @Override
    public void onFinishButtonPressed() {
        loadActivity();
    }

    private void loadActivity() {
        Toast.makeText(this, "Load Next Activity", Toast.LENGTH_SHORT).show();
    }
}
