package com.glorystudent.util;

import android.graphics.Color;

/**
 * Created by hyj on 2017/3/7.
 */
public class ColorUtil {

    public static String colorToString(int color) {
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        if (red == 255 && green == 255 && blue == 0) {
            return "1,1,0,1";
        }else if (red == 255 && green == 0 && blue == 0) {
            return "1,0,0,1";
        }else if(red == 0 && green == 255 && blue == 0){
            return "0,1,0,1";
        } else if (red == 0 && green == 0 && blue == 255) {
            return "0,0,1,1";
        }
        return "1,1,0,1";
    }


    public static int stringToColor(String colorStr) {
        switch (colorStr){
            case "1,1,0,1":
                return Color.YELLOW;
            case "1,0,0,1":
                return Color.RED;
            case "0,1,0,1":
                return Color.GREEN;
            case "0,0,1,1":
                return Color.BLUE;
        }
        return Color.YELLOW;
    }
}
