package com.juncsoft.KLJY.system.Research.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.juncsoft.KLJY.ResearchFollowup.entity.ResearchFollowSetUp;
import com.juncsoft.KLJY.system.Research.dao.ResearchAdminDao;
import com.juncsoft.KLJY.system.Research.entity.ResearchMember;
import com.juncsoft.KLJY.system.Research.entity.ResearchTopic;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class ResearchAdminDaoImpl implements ResearchAdminDao {

	public void delete(ResearchTopic t) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<ResearchFollowSetUp> list = session.createQuery("from ResearchFollowSetUp where researchTopicId = ?")
					.setLong(0, t.getId()).list();
			for(ResearchFollowSetUp rfsu : list){
				session.delete(rfsu);
			}
			session.delete(t);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	public JSONObject findAll(Integer start, Integer limit) throws Exception {
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			long total = (Long) session.createQuery(
					"select count(*) from ResearchTopic").list().iterator()
					.next();
			json.put("total", total);
			List root = session.createCriteria(ResearchTopic.class)
					.setFirstResult(start).setMaxResults(limit).addOrder(
							Order.asc("id")).list();
			json.put("root", root);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	public ResearchTopic findById(Long id) throws Exception {
		ResearchTopic r = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			r = (ResearchTopic) session.get(ResearchTopic.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return r;
	}

	public ResearchTopic saveOrUpdate(ResearchTopic t) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (t.getId() == null || t.getId() <= 0) {
				t.setId((Long) session.save(t));
			} else {
				session.update(t);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return t;
	}

	public JSONArray crf_findById(Long id) throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select CRFCode from SYS_ResearchTopic_CRF_Rel where ResearchTopicID=?";
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				array.add(rs.getString(1));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return array;
	}

	public JSONArray crf_findAll() throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select CODE,NAME from SYS_ZD_CRF order by ORDERNO";
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				array.add("{id:'" + rs.getString(1) + "',text:'"
						+ rs.getString(2) + "',leaf:true,iconCls:'img-none'}");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return array;
	}

	public void crf_save(Long id, JSONArray crf) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "delete from SYS_ResearchTopic_CRF_Rel where ResearchTopicID=?";
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ps.executeUpdate();
			for (Object object : crf) {
				sql = "insert into SYS_ResearchTopic_CRF_Rel(ResearchTopicID,CRFCode) values(?,?)";
				ps = conn.prepareStatement(sql);
				ps.setLong(1, id);
				ps.setString(2, object.toString());
				ps.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
	}

	public ResearchMember mem_delete(ResearchMember mem) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(mem);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public JSONObject mem_findAll(Integer start, Integer limit)
			throws Exception {
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			long total = (Long) session.createQuery(
					"select count(*) from ResearchMember").list().iterator()
					.next();
			json.put("total", total);
			List root = session.createCriteria(ResearchMember.class)
					.setFirstResult(start).setMaxResults(limit).addOrder(
							Order.desc("id")).list();
			json.put("root", root);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	public ResearchMember mem_saveOrUpdate(ResearchMember mem) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (mem.getId() == null || mem.getId() <= 0) {
				mem.setId((Long) session.save(mem));
			} else {
				session.update(mem);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mem;
	}

	public void mem_res_rel_save(Long id, JSONArray mems) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "delete from SYS_ResearchTopic_MEM_Rel where ResearchTopicID=?";
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ps.executeUpdate();
			for (Object object : mems) {
				sql = "insert into SYS_ResearchTopic_MEM_Rel(ResearchTopicID,MemID) values(?,?)";
				ps = conn.prepareStatement(sql);
				ps.setLong(1, id);
				ps.setLong(2, Long.parseLong(object.toString()));
				ps.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
	}

	@SuppressWarnings("unchecked")
	public JSONObject mem_res_rel_find(Long id) throws Exception {
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) from SYS_ResearchTopic_MEM_Rel where ResearchTopicID=?";
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			long total = 0;
			List<ResearchMember> root = new ArrayList<ResearchMember>();
			if (rs.next()) {
				total = rs.getLong(1);
				if (total > 0) {
					sql = "select MemID from SYS_ResearchTopic_MEM_Rel where ResearchTopicID=?";
					ps = conn.prepareStatement(sql);
					ps.setLong(1, id);
					rs = ps.executeQuery();
					String ids = "";
					while (rs.next()) {
						ids += rs.getLong(1) + ",";
					}
					if (ids.length() > 0) {
						ids = ids.substring(0, ids.length() - 1);
					}
					ids = "(" + ids + ")";
					sql = "from ResearchMember where id in" + ids
							+ " order by id";
					Session session = DatabaseUtil.getSession();
					Transaction tran = session.beginTransaction();
					root = session.createQuery(sql).list();
					tran.commit();
					session.close();
				}
			}
			json.put("total", total);
			json.put("root", root);
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return json;
	}

	public JSONArray group_rel_find(Long id) throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select code,name,s_desc from SYS_ResearchTopic_SubGroup_Rel where ResearchTopicID=? order by code";
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				array.add("{code:'" + rs.getString(1) + "',name:'"
						+ rs.getString(2) + "',s_desc:'" + rs.getString(3)
						+ "'}");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return array;
	}

	public void group_rel_save(Long id, JSONArray array) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "delete from SYS_ResearchTopic_SubGroup_Rel where ResearchTopicID=?";
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ps.executeUpdate();
			sql = "";
			for (Object object : array) {
				JSONObject json = JSONObject.fromObject(object);
				sql += "insert into SYS_ResearchTopic_SubGroup_Rel(ResearchTopicID,code,name,s_desc) values("
						+ id
						+ ",'"
						+ json.getString("code")
						+ "','"
						+ json.getString("name")
						+ "','"
						+ json.getString("s_desc") + "');";
			}
			if(sql.length()>0){
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
	}

}
