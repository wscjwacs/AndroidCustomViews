package ca.com.androidcustomviews.customviews;

/**
 * Created by hp on 2018/7/31.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ca.com.androidcustomviews.R;



/**
 *  铃铛左右摇晃View
 * Created by Jin on 2016/4/1.
 */
public class LingDangAnimationView extends RelativeLayout implements ViewTreeObserver.OnGlobalLayoutListener{

    /**
     *  中间到左边动画
     */
    private RotateAnimation holder2LeftAnimation;
    /**
     *  左边到右边动画
     */
    private RotateAnimation left2RightAnimation;
    /**
     *  右边到左边动画
     */
    private RotateAnimation right2LeftAnimation;

    private ImageView imageView;

    private int width,height;
    private int level;


    private static  boolean isRotation;

    public LingDangAnimationView(Context context) {
        this(context,null);
    }


    public LingDangAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LingDangAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {

        holder2LeftAnimation = new RotateAnimation(0f, 45f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        holder2LeftAnimation.setDuration(500);
        left2RightAnimation = new RotateAnimation(45f, -45f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        left2RightAnimation.setDuration(1000);
        right2LeftAnimation = new RotateAnimation(-45f, 45f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        right2LeftAnimation.setDuration(1000);


        getViewTreeObserver().addOnGlobalLayoutListener(this);

        holder2LeftAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isRotation)
                imageView.startAnimation(left2RightAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        left2RightAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isRotation)
                imageView.startAnimation(right2LeftAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        right2LeftAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isRotation)
                imageView.startAnimation(left2RightAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onGlobalLayout() {
        int tmpW = getWidth();
        int tmpH = getHeight();
        if (width != tmpW || height != tmpH) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
            width = tmpW;
            height = tmpH;
        }
    }

    public void setRotation(boolean rotation) {
        isRotation = rotation;
    }


    public void setLevel(int level){
        this.level=level;
        show();
    }


    private void show() {
        if (imageView==null) {
            imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.lingdang1);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width/2,height);
            layoutParams.addRule(CENTER_IN_PARENT);
            imageView.setLayoutParams(layoutParams);
            addView(imageView);
        }

        switch (level){
            case 1:
                removeAnimation();
//                setRotation(false);
//                imageView.setImageResource(R.drawable.lingdang1);
                setImageResource(R.drawable.lingdang1,false);
                break;
            case 2:
                removeAnimation();
//                setRotation(false);
//                imageView.setImageResource(R.drawable.lingdang2);
                setImageResource(R.drawable.lingdang2,false);
                break;
            case 3:
//                setRotation(true);
//                imageView.setImageResource(R.drawable.lingdang0);
                setImageResource(R.drawable.lingdang0,true);
                break;
        }
        if(level==3){
            imageView.startAnimation(holder2LeftAnimation);
        }
    }

    private void removeAnimation(){
        imageView.clearAnimation();
    }

    private void setImageResource(int img,boolean hasAnimation){
        setRotation(hasAnimation);
        imageView.setImageResource(img);
    }

}