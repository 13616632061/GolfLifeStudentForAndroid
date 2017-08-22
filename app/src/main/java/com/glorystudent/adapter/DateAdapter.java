package com.glorystudent.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.glorystudent.entity.SpecialCalendar;
import com.glorystudent.golflife.R;

import org.greenrobot.eventbus.EventBus;

public class DateAdapter extends BaseAdapter {
	private static String TAG = "DateAdapter";
	private boolean isLeapyear = false; // 是否为闰年
	private int daysOfMonth = 0; // 某月的天数
	private int dayOfWeek = 0; // 具体某一天是星期几
	private int nextDayOfWeek = 0;
	private int lastDayOfWeek = 0;
	private int lastDaysOfMonth = 0; // 上一个月的总天数
	private int eachDayOfWeek = 0;
	private Context context;
	private SpecialCalendar sc = null;
	private Resources res = null;
	private Drawable drawable = null;
	private String[] dayNumber = new String[7];
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
	private int currentFlag = -1; // 用于标记当天
	// 系统当前时间
	private String sysDate = "";
	private String sys_year = "";
	private String sys_month = "";
	private String sys_day = "";
	private String currentYear = "";
	private String currentMonth = "";
	private String currentWeek = "";
	private String currentDay = "";
	private int weeksOfMonth;
	private int default_postion;
	private int clickTemp = -1;
	private int week_num = 0;
	private int week_c = 0;
	private int month = 0;
	private int jumpWeek = 0;
	private int c_month = 0;
	private int c_day_week = 0;
	private int n_day_week = 0;
	private boolean isClick;//true 为点击，false为滑动
	private boolean isStart;
	private static boolean isFirst = true;
	private static int savePosition;
	private boolean isTodayPage;
	private boolean isFirstClick = false;

	// 标识选择的Item
	public void setSeclection(int position, boolean isClick) {
		this.isClick = isClick;
		clickTemp = position;
	}

	public void setFirstClick(boolean firstClick) {
		isFirstClick = firstClick;
	}

	public void setTodayPage(boolean isTodayPage) {
		this.isTodayPage = isTodayPage;
	}

	public DateAdapter() {
		Date date = new Date();
		sysDate = sdf.format(date); // 当期日期
		sys_year = sysDate.split("-")[0];
		sys_month = sysDate.split("-")[1];
		sys_day = sysDate.split("-")[2];
		month = Integer.parseInt(sys_month);
	}

	public DateAdapter(Context context, Resources rs, int year_c, int month_c,
					   int week_c, int week_num, int default_postion, boolean isStart, boolean isClick, boolean isTodayPage) {
		this();
		this.context = context;
		this.res = rs;
		this.default_postion = default_postion;
		this.week_c = week_c;
		this.isStart = isStart;
		this.isClick = isClick;
		this.isTodayPage = isTodayPage;
		sc = new SpecialCalendar();

		lastDayOfWeek = sc.getWeekDayOfLastMonth(year_c, month_c,
				sc.getDaysOfMonth(sc.isLeapYear(year_c), month_c));

		currentYear = String.valueOf(year_c);
		// 得到当前的年份
		currentMonth = String.valueOf(month_c); // 得到本月
		// （jumpMonth为滑动的次数，每滑动一次就增加一月或减一月）
		currentDay = String.valueOf(sys_day); // 得到当前日期是哪天
		getCalendar(Integer.parseInt(currentYear),
				Integer.parseInt(currentMonth));
		currentWeek = String.valueOf(week_c);
		Log.d(TAG, "DateAdapter: 得到年月日结果--->" + year_c + " " + month_c + " " + week_c );
		getWeek(Integer.parseInt(currentYear), Integer.parseInt(currentMonth),
				Integer.parseInt(currentWeek));

	}

	public int getTodayPosition() {
		int todayWeek = sc.getWeekDayOfLastMonth(Integer.parseInt(sys_year),
				Integer.parseInt(sys_month), Integer.parseInt(sys_day));
		if (todayWeek == 7) {
			clickTemp = 0;
		} else {
			clickTemp = todayWeek;
		}
		return clickTemp;
	}

	public int getCurrentMonth(int position) {
		int thisDayOfWeek = sc.getWeekdayOfMonth(Integer.parseInt(currentYear),
				Integer.parseInt(currentMonth));
		if (isStart) {
			if (thisDayOfWeek != 7) {
				if (position < thisDayOfWeek) {
					return Integer.parseInt(currentMonth) - 1 == 0 ? 12
							: Integer.parseInt(currentMonth) - 1;
				} else {
					return Integer.parseInt(currentMonth);
				}
			} else {
				return Integer.parseInt(currentMonth);
			}
		} else {
			return Integer.parseInt(currentMonth);
		}

	}

	public int getCurrentYear(int position) {
		int thisDayOfWeek = sc.getWeekdayOfMonth(Integer.parseInt(currentYear),
				Integer.parseInt(currentMonth));
		if (isStart) {
			if (thisDayOfWeek != 7) {
				if (position < thisDayOfWeek) {
					return Integer.parseInt(currentMonth) - 1 == 0 ? Integer
							.parseInt(currentYear) - 1 : Integer
							.parseInt(currentYear);
				} else {
					return Integer.parseInt(currentYear);
				}
			} else {
				return Integer.parseInt(currentYear);
			}
		} else {
			return Integer.parseInt(currentYear);
		}
	}

	public void getCalendar(int year, int month) {
		isLeapyear = sc.isLeapYear(year); // 是否为闰年
		daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
		dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几
		lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1);
		nextDayOfWeek = sc.getDaysOfMonth(isLeapyear, month + 1);
	}

	public void getWeek(int year, int month, int week) {
		Log.d(TAG, "getWeek: 制作年月日--->" + year + " " + month + " " + "lastDaysOfMonth--->" + lastDaysOfMonth + "dayOfWeek--->" + dayOfWeek + "week-->" + week );
		for (int i = 0; i < dayNumber.length; i++) {
			if (dayOfWeek == 7) {
				dayNumber[i] = String.valueOf((i + 1) + 7 * (week - 1));
			} else {
				if (week == 1) {
					if (i < dayOfWeek) {
						dayNumber[i] = String.valueOf(lastDaysOfMonth
								- (dayOfWeek - (i + 1)));
					} else {
						dayNumber[i] = String.valueOf(i - dayOfWeek + 1);
					}
				} else {
					dayNumber[i] = String.valueOf((7 - dayOfWeek + 1 + i) + 7
							* (week - 2));
				}
			}

		}
	}

	public String[] getDayNumbers() {
		return dayNumber;
	}

	/**
	 * 得到某月有几周(特殊算法)
	 */
	public int getWeeksOfMonth() {
		int preMonthRelax = 0;
		if (dayOfWeek != 7) {
			preMonthRelax = dayOfWeek;
		}
		if ((daysOfMonth + preMonthRelax) % 7 == 0) {
			weeksOfMonth = (daysOfMonth + preMonthRelax) / 7;
		} else {
			weeksOfMonth = (daysOfMonth + preMonthRelax) / 7 + 1;
		}
		return weeksOfMonth;
	}

	/**
	 * 某一天在第几周
	 */
	public void getDayInWeek(int year, int month) {

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dayNumber.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_calendar, null);
		}

		TextView tvCalendar = (TextView) convertView
				.findViewById(R.id.tv_calendar);
		tvCalendar.setText(dayNumber[position]);
		tvCalendar.setTextColor(context.getResources().getColor(R.color.primaryColor));


		if (clickTemp == position && isClick) {
			if(isFirst){
				savePosition = position;
				isFirst = false;
			}
			tvCalendar.setSelected(true);
			tvCalendar.setTextColor(context.getResources().getColor(R.color.colorWhite));
			tvCalendar.setBackgroundResource(R.drawable.shape_circle_message);
			EventBus.getDefault().post(position);
		} else {
			tvCalendar.setSelected(false);
			tvCalendar.setTextColor(context.getResources().getColor(R.color.primaryColor));
			tvCalendar.setBackgroundColor(Color.TRANSPARENT);
		}

		if(isTodayPage && savePosition == position && clickTemp != savePosition){
			tvCalendar.setSelected(false);
			tvCalendar.setTextColor(context.getResources().getColor(R.color.primaryColor));
			tvCalendar.setBackgroundResource(R.drawable.shape_circle);
		}

		return convertView;
	}
}
