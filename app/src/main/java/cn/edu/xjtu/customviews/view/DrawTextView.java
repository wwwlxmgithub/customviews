package cn.edu.xjtu.customviews.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mg on 2017/8/25.
 */

public class DrawTextView extends View {
    private String content; // 文本内容
    private Paint textPaint; // 绘制文本的画笔

    private int width;
    private int height;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Paint getTextPaint() {
        return textPaint;
    }

    public void setTextPaint(Paint textPaint) {
        this.textPaint = textPaint;
    }

    public DrawTextView(Context context) {
        super(context, null);
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        content = "sample";
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.MAGENTA);
        textPaint.setTextSize(50);
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

        float textWidth = textPaint.measureText(content);
        canvas.translate(width / 2, height / 2);
        canvas.drawText(content, -textWidth / 2, 0, textPaint);
    }

    public void setTextSize(float size){
        textPaint.setTextSize(size);
        invalidate();
    }

    public void setColor(int color){
        this.textPaint.setColor(color);
        invalidate();
    }
}
