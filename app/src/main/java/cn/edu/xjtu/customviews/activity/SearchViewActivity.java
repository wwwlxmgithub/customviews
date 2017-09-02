package cn.edu.xjtu.customviews.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import cn.edu.xjtu.customviews.R;

public class SearchViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        setTitle("SearchView");
    }
}

class SearchView extends View{

    private Paint paint;

    // view 宽高
    private int width, height;

    // 视图的状态
    private static enum State{
        NONE, // 初始状态
        STARTING, // 准备搜索
        SEARCHING, // 正在搜索
        ENDING // 准备结束
    }
    // 当前的状态
    private State currentState = State.NONE;
    // 放大镜与外部圆环
    private Path searchPath;
    private Path circlePath;

    // 测量path并截取部分的工具
    private PathMeasure measure;

    // 默认的动效周期
    private int duration = 2000;

    // 控制各个过程的动画
    private ValueAnimator startingAnimator;
    private ValueAnimator searchAnimator;
    private ValueAnimator endingAnimator;

    // 动画数值（用于控制动画的状态，因为同一时间只允许有一种状态出现，具体数值处理取决于当前状态）
    private float animatorValue = 0;

    //动效过程监听器
    private ValueAnimator.AnimatorUpdateListener updateListener;
    private ValueAnimator.AnimatorListener animatorListener;

    // 用于控制动画状态转换
    private Handler handler;

    // 判断是否已经搜索结束
    private boolean finished = false;

    private int count = 0;

    public SearchView(Context context) {
        super(context, null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPath();

        initListener();

        initHandler();

        initAnimator();

        // 进入开始动画
        currentState = State.STARTING;
        startingAnimator.start();
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
        drawSearch(canvas);
    }

    private void drawSearch(Canvas canvas) {
        paint.setColor(Color.WHITE);

        canvas.translate(width / 2, height / 2);

        canvas.drawColor(Color.parseColor("#0082d7"));

        switch (currentState){
            case NONE:
                canvas.drawPath(searchPath, paint);
                break;
            case STARTING:
                measure.setPath(searchPath, false);
                Path dest = new Path();
                measure.getSegment(measure.getLength() * animatorValue, measure.getLength(), dest, true);
                canvas.drawPath(dest, paint);
            break;
            case SEARCHING:
            measure.setPath(circlePath, false);
            Path dest2 = new Path();
            float stop = measure.getLength() * animatorValue;
            float start = (float) (stop - ((0.5 - Math.abs(animatorValue - 0.5)) * 200f));
            measure.getSegment(start, stop, dest2, true);
            canvas.drawPath(dest2, paint);
            break;
            case ENDING:
                measure.setPath(searchPath, false);
                Path dest3 = new Path();
                measure.getSegment(measure.getLength() * animatorValue, measure.getLength(), dest3, true);
                canvas.drawPath(dest3, paint);
            break;
        }
    }

    private void initHandler() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (currentState){
                    case STARTING:
                        // 从开始动画转换到搜索动画
                        finished = false;
                        currentState = State.SEARCHING;
                        startingAnimator.removeAllListeners();
                        searchAnimator.start();
                        break;
                    case SEARCHING:
                        if (!finished){
                            // 如果搜索未结束 则继续执行搜索动画
                            searchAnimator.start();
                            count ++;
                            if (count > 2){
                                // count大于2则进入结束状态
                                finished = true;
                            }
                        }else {
                            // 如果搜索已结束则进入结束动画
                            currentState = State.ENDING;
                            endingAnimator.start();
                        }
                        break;
                    case ENDING:
                        currentState = State.NONE;
                        break;
                }
            }
        };
    }

    private void initListener() {
        updateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        };

        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        };
    }

    private void initAnimator() {
        startingAnimator = ValueAnimator.ofFloat(0, 1).setDuration(duration);
        searchAnimator = ValueAnimator.ofFloat(0, 1).setDuration(duration);
        endingAnimator = ValueAnimator.ofFloat(1, 0).setDuration(duration);

        startingAnimator.addUpdateListener(updateListener);
        searchAnimator.addUpdateListener(updateListener);
        endingAnimator.addUpdateListener(updateListener);

        startingAnimator.addListener(animatorListener);
        searchAnimator.addListener(animatorListener);
        endingAnimator.addListener(animatorListener);
    }

    private void initPath() {
        searchPath = new Path();
        circlePath = new Path();

        measure = new PathMeasure();

        // 注意，不能取到360度，否则系统会自动优化，测量取不到需要的数值
        RectF oval1 = new RectF(-50, -50, 50, 50); // 放大镜圆环
        searchPath.addArc(oval1, 45, 359.9f);

        RectF oval2 = new RectF(-100, -100, 100, 100); // 外部圆环
        circlePath.addArc(oval2, 45, -359.9f);

        float[] pos = new float[2];

        measure.setPath(circlePath, false); // 放大镜把手的位置
        measure.getPosTan(0, pos, null);

        searchPath.lineTo(pos[0], pos[1]);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
    }

}
