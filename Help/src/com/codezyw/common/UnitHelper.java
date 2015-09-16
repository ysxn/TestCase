package com.codezyw.common;

import android.content.Context;

public class UnitHelper {
	public static float dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }
    
    /**
     * 输入数字已经乘以万
     * @param num
     * @return
     */
    public static String formatNumStringWan(long num) {
        if (num == 0) {
            return "--";
        }
        StringBuffer sb = new StringBuffer();
        if (num < 0L) {
            sb.append("-");
        }
        num = Math.abs(num);
        if (num <= 9999L) {
            String data = String.valueOf(num).trim();
            sb.append(data);
            sb.append("万");
            return sb.toString();
        } else if (num >= 10000L && num <= 999999L) {
            String data = String.valueOf(num+50L).trim();
            int length = data.length();
            sb.append(data.substring(0, length - 4)).append(".").append(data.substring(length - 4, length - 2));
            sb.append("亿");
            return sb.toString();
        } else if (num >= 1000000L && num <= 9999999L) {
            String data = String.valueOf(num+500L).trim();
            int length = data.length();
            sb.append(data.substring(0, length - 4)).append(".").append(data.substring(length - 4, length - 3));
            sb.append("亿");
            return sb.toString();
        } else {
            String data = String.valueOf(num+5000L).trim();
            int length = data.length();
            sb.append(data.substring(0, length - 4));
            sb.append("亿");
            return sb.toString();
        }
    }
}