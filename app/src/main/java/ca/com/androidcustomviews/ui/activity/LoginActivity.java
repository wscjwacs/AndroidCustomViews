package ca.com.androidcustomviews.ui.activity;

import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ca.com.androidcustomviews.R;
import ca.com.androidcustomviews.base.BaseActivity;
import ca.com.androidcustomviews.customviews.LoginView;

/**
 * Created by wscjw on 2018/11/18.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.lv_view)
    LoginView lvView;

    @Override
    protected int initContentView() {
        return R.layout.activiyt_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }



    @OnClick(R.id.lv_view)
    public void onViewClicked() {
        lvView.start();
    }
}
