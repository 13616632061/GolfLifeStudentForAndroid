package com.glorystudent.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 获取时间
 * Created by hyj on 2016/12/20.
 */
public class TimeUtil {

    /**
     * TODO UTC时间转换为标准时间
     *
     * @param time
     * @return
     */
    public static String getUTC2StandardTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date parse = sdf.parse(time);
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            String standardTime = format.format(parse);
            return standardTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * TODO 转换UTC时间为本地时间，当前时间-得到的本地时间 = 时间差（小时为单位）
     *
     * @param time
     * @return
     */
    public static String getTime(String time) {
        long hours = 0;
        try {
            String ts = time.replace("Z", "UTC");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date dt = sdf.parse(ts);
            Date currentDate = new Date();
            long leadTime = currentDate.getTime() - dt.getTime();
            hours = leadTime / 1000 / 60 / 60;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (hours <= 0) {
            return "刚刚";
        } else if (hours >= 24) {
            return hours / 24 + "天前";
        }
        return hours + "小时前";
    }

    /**
     * TODO 转换时间（格式：MM/dd - MM/dd）
     *
     * @param start
     * @param end
     * @return
     */
    public static String getTimeQuantum(String start, String end) {
        String startTime = start.replace("Z", "UTC");
        String endTime = end.replace("Z", "UTC");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(startTime);
            endDate = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String sTime;
        String eTime;
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd");
        sTime = sdf2.format(startDate);
        eTime = sdf2.format(endDate);
        String returnTime = sTime + "-" + eTime;
        return returnTime;
    }

    public static String getTeamAlbumTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String time = format.format(date);
        return time;
    }

    public static String getEventTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = format.parse(time);
            String sTime = format.format(parse);
            return sTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String appointmentTimeToStandardTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd HH:mm");
        String sTime = sdf2.format(date);
        return sTime;
    }

    /**
     * 获取查看凭证中的日期格式
     *
     * @return
     */
    public static String getCertificateTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        String STime = format.format(date);
        String week = getWeek(date);
        week.replace("星期", "周");
        return STime + "(" + week + ")";
    }

    /**
     * 根据date返回活动日期
     *
     * @param date
     * @return
     */
    public static String getEventTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String sTime = format.format(date);
        return sTime;
    }

    /**
     * 根据赛事活动时间返回date类型
     *
     * @return
     */
    public static Date getDateFromEvent(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取赛事活动的展示时间
     *
     * @param time
     * @return
     */
    public static String getCompetitionTime(String time) {
        SimpleDateFormat formatOld = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatNew = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        try {
            Date parse = formatOld.parse(time);
            Log.d("print", "getCompetitionTime: " + parse);
            String sTime = formatNew.format(parse);
            return sTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建活动发布时的时间
     *
     * @param date
     * @param text
     * @return
     */
    public static String getReleasedTime(Date date, String text) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String sTime = format.format(date);
        return sTime + " " + text;
    }

    public static String getReleasedTime2(Date date, String text) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String sTime = format.format(date);
        return sTime + " " + text;
    }

    /**
     * 我参加的活动展示时间
     *
     * @param time
     * @return
     */
    public static Date getDateFromJoin(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取明天的日期
     *
     * @return
     */
    public static Date getTomorrowDate() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(calendar.DATE, 1);//把日期向后推一天
        date = calendar.getTime();
        return date;
    }

    /**
     * 发布活动的上传时间格式
     *
     * @param date
     * @return
     */
    public static String getUploadingTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sTime = format.format(date);
        return sTime;
    }

    /**
     * 根据上传时间格式返回日期
     *
     * @param time
     * @return
     */
    public static Date getDateFromUploading(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 根据指定格式获取时间毫秒数，格式：yyyy年MM月dd日 HH:mm
     *
     * @param str
     * @return
     */
    public static Long getMilliseconds(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        try {
            Date date = format.parse(str);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    /**
     * 根据指定格式获取时间毫秒数，格式：yyyy年MM月dd日
     *
     * @param str
     * @return
     */
    public static Long getMilliseconds2(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            Date date = format.parse(str);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    /**
     * 获取时间，返回Date类型
     *
     * @param time
     * @return
     */
    public static Date getStandardDate(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取预览显示时间
     *
     * @param date
     * @return
     */
    public static String getPreviewTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String sTime = format.format(date);
        return sTime;
    }

    /**
     * 获取预览显示时间
     *
     * @param time
     * @return
     */
    public static String getPreviewTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String pTime = "";
        try {
            Date date = format.parse(time);
            pTime = getPreviewTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return pTime;
    }

    /**
     * 获取图片命名日期格式
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getImageNameTime(Date date) {
        if (date == null)
            date = new Date();
        String formatStr = new String();
        SimpleDateFormat matter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        formatStr = matter.format(date);
        return formatStr;
    }

    /**
     * TODO 给定毫秒数，获取HH:mm:ss格式时间
     *
     * @param time
     * @return
     */
    public static String getToDay(int time) {
        int h = time / 1000 / 60 / 60;
        int m = time / 1000 / 60 % 60;
        int s = time / 1000 % 60;
        String hh = h < 10 ? "0" + h : h + "";
        String mm = m < 10 ? "0" + m : m + "";
        String ss = s < 10 ? "0" + s : s + "";
        if (h == 0) {
            return mm + ":" + ss;
        } else {
            return hh + ":" + mm + ":" + ss;
        }
    }

    /**
     * TODO 给定秒数，获取mm:ss格式时间
     *
     * @param time
     * @return
     */
    public static String getMinuteAndSecond(int time) {
        int m = time / 60 % 60;
        int s = time % 60;
        String mm = m < 10 ? "0" + m : m + "";
        String ss = s < 10 ? "0" + s : s + "";
        return mm + ":" + ss;
    }

    /**
     * TODO 给定yyyy-MM-dd,获取星期几
     *
     * @param pTime
     * @return 数字星期几
     */
    public static int getWeek(String pTime) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(pTime));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week += "7";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week += "1";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week += "2";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week += "3";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week += "4";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week += "5";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week += "6";
        }
        return Integer.valueOf(Week);
    }

    /**
     * TODO 获取某年某月有多少天
     *
     * @param nYear
     * @param nMonth
     * @return
     */
    public static int getMonthDays(int nYear, int nMonth) {
    /*闰年计算方法：普通年能被4整除且不能被100整除的为闰年。（如2004年就是闰年,1901年不是闰年
    世纪年能被400整除的是闰年。(如2000年是闰年，1900年不是闰年) 　*/

        int[] nDays = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (nYear % 4 == 0 && nYear % 100 != 0 || nYear % 400 == 0) {
            nDays[1] = 29;
        }

        return nDays[(nMonth - 1) % 12];
    }

    /**
     * TODO 根据传入的Date获取星期几
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(date);
        return week;
    }

    /**
     * TODO 比较日期是否在今天之前
     */
    public static boolean dateCompareToday(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = RequestUtil.getCurrentTime();
        long currenttime = 0;
        long datetime = 0;
        try {
            Date currentDate = sdf.parse(currentTime);
            Date dateDate = sdf.parse(date);
            currenttime = currentDate.getTime();
            datetime = dateDate.getTime() + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datetime >= currenttime;
    }

    /**
     * 根据当前日期减N天
     *
     * @param sub
     * @return
     */
    public static String subDate(int sub) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - sub);
        String time = null;
        try {
            Date endDate = dft.parse(dft.format(date.getTime()));
            time = dft.format(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String subDate(int sub, String day) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        String time = null;
        try {
            Date beginDate = dft.parse(day);
            Calendar date = Calendar.getInstance();
            date.setTime(beginDate);
            date.set(Calendar.DATE, date.get(Calendar.DATE) - sub);
            Date endDate = dft.parse(dft.format(date.getTime()));
            time = dft.format(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 判断一个日期是否在一段日期之内
     */
    public static boolean isInDate(String time, String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = null;
        Date startDate = null;
        Date endDate = null;
        try {
            currentDate = sdf.parse(time);
            startDate = sdf.parse(startTime);
            endDate = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (currentDate.getTime() >= startDate.getTime() && currentDate.getTime() <= endDate.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取2个日期相差几天
     *
     * @param smdate
     * @param bdate
     * @return
     */
    public static int daysBetween(String smdate, String bdate) {
//        String[] split = smdate.split("-");
//        Integer sy = Integer.valueOf(split[0]);
//        Integer sm = Integer.valueOf(split[1]);
//        Integer sd = Integer.valueOf(split[2]);
//
//        String[] split1 = bdate.split("-");
        Date startDate = null, endDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = sdf.parse(smdate);
            endDate = sdf.parse(bdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);


        return (int) ((fromCalendar.getTime().getTime() - toCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));

//        int day = 0;
//        long time1 = 0,time2 = 0;
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(sdf.parse(smdate));
//            time1 = cal.getTime().getTime();
//            cal.setTime(sdf.parse(bdate));
//            time2 = cal.getTime().getTime();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        int time = (int) (time1 - time2);
//        if (time >= 0) {
//            return time/(1000*3600*24);
//        }else {
//            return -(Math.abs(time)/ (1000*3600*24));
//        }
    }

    /**
     * TODO 给定毫秒数，获取HH:mm格式时间
     *
     * @param time
     * @return
     */
    public static String getChatTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String t = sdf.format(date);
        return t;
    }
}
