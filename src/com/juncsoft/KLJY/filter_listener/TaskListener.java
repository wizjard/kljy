package com.juncsoft.KLJY.filter_listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.plan.entity.Plan;
import com.juncsoft.KLJY.plan.entity.PlanItem;
import com.juncsoft.KLJY.util.DateUtil;
import com.stongnet.sms.http.Sms;
import com.stongnet.sms.http.SmsOperator;

public class TaskListener extends HttpServlet implements ServletContextListener {
	

	/**
	 * @author xie-liehu
	 * @serial 无serialVersionUID，添加一个默认值。
	 */
	private static final long serialVersionUID = 1L;
	
	private java.util.Timer timer = null; 
	public TaskListener() { 
	}
	public void contextInitialized(ServletContextEvent event) { 
		timer = new java.util.Timer(true); 
		event.getServletContext().log("瀹氭椂鍣ㄥ凡鍚姩"); 
		timer.schedule(new MyTask(event.getServletContext()), 0, 60 * 60 * 1000); 
		timer.schedule(new SendMessageTask(event.getServletContext()), 0,  24* 60 * 60 * 1000);  //每隔一天定时遍历planItem看有没有要发送的信息
		event.getServletContext().log("宸茬粡娣诲姞浠诲姟璋冨害琛�"); 
	} 

	public void contextDestroyed(ServletContextEvent event) { 
		timer.cancel(); 
		event.getServletContext().log("瀹氭椂鍣ㄩ攢姣�"); 
	} 

}
