package cn.edu.xjtu.customviews.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xjtu.customviews.R;
import cn.edu.xjtu.customviews.data.PieData;
import cn.edu.xjtu.customviews.view.PieView;

public class PieViewActivity extends AppCompatActivity {
    private PieView pieView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_view);
        initView();
    }

    private void initView() {
        setTitle("PieView");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pieView = (PieView) findViewById(R.id.pieView);
        List<PieData> datas = new ArrayList<>();
        float value = 4;
        for (int i = 0; i < 9; i++){
            PieData data = new PieData("data" + i, value + i);
            datas.add(data);
        }
        pieView.setPieDatas(datas);

        Button btn = (Button) findViewById(R.id.actionBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieView.setStartAngle(pieView.getStartAngle() + 4);
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
