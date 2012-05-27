package com.juncsoft.KLJY.InHospitalCase.pitemScore.fever.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import net.sf.json.JSONObject;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class PatientService {
	/**
	 * 查询病人信息
	 * @param 病人ID
	 * @return
	 * @throws Exception
	 */
	public JSONObject findByID(Integer id) throws Exception {
		JSONObject json = new JSONObject();
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			String SQL = "select patientNo ,patientName,gender,age from t_patient p ," +
			             " t_InHsp_CaseMaster c where p.id = c.patientid and c.id = " + id;
			
			conn = DatabaseUtil.getConnection();
			sm = conn.createStatement();
			rs = sm.executeQuery(SQL);
			while (rs.next()) {
				json.put("patientName", rs.getString("patientName"));
				json.put("patientNo", rs.getString("patientNo"));
				String temp = rs.getString("gender");
				if(temp.equals("1")){
					temp = "男";
				}else if(temp.equals("2")){
					temp = "女";
				}
				json.put("sex", temp);
				json.put("age", rs.getString("age"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return json;
	}
}
