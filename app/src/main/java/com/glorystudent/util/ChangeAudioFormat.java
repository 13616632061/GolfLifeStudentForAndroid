package com.glorystudent.util;

import android.util.Log;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;
import java.io.File;
import java.io.IOException;


public class ChangeAudioFormat {

    public static String amrToMp3(String sourcePath) {
        String sPath = sourcePath; //例：/sdcard/0/1234134.amr
        String[] split = sPath.split("\\.");
        String tPath = split[0] + ".mp3"; //则转换MP3所存路径 /sdcard/0/1234134.mp3
        return changeToMp3(sPath, tPath);
    }

    private static String changeToMp3(String sourcePath, String targetPath) {
        Log.d("luyin", "changeToMp3: --->" + sourcePath + " " + targetPath);
        File source = new File("storage/emulated/0/1488856159462.amr");
        File target = new File("storage/emulated/0/1488856159462.mp3");
        if (source.exists()) {
            Log.d("yuyin", "changeToMp3: 存在");
        }else {
            Log.d("yuyin", "changeToMp3: 不存在");
        }

        if (!target.exists()) {
            try {
                target.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        AudioAttributes audio = new AudioAttributes();
        Encoder encoder = new Encoder();

        audio.setCodec("libmp3lame");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        try {
            encoder.encode(source, target, attrs);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InputFormatException e) {
            e.printStackTrace();
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        return target.getPath();
    }
}
