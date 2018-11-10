package ca.com.androidcustomviews.customviews.lockview;

/**
 * Created by wscjw on 2018/11/10.
 */

public class MathUtil {

    //判断一个点是不是在圆内
    public  static  boolean checkInRound(float circleX,float  circleY, float r,float  touchX, float touchY){
        return Math.sqrt(Math.pow((circleX-touchX),2)+Math.pow((circleY-touchY),2))< r;
    }


    //连点之间的距离
   public static double distance(double x1 , double y1 , double x2 , double y2 )  {
        return Math.sqrt(Math.abs(x1 - x2) * Math.abs(x1 - x2) + Math.abs(y1 - y2) * Math.abs(y1 - y2));
    }
}
