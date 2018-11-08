package ca.com.androidcustomviews.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import ca.com.androidcustomviews.R;
import ca.com.androidcustomviews.base.BaseActivity;
import ca.com.androidcustomviews.customviews.LoadingView;

/**
 * Created by hp on 2018/9/4.
 */

public class SplashActivity extends Activity {
    private LoadingView mLoadingView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {
        frameLayout = new FrameLayout(this);
        ImageView contentView=new ImageView(this);
        contentView.setBackgroundResource(R.mipmap.bg);
        frameLayout.addView(contentView);
        mLoadingView =new LoadingView(this);
        frameLayout.addView(mLoadingView);
        setContentView(frameLayout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingView.splashDisappear();
            }
        },3000);
    }


}
