package cn.edu.xjtu.customviews.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import cn.edu.xjtu.customviews.R;

public class ListViewPreviewActivity extends AppCompatActivity {

    private ListView listView;
    private int layoutId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_preview);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        setTitle("LisView");
        layoutId = getIntent().getIntExtra(ListViewStyleActivity.LAYOUT_ID, -1);

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new ListViewAdapter());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class  ListViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return "";
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(ListViewPreviewActivity.this)
            .inflate(layoutId, parent, false);
            return convertView;
        }
    }
}
