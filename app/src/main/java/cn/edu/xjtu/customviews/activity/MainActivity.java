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
        findViewById(R.id.pieViewButton).setOnClickListener(this);

        findViewById(R.id.CheckViewBtn).setOnClickListener(this);

        findViewById(R.id.drawTextBtn).setOnClickListener(this);

        findViewById(R.id.loadingBtn).setOnClickListener(this);

        findViewById(R.id.pathViewBtn).setOnClickListener(this);

        findViewById(R.id.BezierBtn).setOnClickListener(this);

        findViewById(R.id.turningArrow).setOnClickListener(this);

        findViewById(R.id.searchViewBtn).setOnClickListener(this);

        findViewById(R.id.zoomImageViewBtn).setOnClickListener(this);

        findViewById(R.id.regionTestBtn).setOnClickListener(this);

        findViewById(R.id.dragViewBtn).setOnClickListener(this);

        findViewById(R.id.gestureDetectorBtn).setOnClickListener(this);

        findViewById(R.id.taijiBtn).setOnClickListener(this);

        findViewById(R.id.smileViewBtn).setOnClickListener(this);

        findViewById(R.id.listViewStyleBtn).setOnClickListener(this);

        findViewById(R.id.recyclerViewBtn).setOnClickListener(this);

        findViewById(R.id.cubicInputBtn).setOnClickListener(this);

        findViewById(R.id.propertyAnimBtn).setOnClickListener(this);

        findViewById(R.id.drawerActBtn).setOnClickListener(this);

        findViewById(R.id.buttonsBtn).setOnClickListener(this);
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
            case R.id.BezierBtn:
                Utils.startActivity(this, BezierCurveActivity.class);
                break;
            case R.id.turningArrow:
                Utils.startActivity(this, PathMeasureActivity.class);
                break;
            case R.id.searchViewBtn:
                Utils.startActivity(this, SearchViewActivity.class);
                break;
            case R.id.zoomImageViewBtn:
                Utils.startActivity(this, ZoomImageViewActivity.class);
                break;
            case R.id.regionTestBtn:
                Utils.startActivity(this, RegionTestActivity.class);
                break;
            case R.id.dragViewBtn:
                Utils.startActivity(this, DragViewActivity.class);
                break;
            case R.id.gestureDetectorBtn:
                Utils.startActivity(this, GestureDetectorActivity.class);
                break;
            case R.id.taijiBtn:
                Utils.startActivity(this, TaiJiActivity.class);
                break;
            case R.id.smileViewBtn:
                Utils.startActivity(this, SmileViewActivity.class);
                break;
            case R.id.listViewStyleBtn:
                Utils.startActivity(this, ListViewStyleActivity.class);
                break;
            case R.id.recyclerViewBtn:
                Utils.startActivity(this, RecyclerViewActivity.class);
                break;
            case R.id.cubicInputBtn:
                Utils.startActivity(this, CubicInputActivity.class);
                break;
            case R.id.propertyAnimBtn:
                Utils.startActivity(this, PropertyAnimation2Activity.class);
                break;
            case R.id.drawerActBtn:
                Utils.startActivity(this, DrawerActivity.class);
            case R.id.buttonsBtn:
                Utils.startActivity(this, ButtonsActivity.class);
        }
    }
}
