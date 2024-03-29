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

import com.juncsoft.KLJY.InHospitalCase.Apply.dao.CommunicationDao;
import com.juncsoft.KLJY.InHospitalCase.Apply.entity.Communication;
import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;

public class CommunicationDaoImpl implements CommunicationDao {
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

	// 查询病人的信息

	
	public JSONObject get_Patient_Info(Long id) throws Exception {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Communication c = (Communication) session.get(Communication.class,
					id);
			json = JSONObject.fromObject(c, JSONValueProcessor
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

	// 查询病人的基本信息
	
	public JSONObject get_Main_Info(Long historyCaseId) throws Exception {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			InHspCaseMaster cm = (InHspCaseMaster) session.get(
					InHspCaseMaster.class, historyCaseId);
			if (cm != null) {
				json.put("age", cm.getAge());
			}
			Patient p = (Patient) session.get(Patient.class, cm.getPatientId());
			if (p != null) {
				json.put("patientName", p.getPatientName());
				json.put("patientPhone", p.getHomeTel());
				json.put("frelation", p.getContacterRelationship());
				json.put("familyPhone", p.getContacterTel());
				json.put("gender", p.getGender());
				json.put("province", p.getProvince());
				json.put("Occupation", p.getOccupation());
				
				Map<String, String> map = getSomeMessage("'" + json.getString("gender")
						+ "'", "'" + json.getString("province") + "'", "'"
						+ json.getString("frelation") + "'", "'"
						+ json.getString("Occupation") + "'");
				json.put("gender", map.get("gen"));
				json.put("frelation", map.get("rel"));
				

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
			if(conn != null){
				conn.close();
			}
			if(pstmt != null){
				pstmt.close();
			}
			if(sex != null){
				sex.close();
			}
			if(occu != null){
				occu.close();
			}
			if(pro != null){
				pro.close();
			}
			if(rel != null){
				rel.close();
			}
		}
		return someInfo;
	}

	// 修改和保存
	
	public String saveOrUpdate(Communication entity, Long id) throws Exception {
		Session session = null;
		Transaction tran = null;
		String backId = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id == -1) {
				Long bid = (Long) session.save(entity);
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

	// 删除一条记录
	
	public void deleteData(Communication entity) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (entity != null) {
				session.delete(entity);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	// 打印
	
	public JSONObject Communication_print(Long id) throws Exception {
		Session session = null;
		Transaction tran = null;
		Communication ma = null;
		JSONObject json = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			ma = (Communication) session.get(Communication.class, id);
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

}
