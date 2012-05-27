package com.juncsoft.KLJY.InHospitalCase.aids.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.aids.dao.AidsHistoryDao;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory_Cases;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory_CurrentStatus;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory_MainSymptom;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory_TreatProcess;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory_otherPositiveSymptom;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory_sideSymptom;
import com.juncsoft.KLJY.InHospitalCase.aids.impl.AidsHistoryImpl;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class AidsHistoryAction extends DispatchAction{

	private AidsHistoryDao dao = new AidsHistoryImpl();
	public ActionForward getAidsHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("[" + dao.getAidsHistoryTree() + "]");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(sb.toString());
			out.close();
		}
		return null;
	}
	
	public ActionForward findContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long caseId = Long.parseLong(request.getParameter("caseId"));
			AidsHistory aidsHistory = dao.findContent(caseId);
			res.setData(JSONObject.fromObject(aidsHistory,
					JSONValueProcessor.cycleExcludel(new String[] {"preIllHis"}, "")).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward saveOrUpdateContent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			
			JSONObject object = JSONObject.fromObject(data);
			AidsHistory aidsHistory = (AidsHistory) JSONObject
					.toBean(object, AidsHistory.class);
			
			List<AidsHistory_Cases> aidsHistory_cases = new ArrayList<AidsHistory_Cases>();
			for (Object obj : object.getJSONArray("aidsHistory_cases")) {
				JSONObject thisObj = JSONObject.fromObject(obj);
				AidsHistory_Cases aidsHistory_case = (AidsHistory_Cases) JSONObject
						.toBean(thisObj, AidsHistory_Cases.class);
				
				aidsHistory_case.setAidsHistory(aidsHistory);
				
				aidsHistory_cases.add(aidsHistory_case);
			}
			aidsHistory.setAidsHistory_cases(aidsHistory_cases);
			
			List<AidsHistory_MainSymptom> aidsHistory_mainSymptomList = new ArrayList<AidsHistory_MainSymptom>();
			for (Object obj : object.getJSONArray("aidsHistory_mainSymptom")) {
				JSONObject thisObj = JSONObject.fromObject(obj);
				AidsHistory_MainSymptom aidsHistory_mainSymptom = (AidsHistory_MainSymptom) JSONObject
						.toBean(thisObj, AidsHistory_MainSymptom.class);
				
				aidsHistory_mainSymptom.setAidsHistory(aidsHistory);
				
				aidsHistory_mainSymptomList.add(aidsHistory_mainSymptom);
			}
			aidsHistory.setAidsHistory_mainSymptom(aidsHistory_mainSymptomList);
			
			List<AidsHistory_otherPositiveSymptom> aidsHistory_otherPositiveList = new ArrayList<AidsHistory_otherPositiveSymptom>();
			for (Object obj : object.getJSONArray("aidsHistory_otherPositive")) {
				JSONObject thisObj = JSONObject.fromObject(obj);
				AidsHistory_otherPositiveSymptom aidsHistory_otherPositive = (AidsHistory_otherPositiveSymptom) JSONObject
						.toBean(thisObj, AidsHistory_otherPositiveSymptom.class);
				
				aidsHistory_otherPositive.setAidsHistory(aidsHistory);
				
				aidsHistory_otherPositiveList.add(aidsHistory_otherPositive);
			}
			aidsHistory.setAidsHistory_otherPositive(aidsHistory_otherPositiveList);
			
			List<AidsHistory_sideSymptom> aidsHistory_sideSysptomList = new ArrayList<AidsHistory_sideSymptom>();
			for (Object obj : object.getJSONArray("aidsHistory_sideSysptom")) {
				JSONObject thisObj = JSONObject.fromObject(obj);
				AidsHistory_sideSymptom aidsHistory_sideSysptom = (AidsHistory_sideSymptom) JSONObject
						.toBean(thisObj, AidsHistory_sideSymptom.class);
				
				aidsHistory_sideSysptom.setAidsHistory(aidsHistory);
				
				aidsHistory_sideSysptomList.add(aidsHistory_sideSysptom);
			}
			aidsHistory.setAidsHistory_sideSysptom(aidsHistory_sideSysptomList);
			
			List<AidsHistory_TreatProcess> aidsHistory_treatList = new ArrayList<AidsHistory_TreatProcess>();
			for (Object obj : object.getJSONArray("aidsHistory_treat")) {
				JSONObject thisObj = JSONObject.fromObject(obj);
				AidsHistory_TreatProcess aidsHistory_treat = (AidsHistory_TreatProcess) JSONObject
						.toBean(thisObj, AidsHistory_TreatProcess.class);
				
				aidsHistory_treat.setAidsHistory(aidsHistory);
				
				aidsHistory_treatList.add(aidsHistory_treat);
			}
			aidsHistory.setAidsHistory_treat(aidsHistory_treatList);
			
			List<AidsHistory_CurrentStatus> aidsHistory_currentStatusList = new ArrayList<AidsHistory_CurrentStatus>();
			for (Object obj : object.getJSONArray("aidsHistory_currentStatus")) {
				JSONObject thisObj = JSONObject.fromObject(obj);
				AidsHistory_CurrentStatus aidsHistory_currentStatus = (AidsHistory_CurrentStatus) JSONObject
						.toBean(thisObj, AidsHistory_CurrentStatus.class);
				
				aidsHistory_currentStatus.setAidsHistory(aidsHistory);
				
				aidsHistory_currentStatusList.add(aidsHistory_currentStatus);
			}
			aidsHistory.setAidsHistory_currentStatus(aidsHistory_currentStatusList);
			
			Long id = dao.saveOrUpdateContent(aidsHistory);
			res.setData(id.toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
}
