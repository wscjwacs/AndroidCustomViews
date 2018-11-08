package ca.com.androidcustomviews.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ca.com.androidcustomviews.R;
import ca.com.androidcustomviews.base.BaseActivity;
import ca.com.androidcustomviews.customviews.LingDangAnimationView;
import ca.com.androidcustomviews.customviews.LingDangAnimationView2;

public class LingDangActivity extends BaseActivity {

    @BindView(R.id.ling_dang_view)
    LingDangAnimationView2 lingDangView;
    @BindView(R.id.btn_main)
    Button btnMain;
    @BindView(R.id.btn_main2)
    Button btnMain2;
    @BindView(R.id.btn_main3)
    Button btnMain3;

    @Override
    protected int initContentView() {
        return R.layout.activity_ling_dang;
    }

    @Override
    protected void initView() {
     //  lingDangView.setLevel(1); // onCreate里面还没有开始绘view的绘制流程
    }

    @Override
    protected void initData() {

    }





    @OnClick({R.id.btn_main,R.id.btn_main2, R.id.btn_main3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_main:
                lingDangView.setLevel(1);
                break;
            case R.id.btn_main2:
                lingDangView.setLevel(2);
                break;
            case R.id.btn_main3:
                lingDangView.setLevel(3);
                break;
        }
    }

}
