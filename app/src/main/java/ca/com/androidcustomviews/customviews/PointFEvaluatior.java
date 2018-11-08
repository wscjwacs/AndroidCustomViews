package ca.com.androidcustomviews.customviews;

import android.animation.TypeEvaluator;
import android.graphics.PointF;
import android.util.TypedValue;

/**
 * Created by hp on 2018/7/17.
 */

public class PointFEvaluatior implements TypeEvaluator<PointF> {
    private PointF P1,P2;// Pon p0 和 p3 可以直接计算出来，通过构造方法传出来

    public PointFEvaluatior(PointF p1,PointF p2) {
        this.P1 = p1;
        this.P2 = p2;
    }


    /**
     * 根据贝塞尔曲线公式，计算出曲线上面的点的坐标，即图片移动的轨迹
     * @param t
     * @param P0
     * @param P3
     * @return
     */
    @Override
    public PointF evaluate(float t, PointF P0, PointF P3) {
        PointF  pointF = new PointF();
        pointF.x = P0.x * (1-t) * (1-t) * (1-t)
                + 3 * P1.x * t * (1-t) * (1-t)
                + 3 * P2.x * t * t * (1-t)
                + P3.x * t * t * t;

        pointF.y = P0.y * (1-t) * (1-t) * (1-t)
                + 3 * P1.y * t * (1-t) * (1-t)
                + 3 * P2.y * t * t * (1-t)
                + P3.y * t * t * t;
        return pointF;
    }
}
