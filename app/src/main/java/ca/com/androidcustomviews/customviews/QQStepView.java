package ca.com.androidcustomviews.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import ca.com.androidcustomviews.R;


/**
 *
 *  1.分析效果
 2.确定自定义属性，编写attrs.xml
 3.再布局中使用
 4.在自定义view中获取自定义属性.
 5.onMeasure()
 6.画外圆弧，内圆弧，文字
 7.其他

 */

public class QQStepView extends View {
    public static final String TAG = "QQStepView";
    private int mOuterColor = Color.BLUE;
    private int mInnerColor = Color.RED;
    private int mBoderWidth = 20;//px
    private int mStepTextColor = Color.BLACK;
    private int mSetpTextSize = 20;
    private Paint mOutPaint;
    private Paint mInnerPaint;
    private Paint mTextPaint;

    private int mCurrStep;
    private int mStepMax;

    public QQStepView(Context context) {
        this(context, null);

    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor);

        mBoderWidth =  array.getDimensionPixelOffset(R.styleable.QQStepView_boderWidth, mBoderWidth);
        mSetpTextSize =  array.getDimensionPixelOffset(R.styleable.QQStepView_setpTextSize, mSetpTextSize);

        array.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //调用者可能在布局文件中设置的宽度高度不一致 ，取最小值
        //获取模式 AT_MOST
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width > height ? height : width, height > width ? width : height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画外圆弧
        RectF rectF = new RectF(mBoderWidth / 2, mBoderWidth / 2,
                getWidth() - mBoderWidth / 2, getHeight() - mBoderWidth / 2);
        canvas.drawArc(rectF, 135, 270, false, mOutPaint);
        if (mStepMax == 0) return;//第一次系统初始化加载布局的时候会执行onDraw方法

        //画内圆弧
        float sweepAngle = (float) mCurrStep / mStepMax;
        canvas.drawArc(rectF, 135, sweepAngle * 270, false, mInnerPaint);


        //画文字
        String setpText = "" + (int) mCurrStep;
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(setpText, 0, setpText.length(), textBounds);
        int dx = (getWidth() - textBounds.width()) / 2;
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();

        //基线 baseLine
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(setpText, dx, baseLine, mTextPaint);

    }

    public synchronized int getmCurrStep() {
        return mCurrStep;
    }

    public synchronized int getmStepMax() {
        return mStepMax;
    }

    public synchronized void setCurrStep(int mCurrStep) {
        if(0 > mCurrStep){
            throw new IllegalArgumentException("当前步数不能小于0");
        }
        this.mCurrStep = mCurrStep;
        invalidate();
    }


    public synchronized void setStepMax(int mStepMax) {
        if(0 > mCurrStep){
            throw new IllegalArgumentException("最大步数不能小于0");
        }
        this.mStepMax = mStepMax;
    }

    private void init() {
        mOutPaint = new Paint();
        mOutPaint.setColor(mOuterColor);
        mOutPaint.setStrokeWidth(mBoderWidth);
        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutPaint.setStrokeJoin(Paint.Join.ROUND);

        mOutPaint.setAntiAlias(true);


        mInnerPaint = new Paint();
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStrokeJoin(Paint.Join.ROUND);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStrokeWidth(mBoderWidth);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setAntiAlias(true);

        //创建画笔
        mTextPaint = new Paint();
        //设置抗锯齿
        mTextPaint.setAntiAlias(true);
//        //设置为Round
        mTextPaint.setStrokeCap(Paint.Cap.ROUND);
        mTextPaint.setStrokeJoin(Paint.Join.ROUND);
        //设置画笔颜色
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(mSetpTextSize);

    }
}
