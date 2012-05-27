package com.juncsoft.KLJY.InHospitalCase.Liver.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DailyCourseRec;
import com.juncsoft.KLJY.InHospitalCase.Liver.PresentHistory.PresentIllnessHistoryN;
import com.juncsoft.KLJY.InHospitalCase.Liver.PresentHistory.PresentIllnessHistoryNx;
import com.juncsoft.KLJY.InHospitalCase.Liver.dao.LiverCaseDao;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.ChiefComplaint;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.ChiefComplaintSysptom;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.Diagnosis;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.EpidemicDisHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.FamilyHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.LabDiagnosticExamination;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.MarritalChildbearingHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.MenstrualHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.OutHspRec;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.OutHspRecYiZhu;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory_Infect;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory_NoInfect;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory_OuterHurt;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory_Surgery;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PersonalHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PhysicalExamination;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PresentIllnessHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PresentIllnessHistory_OnSet;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PresentIllnessHistory_Treat;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.RevisedDiagnosis;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.TreatResult_Dict;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.DictionaryUtil;

public class LiverCaseDaoImpl implements LiverCaseDao {

	@SuppressWarnings("unchecked")
	public ChiefComplaint ChiefComplaint_findByCaseID(Long id) throws Exception {
		ChiefComplaint cc = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(ChiefComplaint.class).add(
					Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				cc = (ChiefComplaint) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return cc;
	}

	public ChiefComplaint ChiefComplaint_saveOrUpdate(ChiefComplaint cc)
			throws Exception {
		Long id = cc.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(cc);
			} else {
				session.update(cc);
			}
			cc = (ChiefComplaint) session.get(ChiefComplaint.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return cc;
	}

	public void ChiefComplaint_deleteSysptom(Long id) throws Exception {
		ChiefComplaintSysptom ccs = new ChiefComplaintSysptom();
		ccs.setId(id);
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(ccs);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	public EpidemicDisHistory EpidemicDis_findByCaseID(Long id)
			throws Exception {
		EpidemicDisHistory edh = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(EpidemicDisHistory.class).add(
					Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				edh = (EpidemicDisHistory) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return edh;
	}

	public EpidemicDisHistory EpidemicDis_saveOrUpdate(EpidemicDisHistory edh)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (edh.getId() == null || edh.getId() <= 0) {
				edh.setId((Long) session.save(edh));
			} else {
				session.update(edh);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return edh;
	}

	@SuppressWarnings("unchecked")
	public PersonalHistory PersonalHistory_findByCaseID(Long id)
			throws Exception {
		PersonalHistory ph = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(PersonalHistory.class).add(
					Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				ph = (PersonalHistory) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return ph;
	}

	public PersonalHistory PersonalHistory_saveOrUpdate(PersonalHistory ph)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (ph.getId() == null || ph.getId() <= 0) {
				ph.setId((Long) session.save(ph));
			} else {
				session.update(ph);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return ph;
	}

	@SuppressWarnings("unchecked")
	public MenstrualHistory MenstrualHistory_findByCaseID(Long id)
			throws Exception {
		MenstrualHistory mh = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(MenstrualHistory.class).add(
					Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				mh = (MenstrualHistory) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mh;
	}

	public MenstrualHistory MenstrualHistory_saveOrUpdate(MenstrualHistory mh)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (mh.getId() == null || mh.getId() <= 0) {
				mh.setId((Long) session.save(mh));
			} else {
				session.update(mh);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mh;
	}

	@SuppressWarnings("unchecked")
	public MarritalChildbearingHistory MarritalChildbearingHistory_findByCaseID(
			Long id) throws Exception {
		MarritalChildbearingHistory mh = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(
					MarritalChildbearingHistory.class).add(
					Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				mh = (MarritalChildbearingHistory) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mh;
	}

	public MarritalChildbearingHistory MarritalChildbearingHistory_saveOrUpdate(
			MarritalChildbearingHistory mh) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (mh.getId() == null || mh.getId() <= 0) {
				mh.setId((Long) session.save(mh));
			} else {
				session.update(mh);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mh;
	}

	@SuppressWarnings("unchecked")
	public FamilyHistory FamilyHistory_findByCaseID(Long id) throws Exception {
		FamilyHistory fh = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(FamilyHistory.class).add(
					Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				fh = (FamilyHistory) list.get(0);
			} else {
				List _list = session.createCriteria(
						MarritalChildbearingHistory.class).add(
						Restrictions.eq("caseId", id)).list();
				MarritalChildbearingHistory mcbh = new MarritalChildbearingHistory();
				if (_list.size() > 0) {
					mcbh = (MarritalChildbearingHistory) _list.get(0);
					fh = new FamilyHistory();// liugang 2011-05-13 add
					fh.setCaseId(id);
					fh.setSon(mcbh.getSunCount());
					fh.setDaughter(mcbh.getDaughterCount());
				}
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return fh;
	}

	public FamilyHistory FamilyHistory_saveOrUpdate(FamilyHistory fh)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (fh.getId() == null || fh.getId() <= 0) {
				fh.setId((Long) session.save(fh));
			} else {
				session.update(fh);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return fh;
	}

	@SuppressWarnings("unchecked")
	public Diagnosis Diagnosis_findByCaseID(Long id) throws Exception {
		Diagnosis diag = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(Diagnosis.class).add(
					Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				diag = (Diagnosis) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return diag;
	}

	public Diagnosis Diagnosis_saveOrUpdate(Diagnosis diag) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (diag.getId() == null || diag.getId() <= 0) {
				diag.setId((Long) session.save(diag));
			} else {
				session.update(diag);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return diag;
	}

	@SuppressWarnings("unchecked")
	public RevisedDiagnosis RevisedDiagnosis_findByCaseID(Long id)
			throws Exception {
		RevisedDiagnosis diag = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(RevisedDiagnosis.class).add(
					Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				diag = (RevisedDiagnosis) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return diag;
	}

	public RevisedDiagnosis RevisedDiagnosis_saveOrUpdate(RevisedDiagnosis diag)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (diag.getId() == null || diag.getId() <= 0) {
				diag.setId((Long) session.save(diag));
			} else {
				session.update(diag);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return diag;
	}

	@SuppressWarnings("unchecked")
	public LabDiagnosticExamination LabDiagnosticExamination_findByCaseID(
			Long id) throws Exception {
		LabDiagnosticExamination lde = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(LabDiagnosticExamination.class)
					.add(Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				lde = (LabDiagnosticExamination) list.get(0);
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

	public LabDiagnosticExamination LabDiagnosticExamination_saveOrUpdate(
			LabDiagnosticExamination lde) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (lde.getId() == null || lde.getId() <= 0) {
				lde.setId((Long) session.save(lde));
				lde = (LabDiagnosticExamination) session.get(
						LabDiagnosticExamination.class, lde.getId());
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

	@SuppressWarnings("unchecked")
	public PhysicalExamination PhysicalExamination_findByCaseID(Long id)
			throws Exception {
		PhysicalExamination fe = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(PhysicalExamination.class).add(
					Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				fe = (PhysicalExamination) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return fe;
	}

	public PhysicalExamination PhysicalExamination_saveOrUpdate(
			PhysicalExamination fe) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (fe.getId() == null || fe.getId() <= 0) {
				fe.setId((Long) session.save(fe));
			} else {
				session.update(fe);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return fe;
	}

	public void PastHistory_delete(Long id, String flag) throws Exception {
		Object object = null;
		if (flag.equalsIgnoreCase("Infect")) {
			PastHistory_Infect obj = new PastHistory_Infect();
			obj.setId(id);
			object = obj;
		} else if (flag.equalsIgnoreCase("NoInfect")) {
			PastHistory_NoInfect obj = new PastHistory_NoInfect();
			obj.setId(id);
			object = obj;
		} else if (flag.equalsIgnoreCase("OuterHurt")) {
			PastHistory_OuterHurt obj = new PastHistory_OuterHurt();
			obj.setId(id);
			object = obj;
		} else if (flag.equalsIgnoreCase("Surgery")) {
			PastHistory_Surgery obj = new PastHistory_Surgery();
			obj.setId(id);
			object = obj;
		}
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

	@SuppressWarnings("unchecked")
	public PastHistory PastHistory_findByCaseID(Long id) throws Exception {
		PastHistory ph = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(PastHistory.class).add(
					Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				ph = (PastHistory) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return ph;
	}

	public PastHistory PastHistory_saveOrUpdate(PastHistory ph)
			throws Exception {
		Long id = ph.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(ph);
			} else {
				session.update(ph);
			}
			ph = (PastHistory) session.get(PastHistory.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return ph;
	}

	public void PresentIllnessHistory_deleteOnSet(Long id) throws Exception {
		PresentIllnessHistory_OnSet onset = new PresentIllnessHistory_OnSet();
		onset.setId(id);
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(onset);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void PresentIllnessHistory_deleteTreat(Long id) throws Exception {
		PresentIllnessHistory_Treat treat = new PresentIllnessHistory_Treat();
		treat.setId(id);
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(treat);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	public PresentIllnessHistory PresentIllnessHistory_findByCaseID(Long id)
			throws Exception {
		PresentIllnessHistory ph = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(PresentIllnessHistory.class)
					.add(Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				ph = (PresentIllnessHistory) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return ph;
	}

	public PresentIllnessHistory PresentIllnessHistory_saveOrUpdate(
			PresentIllnessHistory ph) throws Exception {
		Long id = ph.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(ph);
			} else {
				session.update(ph);
			}
			ph = (PresentIllnessHistory) session.get(
					PresentIllnessHistory.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return ph;
	}

	@SuppressWarnings("unchecked")
	public OutHspRec OutHspRec_findByCaseID(Long id) throws Exception {
		OutHspRec out = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(OutHspRec.class).add(
					Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				out = (OutHspRec) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return out;
	}

	public OutHspRec OutHspRec_saveOrUpdate(OutHspRec out) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (out.getId() == null || out.getId() <= 0) {
				Long id = (Long) session.save(out);
				out = (OutHspRec) session.get(OutHspRec.class, id);
			} else {
				session.update(out);
			}
			// 出院时更新病历主表记录
			InHspCaseMaster master = (InHspCaseMaster) session.get(
					InHspCaseMaster.class, out.getCaseId());
			if (out.getOutIlls1() != null && !out.getOutIlls1().equals("")) {
				master.setOutDate(out.getOutHspDate());
			}

			master.setOutIlls(out.getOutIlls1());
			master.setOutWardCode(out.getOutHspWard());
			session.update(master);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return out;
	}

	public void OutHspRecYiZhu_delete(OutHspRecYiZhu yizhu) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(yizhu);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	public List<OutHspRecYiZhu> OutHspRecYiZhu_findByCaseID(Long id)
			throws Exception {
		List<OutHspRecYiZhu> list = new ArrayList<OutHspRecYiZhu>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(OutHspRecYiZhu.class).add(
					Restrictions.eq("caseId", id)).addOrder(
					Order.asc("orderNo")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public OutHspRecYiZhu OutHspRecYiZhu_saveOrUpdate(OutHspRecYiZhu yizhu)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (yizhu.getId() == null || yizhu.getId() <= 0) {
				session.save(yizhu);
			} else {
				session.update(yizhu);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return yizhu;
	}

	public OutHspRec OutHspRec_nullInit(Long id) throws Exception {
		OutHspRec out = new OutHspRec();
		out.setId(new Long(-1));
		out.setLab(null);
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			InHspCaseMaster master = (InHspCaseMaster) session.get(
					InHspCaseMaster.class, id);
			out.setInHspWard(master.getInWardCode());
			out.setCaseId(master.getId());
			List list = session.createCriteria(ChiefComplaint.class).add(
					Restrictions.eq("caseId", master.getId())).list();
			String ccStr = "";
			if (list.size() > 0) {
				ChiefComplaint cc = (ChiefComplaint) list.get(0);
				if (cc != null) {
					if (cc.getCcContent() != null
							&& cc.getCcContent().length() > 0) {
						ccStr = "患者主因" + cc.getCcContent().replaceAll("。", "")
								+ "入院。";
					}
				}
			}
			list = session.createCriteria(Diagnosis.class).add(
					Restrictions.eq("caseId", master.getId())).list();
			if (list.size() > 0) {
				Diagnosis diag = (Diagnosis) list.get(0);
				if (diag != null) {
					out.setOutIlls1(diag.getQueding_diagnosis1());
					out.setOutIlls2(diag.getQueding_diagnosis2());
					out.setOutIlls3(diag.getQueding_diagnosis3());
					out.setOutIlls4(diag.getQueding_diagnosis4());
					out.setOutIlls5(diag.getQueding_diagnosis5());
					out.setOutIlls6(diag.getQueding_diagnosis6());
					out.setOutIlls7(diag.getQueding_diagnosis7());
					out.setOutIlls8(diag.getQueding_diagnosis8());
					out.setOutIlls9(diag.getQueding_diagnosis9());
					out.setOutIlls10(diag.getQueding_diagnosis10());
					out.setOutIlls11(diag.getQueding_diagnosis11());
					out.setOutIlls12(diag.getQueding_diagnosis12());
					out.setOutIlls13(diag.getQueding_diagnosis13());
					out.setOutIlls14(diag.getQueding_diagnosis14());
				}
			}
			list = session.createCriteria(PhysicalExamination.class).add(
					Restrictions.eq("caseId", master.getId())).list();
			String rst = "";
			if (list.size() > 0) {
				PhysicalExamination exam = (PhysicalExamination) list.get(0);
				if (exam != null) {
					String temp = exam.getSmtz_xueya();
					String temp2 = exam.getSmtz_xueya2();
					rst += "BP：" + temp + "/" + temp2 + "mmHg,";
					temp = DictionaryUtil.getPublicDictionaryText(
							"EMR-liver-PhysicalExamination", "ybzc_shenzhi",
							exam.getYbzc_shenzhi());
					if (temp.equals("其它")) {
						temp = exam.getYbzc_shenzhi0();
					}
					rst += (temp == null || temp.length() == 0) ? "" : "神志:"
							+ temp + ",";
					temp = DictionaryUtil.getPublicDictionaryText(
							"EMR-liver-PhysicalExamination", "pfnm_seze", exam
									.getPfnm_seze());
					if (temp.equals("其它")) {
						temp = exam.getPfnm_seze0();
					}
					rst += (temp == null || temp.length() == 0) ? "" : "皮肤色泽:"
							+ temp + ",";
					temp = DictionaryUtil.getPublicDictionaryText(
							"EMR-liver-PhysicalExamination", "eyes_gongmo",
							exam.getEyes_gongmo());
					rst += (temp == null || temp.length() == 0) ? "" : "巩膜:"
							+ temp + ",";
					if (exam.getFei_huxiyin() == 1) {
						rst += "肺呼吸音：" + exam.getFei_huxiyinDesc() + ",";
					} else {
						rst += "肺呼吸音:正常,";
					}
					temp = exam.getXinz_xinlv();
					rst += (temp == null || temp.length() == 0) ? "" : "心率:"
							+ temp + "次/分,";
					temp = DictionaryUtil.getPublicDictionaryText(
							"EMR-liver-PhysicalExamination", "xinz_xinrate",
							exam.getXinz_xinrate());
					rst += (temp == null || temp.length() == 0) ? "" : "心律:"
							+ temp + ",";
					temp = DictionaryUtil.getPublicDictionaryText(
							"EMR-liver-PhysicalExamination", "fubu_fubi", exam
									.getFubu_fubi());
					if (temp.equals("其它")) {
						temp = exam.getFubu_fubi0();
					}
					rst += (temp == null || temp.length() == 0) ? "" : "腹壁:"
							+ temp + ",";
					temp = DictionaryUtil.getPublicDictionaryText(
							"EMR-liver-PhysicalExamination", "fubu_jijzh", exam
									.getFubu_yatong());
					if (temp.equals("其它")) {
						temp = exam.getFubu_yatong0();
					}
					rst += (temp == null || temp.length() == 0) ? "" : "压痛:"
							+ temp + ",";
					temp = DictionaryUtil.getPublicDictionaryText(
							"EMR-liver-PhysicalExamination", "fubu_jijzh", exam
									.getFubu_fantt());
					if (temp.equals("其它")) {
						temp = exam.getFubu_fantt0();
					}
					rst += (temp == null || temp.length() == 0) ? "" : "反跳痛:"
							+ temp + ",";
					if (exam.getFubu_ganzang() == 1) {
						rst += "肝脏:" + exam.getFubu_ganzangDesc() + ",";
					} else {
						rst += "肝脏:未触及,";
					}
					if (exam.getFubu_pi() == 1) {
						rst += "脾脏:" + exam.getFubu_piDesc() + ",";
					} else {
						rst += "脾脏:未触及,";
					}
					if (exam.getFubu_ganqukt() == 1) {
						rst += "肝区叩痛:有,";
					} else {
						rst += "肝区叩痛:无,";
					}
					temp = DictionaryUtil.getPublicDictionaryText(
							"EMR-liver-PhysicalExamination", "fubu_murphy",
							exam.getFubu_yidong());
					rst += (temp == null || temp.length() == 0) ? "" : "移动性浊音:"
							+ temp + ",";
					temp = DictionaryUtil.getPublicDictionaryText(
							"EMR-liver-PhysicalExamination", "jisi_xiazhi",
							exam.getJisi_xiazhi());
					if (temp.equals("其它")) {
						temp = exam.getJisi_xiazhi0();
					}
					rst += (temp == null || temp.length() == 0) ? "" : "下肢水肿:"
							+ temp + ",";
					if (rst.length() > 0) {
						rst = "入院查体：" + rst.substring(0, rst.length() - 1)
								+ "。";
					}
				}
				out.setInHspStatu(ccStr + rst);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return out;
	}

	public JSONArray getTreatResult_Dict(String id) throws Exception {
		Session session = null;
		Transaction tran = null;
		JSONArray data = new JSONArray();
		JSONObject json = null;
		List<TreatResult_Dict> trdList = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String treeNode = "";
			if (!id.equals("-1")) {
				treeNode = (String) session.createSQLQuery(
						"select treeNode from TreatResult_Dict where coding="
								+ "'" + id + "'").list().iterator().next();
				treeNode = String.valueOf(Long.parseLong(treeNode) + 1L);
			}
			if (id.equals("-1")) {
				trdList = session.createCriteria(TreatResult_Dict.class).add(
						Restrictions.eq("treeNode", "1")).list();
			} else {
				trdList = session.createCriteria(TreatResult_Dict.class).add(
						Restrictions.like("coding", "%" + id + "%")).add(
						Restrictions.eq("treeNode", treeNode)).list();
			}
			List<TreatResult_Dict> tList = null;
			for (TreatResult_Dict t : trdList) {
				json = new JSONObject();
				tList = new ArrayList<TreatResult_Dict>();
				if (!treeNode.equals("") && !id.endsWith("-1")) {
					tList = session.createCriteria(TreatResult_Dict.class).add(
							Restrictions.like("coding", "%" + t.getCoding()
									+ "%")).add(
							Restrictions.eq("treeNode", String.valueOf(Long
									.parseLong(treeNode) + 1L))).list();
					if (tList.size() == 0) {
						json.put("iconCls", "icon-none");
						json.put("leaf", "true");
					}
				}

				json.put("id", t.getCoding());
				json.put("text", t.getNodeName());
				data.add(json);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return data;
	}

	public boolean checkSubmitCase(Long kid) {
		Session session = null;
		Transaction tran = null;
		boolean result = false;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Diagnosis diag = null;
			List list = session.createCriteria(Diagnosis.class).add(
					Restrictions.eq("caseId", kid)).list();
			if (list != null && list.size() > 0) {
				diag = (Diagnosis) list.get(0);
				if (diag != null) {
					if (diag.getChubu_treatDoctorId() == null
							|| diag.getQueding_inhspDoctorId() == null) {
						result = true;
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return result;
	}

	public void memberCaseCopyLiverCaseByCaseId(Long id, Session session) {
		try {
			Long newCaseId = getCaseId(id);

			// 主诉
			ChiefComplaint cc = (ChiefComplaint) copyLiver2TCM_getObject(
					ChiefComplaint.class, id);
			if (cc != null) {
				Long ccId = cc.getId();// 暂存主诉ID
				cc.setId(null);
				cc.setCaseId(newCaseId);
				ChiefComplaint newCc = new ChiefComplaint();
				// 保存
				newCc.setId(copyLiver2TCM_saveObject(cc));
				executeChiefComplaint(ccId,newCc);
			}
			// 新现病史
			/*
			 * PresentIllnessHistoryN phw = (PresentIllnessHistoryN)
			 * copyLiver2TCM_getObject( PresentIllnessHistoryN.class,
			 * id,session); if (phw != null) { phw.setId(null);
			 * phw.setCaseId(newCaseId); for (PresentIllnessHistoryNx nx :
			 * phw.getNxs()) { nx.setId(null); nx.setN(phw); }
			 * copyLiver2TCM_saveObject(phw); }
			 */
			// 流行病学史
			EpidemicDisHistory eh = (EpidemicDisHistory) copyLiver2TCM_getObject(
					EpidemicDisHistory.class, id);
			if (eh != null) {
				eh.setId(null);
				eh.setCaseId(newCaseId);
				copyLiver2TCM_saveObject(eh);
			}
			// 既往史
			PastHistory ph = (PastHistory) copyLiver2TCM_getObject(
					PastHistory.class, id);
			if (ph != null) {
				Long phId = ph.getId();
				ph.setId(null);
				ph.setCaseId(newCaseId);

				PastHistory newPh = new PastHistory();
				newPh.setId(copyLiver2TCM_saveObject(ph));
				executePastHistory(phId,newPh);
			}
			// 个人史
			PersonalHistory perh = (PersonalHistory) copyLiver2TCM_getObject(
					PersonalHistory.class, id);
			if (perh != null) {
				perh.setId(null);
				perh.setCaseId(newCaseId);
				copyLiver2TCM_saveObject(perh);
			}
			// 月经史
			MenstrualHistory mh = (MenstrualHistory) copyLiver2TCM_getObject(
					MenstrualHistory.class, id);
			if (mh != null) {
				mh.setId(null);
				mh.setCaseId(newCaseId);
				copyLiver2TCM_saveObject(mh);
			}
			// 婚育史
			MarritalChildbearingHistory mch = (MarritalChildbearingHistory) copyLiver2TCM_getObject(
					MarritalChildbearingHistory.class, id);
			if (mch != null) {
				mch.setId(null);
				mch.setCaseId(newCaseId);
				copyLiver2TCM_saveObject(mch);
			}
			// 家族史
			FamilyHistory fh = (FamilyHistory) copyLiver2TCM_getObject(
					FamilyHistory.class, id);
			if (fh != null) {
				fh.setId(null);
				fh.setCaseId(newCaseId);
				copyLiver2TCM_saveObject(fh);
			}
			// 辅助检查
			LabDiagnosticExamination lab = (LabDiagnosticExamination) copyLiver2TCM_getObject(
					LabDiagnosticExamination.class, id);
			if (fh != null) {
				lab.setId(null);
				lab.setCaseId(newCaseId);
				copyLiver2TCM_saveObject(lab);
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	private Long copyLiver2TCM_saveObject(Object object) throws Exception {
		Session session = null;
		Transaction tran = null;
		Long result = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			result = Long.parseLong(session.save(object).toString());
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return result;
	}

	private Long getCaseId(Long id) {
		Session session = null;
		Transaction tran = null;
		Long caseId = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			// 入院記錄
			InHspCaseMaster caseHsp = (InHspCaseMaster) session.get(
					InHspCaseMaster.class, id);
			if (caseHsp != null) {
				InHspCaseMaster newInHspCaseMaster = new InHspCaseMaster();
				newInHspCaseMaster.setPatientId(caseHsp.getPatientId());
				newInHspCaseMaster.setInHspTimes(caseHsp.getInHspTimes());
				newInHspCaseMaster.setInHspDate(caseHsp.getInHspDate());
				newInHspCaseMaster.setInHspDateModify(caseHsp
						.getInHspDateModify());
				newInHspCaseMaster.setInWardCode(caseHsp.getInWardCode());
				newInHspCaseMaster.setInIlls(caseHsp.getInIlls());
				newInHspCaseMaster.setInStatus(caseHsp.getInStatus());
				newInHspCaseMaster.setOutDate(caseHsp.getOutDate());
				newInHspCaseMaster.setOutIlls(caseHsp.getOutIlls());
				newInHspCaseMaster.setOutWardCode(caseHsp.getOutWardCode());
				newInHspCaseMaster.setResponsibleDoc(caseHsp
						.getResponsibleDoc());
				newInHspCaseMaster.setCurrentWordCode(caseHsp
						.getCurrentWordCode());
				newInHspCaseMaster.setCaseModuleId(caseHsp.getCaseModuleId());
				newInHspCaseMaster.setAge(caseHsp.getAge());
				newInHspCaseMaster.setFlag(1);// 设置为该会员的入会病历
				caseId = Long.parseLong(session.save(newInHspCaseMaster)
						.toString());
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return caseId;
	}
	
	//主诉
	private void executeChiefComplaint(Long ccId,ChiefComplaint newCc){
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hqlChief = "from ChiefComplaintSysptom where cc=" + ccId;
			List listChiefComplaintSysptom = session.createQuery(hqlChief)
					.list();
			for (int iChief = 0; iChief < listChiefComplaintSysptom.size(); iChief++) {
				ChiefComplaintSysptom ccs = (ChiefComplaintSysptom) listChiefComplaintSysptom
						.get(iChief);
				ccs.setId(null);
				ccs.setCc(newCc);
				session.save(ccs);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
	
	//既往史
	private void executePastHistory(Long phId,PastHistory newPh){
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hqlPast = "from PastHistory_Infect where ph=" + phId;
			List listPastHistory_Infect = session.createQuery(hqlPast)
					.list();
			for (int iPast = 0; iPast < listPastHistory_Infect.size(); iPast++) {
				PastHistory_Infect infect = (PastHistory_Infect) listPastHistory_Infect
						.get(iPast);
				infect.setId(null);
				infect.setPh(newPh);
				session.save(infect);
			}
			String hqlNoInfect = "from PastHistory_NoInfect where ph="
					+ phId;
			List listPastHistory_NoInfect = session
					.createQuery(hqlNoInfect).list();
			for (int iNoInfect = 0; iNoInfect < listPastHistory_NoInfect
					.size(); iNoInfect++) {
				PastHistory_NoInfect noInfect = (PastHistory_NoInfect) listPastHistory_NoInfect
						.get(iNoInfect);
				noInfect.setId(null);
				noInfect.setPh(newPh);
				session.save(noInfect);
			}

			String hqlouter = "from PastHistory_OuterHurt where ph=" + phId;
			List listPastHistory_OuterHurt = session.createQuery(hqlouter)
					.list();
			for (int iouter = 0; iouter < listPastHistory_OuterHurt.size(); iouter++) {
				PastHistory_OuterHurt outer = (PastHistory_OuterHurt) listPastHistory_OuterHurt
						.get(iouter);
				outer.setId(null);
				outer.setPh(newPh);
				session.save(outer);
			}
			String hqlsurgery = "from PastHistory_Surgery where ph=" + phId;
			List listPastHistory_Surgery = session.createQuery(hqlsurgery)
					.list();
			for (int isurgery = 0; isurgery < listPastHistory_Surgery
					.size(); isurgery++) {
				PastHistory_Surgery surgery = (PastHistory_Surgery) listPastHistory_Surgery
						.get(isurgery);
				surgery.setId(null);
				surgery.setPh(newPh);
				session.save(surgery);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
}
