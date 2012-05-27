package com.juncsoft.KLJY.filter_listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.util.JSONUtils;

public class ContextListener implements ServletContextListener {
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent arg0) {
		try {
			System.out.println("项目配置开始 ->>");
			JSONUtils.getMorpherRegistry().registerMorpher(
					new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss",
							"yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM-dd",
							"yyyy年MM月dd日HH时" }));
			System.out.println("设置JSON解析时间格式：");
			System.out.println("\t (1) yyyy-MM-dd HH:mm:ss");
			System.out.println("\t (2) yyyy-MM-dd HH:mm");
			System.out.println("\t (3) yyyy-MM-dd HH");
			System.out.println("\t (4) yyyy-MM-dd");
			System.out.println("\t (5) yyyy年MM月dd日HH时");
			System.out.println("配置Log4j：");
			String log4jPath = arg0.getServletContext().getRealPath("/")
					+ "WEB-INF\\log4j.properties";
			System.out.println("\tLog4j.properties：" + log4jPath);
			PropertyConfigurator.configure(log4jPath);
			System.out.println("\tLog4j配置成功");
			System.out.println("以下测试Log4j配置情况:");
			Logger.getRootLogger().error("Test log4j and server start......");
		} catch (Exception e) {
			System.out.println("服务启动配置出错。。。。。。。");
			e.printStackTrace();
		}
	}
}
