package ca.com.androidcustomviews.customviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ca.com.androidcustomviews.R;

/**
 * Created by hp on 2018/7/10.
 */

@SuppressLint("AppCompatCustomView")
public class CorloTrackView extends TextView {
    //设置字体的颜色变化的方向，也可以设置从上到下，变色
    public static final int LEFT_TO_RIGHT = 0;//代表从左到右标志位,
    public static final int RIGHT_TO_LEFT = 1;//代表从右到左标志位

    private int mChangeTextColor ;
    private int mOriginTextColor ;

    private int direction = LEFT_TO_RIGHT;//默认为从左到右
    private Paint mOriginTextPaint;//默认画笔
    private Paint mChangeTextPaint;//改变颜色的画笔



    //当前进度
    private float currentProgress;

    public CorloTrackView(Context context) {
        this(context, null);
    }

    public CorloTrackView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CorloTrackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context,attrs);

    }

    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CorloTrackView);
        mChangeTextColor = typedArray.getColor(R.styleable.CorloTrackView_changeTextColor, getTextColors().getDefaultColor());
        mOriginTextColor = typedArray.getColor(R.styleable.CorloTrackView_originTextColor, getTextColors().getDefaultColor());
        typedArray.recycle();
        mOriginTextPaint = getTextPaintByColor(mOriginTextColor);
        mChangeTextPaint = getTextPaintByColor(mChangeTextColor);
    }

    /**
     * 设置画笔颜色
     * @param changeColor
     */
    public synchronized void setChangeColor(int changeColor){
        this.mChangeTextPaint.setColor(changeColor);
    }

    public synchronized void setOriginColor(int originColor){
        this.mOriginTextPaint.setColor(originColor);
    }


    /**
     * 根据自定义画笔，重写onDray()方法，重写绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {

        //根据当前进度，获取当前中间值
        int middle = (int) (currentProgress * getWidth());

        //根据朝向，绘制TextView
        if(LEFT_TO_RIGHT == direction){
            //当前朝向为  从左到右
            drawText(canvas,mChangeTextPaint,0,middle);
            drawText(canvas,mOriginTextPaint,middle,getWidth());
        }else{
            //当前朝向  从右到左
            drawText(canvas,mChangeTextPaint,getWidth() - middle,getWidth());
            drawText(canvas,mOriginTextPaint,0,getWidth()-middle);
        }

    }

    /**
     * 绘制TextView
     * @param canvas
     * @param textPaint
     * @param start
     * @param end
     */
    private void drawText(Canvas canvas, Paint textPaint, int start, int end) {

        //保存画布状态
        canvas.save();
        Rect rect = new Rect(start,0,end,getHeight());
        canvas.clipRect(rect);

        //获取文字
        String text = getText().toString();

        //方法一：计算文字的中心位置
        Rect bounds = new Rect();
        textPaint.getTextBounds(text,0,text.length(),bounds);//1.获取代表文字的区域
        //获取字体的宽度
        int x = getWidth()/2 - bounds.width()/2;
        //获取基线
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;
        int baseLine = getHeight()/2 + dy;
        canvas.drawText(text,x, baseLine,textPaint);


        //方法二：计算文字的中心位置
//        //文字的x轴坐标
//        float stringWidth = textPaint.measureText(text);
//        float x = (getWidth() - stringWidth) / 2;
//        //文字的y轴坐标
//        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
//        float y = getHeight() / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
//        canvas.drawText(text,x,y,textPaint);



        //释放画布状态，既恢复Canvas旋转，缩放等之后的状态。
        canvas.restore();
    }
    public synchronized int getDirection() {
        return direction;
    }

    public synchronized void setDirection(@Direction int direction) {
        this.direction = direction;
    }


    /**
     * 设置进度
     * @param currentProgress
     */
    public synchronized  void setCurrentProgress(float currentProgress){
        this.currentProgress = currentProgress;
        invalidate();
    }

    public synchronized float getCurrentProgress() {
        return currentProgress;
    }

    /**
     * 根据自定义的颜色创建画笔
     * @param textColor
     * @return
     */
    private Paint getTextPaintByColor(int textColor) {
        //创建画笔
        Paint paint = new Paint();
        //设置画笔颜色
        paint.setColor(textColor);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置防抖动
        paint.setDither(true);
        //设置字体大小
        paint.setTextSize(getTextSize());
        return paint;
    }

    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({LEFT_TO_RIGHT, RIGHT_TO_LEFT})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Direction {
    }
}
