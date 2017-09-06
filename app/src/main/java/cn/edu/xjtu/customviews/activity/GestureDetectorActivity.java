package cn.edu.xjtu.customviews.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.xjtu.customviews.R;

public class GestureDetectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_detector);
        setTitle("GestureDetector");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

/**
 * 可以拖动的球
 */
class FallingBall extends View{

    private int width, height;
    /**
     * 小方块开始位置的XY
     */
    private float startX = 0, startY = 0;
    /**
     * 边长
     */
    private float edgeLength = 200;

    private RectF rect = new RectF(startX, startY, startX + edgeLength, startY + edgeLength);

    /**
     * 修正距离XY
     */
    private float fixedX = 0, fixedY = 0;

    private Paint paint;

    private GestureDetector detector;
    /**
     * 是否可以拖动
     */
    private boolean canFall = false;

    /**
     * 速度 px/s
     */
    private float speedX = 0, speedY = 0;

    private boolean xFixed = false, yFixed = false;

    /**
     * 每100ms更新一次
     */
    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 刷新内容
            startX = startX + speedX / 30;
            startY = startY + speedY / 30;

            speedX *= 0.97;
            speedY *= 0.97;
            if (Math.abs(speedX) < 10){
                speedX = 0;
            }
            if (Math.abs(speedY) < 10){
                speedY = 0;
            }
            if (refreshRectByCurrentPoint()){
                // 转向
                if (xFixed){
                    speedX = -speedX;
                }
                if (yFixed){
                    speedY = -speedY;
                }
            }
            invalidate();
            if (speedX == 0 && speedY == 0){
                handler.removeCallbacks(this);
                return;
            }
            handler.postDelayed(this, 33);
        }
    };
    private GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (!canFall){
                return false;
            }
            speedX = velocityX;
            speedY = velocityY;
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, 0);

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                if(contains(event.getX(), event.getY())){
                    canFall = true;
                    fixedX = event.getX() - startX;
                    fixedY = event.getY() - startY;
                    speedX = speedY = 0;
                }else {
                    canFall = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (canFall){
                    startX = event.getX() - fixedX;
                    startY = event.getY() - fixedY;
                    if (refreshRectByCurrentPoint()){
                        fixedX = event.getX() - startX;
                        fixedY = event.getY() - startY;
                    }
                }
                invalidate();
                break;
        }
        return true;
    }

    private Bitmap bitmap;
    public FallingBall(Context context) {
        super(context);
        init(context);
    }

    public FallingBall(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        detector = new GestureDetector(context, listener);

        paint = new Paint();
        paint.setColor(Color.BLACK);

        paint.setAntiAlias(true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        startX = (w - edgeLength) / 2;
        startY = (h - edgeLength) / 2;
        refreshRectByCurrentPoint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(rect, paint);
        canvas.drawBitmap(bitmap, new Rect(0 ,0, bitmap.getWidth(), bitmap.getHeight()), rect, paint);
    }

    private boolean contains(float x, float y){
        float redius = edgeLength / 2;
        float centerX = rect.left + redius;
        float centerY = rect.top + redius;
        return Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)) <= redius;
    }

    /**
     * 刷新方块位置
     * @return true 表示修正过位置，false表示没有修正过位置
     */
    private boolean refreshRectByCurrentPoint() {
        boolean fixed = false;
        xFixed = yFixed = false;
        // 修正坐标
        if (startX < 0){
            startX = 0;
            fixed = xFixed = true;
        }
        if (startY < 0){
            startY = 0;
            fixed = yFixed = true;
        }

        if (startX + edgeLength > width){
            startX = width - edgeLength;
            fixed = xFixed = true;
        }

        if (startY + edgeLength > height){
            startY = height - edgeLength;
            fixed = yFixed = true;
        }
        rect.left = startX;
        rect.right = startX + edgeLength;
        rect.top = startY;
        rect.bottom = startY + edgeLength;
        return fixed;
    }
}
