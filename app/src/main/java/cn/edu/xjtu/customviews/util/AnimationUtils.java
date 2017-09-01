package cn.edu.xjtu.customviews.util;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * 动画工具类
 * Created by Mg on 2017/8/25.
 */

public class AnimationUtils {

    /**
     * 旋转动画
     * @param duration 时长
     * @param fromAngle 起始角度
     * @param toAngle 结束角度
     * @param isFillAfter 动画结束后是否保持最后的状态
     * @param repeatCount 重复次数
     * @return mLoadingRotateAnimation
     */
    public static RotateAnimation initRotateAnimation(long duration,
                                                      int fromAngle, int toAngle,
                                                      boolean isFillAfter, int repeatCount) {
        RotateAnimation mLoadingRotateAnimation = new RotateAnimation(fromAngle, toAngle,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        LinearInterpolator lirInterpolator = new LinearInterpolator();
        mLoadingRotateAnimation.setInterpolator(lirInterpolator);
        mLoadingRotateAnimation.setDuration(duration);
        mLoadingRotateAnimation.setFillAfter(isFillAfter);
        mLoadingRotateAnimation.setRepeatCount(repeatCount);
        mLoadingRotateAnimation.setRepeatMode(Animation.RESTART);
        return mLoadingRotateAnimation;
    }

    /**
     * 旋转动画
     * @param isClockWise 是否是顺时针
     * @param duration 时长
     * @param isFillAfter 动画结束后是否保持最后的状态
     * @param repeatCount 重复次数
     * @return mLoadingRotateAnimation mLoadingRotateAnimation
     */
    public static RotateAnimation initRotateAnimation(boolean isClockWise, long duration,
                                                      boolean isFillAfter, int repeatCount) {
        int endAngle;
        if (isClockWise) {
            endAngle = 360;
        } else {
            endAngle = -360;
        }
        RotateAnimation mLoadingRotateAnimation = new RotateAnimation(0, endAngle,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        LinearInterpolator lirInterpolator = new LinearInterpolator();
        mLoadingRotateAnimation.setInterpolator(lirInterpolator);
        mLoadingRotateAnimation.setDuration(duration);
        mLoadingRotateAnimation.setFillAfter(isFillAfter);
        mLoadingRotateAnimation.setRepeatCount(repeatCount);
        mLoadingRotateAnimation.setRepeatMode(Animation.RESTART);
        return mLoadingRotateAnimation;
    }

    /**
     * 初始化动画drawable
     * @param context context
     * @param drawableIds ids
     * @param durationTime 时长
     * @param isOneShot 是否是一次性的
     * @return mAnimationDrawable mAnimationDrawable
     */
    public static AnimationDrawable initAnimationDrawable(Context context, int[] drawableIds,
                                                          int durationTime, boolean isOneShot) {
        AnimationDrawable mAnimationDrawable = new AnimationDrawable();
        for (int i = 0; i < drawableIds.length; i++) {
            int id = drawableIds[i];
            mAnimationDrawable.addFrame(context.getResources().getDrawable(id), durationTime);
        }
        mAnimationDrawable.setOneShot(isOneShot);
        return mAnimationDrawable;
    }

    /**
     * 初始化透明度动画
     * @param context context
     * @param fromAlpha 起始透明度
     * @param toAlpha 结束透明度
     * @param duration 时长
     * @return alphaAnimation
     */
    public static Animation initAlphaAnimtion(Context context, float fromAlpha, float toAlpha,
                                              long duration) {
        Animation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(duration);
        return alphaAnimation;
    }
}
