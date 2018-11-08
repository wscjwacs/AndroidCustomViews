package ca.com.androidcustomviews.customviews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;

import ca.com.androidcustomviews.R;

/**
 * Created by hp on 2018/8/31.
 */

public class LoadingView extends View {
    //大圆的半径为整体宽度的1/4
    private float mRotateRadius;
    //小圆的半径
    private float mCircleRadius = 18;
    private float mAngle;//每一个圆间隔的角度
    private float mRotationAngle;//每一个圆旋转的角度；
    private Paint mCirclePaint;
    private int mCenterX, mCenterY;
    //小圆的颜色
    private int[] mColorArray;
    private boolean isStaring = false;

    private ValueAnimator mAnimator;

    private long mRotationDuration = 2000;

    private LoadingState loadingState;

    private float currBigCircleRadius;

    private float mWaveRadius;

    private Paint mBackGroundPaint;
  //整体的背景颜色
    private int mSplashBgColor =getResources().getColor(R.color.visi);
    //参数 保存了一些绘制状态，会被动态地改变
    private float mHoleRadius = 0F;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mColorArray = context.getResources().getIntArray(R.array.splash_circle_colors);
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mBackGroundPaint = new Paint();
        mBackGroundPaint.setColor(mSplashBgColor);
        mBackGroundPaint.setAntiAlias(true);
        mBackGroundPaint.setStyle(Paint.Style.STROKE);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
        mRotateRadius = w / 8;
        currBigCircleRadius = mRotateRadius;
        mWaveRadius =(float) Math.sqrt((w * w + h * h) / 2);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


//        // ValueAnimator rotateAnim = ValueAnimator.ofFloat(0, (float) Math.PI * 2);//这样每次都会new  一个对象
//        if (!isStaring) {
//            Log.e(TAG, "onDraw: =====>>>>"+isStaring );
//            mAnimator = ValueAnimator.ofFloat(0, (float) Math.PI * 2);
//            mAnimator.setInterpolator(new LinearInterpolator());
//            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    //得到某个时间点计算的结果---这个时间点当前大圆旋转的角度
//                    mRotationAngle = (float) animation.getAnimatedValue();
//                    postInvalidate();
//
//                }
//            });
//            mAnimator.setDuration(mRotationDuration);
//            //重复次数无限循环
//            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
//            mAnimator.start();
//            isStaring = true;
//
//        }
//        drawCircle(canvas);
        if (loadingState==null){
            loadingState = new RotationState();
        }
        loadingState.drawState(canvas);
    }

    private void drawCircle(Canvas canvas) {
        mAngle = (float) (Math.PI * 2 / mColorArray.length);
        for (int i = 0; i < mColorArray.length; i++) {
            double currAngle = mRotationAngle + i * mAngle;
            mCirclePaint.setColor(mColorArray[i]);
            float cx = mCenterX + (float) (mRotateRadius * Math.cos(currAngle));
            float cy = mCenterY + (float) (mRotateRadius * Math.sin(currAngle));
            canvas.drawCircle(cx, cy, mCircleRadius, mCirclePaint);
        }
    }



    //策略模式
    public abstract class LoadingState{
        public  abstract  void drawState(Canvas canvas);
    }

    //旋转状态
   public class RotationState extends LoadingState{
       public RotationState(){
           mAnimator = ValueAnimator.ofFloat(0, (float) Math.PI * 2);
           mAnimator.setInterpolator(new LinearInterpolator());
           mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
               @Override
               public void onAnimationUpdate(ValueAnimator animation) {
                   //得到某个时间点计算的结果---这个时间点当前大圆旋转的角度
                   mRotationAngle = (float) animation.getAnimatedValue();
                   postInvalidate();

               }
           });
           mAnimator.setDuration(mRotationDuration);
           //重复次数无限循环
           mAnimator.setRepeatCount(ValueAnimator.INFINITE);
           mAnimator.start();
           isStaring = true;
       }

       public void cancle(){
           mAnimator.cancel();
       }

        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawCircle(canvas);
        }
    }


    public void splashDisappear() {
        if (loadingState!=null&&loadingState instanceof RotationState){
            RotationState rotationState = (RotationState) loadingState;
            rotationState.cancle();
            post(new Runnable() {
                @Override
                public void run() {
                    loadingState = new MergingState();
                }
            });
        }

    }

    //聚合状态
    public class MergingState extends LoadingState {
        public MergingState(){
            mAnimator = ValueAnimator.ofFloat(currBigCircleRadius,0);
            mAnimator.setInterpolator(new AnticipateInterpolator(6f));
            mAnimator.setDuration(mRotationDuration);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRotateRadius = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loadingState = new ExpandState();
                }
            });
            mAnimator.start();
        }

        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawCircle(canvas);
        }
    }


    //水波纹扩散
    public class ExpandState extends LoadingState {
        public ExpandState(){
            mAnimator = ValueAnimator.ofFloat(0,mWaveRadius);
            mAnimator.setDuration(2000);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mHoleRadius = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            mAnimator.start();
        }

        @Override
        public void drawState(Canvas canvas) {

            drawBackground(canvas);
        }
    }

    private void drawBackground(Canvas canvas){
        if (mHoleRadius>0){
            float stokenWidth = mWaveRadius - mHoleRadius;
            mBackGroundPaint.setStrokeWidth(stokenWidth);
            canvas.drawCircle(mCenterX,mCenterY,mHoleRadius+stokenWidth/2,mBackGroundPaint);
        }else {
            canvas.drawColor(mSplashBgColor);
        }

    }
}
