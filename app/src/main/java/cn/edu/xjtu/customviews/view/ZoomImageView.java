package cn.edu.xjtu.customviews.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

/**
 *
 * Created by Mg on 2017/9/3.
 */

public class ZoomImageView extends android.support.v7.widget.AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener,
        View.OnTouchListener{
    private boolean once = false;
    /**
     * 初始时缩放的值
     */
    private float initScale;
    /**
     * 双击放大时缩放的值
     */
    private float midScale;
    /**
     * 放大的最大值
     */
    private float maxScale;

    private Matrix scaleMatrix;
    /**
     * 捕获用户多指触控时缩放的比例
     */
    private ScaleGestureDetector scaleGestureDetector;

    // =============自由移动的变量
    /**
     * 记录上一次多点触控的数量
     */
    private int lastPointCount;
    /**
     * 上一次触控的中心点
     */
    private float lastX, lastY;

    private int touchSlop;
    private boolean isCanDrag;

    private RectF matrixRectF;

    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;

    // =============双击放大缩小
    private GestureDetector gestureDetector;

    private boolean isAutoScale;
    /**
     * 自动缩放
     */
    private class AutoScaleRunnable implements Runnable{
        /**
         * 缩放目标值
         */
        private float targetScale;
        /**
         * 缩放中心
         */
        private float x, y;

        private final float BIGGER = 1.07f;

        private final float SMALLER = 0.93f;

        private float tmpScale;

        public AutoScaleRunnable(float targetScale, float x, float y) {
            this.targetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getScale() < targetScale){
                tmpScale = BIGGER;
            }
            if (getScale() > targetScale){
                tmpScale = SMALLER;
            }
        }

        @Override
        public void run() {
            // 进行缩放
            scaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(scaleMatrix);

            float currentScale = getScale();
            if ((tmpScale > 1.0f  && currentScale < targetScale)
                    || (tmpScale < 1.0f && currentScale > targetScale)){
                postDelayed(this, 16);
            }else {
                // 达到或大于目标值
                float scale = targetScale / currentScale;
                scaleMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(scaleMatrix);

                isAutoScale = false;
            }
        }
    }
    public ZoomImageView(Context context) {
        super(context, null);
        init(context);
    }

    private void init(Context context) {
        scaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {

                if (isAutoScale){
                    return  true;
                }

                float x = e.getX();
                float y = e.getY();

                if (getScale() < midScale){
                    /*scaleMatrix.postScale(midScale / getScale(),
                            midScale / getScale(), x, y);
                    setImageMatrix(scaleMatrix);*/

                    postDelayed(new AutoScaleRunnable(midScale, x, y), 16);

                    isAutoScale = true;
                }else {
                    /*scaleMatrix.postScale(initScale / getScale(),
                            initScale / getScale(), x, y);
                    setImageMatrix(scaleMatrix);*/

                    postDelayed(new AutoScaleRunnable(initScale, x, y), 16);
                    isAutoScale = true;
                }

                return true;
            }
        });
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init(context);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 注册接口
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 移除接口
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    /**
     * 获取ImageView加载完成的图片
     */
    @Override
    public void onGlobalLayout() {
        if (!once){
            // 得到控件的宽和高
            int width = getWidth();
            int height = getHeight();
            //得到图片，以及宽和高
            Drawable drawable = getDrawable();
            if (drawable == null){
                return;
            }
            int dw = drawable.getIntrinsicWidth();
            int dh = drawable.getIntrinsicHeight();
            float scale = 1.0f;
            /**
             * 如果图片的宽度大于控件的宽度，但是图片的高度小于控件的高度，我们将宽度缩小
             */
            if (dw > width && dh < height){
                scale = width * 1.0f / dw;
            }else if (dh > height && dw < width){
                /**
                 * 如果图片的高度大于控件的高度，但是图片的宽度小于控件的高度，我们将高度缩小
                 */
                scale = height * 1.0f / dh;
            }else if ((dw < width && dh < height) || (dw > width && dh > height)){
                /**
                 * 如果图片的宽度和高度都小于或大于控件的宽度和高度，那么我们将取宽高缩放的最小值
                 */
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }
            /**
             * 得到了初始化时缩放的比例
             */
            initScale = scale;
            midScale = 2 * scale;
            maxScale = 4 * scale;

            // 将图片移动至控件的中心
            int dx = getWidth() / 2 - dw / 2;
            int dy = getHeight() / 2 - dh / 2;

            scaleMatrix.postTranslate(dx, dy);
            scaleMatrix.postScale(initScale, initScale, width / 2, height / 2);
            setImageMatrix(scaleMatrix);
            once = true;
        }
    }

    /**
     * 获取当前图片的缩放值
     * @return
     */
    public float getScale(){

        float[] values = new float[9];
        scaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 缩放区间：initScale，midScale，maxScale
     * @param detector
     * @return
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {

        float scale = getScale();

        float scaleFactor = detector.getScaleFactor();

        if (getDrawable() == null){
            return true;
        }
        // 缩放范围的控制
        if ((scale < maxScale && scaleFactor > 1.0f) || (scale > initScale &&scaleFactor < 1.0f)){
            if (scaleFactor * scale < initScale){
                scaleFactor = initScale / scale;
            }
            if (scaleFactor * scale > maxScale){
                scaleFactor = maxScale / scale;
            }
            scaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());

            checkBorderAndCenterWhenScale();
            setImageMatrix(scaleMatrix);
        }

        return true;
    }

    /**
     * 获得蹄片放大缩小以后的宽高以及top，bottom，left，right
     * @return
     */
    private RectF getMatrixRectF(){
    Matrix matrix = scaleMatrix;
    RectF rectf = new RectF();

    Drawable d = getDrawable();
    if (d != null){
        rectf.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        matrix.mapRect(rectf);
    }
    return rectf;
}
    /**
     * 在缩放的时候进行边界控制以及位置的控制
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rect = getMatrixRectF();

        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();
        /**
         * 缩放时进行边界检测，避免出现白边
         */
        if (rect.width() >= width){
            if (rect.left > 0){
                deltaX = -rect.left;
            }

            if(rect.right < width){
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height){
            if (rect.top > 0){
                deltaY = -rect.top;
            }
            if (rect.bottom < height){
                deltaY = height - rect.bottom;
            }
        }
        /**
         * 如果宽度或者高度小于控件的宽高，让其居中
         */
        if (rect.width() < width){
            deltaX = width / 2 - rect.right + rect.width() / 2;
        }

        if (rect.height() < height){
            deltaY = height / 2 - rect.bottom + rect.height() / 2;
        }

        scaleMatrix.postTranslate(deltaX, deltaY);

    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {

        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)){
            return true;
        }
        scaleGestureDetector.onTouchEvent(event);

        float x = 0, y = 0;
        /**
         * 拿到多点触控的数量
         */
        int count = event.getPointerCount();

        for (int i = 0; i < count; i++){
            x += event.getX();
            y += event.getY();
        }

        x /= count;
        y /= count;

        if (lastPointCount != count){
            isCanDrag = false;
            lastX = x;
            lastY = y;
        }
        lastPointCount = count;
        RectF rect = getMatrixRectF();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (rect.width() > getWidth() || rect.height() > getHeight()){
                   // 拦截父控件的触摸事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (rect.width() > getWidth() + 0.01 || rect.height() > getHeight() + 0.01){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                float dx = x - lastX;
                float dy = y - lastY;

                if (!isCanDrag){
                    isCanDrag = isMoveAction(dx, dy);
                }
                if (isCanDrag){
                    RectF rectF = getMatrixRectF();
                    if (getDrawable() != null){
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        // 如果宽度小于控件宽度，不许横向移动
                        if (rectF.width() < getWidth()){
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }

                        // 如果高度小于控件高度，不许纵向移动
                        if (rectF.height() < getHeight()){
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }

                        scaleMatrix.postTranslate(dx, dy);checkBorderWhenTranslate();
                        setImageMatrix(scaleMatrix);
                    }

                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastPointCount = 0;
                break;
        }
        return true;
    }

    /**
     * 当移动时进行边界检查
     */
    private void checkBorderWhenTranslate() {
        RectF rect = getMatrixRectF();
        float deltaX = 0, deltaY = 0;

        int width = getWidth();
        int height = getHeight();
        if (rect.top > 0 && isCheckTopAndBottom){
            deltaY = -rect.top;
        }

        if (rect.bottom < height && isCheckTopAndBottom){
            deltaY = height - rect.bottom;
        }

        if (rect.left > 0 && isCheckLeftAndRight){
            deltaX = -rect.left;
        }
        if (rect.right < width && isCheckLeftAndRight){
            deltaX = width - rect.right;
        }
        scaleMatrix.postTranslate(deltaX, deltaY);

    }

    /**
     * 判断是否是move
     * @param dx
     * @param dy
     * @return
     */
    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) > touchSlop;
    }
}
