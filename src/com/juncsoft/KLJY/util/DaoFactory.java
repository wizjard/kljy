package com.juncsoft.KLJY.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {
	private static Properties prop;
	static {
		prop = new Properties();
		InputStream is = DaoFactory.class.getClassLoader().getResourceAsStream(
				"com/juncsoft/KLJY/config/daofactory.config.properties");
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static Object InstanceImplement(Class cls) {
		Object impl = null;
		try {
			impl = Class.forName(prop.getProperty(cls.getName())).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return impl;
	}
}
