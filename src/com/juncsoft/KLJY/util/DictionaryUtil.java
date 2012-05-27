package com.juncsoft.KLJY.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;

import net.sf.json.JSONArray;

public class DictionaryUtil {
	private static Properties prop;
	static {
		prop = new Properties();
		InputStream is = DictionaryUtil.class.getClassLoader()
				.getResourceAsStream(
						"com/juncsoft/KLJY/config/dictionary.properties");
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getIndependentDictionaryList(String code)
			throws Exception {
		String sql = prop.getProperty(code);
		if (sql == null) {
			throw new Exception("<" + code + "> Dictonary is not register!");
		}
		JSONArray array = new JSONArray();
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			sm = conn.createStatement();
			rs = sm.executeQuery(sql.split(";")[0]);
			ResultSetMetaData meta = rs.getMetaData();
			int count = meta.getColumnCount();
			while (rs.next()) {
				JSONArray arr = new JSONArray();
				for (int i = 1; i <= count; i++) {
					arr.add(rs.getString(i));
				}
				array.add(arr);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return array.toString();
	}

	public static String getIndependentDictionaryText(String code, String value)
			throws Exception {
		if (value == null || value.length() == 0
				|| value.equalsIgnoreCase("null")) {
			return "";
		}
		String rst = "";
		String sql = prop.getProperty(code);
		if (sql == null) {
			throw new Exception("<" + code + "> Dictonary is not register!");
		}
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql.split(";")[1]);
			sm.setString(1, value);
			rs = sm.executeQuery();
			if (rs.next()) {
				rst = rs.getString(1);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return rst;
	}

	public static String getPublicDictionaryText(String pageCode,
			String fldCode, String value) throws Exception {
		String rst = "";
		if (value == null || value.length() == 0
				|| value.equalsIgnoreCase("null"))
			return "";
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			String sql = "select text from SYS_DICT_Item where value=? and "
					+ "fldCodeId=(select id from SYS_DICT_FldCode where code=? and "
					+ "moduleId=(select id from SYS_DICT_Modules where code=?))";
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, value);
			sm.setString(2, fldCode);
			sm.setString(3, pageCode);
			rs = sm.executeQuery();
			if (rs.next()) {
				rst = rs.getString(1);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return rst;
	}
}
