package com.juncsoft.KLJY.system.BelongAndRole.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.system.BelongAndRole.dao.BelongAndRoleDao;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class BelongAndRoleDaoImpl implements BelongAndRoleDao {

	public JSONArray belong_findAll() throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			String sql = "select ID,CODE,NAME from SYS_ZD_UserBelong where PCODE is null";
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			rs = sm.executeQuery();
			while (rs.next()) {
				JSONObject object = new JSONObject();
				object.put("id", rs.getLong(1));
				object.put("text", rs.getString(3) + " (" + rs.getString(2)
						+ ")");
				object.put("leaf", false);
				object.put("children", belong_findByPcode(rs.getString(2)));
				array.add(object);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return array;
	}

	private JSONArray belong_findByPcode(String pcode) throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			String sql = "select ID,CODE,NAME from SYS_ZD_UserBelong where PCODE=?";
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, pcode);
			rs = sm.executeQuery();
			while (rs.next()) {
				JSONObject object = new JSONObject();
				object.put("id", rs.getLong(1));
				object.put("text", rs.getString(3) + " (" + rs.getString(2)
						+ ")");
				object.put("leaf", true);
				array.add(object);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return array;
	}

	public JSONObject belong_findById(Long id) throws Exception {
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			String sql = "select ID,PCODE,CODE,NAME,PYCODE,MEMO from SYS_ZD_UserBelong where ID=?";
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setLong(1, id);
			rs = sm.executeQuery();
			if (rs.next()) {
				json.put("ID", rs.getLong(1));
				json.put("PCODE", rs.getString(2));
				json.put("CODE", rs.getString(3));
				json.put("NAME", rs.getString(4));
				json.put("PYCODE", rs.getString(5));
				json.put("MEMO", rs.getString(6));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return json;
	}

	public void belong_delete(Long id) throws Exception {
		Connection conn = null;
		PreparedStatement sm = null;
		try {
			JSONObject json = this.belong_findById(id);
			String sql = "delete from SYS_ZD_UserBelong where ID=?;delete from SYS_ZD_UserBelong where PCODE=?";
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setLong(1, id);
			sm.setString(2, json.getString("CODE"));
			sm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, null);
		}
	}

	public void belong_saveOrUpdate(JSONObject json) throws Exception {
		Connection conn = null;
		PreparedStatement sm = null;
		try {
			Long id = Long.parseLong(json.getString("ID"));
			String sql = "";
			if (id == -1) {
				sql = "insert into SYS_ZD_UserBelong(PCODE,CODE,NAME,PYCODE,MEMO) values(?,?,?,?,?)";
			} else {
				sql = "update SYS_ZD_UserBelong set PCODE=?,CODE=?,NAME=?,PYCODE=?,MEMO=? where ID="
						+ id;
			}
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			String temp = json.getString("PCODE");
			if (temp == null || temp.length() == 0) {
				sm.setNull(1, Types.VARCHAR);
			} else {
				sm.setString(1, temp);
			}
			sm.setString(2, json.getString("CODE"));
			sm.setString(3, json.getString("NAME"));
			sm.setString(4, json.getString("PYCODE"));
			sm.setString(5, json.getString("MEMO"));
			sm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, null);
		}
	}

	public JSONArray role_findAll() throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			String sql = "select ID,CODE,NAME,MEMO,Modules from SYS_ZD_UserRole";
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			rs = sm.executeQuery();
			while (rs.next()) {
				JSONObject json = new JSONObject();
				json.put("ID", rs.getLong(1));
				json.put("CODE", rs.getString(2));
				json.put("NAME", rs.getString(3));
				json.put("MEMO", rs.getString(4));
				json.put("Modules", rs.getString(5));
				array.add(json);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return array;
	}

	public void role_delete(Long id) throws Exception {
		Connection conn = null;
		PreparedStatement sm = null;
		try {
			String sql = "delete from SYS_ZD_UserRole where ID=?";
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setLong(1, id);
			sm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, null);
		}
	}

	public void role_saveOrUpdate(JSONArray array) throws Exception {
		Connection conn = null;
		PreparedStatement sm = null;
		try {
			String sql = "";
			for (Object object : array) {
				JSONObject json = JSONObject.fromObject(object);
				Long id = Long.parseLong(json.getString("ID"));
				if (id == -1) {
					sql += "insert into SYS_ZD_UserRole(CODE,NAME,MEMO) values('"
							+ json.getString("CODE")
							+ "','"
							+ json.getString("NAME")
							+ "','"
							+ json.getString("MEMO") + "');";
				} else {
					sql += "update SYS_ZD_UserRole set CODE='"
							+ json.getString("CODE") + "',NAME='"
							+ json.getString("NAME") + "',MEMO='"
							+ json.getString("MEMO") + "' where ID=" + id + ";";
				}
			}
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, null);
		}
	}

	public void role_updateModules(Long id, String modules) throws Exception {
		Connection conn = null;
		PreparedStatement sm = null;
		try {
			String sql = "update SYS_ZD_UserRole set Modules=? where ID=?";
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, modules);
			sm.setLong(2, id);
			sm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, null);
		}
	}

}
