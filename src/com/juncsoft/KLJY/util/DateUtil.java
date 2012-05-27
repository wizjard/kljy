package com.juncsoft.KLJY.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtil {
	/**
	 * @param date
	 * @param format 0: 2011-01-01; 1: 2011年01月01日; 2: 2011年1月1日;  other: 2011-1-1
	 * @return
	 */
	public static String formatDate(Date date, int format){
		if(date==null){
			return "";
		}
		int y = date.getYear();
		y = y>1000 ? y : y+1900;
		int m = date.getMonth();
		String mstr = m>8 ? (m+1)+"" : "0"+(m+1);
		int d = date.getDate();
		String dstr = d>9 ? (d)+"" : "0"+(d);
		switch(format){
		case 1:
			return y+"年"+mstr+"月"+dstr+"日";
		case 2:
			return y+"年"+(m+1)+"月"+d+"日";
		case 0:
			return y+"-"+mstr+"-"+dstr;
		default:
			return y+"-"+(m+1)+"-"+d;
		}
	}
	
	public static boolean inSameMonth(Date date1, Date date2){
		return (date1.getMonth()==date2.getMonth() && date1.getYear()==date2.getYear());
	}
	
	public static boolean inSameMonth(String date1, String date2){
		String[] d1 = date1.split("-");
		String[] d2 = date2.split("-");
		return (Integer.parseInt(d1[0])==Integer.parseInt(d2[0]) &&
				Integer.parseInt(d1[1])==Integer.parseInt(d2[1]));
	}
	
	public static boolean inSameDate(Date date1, Date date2){
		if(date1 == null || date2 == null) {
			return false;
		}
		return (date1.getDate()==date2.getDate() && 
				date1.getMonth()==date2.getMonth() && 
				date1.getYear()==date2.getYear());
	}
	
	public static boolean inSameDate(String date1, String date2){
		String[] d1 = date1.split("-");
		String[] d2 = date2.split("-");
		return (Integer.parseInt(d1[2])==Integer.parseInt(d2[2]) &&
				Integer.parseInt(d1[0])==Integer.parseInt(d2[0]) &&
				Integer.parseInt(d1[1])==Integer.parseInt(d2[1]));
	}
	
	/*
	 * 得到当前日期的前几天后后几天的日期    公用方法
	 * 前几天k为负值，后几天k为正值   by cheng jiangyu
	 */
	public static String getXDate(int k){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, k);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String resultDate = sdf.format(cal.getTime());
		return resultDate;
	}
	
	/*
	 * 得到今天的字符串类型日期   格式为yyyy-MM-dd HH:mm:ss
	 * 
	 */
	public static String getToday(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String resultDate = sdf.format(date);
		return resultDate;
	}
	
	
	 /** 
     *  
     * @param datestr 日期字符串 
     * @param day  相对天数，为正数表示之后，为负数表示之前 
     * @return 指定日期字符串n天之前或者之后的日期 
     */  
    public static Date getBeforeAfterDate(String datestr, int day) {  
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
        Date olddate = null;  
        try {  
            df.setLenient(false);  
            olddate = new java.sql.Date(df.parse(datestr).getTime());  
        } catch (ParseException e) {  
            throw new RuntimeException("日期转换错误");  
        }  
        Calendar cal = new GregorianCalendar();  
        cal.setTime(olddate);  
  
        int Year = cal.get(Calendar.YEAR);  
        int Month = cal.get(Calendar.MONTH);  
        int Day = cal.get(Calendar.DAY_OF_MONTH);  
  
        int NewDay = Day + day;  
  
        cal.set(Calendar.YEAR, Year);  
        cal.set(Calendar.MONTH, Month);  
        cal.set(Calendar.DAY_OF_MONTH, NewDay);  
  
        return new Date(cal.getTimeInMillis());  
    } 
	
}
