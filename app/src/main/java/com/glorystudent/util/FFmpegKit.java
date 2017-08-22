package com.glorystudent.util;

import android.util.Log;

/**
 * Created by Gavin.J on 2017/8/2.
 */

public class FFmpegKit {
    private static final String TAG = "FFmpegKit";

//    public interface KitInterface {
//        void onStart();
//
//        void onProgress(int progress);
//
//        void onEnd(int result);
//    }

    static {
        System.loadLibrary("ffmpeg");
        System.loadLibrary("ffmpeginvoke");
    }

    public static int execute(String[] commands) {
        Log.i(TAG, "execute: 执行了：" + commands.length);
        return run(commands);
    }

//    public static void execute(String[] commands, final KitInterface kitIntenrface) {
//        new AsyncTask<String[], Integer, Integer>() {
//            @Override
//            protected void onPreExecute() {
//                if (kitIntenrface != null) {
//                    kitIntenrface.onStart();
//                }
//            }
//
//            @Override
//            protected Integer doInBackground(String[]... params) {
//                Log.i(TAG, "doInBackground: 执行了：" + params[0].length);
//                return run(params[0]);
//            }
//
//            @Override
//            protected void onProgressUpdate(Integer... values) {
//                if (kitIntenrface != null) {
//                    kitIntenrface.onProgress(values[0]);
//                }
//            }
//
//            @Override
//            protected void onPostExecute(Integer integer) {
//                if (kitIntenrface != null) {
//                    kitIntenrface.onEnd(integer);
//                }
//            }
//        }.execute(commands);
//    }

    public native static int run(String[] commands);
}
