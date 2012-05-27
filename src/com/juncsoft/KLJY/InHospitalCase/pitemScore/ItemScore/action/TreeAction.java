package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.TreeDao;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl.TreeDaoImpl;

public class TreeAction extends DispatchAction {
	private TreeDao dao = new TreeDaoImpl();

	/**
	 * 获取树节点
	 * @param mapping
	 * @param from
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTreeNode(ActionMapping mapping, ActionForm from,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Integer patientId = Integer.parseInt(request.getParameter("PatientID")) > 0 ? Integer.parseInt(request.getParameter("PatientID")) : -1;
			if (patientId > 0) {
				out.println(JSONArray.fromObject(dao.getTreeNode(patientId)).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward getRelPageName(ActionMapping mapping, ActionForm from,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String treeId = request.getParameter("TreeID");
			Double tId = Double.parseDouble(treeId);
			out.println(JSONObject.fromObject(dao.getRelPageName(tId)).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
}
