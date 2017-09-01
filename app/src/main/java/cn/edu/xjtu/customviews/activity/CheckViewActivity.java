package cn.edu.xjtu.customviews.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import cn.edu.xjtu.customviews.R;
import cn.edu.xjtu.customviews.view.CheckView;

public class CheckViewActivity extends AppCompatActivity implements View.OnClickListener {

    private CheckView checkView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_view);
        initView();
    }

    private void initView() {
        setTitle("CheckView");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkView = (CheckView) findViewById(R.id.checkView);

        Button unCheck = (Button) findViewById(R.id.unCheckBtn);
        unCheck.setOnClickListener(this);

        Button check = (Button) findViewById(R.id.checkBtn);
        check.setOnClickListener(this);
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
            case R.id.checkBtn:
                checkView.check();
                break;
            case R.id.unCheckBtn:
                checkView.unCheck();
                break;
        }
    }
}
