package com.juncsoft.KLJY.followVisit.impl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.followVisit.dao.ZhengzuangtizhengDao;
import com.juncsoft.KLJY.followVisit.entity.Ptsymptomst;
import com.juncsoft.KLJY.followVisit.entity.Symptomst;
import com.juncsoft.KLJY.followVisit.entity.T_state;
import com.juncsoft.KLJY.followVisit.entity.T_symptom;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class ZhengzuangtizhengDaoImpl implements ZhengzuangtizhengDao {

	public List findSymptomList(Integer p_id) throws Exception {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		List plist = new ArrayList();

		try {
			conn = DatabaseUtil.getConnection();
			String sql = " select sy.sy_id as sy_id,sy.sy_text as sy_text,pt.ptimes as time,sy.t_symptom_ID as t_id, tm.symptomName as name,te.t_statu as statu from Symptomst sy,ptsymptomst pt,t_symptom tm,t_state te"
					+ " where sy.pt_id=pt.pt_id "
					+ " and tm.t_id=sy.t_symptom_ID "
					+ " and te.t_id=sy.t_state_ID "
					+ " and sy.pt_id=(select   pt_id   from   ptsymptomst a "
					+ " where a.ptimes = (select   max(ptimes)   from   ptsymptomst where pid='"+p_id+"' )  ) order by sy_id  ";
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			System.out.print(" sql="+ sql);
			while (rs.next()) {
				Hashtable<String, String> ht = new Hashtable<String, String>();
				ht.put("sy_id", rs.getString("sy_id"));
				ht.put("time", rs.getString("time"));
				ht.put("t_id", rs.getString("t_id"));
				ht.put("name", rs.getString("name"));
				ht.put("statu", rs.getString("statu"));
				if (null == rs.getString("sy_text")) {
					ht.put("sy_text", "");
				} else {
					ht.put("sy_text", rs.getString("sy_text"));
				}
				plist.add(ht);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return plist;
	}

	public void deleteSymptom(Symptomst symptomst) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(symptomst);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public Integer saveSymptom(Symptomst symptomst) throws Exception {
		Session session = null;
		Transaction tran = null;
		Integer s_id = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			s_id = (Integer) session.save(symptomst);
			tran.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return s_id;
	}

	public Symptomst findSymptom(Integer sy_id) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tran = null;
		Symptomst symptomst = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			symptomst = (Symptomst) session.get(Symptomst.class, sy_id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return symptomst;
	}

	public void updateSymptom(Symptomst symptomst) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.update(symptomst);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	public List findT_state() throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tran = null;
		List<T_state> tlist = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			tlist = session.createQuery("from T_state").list();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return tlist;
	}

	public Integer savePtsymptomst(Ptsymptomst ptsymptomst) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tran = null;
		Integer ptid = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			ptid = (Integer) session.save(ptsymptomst);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return ptid;
	}

	public List findT_symptom() throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tran = null;
		List<T_symptom> tlist = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			tlist = session.createQuery("from T_symptom").list();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return tlist;
	}

	// ����
	public Integer getMaxid() throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tran = null;
		Symptomst symptomst = new Symptomst();
		Integer ptid = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			ptid = (Integer) session.save(symptomst);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return ptid;
	}

	// ��ѯ���һ�εǼ�id
	public Integer getLassptid(Integer pid) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		Integer pt_id = null;
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "select   a.pt_id  as pt_id  from   ptsymptomst a "
					+ " where a.pid='"
					+ pid
					+ "' and a.ptimes=(select   max(ptimes)   from   ptsymptomst)";
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				pt_id = new Integer(rs.getString("pt_id"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return pt_id;
	}

	public Integer getMoreTwo(Integer pid) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		Integer pcount = null;
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "select count(*) as pcount from ptsymptomst where pid='"
					+ pid + "'";
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				pcount = new Integer(rs.getString("pcount"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return pcount;
	}

	public List findCount(Integer pid) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tran = null;
		List<Ptsymptomst> tlist = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			tlist = session.createQuery("from Ptsymptomst where pid=" + pid)
					.list();
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return tlist;
	}

	public String findZhengZuang(Integer pid, Integer ptid, Integer tmid)
			throws Exception {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		String statu = null;
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "select t_e.t_statu as t_statu from symptomst sy,t_state t_e ,ptsymptomst pt,t_symptom tm "
					+ " where sy.t_state_ID=t_e.t_id "
					+ " and pt.pt_id=sy.pt_id "
					+ " and tm.t_id=sy.t_symptom_ID "
					+ " and pt.pid="
					+ pid
					+ " and sy.pt_id=" + ptid + " and sy.t_symptom_ID=" + tmid;
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				statu = rs.getString("t_statu");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return statu;
	}

	public List findZhengZuangName(Integer pid) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "select distinct(tm.symptomName),sy.sy_text as sy_text,sy.t_symptom_ID from symptomst sy,ptsymptomst pt,t_symptom tm"
					+ " where pt.pt_id=sy.pt_id "
					+ " and sy.t_symptom_ID=tm.t_id " + " and pid=" + pid;
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				Hashtable<String, String> ht = new Hashtable<String, String>();
				ht.put("t_symptom_ID", rs.getString("t_symptom_ID"));
				ht.put("symptomName", rs.getString("symptomName"));
				if (null == rs.getString("sy_text")
						|| "无".equals(rs.getString("sy_text"))) {
					ht.put("sy_text", "");
				} else {
					ht.put("sy_text", rs.getString("sy_text"));
				}
				list.add(ht);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return list;
	}

}
