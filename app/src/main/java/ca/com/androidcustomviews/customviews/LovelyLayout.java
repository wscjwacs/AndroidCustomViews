package ca.com.androidcustomviews.customviews;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

import ca.com.androidcustomviews.R;

/**
 * Created by hp on 2018/7/17.
 */

public class LovelyLayout extends RelativeLayout {
    private Drawable redDrawable, yellowDrawable, blueDrawable;
    private Drawable[] drawables;
    private int mDrawableWidth,mDrawableHeight;
    private LayoutParams params;
    private int mWidth,mHeight;
    private Random mRandom = new Random();
    private Interpolator[] interpolators;

    public LovelyLayout(Context context) {
        this(context, null);
    }

    public LovelyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }



    public LovelyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        initDrawables();
        params = new LayoutParams(mDrawableWidth,mDrawableHeight);
        params.addRule(CENTER_HORIZONTAL,TRUE);
        params.addRule(ALIGN_PARENT_BOTTOM,TRUE);
        interpolators = new Interpolator[]{
                new LinearInterpolator(),// 线性
                new AccelerateDecelerateInterpolator(),// 先加速后减速
                new AccelerateInterpolator(),// 加速
                new DecelerateInterpolator()// 减速

        };

    }

    private void initDrawables() {
        redDrawable = getResources().getDrawable(R.drawable.pic1);
        yellowDrawable = getResources().getDrawable(R.drawable.pic2);
        blueDrawable = getResources().getDrawable(R.drawable.pic3);
        drawables = new Drawable[3];
        drawables[0] = redDrawable;
        drawables[1] = yellowDrawable;
        drawables[2] = blueDrawable;

        //三个图片大小一样
        mDrawableWidth  = redDrawable.getIntrinsicWidth();
        mDrawableHeight = redDrawable.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth  = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    public void addLove(){
        ImageView love = new ImageView(getContext());
        love.setImageDrawable(drawables[mRandom.nextInt(drawables.length)]);
        addView(love,params);
        AnimatorSet finalAinmtor = getAnimator(love);
        finalAinmtor.start();
    }

    private AnimatorSet getAnimator(ImageView love) {
        AnimatorSet animatorSet = new AnimatorSet();
        // 1.alpha动画
        ObjectAnimator alpha = ObjectAnimator.ofFloat(love, "alpha", 0.3f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(love, "scaleX", 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(love, "scaleY", 0.2f, 1f);

        animatorSet.setDuration(200);
        animatorSet.playTogether(alpha,scaleX,scaleY);
        animatorSet.setTarget(love);

        AnimatorSet bezierAnimatorSet = new AnimatorSet();
        ValueAnimator bezierValueAnimator = getBezierValueAnimator(love);//getBeziValueAnimator得到贝赛尔曲线轨迹位移动画
        bezierAnimatorSet.playSequentially(animatorSet,bezierValueAnimator);
        bezierAnimatorSet.setTarget(love);
        return  bezierAnimatorSet;
    }


    private ValueAnimator getBezierValueAnimator(final ImageView imageView){
         //获取两个控制点
        PointF P1 = getPointF(1);
        PointF P2 = getPointF(2);
        //获得起始点
        PointF P0 = new PointF((mWidth - mDrawableWidth) / 2, mHeight
                - mDrawableHeight);
        //获得终点
        PointF P3 = new PointF(mRandom.nextInt(mWidth),0);
        PointFEvaluatior evaluatior = new PointFEvaluatior(P1,P2);
        ValueAnimator animator = ValueAnimator.ofObject(evaluatior,P0,P3);//把起始点终点传进去
        animator.setDuration(2000);
        animator.setTarget(imageView);
        animator.setInterpolator(interpolators[mRandom.nextInt(interpolators.length)]);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
                imageView.setAlpha(1-animation.getAnimatedFraction());
            }
        });


        return animator;
    }

    private PointF getPointF(int i) {
        Log.e("TAG", "getPointF: ===========>>> i = "+i+" ==>"+(mRandom.nextInt(mHeight)-(i-1)*mHeight/2 ));
         return new PointF(mRandom.nextInt(mWidth)-mDrawableWidth,mRandom.nextInt(mHeight)-(i-1)*mHeight/2);
    }

}
