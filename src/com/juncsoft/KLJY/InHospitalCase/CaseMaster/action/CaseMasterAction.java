package com.juncsoft.KLJY.InHospitalCase.CaseMaster.action;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.juncsoft.KLJY.InHospitalCase.CaseMaster.dao.InHspCaseMasterDao;
import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.InHospitalCase.CaseMaster.util.CaseCfgUtil;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class CaseMasterAction extends DispatchAction {
	private InHspCaseMasterDao dao = (InHspCaseMasterDao) DaoFactory
			.InstanceImplement(InHspCaseMasterDao.class);

	public ActionForward getAgeAndSex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			int pid =  Integer.parseInt(request.getParameter("pid"));
			int scId = Integer.parseInt(request.getParameter("scId"));					
			JSONObject result = dao.getAgeAndSex(pid,scId);			
			res.setData(result.toString());
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
	
	
	public ActionForward saveOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			InHspCaseMaster cm = (InHspCaseMaster) JSONObject.toBean(JSONObject
					.fromObject(data), InHspCaseMaster.class);
			res.setData(JSONObject.fromObject(dao.saveOrUpdate(cm),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
					.toString());
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

	public ActionForward findById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(dao.findById(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
					.toString());
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

	public ActionForward queryAllByPatient(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			String data = JSONArray.fromObject(dao.queryAllByPatient(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
					.toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward queryMyInHspRecCfg(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			User user = (User) request.getSession().getAttribute("user");
			String ward = "";
			if (user != null) {
				ward = user.getBelong();
			}
			if (ward == null || ward.length() == 0) {
				InHspCaseMaster cm = dao.findById(id);
				ward = cm.getCurrentWordCode();
				if (ward == null || ward.length() == 0) {
					ward = cm.getInWardCode();
				}
			}
			if (ward != null && ward.length() > 0) {
				res.setData(CaseCfgUtil.queryMyInHspRecCfg(ward).toString());
			} else {
				res.setData("[]");
			}
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

	public ActionForward updateInHspRecCfg(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			String caseModuleId = request.getParameter("caseModuleId");
			InHspCaseMaster cm = dao.findById(id);
			cm.setCaseModuleId(caseModuleId);
			dao.saveOrUpdate(cm);
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
	/**查询住院记录列表**/
	public ActionForward toInHspRec(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String caseModuleId = request.getParameter("caseModuleId");
			res.setData(CaseCfgUtil.queryInHspRecCfgByCode(caseModuleId)
					.toString());
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

	public ActionForward queryPersonalInfoByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(dao.queryPersonalInfoByCaseID(id).toString());
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

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			InHspCaseMaster cm = new InHspCaseMaster();
			cm.setId(id);
			dao.delete(cm);
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
	
	public ActionForward getAge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long pid = Long.parseLong(request.getParameter("pid"));
			String inHspDate = request.getParameter("inHspDate");
			DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			res.setData(dao.getAge(pid, sf.parse(inHspDate)));
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
