package cn.edu.xjtu.customviews.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 绘制PathView
 * Created by Mg on 2017/8/26.
 */

public class PathView extends View {
    /**
     * 宽和高
     */
    private int width, height;
    /**
     * 圆点半径
     */
    private int radius = 15;
    /**
     * 绘制网格的画笔
     */
    private Paint netPaint;
    /**
     * 绘制覆盖区域的画笔
     */
    private Paint overridePaint;
    /**
     * 绘制圆点的画笔
     */
    private Paint dotPaint;
    /**
     * 绘制标题的画笔
     */
    private Paint titlePaint;
    /**
     * 数据
     */
    private float[] data = {100,60,60,60,100,20, 100,60,60,60,100,20, 100,60,60,60};

    private float phase = 1;
    /**
     * 路径绘制效果
     */
    private PathEffect effect;
    /**
     * 绘制的维度数量
     */
    private int dimenssionCount = 16;
    /**
     * 相邻维度的夹角
     */
    private float angle;

    /**
     * 从内向外网格数量
     */
    private int spaceCount  = 4;

    // 坐标轴最大值
    private float maxValue = 100;
    // 覆盖区域颜色
    private int OVERRIDE_COLOR = 0x7f7D7DFC;
    // 各坐标点的颜色
    private int POINT_COLOR = 0xff0000FF;
    // 蜘蛛网格颜色
    private int GRID_COLOR = 0xFF888888;
    //标题字的颜色
    private int TITLE_COLOR = 0xFF888888;
    /**
     * 绘制路径
     */
    private Path path;
    private Path titlePath;

    /**
     * 各个数据所处的坐标点
     */
    private float[][] points;

    private String[] titles = new String[]{"a", "b", "c", "d", "e", "f", "g",
            "h", "i", "j", "k", "l", "m", "n", "o", "p"};

    public PathView(Context context) {
        super(context, null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 设置各个坐标的名称
     * @param titles 名称
     */
    public void setTitles(String[] titles) {
        this.titles = titles;
        invalidate();
    }

    /**
     * 设置各维度的数据
     * @param data 数据
     */
    public void setData(float[] data) {
        this.data = data;
        invalidate();
    }

    /**
     * 设置各个坐标轴的最大值
     * @param maxValue 最大值
     */
    public void setMaxValue (float maxValue){
        this.maxValue = maxValue;
         invalidate();
    }

    /**
     * 设置绘制网格的颜色
     * @param gridColor 网格颜色
     */
    public void setGridColor(int gridColor) {
        netPaint.setColor(gridColor);
        invalidate();
    }

    /**
     * 设置覆盖区域的颜色
     * @param overrideColor  覆盖区域颜色
     */
    public void setOverrideColor(int overrideColor) {
        overridePaint.setColor(overrideColor);
        invalidate();
    }

    /**
     * 设置数据圆点的颜色
     * @param pointColor 圆点颜色
     */
    public void setPointColor(int pointColor) {
        dotPaint.setColor(pointColor);
        invalidate();
    }

    /**
     * 设置路径绘制效果（如虚线）
     * @param effect 效果
     */
    public void setPathEffect(PathEffect effect){
        this.effect = effect;
         invalidate();
    }

    /**
     * 设置维度数量
     * @param dimensionCount 维度数
     */
    public void setDimensionCount(int dimensionCount) {
        this.dimenssionCount = dimensionCount;
         invalidate();
    }

    public void setSpaceCount(int spaceCount) {
        this.spaceCount = spaceCount;
        invalidate();
    }

    float[] param = new float[2]; {
        param[0] = 5;
        param[1] = 5;
    }
    private void init() {

        netPaint = new Paint();
        netPaint.setColor(GRID_COLOR);
        netPaint.setStyle(Paint.Style.STROKE);
        netPaint.setAntiAlias(true);

        effect = new DashPathEffect(param, phase);
        netPaint.setPathEffect(effect);

        titlePaint = new Paint();
        titlePaint.setColor(TITLE_COLOR);
        titlePaint.setTextSize(30);
        titlePaint.setTypeface(Typeface.MONOSPACE);
        titlePaint.setStyle(Paint.Style.STROKE);
        titlePaint.setAntiAlias(true);

        overridePaint = new Paint();
        overridePaint.setColor(OVERRIDE_COLOR);
        overridePaint.setStyle(Paint.Style.FILL);
        overridePaint.setAntiAlias(true);

        dotPaint = new Paint();
        dotPaint.setColor(POINT_COLOR);
        dotPaint.setStyle(Paint.Style.FILL);
        dotPaint.setAntiAlias(true);

        // 维度之间的夹角 弧度值
        angle = (float) (360.0 / dimenssionCount);

        points = new float[dimenssionCount][2];

        for(int i = 0; i < dimenssionCount; i++) {
            data[i] = data[i] / maxValue;
        }

        path = new Path();
        titlePath = new Path();
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
        Paint.FontMetrics fontMetrics = titlePaint.getFontMetrics();
        float titleHeight = fontMetrics.descent - fontMetrics.ascent;
        // 移动到画布中心
        canvas.translate(width / 2, height / 2);

        float R = width < height ? width : height;
        R /= 4;
        radius = (int) (R / 25);

        // 计算要绘制的多边形之间的间距
        int spacing = (int) (R / spaceCount);
        for (int i = 1; i <= spaceCount; i++){
            // 第i个多边形的半径
            int r = i * spacing;
            path.reset();
            // 移动到
            path.moveTo(r, 0);
            for (int j = 0; j < dimenssionCount; j++){
                float ang = (float) (angle * j * 2 * Math.PI / 360.0);
                int x = (int) (r * Math.cos(ang));
                int y = (int) (r * Math.sin(ang));
                if (i == spaceCount) {
                    // 绘制最外边的多边形时，计算该维度数据点的位置
                    points[j][0] = x * data[j];
                    points[j][1] = y * data[j];

                    // 绘制一条从中心到边缘的维度线
                    canvas.drawLine(0, 0, x, y, netPaint);

                    // 绘制最外边的多边形时，绘制该维度的标题
                    titlePath.reset();
                    titlePath.moveTo(x, y);

                    int deltaX = (int) ((r + titleHeight) * Math.cos(ang));
                    int deltaY = (int) ((r + titleHeight) * Math.sin(ang));
                    titlePath.lineTo(deltaX , y);
                    // 绘制每个维度的标题
                    // canvas.drawTextOnPath(titles[j], titlePath, 10, 10, titlePaint);
                    canvas.drawText(titles[j], deltaX, deltaY, titlePaint);
                }
                path.lineTo(x, y);
            }
            path.close();
            canvas.drawPath(path, netPaint);
        }
        // 绘制数据点
        drawPoints(canvas);
        // 绘制覆盖区域
        drawOverrideArea(canvas);


    }
    private void drawOverrideArea(Canvas canvas){
        path.reset();
        path.moveTo(points[0][0], points[0][1]);
        for(int i = 1; i < dimenssionCount; i++) {
            path.lineTo(points[i][0], points[i][1]);
        }
        path.close();
        canvas.drawPath(path, overridePaint);
    }

    /**
     * 绘制各个数据圆点
     */
    private void drawPoints(Canvas canvas) {

        for(int i = 0; i < dimenssionCount; i++) {
            canvas.drawCircle(points[i][0], points[i][1], radius, dotPaint);
        }
        phase += 2;
    }
}
