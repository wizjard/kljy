package com.juncsoft.KLJY.InHospitalCase.Fever.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.InHospitalCase.Fever.dao.IFeverDao;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.SysDictModules;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverAfterTreatment;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverAssociatedSystom;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverCurrentState;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverFeverHistoryContent;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverFeverHistoryIllness;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverIncidence;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverMedicineAfterTreatment;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverMedicineCurrentState;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverMedicineFeverHistoryIllness;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverMedicineIncidence;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverNegativeSystom;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverOtherSystom;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverOthersSystom;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverSystomEvolution;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.ChiefComplaint;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.ChiefComplaintSysptom;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class feverDaoImpl implements IFeverDao {
	
	// 主诉
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

	
/////////////////////////////发热中医///////////////////////////////////////////
	//发病情况添加或修改
	public TInHspFeverMedicineIncidence feverMedicineIncidence_saveOrUpdate(TInHspFeverMedicineIncidence incidence)throws Exception{
		Long id = incidence.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			//开始事物
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(incidence);
			} else {
				session.update(incidence);
			}
			incidence = (TInHspFeverMedicineIncidence) session.get(TInHspFeverMedicineIncidence.class, id);
			//提交事物
			tran.commit();
		} catch (Exception e) {
			//回滚事物
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return incidence;
	}
	//查找发病情况
	public TInHspFeverMedicineIncidence selectFeverMedicineIncidenceByCaseId(Long caseId) throws Exception{
		
		TInHspFeverMedicineIncidence feverContent = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(TInHspFeverMedicineIncidence.class)
					.add(Restrictions.eq("caseId", caseId)).addOrder(Order.desc("id")).list();
			if (list.size() > 0) {
				feverContent = (TInHspFeverMedicineIncidence) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return feverContent;
	}
	//主要症状添加或修改
	public TInHspFeverMedicineFeverHistoryIllness feverMedicineMainSysytom_saveOrUpdate(TInHspFeverMedicineFeverHistoryIllness incidence)throws Exception{
		Long id = incidence.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			//开始事物
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(incidence);
			} else {
				session.update(incidence);
			}
			incidence = (TInHspFeverMedicineFeverHistoryIllness) session.get(TInHspFeverMedicineFeverHistoryIllness.class, id);
			//提交事物
			tran.commit();
		} catch (Exception e) {
			//回滚事物
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return incidence;
	}
	//查找主要症状
	public TInHspFeverMedicineFeverHistoryIllness selectFeverMedicineMainSystomByCaseId(Long caseId) throws Exception{
		TInHspFeverMedicineFeverHistoryIllness feverContent = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(TInHspFeverMedicineFeverHistoryIllness.class)
					.add(Restrictions.eq("caseId", caseId)).addOrder(Order.desc("id")).list();
			if (list.size() > 0) {
				feverContent = (TInHspFeverMedicineFeverHistoryIllness) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return feverContent;
	}
	//诊疗经过添加或修改
	public TInHspFeverMedicineAfterTreatment feverMedicineAfterTreatment_saveOrUpdate(TInHspFeverMedicineAfterTreatment incidence)throws Exception{
		Long id = incidence.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			//开始事物
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(incidence);
			} else {
				session.update(incidence);
			}
			incidence = (TInHspFeverMedicineAfterTreatment) session.get(TInHspFeverMedicineAfterTreatment.class, id);
			//提交事物
			tran.commit();
		} catch (Exception e) {
			//回滚事物
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return incidence;
	}
	//查找诊疗经过
	public TInHspFeverMedicineAfterTreatment selectFeverMedicineAfterTreatmentByCaseId(Long caseId) throws Exception{
		TInHspFeverMedicineAfterTreatment feverContent = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(TInHspFeverMedicineAfterTreatment.class)
					.add(Restrictions.eq("caseId", caseId)).addOrder(Order.desc("id")).list();
			if (list.size() > 0) {
				feverContent = (TInHspFeverMedicineAfterTreatment) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return feverContent;
	}
	//目前状况添加或修改
	public TInHspFeverMedicineCurrentState feverMedicineCurrentState_saveOrUpdate(TInHspFeverMedicineCurrentState incidence)throws Exception{
		Long id = incidence.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			//开始事物
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(incidence);
			} else {
				session.update(incidence);
			}
			incidence = (TInHspFeverMedicineCurrentState) session.get(TInHspFeverMedicineCurrentState.class, id);
			//提交事物
			tran.commit();
		} catch (Exception e) {
			//回滚事物
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return incidence;
	}
	//查找目前状况
	public TInHspFeverMedicineCurrentState selectFeverMedicineCurrentStateByCaseId(Long caseId) throws Exception{
		TInHspFeverMedicineCurrentState feverContent = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(TInHspFeverMedicineCurrentState.class)
					.add(Restrictions.eq("caseId", caseId)).addOrder(Order.desc("id")).list();
			if (list.size() > 0) {
				feverContent = (TInHspFeverMedicineCurrentState) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return feverContent;
	}
	/**
	 * 查询发热中医的公共字典
	 */
	public List<SysDictModules> selectFeverMedicine(String name)throws Exception{
		List<SysDictModules> list = new ArrayList<SysDictModules>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from SysDictModules where pid =  (select id from SysDictModules where name like '"+name+"')").list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}
	/**
	 * 查询历史信息
	 * 
	 */
	public TInHspFeverFeverHistoryContent feverHistoryContent_saveOrUpdate(TInHspFeverFeverHistoryContent fever)
	throws Exception{
		Long id = fever.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			//开始事物
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(fever);
			} else {
				session.update(fever);
			}
			fever = (TInHspFeverFeverHistoryContent) session.get(TInHspFeverFeverHistoryContent.class, id);
			//提交事物
			tran.commit();
		} catch (Exception e) {
			//回滚事物
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return fever;
	}
	public TInHspFeverFeverHistoryContent selectFeverHistoryContentByCaseId(Long caseId,int feverType) throws Exception{
		TInHspFeverFeverHistoryContent feverContent = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(TInHspFeverFeverHistoryContent.class)
					.add(Restrictions.eq("caseId", caseId))
					.add(Restrictions.eq("feverType", feverType))
					.addOrder(Order.desc("id")).list();
			if (list.size() > 0) {
				feverContent = (TInHspFeverFeverHistoryContent) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return feverContent;
	}
	/**
	 * 发热西医添加病例
	 */
	public TInHspFeverFeverHistoryIllness feverHistory_saveOrUpdate(
			TInHspFeverFeverHistoryIllness fever) throws Exception {
		Long id = fever.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			//开始事物
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(fever);
			} else {
				session.update(fever);
			}
			fever = (TInHspFeverFeverHistoryIllness) session.get(TInHspFeverFeverHistoryIllness.class, id);
			//提交事物
			tran.commit();
		} catch (Exception e) {
			//回滚事物
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return fever;
	}
	/**
	 * 添加或修改发病情况
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverIncidence incidence_saveOrUpdate(TInHspFeverIncidence incidence)
	throws Exception{
		Long id = incidence.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			//开始事物
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(incidence);
			} else {
				session.update(incidence);
			}
			incidence = (TInHspFeverIncidence) session.get(TInHspFeverIncidence.class, id);
			//提交事物
			tran.commit();
		} catch (Exception e) {
			//回滚事物
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return incidence;
	}
	public TInHspFeverAfterTreatment afterTreatment_saveOrUpdate(
			TInHspFeverAfterTreatment afterTreatment) throws Exception {
		Long id = afterTreatment.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			//开始事物
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(afterTreatment);
			} else {
				session.update(afterTreatment);
			}
			afterTreatment = (TInHspFeverAfterTreatment) session.get(TInHspFeverAfterTreatment.class, id);
			//提交事物
			tran.commit();
		} catch (Exception e) {
			//回滚事物
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return afterTreatment;
	}
	public TInHspFeverAssociatedSystom assocatesSystom_saveOrUpdate(
			TInHspFeverAssociatedSystom assocatesSystom) throws Exception {
		Long id = assocatesSystom.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			//开始事物
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(assocatesSystom);
			} else {
				session.update(assocatesSystom);
			}
			assocatesSystom = (TInHspFeverAssociatedSystom) session.get(TInHspFeverAssociatedSystom.class, id);
			//提交事物
			tran.commit();
		} catch (Exception e) {
			//回滚事物
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return assocatesSystom;
	}
	public TInHspFeverCurrentState currentState_saveOrUpdate(
			TInHspFeverCurrentState currentState) throws Exception {
		Long id = currentState.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			//开始事物
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(currentState);
			} else {
				session.update(currentState);
			}
			currentState = (TInHspFeverCurrentState) session.get(TInHspFeverCurrentState.class, id);
			//提交事物
			tran.commit();
		} catch (Exception e) {
			//回滚事物
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return currentState;
	}
	public TInHspFeverNegativeSystom negative_saveOrUpdate(
			TInHspFeverNegativeSystom negative) throws Exception {
		Long id = negative.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			//开始事物
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(negative);
			} else {
				session.update(negative);
			}
			negative = (TInHspFeverNegativeSystom) session.get(TInHspFeverNegativeSystom.class, id);
			//提交事物
			tran.commit();
		} catch (Exception e) {
			//回滚事物
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return negative;
	}
	public TInHspFeverOtherSystom otherSystom_saveOrUpdate(
			TInHspFeverOtherSystom otherSystom) throws Exception {
		Long id = otherSystom.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			//开始事物
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(otherSystom);
			} else {
				session.update(otherSystom);
			}
			otherSystom = (TInHspFeverOtherSystom) session.get(TInHspFeverOtherSystom.class, id);
			//提交事物
			tran.commit();
		} catch (Exception e) {
			//回滚事物
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return otherSystom;
	}
	public TInHspFeverOthersSystom othersSystom_saveOrUpdate(
			TInHspFeverOthersSystom othersSystom) throws Exception {
		Long id = othersSystom.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			//开始事物
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(othersSystom);
			} else {
				session.update(othersSystom);
			}
			othersSystom = (TInHspFeverOthersSystom) session.get(TInHspFeverOthersSystom.class, id);
			//提交事物
			tran.commit();
		} catch (Exception e) {
			//回滚事物
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return othersSystom;
	}
	public TInHspFeverSystomEvolution systomEvolution_saveOrUpdate(
			TInHspFeverSystomEvolution systomEvolution) throws Exception {
		Long id = systomEvolution.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			//开始事物
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(systomEvolution);
			} else {
				session.update(systomEvolution);
			}
			systomEvolution = (TInHspFeverSystomEvolution) session.get(TInHspFeverSystomEvolution.class, id);
			//提交事物
			tran.commit();
		} catch (Exception e) {
			//回滚事物
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return systomEvolution;
	}
	/**
	 * 根据病人查询病例信息（主要症状）
	 */
	public TInHspFeverFeverHistoryIllness selectFeverHistoryByCaseId(Long caseId)
			throws Exception {
		TInHspFeverFeverHistoryIllness fever = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(TInHspFeverFeverHistoryIllness.class)
					.add(Restrictions.eq("caseId", caseId)).addOrder(Order.desc("id")).list();
			if (list.size() > 0) {
				fever = (TInHspFeverFeverHistoryIllness) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return fever;
	}
	/**
	 * 添加或修改发病情况
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverAfterTreatment selectAfterTreatmentByCaseId(Long caseId)
	throws Exception{
		
		TInHspFeverAfterTreatment afterTreatment = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(TInHspFeverAfterTreatment.class)
					.add(Restrictions.eq("caseId", caseId)).addOrder(Order.desc("id")).list();
			if (list.size() > 0) {
				afterTreatment = (TInHspFeverAfterTreatment) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return afterTreatment;
	}
	/**
	 * 根据病人查询病例信息
	 * @param caseId
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverIncidence selectIncidenceByCaseId(Long caseId) throws Exception{
		TInHspFeverIncidence incidence = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(TInHspFeverIncidence.class)
					.add(Restrictions.eq("caseId", caseId)).addOrder(Order.desc("id")).list();
			if (list.size() > 0) {
				incidence = (TInHspFeverIncidence) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return incidence;
	}
	
	public TInHspFeverAssociatedSystom selectAssocatesSystomByCaseId(Long caseId)
			throws Exception {
		TInHspFeverAssociatedSystom associatedSystom = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(TInHspFeverAssociatedSystom.class)
					.add(Restrictions.eq("caseId", caseId)).addOrder(Order.desc("id")).list();
			if (list.size() > 0) {
				associatedSystom = (TInHspFeverAssociatedSystom) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return associatedSystom;
	}
	public TInHspFeverCurrentState selectCurrentStateByCaseId(Long caseId) throws Exception {
	TInHspFeverCurrentState currentState = null;
	Session session = null;
	Transaction tran = null;
	try {
		session = DatabaseUtil.getSession();
		tran = session.beginTransaction();
		List list = session.createCriteria(TInHspFeverCurrentState.class)
				.add(Restrictions.eq("caseId", caseId)).addOrder(Order.desc("id")).list();
		if (list.size() > 0) {
			currentState = (TInHspFeverCurrentState) list.get(0);
		}
		tran.commit();
	} catch (Exception e) {
		tran.rollback();
		throw e;
	} finally {
		DatabaseUtil.closeResource(session);
	}
	return currentState;
	}
	public TInHspFeverNegativeSystom selectNegativeByCaseId(Long caseId)
			throws Exception {
		TInHspFeverNegativeSystom negative = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(TInHspFeverNegativeSystom.class)
					.add(Restrictions.eq("caseId", caseId)).addOrder(Order.desc("id")).list();
			if (list.size() > 0) {
				negative = (TInHspFeverNegativeSystom) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return negative;
	}
	public TInHspFeverOtherSystom selectOtherSystomByCaseId(Long caseId)
			throws Exception {
		TInHspFeverOtherSystom otherSystom = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(TInHspFeverOtherSystom.class)
					.add(Restrictions.eq("caseId", caseId)).addOrder(Order.desc("id")).list();
			if (list.size() > 0) {
				otherSystom = (TInHspFeverOtherSystom) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return otherSystom;
	}
	public TInHspFeverOthersSystom selectOthersSystomByCaseId(Long caseId)
			throws Exception {
		TInHspFeverOthersSystom othersSystom = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(TInHspFeverOthersSystom.class)
					.add(Restrictions.eq("caseId", caseId)).addOrder(Order.desc("id")).list();
			if (list.size() > 0) {
				othersSystom = (TInHspFeverOthersSystom) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return othersSystom;
	}
	public TInHspFeverSystomEvolution selectSystomEvolutionByCaseId(Long caseId)
			throws Exception {
		TInHspFeverSystomEvolution systomEvolution = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(TInHspFeverSystomEvolution.class)
					.add(Restrictions.eq("caseId", caseId)).addOrder(Order.desc("id")).list();
			if (list.size() > 0) {
				systomEvolution = (TInHspFeverSystomEvolution) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return systomEvolution;
	}
	public static void main(String[] args) {
		feverDaoImpl dao=new feverDaoImpl();
		TInHspFeverFeverHistoryContent content=null;
		try {
			content = dao.selectFeverHistoryContentByCaseId(Long.parseLong("4926"), 0);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(content.getId());
	}
}
