package cn.edu.xjtu.customviews.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;

import cn.edu.xjtu.customviews.R;

public class CubicInputActivity extends AppCompatActivity {

    private FrameLayout input1, input2;
    private float startDegrees = 90;
    private int duration = 2000;
    private AccelerateDecelerateInterpolator interpolator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cubic_input);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("立方体输入");

        input1 = (FrameLayout) findViewById(R.id.input1);
        input2 = (FrameLayout) findViewById(R.id.input2);

        interpolator = new AccelerateDecelerateInterpolator();
        final RotateAnimation animation = new RotateAnimation(0, 90);
        animation.setDuration(duration);
        animation.setInterpolator(interpolator);


        Button rotate = (Button) findViewById(R.id.rotateBtn);
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*animation.reset();
                input1.setAnimation(animation);
                animation.start();*/
                input1.animate().setDuration(duration).setInterpolator(interpolator)
                        .rotationX(startDegrees).start();
                input2.animate().setDuration(duration).setInterpolator(interpolator)
                        .translationY(-input2.getMeasuredHeight() / 2)
                        .rotationX(startDegrees + 270).start();
                startDegrees += 90;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
