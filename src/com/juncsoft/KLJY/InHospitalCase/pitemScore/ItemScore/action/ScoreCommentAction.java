package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.action;

import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.ScoreCommentDao;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.ScoreCommentDeleteDao;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Apacheii;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Bclc;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Bmi;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.ChildPugh;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Clip;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Gcs;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Hc;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.He;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Hepatomyocardosis;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Hps;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Hrs;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Lctos;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Meld;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.OrganFunc;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Phi;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Plc;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.ScoreComment;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Tnm;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Ts;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl.ScoreCommentDeleteImpl;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl.ScoreCommentImpl;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl.ScoreCommentPrintImpl;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl.ScoreCommentPrintImpl1;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class ScoreCommentAction extends DispatchAction {

	private ScoreCommentDao dao = new ScoreCommentImpl();
	private ScoreCommentDeleteDao daoDelete = new ScoreCommentDeleteImpl();

	public ActionForward public_mainMenu(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long ssmId = Long.parseLong(request.getParameter("ssmId"));
			Long caseId = Long.parseLong(request.getParameter("kid"));
			res.setData(dao.public_mainMenu(ssmId, caseId).toString());
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

	public ActionForward getScoreSetMeal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("[" + dao.getScoreSetMeal() + "]");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(sb.toString());
			out.close();
		}
		return null;
	}
 /*
  *器官树（门诊） 
  */
	public ActionForward getScoreSetMealmenzhen(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("[" + dao.getScoreSetMealmenzhen() + "]");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(sb.toString());
			out.close();
		}
		return null;
	}

	/*
	 * 器官功能评价(门诊)
	 */
	public ActionForward scoreItems_findByScidmenzhen(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		JSONObject obj = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Long scid = Long.parseLong(request.getParameter("id"));
			String subScoreItems = request.getParameter("subScoreItems");
			// 获取评分时间和阶段
			ScoreComment sc = dao.scoreComment_findByScidmenzhen(session, scid);
			obj.put("sc", JSONObject.fromObject(sc,
							JSONValueProcessor.formatDate("yyyy年MM月dd日HH时mm分"))
							.toString());
			// 获取各个评分表中的值
			if (subScoreItems != null && !subScoreItems.equals("")) {
				String[] scoreItems = subScoreItems.split("\\*");
				String scoreItem = "";
				for (int i = 0; i < scoreItems.length; i++) {
					scoreItem = scoreItems[i];
					if (scoreItem.equals("MenzhenChild-Pugh")) {
						ChildPugh cp = dao.childPugh_findByScidmenzhen(session, scid);
						obj.put("MenzhenChild-Pugh", JSONObject
								.fromObject(
										cp,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenMELD")) {
						Meld meld = dao.meld_findByScidmenzhen(session, scid);
						obj.put("MenzhenMELD", JSONObject
								.fromObject(
										meld,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenBCLC")) {
						Bclc bclc = dao.bclc_findByScidmenzhen(session, scid);
						obj.put("MenzhenBCLC", JSONObject
								.fromObject(
										bclc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenBMI")) {
						Bmi bmi = dao.bmi_findByScidmenzhen(session, scid);
						obj.put("MenzhenBMI", JSONObject
								.fromObject(
										bmi,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenCLIP")) {
						Clip clip = dao.clip_findByScidmenzhen(session, scid);
						obj.put("MenzhenCLIP", JSONObject
								.fromObject(
										clip,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenGCS")) {
						Gcs gcs = dao.gcs_findByScidmenzhen(session, scid);
						obj.put("MenzhenGCS", JSONObject
								.fromObject(
										gcs,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenPLC")) {
						Plc plc = dao.plc_findByScidmenzhen(session, scid);
						obj.put("MenzhenPLC", JSONObject
								.fromObject(
										plc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenTNM")) {
						Tnm tnm = dao.tnm_findByScidmenzhen(session, scid);
						obj.put("MenzhenTNM", JSONObject
								.fromObject(
										tnm,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenLCTOS")) {
						Lctos lctos = dao.lctos_findByScidmenzhen(session, scid);
						obj.put("MenzhenLCTOS", JSONObject
								.fromObject(
										lctos,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenHE")) {
						He he = dao.he_findByScidmenzhen(session, scid);
						obj.put("MenzhenHE", JSONObject
								.fromObject(
										he,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenOrganFunc")) {
						OrganFunc organFunc = dao.organFunc_findByScidmenzhen(session, scid);
						obj.put("MenzhenOrganFunc", JSONObject
								.fromObject(
										organFunc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenHC")) {
						Hc hc = dao.hc_findByScidmenzhen(session, scid);
						obj.put("MenzhenHC", JSONObject
								.fromObject(
										hc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenHRS")) {
						Hrs hrs = dao.hrs_findByScidmenzhen(session, scid);
						obj.put("MenzhenHRS", JSONObject
								.fromObject(
										hrs,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenHPS")) {
						Hps hps = dao.hps_findByScidmenzhen(session, scid);
						obj.put("MenzhenHPS", JSONObject
								.fromObject(
										hps,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenHepatomyocardosis")) {
						Hepatomyocardosis hepatomyocardosis = dao
								.hepatomyocardosis_findByScidmenzhen(session, scid);
						obj.put("MenzhenHepatomyocardosis", JSONObject
								.fromObject(
										hepatomyocardosis,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenAPACHEII")) {
						Apacheii apacheii = dao.apacheii_findByScidmenzhen(session,
								scid);
						obj.put("MenzhenAPACHEII", JSONObject
								.fromObject(
										apacheii,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenPHI")) {
						Phi phi = dao.phi_findByScidmenzhen(session, scid);
						obj.put("MenzhenPHI", JSONObject
								.fromObject(
										phi,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenTS")) {
						Ts ts = dao.ts_findByScidmenzhen(session, scid);
						obj.put("MenzhenTS", JSONObject
								.fromObject(
										ts,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					}
				}
			}
			res.setData(obj.toString());
			res.setSuccess(true);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	public ActionForward scoreItems_findByScidzhuyuan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		JSONObject obj = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Long scid = Long.parseLong(request.getParameter("id"));
			String subScoreItems = request.getParameter("subScoreItems");
			// 获取评分时间和阶段
			ScoreComment sc = dao.scoreComment_findByScidzhuyuan(session, scid);
			obj
					.put("sc", JSONObject.fromObject(sc,
							JSONValueProcessor.formatDate("yyyy年MM月dd日HH时mm分"))
							.toString());
			// 获取各个评分表中的值
			if (subScoreItems != null && !subScoreItems.equals("")) {
				String[] scoreItems = subScoreItems.split("\\*");
				String scoreItem = "";
				for (int i = 0; i < scoreItems.length; i++) {
					scoreItem = scoreItems[i];
					if (scoreItem.equals("Child-Pugh")) {
						ChildPugh cp = dao.childPugh_findByScidzhuyuan(session, scid);
						obj.put("Child-Pugh", JSONObject
								.fromObject(
										cp,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MELD")) {
						Meld meld = dao.meld_findByScidzhuyuan(session, scid);
						obj.put("MELD", JSONObject
								.fromObject(
										meld,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("BCLC")) {
						Bclc bclc = dao.bclc_findByScidzhuyuan(session, scid);
						obj.put("BCLC", JSONObject
								.fromObject(
										bclc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("BMI")) {
						Bmi bmi = dao.bmi_findByScidzhuyuan(session, scid);
						obj.put("BMI", JSONObject
								.fromObject(
										bmi,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("CLIP")) {
						Clip clip = dao.clip_findByScidzhuyuan(session, scid);
						obj.put("CLIP", JSONObject
								.fromObject(
										clip,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("GCS")) {
						Gcs gcs = dao.gcs_findByScidzhuyuan(session, scid);
						obj.put("GCS", JSONObject
								.fromObject(
										gcs,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("PLC")) {
						Plc plc = dao.plc_findByScidzhuyuan(session, scid);
						obj.put("PLC", JSONObject
								.fromObject(
										plc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("TNM")) {
						Tnm tnm = dao.tnm_findByScidzhuyuan(session, scid);
						obj.put("TNM", JSONObject
								.fromObject(
										tnm,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("LCTOS")) {
						Lctos lctos = dao.lctos_findByScidzhuyuan(session, scid);
						obj.put("LCTOS", JSONObject
								.fromObject(
										lctos,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("HE")) {
						He he = dao.he_findByScidzhuyuan(session, scid);
						obj.put("HE", JSONObject
								.fromObject(
										he,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("OrganFunc")) {
						OrganFunc organFunc = dao.organFunc_findByScidzhuyuan(session, scid);
						obj.put("OrganFunc", JSONObject
								.fromObject(
										organFunc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("HC")) {
						Hc hc = dao.hc_findByScidzhuyuan(session, scid);
						obj.put("HC", JSONObject
								.fromObject(
										hc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("HRS")) {
						Hrs hrs = dao.hrs_findByScidzhuyuan(session, scid);
						obj.put("HRS", JSONObject
								.fromObject(
										hrs,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("HPS")) {
						Hps hps = dao.hps_findByScidzhuyuan(session, scid);
						obj.put("HPS", JSONObject
								.fromObject(
										hps,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("Hepatomyocardosis")) {
						Hepatomyocardosis hepatomyocardosis = dao
								.hepatomyocardosis_findByScidzhuyuan(session, scid);
						obj.put("Hepatomyocardosis", JSONObject
								.fromObject(
										hepatomyocardosis,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("APACHEII")) {
						Apacheii apacheii = dao.apacheii_findByScidzhuyuan(session,
								scid);
						obj.put("APACHEII", JSONObject
								.fromObject(
										apacheii,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("PHI")) {
						Phi phi = dao.phi_findByScidzhuyuan(session, scid);
						obj.put("PHI", JSONObject
								.fromObject(
										phi,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("TS")) {
						Ts ts = dao.ts_findByScidzhuyuan(session, scid);
						obj.put("TS", JSONObject
								.fromObject(
										ts,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					}
				}
			}
			res.setData(obj.toString());
			res.setSuccess(true);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward scoreItems_findByScid(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		JSONObject obj = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Long scid = Long.parseLong(request.getParameter("id"));
			String subScoreItems = request.getParameter("subScoreItems");
			// 获取评分时间和阶段
			ScoreComment sc = dao.scoreComment_findByScid(session, scid);
			obj
					.put("sc", JSONObject.fromObject(sc,
							JSONValueProcessor.formatDate("yyyy年MM月dd日HH时mm分"))
							.toString());
			// 获取各个评分表中的值
			if (subScoreItems != null && !subScoreItems.equals("")) {
				String[] scoreItems = subScoreItems.split("\\*");
				String scoreItem = "";
				for (int i = 0; i < scoreItems.length; i++) {
					scoreItem = scoreItems[i];
					if (scoreItem.equals("Child-Pugh")) {
						ChildPugh cp = dao.childPugh_findByScid(session, scid);
						obj.put("Child-Pugh", JSONObject
								.fromObject(
										cp,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MELD")) {
						Meld meld = dao.meld_findByScid(session, scid);
						obj.put("MELD", JSONObject
								.fromObject(
										meld,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("BCLC")) {
						Bclc bclc = dao.bclc_findByScid(session, scid);
						obj.put("BCLC", JSONObject
								.fromObject(
										bclc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("BMI")) {
						Bmi bmi = dao.bmi_findByScid(session, scid);
						obj.put("BMI", JSONObject
								.fromObject(
										bmi,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("CLIP")) {
						Clip clip = dao.clip_findByScid(session, scid);
						obj.put("CLIP", JSONObject
								.fromObject(
										clip,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("GCS")) {
						Gcs gcs = dao.gcs_findByScid(session, scid);
						obj.put("GCS", JSONObject
								.fromObject(
										gcs,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("PLC")) {
						Plc plc = dao.plc_findByScid(session, scid);
						obj.put("PLC", JSONObject
								.fromObject(
										plc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("TNM")) {
						Tnm tnm = dao.tnm_findByScid(session, scid);
						obj.put("TNM", JSONObject
								.fromObject(
										tnm,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("LCTOS")) {
						Lctos lctos = dao.lctos_findByScid(session, scid);
						obj.put("LCTOS", JSONObject
								.fromObject(
										lctos,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("HE")) {
						He he = dao.he_findByScid(session, scid);
						obj.put("HE", JSONObject
								.fromObject(
										he,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("OrganFunc")) {
						OrganFunc organFunc = dao.organFunc_findByScid(session,
								scid);
						obj.put("OrganFunc", JSONObject
								.fromObject(
										organFunc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("HC")) {
						Hc hc = dao.hc_findByScid(session, scid);
						obj.put("HC", JSONObject
								.fromObject(
										hc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("HRS")) {
						Hrs hrs = dao.hrs_findByScid(session, scid);
						obj.put("HRS", JSONObject
								.fromObject(
										hrs,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("HPS")) {
						Hps hps = dao.hps_findByScid(session, scid);
						obj.put("HPS", JSONObject
								.fromObject(
										hps,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("Hepatomyocardosis")) {
						Hepatomyocardosis hepatomyocardosis = dao
								.hepatomyocardosis_findByScid(session, scid);
						obj.put("Hepatomyocardosis", JSONObject
								.fromObject(
										hepatomyocardosis,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("APACHEII")) {
						Apacheii apacheii = dao.apacheii_findByScid(session,
								scid);
						obj.put("APACHEII", JSONObject
								.fromObject(
										apacheii,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("PHI")) {
						Phi phi = dao.phi_findByScid(session, scid);
						obj.put("PHI", JSONObject
								.fromObject(
										phi,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("TS")) {
						Ts ts = dao.ts_findByScid(session, scid);
						obj.put("TS", JSONObject
								.fromObject(
										ts,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					}
				}
			}
			res.setData(obj.toString());
			res.setSuccess(true);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	//器官 (门诊添加)
	public ActionForward scoreItems_saveOrUpdatemenzhen(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		JSONObject object = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String data = request.getParameter("data");
			data = data.replace("年", "-").replace("月", "-").replace("日", " ").replace("时", ":").replace("分", "");
			JSONObject json = JSONObject.fromObject(data);
			
			ScoreComment sc = (ScoreComment) JSONObject.toBean(json,
					ScoreComment.class);
			sc = dao.scoreComment_saveOrUpdate(session, sc);
			Long scId = sc.getId();
			object
					.put("sc", JSONObject.fromObject(sc,
							JSONValueProcessor.formatDate("yyyy年MM月dd日HH时"))
							.toString());
			json = JSONObject
					.fromObject(request.getParameter("scoreItemsData"));
			String subScoreItems = request.getParameter("subScoreItems");
			if (subScoreItems != null && !subScoreItems.equals("")) {
				String[] scoreItems = subScoreItems.split("\\*");
				String scoreItem = "";
				for (int i = 0; i < scoreItems.length; i++) {
					scoreItem = scoreItems[i];
					JSONObject obj = JSONObject.fromObject(json
							.getJSONObject(scoreItem));
					if (scoreItem.equals("MenzhenChild-Pugh")) {
						ChildPugh cp = (ChildPugh) JSONObject.toBean(obj,
								ChildPugh.class);
						cp.setScId(scId);
						cp = dao.childPugh_saveOrUpdate(session, cp);
						object.put("MenzhenChild-Pugh", JSONObject
								.fromObject(
										cp,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenMELD")) {
						Meld meld = (Meld) JSONObject.toBean(obj, Meld.class);
						meld.setScId(scId);
						meld = dao.meld_saveOrUpdate(session, meld);
						object.put("MenzhenMELD", JSONObject
								.fromObject(
										meld,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenBCLC")) {
						Bclc bclc = (Bclc) JSONObject.toBean(obj, Bclc.class);
						bclc.setScId(scId);
						bclc = dao.bclc_saveOrUpdate(session, bclc);
						object.put("MenzhenBCLC", JSONObject
								.fromObject(
										bclc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenBMI")) {
						Bmi bmi = (Bmi) JSONObject.toBean(obj, Bmi.class);
						bmi.setScId(scId);
						bmi = dao.bmi_saveOrUpdate(session, bmi);
						object.put("MenzhenBMI", JSONObject
								.fromObject(
										bmi,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenCLIP")) {
						Clip clip = (Clip) JSONObject.toBean(obj, Clip.class);
						clip.setScId(scId);
						clip = dao.clip_saveOrUpdate(session, clip);
						object.put("MenzhenCLIP", JSONObject
								.fromObject(
										clip,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenGCS")) {
						Gcs gcs = (Gcs) JSONObject.toBean(obj, Gcs.class);
						gcs.setScId(scId);
						gcs = dao.gcs_saveOrUpdate(session, gcs);
						object.put("MenzhenGCS", JSONObject
								.fromObject(
										gcs,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenPLC")) {
						Plc plc = (Plc) JSONObject.toBean(obj, Plc.class);
						plc.setScId(scId);
						plc = dao.plc_saveOrUpdate(session, plc);
						object.put("MenzhenPLC", JSONObject
								.fromObject(
										plc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenTNM")) {
						Tnm tnm = (Tnm) JSONObject.toBean(obj, Tnm.class);
						tnm.setScId(scId);
						tnm = dao.tnm_saveOrUpdate(session, tnm);
						object.put("MenzhenTNM", JSONObject
								.fromObject(
										tnm,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenLCTOS")) {
						Lctos lctos = (Lctos) JSONObject.toBean(obj,
								Lctos.class);
						lctos.setScId(scId);
						lctos = dao.lctos_saveOrUpdate(session, lctos);
						object.put("MenzhenLCTOS", JSONObject
								.fromObject(
										lctos,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenHE")) {
						He he = (He) JSONObject.toBean(obj, He.class);
						he.setScId(scId);
						he = dao.he_saveOrUpdate(session, he);
						object.put("MenzhenHE", JSONObject
								.fromObject(
										he,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenOrganFunc")) {
						OrganFunc organFunc = (OrganFunc) JSONObject.toBean(
								obj, OrganFunc.class);
						organFunc.setScId(scId);
						organFunc = dao.organFunc_saveOrUpdate(session,
								organFunc);
						object.put("MenzhenOrganFunc", JSONObject
								.fromObject(
										organFunc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenHC")) {
						Hc hc = (Hc) JSONObject.toBean(obj, Hc.class);
						hc.setScId(scId);
						hc = dao.hc_saveOrUpdate(session, hc);
						object.put("MenzhenHC", JSONObject
								.fromObject(
										hc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenHRS")) {
						Hrs hrs = (Hrs) JSONObject.toBean(obj, Hrs.class);
						hrs.setScId(scId);
						hrs = dao.hrs_saveOrUpdate(session, hrs);
						object.put("MenzhenHRS", JSONObject
								.fromObject(
										hrs,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenHPS")) {
						Hps hps = (Hps) JSONObject.toBean(obj, Hps.class);
						hps.setScId(scId);
						hps = dao.hps_saveOrUpdate(session, hps);
						object.put("MenzhenHPS", JSONObject
								.fromObject(
										hps,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenHepatomyocardosis")) {
						Hepatomyocardosis hepatomyocardosis = (Hepatomyocardosis) JSONObject
								.toBean(obj, Hepatomyocardosis.class);
						hepatomyocardosis.setScId(scId);
						hepatomyocardosis = dao.hepatomyocardosis_saveOrUpdate(
								session, hepatomyocardosis);
						object.put("MenzhenHepatomyocardosis", JSONObject
								.fromObject(
										hepatomyocardosis,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenAPACHEII")) {
						Apacheii apacheii = (Apacheii) JSONObject.toBean(obj,
								Apacheii.class);
						apacheii.setScId(scId);
						apacheii = dao.apacheii_saveOrUpdate(session, apacheii);
						object.put("MenzhenAPACHEII", JSONObject
								.fromObject(
										apacheii,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenPHI")) {
						Phi phi = (Phi) JSONObject.toBean(obj, Phi.class);
						phi.setScId(scId);
						phi = dao.phi_saveOrUpdate(session, phi);
						object.put("MenzhenPHI", JSONObject
								.fromObject(
										phi,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MenzhenTS")) {
						Ts ts = (Ts) JSONObject.toBean(obj, Ts.class);
						ts.setScId(scId);
						ts = dao.ts_saveOrUpdate(session, ts);
						object.put("MenzhenTS", JSONObject
								.fromObject(
										ts,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					}
				}
			}
			tran.commit();
			res.setData(object.toString());
			res.setSuccess(true);
		} catch (Exception e) {
			tran.rollback();
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward scoreItems_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		JSONObject object = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String data = request.getParameter("data");	
			data = data.replace("年", "-").replace("月", "-").replace("日", " ").replace("时", ":").replace("分", "");
			JSONObject json = JSONObject.fromObject(data);
			
			ScoreComment sc = (ScoreComment) JSONObject.toBean(json,
					ScoreComment.class);
			sc = dao.scoreComment_saveOrUpdate(session, sc);
			Long scId = sc.getId();
			object
					.put("sc", JSONObject.fromObject(sc,
							JSONValueProcessor.formatDate("yyyy年MM月dd日HH时mm分"))
							.toString());
			json = JSONObject
					.fromObject(request.getParameter("scoreItemsData"));
			String subScoreItems = request.getParameter("subScoreItems");
			if (subScoreItems != null && !subScoreItems.equals("")) {
				String[] scoreItems = subScoreItems.split("\\*");
				String scoreItem = "";
				for (int i = 0; i < scoreItems.length; i++) {
					scoreItem = scoreItems[i];
					JSONObject obj = JSONObject.fromObject(json
							.getJSONObject(scoreItem));
					if (scoreItem.equals("Child-Pugh")) {
						ChildPugh cp = (ChildPugh) JSONObject.toBean(obj,
								ChildPugh.class);
						cp.setScId(scId);
						cp = dao.childPugh_saveOrUpdate(session, cp);
						object.put("Child-Pugh", JSONObject
								.fromObject(
										cp,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("MELD")) {
						Meld meld = (Meld) JSONObject.toBean(obj, Meld.class);
						meld.setScId(scId);
						meld = dao.meld_saveOrUpdate(session, meld);
						object.put("MELD", JSONObject
								.fromObject(
										meld,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("BCLC")) {
						Bclc bclc = (Bclc) JSONObject.toBean(obj, Bclc.class);
						bclc.setScId(scId);
						bclc = dao.bclc_saveOrUpdate(session, bclc);
						object.put("BCLC", JSONObject
								.fromObject(
										bclc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("BMI")) {
						Bmi bmi = (Bmi) JSONObject.toBean(obj, Bmi.class);
						bmi.setScId(scId);
						bmi = dao.bmi_saveOrUpdate(session, bmi);
						object.put("BMI", JSONObject
								.fromObject(
										bmi,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("CLIP")) {
						Clip clip = (Clip) JSONObject.toBean(obj, Clip.class);
						clip.setScId(scId);
						clip = dao.clip_saveOrUpdate(session, clip);
						object.put("CLIP", JSONObject
								.fromObject(
										clip,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("GCS")) {
						Gcs gcs = (Gcs) JSONObject.toBean(obj, Gcs.class);
						gcs.setScId(scId);
						gcs = dao.gcs_saveOrUpdate(session, gcs);
						object.put("GCS", JSONObject
								.fromObject(
										gcs,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("PLC")) {
						Plc plc = (Plc) JSONObject.toBean(obj, Plc.class);
						plc.setScId(scId);
						plc = dao.plc_saveOrUpdate(session, plc);
						object.put("PLC", JSONObject
								.fromObject(
										plc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("TNM")) {
						Tnm tnm = (Tnm) JSONObject.toBean(obj, Tnm.class);
						tnm.setScId(scId);
						tnm = dao.tnm_saveOrUpdate(session, tnm);
						object.put("TNM", JSONObject
								.fromObject(
										tnm,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("LCTOS")) {
						Lctos lctos = (Lctos) JSONObject.toBean(obj,
								Lctos.class);
						lctos.setScId(scId);
						lctos = dao.lctos_saveOrUpdate(session, lctos);
						object.put("LCTOS", JSONObject
								.fromObject(
										lctos,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("HE")) {
						He he = (He) JSONObject.toBean(obj, He.class);
						he.setScId(scId);
						he = dao.he_saveOrUpdate(session, he);
						object.put("HE", JSONObject
								.fromObject(
										he,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("OrganFunc")) {
						OrganFunc organFunc = (OrganFunc) JSONObject.toBean(
								obj, OrganFunc.class);
						organFunc.setScId(scId);
						organFunc = dao.organFunc_saveOrUpdate(session,
								organFunc);
						object.put("OrganFunc", JSONObject
								.fromObject(
										organFunc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("HC")) {
						Hc hc = (Hc) JSONObject.toBean(obj, Hc.class);
						hc.setScId(scId);
						hc = dao.hc_saveOrUpdate(session, hc);
						object.put("HC", JSONObject
								.fromObject(
										hc,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("HRS")) {
						Hrs hrs = (Hrs) JSONObject.toBean(obj, Hrs.class);
						hrs.setScId(scId);
						hrs = dao.hrs_saveOrUpdate(session, hrs);
						object.put("HRS", JSONObject
								.fromObject(
										hrs,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("HPS")) {
						Hps hps = (Hps) JSONObject.toBean(obj, Hps.class);
						hps.setScId(scId);
						hps = dao.hps_saveOrUpdate(session, hps);
						object.put("HPS", JSONObject
								.fromObject(
										hps,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("Hepatomyocardosis")) {
						Hepatomyocardosis hepatomyocardosis = (Hepatomyocardosis) JSONObject
								.toBean(obj, Hepatomyocardosis.class);
						hepatomyocardosis.setScId(scId);
						hepatomyocardosis = dao.hepatomyocardosis_saveOrUpdate(
								session, hepatomyocardosis);
						object.put("Hepatomyocardosis", JSONObject
								.fromObject(
										hepatomyocardosis,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("APACHEII")) {
						Apacheii apacheii = (Apacheii) JSONObject.toBean(obj,
								Apacheii.class);
						apacheii.setScId(scId);
						apacheii = dao.apacheii_saveOrUpdate(session, apacheii);
						object.put("APACHEII", JSONObject
								.fromObject(
										apacheii,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("PHI")) {
						Phi phi = (Phi) JSONObject.toBean(obj, Phi.class);
						phi.setScId(scId);
						phi = dao.phi_saveOrUpdate(session, phi);
						object.put("PHI", JSONObject
								.fromObject(
										phi,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					} else if (scoreItem.equals("TS")) {
						Ts ts = (Ts) JSONObject.toBean(obj, Ts.class);
						ts.setScId(scId);
						ts = dao.ts_saveOrUpdate(session, ts);
						object.put("TS", JSONObject
								.fromObject(
										ts,
										JSONValueProcessor
												.formatDate("yyyy年MM月dd日HH时"))
								.toString());
					}
				}
			}
			tran.commit();
			res.setData(object.toString());
			res.setSuccess(true);
		} catch (Exception e) {
			tran.rollback();
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			out.println(JSONObject.fromObject(res).toString());
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
			//JSONObject data = JSONObject.fromObject(request
			//		.getParameter("data"));
			ScoreCommentPrintImpl impl = new ScoreCommentPrintImpl();
			impl.print(out, request.getRealPath("/"), request
					.getParameter("kid"), request.getParameter("ssmid"), request
					.getParameter("scid"));
			// dao.print(out, data, request.getRealPath("/"));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@SuppressWarnings("deprecation")
	public ActionForward deleteData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String ssmid = request.getParameter("ssmid");
			String scid = request.getParameter("scid");
			daoDelete.deleteData(ssmid, scid);
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		}finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public ActionForward print1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/pdf");
		try {
			ServletOutputStream out = response.getOutputStream();
			//JSONObject data = JSONObject.fromObject(request
			//		.getParameter("data"));
			ScoreCommentPrintImpl1 impl1 = new ScoreCommentPrintImpl1();
			impl1.print(out, request.getRealPath("/"), request
					.getParameter("kid"), request.getParameter("ssmid"), request
					.getParameter("scid"));
			// dao.print(out, data, request.getRealPath("/"));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String str = "sdklsd*fklfs";
		String[] arr = str.split("\\*");
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}
}
