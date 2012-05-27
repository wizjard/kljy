package com.juncsoft.KLJY.member.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.ResearchFollowup.entity.Followup;
import com.juncsoft.KLJY.member.dao.MemberDao;
import com.juncsoft.KLJY.member.entity.Member;
import com.juncsoft.KLJY.member.entity.MemberFirstGroup;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.DictionaryUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;

public class MemberDaoImpl implements MemberDao {

	public JSONObject getPatInfo(Long patId) throws Exception {
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient pat = (Patient) session.get(Patient.class, patId);
			if (pat != null) {
				json.put("patientName", pat.getPatientName());
				json.put("gender", DictionaryUtil.getIndependentDictionaryText(
						"gender_gb", pat.getGender()));
				json.put("mobilePhone", pat.getMobilePhone());
				json.put("homeTel", pat.getHomeTel());
				json.put("patientNo", pat.getPatientNo());
				if (pat.getBirthday() != null) {
					json.put("birthday", new SimpleDateFormat("yyyy-MM-dd")
							.format(pat.getBirthday()));
				}
				json.put("email", pat.getEmail());
				json.put("homePostCode", pat.getHomePostCode());
				json.put("homeAddr", pat.getHomeAddr());
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	public void mem_delete(Member mem) throws Exception {
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
	}

	public Member mem_findById(Long id) throws Exception {
		Member mem = new Member();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			mem = (Member) session.get(Member.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mem;
	}

	public Member mem_save(Member mem) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			mem = setMemberNo(mem);
			mem = setMemberCaseId(mem);
			mem.setId((Long) session.save(mem));
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mem;
	}

	public Member mem_update(Member mem) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.update(mem);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return null;
	}

	private synchronized Member setMemberNo(Member mem) throws Exception {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			String sql = "select seed from t_MemberNo_count";
			conn = DatabaseUtil.getConnection();
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if(rs.next()){
				Long seed = rs.getLong(1);
				if(seed!=null && seed > 0){
					String str = "";
					for (int i = 0, len = 6 - seed.toString().length(); i < len; i++) {
						str += "0";
					}
					str = new SimpleDateFormat("yyMMdd").format(new Date()) + str
							+ seed;
					mem.setMemberNo(str);
					sql = "update t_MemberNo_count set seed=" + (seed + 1);
					sm = conn.createStatement();
					sm.executeUpdate(sql);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return mem;
	}

	private Member setMemberCaseId(Member mem) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			InHspCaseMaster master = new InHspCaseMaster();
			master.setCaseModuleId("liver");
			master.setFlag(1);
			master.setPatientId(mem.getPatientId());
			Long id = (Long) session.save(master);
			mem.setCaseId(id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mem;
	}

	@SuppressWarnings("unchecked")
	public Member mem_findByPatId(Long id) throws Exception {
		Member mem = new Member();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(Member.class).add(
					Restrictions.eq("patientId", id)).list();
			if (list.size() > 0) {
				mem = (Member) list.get(0);
			} else {
				mem.setPatientId(id);
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

	@SuppressWarnings("unchecked")
	public List<Patient> mempat_findAll(Integer start, Integer limit)
			throws Exception {
		List<Patient> pats = new ArrayList<Patient>();
		List<Member> list = new ArrayList<Member>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(Member.class).addOrder(
					Order.desc("inDate")).setFirstResult(start).setMaxResults(
					limit).list();
			String id = "(";
			for (Member m : list) {
				id += m.getPatientId() + ",";
			}
			id = id.substring(0, id.length() - 1) + ")";
			pats = session.createQuery("from Patient where id in " + id).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return pats;
	}

	public long mempat_getTotal() throws Exception {
		long total = 0;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			total = (Long) session.createQuery("select count(*) from Member")
					.list().iterator().next();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return total;
	}

	@SuppressWarnings("unchecked")
	public JSONObject mempat_search(String keyword) throws Exception {
		JSONObject json = new JSONObject();
		keyword = "%" + keyword + "%";
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		Session session = null;
		Transaction tran = null;
		try {
			long total = 0;
			String id = "(";
			String sql = "from t_Member a left outer join t_Patient b on a.patientId=b.id where b.patientName like ? or b.patientNo like ?";
			String totalSql = "select count(*) " + sql;
			String idSql = "select a.patientId " + sql;
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(totalSql);
			sm.setString(1, keyword);
			sm.setString(2, keyword);
			rs = sm.executeQuery();
			rs.next();
			total = rs.getInt(1);
			sm = conn.prepareStatement(idSql);
			sm.setString(1, keyword);
			sm.setString(2, keyword);
			rs = sm.executeQuery();
			while (rs.next()) {
				id += rs.getLong(1) + ",";
			}
			if (total > 0) {
				id = id.substring(0, id.length() - 1) + ")";
				session = DatabaseUtil.getSession();
				tran = session.beginTransaction();
				List<Patient> pats = session.createQuery(
						"from Patient where id in " + id).list();
				json.put("root", JSONArray.fromObject(pats, JSONValueProcessor
						.formatDate("yyyy-MM-dd")));
				json.put("total", total);
				tran.commit();
			} else {
				json.put("root", "[]");
				json.put("total", 0);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	@SuppressWarnings("unchecked")
	public MemberFirstGroup firstgroup_findByCaseId(Long id) throws Exception {
		MemberFirstGroup group = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(MemberFirstGroup.class).add(
					Restrictions.eq("caseId", id)).list();
			if (list.size() > 0)
				group = (MemberFirstGroup) list.get(0);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return group;
	}

	public MemberFirstGroup firstgroup_saveOrUpdate(MemberFirstGroup group)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (group.getId() == null || group.getId() <= 0) {
				group.setId((Long) session.save(group));
				// firstgroup_createFollowupRecord(group, 0);
			} else {
				session.update(group);
				// firstgroup_createFollowupRecord(group, 1);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return group;
	}

	@SuppressWarnings( { "deprecation", "unchecked" })
	private void firstgroup_createFollowupRecord(MemberFirstGroup group,
			int saveOrUpdate) throws Exception {
		Followup follow = new Followup();
		follow.setPatientResearchId(new Long(-1));
		follow.setFollowTimes("1");
		follow.setFollowCycle(group.getCheckCycle());
		follow.setFollowContent(group.getCheckName());
		Date followupPlanDate = group.getStartDate();
	//	followupPlanDate.setMonth(followupPlanDate.getMonth()
	//			+ follow.getFollowCycle());
		follow.setFollowupPlanDate(followupPlanDate);
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			InHspCaseMaster master = (InHspCaseMaster) session.get(
					InHspCaseMaster.class, group.getCaseId());
			follow.setPatientId(master.getPatientId());
			if (saveOrUpdate == 0)
				session.save(follow);
			else if (saveOrUpdate == 1) {
				List list = session.createCriteria(Followup.class).add(
						Restrictions.eq("patientId", master.getPatientId()))
						.add(Restrictions.eq("patientResearchId", -1)).add(
								Restrictions.eq("followTimes", 1)).list();
				if (list.size() > 0) {
					Followup temp = (Followup) list.get(0);
					temp.setFollowCycle(follow.getFollowCycle());
					temp.setFollowContent(follow.getFollowContent());
					temp.setFollowupPlanDate(followupPlanDate);
					session.update(temp);
				}
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}
}
