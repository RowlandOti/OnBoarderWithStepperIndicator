package com.rowland.onboarderwithstepperindicator.view;

public class StepperIndicatorManager {

    private StepperIndicator mStepperIndicatorView;

    public StepperIndicatorManager(StepperIndicator mStepperIndicatorView) {
        this.mStepperIndicatorView = mStepperIndicatorView;
    }

    public void setStepperLineColor(int lineColor) {
        mStepperIndicatorView.setLineColor(lineColor);
    }

    public void setStepperIndicatorColor(int indicatorColor) {
        mStepperIndicatorView.setIndicatorColor(indicatorColor);
    }

    public void setStepperCircleColor(int circleColor) {
        mStepperIndicatorView.setCircleColor(circleColor);
    }

    public void setStepperCircleStrokeWidth(float circleStrokeWidth) {
        mStepperIndicatorView.setCircleStrokeWidth(circleStrokeWidth);
    }

    public void setStepperLineDoneColor(int lineDoneColor) {
        mStepperIndicatorView.setLineDoneColor(lineDoneColor);
    }

    public void setStepperLineStrokeWidth(float lineStrokeWidth) {
        mStepperIndicatorView.setLineStrokeWidth(lineStrokeWidth);
    }

    public void setStepperCircleRadius(float circleRadius) {
        mStepperIndicatorView.setCircleRadius(circleRadius);
    }

    public void setStepperIndicatorRadius(float indicatorRadius) {
        mStepperIndicatorView.setIndicatorRadius(indicatorRadius);
    }

    public void setStepperLineMargin(float lineMargin) {
        mStepperIndicatorView.setLineMargin(lineMargin);
    }

    public void setStepperAnimationDuration(int animationDuration) {
        mStepperIndicatorView.setAnimationDuration(animationDuration);
    }
}
