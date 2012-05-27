package com.juncsoft.KLJY.InHospitalCase.Fever.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

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
import com.juncsoft.KLJY.InHospitalCase.Fever.service.IFeverManager;
import com.juncsoft.KLJY.InHospitalCase.Fever.service.PatientService;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.Response;

public class FeverAction extends DispatchAction {
	private PatientService ps = new PatientService();
	private IFeverManager manager = (IFeverManager) DaoFactory
	.InstanceImplement(IFeverManager.class);
	/**
	 * 加载项目
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getFeverHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("[" +
					"{text:'发病情况',leaf:true}," +
					"{text:'主要症状（必填项）',leaf:true}," +
					"{text:'次要症状',leaf:true}," +
					"{text:'重要阴性症状',leaf:true}," +
					"{text:'伴随症状',leaf:true}," +
					"{text:'诊疗经过',leaf:true}," +
					"{text:'症状病情演变',leaf:true}," +
					"{text:'目前状况',leaf:true}," +
					"{text:'其它疾病情况',leaf:true}," +
					"]");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(sb.toString());
			out.close();
		}
		
		return null;
	}
	/**
	 * 获取发热中医公共字典的信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getFeverMedicine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		List<SysDictModules> list = manager.selectFeverMedicine("现病史(新)");
		try {
			sb.append("[");
			for(int i=0; i<list.size(); i++){
				SysDictModules dictModules =(SysDictModules)list.get(i);
					sb.append(
						"{text:'"+dictModules.getName()+"',leaf:true},");
						
			}
			sb.append("]");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(sb.toString());
			out.close();
		}
		
		return null;
	}
	/**
	 * 获取病人信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPatient(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("执行查询病人：");
		PrintWriter out = response.getWriter();
		Response re = new Response();
		try {
			String id = request.getParameter("kid");
			System.out.println("kid：" + id);
			JSONObject json = ps.findByID(Integer.parseInt(id));
			if(json != null){
				re.setData(json.toString());
				re.setSuccess(true);
			}
		} catch (Exception e) {
			re.setSuccess(false);
			re.setMsg("查询失败！");
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}
	/**
	 * 获取发热西医病例加载病例信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward findContents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		String strId="";
		try{
			//获取组合信息内容
			String KID=request.getParameter("KID");
			Long caseId=Long.parseLong(KID);
			TInHspFeverFeverHistoryContent feverContent=manager.selectFeverHistoryContentByCaseId(Long.parseLong(KID),0);
			if(feverContent != null){
				String str=JSONObject.fromObject(feverContent).toString();
				res.setData(str);
				res.setSuccess(true);
			}
			
		}catch(Exception e){
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		}finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	/**
	 * 保存发热西医数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveFeverIllness(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data=request.getParameter("data");
			String content=request.getParameter("content");
			String id=request.getParameter("id");
			String KID=request.getParameter("KID");
			String oldOperations=request.getParameter("oldOperations");
			TInHspFeverFeverHistoryContent feverContents=new TInHspFeverFeverHistoryContent();
			if(Long.parseLong(id) > 0){
				feverContents.setId(Long.parseLong(id));
			}
			feverContents.setCaseId(Long.parseLong(KID));
			feverContents.setContent(content);
			feverContents.setOldOperations(oldOperations);
			feverContents.setFeverType(0);
			manager.feverHistoryContent_saveOrUpdate(feverContents);
			String[] arr=data.split(";");
			for(int i=0 ; i< arr.length ;i++){
				if(arr[i].indexOf('|') != -1){
					//获取值
					String str= arr[i].substring(arr[i].indexOf("|")-1,arr[i].indexOf("|"));
					String sonStr=arr[i].substring(arr[i].indexOf("|")+1);
					sonStr=sonStr.replace('\\',' ');
					sonStr=sonStr.replaceAll(" ","");
					//System.out.println(sonStr);
					//发病情况
					if(str.trim().equals("0")){
						JSONObject json = JSONObject.fromObject(sonStr.trim());
						TInHspFeverIncidence incidence = (TInHspFeverIncidence) JSONObject.toBean(
								json, TInHspFeverIncidence.class);
						//获取当前时间
						String dateTime=manager.showDate();
						incidence.setDateTime(dateTime);
						incidence = manager.incidence_saveOrUpdate(incidence);
						res.setData(JSONObject.fromObject(incidence).toString());
						res.setSuccess(true);
					}
					//主要症状
					if(str.trim().equals("1")){
						JSONObject json = JSONObject.fromObject(sonStr);
						TInHspFeverFeverHistoryIllness mainSystom = (TInHspFeverFeverHistoryIllness) JSONObject.toBean(
								json, TInHspFeverFeverHistoryIllness.class);
						//获取当前时间
						String dateTime=manager.showDate();
						mainSystom.setDateTime(dateTime);
						mainSystom = manager.feverHistory_saveOrUpdate(mainSystom);
						res.setData(JSONObject.fromObject(mainSystom).toString());
						res.setSuccess(true);
					}
					//次要症状
					if(str.trim().equals("2")){					
						JSONObject json = JSONObject.fromObject(sonStr);
						TInHspFeverOtherSystom otherSystom = (TInHspFeverOtherSystom) JSONObject.toBean(
								json, TInHspFeverOtherSystom.class);
						//获取当前时间
						String dateTime=manager.showDate();
						//otherSystom.setDateTime(dateTime);
						otherSystom = manager.otherSystom_saveOrUpdate(otherSystom);
						res.setData(JSONObject.fromObject(otherSystom).toString());
						res.setSuccess(true);
					}
					//重要阴性症状
					if(str.trim().equals("3")){
						JSONObject json = JSONObject.fromObject(sonStr);
						TInHspFeverNegativeSystom negativeSystom = (TInHspFeverNegativeSystom) JSONObject.toBean(
								json, TInHspFeverNegativeSystom.class);
						//获取当前时间
						String dateTime=manager.showDate();
						//negativeSystom.setDateTime(dateTime);
						negativeSystom = manager.negative_saveOrUpdate(negativeSystom);
						res.setData(JSONObject.fromObject(negativeSystom).toString());
						res.setSuccess(true);
					}
					//伴随症状
					if(str.trim().equals("4")){
						JSONObject json = JSONObject.fromObject(sonStr);
						TInHspFeverAssociatedSystom associatedSystom = (TInHspFeverAssociatedSystom) JSONObject.toBean(
								json, TInHspFeverAssociatedSystom.class);
						//获取当前时间
						String dateTime=manager.showDate();
						//associatedSystom.setDateTime(dateTime);
						associatedSystom = manager.assocatesSystom_saveOrUpdate(associatedSystom);
						res.setData(JSONObject.fromObject(associatedSystom).toString());
						res.setSuccess(true);
					}
					//诊疗经过
					if(str.trim().equals("5")){
						JSONObject json = JSONObject.fromObject(sonStr);
						TInHspFeverAfterTreatment afterTreatment = (TInHspFeverAfterTreatment) JSONObject.toBean(
								json, TInHspFeverAfterTreatment.class);
						//获取当前时间
						String dateTime=manager.showDate();
						//afterTreatment.setDateTime(dateTime);
						afterTreatment = manager.afterTreatment_saveOrUpdate(afterTreatment);
						res.setData(JSONObject.fromObject(afterTreatment).toString());
						res.setSuccess(true);
					}
					//症状病情演变
					if(str.trim().equals("6")){;
						JSONObject json = JSONObject.fromObject(sonStr);
						TInHspFeverSystomEvolution evclutionSystom = (TInHspFeverSystomEvolution) JSONObject.toBean(
								json, TInHspFeverSystomEvolution.class);
						//获取当前时间
						String dateTime=manager.showDate();
						//evclutionSystom.setDateTime(dateTime);
						evclutionSystom = manager.systomEvolution_saveOrUpdate(evclutionSystom);
						res.setData(JSONObject.fromObject(evclutionSystom).toString());
						res.setSuccess(true);
					}
					//目前状况
					if(str.trim().equals("7")){
						JSONObject json = JSONObject.fromObject(sonStr);
						TInHspFeverCurrentState currentState = (TInHspFeverCurrentState) JSONObject.toBean(
								json, TInHspFeverCurrentState.class);
						//获取当前时间
						String dateTime=manager.showDate();
						//currentState.setDateTime(dateTime);
						currentState = manager.currentState_saveOrUpdate(currentState);
						res.setData(JSONObject.fromObject(currentState).toString());
						res.setSuccess(true);
					}
					//其它疾病情况
					if(str.trim().equals("8")){
						JSONObject json = JSONObject.fromObject(sonStr);
						TInHspFeverOthersSystom othersSystom = (TInHspFeverOthersSystom) JSONObject.toBean(
								json, TInHspFeverOthersSystom.class);
						//获取当前时间
						String dateTime=manager.showDate();
						//othersSystom.setDateTime(dateTime);
						othersSystom = manager.othersSystom_saveOrUpdate(othersSystom);
						res.setData(JSONObject.fromObject(othersSystom).toString());
						res.setSuccess(true);
					}
				}
				
			}
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	
	/**
	 * 保存发热中医数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveFeverMedicineIllness(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data=request.getParameter("data");
			String content=request.getParameter("content");
			String id=request.getParameter("id");
			String KID=request.getParameter("KID");
			String oldOperations=request.getParameter("oldOperations");
			TInHspFeverFeverHistoryContent feverContents=new TInHspFeverFeverHistoryContent();
			if(Long.parseLong(id) > 0){
				feverContents.setId(Long.parseLong(id));
			}
			feverContents.setCaseId(Long.parseLong(KID));
			feverContents.setContent(content);
			feverContents.setOldOperations(oldOperations);
			feverContents.setFeverType(1);
			manager.feverHistoryContent_saveOrUpdate(feverContents);
			String[] arr=data.split(";");
			for(int i=0 ; i< arr.length ;i++){
				if(arr[i].indexOf('|') != -1){
					//获取值
					String str= arr[i].substring(arr[i].indexOf("|")-1,arr[i].indexOf("|"));
					String sonStr=arr[i].substring(arr[i].indexOf("|")+1);
					sonStr=sonStr.replace('\\',' ');
					sonStr=sonStr.replaceAll(" ","");
					//发病情况
					if(str.trim().equals("0")){
						JSONObject json = JSONObject.fromObject(sonStr.trim());
						TInHspFeverMedicineIncidence incidence = (TInHspFeverMedicineIncidence) JSONObject.toBean(
								json, TInHspFeverMedicineIncidence.class);
						//获取当前时间
						String dateTime=manager.showDate();
						incidence.setDateTime(dateTime);
						incidence = manager.feverMedicineIncidence_saveOrUpdate(incidence);
						res.setData(JSONObject.fromObject(incidence).toString());
						res.setSuccess(true);
					}
					//主要症状
					if(str.trim().equals("1")){
						JSONObject json = JSONObject.fromObject(sonStr);
						TInHspFeverMedicineFeverHistoryIllness mainSystom = (TInHspFeverMedicineFeverHistoryIllness) JSONObject.toBean(
								json, TInHspFeverMedicineFeverHistoryIllness.class);
						//获取当前时间
						String dateTime=manager.showDate();
						mainSystom.setDateTime(dateTime);
						mainSystom = manager.feverMedicineMainSysytom_saveOrUpdate(mainSystom);
						res.setData(JSONObject.fromObject(mainSystom).toString());
						res.setSuccess(true);
					}
					
					//诊疗经过
					if(str.trim().equals("5")){
						JSONObject json = JSONObject.fromObject(sonStr);
						TInHspFeverMedicineAfterTreatment afterTreatment = (TInHspFeverMedicineAfterTreatment) JSONObject.toBean(
								json, TInHspFeverMedicineAfterTreatment.class);
						//获取当前时间
						String dateTime=manager.showDate();
						//afterTreatment.setDateTime(dateTime);
						afterTreatment = manager.feverMedicineAfterTreatment_saveOrUpdate(afterTreatment);
						res.setData(JSONObject.fromObject(afterTreatment).toString());
						res.setSuccess(true);
					}
					
					//目前状况
					if(str.trim().equals("7")){
						JSONObject json = JSONObject.fromObject(sonStr);
						TInHspFeverMedicineCurrentState currentState = (TInHspFeverMedicineCurrentState) JSONObject.toBean(
								json, TInHspFeverMedicineCurrentState.class);
						//获取当前时间
						String dateTime=manager.showDate();
						//currentState.setDateTime(dateTime);
						currentState = manager.feverMedicineCurrentState_saveOrUpdate(currentState);
						res.setData(JSONObject.fromObject(currentState).toString());
						res.setSuccess(true);
					}
					
				}
				
			}
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	/**
	 * 获取发热中医病例加载病例信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward findFeverMedicineContents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		String strId="";
		try{
			//获取组合信息内容
			String KID=request.getParameter("KID");
			Long caseId=Long.parseLong(KID);
			TInHspFeverFeverHistoryContent feverContent=manager.selectFeverHistoryContentByCaseId(Long.parseLong(KID),1);
			if(feverContent != null){
				String str=JSONObject.fromObject(feverContent).toString();
				res.setData(str);
				res.setSuccess(true);
			}
			
		}catch(Exception e){
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		}finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
}
