package cn.edu.xjtu.customviews.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.MotionEvent;

import java.util.Arrays;

import cn.edu.xjtu.customviews.R;
import cn.edu.xjtu.customviews.util.Utils;
import cn.edu.xjtu.customviews.view.CustomView;

/**
 * 使用Region
 */
public class RegionTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_test);

        setTitle("Region初探");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RemoteControlMenu menu = (RemoteControlMenu) findViewById(R.id.remoteControlMenu);
        menu.setListener(new RemoteControlMenu.MenuListener() {
            @Override
            public void onCenterClicked() {
                //Utils.showToast(RegionTestActivity.this, "onCenterClicked");
            }

            @Override
            public void onUpClicked() {
                //Utils.showToast(RegionTestActivity.this, "onUpClicked");
            }

            @Override
            public void onRightClicked() {
               // Utils.showToast(RegionTestActivity.this, "onRightClicked");
            }

            @Override
            public void onDownClicked() {
               // Utils.showToast(RegionTestActivity.this, "onDownClicked");
            }

            @Override
            public void onLeftClicked() {
                // Utils.showToast(RegionTestActivity.this, "onLeftClicked");
            }
        });
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

class RemoteControlMenu extends CustomView{
    /**
     * 上下左右中五个按键的path
     */
    private Path upPath, downPath, leftPath, rightPath, centerPath;
    /**
     * 上下左右中五个按键的Region
     */
    private Region upRegion, downRegion, leftRegion, rightRegion, centerRegion;

    private Matrix mapMatrix = null;

    final int CENTER = 0, UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4;
    int touchFlag = -1, currentFlag = -1;

    MenuListener listener;
    /**
     * 默认按钮的颜色
     */
    int defautColor = 0xFF4E5268;
    /**
     * 按钮选中后的颜色
     */
    int touchedColor = 0xFFDF9C81;

    public void setListener(MenuListener listener) {
        this.listener = listener;
    }

    public RemoteControlMenu(Context context) {
        super(context);
        init();
    }

    private void init() {
        upPath = new Path();
        downPath = new Path();
        leftPath = new Path();
        rightPath = new Path();
        centerPath = new Path();

        upRegion = new Region();
        downRegion = new Region();
        leftRegion = new Region();
        rightRegion = new Region();
        centerRegion = new Region();

        defaultPaint.setColor(defautColor);
        defaultPaint.setAntiAlias(true);

        mapMatrix = new Matrix();
    }

    public RemoteControlMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init();

    }

    public RemoteControlMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mapMatrix.reset();

        Region globalRegion = new Region(-w, -h, w, h);
        int minWidth = w > h ? h : w;
        minWidth *= 0.8;

        int bc = minWidth / 2;
        RectF bigCircle = new RectF(-bc, -bc, bc, bc);

        int sc = minWidth / 4;
        RectF smallCircle = new RectF(-sc, -sc, sc, sc);

        float bigSweepAngle = 84;
        float smallSweepAngle = -80;

        // 根据视图大小，初始化Path和Region
        centerPath.addCircle(0, 0, 0.2f * minWidth, Path.Direction.CW);
        centerRegion.setPath(centerPath, globalRegion);

        rightPath.addArc(bigCircle, -40, bigSweepAngle);
        rightPath.arcTo(smallCircle, 40, smallSweepAngle);
        rightPath.close();
        rightRegion.setPath(rightPath, globalRegion);

        upPath.addArc(bigCircle, 230, bigSweepAngle);
        upPath.arcTo(smallCircle, 310, smallSweepAngle);
        upPath.close();
        upRegion.setPath(upPath, globalRegion);

        leftPath.addArc(bigCircle, 140, bigSweepAngle);
        leftPath.arcTo(smallCircle, 220, smallSweepAngle);
        leftPath.close();
        leftRegion.setPath(leftPath, globalRegion);

        downPath.addArc(bigCircle, 50, bigSweepAngle);
        downPath.arcTo(smallCircle, 130, smallSweepAngle);
        downPath.close();
        downRegion.setPath(downPath, globalRegion);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float[] points = new float[2];
        points[0] = event.getX();
        points[1] = event.getY();

        mapMatrix.mapPoints(points);

        int x = (int) points[0];
        int y = (int) points[1];

        System.out.println("x : " + x + ", y : " + y);

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                touchFlag = getTouchedPath(x, y);
                currentFlag = touchFlag;
                break;
            case MotionEvent.ACTION_MOVE:
                currentFlag = getTouchedPath(x, y);
                break;
            case MotionEvent.ACTION_UP:
                currentFlag = getTouchedPath(x, y);
                // 如果手指按下区域和抬起区域相同且不为空，则判断点击事件
                if (currentFlag == touchFlag && currentFlag != -1 && listener != null){
                    switch (currentFlag){
                        case CENTER:
                            listener.onCenterClicked();
                            break;
                        case LEFT:
                            listener.onLeftClicked();
                            break;
                        case RIGHT:
                            listener.onRightClicked();
                            break;
                        case DOWN:
                            listener.onDownClicked();
                            break;
                        case UP:
                            listener.onUpClicked();
                            break;
                    }
                }
                touchFlag = currentFlag = -1;
                break;
            case MotionEvent.ACTION_CANCEL:
                touchFlag = currentFlag = -1;
                break;
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(viewWidth / 2, viewHeight / 2);

        // 获得测量矩阵(逆矩阵)
        if (mapMatrix.isIdentity()){
            canvas.getMatrix().invert(mapMatrix);
        }

        // 绘制默认颜色
        canvas.drawPath(centerPath, defaultPaint);
        canvas.drawPath(rightPath, defaultPaint);
        canvas.drawPath(leftPath, defaultPaint);
        canvas.drawPath(downPath, defaultPaint);
        canvas.drawPath(upPath, defaultPaint);

        // 绘制触摸区域的颜色
        defaultPaint.setColor(touchedColor);
        switch (currentFlag){
                case CENTER:
                    canvas.drawPath(centerPath, defaultPaint);
                    break;
                case LEFT:
                    canvas.drawPath(leftPath, defaultPaint);
                    break;
                case RIGHT:
                    canvas.drawPath(rightPath, defaultPaint);
                    break;
                case DOWN:
                    canvas.drawPath(downPath, defaultPaint);
                    break;
                case UP:
                    canvas.drawPath(upPath, defaultPaint);
                    break;
        }
        canvas.drawCircle(0, 0, 12, defaultPaint);
        defaultPaint.setColor(defautColor);

    }

    // 获取当前触摸点在哪个区域
    int getTouchedPath(int x, int y) {
        if (centerRegion.contains(x, y)) {
            System.out.println("center : ");
            return CENTER;
        } else if (upRegion.contains(x, y)) {
            System.out.println("up : ");
            return UP;
        } else if (rightRegion.contains(x, y)) {
            System.out.println("right : ");
            return RIGHT;
        } else if (downRegion.contains(x, y)) {
            System.out.println("down : " );
            return DOWN;
        } else if (leftRegion.contains(x, y)) {
            System.out.println("left : ");
            return LEFT;
        }
        return -1;
    }

    interface MenuListener {
        void onCenterClicked();

        void onUpClicked();

        void onRightClicked();

        void onDownClicked();

        void onLeftClicked();

    }
}
