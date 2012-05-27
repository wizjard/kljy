package com.juncsoft.KLJY.InHospitalCase.Apply.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.InHospitalCase.Apply.dao.MarrowApplyDao;
import com.juncsoft.KLJY.InHospitalCase.Apply.entity.MarrowApply;
import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;

public class MarrowApplyDaoImpl implements MarrowApplyDao {

	// 初始化索引菜单
	@SuppressWarnings("unchecked")
	public JSONArray public_mainMenu(String entityName, Long kid)
			throws Exception {
		JSONArray array = new JSONArray();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<Object> list = session.createQuery(
					"from " + entityName
							+ " where historyCaseId=? order by checkDate")
					.setLong(0, kid).list();
			if (list.size() > 0) {
				for (Object obj : list) {
					JSONObject json = JSONObject.fromObject(obj,
							JSONValueProcessor.formatDate("yyyy年MM月dd日HH时"));
					array.add("{id:" + json.getString("id") + ",checkDate:'"
							+ json.getString("checkDate") + "'}");
				}
				tran.commit();
			}
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return array;
	}

	// 初始化页面的时候调用该方法查询病人信息
	public JSONObject get_patient_Message(Long kid, Long id) throws Exception {
		Session session = null;
		Transaction tran = null;
		JSONObject json = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			// MarrowApply ma = (MarrowApply) session.get(MarrowApply.class,
			// id);
			// Query query = session.createQuery(hql);
			MarrowApply ma = (MarrowApply) session.get(MarrowApply.class, id);
			json = JSONObject.fromObject(ma, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	// 查询病人基本信息
	
	public JSONObject get_Main_Message(Long historyCaseId) throws Exception {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			InHspCaseMaster cm = (InHspCaseMaster) session.get(
					InHspCaseMaster.class, historyCaseId);
			if (cm != null) {
				json.put("patientAge", cm.getAge());
			}
			Patient p = (Patient) session.get(Patient.class, cm.getPatientId());
			if (p != null) {
				json.put("patientName", p.getPatientName());
				// json.put("patientNum", p.getPatientNo());
				json.put("patientSex", p.getGender());
				json.put("province", p.getProvince());
				json.put("relationship", p.getContacterRelationship());
				json.put("Occupation", p.getOccupation());
				Map<String, String> map = getSomeMessage("'"
						+ json.getString("patientSex") + "'", "'"
						+ json.getString("province") + "'", "'"
						+ json.getString("relationship") + "'", "'"
						+ json.getString("Occupation") + "'");
				json.put("patientSex", map.get("gen"));
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	// 打印
	
	public JSONObject MarrowApply_print(Long id) throws Exception {
		Session session = null;
		Transaction tran = null;
		MarrowApply ma = null;
		JSONObject json = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			ma = (MarrowApply) session.get(MarrowApply.class, id);
			json = JSONObject.fromObject(ma, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			json.put("patientArea", getOffice("'"
					+ json.getString("patientArea") + "'"));
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	// 查询科室到打印
	
	public String getOffice(String code) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String office = null;

		try {
			conn = DatabaseUtil.getConnection();
			if (code != null) {
				pstmt = conn
						.prepareStatement("select name from sys_zd_userbelong where CODE = "
								+ code);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					office = rs.getString("name");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(conn, pstmt, rs);
		}
		return office;
	}

	// 经过ID判断传入信息是进行修改或保存,该方法同时负责修改与保存两个功能
	
	public String saveOrUpdate(MarrowApply entity, Long id, Long kid)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		String backId = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			System.out.println("修改数据" + id);
			if (id == -1) {
				Long bid = (Long) session.save(entity);
				System.out.println(bid);
				backId = "{" + "\"" + "id" + "\"" + ":" + bid + "}";
			} else {
				session.update(entity);
				backId = "{" + "\"" + "id" + "\"" + ":" + id + "}";
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return backId;
	}

	// 用于删除一条根据所传入选定记录，删除后使用病人id（patientId）查询出一条新的记录显示在页面上，如果删除的记录在数据库中只有一条，那么查询基本信息
	
	public JSONObject deletePatientMessage(Long id, Long patientId,
			Long historyCaseId, MarrowApply entity) throws Exception {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(entity);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	
	public Map<String, String> getSomeMessage(String gender, String province,
			String relationship, String occupation) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet sex = null;
		ResultSet occu = null;
		ResultSet pro = null;
		ResultSet rel = null;
		Map<String, String> someInfo = new HashMap<String, String>();
		conn = DatabaseUtil.getConnection();
		try {

			if (gender != null) {
				// 查询性别
				pstmt = conn
						.prepareStatement("select name from sys_zd_gb_gender where code ="
								+ gender);
				sex = pstmt.executeQuery();
				if (sex.next()) {
					someInfo.put("gen", sex.getString("name"));
				}
			}
			if (province != null) {
				// 查询籍贯
				pstmt = conn
						.prepareStatement("select name from sys_zd_province where code ="
								+ province);
				pro = pstmt.executeQuery();
				if (pro.next()) {
					someInfo.put("pro", pro.getString("name"));
				}
			}
			if (relationship != null) {
				// 查询关系
				pstmt = conn
						.prepareStatement("select name from sys_zd_relationship where code ="
								+ relationship);
				rel = pstmt.executeQuery();
				if (rel.next()) {
					someInfo.put("rel", rel.getString("name"));
				}
			}
			if (occupation != null) {
				System.out.println("occupation:" + occupation);
				// 查询职业
				pstmt = conn
						.prepareStatement("select name from sys_zd_gb_occupation where code ="
								+ occupation);
				occu = pstmt.executeQuery();
				if (occu.next()) {
					someInfo.put("occu", occu.getString("name"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn !=null){
				conn.close();
			}
			if(pstmt !=null){
				pstmt.close();
			}
			if(sex !=null){
				sex.close();
			}
			if(occu !=null){
				occu.close();
			}
			if(pro !=null){
				pro.close();
			}
			if(rel !=null){
				rel.close();
			}
		}
		return someInfo;
	}

}
