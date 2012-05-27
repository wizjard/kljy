package com.juncsoft.KLJY.ResearchFollowup.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.ResearchFollowup.dao.FollowSetUpDao;
import com.juncsoft.KLJY.ResearchFollowup.entity.ResearchFollowSetUp;
import com.juncsoft.KLJY.ResearchFollowup.impl.FollowSetUpImpl;
import com.juncsoft.KLJY.util.JSONValueProcessor;

public class FollowSetUpAction  extends DispatchAction{

	private FollowSetUpDao dao = new FollowSetUpImpl();
	public ActionForward getFollopupPro(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String researchTopicId = request.getParameter("researchTopicId");
			List<ResearchFollowSetUp> list = dao.getFollopupPro(Integer.valueOf(researchTopicId));
			out.println(JSONArray.fromObject(list).toString());
		} catch (Exception e) {
			out.print("获取信息失败");
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward saveFollopupPro(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
//			List<ResearchFollowSetUp> list = new ArrayList<ResearchFollowSetUp>();
//			JSONArray arr = JSONArray.fromObject(request.getParameter("proInfo"));
//			for(int i = 0; i < arr.size(); i++){
//				ResearchFollowSetUp rf = (ResearchFollowSetUp) JSONObject.toBean(JSONObject.fromObject(arr.get(i)), ResearchFollowSetUp.class);
//			}
			List<ResearchFollowSetUp> list = JSONArray.toList(JSONArray.fromObject(request.getParameter("proInfo")), ResearchFollowSetUp.class);
			dao.saveFollopupPro(list);
			out.print("保存成功");
		} catch (Exception e) {
			out.print("保存失败");
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println();
			out.close();
		}
		return null;
	}

	public ActionForward deleteFollopupPro(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String researchTopicId = request.getParameter("researchTopicId");
			dao.deleteFollopupPro(Integer.valueOf(researchTopicId));
			out.print("删除成功");
		} catch (Exception e) {
			out.print("删除失败");
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println();
			out.close();
		}
		return null;
	}
}
