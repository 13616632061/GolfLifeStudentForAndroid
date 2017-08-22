package com.glorystudent.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 滚动选择器的工具类，提供选项选择器和日期选择器两种方法
 */

public class PickerViewUtil {
    private Context context;
    private TimePickerView pvTime;
    private OptionsPickerView pvOptions;
    private Calendar startDate;//今天
    private Calendar endDate;//往后一百年
    private Calendar preDate;//往前一百年

    public PickerViewUtil(Context context) {
        this.context = context;
        startDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        startDate.set(Calendar.MILLISECOND, 0);
        endDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        endDate.add(Calendar.YEAR, 100);
        preDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        preDate.add(Calendar.YEAR, -100);
    }

    /**
     * 含年月日时分
     *
     * @param titleText
     * @param currentCalendar
     * @param timeLisener
     */
    public void showTimePickerView(String titleText, Calendar currentCalendar, final TimeLisener timeLisener) {
        pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (timeLisener != null) {
                    timeLisener.onSubmit(date, v);
                }
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                .setLineSpacingMultiplier(2.0f)
                .setSubmitText("完成")
                .setTitleSize(14)
                .setContentSize(14)
                .setSubCalSize(16)
                .setTitleText(titleText)
                .setOutSideCancelable(false)
                .isCyclic(false)
                .setCancelColor(Color.BLACK)
                .setSubmitColor(Color.BLACK)
                .setTitleColor(Color.BLACK)
                .setRange(startDate.get(Calendar.YEAR), endDate.get(Calendar.YEAR))
                .setRangDate(startDate, endDate)
                .isCenterLabel(false)
                .build();
        if (currentCalendar != null) {
            pvTime.setDate(currentCalendar);
        }
        pvTime.show();
    }

    /**
     * 只有年月日 今天到后100年
     *
     * @param titleText
     * @param currentCalendar
     * @param timeLisener
     */
    public void showTimePickerView2(String titleText, Calendar currentCalendar, final TimeLisener timeLisener) {
        pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (timeLisener != null) {
                    timeLisener.onSubmit(date, v);
                }
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setLineSpacingMultiplier(2.0f)
                .setSubmitText("完成")
                .setTitleSize(14)
                .setContentSize(14)
                .setSubCalSize(16)
                .setTitleText(titleText)
                .setOutSideCancelable(false)
                .isCyclic(false)
                .setCancelColor(Color.BLACK)
                .setSubmitColor(Color.BLACK)
                .setTitleColor(Color.BLACK)
                .setRange(startDate.get(Calendar.YEAR), endDate.get(Calendar.YEAR))
                .setRangDate(startDate, endDate)
                .isCenterLabel(false)
                .build();
        if (currentCalendar != null) {
            pvTime.setDate(currentCalendar);
        }
        pvTime.show();
        pvTime.show();
    }

    /**
     * 只有年月日,前100年到后100年
     *
     * @param titleText
     * @param currentCalendar
     * @param timeLisener
     */
    public void showTimePickerView3(String titleText, Calendar currentCalendar, final TimeLisener timeLisener) {
        pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (timeLisener != null) {
                    timeLisener.onSubmit(date, v);
                }
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setLineSpacingMultiplier(2.0f)
                .setSubmitText("完成")
                .setTitleSize(14)
                .setContentSize(14)
                .setSubCalSize(16)
                .setTitleText(titleText)
                .setOutSideCancelable(false)
                .isCyclic(false)
                .setCancelColor(Color.BLACK)
                .setSubmitColor(Color.BLACK)
                .setTitleColor(Color.BLACK)
                .setRange(preDate.get(Calendar.YEAR), endDate.get(Calendar.YEAR))
                .setRangDate(preDate, endDate)
                .isCenterLabel(false)
                .build();
        if (currentCalendar != null) {
            pvTime.setDate(currentCalendar);
        }
        pvTime.show();
        pvTime.show();
    }

    public void showOptionsPickerView(String titleText, final List<String> options, final OptionsLisener optionsLisener) {
        pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (optionsLisener != null) {
                    optionsLisener.onSubmit(options1, options2, options3, v);
                }
            }
        })
                .setSubmitText("完成")
                .setLineSpacingMultiplier(9f)
                .setTitleSize(14)
                .setContentTextSize(14)
                .setSubCalSize(16)
                .setLineSpacingMultiplier(2.0f)
                .setTitleText(titleText)
                .setCancelColor(Color.BLACK)
                .setSubmitColor(Color.BLACK)
                .setTitleColor(Color.BLACK)
                .isCenterLabel(false)
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();
        pvOptions.setPicker(options);
        pvOptions.show();
    }

    public interface TimeLisener {
        void onSubmit(Date date, View v);
    }

    public interface OptionsLisener {
        void onSubmit(int options1, int options2, int options3, View v);
    }
}
