package cn.edu.xjtu.customviews.util;

import android.content.Context;
import android.content.Intent;

/**工具类
 * Created by Mg on 2017/8/24.
 */

public class Utils {
    public static void startActivity(Context fromActivity, Class toActivity){
        Intent intent = new Intent();
        intent.setClass(fromActivity, toActivity);
        fromActivity.startActivity(intent);

    }
}
