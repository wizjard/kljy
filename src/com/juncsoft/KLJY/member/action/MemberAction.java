package com.juncsoft.KLJY.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.member.dao.MemberDao;
import com.juncsoft.KLJY.member.entity.Member;
import com.juncsoft.KLJY.member.entity.MemberFirstGroup;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class MemberAction extends DispatchAction {
	private MemberDao dao = (MemberDao) DaoFactory
			.InstanceImplement(MemberDao.class);

	public ActionForward getPatInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long patId = Long.parseLong(request.getParameter("patId"));
			String outStr = "{data:[" + dao.getPatInfo(patId).toString()
					+ "],success:true}";
			out.println(outStr);
		} catch (Exception e) {
			out.println("{scueess:false}");
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward getMemInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long memId = Long.parseLong(request.getParameter("memId"));
			Long patId = Long.parseLong(request.getParameter("patId"));
			String outStr = "";
			if (memId == -1) {
				outStr = "{data:["
						+ JSONObject.fromObject(dao.mem_findByPatId(patId),
								JSONValueProcessor.formatDate("yyyy-MM-dd"))
						+ "],success:true}";
			} else {
				outStr = "{data:["
						+ JSONObject.fromObject(dao.mem_findById(memId),
								JSONValueProcessor.formatDate("yyyy-MM-dd"))
						+ "],success:true}";
			}
			out.println(outStr);
		} catch (Exception e) {
			out.println("{scueess:false}");
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward mem_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String data = request.getParameter("data");
			Member mem = (Member) JSONObject.toBean(
					JSONObject.fromObject(data), Member.class);
			if (mem.getId() == null || mem.getId() <= 0) {
				mem = dao.mem_save(mem);
			} else {
				dao.mem_update(mem);
			}
			out.println("{success:true,msg:" + mem.getId() + "}");
		} catch (Exception e) {
			out.println("{scueess:false}");
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward mempat_findAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String root = JSONArray.fromObject(
					dao.mempat_findAll(start, limit),
					JSONValueProcessor.formatDate("yyyy-MM-dd")).toString();
			long total = dao.mempat_getTotal();
			out.println("{root:" + root + ",total:" + total + "}");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward mempat_search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			out.println(dao.mempat_search(request.getParameter("keyword"))
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward firstgroup_findByCaseId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(dao.firstgroup_findByCaseId(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward firstgroup_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			MemberFirstGroup group = (MemberFirstGroup) JSONObject.toBean(
					JSONObject.fromObject(data), MemberFirstGroup.class);
			group = dao.firstgroup_saveOrUpdate(group);
			res.setData(group.getId().toString());
			res.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
}
