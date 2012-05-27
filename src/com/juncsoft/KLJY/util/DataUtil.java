package com.juncsoft.KLJY.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtil {
	/**
	 * 根据生日计算年龄，小于1岁用月表示，小于1月用天表示，大于5岁用周岁表示，小于5岁大于1岁用岁月表示
	 * 
	 * @param birthday
	 * @param current
	 * @return
	 */
	public static String getCurrentAge(Date birthday, Date current) {
		// 定义返回变量
		String age = "";
		// 如果当前时间小于生日，返回空字符串
		if (current.getTime() < birthday.getTime()) {
			return age;
		}
		// 定义三个临时变量用于存储临时时间差
		int year = 0;
		int month = 0;
		int day = 0;
		// 将Date时间转化为Calendar日历对象
		Calendar b = Calendar.getInstance();
		b.clear();
		b.setTime(birthday);
		Calendar c = Calendar.getInstance();
		c.clear();
		c.setTime(current);

		year = c.get(Calendar.YEAR) - b.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH) - b.get(Calendar.MONTH);
		day = c.get(Calendar.DATE) - b.get(Calendar.DATE);

		if (year == 0) {// 同年情况
			if (month == 1) {// 同年差一个月，两种情况：1、足月算一个月 2、不足月算天
				if (day < 0) {
					month = 0;// 不到一个月，月置空
					day += b.getActualMaximum(Calendar.DAY_OF_MONTH);
				} else {
					day = 0;// 超过一个月，天置空
				}
			} else if (month > 1) {// 大于一个月，两种情况：1、足月不管 2、不足月减去一个月
				if (day < 0) {
					month -= 1;
				}
				day = 0;// 天置空
			} else {// 同一个月的情况，只算天，上面已算出，此处无操作

			}
		} else if (year == 1) {// 差一年的情况
			if (month == 0) {// 同月情况
				if (day < 0) {
					year = 0;
					month = 11;
				}
				day = 0;
			} else if (month > 0) {// 月大于0，满年，清空日
				if (day < 0) {
					month -= 1;
				}
				day = 0;
			} else {// 月小于0情况
				year = 0;
				month += 12;
				if (month == 1) {// 相差一个月的情况
					if (day < 0) {
						month = 0;
						day += b.getActualMaximum(Calendar.DAY_OF_MONTH);
					}
				} else {// 超过一个月
					if (day < 0) {
						month -= 1;
					}
					day = 0;
				}
			}
		} else if (year < 5) {// 小于5岁的情况
			if (month > 0) {// 足月情况，直接给年，然后将日置空
				if (day < 0) {
					month -= 1;
				}
				day = 0;
			} else if (month == 0) {// 同月情况
				if (day >= 0) {// 足天直接给年
					day = 0;
				} else {// 不足天减一年
					year -= 1;
					month = 11;
					day = 0;
				}
			} else {// 不足月情况
				year -= 1;
				month += 12;
				if (day < 0) {
					month -= 1;
				}
				day = 0;
			}
		} else if (year == 5) {
			if (month > 0) {// 足月
				month = 0;
			} else if (month == 0) {// 月份相等
				if (day < 0) {
					year = 4;
					month = 11;
				}
			} else {
				year = 4;
				month += 12;
				if (day < 0) {
					month -= 1;
				}
			}
			day = 0;
		} else {
			Calendar temp = (Calendar) b.clone();
			temp.set(Calendar.YEAR, c.get(Calendar.YEAR));
			if (temp.getTimeInMillis() > c.getTimeInMillis()) {
				year -= 1;
			}
			month = 0;
			day = 0;
		}
		// 拼接年龄字符串
		if (year > 0) {
			age += year + "岁";
		}
		if (month > 0) {
			age += month + "个月";
		}
		if (day > 0) {
			age += day + "天";
		}
		return age;
	}

	public static void main(String[] args) {
		try {
			DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(DataUtil.getCurrentAge(sf.parse("2008-1-22"), sf
					.parse("2011-03-23")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
