package cn.edu.xjtu.customviews.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 心形贝塞尔曲线
 * Created by Mg on 2017/9/1.
 */

public class HeartBezier extends View {
    // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置
    private static final double C = 0.551915024494f;

    private Paint paint, coordinatePaint;

    private int centerX, centerY;
    // 圆的半径
    private float radius = 200;

    // 圆形的控制点和数据点的差值
    private float difference = (float) (radius * C);

    // 顺时针记录绘制圆形的四个数据点
    private float[] data = new float[8];
    // 顺时针记录绘制圆形的8个控制点
    private float[] controls = new float[16];

    // 动画的时长
    private float duration = 1000;

    // 当前已进行时长
    private float current = 0;

    // 将时长划分的份数
    private float count = 100;

    // 每一份的时长
    private float piece = duration / count;

    private Path path;

    public HeartBezier(Context context) {
        super(context, null);
    }

    public HeartBezier(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xffFFCCFF);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(8);

        coordinatePaint = new Paint();
        coordinatePaint.setColor(0xffFF99FF);
        coordinatePaint.setStyle(Paint.Style.STROKE);
        coordinatePaint.setStrokeWidth(5);
        coordinatePaint.setAntiAlias(true);

        // 初始化数据点
        data[0] = 0;
        data[1] = radius;

        data[2] = radius;
        data[3] = 0;

        data[4] = 0;
        data[5] = -radius;

        data[6] = -radius;
        data[7] = 0;
        // 初始化控制点
        controls[0] = data[0] + difference;
        controls[1] = data[1];

        controls[2] = data[2];
        controls[3] = data[3] + difference;

        controls[4]  = data[2];
        controls[5]  = data[3] - difference;

        controls[6]  = data[4] + difference;
        controls[7]  = data[5];

        controls[8]  = data[4] - difference;
        controls[9]  = data[5];

        controls[10] = data[6];
        controls[11] = data[7] - difference;

        controls[12] = data[6];
        controls[13] = data[7] + difference;

        controls[14] = data[0] - difference;
        controls[15] = data[1];

        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w /2;
        centerY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinateSystem(canvas);

        /*canvas.translate(centerX, centerY);
        canvas.scale(1, -1);*/

        drawAuxiliaryLine(canvas);

        // 绘制贝塞尔曲线
        paint.setColor(0xffFFCCFF);
        paint.setStrokeWidth(8);

        path.reset();
        path.moveTo(data[0], data[1]);

        path.cubicTo(controls[0], controls[1], controls[2], controls[3], data[2], data[3]);
        path.cubicTo(controls[4], controls[5], controls[6], controls[7], data[4], data[5]);
        path.cubicTo(controls[8], controls[9], controls[10], controls[11], data[6], data[7]);
        path.cubicTo(controls[12], controls[13], controls[14], controls[15], data[0], data[1]);

        canvas.drawPath(path, paint);

        current += piece;
        if (current < duration){
            data[1] -= 120 / count;
            controls[7] += 80 / count;
            controls[9] += 80 / count;

            controls[4] -= 20 / count;
            controls[10] += 20 / count;

            postInvalidateDelayed((long) piece);
        }
    }

    /**
     * 绘制辅助线
     * @param canvas 画布
     */
    private void drawAuxiliaryLine(Canvas canvas){
        // 绘制数据点和控制点
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(20);

        for (int i = 0; i < 8; i+=2){
            canvas.drawPoint(data[i], data[i + 1], paint);
        }

        for (int i = 0; i < 16; i += 2){
            canvas.drawPoint(controls[i], controls[i + 1], paint);
        }

        // 绘制辅助线
        paint.setStrokeWidth(4);

        for (int i = 2, j = 2; i < 8; i += 2, j += 4){
            canvas.drawLine(data[i], data[i + 1], controls[j], controls[j + 1], paint);
            canvas.drawLine(data[i], data[i + 1], controls[j + 2], controls[j + 3], paint);
        }

        canvas.drawLine(data[0], data[1], controls[0], controls[1], paint);
        canvas.drawLine(data[0], data[1], controls[14], controls[15], paint);
    }

    /**
     * 绘制坐标系
     * @param canvas 画布
     */
    private void drawCoordinateSystem(Canvas canvas){
       // canvas.save();
        // 将坐标系移动到画布中央
        canvas.translate(centerX, centerY);
        canvas.scale(1, -1);

        canvas.drawLine(0, -2000, 0, 2000, coordinatePaint);
        canvas.drawLine(-2000, 0, 2000, 0, coordinatePaint);

        // canvas.restore();

    }
}
