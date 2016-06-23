package com.rowland.onboarderwithstepperindicator.view.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rowland.onboarderwithstepperindicator.R;

/**
 * Base {@link Activity} class for every Activity in this application.
 */
public abstract class ABaseActivity extends AppCompatActivity {

    // Class Variables
    private final String LOG_TAG = ABaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId     The container view to where add the fragment.
     * @param fragment            The fragment to be added.
     * @param isAnimateTransition The boolean on whether to animate fragment transition.
     */
    protected void addFragment(int containerViewId, Fragment fragment, boolean isAnimateTransition) {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (isAnimateTransition) {
            animateFragmentTransition(ft);
        }
        ft.add(containerViewId, fragment);
        ft.commit();
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId     The container view to where add the fragment.
     * @param fragment            The fragment to be added.
     * @param isAddToBackStack    The boolean on whether to add to backstack.
     * @param isAnimateTransition The boolean on whether to animate fragment transition.
     */
    protected void replaceFragment(int containerViewId, Fragment fragment, boolean isAddToBackStack, boolean isAnimateTransition) {
        // invalidateOptionsMenu();
        String backStateName = fragment.getClass().getName();
        FragmentManager fm = this.getSupportFragmentManager();
        boolean fragmentPopped = fm.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && getSupportFragmentManager().findFragmentByTag(backStateName) == null) {
            FragmentTransaction ft = fm.beginTransaction();
            if (isAnimateTransition) {
                animateFragmentTransition(ft);
            }
            ft.replace(containerViewId, fragment, backStateName);
            if (isAddToBackStack) {
                ft.addToBackStack(backStateName);
            }
            ft.commit();
        }
    }


    /**
     * Adds a {@link FragmentTransaction} to this activity's layout.
     *
     * @param ft The FragmentTransaction
     */
    private void animateFragmentTransition(FragmentTransaction ft) {
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.slide_in_to_left, R.anim.slide_out_to_right);
    }

    /*
   * Smoothen the activity transition
   * */
    protected void animateActivityTransition() {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param isToolbarTransparent whether to set statusbar transparent.
     */
    public void setStatusbarTransparent(boolean isToolbarTransparent) {
        if (isToolbarTransparent) {
            // Check for minimum api as Lollipop
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Set up  the systemUi flags
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                // Set the status bar tobe transparent
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    // Derived methods have no direct access to this class - for activities that dont own a Toolbar
    public void setToolbar(Toolbar mToolbar, boolean isShowHomeUp, boolean isShowTitle, int iconResource) {
        // Does the inc_toolbar exist?
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            // Should we set up home-up button navigation?
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShowHomeUp);
            // Should we display the title on the inc_toolbar?
            getSupportActionBar().setDisplayShowTitleEnabled(isShowTitle);
            // Should we set logo to appear in inc_toolbar?
            if (!(iconResource == 0)) {
                getSupportActionBar().setIcon(iconResource);
                //this.mToolbar.setLogo(R.drawable.ic_logo_48px);
            }
        }
    }
}
