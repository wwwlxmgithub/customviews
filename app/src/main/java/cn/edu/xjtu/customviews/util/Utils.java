package cn.edu.xjtu.customviews.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**工具类
 * Created by Mg on 2017/8/24.
 */

public class Utils {
    public static void startActivity(Context fromActivity, Class toActivity){
        Intent intent = new Intent();
        intent.setClass(fromActivity, toActivity);
        fromActivity.startActivity(intent);
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
