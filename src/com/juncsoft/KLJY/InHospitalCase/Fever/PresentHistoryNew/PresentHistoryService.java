package com.juncsoft.KLJY.InHospitalCase.Fever.PresentHistoryNew;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class PresentHistoryService {
	@SuppressWarnings("unchecked")
	public Fever_PresentIllnessHistoryN findContent(Long key) throws Exception {
		Fever_PresentIllnessHistoryN n = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(Fever_PresentIllnessHistoryN.class)
					.add(Restrictions.eq("caseId", key)).list();
			if (list.size() > 0) {
				n = (Fever_PresentIllnessHistoryN) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return n;
	}

	public Long saveOrUpdateContent(Fever_PresentIllnessHistoryN preIllHis) throws Exception {
		Long id = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			
			if (preIllHis.getId() == null || preIllHis.getId() <= 0) {
				id = (Long) session.save(preIllHis);
			} else {
				List<Fever_PresentIllnessHistory_Cases> prehis_caselist = session.createQuery("from Fever_PresentIllnessHistory_Cases where pid = ?").setLong(0, preIllHis.getId()).list();
				for(Fever_PresentIllnessHistory_Cases prehis_case : prehis_caselist){
					session.delete(prehis_case);
				}
				
				List<Fever_PresentHistory_MainSymptom> prehis_mainSymptomlist = session.createQuery("from Fever_PresentHistory_MainSymptom where pid = ?").setLong(0, preIllHis.getId()).list();
				for(Fever_PresentHistory_MainSymptom prehis_mainSymptom : prehis_mainSymptomlist){
					session.delete(prehis_mainSymptom);
				}
				
				List<Fever_PresentHistory_otherPositiveSymptom> prehis_otherPositivelist = session.createQuery("from Fever_PresentHistory_otherPositiveSymptom where pid = ?").setLong(0, preIllHis.getId()).list();
				for(Fever_PresentHistory_otherPositiveSymptom prehis_otherPositive : prehis_otherPositivelist){
					session.delete(prehis_otherPositive);
				}
				
				List<Fever_PresentHistory_sideSymptom> prehis_sideSysptomlist = session.createQuery("from Fever_PresentHistory_sideSymptom where pid = ?").setLong(0, preIllHis.getId()).list();
				for(Fever_PresentHistory_sideSymptom prehis_sideSysptom : prehis_sideSysptomlist){
					session.delete(prehis_sideSysptom);
				}
				
				List<Fever_PresentHistory_TreatProcess> prehis_treatlist = session.createQuery("from Fever_PresentHistory_TreatProcess where pid = ?").setLong(0, preIllHis.getId()).list();
				for(Fever_PresentHistory_TreatProcess prehis_treat : prehis_treatlist){
					session.delete(prehis_treat);
				}

				List<Fever_PresentHistory_CurrentStatus> prehis_currentStatuslist = session.createQuery("from Fever_PresentHistory_CurrentStatus where pid = ?").setLong(0, preIllHis.getId()).list();
				for(Fever_PresentHistory_CurrentStatus prehis_currentStatus : prehis_currentStatuslist){
					session.delete(prehis_currentStatus);
				}
				
				id = preIllHis.getId();
			//	session.update(preIllHis);
				session.saveOrUpdate(preIllHis);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}
	
	public String getPresentHistoryTree() throws Exception {
		List<Fever_PresentIllnessHistory_Tree> list = new ArrayList<Fever_PresentIllnessHistory_Tree>();
		String presentHistoryTree = "";
		int tier = 1;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from Fever_PresentIllnessHistory_Tree").list();
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
	private static String transferToTree(List<Fever_PresentIllnessHistory_Tree> list, int tier){
		String retStr = "";
		Boolean leaf = false;
		StringBuffer sb = new StringBuffer();
		for(Fever_PresentIllnessHistory_Tree t: list){
			List<Fever_PresentIllnessHistory_Tree> sublist = new ArrayList<Fever_PresentIllnessHistory_Tree>();
			if(t.getTier() == tier){
				for(Fever_PresentIllnessHistory_Tree _t : list){
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
	
	public static void main(String args[]){
		PresentHistoryService prehis = new PresentHistoryService();
		try {
			System.out.println(prehis.getPresentHistoryTree());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
