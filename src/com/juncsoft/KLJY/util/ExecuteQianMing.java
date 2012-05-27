package com.juncsoft.KLJY.util;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.user.entity.User;


public class ExecuteQianMing {

	public void redCasemaster() {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			String hql = "select distinct(name) from t_user where name not in ('龙江','李建军') order by name desc";
			List list = session.createSQLQuery(hql).list();
			for(int i=0,size=list.size();i<size;i++){
				Object[] obj = (Object[])list.get(i);
				String hqll = "select * from t_user where name = '"+obj[0]+"' order by id asc";
				List listl = session.createSQLQuery(hqll).list();
				if(listl != null && listl.size() >0){
					User user = (User)listl.get(0);
					String minId = user.getId().toString();
					StringBuffer sb = new StringBuffer();
					for(int j=1;j<listl.size();j++){
						User userj = (User)listl.get(i);
						sb.append(userj.getId());
					}
					sb.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}

	// A t_Inhsp_Casemaster 住院记录列表
	public void executeCasemaster(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_Inhsp_Casemaster set responsibleDoc = "+minId+" where responsibleDoc in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
	
	// B t_InHsp_Liver_Diagnosis 十二分级 chubu_inhspDoctorId
	public void executeDiagnosis(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_InHsp_Liver_Diagnosis set chubu_inhspDoctorId = "+minId+" where chubu_inhspDoctorId in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
	
	// B1 t_InHsp_Liver_Diagnosis 十二分级 chubu_treatdoctorId
	public void executeDiagnosis1(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_InHsp_Liver_Diagnosis set chubu_treatDoctorId = "+minId+" where chubu_treatDoctorId in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}	
	}
	
	// B2 t_InHsp_Liver_Diagnosis 十二分级 queding_inhspdoctorId
	public void executeDiagnosis2(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_InHsp_Liver_Diagnosis set queding_inhspDoctorId = "+minId+" where queding_inhspDoctorId in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
	
	// B3 t_InHsp_Liver_Diagnosis 十二分级 queding_treatdoctorId
	public void executeDiagnosis3(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_InHsp_Liver_Diagnosis set queding_treatDoctorId = "+minId+" where queding_treatDoctorId in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
	
	
	// B4 t_InHsp_Liver_Diagnosis 十二分级 queding_directordoctorId
	public void executeDiagnosis4(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_InHsp_Liver_Diagnosis set queding_directorDoctorId = "+minId+" where queding_directorDoctorId in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
	
	// C t_InHsp_Liver_OutHspRec 住院记录列表 inhspDoctorId
	public void executeOutHspRec(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_InHsp_Liver_OutHspRec set inhspDoctorId = "+minId+" where inhspDoctorId in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
	
	// C1 t_InHsp_Liver_OutHspRec 住院记录列表 treatDoctorId
	public void executeOutHspRec1(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_InHsp_Liver_OutHspRec set treatDoctorId = "+minId+" where treatDoctorId in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
	
	// C2 t_InHsp_Liver_OutHspRec 住院记录列表 directorDoctorId
	public void executeOutHspRec2(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_InHsp_Liver_OutHspRec set directorDoctorId = "+minId+" where directorDoctorId in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
	
	
	// D t_InHsp_Liver_RevisedDiagnosis 住院记录列表
	public void executeRevisedDiagnosis(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_InHsp_Liver_RevisedDiagnosis set dz_inhspDoctorId = "+minId+" where dz_inhspDoctorId in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}		
	}
	
	// D1 t_InHsp_Liver_RevisedDiagnosis 住院记录列表 dz_treatDoctorId
	public void executeRevisedDiagnosis1(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_InHsp_Liver_RevisedDiagnosis set dz_treatDoctorId = "+minId+" where dz_treatDoctorId in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}		
	}
	
	// D2 t_InHsp_Liver_RevisedDiagnosis 住院记录列表 dz_directorDoctorId
	public void executeRevisedDiagnosis2(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_InHsp_Liver_RevisedDiagnosis set dz_directorDoctorId = "+minId+" where dz_directorDoctorId in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}		
	}
	
	// E t_CourseRec_DailyCourseRec 住院记录列表 submiter
	public void executeDailyCourseRec(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_CourseRec_DailyCourseRec set submiter = "+minId+" where submiter in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}	
	}
	
	// E1 t_CourseRec_DailyCourseRec 住院记录列表 submiter
	public void executeDailyCourseRec1(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_CourseRec_DailyCourseRec set checker = "+minId+" where checker in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
	
	// F t_ScoreComment 住院记录列表
	public void executeScoreComment(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_ScoreComment set doc1 = "+minId+" where doc1 in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
	
	// F1 t_ScoreComment 住院记录列表
	public void executeScoreComment1(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_ScoreComment set doc2 = "+minId+" where doc2 in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
	
	// F2 t_ScoreComment 住院记录列表
	public void executeScoreComment2(Long minId,String arr) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "update t_ScoreComment set doc3 = "+minId+" where doc3 in ("+arr+")";
			session.createSQLQuery(hql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
}
