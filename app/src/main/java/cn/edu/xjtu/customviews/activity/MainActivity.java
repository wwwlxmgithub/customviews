package cn.edu.xjtu.customviews.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xjtu.customviews.R;
import cn.edu.xjtu.customviews.data.PieData;
import cn.edu.xjtu.customviews.util.Utils;
import cn.edu.xjtu.customviews.view.PieView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Button pieViewBtn = (Button) findViewById(R.id.pieViewButton);
        pieViewBtn.setOnClickListener(this);

        Button checkViewBtn = (Button) findViewById(R.id.CheckViewBtn);
        checkViewBtn.setOnClickListener(this);

        Button drawTextBtn = (Button) findViewById(R.id.drawTextBtn);
        drawTextBtn.setOnClickListener(this);

        Button loadingBtn = (Button) findViewById(R.id.loadingBtn);
        loadingBtn.setOnClickListener(this);

        Button pathViewBtn = (Button) findViewById(R.id.pathViewBtn);
        pathViewBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pieViewButton:
                Utils.startActivity(this, PieViewActivity.class);
                break;
            case R.id.CheckViewBtn:
                Utils.startActivity(this, CheckViewActivity.class);
                break;
            case R.id.drawTextBtn:
                Utils.startActivity(this, DrawTextActivity.class);
                break;
            case R.id.loadingBtn:
                Utils.startActivity(this, GorgeousLoadingViewActivity.class);
                break;
            case R.id.pathViewBtn:
                Utils.startActivity(this, PathViewActivity.class);
                break;
        }
    }
}
