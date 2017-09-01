package cn.edu.xjtu.customviews.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

import cn.edu.xjtu.customviews.R;
import cn.edu.xjtu.customviews.util.AnimationUtils;
import cn.edu.xjtu.customviews.view.GorgeousLoadingView;

public class GorgeousLoadingViewActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_PROGRESS:
                    if (mProgress >80) {
                        if (mProgress < 100) {
                            mProgress += 1;
                            // 随机800ms以内刷新一次
                            mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,
                                    new Random().nextInt(300));
                            mLeafLoadingView.setProgress(mProgress);
                        } else {
                            fanImageView.startAnimation(disappearAnimation);
                            disappearAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {}

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    finishTextView.setVisibility(View.VISIBLE);
                                    //finishTextView.startAnimation(showAnimation);
                                    //loadingTv.startAnimation(showAnimation);
                                    animSet.start();
                                }
                                @Override
                                public void onAnimationRepeat(Animation animation) {}
                            });
                            return;
                        }
                    }else {
                        mProgress += 1;
                        // 随机1200ms以内刷新一次
                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,
                                new Random().nextInt(200));
                        mLeafLoadingView.setProgress(mProgress);

                    }

                    break;

                default:
                    break;
            }
        }
    };
    private GorgeousLoadingView mLeafLoadingView;
    private static final int REFRESH_PROGRESS = 0x10;
    private SeekBar mAmpireSeekBar;
    private SeekBar mDistanceSeekBar;
    private TextView mMplitudeText;
    private TextView mDisparityText;
    private View mFanView;
    private Button mClearButton;
    private int mProgress = 0;

    private TextView mProgressText;
    private View mAddProgress;
    private SeekBar mFloatTimeSeekBar;

    private SeekBar mRotateTimeSeekBar;
    private TextView mFloatTimeText;
    private TextView mRotateTimeText;
    private ImageView fanImageView;
    private TextView finishTextView, loadingTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gorgeous_loading_view);
        initView();
        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, 3000);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initView() {
        setTitle("落叶进度条");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFanView = findViewById(R.id.fan_pic);
        RotateAnimation rotateAnimation = AnimationUtils.initRotateAnimation(false, 1500, true,
                Animation.INFINITE);
        mFanView.startAnimation(rotateAnimation);
        mClearButton = (Button) findViewById(R.id.clear_progress);
        mClearButton.setOnClickListener(this);

        mLeafLoadingView = (GorgeousLoadingView) findViewById(R.id.leaf_loading);
        mMplitudeText = (TextView) findViewById(R.id.text_ampair);
        mMplitudeText.setText(getString(R.string.current_mplitude));

        mDisparityText = (TextView) findViewById(R.id.text_disparity);
        mDisparityText.setText(getString(R.string.current_Disparity));

        mAmpireSeekBar = (SeekBar) findViewById(R.id.seekBar_ampair);
        mAmpireSeekBar.setOnSeekBarChangeListener(this);
        mAmpireSeekBar.setProgress(mLeafLoadingView.getMiddleAmplitude());
        mAmpireSeekBar.setMax(50);

        mDistanceSeekBar = (SeekBar) findViewById(R.id.seekBar_distance);
        mDistanceSeekBar.setOnSeekBarChangeListener(this);
        mDistanceSeekBar.setProgress(mLeafLoadingView.getMplitudeDisparity());
        mDistanceSeekBar.setMax(20);

        mAddProgress = findViewById(R.id.add_progress);
        mAddProgress.setOnClickListener(this);
        mProgressText = (TextView) findViewById(R.id.text_progress);

        mFloatTimeText = (TextView) findViewById(R.id.text_float_time);
        mFloatTimeSeekBar = (SeekBar) findViewById(R.id.seekBar_float_time);
        mFloatTimeSeekBar.setOnSeekBarChangeListener(this);
        mFloatTimeSeekBar.setMax(5000);
        mFloatTimeSeekBar.setProgress((int) mLeafLoadingView.getLeafFloatTime());
        mFloatTimeText.setText(getResources().getString(R.string.current_float_time));

        mRotateTimeText = (TextView) findViewById(R.id.text_rotate_time);
        mRotateTimeSeekBar = (SeekBar) findViewById(R.id.seekBar_rotate_time);
        mRotateTimeSeekBar.setOnSeekBarChangeListener(this);
        mRotateTimeSeekBar.setMax(5000);
        mRotateTimeSeekBar.setProgress((int) mLeafLoadingView.getLeafRotateTime());
        mRotateTimeText.setText(getResources().getString(R.string.current_float_time));

        fanImageView = (ImageView) findViewById(R.id.fan_pic);
        finishTextView = (TextView) findViewById(R.id.finishTextView);

        loadingTv = (TextView) findViewById(R.id.title);
        showAnimation = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.show_scale);

        disappearAnimation = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.disappear_scale);

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(finishTextView, "scaleX",
                0.0f, 1f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(finishTextView, "scaleY",
                0.0f, 1f);
        animSet = new AnimatorSet();
        animSet.setDuration(1000);
        animSet.setInterpolator(new AccelerateDecelerateInterpolator());
        //两个动画同时执行
        animSet.playTogether(anim1, anim2);
    }
    AnimatorSet animSet;
    Animation disappearAnimation;
    Animation showAnimation;
    @Override
    public void onClick(View v) {
        if (v == mClearButton) {
            mLeafLoadingView.setProgress(0);
            mHandler.removeCallbacksAndMessages(null);
            mProgress = 0;
        } else if (v == mAddProgress) {
            mProgress++;
            if (mProgress > 100){
            }
            mLeafLoadingView.setProgress(mProgress);
            mProgressText.setText(String.valueOf(mProgress));
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == mAmpireSeekBar) {
            mLeafLoadingView.setMiddleAmplitude(progress);
            mMplitudeText.setText(getString(R.string.current_mplitude));
        } else if (seekBar == mDistanceSeekBar) {
            mLeafLoadingView.setMplitudeDisparity(progress);
            mDisparityText.setText(getString(R.string.current_Disparity));
        } else if (seekBar == mFloatTimeSeekBar) {
            mLeafLoadingView.setLeafFloatTime(progress);
            mFloatTimeText.setText(getResources().getString(R.string.current_float_time));
        }
        else if (seekBar == mRotateTimeSeekBar) {
            mLeafLoadingView.setLeafRotateTime(progress);
            mRotateTimeText.setText(getResources().getString(R.string.current_rotate_time));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}
