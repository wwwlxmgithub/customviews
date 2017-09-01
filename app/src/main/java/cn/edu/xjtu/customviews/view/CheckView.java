package cn.edu.xjtu.customviews.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.edu.xjtu.customviews.R;

/**
 * checkView
 * Created by Mg on 2017/8/25.
 */

public class CheckView extends View {
    private static final int ANIM_NULL = 0;         //动画状态-没有
    private static final int ANIM_CHECK = 1;        //动画状态-开启
    private static final int ANIM_UNCHECK = 2;      //动画状态-结束

    private Context mContext;           // 上下文
    private int mWidth, mHeight;        // 宽高
    private Handler mHandler;           // handler

    private Paint mPaint; // 画笔
    private Paint mCheckPaint;
    private Bitmap okBitmap; // 图片

    private int animCurrentPage = -1;       // 当前页码
    private int animMaxPage = 13;           // 总页数
    private int animDuration = 300;         // 动画时长
    private int animState = ANIM_NULL;      // 动画状态

    private boolean isCheck = false;        // 是否只选中状态

    public CheckView(Context context) {
        super(context, null);
    }

    public CheckView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xffFF5317);

        mCheckPaint = new Paint();
        mCheckPaint.setColor(0xffffffff);
        mCheckPaint.setStyle(Paint.Style.FILL);
        mCheckPaint.setAntiAlias(true);

        okBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.checkmark);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (animCurrentPage < animMaxPage && animCurrentPage >= 0){
                    invalidate();
                    if (ANIM_NULL == animState){
                        return;
                    }else if(ANIM_CHECK == animState){
                        animCurrentPage++;
                        mHandler.sendEmptyMessageDelayed(animCurrentPage, animDuration / animMaxPage);
                    }else if (ANIM_UNCHECK == animState){
                        animCurrentPage--;
                        mHandler.sendEmptyMessageDelayed(animCurrentPage, animDuration / animMaxPage);
                    }
                }else {
                    if (isCheck){
                        animCurrentPage = animMaxPage - 1;
                        // mHandler.sendEmptyMessageDelayed(animCurrentPage, animDuration / animMaxPage);
                    }else {
                        animCurrentPage = -1;
                    }
                    invalidate();
                    animState = ANIM_NULL;
                }
            }
        };
    }
    /**
     * View大小确定
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }
    /**
     * 绘制内容
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 移动坐标系到画布中央
        canvas.translate(mWidth / 2, mHeight / 2);

        // 绘制背景圆形
        canvas.drawCircle(0, 0, 240, mPaint);

        // 得出图像边长
        int sideLength = okBitmap.getHeight();

        System.out.println("sideLength" + sideLength);
        // 得到图像选区 和 实际绘制位置
        Rect src = new Rect(sideLength * animCurrentPage, 0, sideLength * (animCurrentPage + 1), sideLength);
        Rect dst = new Rect(-200, -200, 200, 200);


        // 绘制
        canvas.drawBitmap(okBitmap, src, dst, null);
    }


    /**
     * 选择
     */
    public void check() {
        if (animState != ANIM_NULL || isCheck)
            return;
        animState = ANIM_CHECK;
        animCurrentPage = 0;
        mHandler.sendEmptyMessageDelayed(0, animDuration / animMaxPage);
        isCheck = true;
    }

    /**
     * 取消选择
     */
    public void unCheck() {
        if (animState != ANIM_NULL || (!isCheck))
            return;
        animState = ANIM_UNCHECK;
        animCurrentPage = animMaxPage - 1;
        mHandler.sendEmptyMessageDelayed(0, animDuration / animMaxPage);
        isCheck = false;
    }

    /**
     * 设置动画时长
     * @param animDuration
     */
    public void setAnimDuration(int animDuration) {
        if (animDuration <= 0)
            return;
        this.animDuration = animDuration;
    }

    /**
     * 设置背景圆形颜色
     * @param color
     */
    public void setBackgroundColor(int color){
        mPaint.setColor(color);
    }
}
