package cn.edu.xjtu.customviews.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xjtu.customviews.R;
import cn.edu.xjtu.customviews.util.Utils;

public class RecyclerViewActivity extends AppCompatActivity {

    private List<String> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        setTitle("RecyclerView");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.horizontalRecyclerView);
        // 设置布局管理器LayoutManager
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        // grid 布局
//        GridLayoutManager manager = new GridLayoutManager(this, 3);
//        recyclerView.setLayoutManager(manager);

       // 瀑布流布局
        /*StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);*/

        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);

        // 设置item增加和删除时的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final MyAdapter adapter = new MyAdapter(getDatas());
        // 创建并设置Adapter
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                int pos = datas.indexOf(data);
                Utils.showToast(RecyclerViewActivity.this, "删除 pos " + pos + " data : " + data);
                adapter.removeItem(pos);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int count = 0;
                    while (true){
                        Thread.sleep(2000);
                        adapter.addItem("hehe" + System.currentTimeMillis() + Math.random(), 0);
                        adapter.notifyDataSetChanged();
                        count++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private List<String> getDatas() {
        datas = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            datas.add("data" + System.currentTimeMillis() + Math.random());
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

        public List<String> data;
        private OnRecyclerViewItemClickListener listener = null;
        public MyAdapter(List<String> datas){
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
            holder.textView.setText(data.get(position));
            // 将数据保存在itemview的tag中，以便点击时获取
            holder.itemView.setTag(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void addItem(String content, int position){
            data.add(content);
            notifyItemChanged(position); // Attention!
        }

        public void removeItem(int position){
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
// 自定义RecyclerView的分割线
class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[] {android.R.attr.listDivider};
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL,
        VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private Drawable divider;
    private int orientation;

    DividerItemDecoration(Context context, int orientation) {
        final TypedArray array = context.obtainStyledAttributes(ATTRS);
        divider = array.getDrawable(0);
        array.recycle();
        setOrientation(orientation);

    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.orientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (orientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;

            final int right = left + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (orientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, divider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, divider.getIntrinsicWidth(), 0);
        }
    }
}





































