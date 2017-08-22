package com.glorystudent.util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by hyj on 2017/3/7.
 */
public class FileUtil {
    private static final String TAG = "FileUtil";

    /**
     * 　　* 保存文件
     * 　　* @param toSaveString
     * 　　* @param filePath
     */
    public static void saveFile(String toSaveString, String filePath) {
        FileOutputStream outStream = null;
        try {
            File saveFile = createFile(filePath);
            if (saveFile != null) {
                outStream = new FileOutputStream(saveFile);
                outStream.write(toSaveString.getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件内容
     *
     * @param filePath
     * @return 文件内容
     */
    public static String readFile(String filePath) {
        String str = "";
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            File readFile = new File(filePath);
            if (!readFile.exists()) {
                return null;
            }
            fis = new FileInputStream(readFile);
            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            str = bos.toString();
            return str;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建文件，目录不存在就创建
     *
     * @param filePath
     */
    public static File createFile(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 复制单个文件
     *
     * @param srcFileName  待复制的文件名
     * @param descFileName 目标文件名
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String descFileName) {
        String MESSAGE = "";
        File srcFile = new File(srcFileName);

        // 判断源文件是否存在
        if (!srcFile.exists()) {
            MESSAGE = "源文件：" + srcFileName + "不存在！";
            Log.i(TAG, "copyFile: " + MESSAGE);
            return false;
        } else if (!srcFile.isFile()) {
            MESSAGE = "复制文件失败，源文件：" + srcFileName + "不是一个文件！";
            Log.i(TAG, "copyFile: " + MESSAGE);
            return false;
        }

        // 判断目标文件是否存在
        File destFile = new File(descFileName);
        if (destFile.exists()) {
            // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
            new File(descFileName).delete();
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (!destFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                if (!destFile.getParentFile().mkdirs()) {
                    // 复制文件失败：创建目标文件所在目录失败
                    return false;
                }
            }
        }

        // 复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
