package ca.com.androidcustomviews.customviews;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


public class PahtTest extends View {

    private static final String TAG = "DynamicHeartView";
    private static final int PATH_WIDTH = 2;
    private   Path path;
    Path src;
    // 起始点
    private static final int[] START_POINT = new int[]{
            300, 270
    };
    // 爱心下端点
    private static final int[] BOTTOM_POINT = new int[]{
            300, 400
    };
    // 左侧控制点
    private static final int[] LEFT_CONTROL_POINT = new int[]{
            450, 200
    };
    // 右侧控制点
    private static final int[] RIGHT_CONTROL_POINT = new int[]{
            150, 200
    };


    private PathMeasure mPathMeasure;
     private Paint mPaint;
    private Path mPath;
    private float[] mCurrentPosition = new float[2];
    private float[] posTan = new float[2];

    public PahtTest(Context context) {
        super(context);
        init();
    }

    public PahtTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(PATH_WIDTH);
        mPaint.setColor(Color.RED);


         path = new Path();
         src = new Path();

        path.addRect(200,200,300,300, Path.Direction.CW);
        src.addCircle(200,200,100, Path.Direction.CW);

        path.addPath(src,0,200);

        mPaint.setColor(Color.BLACK);           // 绘制合并后的路径
       mPathMeasure = new PathMeasure(path, true);
        Log.i(TAG, "measure length111 ==================================================== " + mPathMeasure.getLength());
        mCurrentPosition = new float[2];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.WHITE);
         canvas.drawPath(path, mPaint);
//
//        canvas.drawCircle(RIGHT_CONTROL_POINT[0], RIGHT_CONTROL_POINT[1], 5, mPaint);
//        canvas.drawCircle(LEFT_CONTROL_POINT[0], LEFT_CONTROL_POINT[1], 5, mPaint);

                          // <-- 注意 翻转y坐标轴


        // 绘制对应目标
        canvas.drawCircle(mCurrentPosition[0], mCurrentPosition[1], 10, mPaint);
    }

    // 开启路径动画
    public void startPathAnim(long duration) {
        // 0 － getLength()
        ValueAnimator valueAnimator;
        valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        Log.i(TAG, "measure length = " + mPathMeasure.getLength());
        valueAnimator.setDuration(duration);
        // 减速插值器
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                mPathMeasure.getPosTan(value, mCurrentPosition, posTan);
                Log.e(TAG, "onAnimationUpdate: ========>>>posTab[0] = " + posTan[0] + "-- psoTab[1] = " + posTan[1]);
                postInvalidate();
            }

        }



        );
        valueAnimator.start();
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {


                mPathMeasure.nextContour();
                // 0 － getLength()
                ValueAnimator valueAnimator;
                valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
                Log.i(TAG, "measure length ==================================================== " + mPathMeasure.getLength());
                valueAnimator.setDuration(5000);
                // 减速插值器
                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (Float) animation.getAnimatedValue();
                        // 获取当前点坐标封装到mCurrentPosition
                        mPathMeasure.getPosTan(value, mCurrentPosition, posTan);
                        Log.e(TAG, "onAnimationUpdate: ========>>>posTab[0] = " + posTan[0] + "-- psoTab[1] = " + posTan[1]);
                        postInvalidate();
                    }
                });
                valueAnimator.start();
            }



            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }
}

