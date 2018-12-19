package ca.com.androidcustomviews.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import ca.com.androidcustomviews.R;
import ca.com.androidcustomviews.base.BaseActivity;
import ca.com.androidcustomviews.customviews.ProgressButton;

/**
 * Created by wscjw on 2018-12-19.
 */

public class ProgressButtonActivity extends BaseActivity  implements View.OnClickListener {

    ProgressButton pb_button;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pb_btn:
                pb_button.startAnim();
                Message m=mHandler.obtainMessage();
                mHandler.sendMessageDelayed(m,1500);
                break;
            default:
                break;
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pb_button.stopAnim(new ProgressButton.OnStopAnim() {
                @Override
                public void Stop() {
                    Intent i=new Intent();
                    i.setClass(ProgressButtonActivity.this,DYLoadingActivity.class);
                    startActivity(i);
                }
            });

        }
    };





    @Override
    protected int initContentView() {
        return R.layout.activity_progress_button;
    }

    @Override
    protected void initView() {
        pb_button=(ProgressButton)findViewById(R.id.pb_btn);
        pb_button.setBgColor(Color.RED);
        pb_button.setTextColor(Color.WHITE);
        pb_button.setProColor(Color.WHITE);
        pb_button.setButtonText("Login in");
    }

    @Override
    protected void initData() {

    }
}


