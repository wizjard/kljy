package com.juncsoft.KLJY.InHospitalCase.Fever.PresentHistoryNew;

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
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class PresentHistoryAction extends DispatchAction {
	private PresentHistoryService service = new PresentHistoryService();
	public ActionForward getPresentHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("[" + service.getPresentHistoryTree() + "]");
			
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
			Long key = Long.parseLong(request.getParameter("caseId"));
			Fever_PresentIllnessHistoryN preIllHis = service.findContent(key);
			res.setData(JSONObject.fromObject(preIllHis,
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
			Fever_PresentIllnessHistoryN preIllHis = (Fever_PresentIllnessHistoryN) JSONObject
					.toBean(object, Fever_PresentIllnessHistoryN.class);
			List<Fever_PresentIllnessHistory_Cases> prehis_cases = new ArrayList<Fever_PresentIllnessHistory_Cases>();
			for (Object obj : object.getJSONArray("prehis_cases")) {
				JSONObject thisObj = JSONObject.fromObject(obj);
				Fever_PresentIllnessHistory_Cases prehis_case = (Fever_PresentIllnessHistory_Cases) JSONObject
						.toBean(thisObj, Fever_PresentIllnessHistory_Cases.class);
				
		//		prehis_case.setId(null);
				prehis_case.setPreIllHis(preIllHis);
				
				prehis_cases.add(prehis_case);
			}
			preIllHis.setPrehis_cases(prehis_cases);
			
			List<Fever_PresentHistory_MainSymptom> prehis_mainSymptomList = new ArrayList<Fever_PresentHistory_MainSymptom>();
			for (Object obj : object.getJSONArray("prehis_mainSymptom")) {
				JSONObject thisObj = JSONObject.fromObject(obj);
				Fever_PresentHistory_MainSymptom prehis_mainSymptom = (Fever_PresentHistory_MainSymptom) JSONObject
						.toBean(thisObj, Fever_PresentHistory_MainSymptom.class);
				
		//		prehis_mainSymptom.setId(null);
				prehis_mainSymptom.setPreIllHis(preIllHis);
				
				prehis_mainSymptomList.add(prehis_mainSymptom);
			}
			preIllHis.setPrehis_mainSymptom(prehis_mainSymptomList);
			
			List<Fever_PresentHistory_otherPositiveSymptom> prehis_otherPositiveList = new ArrayList<Fever_PresentHistory_otherPositiveSymptom>();
			for (Object obj : object.getJSONArray("prehis_otherPositive")) {
				JSONObject thisObj = JSONObject.fromObject(obj);
				Fever_PresentHistory_otherPositiveSymptom prehis_otherPositive = (Fever_PresentHistory_otherPositiveSymptom) JSONObject
						.toBean(thisObj, Fever_PresentHistory_otherPositiveSymptom.class);
				
		//		prehis_otherPositive.setId(null);
				prehis_otherPositive.setPreIllHis(preIllHis);
				
				prehis_otherPositiveList.add(prehis_otherPositive);
			}
			preIllHis.setPrehis_otherPositive(prehis_otherPositiveList);
			
			List<Fever_PresentHistory_sideSymptom> prehis_sideSysptomList = new ArrayList<Fever_PresentHistory_sideSymptom>();
			for (Object obj : object.getJSONArray("prehis_sideSysptom")) {
				JSONObject thisObj = JSONObject.fromObject(obj);
				Fever_PresentHistory_sideSymptom prehis_sideSysptom = (Fever_PresentHistory_sideSymptom) JSONObject
						.toBean(thisObj, Fever_PresentHistory_sideSymptom.class);
				
		//		prehis_sideSysptom.setId(null);
				prehis_sideSysptom.setPreIllHis(preIllHis);
				
				prehis_sideSysptomList.add(prehis_sideSysptom);
			}
			preIllHis.setPrehis_sideSysptom(prehis_sideSysptomList);
			
			List<Fever_PresentHistory_TreatProcess> prehis_treatList = new ArrayList<Fever_PresentHistory_TreatProcess>();
			for (Object obj : object.getJSONArray("prehis_treat")) {
				JSONObject thisObj = JSONObject.fromObject(obj);
				Fever_PresentHistory_TreatProcess prehis_treat = (Fever_PresentHistory_TreatProcess) JSONObject
						.toBean(thisObj, Fever_PresentHistory_TreatProcess.class);
				
		//		prehis_treat.setId(null);
				prehis_treat.setPreIllHis(preIllHis);
				
				prehis_treatList.add(prehis_treat);
			}
			preIllHis.setPrehis_treat(prehis_treatList);
			
			List<Fever_PresentHistory_CurrentStatus> prehis_currentStatusList = new ArrayList<Fever_PresentHistory_CurrentStatus>();
			for (Object obj : object.getJSONArray("prehis_currentStatus")) {
				JSONObject thisObj = JSONObject.fromObject(obj);
				Fever_PresentHistory_CurrentStatus prehis_currentStatus = (Fever_PresentHistory_CurrentStatus) JSONObject
						.toBean(thisObj, Fever_PresentHistory_CurrentStatus.class);
				
		//		prehis_currentStatus.setId(null);
				prehis_currentStatus.setPreIllHis(preIllHis);
				
				prehis_currentStatusList.add(prehis_currentStatus);
			}
			preIllHis.setPrehis_currentStatus(prehis_currentStatusList);
			
			Long id = service.saveOrUpdateContent(preIllHis);
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
