package cn.edu.xjtu.customviews.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.edu.xjtu.customviews.R;

/**
 * 旋转的箭头
 * Created by Mg on 2017/9/2.
 */

public class TurningArrow extends View {

    // 用于记录当前的位置，取值范围[0, 1] 映射path的整个长度
    private float currentValue = 0;

    // 当前点的实际位置
    private float[] pos;
    // 当前点的tangent值，用于计算图片所需旋转的角度
    private float[] tan;
    // 箭头图片
    private Bitmap arrow;
    // 矩阵，用于对图片的一些操作
    private Matrix matrix;

    private int width, height;

    private Paint paint;

    public TurningArrow(Context context) {
        super(context, null);
    }

    public TurningArrow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        pos = new float[2];
        tan = new float[2];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 10; // 缩放图片
        arrow = BitmapFactory.decodeResource(getResources(), R.drawable.arrow, options);
        matrix = new Matrix();

        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
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

        Path path = new Path();
        path.addCircle(0, 0, 200, Path.Direction.CW);

        PathMeasure measure = new PathMeasure(path, false);

        currentValue += 0.005;
        if (currentValue >= 1){
            currentValue = 0;
        }

        measure.getPosTan(currentValue * measure.getLength(), pos, tan);

        matrix.reset();

        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);

        matrix.postRotate(degrees, arrow.getWidth() / 2, arrow.getHeight() / 2);
        matrix.postTranslate(pos[0] - arrow.getWidth() / 2, pos[1] - arrow.getHeight() / 2);

        canvas.drawBitmap(arrow, matrix, paint);
        canvas.drawPath(path, paint);

        invalidate();
    }
}
