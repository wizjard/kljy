package com.juncsoft.KLJY.InHospitalCase.common.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.List;

import net.sf.json.JSONArray;


import com.juncsoft.KLJY.util.DatabaseUtil;


public class DictUtil {
	/**
	 * 获取字典候选值
	 * @param sql
	 * @param fldCode
	 * @return
	 * @throws Exception
	 */
	public static String GetDictCondidate(String sql)
			throws Exception {
		String returnVal = "";
		if (sql == null || sql.length() == 0) {
			sql = "select itemValue,itemText " + "from ZD_DictItem where "
					+ "indexId=(select max(indexId) from ZD_DictIndex "
					+ "where fldCode='" + sql + "') order by orderNo";
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> rst = new ArrayList<String>();
		try {
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				rst.add("['" + rs.getString(1) + "','" + rs.getString(2)+ "']");
			}
			returnVal = JSONArray.fromObject(rst).toString();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return returnVal;
	}
}
