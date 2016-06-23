package com.rowland.onboarderwithstepperindicator.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.rowland.onboarderwithstepperindicator.view.fragment.OnBoarder;
import com.rowland.onboarderwithstepperindicator.view.fragment.OnBoarderFragment;

import java.util.ArrayList;
import java.util.List;

public class OnBoarderAdapter extends SmartFragmentStatePagerAdapter {

    List<OnBoarder> mFragmentsPages = new ArrayList<OnBoarder>();

    public OnBoarderAdapter(List<OnBoarder> fragmentPage, FragmentManager fm) {
        super(fm);
        this.mFragmentsPages = fragmentPage;
    }

    @Override
    public Fragment getItem(int position) {
        return OnBoarderFragment.newInstance(mFragmentsPages.get(position));
    }

    @Override
    public int getCount() {
        return mFragmentsPages.size();
    }
}
