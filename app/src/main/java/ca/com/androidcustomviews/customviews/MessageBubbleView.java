package ca.com.androidcustomviews.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hp on 2018/7/13.
 */

public class MessageBubbleView extends View {
      private Paint mPaint;
    // 两个实心圆--根据点的坐标来绘制圆
    private PointF mDragPoint, mFixationPoint;
     private int mDragRadius = 9; // 拖拽圆半径

    // 固定圆最大半径(初始半径)/半径的最小值
    private int mFixationRadiusMax = 7;
    private int mFixationRadiusMin = 3;
    private int mFixationRadius;// 固定圆半径


    public MessageBubbleView(Context context) {
        this(context,null);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.RED);
        mDragRadius = dip2px(mDragRadius);
        mFixationRadiusMax = dip2px(mFixationRadiusMax);
        mFixationRadiusMin = dip2px(mFixationRadiusMin);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDragPoint ==null||mFixationPoint ==null){
            return;
        }
        canvas.drawCircle(mDragPoint.x,mDragPoint.y,mDragRadius,mPaint);//画拖拽圆
        mFixationRadius = mFixationRadiusMax - getPointsDistance(mDragPoint,mFixationPoint)/14;//计算固定圆的半径
        if (mFixationRadius>mFixationRadiusMin){
            Path path  = getBezierPath();
            canvas.drawCircle(mFixationPoint.x,mFixationPoint.y,mFixationRadius,mPaint);//画固定圆
            canvas.drawPath(path,mPaint);//画贝塞尔曲线
         }

    }


    /**
     * 计算两圆心之间的距离
     * @param p1
     * @param p2
     * @return
     */
    private int getPointsDistance(PointF p1,PointF p2){
        //以下两种写法都是可以的
       // return (int) Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
        return (int)Math.sqrt(Math.pow(p1.x-p2.x,2)+Math.pow(p1.y-p2.y,2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float downX =  event.getX();
                float downY =  event.getY();
                initDragPoint(downX,downY);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX =  event.getX();
                float moveY =  event.getY();
                updateDragPointLocation(moveX,moveY);
                break;
        }

        invalidate();
        return true;
    }

    private void initDragPoint(float downX, float downY) {
        mDragPoint = new PointF(downX,downY);
        mFixationPoint = new PointF(downX,downY);
    }

    private void updateDragPointLocation(float moveX, float moveY){
        mDragPoint.x = moveX;
        mDragPoint.y = moveY;
    }


    /**
     * 计算贝塞尔曲线路径
     * @return
     */
    private Path getBezierPath( ){
        Path bizerPath = new Path();
        double tanA = (mDragPoint.y-mFixationPoint.y)/(mDragPoint.x-mFixationPoint.x); //计算斜率
        double A = Math.atan(tanA);//计算角A

        float P0x = (float) (mFixationPoint.x+mFixationRadius*Math.sin(A));
        float P0y = (float) (mFixationPoint.y-mFixationRadius*Math.cos(A));

        float P1x = (float) (mDragPoint.x+mDragRadius*Math.sin(A));
        float P1y = (float) (mDragPoint.y-mDragRadius*Math.cos(A));

        float P2x = (float) (mDragPoint.x-mDragRadius*Math.sin(A));
        float P2y = (float) (mDragPoint.y+mDragRadius*Math.cos(A));

        float P3x = (float) (mFixationPoint.x-mFixationRadius*Math.sin(A));
        float P3y = (float) (mFixationPoint.y+mFixationRadius*Math.cos(A));

        PointF conllPoint = getControllPoint();

        bizerPath.moveTo(P0x,P0y);
        bizerPath.quadTo(conllPoint.x,conllPoint.y,P1x,P1y);
        bizerPath.lineTo(P2x,P2y);
        bizerPath.quadTo(conllPoint.x,conllPoint.y,P3x,P3y);
        bizerPath.close();

        return bizerPath;
    }

    //获取控制点
    private PointF getControllPoint(){
       return new PointF((mDragPoint.x+mFixationPoint.x)/2,(mDragPoint.y+mFixationPoint.y)/2);
    }
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }
}
