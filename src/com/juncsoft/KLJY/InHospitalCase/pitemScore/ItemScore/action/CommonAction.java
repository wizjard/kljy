package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.action;

import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.CommonDao;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Field;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.LFunction;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Table;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl.CommonDaoImpl;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.util.TableConig;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class CommonAction extends DispatchAction {
	private CommonDao dao = new CommonDaoImpl();

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTableConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String tableCode = request.getParameter("tableCode");
			Table table = TableConig.getTableConfig(tableCode);
			out.println(JSONObject.fromObject(table).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward getPatientInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String PatientID = request.getParameter("PatientID");
			out.println(JSONObject.fromObject(
					dao.findPatientByID(Integer.parseInt(PatientID)))
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String data = request.getParameter("data");
			JSONObject object = JSONObject.fromObject(data);
			Table table = TableConig.getTableConfig((String) object
					.get("tableCode"));
			table.setKeyValue((String) object.get("keyId"));
			List<Field> flds = table.getFields();
			for (int i = 0, len = flds.size(); i < len; i++) {
				Field fld = table.getFields().get(i);
				table.getFields().get(i).setFieldValue(
						object.getString(fld.getFieldCode()));
			}
			out.println(dao.saveOrUpdate(table));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPageData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String tableCode = request.getParameter("tableCode");
			String keyID = request.getParameter("keyID");
			Table table = TableConig.getTableConfig(tableCode);
			table.setKeyValue(keyID);
			out.println(JSONObject.fromObject(dao.findByID(table)).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String tableCode = request.getParameter("tableCode");
			String keyID = request.getParameter("keyID");
			Table table = TableConig.getTableConfig(tableCode);
			table.setKeyValue(keyID);
			dao.delete(table);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward toItemScoreIndex(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		
		return null;
	}

	/**
	 * 保存或修改肝病功能评估表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveOrUpdateLfun(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		JSONObject json = JSONObject.fromObject(request.getParameter("data"));
		LFunction lf = (LFunction) JSONObject.toBean(json, LFunction.class);
		Response re = new Response();
		try {
			if (lf != null) {
				re.setData(JSONObject
						.fromObject(
								dao.saveOrUpdateLfunction(lf),
								JSONValueProcessor.cycleExcludel(
										new String[] { "opeDate" },
										"yyyy-MM-dd HH:mm")).toString());
				re.setSuccess(true);
			} else {
				re.setData("");
			}
		} catch (Exception e) {
			re.setSuccess(false);
			e.printStackTrace();
		} finally {
			//System.out.println("JSONObject："+JSONObject.fromObject(re).toString
			// ());
			out.println(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}

	public ActionForward getLfun(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long id = Long.parseLong(request.getParameter("id")) > 0 ? Long
				.parseLong(request.getParameter("id")) : -1;
		PrintWriter out = response.getWriter();
		try {
			if (id > 0) {
				LFunction lf = dao.getLfun(id);
				out.print(JSONObject.fromObject(lf,
								JSONValueProcessor.formatDate("yyyy-MM-dd"))
								.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward delLfunById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response re = new Response();
		System.out.println(request.getParameter("id"));
		Long id = Long.parseLong(request.getParameter("id")) > 0 ? Long
				.parseLong(request.getParameter("id")) : -1L;
		try {
			if (id > 0) {
				dao.deleLfunction(id);
				re.setSuccess(true);
				re.setMsg("删除成功！");
			}
		} catch (Exception e) {
			re.setSuccess(false);
			re.setMsg("删除失败！");
			e.printStackTrace();
		} finally {
			out.print(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}

}
