package com.dengfx.googleplay.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by DengFX on 2016/9/21.
 */
public class ToastUtils {
    private static Toast toast;

    public static void showText(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
