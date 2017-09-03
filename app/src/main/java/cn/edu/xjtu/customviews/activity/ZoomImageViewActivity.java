package cn.edu.xjtu.customviews.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.edu.xjtu.customviews.R;
import cn.edu.xjtu.customviews.view.ZoomImageView;

public class ZoomImageViewActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private int[] imgs = new int[]{
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6
    };
    private ImageView[] imageViews = new ImageView[imgs.length];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image_view);
        setTitle("ZoomImageView");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                ZoomImageView imageView  = new ZoomImageView(getApplicationContext());
                imageView.setImageResource(imgs[position]);
                container.addView(imageView);
                imageViews[position] = imageView;

                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

                container.removeView(imageViews[position]);
            }

            @Override
            public int getCount() {
                return imageViews.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
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
