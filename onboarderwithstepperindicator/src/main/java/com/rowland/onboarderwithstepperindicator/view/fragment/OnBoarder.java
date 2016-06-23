package com.rowland.onboarderwithstepperindicator.view.fragment;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.rowland.onboarderwithstepperindicator.R;


public class OnBoarder {

    public String title;
    public String description;
    public Drawable imageResource;
    public float titleTextSize;
    public float descriptionTextSize;

    @StringRes
    public int titleResourceId;
    @StringRes
    public int descriptionResourceId;
    @DrawableRes
    public int imageResourceId;
    @ColorRes
    public int titleColor;
    @ColorRes
    public int descriptionColor;
    @ColorRes
    public int backgroundColor;


    public OnBoarder(String title, String description) {
        this.title = title;
        this.description = description;
        this.backgroundColor = R.color.app_color_accent_orange;
    }

    public OnBoarder(String title, String description, int imageResourceId) {
        this.title = title;
        this.description = description;
        this.imageResourceId = imageResourceId;
        this.backgroundColor = R.color.app_color_black_transparent;
    }

    public OnBoarder(String title, String description, Drawable imageResource) {
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
        this.backgroundColor = R.color.app_color_accent_orange;
    }

    public OnBoarder(int title, int description) {
        this.titleResourceId = title;
        this.descriptionResourceId = description;
        this.backgroundColor = R.color.app_color_accent_orange;
    }

    public OnBoarder(int title, int description, int imageResourceId) {
        this.titleResourceId = title;
        this.descriptionResourceId = description;
        this.imageResourceId = imageResourceId;
        this.backgroundColor = R.color.app_color_accent_orange;
    }

    public OnBoarder(int title, int description, Drawable imageResource) {
        this.titleResourceId = title;
        this.descriptionResourceId = description;
        this.imageResource = imageResource;
        this.backgroundColor = R.color.app_color_accent_orange;
    }

    public String getTitle() {
        return title;
    }

    public int getTitleResourceId() {
        return titleResourceId;
    }

    public String getDescription() {
        return description;
    }

    public int getDescriptionResourceId() {
        return descriptionResourceId;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int color) {
        this.titleColor = color;
    }

    public int getDescriptionColor() {
        return descriptionColor;
    }

    public void setDescriptionColor(int color) {
        this.descriptionColor = color;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public float getTitleTextSize() {
        return titleTextSize;
    }

    public void setTitleTextSize(float titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public float getDescriptionTextSize() {
        return descriptionTextSize;
    }

    public void setDescriptionTextSize(float descriptionTextSize) {
        this.descriptionTextSize = descriptionTextSize;
    }


}
