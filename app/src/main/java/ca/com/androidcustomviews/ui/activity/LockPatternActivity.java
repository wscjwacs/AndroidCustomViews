package ca.com.androidcustomviews.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ca.com.androidcustomviews.R;
import ca.com.androidcustomviews.base.BaseActivity;
import ca.com.androidcustomviews.customviews.lockview.LockPatternView;

public class LockPatternActivity extends BaseActivity implements LockPatternView.CallBack{
    private LockPatternView mLockPatternView;

    private TextView tv;

    @Override
    protected int initContentView() {
        return  R.layout.activity_lock_pattern;

    }

    @Override
    protected void initView() {
        mLockPatternView = findViewById(R.id.lpv_view);
        mLockPatternView.setCallBack(this);
        tv =   findViewById(R.id.btn_retry);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLockPatternView.reset();
            }
        });
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onPwd(String pwd) {
         tv.setText(pwd);
    }
}
