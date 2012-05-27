package com.juncsoft.KLJY.patientanddoctoroperator.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.patientanddoctoroperator.dao.DoctorReplyRecordAndPatientQuestionsDao;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.DoctorReplyRecordAndPatientQuestions;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PatientConsulting;
import com.juncsoft.KLJY.util.BaseService;
import com.juncsoft.KLJY.util.DatabaseUtil;

/**
 * 医生回复和病人提问管理
 * 2011-05-31
 * @author liugang
 *
 */
public class DoctorReplyRecordAndPatientQuestionsImpl extends BaseService
		implements DoctorReplyRecordAndPatientQuestionsDao {

	public List<DoctorReplyRecordAndPatientQuestions> findAllDoctorReplyRecordAndPatientQuestionsByPCId(
			Long pcId) {
		String hql = "from DoctorReplyRecordAndPatientQuestions where pcId ="+pcId+" order by id asc";
		return super.findAllObject(hql);
	}

	/**
	 * 保存并返回回复内容
	 */
	public DoctorReplyRecordAndPatientQuestions saveDoctorReplyRecordAndPatientQuestions(
			DoctorReplyRecordAndPatientQuestions doctorReplyRecordAndPatientQuestions) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if(doctorReplyRecordAndPatientQuestions.getDrAndpqFlag() == 0){
				String hql ="select tda.deptCode,tda.grounpId from t_DepartmentAndGrounpMiddle tda inner join" +
						" t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and doctorId="+doctorReplyRecordAndPatientQuestions.getDrAndpqId();
				List list = session.createSQLQuery(hql).list();
				if(list != null && list.size() > 0){
					Object[] obj = (Object[])list.get(0);
					doctorReplyRecordAndPatientQuestions.setDeptCode(obj[0].toString());
					doctorReplyRecordAndPatientQuestions.setGrounpId(Long.parseLong(obj[1].toString()));
				}
				PatientConsulting pc = (PatientConsulting)session.get(PatientConsulting.class,doctorReplyRecordAndPatientQuestions.getPcId());
				pc.setReadCount(0);
				session.save(pc);
			}
			doctorReplyRecordAndPatientQuestions.setId((Long)session.save(doctorReplyRecordAndPatientQuestions));
			tran.commit();
		} catch (Exception e) {
			throw new RuntimeException("保存并返回回复内容出错",e);
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return doctorReplyRecordAndPatientQuestions;
	}

	/**
	 * 医生屏蔽病人的回复，即拒绝回答
	 */
	public boolean cancelMessageByDoctorOperation(Long pcId) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from DoctorReplyRecordAndPatientQuestions where pcId = "+pcId+" order by id desc";
			List list = session.createQuery(hql).list();
			if(list != null && list.size() > 0){
				DoctorReplyRecordAndPatientQuestions doctorReplyRecordAndPatientQuestions = (DoctorReplyRecordAndPatientQuestions)list.get(0);
				doctorReplyRecordAndPatientQuestions.setDrAndpgCancel(0);
				session.update(doctorReplyRecordAndPatientQuestions);
				tran.commit();
				return true;
			}else{
				tran.commit();
				return false;
			}
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			throw new RuntimeException("医生屏蔽病人的回复，即拒绝回答出错", e);
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}

}
