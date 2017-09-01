package cn.edu.xjtu.customviews.view;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xjtu.customviews.R;
import cn.edu.xjtu.customviews.data.PieData;

/**
 * 自定义的View
 * Created by Mg on 2017/8/24.
 */

public class PieView extends View {
    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {
            0xFFCCFF00,
            0xFF6495ED,
            0xFFE32636,
            0xFF800000,
            0xFF808000,
            0xFFFF8C69,
            0xFF808080,
            0xFFE6B800,
            0xFF7CFC00};

    // 画笔
    private  Paint paint= new Paint();
    // 数据
    private List<PieData> pieDatas;
    // 饼状图初始绘制角度
    private float startAngle = 0;
    // View 的宽高
    private int height;
    private int width;

    private Animation animation;

    public void setPieDatas(List<PieData> pieDatas) {
        this.pieDatas = pieDatas;
        initData();
        postInvalidate();
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
        postInvalidate();
    }

    public float getStartAngle() {
        return startAngle;
    }

    private void initData() {
        if (null == pieDatas || pieDatas.size() == 0){
            // 数据有问题 直接返回
            return;
        }

        float sumValue = 0;
        int size = pieDatas.size();
        for (int i = 0; i < size; i++){
            PieData data = pieDatas.get(i);
            //计算数值和
            sumValue += data.getValue();
            //设置颜色
            data.setColor(mColors[i % mColors.length]);
        }

        for (int i = 0; i < size; i++){
            PieData data = pieDatas.get(i);
            float percentage = data.getValue() / sumValue;

            data.setPercentage(percentage);
            // 对应的角度
            float angle = percentage * 360;
            data.setSweepAngle(angle);
            Log.d("pie angle", angle + "");
        }

    }

    public List<PieData> getPieDatas() {
        return pieDatas;
    }

    public PieView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        animation = AnimationUtils.loadAnimation(context, R.anim.rotate);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        animation.setInterpolator(linearInterpolator);
        this.startAnimation(animation);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public Animation getAnimation() {
        return animation;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = pieDatas.size();
        // 当前起始角度
        float currentStartAngle = startAngle;

        // 饼状图半径
        float r = (float) (Math.min(width, height) / 2 * 0.8);
        // 将画布坐标原点移动到中心位置
        canvas.translate(width / 2, height / 2);
        // 饼状图绘制区域
        RectF rect = new RectF(-r, -r, r, r);
        for (int i = 0; i < size; i++){
            PieData data = pieDatas.get(i);
            paint.setColor(data.getColor());

            canvas.drawArc(rect, currentStartAngle, data.getSweepAngle(), true, paint);
            currentStartAngle += data.getSweepAngle();
        }
    }
}

