package ca.com.androidcustomviews.ui.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import ca.com.androidcustomviews.R;
import ca.com.androidcustomviews.base.BaseActivity;
import ca.com.androidcustomviews.customviews.CorloTrackView;
import ca.com.androidcustomviews.ui.fragment.ItemFragment;

public class ViewPagerIndicator extends BaseActivity {

    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华"};
    private LinearLayout mIndicatorContainer;
    private List<CorloTrackView> mIndicators;
    private ViewPager mViewPager;
    private String TAG = "ViewPagerActivity";

    @Override
    protected int initContentView() {
        return R.layout.activity_view_pager_indicator;
    }

    @Override
    protected void initView() {
        mIndicators = new ArrayList<>();
        mIndicatorContainer = (LinearLayout) findViewById(R.id.indicator_view);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        initIndicator();
        initViewPager();
    }

    @Override
    protected void initData() {

    }


    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }
        });

        /**
         * 添加一个切换的监听那个setOnPageChangeListener过时了
         * 这个看源码去吧
         */
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                Log.e(TAG, "position --> " + position + " positionOffset --> " + positionOffset);
                if (positionOffset > 0) {
                    // 获取左边
                    CorloTrackView left = mIndicators.get(position);
                    // 设置朝向
                    left.setDirection(CorloTrackView.RIGHT_TO_LEFT);
                    // 设置进度  positionOffset 是从 0 一直变化到 1 不信可以看打印
                    left.setCurrentProgress(1 - positionOffset);

                    // 获取右边
                    CorloTrackView right = mIndicators.get(position + 1);
                    right.setDirection(CorloTrackView.LEFT_TO_RIGHT);
                    right.setCurrentProgress(positionOffset);
                }
            }
        });

        // 默认一进入就选中第一个
        CorloTrackView left = mIndicators.get(0);
        left.setDirection(CorloTrackView.RIGHT_TO_LEFT);
        left.setCurrentProgress(1);
    }

    /**
     * 初始化可变色的指示器
     */
    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            // 动态添加颜色跟踪的TextView
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            CorloTrackView corloTrackView = new CorloTrackView(this);
            // 设置两种颜色
            corloTrackView.setOriginColor(Color.BLACK);
            corloTrackView.setChangeColor(Color.RED);
            corloTrackView.setText(items[i]);
            corloTrackView.setTextSize(20);
            corloTrackView.setLayoutParams(params);
            // 把新的加入LinearLayout容器
            mIndicatorContainer.addView(corloTrackView);
            // 加入集合
            mIndicators.add(corloTrackView);
        }
    }


}
