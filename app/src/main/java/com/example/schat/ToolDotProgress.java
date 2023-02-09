package com.example.schat;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class ToolDotProgress extends View {
    // distance between neighbour dot centres
    private int mDotStep = 20;

    // actual dot radius
    private int mDotRadius = 5;

    // Bounced Dot Radius
    private int mBigDotRadius = 8;

    // to get identified in which position dot has to bounce
    private int mDotPosition;

    // specify how many dots you need in a progressbar
    private static final int MIN_COUNT = 1;
    private static final int DEF_COUNT = 10;
    private static final int MAX_COUNT = 100;
    private int mDotCount = DEF_COUNT;

    private static final int MIN_TIMEOUT = 100;
    private static final int DEF_TIMEOUT = 500;
    private static final int MAX_TIMEOUT = 3000;
    private int mTimeout = DEF_TIMEOUT;

    private int mDotColor = Color.parseColor("#fd583f");

    public ToolDotProgress(Context context) {
        super(context);
        initDotSize();
    }

    public ToolDotProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDotSize();
        applyAttrs(context, attrs);
    }

    public ToolDotProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDotSize();
        applyAttrs(context, attrs);
    }

    private void initDotSize() {
        final float scale = getResources().getDisplayMetrics().density;
        mDotStep = (int)(mDotStep * scale);
        mDotRadius = (int)(mDotRadius * scale);
        mBigDotRadius = (int)(mBigDotRadius * scale);
    }

    private void applyAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.ToolDotProgress, 0, 0);

        try {
            mDotColor = a.getColor(R.styleable.ToolDotProgress_color, mDotColor);
            mDotCount = a.getInteger(R.styleable.ToolDotProgress_count, mDotCount);
            mDotCount = Math.min(Math.max(mDotCount, MIN_COUNT), MAX_COUNT);
            mTimeout = a.getInteger(R.styleable.ToolDotProgress_timeout, mTimeout);
            mTimeout = Math.min(Math.max(mTimeout, MIN_TIMEOUT), MAX_TIMEOUT);
        } finally {
            a.recycle();
        }
    }

    //Method to draw your customized dot on the canvas
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShown()) {
            Paint paint = new Paint();
            paint.setColor(mDotColor);
            createDots(canvas, paint);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    private void createDots(Canvas canvas, Paint paint) {
        for (int i = 0; i < mDotCount; i++ ) {
            int radius = (i == mDotPosition) ? mBigDotRadius : mDotRadius;
            canvas.drawCircle(mDotStep / 2 + (i * mDotStep), mBigDotRadius, radius, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // MUST CALL THIS
        setMeasuredDimension(mDotStep * mDotCount, mBigDotRadius * 2);
    }

    private void startAnimation() {
        BounceAnimation bounceAnimation = new BounceAnimation();
        bounceAnimation.setDuration(mTimeout);
        bounceAnimation.setRepeatCount(Animation.INFINITE);
        bounceAnimation.setInterpolator(new LinearInterpolator());
        bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (++mDotPosition >= mDotCount) {
                    mDotPosition = 0;
                }
            }
        });
        startAnimation(bounceAnimation);
    }


    private class BounceAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            // call invalidate to redraw your view again
            invalidate();
        }
    }
}