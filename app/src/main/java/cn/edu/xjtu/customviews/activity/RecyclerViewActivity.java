package cn.edu.xjtu.customviews.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.xjtu.customviews.R;
import cn.edu.xjtu.customviews.util.Utils;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        setTitle("RecyclerView");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.horizontalRecyclerView);
        // 创建默认的线性LayoutManager
        /*LinearLayoutManager manager = new LinearLayoutManager(this);
        // 设置RecyclerView的元素排列方向为水平方式
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(manager);*/

        // grid 布局
       /* GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);*/

       // 瀑布流布局
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);

        final MyAdapter adapter = new MyAdapter(getDatas());
        // 创建并设置Adapter
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Utils.showToast(RecyclerViewActivity.this, data);
            }
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int count = 0;
                    while (count < 5){
                        Thread.sleep(2000);
                        adapter.addItem("hehe", 0);
                        count++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String[] getDatas() {
        String[] datas = new String[40];
        for (int i = 0; i < datas.length; i++){
            datas[i] = "data" + System.currentTimeMillis();
        }
        return datas;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener{

        public String[] data = null;
        private OnRecyclerViewItemClickListener listener = null;
        public MyAdapter(String[] datas){
            this.data = datas;
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            this.listener = listener;
        }

        // 创建新View，被LayoutManager所调用
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
            // 为创建的view注册点击事件
            view.setOnClickListener(this);
            return new MyViewHolder(view);
        }

        // 将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(data[position]);
            // 将数据保存在itemview的tag中，以便点击时获取
            holder.itemView.setTag(data[position]);
        }

        @Override
        public int getItemCount() {
            return data.length;
        }

        public void addItem(String content, int position){
            data[position] = "added";
            notifyItemChanged(position); // Attention!
        }

        public void removeItem(String dataModel){
           /* int position = 0;
            data.remove(position);
            notifyItemRemoved(position); // Attention!*/
        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.onItemClick(v, (String) v.getTag());
            }
        }

    }
    // 自定义的ViewHolder，持有每个item的所有界面元素
    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }

    /**
     * 自定义点击接口 view是item， data是数据
     */
    interface OnRecyclerViewItemClickListener{
        void onItemClick(View view, String data);
    }
}
