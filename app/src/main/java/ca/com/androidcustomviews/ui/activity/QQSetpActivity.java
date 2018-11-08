package ca.com.androidcustomviews.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import ca.com.androidcustomviews.R;
import ca.com.androidcustomviews.base.BaseActivity;
import ca.com.androidcustomviews.customviews.CorloTrackView;
import ca.com.androidcustomviews.customviews.DrawTextView;
import ca.com.androidcustomviews.customviews.DynamicHeartView;
import ca.com.androidcustomviews.customviews.QQStepView;

public class QQSetpActivity extends BaseActivity {


    @BindView(R.id.qqStep)
    QQStepView qqStep;
    @BindView(R.id.et_curr_step)
    EditText etCurrStep;
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.color_track_view)
    CorloTrackView colorTrackView;
    @BindView(R.id.btn_right_to_left)
    Button btnRightToLeft;
    @BindView(R.id.combine_view_pager)
    Button combineViewPager;
    @BindView(R.id.dtv)
    DrawTextView dtv;
    @BindView(R.id.dhv)
    DynamicHeartView dhv;
    @BindView(R.id.btn_start_heart)
    Button btnStartHeart;

    @Override
    protected int initContentView() {
        return R.layout.activity_bubble_view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.start, R.id.btn_right_to_left, R.id.combine_view_pager, R.id.btn_start_heart,R.id.btn_left_to_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:
                QQStepView();
                break;
            case R.id.btn_right_to_left:
                right_to_left();
                break;
            case R.id.btn_left_to_right:
                left_to_right();
                break;
            case R.id.combine_view_pager:
                combine_view_pager();
                break;
            case R.id.btn_start_heart:
                startHeart();
                break;
        }
    }

    private void QQStepView() {
        qqStep.setStepMax(4000);
        if (TextUtils.isEmpty(etCurrStep.getText().toString().trim())) {
            Toast.makeText(QQSetpActivity.this, "输入有误", Toast.LENGTH_SHORT).show();
            return;
        }
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, Float.valueOf(etCurrStep.getText().toString().trim()));
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                qqStep.setCurrStep((int) animatedValue);
            }
        });
        valueAnimator.start();
    }


    public void left_to_right() {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        colorTrackView.setDirection(CorloTrackView.LEFT_TO_RIGHT);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                colorTrackView.setCurrentProgress(animatedValue);
            }
        });

        valueAnimator.start();
    }


    public void right_to_left() {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        colorTrackView.setDirection(CorloTrackView.RIGHT_TO_LEFT);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                colorTrackView.setCurrentProgress(animatedValue);
            }
        });

        valueAnimator.start();
    }

    public void combine_view_pager(){
        startActivity(new Intent(QQSetpActivity.this,ViewPagerIndicator.class));
    }

    public void startHeart(){
        dhv.startPathAnim(4000);
    }
}
