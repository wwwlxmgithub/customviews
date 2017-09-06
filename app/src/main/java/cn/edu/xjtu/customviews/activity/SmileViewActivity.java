package cn.edu.xjtu.customviews.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import cn.edu.xjtu.customviews.R;

public class SmileViewActivity extends AppCompatActivity {

    private SmileView smileView;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smile_view);

        smileView = (SmileView) findViewById(R.id.smileView);

        Handler handler = new Handler(){
            private float degrees = 0;
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                smileView.setDegrees(degrees++);
                this.sendEmptyMessageDelayed(0, 30);
            }
        };
        handler.sendEmptyMessageDelayed(0, 20);
    }
}
class SmileView extends View{
    private Paint paint;
    private int width, height;
    private Path smilePath, dest;

    private PathMeasure pathMeasure;

    private int radius;
    private float currentValue = 0;

    private ValueAnimator animator;

    private float degrees = 0;

    public void setDegrees(float degrees) {
        this.degrees = degrees;
        invalidate();
    }

    public SmileView(Context context) {
        super(context);
        init();
    }

    public SmileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0xFF4EA0E6);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);

        dest = new Path();
        smilePath = new Path();
        radius = 50;
        RectF rect = new RectF(-radius, -radius, radius, radius);
        smilePath.addArc(rect, 1, 180);

        smilePath.addArc(rect, 235, 1);
        smilePath.addArc(rect, -56, 1);

        pathMeasure = new PathMeasure();
        pathMeasure.setPath(smilePath, false);

        animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValue = (float) animation.getAnimatedValue();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);
        canvas.rotate(degrees);

        canvas.drawPath(smilePath, paint);
    }
}
