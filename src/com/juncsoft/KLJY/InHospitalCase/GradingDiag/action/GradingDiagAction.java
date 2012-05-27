package com.juncsoft.KLJY.InHospitalCase.GradingDiag.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.GradingDiag.dao.DiagItemDao;
import com.juncsoft.KLJY.InHospitalCase.GradingDiag.dao.GradingDiagDao;
import com.juncsoft.KLJY.InHospitalCase.GradingDiag.entity.DiagItem;
import com.juncsoft.KLJY.InHospitalCase.GradingDiag.entity.GradingDiag;
import com.juncsoft.KLJY.InHospitalCase.GradingDiag.impl.DiagItemDaoImpl;
import com.juncsoft.KLJY.InHospitalCase.GradingDiag.impl.GradingDiagDaoImpl;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class GradingDiagAction extends DispatchAction {
	private DiagItemDao dao = new DiagItemDaoImpl();
	private GradingDiagDao gdao = new GradingDiagDaoImpl();

	public ActionForward getChildren(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String code = request.getParameter("code");
		PrintWriter out = response.getWriter();
		try {
			DiagItem parent = null;
			if (!code.equalsIgnoreCase("root"))
				parent = dao.getDiagItem(code);
			List<DiagItem> list = dao.getChildren(parent);
			JSONArray array = new JSONArray();
			JSONObject object = null;
			String[] cycleField = new String[] { "parent" };
			for (DiagItem item : list) {
				object = new JSONObject();
				object.put("id", item.getCode());
				object.put("text", item.getNodeText());
				object.put("data", JSONObject.fromObject(item,
						JSONValueProcessor.cycleExcludel(cycleField, "")));
				object.put("leaf", item.getLeaf() == 0 ? false : true);
				if(item.getLeaf()==1){
					object.put("iconCls", "icon-cmp");
				}
				array.add(object);
			}
			out.println(array);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward saveDiagItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Response res = new Response();
		PrintWriter out = response.getWriter();
		try {
			JSONObject data = JSONObject.fromObject(request
					.getParameter("data"));
			String parent = data.getString("parent");
			DiagItem par = null;
			if (!parent.equalsIgnoreCase("root")) {
				par = dao.getDiagItem(parent);
			}
			data.remove("parent");
			DiagItem item = (DiagItem) JSONObject.toBean(data, DiagItem.class);
			item.setParent(par);
			if (dao.isCodeExist(item.getCode())) {
				dao.updateDiagItem(item);
			} else {
				dao.addDiagItem(item);
			}
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res));
			out.close();
		}
		return null;
	}

	public ActionForward deleteDiagItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Response res = new Response();
		PrintWriter out = response.getWriter();
		try {
			String code = request.getParameter("code");
			dao.deleteDiagItem(dao.getDiagItem(code));
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res));
			out.close();
		}
		return null;
	}

	public ActionForward isCodeExist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Response res = new Response();
		PrintWriter out = response.getWriter();
		try {
			String code = request.getParameter("code");
			res.setData(dao.isCodeExist(code) + "");
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res));
			out.close();
		}
		return null;
	}

	public ActionForward findGradingDiagByCaseId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("id"));
		Response res = new Response();
		PrintWriter out = response.getWriter();
		try {
			res.setData(JSONObject.fromObject(gdao.findByCaseId(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res));
			out.close();
		}
		return null;
	}
	/*
	 * 十锟斤拷锟斤拷锟街硷拷锟斤拷住院锟斤拷
	 */
	public ActionForward findGradingDiagByCaseId1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("id"));
		Response res = new Response();
		PrintWriter out = response.getWriter();
		try {
			res.setData(JSONObject.fromObject(gdao.findByCaseId1(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm")).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res));
			out.close();
		}
		return null;
	}
	/*
	 * 十锟斤拷锟斤拷锟街硷拷(锟斤拷锟斤拷)
	 */
	public ActionForward findGradingDiagByCaseIdmenzhen(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("id"));
		Response res = new Response();
		PrintWriter out = response.getWriter();
		try {
			res.setData(JSONObject.fromObject(gdao.findByCaseIdmenzhen(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm")).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res));
			out.close();
		}
		return null;
	}
	public ActionForward findGradingDiagById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("id"));
		Response res = new Response();
		PrintWriter out = response.getWriter();
		try {
			res.setData(JSONObject.fromObject(gdao.findById(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res));
			out.close();
		}
		return null;
	}

	public ActionForward saveOrUpdateGradingDiag(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Response res = new Response();
		PrintWriter out = response.getWriter();
		try {
			String data = request.getParameter("data");
//			data.replace("骞�", "-").replace("鏈�", "-").replace("鏃�", " ").replace("鏃�", ":").replace("鍒�", "");
			JSONObject json = JSONObject.fromObject(data,JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"));
			
			GradingDiag diag = (GradingDiag) JSONObject.toBean(json,
					GradingDiag.class);
			
			res.setData(gdao.saveOrUpdate(diag).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res));
			out.close();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/pdf");
		try {
			ServletOutputStream out = response.getOutputStream();
			JSONObject data = JSONObject.fromObject(request
					.getParameter("data"));
			gdao.print(out, data, request.getRealPath("/"));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    //锟斤拷锟斤拷十锟斤拷锟街硷拷锟斤拷印
	@SuppressWarnings("deprecation")
	public ActionForward print1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/pdf");
		try {
			ServletOutputStream out = response.getOutputStream();
			JSONObject data = JSONObject.fromObject(request.getParameter("data"));
			gdao.print1(out, data, request.getRealPath("/"));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取到所有的Gradings 住院
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getGradings(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("CID"));
			//System.out.print(id);
			out.println(gdao.getGradings(id).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 获取到所有的Gradings 门诊
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getGradingsOut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("PID"));
			//System.out.print(id);
			out.println(gdao.getGradingOutIn(id).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	public ActionForward deleteGrading(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Response res = new Response();
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			gdao.deleteGradingDiag(id);
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res));
			out.close();
		}
		return null;
	}
	/**
	 * 获取到 所有的Gradings （包括 住院的和门诊的）
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward getAllGradings(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String account = request.getParameter("account");
			out.println(gdao.findAllGrading(account).toString());
			System.out.print(gdao.findAllGrading(account).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
}
