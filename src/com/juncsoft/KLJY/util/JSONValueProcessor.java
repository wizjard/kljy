package com.juncsoft.KLJY.util;

import java.text.SimpleDateFormat;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

public class JSONValueProcessor {
	public static JsonConfig formatDate(final String dateFormat) {
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new JsonValueProcessor() {

					public Object processArrayValue(Object arg0, JsonConfig arg1) {
						return null;
					}

					public Object processObjectValue(String key, Object value,
							JsonConfig arg2) {
						if (value == null)
							return "";
						if (value instanceof java.util.Date) {
							return new SimpleDateFormat(dateFormat)
									.format(value);
						}
						return value.toString();
					}

				});
		// cfg.registerJsonValueProcessor(java.lang.Integer.class,
		// new JsonValueProcessor() {
		//
		// public Object processArrayValue(Object arg0, JsonConfig arg1) {
		// return null;
		// }
		//
		// public Object processObjectValue(String key, Object value,
		// JsonConfig cfg) {
		// if (value == null)
		// return "";
		// return value.toString();
		// }
		//
		// });
		// cfg.registerJsonValueProcessor(java.lang.Float.class,
		// new JsonValueProcessor() {
		//
		// public Object processArrayValue(Object arg0, JsonConfig arg1) {
		// return null;
		// }
		//
		// public Object processObjectValue(String key, Object value,
		// JsonConfig cfg) {
		// if (value == null)
		// return "";
		// return value.toString();
		// }
		//
		// });
		cfg.registerJsonValueProcessor(java.sql.Date.class,
				new JsonValueProcessor() {

					public Object processArrayValue(Object arg0, JsonConfig arg1) {
						return null;
					}

					public Object processObjectValue(String key, Object value,
							JsonConfig arg2) {
						if (value == null)
							return "";
						if (value instanceof java.sql.Date) {
							return new SimpleDateFormat(dateFormat)
									.format(value);
						}
						return value.toString();
					}

				});
		return cfg;
	}

	public static JsonConfig cycleExcludel(String[] str, String format) {
		JsonConfig cfg = new JsonConfig();
		if (format != null) {
			cfg = JSONValueProcessor.formatDate(format);
		}
		cfg.setIgnoreDefaultExcludes(false);
		cfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		cfg.setExcludes(str);
		return cfg;
	}
}
