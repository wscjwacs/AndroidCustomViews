package ca.com.androidcustomviews.ui.activity;

import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ca.com.androidcustomviews.R;
import ca.com.androidcustomviews.base.BaseActivity;
import ca.com.androidcustomviews.customviews.LovelyLayout;

public class HuaSuLiveActivity extends BaseActivity {

    @BindView(R.id.btn_dian_zhan)
    Button btnDianZhan;
    @BindView(R.id.loveLayout)
    LovelyLayout loveLayout;

    @Override
    protected int initContentView() {
        return R.layout.activity_hua_su_live;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }



    @OnClick(R.id.btn_dian_zhan)
    public void onViewClicked() {
        for (int i=0;i<15;i++)
        loveLayout.addLove();
    }
}
