package com.rowland.onboarderwithstepperindicator.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.rowland.onboarderwithstepperindicator.R;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unused")
public class StepperIndicator extends View implements ViewPager.OnPageChangeListener {

    private static final int DEFAULT_ANIMATION_DURATION = 250;
    private static final float EXPAND_MARK = 1.3f;

    private Paint circlePaint;
    private Paint linePaint, lineDonePaint, lineDoneAnimatedPaint;
    private Paint indicatorPaint;

    private List<Path> linePathList = new ArrayList<>();
    private float animProgress;
    private float animIndicatorRadius;
    private float animCheckRadius;
    private float lineLength;
    private float checkRadius;

    // Modifiable at runtime using setters
    private int mLineDoneColor;
    private float mCircleStrokeWidth;
    private int mCircleColor;
    private int mIndicatorColor;
    private int mLineColor;
    private float mLineStrokeWidth;
    private float mCircleRadius;
    private float mIndicatorRadius;
    private float mLineMargin;
    private int mAnimationDuration;

    private int stepCount;
    private int currentStep;

    private float[] indicators;

    private ViewPager pager;
    private Bitmap doneIcon;

    private AnimatorSet animatorSet;
    private ObjectAnimator lineAnimator, indicatorAnimator, checkAnimator;
    private int previousStep;

    public StepperIndicator(Context context) {
        this(context, null);
    }

    public StepperIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepperIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StepperIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private static PathEffect createPathEffect(float pathLength, float phase, float offset) {
        return new DashPathEffect(new float[]{pathLength, pathLength},
                Math.max(phase * pathLength, offset));
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        final Resources resources = getResources();

        // Default value
        int defaultCircleColor = ContextCompat.getColor(context, R.color.stpi_default_circle_color);
        float defaultCircleRadius = resources.getDimension(R.dimen.stpi_default_circle_radius);
        float defaultCircleStrokeWidth = resources.getDimension(R.dimen.stpi_default_circle_stroke_width);

        int defaultIndicatorColor = ContextCompat.getColor(context, R.color.stpi_default_indicator_color);
        float defaultIndicatorRadius = resources.getDimension(R.dimen.stpi_default_indicator_radius);

        float defaultLineStrokeWidth = resources.getDimension(R.dimen.stpi_default_line_stroke_width);
        float defaultLineMargin = resources.getDimension(R.dimen.stpi_default_line_margin);
        int defaultLineColor = ContextCompat.getColor(context, R.color.stpi_default_line_color);
        int defaultLineDoneColor = ContextCompat.getColor(context, R.color.stpi_default_line_done_color);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StepperIndicator, defStyleAttr, 0);

        circlePaint = new Paint();
        mCircleStrokeWidth = a.getDimension(R.styleable.StepperIndicator_stpi_circleStrokeWidth, defaultCircleStrokeWidth);
        circlePaint.setStrokeWidth(mCircleStrokeWidth);
        circlePaint.setStyle(Paint.Style.STROKE);
        mCircleColor = a.getColor(R.styleable.StepperIndicator_stpi_circleColor, defaultCircleColor);
        circlePaint.setColor(mCircleColor);
        circlePaint.setAntiAlias(true);

        indicatorPaint = new Paint(circlePaint);
        indicatorPaint.setStyle(Paint.Style.FILL);
        mIndicatorColor = a.getColor(R.styleable.StepperIndicator_stpi_indicatorColor, defaultIndicatorColor);
        indicatorPaint.setColor(mIndicatorColor);
        indicatorPaint.setAntiAlias(true);

        linePaint = new Paint();
        mLineStrokeWidth = a.getDimension(R.styleable.StepperIndicator_stpi_lineStrokeWidth, defaultLineStrokeWidth);
        linePaint.setStrokeWidth(mLineStrokeWidth);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStyle(Paint.Style.STROKE);
        mLineColor = a.getColor(R.styleable.StepperIndicator_stpi_lineColor, defaultLineColor);
        linePaint.setColor(mLineColor);
        linePaint.setAntiAlias(true);

        lineDonePaint = new Paint(linePaint);
        mLineDoneColor = a.getColor(R.styleable.StepperIndicator_stpi_lineDoneColor, defaultLineDoneColor);
        lineDonePaint.setColor(mLineDoneColor);

        lineDoneAnimatedPaint = new Paint(lineDonePaint);

        mCircleRadius = a.getDimension(R.styleable.StepperIndicator_stpi_circleRadius, defaultCircleRadius);
        checkRadius = mCircleRadius + circlePaint.getStrokeWidth() / 2f;
        mIndicatorRadius = a.getDimension(R.styleable.StepperIndicator_stpi_indicatorRadius, defaultIndicatorRadius);
        animIndicatorRadius = mIndicatorRadius;
        animCheckRadius = checkRadius;
        mLineMargin = a.getDimension(R.styleable.StepperIndicator_stpi_lineMargin, defaultLineMargin);

        setStepCount(a.getInteger(R.styleable.StepperIndicator_stpi_stepCount, 2));
        ;
        mAnimationDuration = a.getInteger(R.styleable.StepperIndicator_stpi_animDuration, DEFAULT_ANIMATION_DURATION);

        a.recycle();

        doneIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_done_white_18dp);

        if (isInEditMode())
            currentStep = Math.max((int) Math.ceil(stepCount / 2f), 1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        compute();
    }

    private void compute() {
        indicators = new float[stepCount];
        linePathList.clear();

        float startX = mCircleRadius * EXPAND_MARK + circlePaint.getStrokeWidth() / 2f;

        // Compute position of indicators and line length
        float divider = (getMeasuredWidth() - startX * 2f) / (stepCount - 1);
        lineLength = divider - (mCircleRadius * 2f + circlePaint.getStrokeWidth()) - (mLineMargin * 2);

        // Compute position of circles and lines once
        for (int i = 0; i < indicators.length; i++)
            indicators[i] = startX + divider * i;
        for (int i = 0; i < indicators.length - 1; i++) {
            float position = ((indicators[i] + indicators[i + 1]) / 2) - lineLength / 2;
            final Path linePath = new Path();
            linePath.moveTo(position, getMeasuredHeight() / 2);
            linePath.lineTo(position + lineLength, getMeasuredHeight() / 2);
            linePathList.add(linePath);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onDraw(Canvas canvas) {
        float centerY = getMeasuredHeight() / 2f;

        // Currently Drawing animation from step n-1 to n, or back from n+1 to n
        boolean inAnimation = false;
        boolean inLineAnimation = false;
        boolean inIndicatorAnimation = false;
        boolean inCheckAnimation = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            inAnimation = animatorSet != null && animatorSet.isRunning();
            inLineAnimation = lineAnimator != null && lineAnimator.isRunning();
            inIndicatorAnimation = indicatorAnimator != null && indicatorAnimator.isRunning();
            inCheckAnimation = checkAnimator != null && checkAnimator.isRunning();
        }

        boolean drawToNext = previousStep == currentStep - 1;
        boolean drawFromNext = previousStep == currentStep + 1;

        for (int i = 0; i < indicators.length; i++) {
            final float indicator = indicators[i];
            boolean drawCheck = i < currentStep || (drawFromNext && i == currentStep);

            // Draw back circle
            canvas.drawCircle(indicator, centerY, mCircleRadius, circlePaint);

            // If current step, or coming back from next step and still animating
            if ((i == currentStep && !drawFromNext) || (i == previousStep && drawFromNext && inAnimation)) {
                // Draw animated indicator
                canvas.drawCircle(indicator, centerY, animIndicatorRadius, indicatorPaint);
            }

            // Draw check mark
            if (drawCheck) {
                float radius = checkRadius;
                if ((i == previousStep && drawToNext)
                        || (i == currentStep && drawFromNext))
                    radius = animCheckRadius;
                canvas.drawCircle(indicator, centerY, radius, indicatorPaint);
                if (!isInEditMode()) {
                    if ((i != previousStep && i != currentStep) || (!inCheckAnimation && !(i == currentStep && !inAnimation)))
                        canvas.drawBitmap(doneIcon, indicator - (doneIcon.getWidth() / 2), centerY - (doneIcon.getHeight() / 2), null);
                }
            }

            // Draw lines
            if (i < linePathList.size()) {
                if (i >= currentStep) {
                    canvas.drawPath(linePathList.get(i), linePaint);
                    if (i == currentStep && drawFromNext && (inLineAnimation || inIndicatorAnimation)) // Coming back from n+1
                        canvas.drawPath(linePathList.get(i), lineDoneAnimatedPaint);
                } else {
                    if (i == currentStep - 1 && drawToNext && inLineAnimation) {
                        // Going to n+1
                        canvas.drawPath(linePathList.get(i), linePaint);
                        canvas.drawPath(linePathList.get(i), lineDoneAnimatedPaint);
                    } else
                        canvas.drawPath(linePathList.get(i), lineDonePaint);
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredHeight = (int) Math.ceil((mCircleRadius * EXPAND_MARK * 2) + circlePaint.getStrokeWidth());

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = widthMode == MeasureSpec.EXACTLY ? widthSize : getSuggestedMinimumWidth();
        int height = heightMode == MeasureSpec.EXACTLY ? heightSize : desiredHeight;

        setMeasuredDimension(width, height);
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        if (stepCount < 2)
            throw new IllegalArgumentException("stepCount must be >= 2");
        this.stepCount = stepCount;
        currentStep = 0;
        compute();
        invalidate();
    }

    public int getCurrentStep() {
        return currentStep;
    }

    /**
     * Sets the current step
     *
     * @param currentStep a value between 0 (inclusive) and stepCount (inclusive)
     */
    @UiThread
    public void setCurrentStep(int currentStep) {
        if (currentStep < 0 || currentStep > stepCount)
            throw new IllegalArgumentException("Invalid step value " + currentStep);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (animatorSet != null)
                animatorSet.cancel();
            animatorSet = null;
            lineAnimator = null;
            indicatorAnimator = null;

            if (currentStep == this.currentStep + 1) {
                previousStep = this.currentStep;
                animatorSet = new AnimatorSet();

                // First, draw line to new
                lineAnimator = ObjectAnimator.ofFloat(StepperIndicator.this, "animProgress", 1.0f, 0.0f);

                // Same time, pop check mark
                checkAnimator = ObjectAnimator
                        .ofFloat(StepperIndicator.this, "animCheckRadius", mIndicatorRadius, checkRadius * EXPAND_MARK, checkRadius);

                // Finally, pop current step indicator
                animIndicatorRadius = 0;
                indicatorAnimator = ObjectAnimator
                        .ofFloat(StepperIndicator.this, "animIndicatorRadius", 0f, mIndicatorRadius * 1.4f, mIndicatorRadius);

                animatorSet.play(lineAnimator).with(checkAnimator).before(indicatorAnimator);
            } else if (currentStep == this.currentStep - 1) {
                previousStep = this.currentStep;
                animatorSet = new AnimatorSet();

                // First, pop out current step indicator
                indicatorAnimator = ObjectAnimator.ofFloat(StepperIndicator.this, "animIndicatorRadius", mIndicatorRadius, 0f);

                // Then delete line
                animProgress = 1.0f;
                lineDoneAnimatedPaint.setPathEffect(null);
                lineAnimator = ObjectAnimator.ofFloat(StepperIndicator.this, "animProgress", 0.0f, 1.0f);

                // Finally, pop out check mark to display step indicator
                animCheckRadius = checkRadius;
                checkAnimator = ObjectAnimator.ofFloat(StepperIndicator.this, "animCheckRadius", checkRadius, mIndicatorRadius);

                animatorSet.playSequentially(indicatorAnimator, lineAnimator, checkAnimator);
            }

            if (animatorSet != null) {
                lineAnimator.setDuration(Math.min(500, mAnimationDuration));
                lineAnimator.setInterpolator(new DecelerateInterpolator());
                indicatorAnimator.setDuration(lineAnimator.getDuration() / 2);
                checkAnimator.setDuration(lineAnimator.getDuration() / 2);

                animatorSet.start();
            }
        }

        this.currentStep = currentStep;
        invalidate();
    }

    /**
     * DO NOT CALL, used by animation to draw line
     */
    public void setAnimProgress(float animProgress) {
        this.animProgress = animProgress;
        lineDoneAnimatedPaint.setPathEffect(createPathEffect(lineLength, animProgress, 0.0f));
        invalidate();
    }

    public void setAnimIndicatorRadius(float animIndicatorRadius) {
        this.animIndicatorRadius = animIndicatorRadius;
        invalidate();
    }

    public void setAnimCheckRadius(float animCheckRadius) {
        this.animCheckRadius = animCheckRadius;
        invalidate();
    }

    public void setViewPager(ViewPager pager) {
        if (pager.getAdapter() == null)
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        setViewPager(pager, pager.getAdapter().getCount());
    }

    /**
     * Sets the pager associated with this indicator
     *
     * @param pager        viewpager to attach
     * @param keepLastPage true if should not create an indicator for the last page, to use it as the final page
     */
    public void setViewPager(ViewPager pager, boolean keepLastPage) {
        if (pager.getAdapter() == null)
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        setViewPager(pager, pager.getAdapter().getCount() - (keepLastPage ? 1 : 0));
    }

    /**
     * Sets the pager associated with this indicator
     *
     * @param pager     viewpager to attach
     * @param stepCount the real page count to display (use this if you are using looped viewpager to indicate the real number of pages)
     */
    public void setViewPager(ViewPager pager, int stepCount) {
        if (this.pager == pager)
            return;
        if (this.pager != null)
            pager.removeOnPageChangeListener(this);
        if (pager.getAdapter() == null)
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        this.pager = pager;
        this.stepCount = stepCount;
        currentStep = 0;
        pager.addOnPageChangeListener(this);
        invalidate();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentStep(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentStep = savedState.currentStep;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentStep = currentStep;
        return savedState;
    }

    static class SavedState extends BaseSavedState {

        @SuppressWarnings("UnusedDeclaration")
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        private int currentStep;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentStep = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentStep);
        }
    }

    public void setLineColor(int lineColor) {
        this.mLineColor = lineColor;
        linePaint.setColor(mLineColor);
        update();
    }

    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
        indicatorPaint.setColor(mIndicatorColor);
        update();
    }

    public void setCircleColor(int circleColor) {
        this.mCircleColor = circleColor;
        circlePaint.setColor(mCircleColor);
        update();
    }

    public void setCircleStrokeWidth(float circleStrokeWidth) {
        this.mCircleStrokeWidth = circleStrokeWidth;
        update();
    }

    public void setLineDoneColor(int lineDoneColor) {
        this.mLineDoneColor = lineDoneColor;
        lineDonePaint.setColor(mLineDoneColor);
        update();
    }

    public void setLineStrokeWidth(float lineStrokeWidth) {
        this.mLineStrokeWidth = lineStrokeWidth;
        update();
    }

    public void setCircleRadius(float circleRadius) {
        this.mCircleRadius = circleRadius;
        update();
    }


    public void setIndicatorRadius(float indicatorRadius) {
        this.mIndicatorRadius = indicatorRadius;
        update();
    }

    public void setLineMargin(float lineMargin) {
        this.mLineMargin = lineMargin;
        update();
    }

    public void setAnimationDuration(int animationDuration) {
        this.mAnimationDuration = animationDuration;
        update();
    }

    private void update() {
        invalidate();
        requestLayout();
    }
}

