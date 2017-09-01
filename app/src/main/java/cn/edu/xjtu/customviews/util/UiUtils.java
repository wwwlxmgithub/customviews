package cn.edu.xjtu.customviews.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * UI 工具类
 * Created by Mg on 2017/8/26.
 */

public class UiUtils {
    /**
     * 获得屏幕宽度的像素值
     * @param context context
     * @return pixels
     */
    public static int getScreenWidthPixels(Context context){
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 将dp转为pixel
     * @param context context
     * @param dp dp
     * @return px
     */
    public static int dpToPx(Context context, int dp){
        return (int) (dp * getScreenDensity(context) + 0.5f);

    }

    /**
     * 获得屏幕的密度
     * @param context context
     * @return density
     */
    public static float getScreenDensity(Context context){
        try {
            DisplayMetrics metrics = new DisplayMetrics();
            ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
            return metrics.density;
        }catch (Exception ex){
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }
}
