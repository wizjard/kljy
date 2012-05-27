package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.CommonDao;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Field;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.LFunction;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Table;
import com.juncsoft.KLJY.patient.entity.Patient;

import com.juncsoft.KLJY.util.DatabaseUtil;

public class CommonDaoImpl implements CommonDao {

	public Integer saveOrUpdate(Table table) throws Exception {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			sm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			String sql = "select * from "
					+ table.getTableCode()
					+ " where "
					+ table.getKeyField()
					+ "="
					+ (table.getKeyFieldType().equalsIgnoreCase("int") ? table
							.getKeyValue() : "'" + table.getKeyValue() + "'");
			rs = sm.executeQuery(sql);
			if (table.getKeyValue().equals("-1")) {
				rs.moveToInsertRow();
				List<Field> flds = table.getFields();
				rs.updateTimestamp("LastModifyTime", table.getCurrentDate());
				rs.updateTimestamp("CreateTime", table.getCurrentDate());
				for (Field fld : flds) {
					setValue(rs, fld);
				}
				rs.insertRow();
				rs.close();
				sm.close();
				sql = "select " + table.getKeyField() + " from "
						+ table.getTableCode() + " where CreateTime=?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setTimestamp(1, table.getCurrentDate());
				rs = ps.executeQuery();
				rs.next();
				table.setKeyValue(rs.getInt(1) + "");
			} else {
				if (rs.next()) {
					rs.updateTimestamp("LastModifyTime", table
									.getCurrentDate());
					List<Field> flds = table.getFields();
					for (Field fld : flds) {
						setValue(rs, fld);
					}
					rs.updateRow();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return Integer.parseInt(table.getKeyValue());
	}

	private void setValue(ResultSet rs, Field fld) throws Exception {
		String type = fld.getFieldType();
		String value = fld.getFieldValue() == null ? "" : fld.getFieldValue();
		String code = fld.getFieldCode();
		if (value.equals("")) {
			rs.updateNull(code);
		} else if (type.equalsIgnoreCase("int")) {
			rs.updateInt(code, Integer.parseInt(value));
		} else if (type.equalsIgnoreCase("datetime")) {
			rs.updateTimestamp(code, Timestamp.valueOf(value));
		} else if (type.equalsIgnoreCase("string")) {
			rs.updateString(code, value);
		} else if (type.equalsIgnoreCase("date")) {
			rs.updateDate(code, Date.valueOf(value));
		} else if (type.equalsIgnoreCase("float")) {
			rs.updateFloat(code, Float.valueOf(value));
		}
	}

	private String getValue(ResultSet rs, Field fld) throws Exception {
		String rst = "";
		String type = fld.getFieldType();
		String code = fld.getFieldCode();
		if (type.equalsIgnoreCase("date")) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = rs.getDate(code);
			if (date == null)
				return "";
			rst = df.format(date);
		} else if (type.equalsIgnoreCase("datetime")) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = rs.getDate(code);
			if (date == null)
				return "";
			rst = df.format(date);
		} else if (type.equalsIgnoreCase("float")) {
			rst = rs.getString(code);
		} else {
			rst = rs.getString(code);
		}
		return rst;
	}

	public void delete(Table table) throws Exception {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "delete from "
					+ table.getTableCode()
					+ " where "
					+ table.getKeyField()
					+ "="
					+ (table.getKeyFieldType().equalsIgnoreCase("int") ? table
							.getKeyValue() : "'" + table.getKeyValue() + "'");
			sm = conn.createStatement();
			sm.execute(sql);
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
	}

	public List<Table> findByPatientID(Table table, Integer PatientID)
			throws Exception {

		return null;
	}

	public Table findByID(Table table) throws Exception {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "select * from "
					+ table.getTableCode()
					+ " where "
					+ table.getKeyField()
					+ "="
					+ (table.getKeyFieldType().equalsIgnoreCase("int") ? table
							.getKeyValue() : "'" + table.getKeyValue() + "'");
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next()) {
				List<Field> newFld = new ArrayList<Field>();
				List<Field> flds = table.getFields();
				for (Field fld : flds) {
					String value = getValue(rs, fld);
					fld.setFieldValue(value);
					newFld.add(fld);
				}
				table.setFields(newFld);
				table.setKeyValue(rs.getString(table.getKeyField()));
				table.setCreateDate(rs.getString("CreateTime"));
				table.setLastModifyDate(rs.getString("LastModifyTime"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return table;
	}

	public Patient findPatientByID(Integer patientId) throws Exception {
		Patient p = null;
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "select id,PatientNo,PatientName,"
					+ "(case gender when 0 then '男' when 1 then '女' else '' end) as sex,"
					+ "datediff(year,birthday,getdate()) as age,"
					+ "birthday from t_Patient where id=" + patientId;
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next()) {
				p = new Patient();
				p.setId(rs.getLong("id"));
				p.setPatientNo(rs.getString("PatientNo"));
				p.setPatientName(rs.getString("PatientName"));
				p.setGender(rs.getString("sex"));
				int a = rs.getInt("age") == 0 ? 1 : rs.getInt("age");
				// p.setAge(a);
				Date date = rs.getDate("birthday");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String d = df.format(date);
				p.setBirthday(df.parse(d));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return p;
	}

	/**
	 * 保存肝病功能评估表
	 * 
	 * @param lf肝病功能评估实体
	 * @return status 状态
	 */
	public LFunction saveOrUpdateLfunction(LFunction lfun) throws Exception {
		Session session = null;
		Transaction t = null;
		Long id = -1L;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if (lfun.getLfId() != null && lfun.getLfId() > 0) {
				session.merge(lfun);
			} else {
				id = (Long) session.save(lfun);
			}
			if (id > 0) {
				lfun = (LFunction) session.get(LFunction.class, id);
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return lfun;
	}

	/**
	 * 删除肝病功能评估
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer deleLfunction(Long id) throws Exception {
		Session session = null;
		Transaction t = null;
		Integer status = -1;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if (id > 0) {
				LFunction lf = (LFunction) session.get(LFunction.class, id);
				session.delete(lf);
				status = 1;
			} else {
				status = 0;
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return status;
	}

	/**
	 * 读取肝病功能评估
	 */
	public LFunction getLfun(Long id) throws Exception {
		Session session = null;
		Transaction t = null;
		LFunction lf = null;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if (id > 0) {
				lf = (LFunction) session.get(LFunction.class, id);
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return lf;
	}
}
