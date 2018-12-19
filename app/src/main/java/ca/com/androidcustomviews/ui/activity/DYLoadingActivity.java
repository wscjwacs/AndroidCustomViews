package ca.com.androidcustomviews.ui.activity;

import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ca.com.androidcustomviews.R;
import ca.com.androidcustomviews.base.BaseActivity;
import ca.com.androidcustomviews.customviews.DYLoadingView;

public class DYLoadingActivity extends BaseActivity {

    @BindView(R.id.dy_loading)
    DYLoadingView dyLoading;

    @Override
    protected int initContentView() {
        return R.layout.activity_dyloading;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }




    @OnClick(R.id.dy_loading)
    public void onViewClicked() {
        dyLoading.start();
    }
}
