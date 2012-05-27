package com.juncsoft.KLJY.InHospitalCase.CheckReport.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.CheckReport.dao.CombinationProjectDao;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.ReportFormforPad;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.impl.CombinationProjectImpl;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.Response;

public class CombinationProjectAction extends DispatchAction{

	CombinationProjectDao dao = new CombinationProjectImpl();
	
	/*新增检查结果：PC版原来显示三列，有关联功能，现在在pad上显现为表格，去除关联功能*/
	public ActionForward getCheckReportsForPad(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			String patientId = request.getParameter("id");
			if(patientId == null || patientId.equals("")){
				MemberInfo mem = (MemberInfo) request.getSession().getAttribute("MemberInfo");
				Patient p = mem.getPatient();
				patientId = p.getPatientId();				
			}
			
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));			
			List<ReportFormforPad> reports = dao.getReportsByPatientForPad(patientId);
			Collections.sort(reports);
			Long total = (long)reports.size();
			
			List<ReportFormforPad> tempReports = new ArrayList<ReportFormforPad>();
			for(int i = start,count = 0;(count < limit)&&(i < total);i++,count++){
				tempReports.add(reports.get(i));				
			}			
			JSONObject object = new JSONObject();
			object.put("root", tempReports);
			object.put("total", total);
			out.println(object.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	
	public ActionForward getCombinationProject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		try {
			String is_check_add = request.getParameter("is_check_add");
			String patientId = request.getParameter("patientId");
			
			String reportDate = request.getParameter("reportDate");
			if(is_check_add.equals("check")){
				String obj = dao.getCombinationProject(patientId, reportDate);
				if(obj == null){
					sb.append("[{text:'该病人相关联的历史记录为空',leaf:true}]");
//					System.out.println("该病人相关联的历史记录为空");
				}else{
					if(reportDate.equals("allDate")){
						sb.append("[{text:'按照 时间-->组合项目 形式展开',children:[").append(obj).append("]}]");
//						System.out.println("按照 时间-->组合项目 形式展开");
					}else{
						sb.append("[").append(obj).append("]");
					}
				}											
			}
			
			if(is_check_add.equals("add")){
				String obj = dao.getCombinationProject("allComProj", reportDate);
				sb.append("[").append(obj).append("]");
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(sb.toString());
			out.close();
		}
		return null;
	}
	//门诊查询
	public ActionForward getCombinationProjectofOutPatient(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		try {
			String is_check_add = request.getParameter("is_check_add");
			String patientId = request.getParameter("patientId");
			
			String reportDate = request.getParameter("reportDate");
			if(is_check_add.equals("check")){
				String obj = dao.getCombinationProjectofOutPatient(patientId, reportDate);
				if(obj == null){
					sb.append("[{text:'该病人相关联的历史记录为空',leaf:true}]");
				}else{
					if(reportDate.equals("allDate")){
						sb.append("[{text:'按照 时间-->组合项目 形式展开',children:[").append(obj).append("]}]");
					}else{
						sb.append("[").append(obj).append("]");
					}
				}											
			}
			
			if(is_check_add.equals("add")){
				String obj = dao.getCombinationProject("allComProj", reportDate);
				sb.append("[").append(obj).append("]");
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(sb.toString());
			out.close();
		}
		return null;
	}
	
	public ActionForward getCombinationProjectByTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String is_check_add = request.getParameter("is_check_add");
		} catch (Exception e) {
			
		} finally {
			out.println("[{text:'按照 时间-->组合项目 形式展开',children:[]}]");
			out.close();
		}
		return null;
	}
	
	public ActionForward getAssistanceProject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		try {
			String is_check_add = request.getParameter("is_check_add");
			String patientId = request.getParameter("patientId");
			
			if(is_check_add.equals("check")){
				String obj = null;
				if(obj == null){
					sb.append("[{text:'该病人相关联的历史记录为空',leaf:true}");
				}else{
					
				}							
			}			
			if(is_check_add.equals("add")){
				sb.append("[{text:'辅助检查',children:[");
				
				String obj = "{text:'X线平片',leaf:true},{text:'心电图',leaf:true},{text:'B超',leaf:true},{text:'CT',leaf:true},{text:'MRI',leaf:true},{text:'消化道造影',leaf:true},{text:'胃镜',leaf:true},{text:'结肠镜',leaf:true},{text:'十二指肠镜',leaf:true},{text:'胶囊内镜',leaf:true},{text:'尿素呼气试验',leaf:true},{text:'其他检查',leaf:true}";				
				sb.append(obj).append("]},");
				sb.append("{text:'病理检查',children:[");
				String obj2 = "{text:'病理检查',leaf:true}";
				sb.append(obj2).append("]}");
				sb.append("]");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(sb.toString());
			out.close();
		}
		return null;
	}
	
	//根据时间查询化验检查(默认查询本月的，若用户选中时间则按照选定时间查询)
	public ActionForward getCombinationProjectByAnyTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		try {
			String is_check_add = request.getParameter("is_check_add");
			String patientId = request.getParameter("patientId");
			String reportDate = request.getParameter("reportDate");//用户选择的时间
			//判断一下时间
			//if(reportDate.length()>2){//说明是用户选择的时间（或者是用户点击节点）--排除了用户选最近一个月的记录
				//if(reportDate.contains("no")){}//说明是选择的时间
			//}
		//	String selectflag = request.getParameter("selectflag");//查询标示（表示仅查询时间或是时间下的子节点）
			String obj = dao.getCombinationProjectByAnytime(patientId, reportDate);
			if(obj == null){
				sb.append("[{text:'病人该时期相关的记录为空',leaf:true}]");
			}else{
				if(reportDate.contains("no")||reportDate.equals("")){
					sb.append("[{text:'按照 时间-->组合项目 形式展开',children:[").append(obj).append("]}]");
				}else{
					sb.append("[").append(obj).append("]");
				}
			}											
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(sb.toString());
			out.close();
		}
		return null;
	}
	
	//根据时间查询化验检查(默认查询本月的，若用户选中时间则按照选定时间查询)
	public ActionForward getCombinationProjectByStartTimeAndEndTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		try {
			String patientId = request.getParameter("patientId");
			String kid = request.getParameter("kid");
			String reportDate = request.getParameter("reportDate");//用户选择的时间
			String obj = dao.getCombinationProjectByStartTimeAndEndTime(patientId, kid,reportDate);
			if(obj == null){
				sb.append("[{text:'病人该时期相关的记录为空',leaf:true}]");
			}else{
				if(reportDate.contains("no")||reportDate.equals("")){
					sb.append("[{text:'按照 时间-->组合项目 形式展开',children:[").append(obj).append("]}]");
				}else{
					sb.append("[").append(obj).append("]");
				}
			}											
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(sb.toString());
			out.close();
		}
		return null;
	}
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMyGrounpCheckReportsForByMember(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			String patientId = request.getParameter("patientId");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			List<ReportFormforPad> reports = dao.getMyGrounpCheckReportsForByMember(patientId,search);
//			Collections.sort(reports);
			Long total = (long)reports.size();
			
			List<ReportFormforPad> tempReports = new ArrayList<ReportFormforPad>();
			for(int i = start,count = 0;(count < limit)&&(i < total);i++,count++){
				tempReports.add(reports.get(i));				
			}			
			JSONObject object = new JSONObject();
			object.put("root", tempReports);
			object.put("total", total);
			out.println(object.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMyGrounpCheckReportsFor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			List<ReportFormforPad> reports = dao.getMyGrounpCheckReportsFor(doctorId,search);
//			Collections.sort(reports);
			Long total = (long)reports.size();
			
			List<ReportFormforPad> tempReports = new ArrayList<ReportFormforPad>();
			for(int i = start,count = 0;(count < limit)&&(i < total);i++,count++){
				tempReports.add(reports.get(i));				
			}			
			JSONObject object = new JSONObject();
			object.put("root", tempReports);
			object.put("total", total);
			out.println(object.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMyGrounpCheckReportsForByMemberDoctor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			String patientId = request.getParameter("patientId");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			List<ReportFormforPad> reports = dao.getMyGrounpCheckReportsForByMemberDoctor(patientId,search);
//			Collections.sort(reports);
			Long total = (long)reports.size();
			
			List<ReportFormforPad> tempReports = new ArrayList<ReportFormforPad>();
			for(int i = start,count = 0;(count < limit)&&(i < total);i++,count++){
				tempReports.add(reports.get(i));				
			}			
			JSONObject object = new JSONObject();
			object.put("root", tempReports);
			object.put("total", total);
			out.println(object.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMyGrounpCheckReportsForByMemberGuanLiYuan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			String patientId = request.getParameter("patientId");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			List<ReportFormforPad> reports = dao.getMyGrounpCheckReportsForByMemberGuanLiYuan(patientId,search);
//			Collections.sort(reports);
			Long total = (long)reports.size();
			
			List<ReportFormforPad> tempReports = new ArrayList<ReportFormforPad>();
			for(int i = start,count = 0;(count < limit)&&(i < total);i++,count++){
				tempReports.add(reports.get(i));				
			}			
			JSONObject object = new JSONObject();
			object.put("root", tempReports);
			object.put("total", total);
			out.println(object.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMyGrounpCheckReportsForByMemberGuanLiYuanAll(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			String deptCode = request.getParameter("deptCode");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			List<ReportFormforPad> reports = dao.getMyGrounpCheckReportsForByMemberGuanLiYuanAll(deptCode,search);
//			Collections.sort(reports);
			Long total = (long)reports.size();
			
			List<ReportFormforPad> tempReports = new ArrayList<ReportFormforPad>();
			for(int i = start,count = 0;(count < limit)&&(i < total);i++,count++){
				tempReports.add(reports.get(i));				
			}			
			JSONObject object = new JSONObject();
			object.put("root", tempReports);
			object.put("total", total);
			out.println(object.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	
	public ActionForward deleteReportFormAndReportItems(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			int id = Integer.parseInt(request.getParameter("reportFormforRemoteId")); 
			String receiveDate = request.getParameter("receiveDate");
			int sectionNo=Integer.parseInt(request.getParameter("sectionno"));
			int testTypeNo= Integer.parseInt(request.getParameter("testtypeno"));
			String sampleNo = request.getParameter("sampleNo");
			dao.deleteReportFormAndReportItems(id,receiveDate,sectionNo,testTypeNo,sampleNo);
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
