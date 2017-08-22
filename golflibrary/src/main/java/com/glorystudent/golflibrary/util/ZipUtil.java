package com.glorystudent.golflibrary.util;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by hyj on 2016/12/29.
 */
public class ZipUtil {
    private final static int BUFFERSIZE = 4096;

    /**
     * Gzip 压缩数据
     *
     * @param unGzipStr
     * @return
     */
    public static String compressForGzip(String unGzipStr) {

        if (TextUtils.isEmpty(unGzipStr)) {
            return null;
        }
        try {
            FileInputStream fis = null;
            fis = new FileInputStream(unGzipStr);
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            while((count = fis.read(buffer)) > 0){
                baos2.write(buffer, 0, count);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(baos);
            gzip.write(baos2.toByteArray());
            gzip.close();
            int size = baos.size();
            Log.d("print", "compressForGzip: ---->size:"+size);
            byte[] encode = baos.toByteArray();
            baos.flush();
            baos.close();
            return Base64.encodeToString(encode, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gzip解压数据
     *
     * @param gzipStr
     * @return
     */
    public static String decompressForGzip(String gzipStr) {
        if (TextUtils.isEmpty(gzipStr)) {
            return null;
        }
        byte[] t = Base64.decode(gzipStr, Base64.DEFAULT);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(t);
            GZIPInputStream gzip = new GZIPInputStream(in);
            byte[] buffer = new byte[BUFFERSIZE];
            int n = 0;
            while ((n = gzip.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, n);
            }
            gzip.close();
            in.close();
            out.close();
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Zip 压缩数据
     *
     * @param unZipStr
     * @return
     */
    public static String compressForZip(String unZipStr) {

        if (TextUtils.isEmpty(unZipStr)) {
            return null;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(baos);
            zip.putNextEntry(new ZipEntry("0"));
            zip.write(unZipStr.getBytes());
            zip.closeEntry();
            zip.close();
            byte[] encode = baos.toByteArray();
            baos.flush();
            baos.close();
            return Base64.encodeToString(encode, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Zip解压数据
     *
     * @param zipStr
     * @return
     */
    public static String decompressForZip(String zipStr) {

        if (TextUtils.isEmpty(zipStr)) {
            return null;
        }
        byte[] t = Base64.decode(zipStr, Base64.DEFAULT);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(t);
            ZipInputStream zip = new ZipInputStream(in);
            zip.getNextEntry();
            byte[] buffer = new byte[BUFFERSIZE];
            int n = 0;
            while ((n = zip.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, n);
            }
            zip.close();
            in.close();
            out.close();
            return out.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
