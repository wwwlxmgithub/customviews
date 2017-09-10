package cn.edu.xjtu.customviews.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import cn.edu.xjtu.customviews.R;
import cn.edu.xjtu.customviews.util.Utils;

public class ListViewStyleActivity extends AppCompatActivity implements View.OnClickListener {

    private int[] layouts = {
            R.layout.single_line,
            R.layout.single_line_with_icon,
            R.layout.single_line_width_avatar,
            R.layout.single_line_width_avatar_and_icon,

            R.layout.two_line,
            R.layout.two_line_with_icon,
            R.layout.two_line_with_avatar,
            R.layout.two_line_with_avatar_and_icon,

            R.layout.three_line,
            R.layout.three_line_with_icon,
            R.layout.three_line_with_avatar,
            R.layout.three_line_with_avatar_and_icon
    };

    public static final String LAYOUT_ID = "layoutId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_style);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        setTitle("列表风格");

        findViewById(R.id.singleLineBtn).setOnClickListener(this);
        findViewById(R.id.singleLineWithIconBtn).setOnClickListener(this);
        findViewById(R.id.singleLineWithAvatarBtn).setOnClickListener(this);
        findViewById(R.id.singleLineWithAvatarAndIconBtn).setOnClickListener(this);
        findViewById(R.id.twoLineBtn).setOnClickListener(this);
        findViewById(R.id.twoLineWithIconBtn).setOnClickListener(this);
        findViewById(R.id.twoLineWithAvatarBtn).setOnClickListener(this);
        findViewById(R.id.twoLineWithAvatarAndIconBtn).setOnClickListener(this);
        findViewById(R.id.threeLineBtn).setOnClickListener(this);
        findViewById(R.id.threeLineWithIconBtn).setOnClickListener(this);
        findViewById(R.id.threeLineWithAvatarBtn).setOnClickListener(this);
        findViewById(R.id.threeLineWithAvatarAndIconBtn).setOnClickListener(this);

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
            case R.id.singleLineBtn:
                Utils.startActivityWithIntData(this, ListViewPreviewActivity.class, LAYOUT_ID, layouts[0]);
                break;
            case R.id.singleLineWithIconBtn:
                Utils.startActivityWithIntData(this, ListViewPreviewActivity.class, LAYOUT_ID, layouts[1]);
                break;
            case R.id.singleLineWithAvatarBtn:
                Utils.startActivityWithIntData(this, ListViewPreviewActivity.class, LAYOUT_ID, layouts[2]);
                break;
            case R.id.singleLineWithAvatarAndIconBtn:
                Utils.startActivityWithIntData(this, ListViewPreviewActivity.class, LAYOUT_ID, layouts[3]);
                break;
            case R.id.twoLineBtn:
                Utils.startActivityWithIntData(this, ListViewPreviewActivity.class, LAYOUT_ID, layouts[4]);
                break;
            case R.id.twoLineWithIconBtn:
                Utils.startActivityWithIntData(this, ListViewPreviewActivity.class, LAYOUT_ID, layouts[5]);
                break;
            case R.id.twoLineWithAvatarBtn:
                Utils.startActivityWithIntData(this, ListViewPreviewActivity.class, LAYOUT_ID, layouts[6]);
                break;
            case R.id.twoLineWithAvatarAndIconBtn:
                Utils.startActivityWithIntData(this, ListViewPreviewActivity.class, LAYOUT_ID, layouts[7]);
                break;
            case R.id.threeLineBtn:
                Utils.startActivityWithIntData(this, ListViewPreviewActivity.class, LAYOUT_ID, layouts[8]);
                break;
            case R.id.threeLineWithIconBtn:
                Utils.startActivityWithIntData(this, ListViewPreviewActivity.class, LAYOUT_ID, layouts[9]);
                break;
            case R.id.threeLineWithAvatarBtn:
                Utils.startActivityWithIntData(this, ListViewPreviewActivity.class, LAYOUT_ID, layouts[10]);
                break;
            case R.id.threeLineWithAvatarAndIconBtn:
                Utils.startActivityWithIntData(this, ListViewPreviewActivity.class, LAYOUT_ID, layouts[11]);
                break;
        }
    }
}
