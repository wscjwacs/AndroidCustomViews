package ca.com.androidcustomviews.ui.activity;

import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.com.androidcustomviews.R;
import ca.com.androidcustomviews.base.BaseActivity;
import ca.com.androidcustomviews.customviews.LingDangAnimationView;

public class LikeViewActivity extends BaseActivity {

    @BindView(R.id.lingdang)
    LingDangAnimationView lingdang;

    @Override
    protected int initContentView() {
        return R.layout.activity_like_view;
    }

    @Override
    protected void initView() {
        lingdang.setLevel(1);
    }

    @Override
    protected void initData() {

    }



}
