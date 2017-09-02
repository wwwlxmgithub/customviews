package cn.edu.xjtu.customviews.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 二阶贝塞尔曲线
 * Created by Mg on 2017/9/1.
 */

public class QuadBezier  extends View {
    private Paint paint;

    private int centerX, centerY;

    private PointF start, end, control;

    private Path path;

    public QuadBezier(Context context) {
        super(context, null);
    }

    public QuadBezier(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(8);

        start = new PointF(0, 0);
        end = new PointF(0, 0);

        control = new PointF(0, 0);

        path = new Path();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;

        // 初始化数据点和控制点的位置
        start.x = centerX - 200;
        start.y = centerY;

        end.x = centerX + 200;
        end.y = centerY;

        control.x = centerX + 300;
        control.y = centerY + 300;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        control.x = event.getX();
        control.y = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // canvas.translate(centerX, centerY);
        // 绘制数据点和控制点
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(20);
        canvas.drawPoint(start.x, start.y, paint);
        canvas.drawPoint(end.x, end.y, paint);
        canvas.drawPoint(control.x, control.y, paint);

        // 绘制辅助线
        paint.setStrokeWidth(8);
        canvas.drawLine(start.x, start.y, control.x, control.y, paint);
        canvas.drawLine(end.x, end.y, control.x, control.y, paint);

        paint.setColor(Color.LTGRAY);
        // 绘制贝塞尔曲线
        path.reset();
        path.moveTo(start.x, start.y);
        path.quadTo(control.x, control.y, end.x, end.y);
        paint.setColor(Color.DKGRAY);
        canvas.drawPath(path, paint);
    }
}
