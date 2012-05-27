package com.juncsoft.KLJY.InHospitalCase.CasePrint;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.util.Response;

public class CasePrintAction extends DispatchAction {
	CasePrintService service = new CasePrintService();

	public ActionForward GetPrintData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Integer CaseType = Integer.parseInt(request
					.getParameter("CaseType"));
			res.setData(JSONObject.fromObject(
					service.getPrintData(CaseType, id)).toString());
			res.setSuccess(true);
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
	 * 单独页面表头信息
	 * 09-20==========================
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward singlePageTableInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String kD = request.getParameter("kid");
			if(!"undefined".equals(kD)){
				Long kid = Long.parseLong(kD);
				/*获取表头信息*/
				res.setData(service.getPageTableInfo(kid));
				res.setSuccess(true);
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
	 * 单独页面打印获取方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSinglePagePrintData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		// 病历ID
		Long kid = Long.parseLong(request.getParameter("id")) > 0 ? Long.parseLong(request.getParameter("id")) : -1L;
		
		//病程记录类型或单独页面指定的类型数值
		Integer caseType = Integer.parseInt(request.getParameter("caseType")) > 0 ? Integer.parseInt(request.getParameter("caseType")) : -1;
		SinglePagePrint print = new SinglePagePrint();
		try {
			if(kid > 0 && caseType > 0){
				System.out.println("ID：" + kid + "\tcaseType：" + caseType);
				res.setData(JSONObject.fromObject(print.getPrintData(caseType, kid)).toString());
				res.setSuccess(true);
			}
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		}finally{
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}		
		return null;
	}

}
