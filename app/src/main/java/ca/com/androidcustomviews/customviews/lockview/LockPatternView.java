package ca.com.androidcustomviews.customviews.lockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wscjw on 2018/11/10.
 */

public class LockPatternView extends View {


    private Paint mLinePaint;
    private Paint mPressedPaint;
    private Paint mErrorPaint;
    private Paint mNormalPaint;
    private Paint mArrowPaint;
     // 颜色
    private int mOuterPressedColor = 0xff8cbad8;
    private int mInnerPressedColor = 0xff0596f6;
    private int mOuterNormalColor = 0xffd9d9d9;
    private int mInnerNormalColor = 0xff929292;
    private int mOuterErrorColor = 0xff901032;
    private int mInnerErrorColor = 0xffea0945;
    private int mDotRadius;
    private boolean mIsInit;

    private Point[][] mPoints = new Point[3][3];

    private List<Point> mSelectedPoints = new ArrayList<>();

   // 按下的时候是否是按在一个点上
    private boolean mIsTouchPoint = false;

    public LockPatternView(Context context) {
        this(context,null);
    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

     }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mIsInit) {//避免多次调用
            initPaint();
            initDot();
            mIsInit = true;
        }
        // 绘制九个宫格
        drawShow(canvas);
    }

    // 手指触摸的位置
    private float mMovingX = 0f;
    private float mMovingY = 0f;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取 触摸点相对于view的位置
        mMovingX = event.getX();
        mMovingY = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Point point = checkTouchWitinCircle();
                if (point !=null){
                    mIsTouchPoint = true;
                    if (!mSelectedPoints.contains(point)){
                        mSelectedPoints.add(point);
                    }
                    point.setStatusPressed();
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsTouchPoint) {
                    Point point2 = checkTouchWitinCircle();
                    if (point2 != null) {
                        if (!mSelectedPoints.contains(point2)) {
                            mSelectedPoints.add(point2);
                        }
                        point2.setStatusPressed();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsTouchPoint = false;
                String str="";
                //回调密码获取监听
                for (Point point3 : mSelectedPoints) {
                    int index = point3.index+1;
                    str += index;
                }
                Log.e("TAG", str);
                if(str.equals("1235789")){
                    Log.e("TAG","密码正确");
                    if (callBack!=null){
                        callBack.onPwd ("恭喜你,密码正确: "+str);
                    }
                }else{
                    for (Point point4 :mSelectedPoints) {
                        point4.setStatusError();
                    }
                    if (callBack!=null){
                        callBack.onPwd ("抱歉,密码"+str+"错误,点击重试!");
                    }
                }
                break;
        }
        postInvalidate();
        return true;
    }


    private Point  checkTouchWitinCircle(){
        for (int i =0 ;i<3 ;i++) {
            for (Point point : mPoints[i]) {
                // for 循环九个点，判断手指位置是否在这个九个点里面
                if (MathUtil.checkInRound(point .centerX, point.centerY, mDotRadius,mMovingX, mMovingY)){
                    return point;
                }
            }
        }
        return null;
    }

    private void drawShow(Canvas canvas) {
        for (int i=0;i<3;i++){
            Point[] points = mPoints[i];
            for (Point point: points) {
                if (point.statusIsNormal()) { //正常状态
                    mNormalPaint.setColor(mOuterNormalColor);
                    canvas.drawCircle(point.centerX, point.centerY, mDotRadius, mNormalPaint);
                    mNormalPaint.setColor(mInnerNormalColor);
                    canvas.drawCircle(point.centerX, point.centerY, mDotRadius / 6, mNormalPaint);
                }else if (point.statusIsPressed()){ //按下状态
                    mNormalPaint.setColor(mOuterPressedColor);
                    canvas.drawCircle(point.centerX, point.centerY, mDotRadius, mNormalPaint);
                    mNormalPaint.setColor(mInnerPressedColor);
                    canvas.drawCircle(point.centerX, point.centerY, mDotRadius / 6, mNormalPaint);
                }else if (point.statusIsError()){ //错误状态
                    mNormalPaint.setColor(mOuterErrorColor);
                    canvas.drawCircle(point.centerX, point.centerY, mDotRadius, mNormalPaint);
                    mNormalPaint.setColor(mInnerErrorColor);
                    canvas.drawCircle(point.centerX, point.centerY, mDotRadius / 6, mNormalPaint);
                }
            }
        }
        //绘制两个点之间的连线以及箭头
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        if (mSelectedPoints.size() >= 1){
            Point lastPoint = mSelectedPoints .get(0);
            for (int i=1;i<mSelectedPoints.size();i++){
                drawLine(lastPoint, mSelectedPoints.get(i), canvas, mLinePaint);
                drawArrow(canvas, mArrowPaint , lastPoint, mSelectedPoints.get(i), (mDotRadius / 5), 38);
                lastPoint = mSelectedPoints.get(i);
            }

            // 绘制最后一个点到手指当前位置的连线
            // 如果手指在内圆里面就不要绘制
            boolean isInnerPoint = MathUtil.checkInRound(lastPoint.centerX , lastPoint.centerY ,
                    mDotRadius / 4, mMovingX, mMovingY);
            if (!isInnerPoint && mIsTouchPoint) {
                drawLine(lastPoint,new  Point( mMovingX ,  mMovingY , -1), canvas, mLinePaint);
            }
        }
    }

    private void drawArrow(Canvas canvas, Paint paint, Point start, Point end, float  arrowHeight, int angle) {

        float d = (float) MathUtil.distance(start.centerX, start.centerY, end.centerX, end.centerY);
        float sin_B = ((end.centerX - start.centerX) / d) ;
        float cos_B = ((end.centerY - start.centerY) / d) ;
        float tan_A = (float) Math.tan(Math.toRadians(angle ));;
        float h = (float) (d - arrowHeight  - mDotRadius * 1.1);
        float l = arrowHeight * tan_A;
        float a = l * sin_B;
        float b = l * cos_B;
        float x0 = h * sin_B;
        float y0 = h * cos_B;
        float x1 = start.centerX + (h + arrowHeight) * sin_B;
        float y1 = start.centerY + (h + arrowHeight) * cos_B;
        float x2 = start.centerX + x0 - b;
        float y2 = start.centerY  + y0 + a;
        float x3 = start.centerX  + x0 + b;
        float y3 = start.centerY + y0 - a;
        Path path =new Path();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawLine(Point start, Point end, Canvas canvas, Paint paint) {

        double pointDistance = MathUtil.distance(start.centerX , start.centerY , end.centerX , end.centerY );
        float  dx = end.centerX - start.centerX;
        float  dy = end.centerY - start.centerY;

        float rx = (float) (dx / pointDistance * (mDotRadius / 6.0));
        float ry = (float) (dy / pointDistance * (mDotRadius / 6.0));

        canvas.drawLine(start.centerX + rx, start.centerY + ry, end.centerX - rx, end.centerY - ry, paint);

    }

    private void initDot() {

        int width = this.getWidth();
        int height = this.getHeight();
        int squareWidth = width / 3;//每行显示三个圆

        //兼容横竖屏
        int offsetX = 0;
        int offsetY = 0;

        if (height>width){
           offsetY =  (height-width)/2;
           width = height;
        }else {
            offsetX  = ( width - height)/2;
            height = width;
        }
        mDotRadius = width / 18;
        mPoints[0][0] = new Point(offsetX + squareWidth    / 2, offsetY + squareWidth / 2, 0);
        mPoints[0][1] = new Point(offsetX + squareWidth * 3/ 2, offsetY + squareWidth / 2, 1);
        mPoints[0][2] = new Point(offsetX + squareWidth * 5/ 2, offsetY + squareWidth / 2, 2);
        mPoints[1][0] = new Point(offsetX + squareWidth    / 2, offsetY + squareWidth *3/ 2, 3);
        mPoints[1][1] = new Point(offsetX + squareWidth * 3/ 2, offsetY + squareWidth *3/ 2, 4);
        mPoints[1][2] = new Point(offsetX + squareWidth * 5/ 2, offsetY + squareWidth *3/ 2, 5);
        mPoints[2][0] = new Point(offsetX + squareWidth    / 2, offsetY + squareWidth *5/ 2, 6);
        mPoints[2][1] = new Point(offsetX + squareWidth *3 / 2, offsetY + squareWidth *5/ 2, 7);
        mPoints[2][2] = new Point(offsetX + squareWidth * 5/ 2, offsetY + squareWidth *5/ 2, 8);
    }

    private void initPaint() {
        // new Paint 对象 ，设置 paint 颜色
        // 线的画笔
        mLinePaint = new Paint();
        mLinePaint.setColor(mInnerPressedColor);
        mLinePaint.setStyle( Paint.Style.STROKE);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(mDotRadius / 9);

        mPressedPaint = new Paint();
        mPressedPaint.setStyle( Paint.Style.STROKE);
        mPressedPaint.setAntiAlias(true);
        mPressedPaint.setStrokeWidth(mDotRadius / 6);

        mErrorPaint = new Paint();
        mErrorPaint.setStyle( Paint.Style.STROKE);
        mErrorPaint.setAntiAlias(true);
        mErrorPaint.setStrokeWidth(mDotRadius / 6);

        mNormalPaint = new Paint();
        mNormalPaint.setStyle( Paint.Style.STROKE);
        mNormalPaint.setAntiAlias(true);
        mNormalPaint.setStrokeWidth(mDotRadius / 6);

        mArrowPaint = new Paint();
        mArrowPaint.setColor(mInnerPressedColor);
        mArrowPaint.setStyle( Paint.Style.FILL);
        mArrowPaint.setAntiAlias(true);
    }

    /**
     * 宫格的类
     */
    class Point {

        private float centerX ;
        private float centerY ;
        public int index ;

        private int STATUS_NORMAL = 1;
        private int STATUS_PRESSED = 2;
        private int STATUS_ERROR = 3;
        //当前点的状态，有三种状态
        private int status = STATUS_NORMAL;


        public Point( float centerX , float centerY , int index) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.index = index;
        }

        void setStatusPressed() {
            status = STATUS_PRESSED;
        }

        void setStatusNormal() {
            status = STATUS_NORMAL;
        }

        void setStatusError() {
            status = STATUS_ERROR;
        }

        boolean statusIsPressed(){
            return status == STATUS_PRESSED;
        }

        boolean statusIsError() {
            return status == STATUS_ERROR;
        }

        boolean statusIsNormal()  {
            return status == STATUS_NORMAL;
        }
    }


    public void reset(){
        for (Point point4 :mSelectedPoints) {
            point4.setStatusNormal();
        }
        mSelectedPoints.clear();
        postInvalidate();
    }


    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    CallBack callBack;
    public interface CallBack{
        void onPwd(String pwd);
    }
}
