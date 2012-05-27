package com.juncsoft.KLJY.InHospitalCase.ENT.action;

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
import com.juncsoft.KLJY.InHospitalCase.ENT.entity.ChiefComplaintSysptom;
import com.juncsoft.KLJY.InHospitalCase.ENT.entity.PresentIllnessHistory;
import com.juncsoft.KLJY.InHospitalCase.ENT.entity.PresentIllnessHistoryENT;
import com.juncsoft.KLJY.InHospitalCase.ENT.entity.PresentIllnessHistoryENTDetails;
import com.juncsoft.KLJY.InHospitalCase.ENT.entity.PresentIllnessHistory_OnSet;
import com.juncsoft.KLJY.InHospitalCase.ENT.entity.SpecialExamination;
import com.juncsoft.KLJY.InHospitalCase.ENT.dao.ENTCaseDao;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class ENTCaseAction extends DispatchAction {
	private ENTCaseDao dao = (ENTCaseDao) DaoFactory
			.InstanceImplement(ENTCaseDao.class);

	public ActionForward se_findByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(
					dao.SpecialExamination_findByCaseId(id)).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward se_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			SpecialExamination se = (SpecialExamination) JSONObject.toBean(
					json, SpecialExamination.class);
			se = dao.SpecialExamination_saveOrUpdate(se);
			res.setData(JSONObject.fromObject(se).toString());
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

	public ActionForward PresentIllness_findByCaseId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Response re = new Response();
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			re.setData(JSONObject.fromObject(
					dao.PresentIllness_findByCaseId(id),
					JSONValueProcessor.cycleExcludel(new String[] { "pi" },
							"yyyy-MM-dd HH:mm")).toString());
			re.setSuccess(true);
		} catch (Exception e) {
			re.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.print(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}

	public ActionForward PresentIllness_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Response re = new Response();
		PrintWriter out = response.getWriter();
		try {
			String data = request.getParameter("data");
			JSONObject json = JSONObject.fromObject(data);
			PresentIllnessHistoryENT pi = (PresentIllnessHistoryENT) JSONObject
					.toBean(json, PresentIllnessHistoryENT.class);
			List<PresentIllnessHistoryENTDetails> mydetails = new ArrayList<PresentIllnessHistoryENTDetails>();
			JSONArray details = json.getJSONArray("details");
			for (int i = 0, len = details.size(); i < len; i++) {
				PresentIllnessHistoryENTDetails detail = (PresentIllnessHistoryENTDetails) JSONObject
						.toBean(details.getJSONObject(i),
								PresentIllnessHistoryENTDetails.class);
				detail.setPi(pi);
				if (detail.getId() == -1) {
					detail.setId(null);
				}
				mydetails.add(detail);
			}
			pi.setDetails(mydetails);
			pi = dao.PresentIllness_saveOrUpdate(pi);
			re.setData(JSONObject.fromObject(
					pi,
					JSONValueProcessor.cycleExcludel(new String[] { "pi" },
							"yyyy-MM-dd HH:mm")).toString());
			re.setSuccess(true);
		} catch (Exception e) {
			re.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.print(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}

	public ActionForward PresentIllness_deleteDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Response re = new Response();
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			PresentIllnessHistoryENTDetails detail = new PresentIllnessHistoryENTDetails();
			detail.setId(id);
			dao.PresentIllness_deleteDetails(detail);
			re.setSuccess(true);
		} catch (Exception e) {
			re.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.print(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}

	public ActionForward PresentIllnessDetails_findById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Response re = new Response();
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			PresentIllnessHistoryENTDetails detail = dao
					.PresentIllnessDetails_findById(id);
			detail.setPi(null);
			re.setData(JSONObject.fromObject(detail).toString());
			re.setSuccess(true);
		} catch (Exception e) {
			re.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.print(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}

	// ---------------------------------------------现病史---------------------------------------------
	/**
	 * 保存或修改现病史(艾滋)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward pre_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response re = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			PresentIllnessHistory pre = (PresentIllnessHistory) JSONObject
					.toBean(json, PresentIllnessHistory.class);

			/* 起病史 */
			JSONArray array = json.getJSONArray("onsets");
			List<PresentIllnessHistory_OnSet> onsets = new ArrayList<PresentIllnessHistory_OnSet>();
			System.out.println("返回onsets长度：" + onsets.size());
			for (int i = 0, len = array.size(); i < len; i++) {
				PresentIllnessHistory_OnSet onset = (PresentIllnessHistory_OnSet) JSONObject
						.toBean(array.getJSONObject(i),
								PresentIllnessHistory_OnSet.class);
				ChiefComplaintSysptom ccs = onset.getCcs();
				if (ccs == null) {
					ccs = new ChiefComplaintSysptom();
				}
				onset.setCcs(ccs);
				ccs.setPreOn(onset);
				onset.setPi(pre);
				onsets.add(onset);

			}
			pre.setOnsets(onsets);
			// 返回保存对象 把保存对象放入页面临时数据缓存中
			pre = dao.prentSaveOrUpdate(pre);
			re.setData(JSONObject
					.fromObject(
							pre,
							JSONValueProcessor.cycleExcludel(
									new String[] { "pi" }, "")).toString());
			System.out.println("保存现病史："
					+ JSONObject.fromObject(
							pre,
							JSONValueProcessor.cycleExcludel(
									new String[] { "pi" }, "")).toString());
			re.setSuccess(true);
		} catch (Exception e) {
			re.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(re).toString());
		}
		return null;
	}

	/**
	 * 查询现病史 根据病例主ID查询现病史
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPreentByCaseId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long caseid = Long.parseLong(request.getParameter("id")) > 0 ? Long
				.parseLong(request.getParameter("id")) : -1L;
		PrintWriter out = response.getWriter();
		Response re = new Response();
		try {
			System.out.println("查询现病史(艾滋)：" + caseid);
			if (caseid > 0) {
				PresentIllnessHistory pre = dao.getPrentByCaseId(caseid);
				if (pre != null) {
					re.setData(JSONObject.fromObject(
							pre,
							JSONValueProcessor.cycleExcludel(
									new String[] { "pi" }, "yyyy-mm-dd"))
							.toString());
					System.out.println("查询现病史："
							+ JSONObject.fromObject(
									pre,
									JSONValueProcessor
											.cycleExcludel(
													new String[] { "pi" },
													"yyyy-mm-dd")).toString());
				} else {
					System.out.println("暂无数据！");
				}
			}
			re.setSuccess(true);
		} catch (Exception e) {
			re.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}

	/**
	 * 删除现病史-起病史
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delePreOnsetById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("id")) > 0 ? Long
				.parseLong(request.getParameter("id")) : -1;
		Response re = new Response();
		PrintWriter out = response.getWriter();
		try {
			dao.delePreOnsetsById(id);
			re.setSuccess(true);
			System.out.println("删除起病史成功！(action)");
		} catch (Exception e) {
			System.out.println("删除起病史失败！(action)");
			re.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.print(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}
}
