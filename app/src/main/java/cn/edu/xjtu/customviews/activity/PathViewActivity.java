package cn.edu.xjtu.customviews.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import cn.edu.xjtu.customviews.R;
import cn.edu.xjtu.customviews.view.PathView;

public class PathViewActivity extends AppCompatActivity implements View.OnClickListener {

    private PathView radarPathView;

    private Button titleRadioBtn;

    private boolean titleCap = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_view);
        initView();
    }

    private void initView() {
        setTitle("Radar雷达图");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        radarPathView = (PathView) findViewById(R.id.pathView);
        /*double random = Math.random();
        if (random <= 0.3){
            radarPathView.setDimensionCount(5);
            radarPathView.setSpaceCount(5);
            radarPathView.setMaxValue(1);
            radarPathView.setTitles(new String[]{"A", "B", "C", "D", "E"});
            radarPathView.setData(new float[]{0.4f, 0.5f, 0.6f, 0.7f, 0.8f});
        }else if (random <= 0.7){
            radarPathView.setDimensionCount(6);
            radarPathView.setSpaceCount(6);
            radarPathView.setMaxValue(100);
            radarPathView.setTitles(new String[]{"A", "B", "C", "D", "E", "F"});
            radarPathView.setData(new float[]{40, 50, 60, 70, 80, 90});
        }else {
            radarPathView.setDimensionCount(7);
            radarPathView.setSpaceCount(7);
            radarPathView.setMaxValue(100);
            radarPathView.setTitles(new String[]{"A", "B", "C", "D", "E", "F", "G"});
            radarPathView.setData(new float[]{40, 50, 60, 70, 80, 90, 100});
        }*/

        titleRadioBtn = (Button) findViewById(R.id.titleCapRadioBtn);
        titleRadioBtn.setOnClickListener(this);
        findViewById(R.id.redOverrideBtn).setOnClickListener(this);
        findViewById(R.id.grayOverrideBtn).setOnClickListener(this);
        findViewById(R.id.greenOverrideBtn).setOnClickListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.redOverrideBtn:
                /*radarPathView.setDimensionCount(5);
                radarPathView.setSpaceCount(5);
                radarPathView.setMaxValue(1);
                radarPathView.setTitles(new String[]{"A", "B", "C", "D", "E"});
                radarPathView.setData(new float[]{0.4f, 0.5f, 0.6f, 0.7f, 0.8f});
                recreate();*/
                radarPathView.setOverrideColor(0x7FFF0000);
                break;
            case R.id.grayOverrideBtn:
                /*radarPathView.setDimensionCount(6);
                radarPathView.setSpaceCount(6);
                radarPathView.setMaxValue(100);
                radarPathView.setTitles(new String[]{"A", "B", "C", "D", "E", "F"});
                radarPathView.setData(new float[]{40, 50, 60, 70, 80, 90});
                recreate();*/
                radarPathView.setOverrideColor(0x7F888888);
                break;
            case R.id.greenOverrideBtn:
                /*radarPathView.setDimensionCount(7);
                radarPathView.setSpaceCount(7);
                radarPathView.setMaxValue(100);
                radarPathView.setTitles(new String[]{"A", "B", "C", "D", "E", "F", "G"});
                radarPathView.setData(new float[]{40, 50, 60, 70, 80, 90, 100});
                recreate();*/
                radarPathView.setOverrideColor(0x7F00FF00);
                break;

            case R.id.titleCapRadioBtn:
                if (titleCap){
                    radarPathView.setTitles(new String[]{"A", "B", "C", "D", "E", "F", "G",
                            "H", "I", "J", "K", "L", "M", "N", "O", "P"});
                    titleCap = false;
                }else {
                    radarPathView.setTitles(new String[]{"a", "b", "c", "d", "e", "f", "g",
                            "h", "i", "j", "k", "l", "m", "n", "o", "p"});
                    titleCap = true;
                }
        }
    }
}
