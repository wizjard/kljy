package com.juncsoft.KLJY.biomedical.contorl.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.biomedical.contorl.dao.MemberSearchTotalDao;
import com.juncsoft.KLJY.biomedical.contorl.impl.MemberSearchTotalImpl;
import com.juncsoft.KLJY.util.Response;

public class MemberSearchTotalAction extends DispatchAction {
	private MemberSearchTotalDao dao = new MemberSearchTotalImpl();

	/**
	 * 查询会员明细的信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchMemberByCondition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			out.println(dao.searchMemberByCondition(start, limit).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 查询会员监督统计信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward superviseMember(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			JSONObject object = new JSONObject();
			object = dao.superviseMember(start, limit);

			out.println(object);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 导出会员明细信息到Excel
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward SearchmemberDataAnalysisToExcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			response.reset();
			response.setContentType("bin"); 
			response.setHeader("Content-Disposition","attachment; filename=\"MemberInfo.xls\"");
			dao.exportSearchDataToExcel(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}

	/**
	 * 导出会员监督统计信息到Excel
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward memberDataAnalysisToExcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			response.reset();
			response.setContentType("bin"); 
			response.setHeader("Content-Disposition","attachment; filename=\"MemberControl.xls\"");
			dao.exportDataToExcel(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}
	//liugang内网全院会员明细表
	public ActionForward searchInMemberByCondition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			//System.out.println(search);
			out.println(dao.searchInMemberByCondition(start, limit, search).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	//liugang内网全院会员明细表
	public ActionForward superviseInMember(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			JSONObject object = new JSONObject();
			object = dao.superviseInMember(start, limit);
			out.println(object);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	/**
	 * 导出会员明细信息到Excel(内网)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward SearchmemberInDataAnalysisToExcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			response.reset();
			response.setContentType("bin"); 
			response.setHeader("Content-Disposition","attachment; filename=\"MemberInfoDate.xls\"");
			dao.exportSearchInDataToExcel(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}

	/**
	 * 导出会员监督统计信息到Excel（内网）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward memberInDataAnalysisToExcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			response.reset();
			response.setContentType("bin"); 
			response.setHeader("Content-Disposition","attachment; filename=\"MemberInControl.xls\"");
			dao.exportsuperviseInMemberExcel(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}
}
