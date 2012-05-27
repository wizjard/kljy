package com.juncsoft.KLJY.filter_listener;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import com.juncsoft.KLJY.InHospitalCase.CheckReport.impl.CopyDataImplforRemote;

public class MyTask extends TimerTask {
	private static final int C_SCHEDULE_HOUR = 2;
	private static boolean isRunning = false; 
	private ServletContext context = null; 
	
	public MyTask(){} 
	  
	public MyTask(ServletContext context){ 
		this.context = context; 
	} 
	
	public void run(){ 
		Calendar cal = Calendar.getInstance(); 
		if (!isRunning){
			if (C_SCHEDULE_HOUR == cal.get(Calendar.HOUR_OF_DAY)) { 
				isRunning = true; 
				context.log("开始执行指定任务"); 
				System.out.println("开始拷贝数据");
				int interval = -2;			
				CopyDataImplforRemote cd = new CopyDataImplforRemote();
				cd.copyReportInfoByDate(DateAdd(new Date(), interval), "2050-01-01");
				isRunning = false; 
				context.log("指定任务执行结束"); 
			}
		} 
		else{ 
			context.log("上一次任务执行还未结束"); 
		} 
	} 
//	获得从今天算起往前或后推interval的日期
	public static String DateAdd(Date date, int interval) {
		  java.text.DateFormat df = java.text.DateFormat.getDateInstance();
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);

		  calendar.add(Calendar.DATE, interval);

		  String mm = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		  if (mm.length() == 1) {
		    mm = "0" + mm;
		  }
		  String day = String.valueOf(calendar.get(Calendar.DATE));
		  if (day.length() == 1) {
		    day = "0" + day;
		  }
		  String returnDate = String.valueOf(calendar.get(Calendar.YEAR)) + "-" + mm
		      + "-" + day;
		  System.out.println(returnDate);
		  return returnDate;
		}
}
