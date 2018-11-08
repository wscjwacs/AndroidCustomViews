package ca.com.androidcustomviews.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hp on 2018/7/10.
 */

public class DrawTextView extends View {
    private Paint mPaint ;
    private String text = "深圳市易美科+abcdefg";
    public DrawTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    /**
     * 初始化画笔设置
     */
    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#FF4081"));
        mPaint.setTextSize(40f);
    }

    /**
     * 绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#99ffff00"));
//        //文字的x轴坐标
//        float stringWidth = mPaint.measureText(text);
//        float x = (getWidth() - stringWidth) / 2;
//        //文字的y轴坐标
//        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
//        float y = getHeight() / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
//        canvas.drawText(text, x, y, mPaint);
        //获取文字


        Rect bounds = new Rect();
        mPaint.getTextBounds(text,0,text.length(),bounds);
        //获取字体的宽度
        int x = getWidth()/2 - bounds.width()/2;
        //获取基线
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;
        int baseLine = getHeight()/2 + dy;

        canvas.drawText(text,x,baseLine,mPaint);

     //   canvas.drawText(text, 0 ,getHeight()/2 ,mPaint);
    }

    /**
     * 测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(300,300);
        }else  if (wSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(300,hSpecSize);
        }else if (hSpecMode ==  MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSpecSize,300);
        }
    }


}
