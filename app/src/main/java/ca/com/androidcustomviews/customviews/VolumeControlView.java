package ca.com.androidcustomviews.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import ca.com.androidcustomviews.R;

/**
 * 分析：1.背景圆弧的颜色
 * 2.进度圆弧的颜色
 * 3.圆弧之间的间隙
 * 4.圆弧的宽度
 * 5.画的条数
 * 6.中间图片
 */

public class VolumeControlView extends View {


    private int mBgArcColor;//背景 圆弧的颜色
    private int mProgressColor;//当前进度圆弧的颜色
    private int mSplit; // 圆弧的间隙
    private int mCount;//
    private int mCircleWidth;//圆弧的宽度
    private Bitmap mCenterImage;//中间的图片
    private Paint mPaint;
    private int currProgress;//当前的进度


    public VolumeControlView(Context context) {
        this(context, null);
    }

    public VolumeControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VolumeControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VolumeControlView);
        mBgArcColor = typedArray.getColor(R.styleable.VolumeControlView_bgArcColor, mBgArcColor);
        mProgressColor = typedArray.getColor(R.styleable.VolumeControlView_progressColor, mProgressColor);
        mSplit = typedArray.getInt(R.styleable.VolumeControlView_split, mSplit);
        mCount = typedArray.getInt(R.styleable.VolumeControlView_count, mCount);
        mCircleWidth = typedArray.getInt(R.styleable.VolumeControlView_arcWidth, mCircleWidth);
        mCenterImage = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(R.styleable.CustomVolumControlBar_centerPic, 0));
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setColor(mBgArcColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mWidth = 0;
        ;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int desireWidth = getPaddingLeft() + getPaddingRight() + mCenterImage.getWidth();// 由图片和文字决定的宽
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                mWidth = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                mWidth = desireWidth;
                break;
            case MeasureSpec.UNSPECIFIED:

                break;
        }
        setMeasuredDimension(mWidth, mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int circleCenter = getWidth() / 2;
        int radious = circleCenter - mCircleWidth / 2;
        int realRadious = radious - mCircleWidth / 2;
        drawOval(canvas, circleCenter, radious);


    }

    private void drawOval(Canvas canvas, int circleCenter, int radious) {


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
        }
        return super.onTouchEvent(event);
    }


}
