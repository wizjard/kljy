package com.juncsoft.KLJY.Public.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.Public.entity.IllsEntity;
import com.juncsoft.KLJY.Public.entity.PageDict;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class CommonService {
	public JSONArray getSelfQueryList(String sql) throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			ResultSetMetaData meta = rs.getMetaData();
			int cols = meta.getColumnCount();
			while (rs.next()) {
				JSONObject json = new JSONObject();
				for (int i = 1; i <= cols; i++) {
					json.put(meta.getColumnName(i), rs.getString(i));
				}
				array.add(json);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return array;
	}

	public String queryMyBelongs(String belong) throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			sm = conn
					.prepareStatement("select CODE,NAME from SYS_ZD_UserBelong where PCODE=(select PCODE from SYS_ZD_UserBelong where CODE=?) order by CODE");
			sm.setString(1, belong);
			rs = sm.executeQuery();
			while (rs.next()) {
				array.add("['" + rs.getString(1) + "','" + rs.getString(2)
						+ "']");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return array.toString();
	}

	public String queryMyDoctors(String belong) throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "select t.id,t.Name,s.NAME from t_User t inner join SYS_ZD_UserRole s on t.role = s.CODE where valid=1 and t.belong=? order by t.id asc";
			sm = conn.prepareStatement(sql);
			sm.setString(1, belong);
			rs = sm.executeQuery();
			while (rs.next()) {
				array.add("[" + rs.getLong(1) + ",'" + rs.getString(2) + "("
						+ rs.getString(3) + ")" + "']");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return array.toString();
	}

	@SuppressWarnings("unchecked")
	public JSONObject queryIlls(String keyword, Integer start, Integer limit)
			throws Exception {
		keyword = keyword.toUpperCase();
		keyword = "%" + keyword + "%";
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<IllsEntity> list = session.createCriteria(IllsEntity.class)
					.add(
							Restrictions.or(Restrictions
									.like("pycode", keyword), Restrictions
									.like("name", keyword))).addOrder(
							Order.asc("code")).setFirstResult(start)
					.setMaxResults(limit).list();
			json.put("root", list);
			long total = (Long) session
					.createQuery(
							"select count(*) from IllsEntity where (code like ? or pycode like ?)")
					.setString(0, keyword).setString(1, keyword).list()
					.iterator().next();
			json.put("total", total);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	public JSONObject getPageDictionary(String pageCode) throws Exception {
		JSONObject json = new JSONObject();
		String sql = "select f.code,i.value,i.text,i.orderNo from SYS_DICT_Item i "
				+ "left outer join SYS_DICT_FldCode f on i.fldCodeId=f.id "
				+ "where f.id in(select id from SYS_DICT_FldCode "
				+ "where moduleId=(select id from SYS_DICT_Modules "
				+ "where code=?))";
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, pageCode);
			rs = sm.executeQuery();
			PageDict pd = new PageDict();
			while (rs.next()) {
				pd.addItem(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getInt(4));
			}
			json = JSONObject.fromObject(pd.getPageDict());
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return json;
	}

	public JSONObject getPageDictionaryNew(String pageCode) throws Exception {
		JSONObject json = new JSONObject();
		String sql = "select f.code,i.value,i.text,i.orderNo from SYS_DICT_ItemNew i "
				+ "left outer join SYS_DICT_FldCodeNew f on i.fldCodeId=f.id "
				+ "where f.id in(select id from SYS_DICT_FldCodeNew "
				+ "where moduleId=(select id from SYS_DICT_ModulesNew "
				+ "where code=?))";
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, pageCode);
			rs = sm.executeQuery();
			PageDict pd = new PageDict();
			while (rs.next()) {
				pd.addItem(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getInt(4));
			}
			json = JSONObject.fromObject(pd.getPageDict());
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return json;
	}
	
	public JSONArray getDictionarys(String pageCode, String fldCode)
			throws Exception {
		JSONArray array = new JSONArray();
		String sql = "select i.value,i.text,i.orderNo from SYS_DICT_Item i "
				+ "left outer join SYS_DICT_FldCode f on i.fldCodeId=f.id "
				+ "where f.id =(select id from SYS_DICT_FldCode "
				+ "where moduleId=(select id from SYS_DICT_Modules "
				+ "where code=?) and code=?) order by i.orderNo";
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, pageCode);
			sm.setString(2, fldCode);
			rs = sm.executeQuery();
			while (rs.next()) {
				array.add("['" + rs.getString(1) + "','" + rs.getString(2)
						+ "']");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return array;
	}

	public String GetHeaderInfo(Long pid, Long cid) throws Exception {
		String rst = "";
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		String sql = "";
		if (cid != null) {
			sql = "select a.inHspTimes,b.patientName,b.patientNo from t_InHsp_CaseMaster a left outer join t_Patient b on a.patientId=b.id where a.id="
					+ cid;
		} else {
			sql = "select 0,patientName,patientNo from t_Patient where id="
					+ pid;
		}
		try {
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			rs = sm.executeQuery();
			if (rs.next()) {
				rst = "inHspTimes:" + rs.getInt(1) + ",patientName:'"
						+ rs.getString(2) + "',patientNo:'" + rs.getString(3)
						+ "'";
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return "{" + rst + "}";
	}
	
	public String getHeaderInfomenzhen(Long pid, Long cid) throws Exception {
		String rst = "";
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		String sql = "";
		if (cid != null) {
			sql = "select t.outCount,b.patientName,b.patientNo from t_OutOrMergencyCase t left outer join t_Patient b on t.patientId=b.id where t.id="
					+ cid;
		} else {
			sql = "select 0,patientName,patientNo from t_Patient where id="
					+ pid;
		}
		try {
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			rs = sm.executeQuery();
			if (rs.next()) {
				rst = "outCount:" + rs.getInt(1) + ",patientName:'"
						+ rs.getString(2) + "',patientNo:'" + rs.getString(3)
						+ "'";
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return "{" + rst + "}";
	}

	public String getPatientHisCode(Long patientId) throws Exception {
		String rst = new String();
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			String sql = "select patientId from t_Patient where id="
					+ patientId;
			conn = DatabaseUtil.getConnection();
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
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

	/**
	 * 根据医生的所在科室查找当前科室下的所有医生
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public String queryMyDoctorsNew(String deptCode) throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "select id,name,gbjaejik from t_user where hisDoctorId ='在职' and (unkown1 in ('"+deptCode+"') or deptcodename in ('"+deptCode+"') or drdeptname1 in ('"+deptCode+"') or " +
					"drdeptname2 in ('"+deptCode+"') or drdeptname3 in ('"+deptCode+"') or drdeptname4 in ('"+deptCode+"'))";
			sm = conn.prepareStatement(sql);
			rs = sm.executeQuery();
			while (rs.next()) {
				array.add("[" + rs.getLong(1) + ",'" + rs.getString(2) + "("
						+ rs.getString(3) + ")" + "']");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return array.toString();
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(new CommonService()
					.getPageDictionary("EMR-liver-ChiefComplaint"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
