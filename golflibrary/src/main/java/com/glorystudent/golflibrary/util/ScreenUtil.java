package com.glorystudent.golflibrary.util;

import android.content.Context;

/**
 * Created by hyj on 2016/9/26 0026.
 */
public class ScreenUtil {
    public static int getStatusHeight(Context context){
        int resid = context.getResources().getIdentifier("status_bar_height","dimen","android");
        if(resid > 0){
            return context.getResources().getDimensionPixelSize(resid);
        }
        return -1;
    }
}
