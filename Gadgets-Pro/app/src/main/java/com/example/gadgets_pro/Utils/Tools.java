package com.myapp.ytvideos.Utils;

import android.content.Context;

public class Tools
{
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
