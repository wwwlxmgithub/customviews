package cn.edu.xjtu.customviews.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.MotionEvent;

import cn.edu.xjtu.customviews.R;
import cn.edu.xjtu.customviews.view.CustomView;

public class DragViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_view);

        setTitle("DragView");
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

class DragView extends CustomView{

    private Bitmap bitmap; // 图片

    private RectF bitmapRectF; // 图片所在区域

    Matrix bitmapMatrix; // 控制图片的matrix

    boolean canDrag = false;

    PointF lastPoint = new PointF(0, 0);

    public DragView(Context context) {
        super(context);
        init();
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outHeight = 800 / 2;
        options.outWidth = 800 / 2;

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        bitmapRectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());

        bitmapMatrix = new Matrix();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                // 判断是否是第一个手指 && 是否包含在图片区域内
                if (event.getPointerId(event.getActionIndex()) == 0 && bitmapRectF.contains(event.getX(), event.getY())){
                    canDrag = true;
                    lastPoint.set(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                // 判断是否是第一个手指
                if(event.getPointerId(event.getActionIndex()) == 0){
                    canDrag = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果存在第一个手指，且这个手指的落点在图片的区域内
                if (canDrag){
                    // 注意getX（）和getY（）
                    int index = event.findPointerIndex(0);
                    bitmapMatrix.postTranslate(event.getX() - lastPoint.x, event.getY() - lastPoint.y);
                    lastPoint.set(event.getX(index), event.getY(index));

                    bitmapRectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());

                    bitmapMatrix.mapRect(bitmapRectF);
                    invalidate();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,  bitmapMatrix, defaultPaint);
    }
}