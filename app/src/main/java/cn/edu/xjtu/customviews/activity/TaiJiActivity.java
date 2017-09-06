package cn.edu.xjtu.customviews.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import cn.edu.xjtu.customviews.R;

public class TaiJiActivity extends AppCompatActivity {

    private TaiJi taiji;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_ji);
        setTitle("TaiJi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        taiji = (TaiJi) findViewById(R.id.taijiView);
        Handler handler = new Handler(){
            private float degrees = 0;
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                taiji.setDegrees(degrees ++);
                this.sendEmptyMessageDelayed(0, 30);
            }
        };
        handler.sendEmptyMessageDelayed(0, 30);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class TaiJi extends View{
    private Paint whitePaint, blackPaint;
    private float degrees = 0;

    public void setDegrees(float degrees) {
        this.degrees = degrees;
        invalidate();
    }

    public TaiJi(Context context) {
        super(context);
        init();
    }

    public TaiJi(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setAntiAlias(true);

        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        canvas.translate(width / 2, height / 2);

        canvas.drawColor(Color.LTGRAY);
        canvas.rotate(degrees);

        // 绘制两个半圆
        int radius = Math.min(width, height) / 2 - 100;
        RectF rect  = new RectF(-radius, -radius, radius, radius);
        canvas.drawArc(rect, 90, 180, true, blackPaint);
        canvas.drawArc(rect, -90, 180, true, whitePaint);

        // 绘制两个小圆
        int smallRadius = radius / 2;
        canvas.drawCircle(0, -smallRadius, smallRadius, blackPaint);
        canvas.drawCircle(0, smallRadius, smallRadius, whitePaint);

        // 绘制两个鱼眼
        canvas.drawCircle(0, -smallRadius, smallRadius / 4, whitePaint);
        canvas.drawCircle(0, smallRadius, smallRadius / 4, blackPaint);
    }
}
