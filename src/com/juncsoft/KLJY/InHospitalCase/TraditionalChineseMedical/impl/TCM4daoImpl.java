package com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import com.juncsoft.KLJY.InHospitalCase.Liver.PresentHistory.PresentIllnessHistoryN;
import com.juncsoft.KLJY.InHospitalCase.Liver.PresentHistory.PresentIllnessHistoryNx;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.ChiefComplaint;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.ChiefComplaintSysptom;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.EpidemicDisHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.FamilyHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.LabDiagnosticExamination;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.MarritalChildbearingHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.MenstrualHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory_Infect;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory_NoInfect;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory_OuterHurt;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory_Surgery;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PersonalHistory;
import com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.dao.TCMdao;
import com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.entity.TCM4;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class TCM4daoImpl implements TCMdao {

	@SuppressWarnings("unchecked")
	public TCM4 TCM4_findByCaseId(Long id) throws Exception {
		TCM4 tcm = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(TCM4.class).add(
					Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				tcm = (TCM4) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return tcm;
	}

	public TCM4 TCM4_saveOrUpdate(TCM4 tcm) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (tcm.getId() == null || tcm.getId() <= 0) {
				tcm.setId((Long) session.save(tcm));
			} else {
				session.update(tcm);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return tcm;
	}

	@SuppressWarnings("unchecked")
	public com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.entity.LabDiagnosticExamination LabDiagnosticExamination_findByCaseID(
			Long id) throws Exception {
		com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.entity.LabDiagnosticExamination lde = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session
					.createCriteria(
							com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.entity.LabDiagnosticExamination.class)
					.add(Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				lde = (com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.entity.LabDiagnosticExamination) list
						.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return lde;
	}

	public com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.entity.LabDiagnosticExamination LabDiagnosticExamination_saveOrUpdate(
			com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.entity.LabDiagnosticExamination lde)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (lde.getId() == null || lde.getId() <= 0) {
				lde.setId((Long) session.save(lde));
				lde = (com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.entity.LabDiagnosticExamination) session
						.get(
								com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.entity.LabDiagnosticExamination.class,
								lde.getId());
			} else {
				session.update(lde);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return lde;
	}

	public void copyLiver2TCM(Long liverId, Long tcmId) throws Exception {
		try {
			// 主诉
			ChiefComplaint cc = (ChiefComplaint) copyLiver2TCM_getObject(
					ChiefComplaint.class, liverId);
			if (cc != null) {
				ChiefComplaint old = (ChiefComplaint) copyLiver2TCM_getObject(
						ChiefComplaint.class, tcmId);
				if (old != null)
					copyLiver2TCM_deleteObject(old);
				cc.setId(null);
				cc.setCaseId(tcmId);
				for (ChiefComplaintSysptom ccs : cc.getSysptoms()) {
					ccs.setCc(cc);
					ccs.setId(null);
				}
				copyLiver2TCM_saveObject(cc);
			}
			// 现病史
			// PresentIllnessHistory pih = (PresentIllnessHistory)
			// copyLiver2TCM_getObject(
			// PresentIllnessHistory.class, liverId);
			// if (pih != null) {
			// PresentIllnessHistory old = (PresentIllnessHistory)
			// copyLiver2TCM_getObject(
			// PresentIllnessHistory.class, tcmId);
			// if (old != null)
			// copyLiver2TCM_deleteObject(old);
			// pih.setId(null);
			// pih.setCaseId(tcmId);
			// pih.getIllThis().setId(null);
			// pih.getIllThis().setPi(pih);
			// for (PresentIllnessHistory_OnSet onset : pih.getOnsets()) {
			// onset.setPi(pih);
			// onset.setId(null);
			// }
			// for (PresentIllnessHistory_Treat treat : pih.getTreats()) {
			// treat.setId(null);
			// treat.setPi(pih);
			// }
			// copyLiver2TCM_saveObject(pih);
			// }
			// 新现病史
			PresentIllnessHistoryN phw = (PresentIllnessHistoryN) copyLiver2TCM_getObject(
					PresentIllnessHistoryN.class, liverId);
			if (phw != null) {
				PresentIllnessHistoryN old = (PresentIllnessHistoryN) copyLiver2TCM_getObject(
						PresentIllnessHistoryN.class, tcmId);
				if (old != null)
					copyLiver2TCM_deleteObject(old);
				phw.setId(null);
				phw.setCaseId(tcmId);
				for (PresentIllnessHistoryNx nx : phw.getNxs()) {
					nx.setId(null);
					nx.setN(phw);
				}
				copyLiver2TCM_saveObject(phw);
			}
			// 流行病学史
			EpidemicDisHistory eh = (EpidemicDisHistory) copyLiver2TCM_getObject(
					EpidemicDisHistory.class, liverId);
			if (eh != null) {
				EpidemicDisHistory old = (EpidemicDisHistory) copyLiver2TCM_getObject(
						EpidemicDisHistory.class, tcmId);
				if (old != null)
					copyLiver2TCM_deleteObject(old);
				eh.setId(null);
				eh.setCaseId(tcmId);
				copyLiver2TCM_saveObject(eh);
			}
			// 既往史
			PastHistory ph = (PastHistory) copyLiver2TCM_getObject(
					PastHistory.class, liverId);
			if (ph != null) {
				PastHistory old = (PastHistory) copyLiver2TCM_getObject(
						PastHistory.class, tcmId);
				if (old != null)
					copyLiver2TCM_deleteObject(old);
				ph.setId(null);
				ph.setCaseId(tcmId);
				for (PastHistory_Infect infect : ph.getInfects()) {
					infect.setId(null);
					infect.setPh(ph);
				}
				for (PastHistory_NoInfect noInfect : ph.getNoInfects()) {
					noInfect.setId(null);
					noInfect.setPh(ph);
				}
				for (PastHistory_OuterHurt outer : ph.getOuterHurts()) {
					outer.setId(null);
					outer.setPh(ph);
				}
				for (PastHistory_Surgery surgery : ph.getSurgerys()) {
					surgery.setId(null);
					surgery.setPh(ph);
				}
				copyLiver2TCM_saveObject(ph);
			}
			// 个人史
			PersonalHistory perh = (PersonalHistory) copyLiver2TCM_getObject(
					PersonalHistory.class, liverId);
			if (perh != null) {
				PersonalHistory old = (PersonalHistory) copyLiver2TCM_getObject(
						PersonalHistory.class, tcmId);
				if (old != null)
					copyLiver2TCM_deleteObject(old);
				perh.setId(null);
				perh.setCaseId(tcmId);
				copyLiver2TCM_saveObject(perh);
			}
			// 月经史
			MenstrualHistory mh = (MenstrualHistory) copyLiver2TCM_getObject(
					MenstrualHistory.class, liverId);
			if (mh != null) {
				MenstrualHistory old = (MenstrualHistory) copyLiver2TCM_getObject(
						MenstrualHistory.class, tcmId);
				if (old != null)
					copyLiver2TCM_deleteObject(old);
				mh.setId(null);
				mh.setCaseId(tcmId);
				copyLiver2TCM_saveObject(mh);
			}
			// 婚育史
			MarritalChildbearingHistory mch = (MarritalChildbearingHistory) copyLiver2TCM_getObject(
					MarritalChildbearingHistory.class, liverId);
			if (mch != null) {
				MarritalChildbearingHistory old = (MarritalChildbearingHistory) copyLiver2TCM_getObject(
						MarritalChildbearingHistory.class, tcmId);
				if (old != null)
					copyLiver2TCM_deleteObject(old);
				mch.setId(null);
				mch.setCaseId(tcmId);
				copyLiver2TCM_saveObject(mch);
			}
			// 家族史
			FamilyHistory fh = (FamilyHistory) copyLiver2TCM_getObject(
					FamilyHistory.class, liverId);
			if (fh != null) {
				FamilyHistory old = (FamilyHistory) copyLiver2TCM_getObject(
						FamilyHistory.class, tcmId);
				if (old != null)
					copyLiver2TCM_deleteObject(old);
				fh.setId(null);
				fh.setCaseId(tcmId);
				copyLiver2TCM_saveObject(fh);
			}
			// 辅助检查
			LabDiagnosticExamination lab = (LabDiagnosticExamination) copyLiver2TCM_getObject(
					LabDiagnosticExamination.class, liverId);
			if (fh != null) {
				LabDiagnosticExamination old = (LabDiagnosticExamination) copyLiver2TCM_getObject(
						LabDiagnosticExamination.class, tcmId);
				if (old != null)
					copyLiver2TCM_deleteObject(old);
				lab.setId(null);
				lab.setCaseId(tcmId);
				if (lab.getLab() != null) {
					lab.getLab().setId(null);
				}
				copyLiver2TCM_saveObject(lab);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private Object copyLiver2TCM_getObject(Class cls, Long caseId)
			throws Exception {
		Object object = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(cls).add(
					Restrictions.eq("caseId", caseId)).list();
			if (list.size() > 0) {
				object = list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return object;
	}

	private void copyLiver2TCM_deleteObject(Object object) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(object);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	private void copyLiver2TCM_saveObject(Object object) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.save(object);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}
}
