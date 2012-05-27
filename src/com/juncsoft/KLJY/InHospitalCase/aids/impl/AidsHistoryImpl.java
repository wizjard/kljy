package com.juncsoft.KLJY.InHospitalCase.aids.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.InHospitalCase.aids.dao.AidsHistoryDao;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistoryTree;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory_Cases;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory_CurrentStatus;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory_MainSymptom;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory_TreatProcess;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory_otherPositiveSymptom;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory_sideSymptom;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class AidsHistoryImpl implements AidsHistoryDao{

	public String getAidsHistoryTree() throws Exception {
		List<AidsHistoryTree> list = new ArrayList<AidsHistoryTree>();
		String presentHistoryTree = "";
		int tier = 1;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from AidsHistoryTree").list();
			presentHistoryTree = transferToTree(list, tier);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return presentHistoryTree;
	}
	private static String transferToTree(List<AidsHistoryTree> list, int tier){
		String retStr = "";
		StringBuffer sb = new StringBuffer();
		for(AidsHistoryTree t: list){
			List<AidsHistoryTree> sublist = new ArrayList<AidsHistoryTree>();
			if(t.getTier() == tier){
				for(AidsHistoryTree _t : list){
					if(t.getCode().length() < _t.getCode().length() && t.getCode().equals(_t.getCode().substring(0, 2))){
						sublist.add(_t);
					}
				}
				if(sublist.size() > 0){
					sb.append("{text:'" + t.getName() + "',leaf:false," + "children:[")
						.append(transferToTree(sublist, tier+1)).append("]},");
				}else{
					sb.append("{text:'" + t.getName() + "',leaf:true,iconCls:'icon-none','targetPage':'" + t.getHref() + "'},");
				}
			}
		}
		String str = sb.toString();
		sb.delete(0, sb.length());
		if(str != null && str.length() != 0){
			sb.append(str.substring(0, str.length() - 1));
			retStr = sb.toString();
		}
		return retStr;
	}
	public AidsHistory findContent(Long caseId) throws Exception {
		AidsHistory aidsHistory = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(AidsHistory.class).add(Restrictions.eq("caseId", caseId)).list();
			if (list.size() > 0) {
				aidsHistory = (AidsHistory) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return aidsHistory;
	}
	public Long saveOrUpdateContent(AidsHistory aidsHistory) throws Exception {
		Long id = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			
			if (aidsHistory.getId() == null || aidsHistory.getId() <= 0) {
				id = (Long) session.save(aidsHistory);
			} else {
				List<AidsHistory_Cases> aidsHistory_caselist = session.createQuery("from AidsHistory_Cases where pid = ?").setLong(0, aidsHistory.getId()).list();
				for(AidsHistory_Cases aidsHistory_case : aidsHistory_caselist){
					session.delete(aidsHistory_case);
				}
				
				List<AidsHistory_MainSymptom> aidsHistory_mainSymptomlist = session.createQuery("from AidsHistory_MainSymptom where pid = ?").setLong(0, aidsHistory.getId()).list();
				for(AidsHistory_MainSymptom aidsHistory_mainSymptom : aidsHistory_mainSymptomlist){
					session.delete(aidsHistory_mainSymptom);
				}
				
				List<AidsHistory_otherPositiveSymptom> aidsHistory_otherPositivelist = session.createQuery("from AidsHistory_otherPositiveSymptom where pid = ?").setLong(0, aidsHistory.getId()).list();
				for(AidsHistory_otherPositiveSymptom aidsHistory_otherPositive : aidsHistory_otherPositivelist){
					session.delete(aidsHistory_otherPositive);
				}
				
				List<AidsHistory_sideSymptom> aidsHistory_sideSysptomlist = session.createQuery("from AidsHistory_sideSymptom where pid = ?").setLong(0, aidsHistory.getId()).list();
				for(AidsHistory_sideSymptom aidsHistory_sideSysptom : aidsHistory_sideSysptomlist){
					session.delete(aidsHistory_sideSysptom);
				}
				
				List<AidsHistory_TreatProcess> aidsHistory_treatlist = session.createQuery("from AidsHistory_TreatProcess where pid = ?").setLong(0, aidsHistory.getId()).list();
				for(AidsHistory_TreatProcess aidsHistory_treat : aidsHistory_treatlist){
					session.delete(aidsHistory_treat);
				}

				List<AidsHistory_CurrentStatus> aidsHistory_currentStatuslist = session.createQuery("from AidsHistory_CurrentStatus where pid = ?").setLong(0, aidsHistory.getId()).list();
				for(AidsHistory_CurrentStatus aidsHistory_currentStatus : aidsHistory_currentStatuslist){
					session.delete(aidsHistory_currentStatus);
				}
				
				id = aidsHistory.getId();
				session.saveOrUpdate(aidsHistory);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}
}
