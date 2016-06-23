package com.rowland.onboarderwithstepperindicator.view;


import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.rowland.onboarderwithstepperindicator.view.fragment.OnBoarder;

import java.util.ArrayList;
import java.util.List;


public class ColorsArrayBuilder {

    public static Integer[] getPageBackgroundColors(Context context, List<OnBoarder> onBoarderPages) {
        List<Integer> colorsList = new ArrayList<>();
        for (OnBoarder onBoarder : onBoarderPages) {
            colorsList.add(ContextCompat.getColor(context, onBoarder.getBackgroundColor()));
        }
        return colorsList.toArray(new Integer[onBoarderPages.size()]);
    }

}
