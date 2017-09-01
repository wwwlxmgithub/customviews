package cn.edu.xjtu.customviews.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.edu.xjtu.customviews.R;
import cn.edu.xjtu.customviews.view.DrawTextView;

public class DrawTextActivity extends AppCompatActivity implements View.OnClickListener{

    private DrawTextView drawTextView;
    private float textSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_text);
        initView();
    }

    private void initView() {
        textSize = 50;
        drawTextView = (DrawTextView) findViewById(R.id.drawTextView);
        drawTextView.setTextSize(textSize);
        Button bigBtn = (Button) findViewById(R.id.bigBtn);
        bigBtn.setOnClickListener(this);

        Button smallBtn = (Button) findViewById(R.id.smallBtn);
        smallBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bigBtn:
                if (textSize < 80){
                    textSize *= 1.2;
                    drawTextView.setTextSize(textSize);
                }
                break;
            case R.id.smallBtn:
                if (textSize > 10){
                    textSize *= 0.8;
                    drawTextView.setTextSize(textSize);
                }
                break;
        }
    }
}
