package ca.com.androidcustomviews.customviews;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import ca.com.androidcustomviews.R;

/**
 * 实现分析:
 * 1.点赞的是一个图片(attr)
 * 2.图片会执行动画（缩放动画，先放大，在缩小到正常尺寸，属性动画最合适）
 * 3.点赞的时候移动的是图片还是文字(位移动画，和透明度变换，只是出现一个动画效果，控件本身并不没有移动，所以补间动画比较合适)
 * 4.移动的距离需要一个设定，移动的图片或者文字仅仅作为动画效果，并不能占用控件的实际大小，所以考虑使用popupwindow实现(attr)
 * 5.字体的颜色设置(attr) 大小
 * 6.动画的起始位置，结束位置，开始的透明度，结束的透明度 (attr)
 * <p>
 * 7.移动的图片(attr)
 */

public class LikeView extends View implements View.OnClickListener{

    private int mMoveDistance = 60;
    private int mStartMovePosition = 0;

    private String mText = "+1";
    private int mTextColor = Color.BLACK;
    private int mTextSize = 16;

    private float mFromAlpha = 1.0f;
    private float mToAlpha = 0f;
    private int mDuration = 1000;

    private Drawable mLikeImg;//点赞图片
    private Drawable mAnimImg;//动画的图片

    private int mAnimType = 1;//动画执行的类型(图片或者文字)

    private AnimationSet mAnimationSet;
    private PopupWindow mPopupWindow;
    private AppCompatTextView tvAnimation;


    public LikeView(Context context) {
        this(context, null);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LikeView);
        mMoveDistance = typedArray.getInt(R.styleable.LikeView_mmove_distance, mMoveDistance);
        mStartMovePosition = typedArray.getInt(R.styleable.LikeView_mstart_move_position, mStartMovePosition);
        mText = typedArray.getString(R.styleable.LikeView_mtext);
        mTextColor = typedArray.getColor(R.styleable.LikeView_mtext_color, mTextColor);
        mTextSize = typedArray.getInt(R.styleable.LikeView_mtext_size, mTextSize);
        mFromAlpha = typedArray.getFloat(R.styleable.LikeView_mfrom_alpha, mFromAlpha);
        mToAlpha = typedArray.getFloat(R.styleable.LikeView_mto_alpha, mToAlpha);
        mDuration = typedArray.getInt(R.styleable.LikeView_mduration, mDuration);
        mLikeImg = typedArray.getDrawable(R.styleable.LikeView_mlike_img);
        mAnimImg = typedArray.getDrawable(R.styleable.LikeView_mainm_img);
        mAnimType = typedArray.getInt(R.styleable.LikeView_manim_type, mAnimType);
        if (mLikeImg == null) {
            mLikeImg = ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_black_24dp);
        }
        typedArray.recycle();
        setListener();
        initPopupWindow();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mLikeImg.draw(canvas);
    }

    private void setListener(){
        this.setOnClickListener(this);
    }

    private void initPopupWindow(){
        //我们将PopupWindow作为动画View
        //初始化我们的PopupWindow
        mPopupWindow = new PopupWindow();
        //PopupWindow创建相对布局
        RelativeLayout layout = new RelativeLayout(getContext());
        //布局参数
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        tvAnimation = new AppCompatTextView(getContext());
        tvAnimation.setIncludeFontPadding(false);
        tvAnimation.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
        tvAnimation.setTextColor(mTextColor);
        if (mAnimType == 1) {
            tvAnimation.setText(mText);
        } else {
            tvAnimation.setText("");
            tvAnimation.setBackgroundDrawable(mAnimImg);
        }
        tvAnimation.setLayoutParams(layoutParams);
        layout.addView(tvAnimation);
        mPopupWindow.setContentView(layout);

        //量测我们的动画的宽高
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        tvAnimation.measure(w, h);
        mPopupWindow.setWidth(tvAnimation.getMeasuredWidth());
         mPopupWindow.setHeight(mMoveDistance + tvAnimation.getMeasuredHeight());
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setFocusable(false);
        mPopupWindow.setTouchable(false);
        mPopupWindow.setOutsideTouchable(false);

        this.setOnClickListener(this);


    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width ;
        int height ;
        int  widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int  heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode== MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED){
            width = mLikeImg.getIntrinsicWidth();
        }else {
            width = widthSize;
        }
        if (heightMode == MeasureSpec.AT_MOST|| heightMode == MeasureSpec.UNSPECIFIED ){
            height = mLikeImg.getIntrinsicHeight();
        }else {
            height = heightSize;
        }
        setMeasuredDimension(width,height);

        mLikeImg.setBounds(new Rect(0,0,width,height));
    }


    @Override
    public void onClick(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            int offsetY = -getHeight() - mPopupWindow.getHeight();
            mPopupWindow.showAsDropDown(this, getWidth() / 2 - mPopupWindow.getWidth() / 2, offsetY);
            mPopupWindow.update();
            if (mAnimationSet == null) {
                showPopAnimation();
            }
            tvAnimation.startAnimation(mAnimationSet);
            showScaleAnimation();

        }
    }

    /**
     * 动画组合
     */
    private void showPopAnimation() {
        mAnimationSet = new AnimationSet(true);
        TranslateAnimation translateAnim = new TranslateAnimation(0, 0, mStartMovePosition, -mMoveDistance);
        AlphaAnimation alphaAnim = new AlphaAnimation(mFromAlpha, mToAlpha);
        mAnimationSet.addAnimation(translateAnim);
        mAnimationSet.addAnimation(alphaAnim);
        mAnimationSet.setDuration(mDuration);
        mAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mPopupWindow.dismiss();
                        }
                    });
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
    /**
     * 缩放动画
     */
    private void showScaleAnimation() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this,"scaleX",1f,0.8f,1.2f,1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this,"scaleY",1f,0.8f,1.2f,1f);
        scaleX.setDuration(mDuration);
        scaleY.setDuration(mDuration);
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleY.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(scaleY);
        set.start();
    }




}
