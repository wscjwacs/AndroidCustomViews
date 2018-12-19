package ca.com.androidcustomviews.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ca.com.androidcustomviews.R;
import ca.com.androidcustomviews.adapter.MainAdapter;
import ca.com.androidcustomviews.base.BaseActivity;
import ca.com.androidcustomviews.base.BaseRecyclerAdapter;
import ca.com.androidcustomviews.bean.TypeBean;


public class MainActivity extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.rv_main)
    RecyclerView vScroll;
    private MainAdapter adapter;
    private List<TypeBean> typeBeans = new ArrayList<>();



    @Override
    protected int initContentView() {

        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        adapter = new MainAdapter(this);
        adapter.setData(getData());
        adapter.setOnItemClickListener(this);
        vScroll.setLayoutManager(new LinearLayoutManager(this));
        vScroll.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }





    private List<TypeBean> getData() {
        typeBeans.add(new TypeBean("防QQ运动", 0));
        typeBeans.add(new TypeBean("防QQ消息拖拽--贝塞尔曲线，正余弦函数实现", 1));
        typeBeans.add(new TypeBean("防花束直播点赞效果--贝塞尔曲线实现", 2));
        typeBeans.add(new TypeBean("波浪效果，动画实现", 3));
        typeBeans.add(new TypeBean("点赞动画效果实现", 4));
        typeBeans.add(new TypeBean("铃铛左右摇摆动画", 5));
        typeBeans.add(new TypeBean("防雅虎新闻摘要加载动画", 6));
        typeBeans.add(new TypeBean("灵动的红鲤鱼动画", 7));
        typeBeans.add(new TypeBean("Touch事件分发 - 九宫格解锁", 8));
        typeBeans.add(new TypeBean("仿抖音加载框之两颗小球转动效果", 9));
        typeBeans.add(new TypeBean("登录按钮效果切换", 10));
        typeBeans.add(new TypeBean("吸顶效果--一行代码实现", 11));
        return typeBeans;
    }

    @Override
    public void onItemClick(int position) {
        switch (typeBeans.get(position).getType()) {
            case 0:
                startActivity(new Intent(MainActivity.this, QQSetpActivity.class));
                break;
            case 1:
                startActivity(new Intent(MainActivity.this, BubbleViewActivity.class));
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, HuaSuLiveActivity.class));
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, WaveViewActivity.class));
                break;
            case 4:
                startActivity(new Intent(MainActivity.this, LikeViewActivity.class));
                break;
            case 5:
                startActivity(new Intent(MainActivity.this, LingDangActivity.class));
                break;
            case 6:
                startActivity(new Intent(MainActivity.this, SplashActivity.class));
                break;
            case 7:
                startActivity(new Intent(MainActivity.this, RedCarpActivity.class));
                break;
            case 8:
                startActivity(new Intent(MainActivity.this, LockPatternActivity.class));
                break;
            case 9:
                startActivity(new Intent(MainActivity.this, DYLoadingActivity.class));
                break;
            case 10:
               // startActivity(new Intent(MainActivity.this, LoginActivity.class));
                startActivity(new Intent(MainActivity.this, ProgressButtonActivity.class));
                break;
            case 11:
           //     startActivity(new Intent(MainActivity.this, ProgressButtonActivity.class));
                break;
        }
    }


    /**
     * 当setContentView设置显示OK以后会回调Activity的onContentChanged方法。
     * Activity的各种View的findViewById()方法等都可以放到该方法中，系统会帮忙回调。
     */
    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    /**
     * 此方法是activity的方法，当此activity在栈顶时，触屏点击按home，back，menu键等都会触发此方法。
     * 下拉statubar、旋转屏幕、锁屏不会触发此方法。所以它会用在屏保应用上，因为当你触屏机器 就会立
     * 马触发一个事件，而这个事件又不太明确是什么，正好屏保满足此需求；或者对于一个Activity，控制多
     * 长时间没有用户点响应的时候，自己消失等。
     */
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }



    @OnClick({R.id.rv_main, R.id.tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rv_main:
                break;
            case R.id.tv:
                break;
        }
    }
}
