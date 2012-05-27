package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.ScoreCommentDeleteDao;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.ScoreComment;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.ScoreSetMeal;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class ScoreCommentDeleteImpl implements ScoreCommentDeleteDao {

	private Map<String, String> data;// 数据中心
	private ScoreCommentImpl impl;// 评分实体查询工具


	/**
	 * 删除器官功能评价中的数据
	 */
	public void deleteData(String ssmid,
			String scid){
		data = new HashMap<String, String>();
		data.put("ssmid", ssmid);
		data.put("scid", scid);
		preparData();
	}
		
		
	/**
	 * 准备删除的数据
	 * 
	 * @throws Exception
	 */
	private void preparData(){
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			
			// 总评分信息
			ScoreComment scoreComment = (ScoreComment) session.get(
					ScoreComment.class, Long.parseLong(data.get("scid")));
			session.delete(scoreComment);
			// 子页面评分信息
			ScoreSetMeal scoreSetMeal = (ScoreSetMeal) session.get(
					ScoreSetMeal.class, Long.parseLong(data.get("ssmid")));
			if (scoreSetMeal != null) {
				data.put("title", scoreSetMeal.getName());
				data.put("printPages", scoreSetMeal.getSubScoreItems());
				String[] printPages = scoreSetMeal.getSubScoreItems().split(
						"\\*");
				for (int i = 0; i < printPages.length; i++) {
					// 压入子页面数据
					preparPageData(printPages[i], session);
				}
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	/**
	 * 准备子页面数据
	 * 
	 * @param pageflag
	 * @param session
	 * @throws Exception
	 */
	private void preparPageData(String pageflag, Session session)
			throws Exception {
		Long scid = Long.parseLong(data.get("scid"));
		try {
			if (pageflag.equals("Child-Pugh")) {
				returnString("t_ScoreComment_ChildPugh",scid,session);
			} else if (pageflag.equals("MELD")) {
				returnString("t_ScoreComment_Meld",scid,session);
			} else if (pageflag.equals("BCLC")) {
				returnString("t_ScoreComment_Bclc",scid,session);
			} else if (pageflag.equals("BMI")) {
				returnString("t_ScoreComment_Bmi",scid,session);
			} else if (pageflag.equals("CLIP")) {
				returnString("t_ScoreComment_Clip",scid,session);
			} else if (pageflag.equals("GCS")) {
				returnString("t_ScoreComment_Gcs",scid,session);
			} else if (pageflag.equals("PLC")) {
				returnString("t_ScoreComment_Plc",scid,session);
			} else if (pageflag.equals("TNM")) {
				returnString("t_ScoreComment_Tnm",scid,session);
			} else if (pageflag.equals("LCTOS")) {
				returnString("t_ScoreComment_Lctos",scid,session);
			} else if (pageflag.equals("HE")) {
				returnString("t_ScoreComment_He",scid,session);
			} else if (pageflag.equals("OrganFunc")) {
				returnString("t_ScoreComment_OrganFunc",scid,session);
			} else if (pageflag.equals("HC")) {
				returnString("t_ScoreComment_Hc",scid,session);
			} else if (pageflag.equals("HRS")) {
				returnString("t_ScoreComment_Hrs",scid,session);
			} else if (pageflag.equals("HPS")) {
				returnString("t_ScoreComment_Hps",scid,session);
			} else if (pageflag.equals("Hepatomyocardosis")) {
				returnString("t_ScoreComment_Hepatomyocardosis",scid,session);
			} else if (pageflag.equals("APACHEII")) {
				returnString("t_ScoreComment_Apacheii",scid,session);
			} else if (pageflag.equals("PHI")) {
				returnString("t_ScoreComment_Phi",scid,session);
			} else if (pageflag.equals("TS")) {
				returnString("t_ScoreComment_Ts",scid,session);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void returnString(String name,Long scid,Session session){
		String sql = "delete from "+name+" where scId ="+scid+"  and fiag='N'";
		session.createSQLQuery(sql).executeUpdate();
	}
}
