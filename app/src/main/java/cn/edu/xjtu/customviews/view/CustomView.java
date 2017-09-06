package cn.edu.xjtu.customviews.view;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义View的基类
 * Created by Mg on 2017/9/5.
 */

public class CustomView extends View {

    protected Context context;

    protected int viewHeight, viewWidth;

    protected Paint defaultPaint = new Paint();

    protected TextPaint defaultTextPaint = new TextPaint();

    public CustomView(Context context) {
        super(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }
}
