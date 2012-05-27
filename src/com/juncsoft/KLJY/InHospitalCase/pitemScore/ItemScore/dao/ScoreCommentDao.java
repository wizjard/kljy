package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao;

import javax.servlet.ServletOutputStream;

import org.hibernate.Session;

import com.itextpdf.text.pdf.codec.Base64.OutputStream;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface ScoreCommentDao {

	public JSONArray public_mainMenu(Long ssmId, Long caseId)throws Exception;
	public ScoreComment scoreComment_saveOrUpdate(Session session, ScoreComment sc) throws Exception;
	public ScoreComment scoreComment_findByScid(Session session, Long id) throws Exception;
	
	//器官功能评价(门诊)
	public ScoreComment scoreComment_findByScidmenzhen(Session session, Long id)throws Exception;
	//住院
	public ScoreComment scoreComment_findByScidzhuyuan(Session session, Long id)throws Exception;
	
	
	public ChildPugh childPugh_saveOrUpdate(Session session, ChildPugh cp) throws Exception;
	public ChildPugh childPugh_findByScid(Session session, Long scid) throws Exception;
	 //门诊
	public ChildPugh childPugh_findByScidmenzhen(Session session, Long scid) throws Exception;
	//住院
	public ChildPugh childPugh_findByScidzhuyuan(Session session, Long scid) throws Exception;
	
	public Meld meld_saveOrUpdate(Session session, Meld meld) throws Exception;
	public Meld meld_findByScid(Session session, Long scid) throws Exception;
	//门诊
	public Meld meld_findByScidmenzhen(Session session, Long scid) throws Exception ;
	//住院
	public Meld meld_findByScidzhuyuan(Session session, Long scid) throws Exception ;
	
	public Bclc bclc_saveOrUpdate(Session session, Bclc bclc) throws Exception;
	public Bclc bclc_findByScid(Session session, Long scid) throws Exception;
	//门诊
	public Bclc bclc_findByScidmenzhen(Session session, Long scid) throws Exception ;
	//住院
	public Bclc bclc_findByScidzhuyuan(Session session, Long scid) throws Exception;
	
	public Bmi bmi_findByScid(Session session, Long scid) throws Exception;
	public Bmi bmi_saveOrUpdate(Session session, Bmi bmi) throws Exception;
	//门诊
	public Bmi bmi_findByScidmenzhen(Session session, Long scid) throws Exception ;
	//住院
	public Bmi bmi_findByScidzhuyuan(Session session, Long scid) throws Exception;
	
	public Clip clip_findByScid(Session session, Long scid) throws Exception;
	public Clip clip_saveOrUpdate(Session session, Clip clip) throws Exception;
	//门诊
	public Clip clip_findByScidmenzhen(Session session, Long scid) throws Exception ;
	//住院
	public Clip clip_findByScidzhuyuan(Session session, Long scid) throws Exception ;
	
	public Gcs gcs_findByScid(Session session, Long scid) throws Exception;
	public Gcs gcs_saveOrUpdate(Session session, Gcs gcs) throws Exception;
	//门诊
	public Gcs gcs_findByScidmenzhen(Session session, Long scid) throws Exception;
	//住院
	public Gcs gcs_findByScidzhuyuan(Session session, Long scid) throws Exception ;
	
	public Plc plc_findByScid(Session session, Long scid) throws Exception;
	public Plc plc_saveOrUpdate(Session session, Plc plc) throws Exception;
	public Plc plc_findByScidmenzhen(Session session, Long scid) throws Exception;//门诊
	public Plc plc_findByScidzhuyuan(Session session, Long scid) throws Exception;//住院
	
	public Tnm tnm_findByScid(Session session, Long scid) throws Exception;
	public Tnm tnm_saveOrUpdate(Session session, Tnm tnm) throws Exception;
	public Tnm tnm_findByScidmenzhen(Session session, Long scid) throws Exception;//门诊
	public Tnm tnm_findByScidzhuyuan(Session session, Long scid) throws Exception;//住院
	
	public Lctos lctos_findByScid(Session session, Long scid) throws Exception;
	public Lctos lctos_saveOrUpdate(Session session, Lctos lctos) throws Exception;
	public Lctos lctos_findByScidmenzhen(Session session, Long scid) throws Exception;//门诊
	public Lctos lctos_findByScidzhuyuan(Session session, Long scid) throws Exception;//住院
	
	public He he_findByScid(Session session, Long scid) throws Exception;
	public He he_saveOrUpdate(Session session, He he) throws Exception;
	//门诊
	public He he_findByScidmenzhen(Session session, Long scid) throws Exception;
	//住院
	public He he_findByScidzhuyuan(Session session, Long scid) throws Exception;
	
	public Hepatomyocardosis hepatomyocardosis_findByScid(Session session, Long scid) throws Exception;
	public Hepatomyocardosis hepatomyocardosis_saveOrUpdate(Session session, Hepatomyocardosis hepatomyocardosis) throws Exception;
	public Hepatomyocardosis hepatomyocardosis_findByScidmenzhen(Session session, Long scid) throws Exception;//门诊
	public Hepatomyocardosis hepatomyocardosis_findByScidzhuyuan(Session session, Long scid) throws Exception;//住院
	
	public Hps hps_findByScid(Session session, Long scid) throws Exception;
	public Hps hps_saveOrUpdate(Session session, Hps hps) throws Exception;
	public Hps hps_findByScidmenzhen(Session session, Long scid) throws Exception;//门诊
	public Hps hps_findByScidzhuyuan(Session session, Long scid) throws Exception;//住院
	
	public Hrs hrs_findByScid(Session session, Long scid) throws Exception;
	public Hrs hrs_saveOrUpdate(Session session, Hrs hrs) throws Exception;
	public Hrs hrs_findByScidmenzhen(Session session, Long scid) throws Exception;//门诊
	public Hrs hrs_findByScidzhuyuan(Session session, Long scid) throws Exception;//住院
	
	public Apacheii apacheii_findByScid(Session session, Long scid) throws Exception;
	public Apacheii apacheii_saveOrUpdate(Session session, Apacheii apacheii) throws Exception;
	public Apacheii apacheii_findByScidmenzhen(Session session, Long scid) throws Exception;//门诊
	public Apacheii apacheii_findByScidzhuyuan(Session session, Long scid) throws Exception;//住院
	
	public Hc hc_findByScid(Session session, Long scid) throws Exception;
	public Hc hc_saveOrUpdate(Session session, Hc hc) throws Exception;
	public Hc hc_findByScidmenzhen(Session session, Long scid) throws Exception;//门诊
	public Hc hc_findByScidzhuyuan(Session session, Long scid) throws Exception;//住院
	
	public Phi phi_findByScid(Session session, Long scid) throws Exception;
	public Phi phi_saveOrUpdate(Session session, Phi phi) throws Exception;
	public Phi phi_findByScidmenzhen(Session session, Long scid) throws Exception;//门诊
	public Phi phi_findByScidzhuyuan(Session session, Long scid) throws Exception;//住院
	
	public Ts ts_findByScid(Session session, Long scid) throws Exception;
	public Ts ts_saveOrUpdate(Session session, Ts ts) throws Exception;
	public Ts ts_findByScidmenzhen(Session session, Long scid) throws Exception;//门诊
	public Ts ts_findByScidzhuyuan(Session session, Long scid) throws Exception;//住院
	///////////////////////////////////////////////////////////////
	public OrganFunc organFunc_findByScid(Session session, Long scid) throws Exception;
	public OrganFunc organFunc_saveOrUpdate(Session session, OrganFunc organFunc) throws Exception;
	//肝功能评价(门诊)
	public OrganFunc organFunc_findByScidmenzhen(Session session, Long scid) throws Exception ;
	//肝功能评价(住院)
	public OrganFunc organFunc_findByScidzhuyuan(Session session, Long scid) throws Exception;
	
	
	public String getScoreSetMeal() throws Exception;
	//肝功能评价(门诊)
	public String getScoreSetMealmenzhen() throws Exception;
	
	public void print(ServletOutputStream out, JSONObject data, String path) throws Exception;
	
	//删除器官功能评价
//	public void deleteData(String kid, String ssmid,
//			String scid);
}
