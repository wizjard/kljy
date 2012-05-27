package com.juncsoft.KLJY.patientanddoctoroperator.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.patientanddoctoroperator.dao.DoctorReplyRecordAndPatientQuestionsDao;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.DoctorReplyRecordAndPatientQuestions;
import com.juncsoft.KLJY.patientanddoctoroperator.impl.DoctorReplyRecordAndPatientQuestionsImpl;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

/**
 * 医生回复和病人提问管理 2011-05-31
 * 
 * @author liugang
 * 
 */
public class DoctorReplyRecordAndPatientQuestionsAction extends DispatchAction {
	private DoctorReplyRecordAndPatientQuestionsDao dao = new DoctorReplyRecordAndPatientQuestionsImpl();

	/**
	 * 查询病人某个问题对应的回复列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findAllDoctorReplyRecordAndPatientQuestionsByPCId(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Long pcId = Long.parseLong(request.getParameter("pcId"));
			String data = JSONArray
					.fromObject(
							dao
									.findAllDoctorReplyRecordAndPatientQuestionsByPCId(pcId),
							JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	
	public ActionForward saveDoctorReplyRecordAndPatientQuestions(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			DoctorReplyRecordAndPatientQuestions doctorReplyRecordAndPatientQuestions = new DoctorReplyRecordAndPatientQuestions();
			Long pcId = Long.parseLong(request.getParameter("pcId"));
			String content = request.getParameter("content");
			doctorReplyRecordAndPatientQuestions.setPcId(pcId);
			String date = request.getParameter("date");
 			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			doctorReplyRecordAndPatientQuestions.setDrAndpqDate(sdf.parse(date));
			doctorReplyRecordAndPatientQuestions.setDrAndpqContent(content);
			User user = (User)request.getSession().getAttribute("user");
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute("MemberInfo");
//			String zixunDept = request.getParameter("zixunDept");
//			String zeRerDept = request.getParameter("zeRerDept");
			if(mem == null && user == null){
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.removeAttribute("MemberInfo");
					session.invalidate();
				}
				res.setSuccess(false);
			}else if(mem != null && user == null){
				doctorReplyRecordAndPatientQuestions.setDrAndpqId(mem.getPatient().getId());
				doctorReplyRecordAndPatientQuestions.setDrAndpqName(mem.getPatient().getPatientName());
				doctorReplyRecordAndPatientQuestions.setDrAndpqFlag(1);//1：表示病人
				doctorReplyRecordAndPatientQuestions.setDeptCode(mem.getDeptCode());
				doctorReplyRecordAndPatientQuestions.setGrounpId(mem.getGrounpId());
			}else if(mem == null && user.getId() != null){
				doctorReplyRecordAndPatientQuestions.setDrAndpqId(user.getId());
				doctorReplyRecordAndPatientQuestions.setDrAndpqName(user.getName());
				doctorReplyRecordAndPatientQuestions.setDrAndpqFlag(0);//0:表示医生
				doctorReplyRecordAndPatientQuestions.setDrAndpgReadPatient(0);//病人已读1,0:未读
			}
			doctorReplyRecordAndPatientQuestions.setDrAndpgCancel(1);
			if(doctorReplyRecordAndPatientQuestions.getDrAndpqId() != null){
				res.setData(JSONObject.fromObject(dao.saveDoctorReplyRecordAndPatientQuestions(doctorReplyRecordAndPatientQuestions),
						JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
						.toString());
				res.setSuccess(true);
			}else{
				res.setData("");
				res.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	
	public ActionForward cancelMessageByDoctorOperation(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long pcId = Long.parseLong(request.getParameter("pcId"));
			boolean flag = dao.cancelMessageByDoctorOperation(pcId);
			res.setSuccess(flag);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
}
