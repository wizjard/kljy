package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.ScoreCommentDao;
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
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.ScoreSetMeal;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.ScoreSetMealmenzhen;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Tnm;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Ts;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.DictionaryUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;

public class ScoreCommentImpl implements ScoreCommentDao {

	@SuppressWarnings("unchecked")
	public JSONArray public_mainMenu(Long ssmId, Long caseId) throws Exception {
		JSONArray array = new JSONArray();
		Session session = null;
		Transaction tran = null;
		List<ScoreComment> list = new ArrayList<ScoreComment>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery(
					"from ScoreComment where ssmId = ? and caseId = ?")
					.setLong(0, ssmId).setLong(1, caseId).list();
			for (ScoreComment sc : list) {
				JSONObject json = JSONObject.fromObject(sc, JSONValueProcessor
						.formatDate("yyyy年MM月dd日HH时mm分"));
				array.add("{id:" + json.getString("id") + ",scoreTime:'"
						+ json.getString("scoreTime")
						+ "',iconCls:'icon-none'}");
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return array;
	}

	public ScoreComment scoreComment_saveOrUpdate(Session session,
			ScoreComment sc) throws Exception {
		Long id = sc.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(sc);
			} else {
				session.update(sc);
			}
			sc = (ScoreComment) session.get(ScoreComment.class, id);
		} catch (Exception e) {
			throw e;
		}
		return sc;
	}

	public ChildPugh childPugh_saveOrUpdate(Session session, ChildPugh cp)
			throws Exception {
		Long id = cp.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(cp);
			} else {
				session.update(cp);
			}
			cp = (ChildPugh) session.get(ChildPugh.class, id);
		} catch (Exception e) {
			throw e;
		}
		return cp;
	}

	public Meld meld_saveOrUpdate(Session session, Meld meld) throws Exception {
		Long id = meld.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(meld);
			} else {
				session.update(meld);
			}
			meld = (Meld) session.get(Meld.class, id);
		} catch (Exception e) {
			throw e;
		}
		return meld;
	}

	@SuppressWarnings("unchecked")
	public ChildPugh childPugh_findByScid(Session session, Long scid)
			throws Exception {
		ChildPugh cp = null;
		try {
			List<ChildPugh> list = session.createQuery(
					"from ChildPugh where scId = ?").setLong(0, scid).list();
			if (list.size() > 0) {
				cp = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return cp;
	}
   //门诊
	@SuppressWarnings("unchecked")
	public ChildPugh childPugh_findByScidmenzhen(Session session, Long scid)
			throws Exception {
		ChildPugh cp = null;
		try {
			List<ChildPugh> list = session.createQuery(
					"from ChildPugh where scId = ? and fiag='Y'").setLong(0, scid).list();
			if (list.size() > 0) {
				cp = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return cp;
		}
	//住院
	@SuppressWarnings("unchecked")
	public ChildPugh childPugh_findByScidzhuyuan(Session session, Long scid)
			throws Exception {
		ChildPugh cp = null;
		try {
			List<ChildPugh> list = session.createQuery(
					"from ChildPugh where scId = ? and fiag='N'").setLong(0, scid).list();
			if (list.size() > 0) {
				cp = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return cp;
		}
	
	@SuppressWarnings("unchecked")
	public Meld meld_findByScid(Session session, Long scid) throws Exception {
		Meld meld = null;
		try {
			List<Meld> list = session.createQuery("from Meld where scId = ?")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				meld = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return meld;
	}

	//门诊
	@SuppressWarnings("unchecked")
	public Meld meld_findByScidmenzhen(Session session, Long scid) throws Exception {
		Meld meld = null;
		try {
			List<Meld> list = session.createQuery("from Meld where scId = ? and fiag='Y'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				meld = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return meld;
	}
	//住院
	@SuppressWarnings("unchecked")
	public Meld meld_findByScidzhuyuan(Session session, Long scid) throws Exception {
		Meld meld = null;
		try {
			List<Meld> list = session.createQuery("from Meld where scId = ? and fiag='N'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				meld = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return meld;
	}
	
	public ScoreComment scoreComment_findByScid(Session session, Long id)
			throws Exception {
		ScoreComment sc = null;
		try {
			sc = (ScoreComment) session.get(ScoreComment.class, id);
		} catch (Exception e) {
			throw e;
		}
		return sc;
	}
	
	//器官功能评价(门诊)
	@SuppressWarnings("unchecked")
	public ScoreComment scoreComment_findByScidmenzhen(Session session, Long id)
			throws Exception {
		ScoreComment sc = null;
		try {
			String sql="from ScoreComment s where s.fiag='Y' and s.id="+id+"";
			Query query=session.createQuery(sql);
			List list=query.list();
			for(int i=0;i<list.size();i++){
				sc=(ScoreComment)list.get(i);
			}
//			sc = (ScoreComment) session.get(ScoreComment.class, id);
		} catch (Exception e) {
			throw e;
		}
		return sc;
		}
	//器官功能评价(住院)
	@SuppressWarnings("unchecked")
	public ScoreComment scoreComment_findByScidzhuyuan(Session session, Long id)
			throws Exception {
		ScoreComment sc = null;
		try {
			String sql="from ScoreComment s where s.fiag='N' and s.id="+id+"";
			Query query=session.createQuery(sql);
			List list=query.list();
			for(int i=0;i<list.size();i++){
				sc=(ScoreComment)list.get(i);
			}
//			sc = (ScoreComment) session.get(ScoreComment.class, id);
		} catch (Exception e) {
			throw e;
		}
		return sc;
		}
	
	@SuppressWarnings("unchecked")
	public Bclc bclc_findByScid(Session session, Long scid) throws Exception {
		Bclc bclc = null;
		try {
			List<Bclc> list = session.createQuery("from Bclc where scId = ?")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				bclc = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return bclc;
	}
//门诊
	@SuppressWarnings("unchecked")
	public Bclc bclc_findByScidmenzhen(Session session, Long scid) throws Exception {
		Bclc bclc = null;
		try {
			List<Bclc> list = session.createQuery("from Bclc where scId = ? and fiag='Y'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				bclc = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return bclc;
	}
	
//住院
	@SuppressWarnings("unchecked")
	public Bclc bclc_findByScidzhuyuan(Session session, Long scid) throws Exception {
		Bclc bclc = null;
		try {
			List<Bclc> list = session.createQuery("from Bclc where scId = ? and fiag='N'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				bclc = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return bclc;
	}
	public Bclc bclc_saveOrUpdate(Session session, Bclc bclc) throws Exception {
		Long id = bclc.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(bclc);
			} else {
				session.update(bclc);
			}
			bclc = (Bclc) session.get(Bclc.class, id);
		} catch (Exception e) {
			throw e;
		}
		return bclc;
	}

	@SuppressWarnings("unchecked")
	public Bmi bmi_findByScid(Session session, Long scid) throws Exception {
		Bmi bmi = null;
		try {
			List<Bmi> list = session.createQuery("from Bmi where scId = ?")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				bmi = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return bmi;
	}

	//门诊
	@SuppressWarnings("unchecked")
	public Bmi bmi_findByScidmenzhen(Session session, Long scid) throws Exception {
		Bmi bmi = null;
		try {
			List<Bmi> list = session.createQuery("from Bmi where scId = ? and fiag='Y'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				bmi = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return bmi;
	}
	//住院
	@SuppressWarnings("unchecked")
	public Bmi bmi_findByScidzhuyuan(Session session, Long scid) throws Exception {
		Bmi bmi = null;
		try {
			List<Bmi> list = session.createQuery("from Bmi where scId = ? and fiag='N'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				bmi = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return bmi;
	}
	public Bmi bmi_saveOrUpdate(Session session, Bmi bmi) throws Exception {
		Long id = bmi.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(bmi);
			} else {
				session.update(bmi);
			}
			bmi = (Bmi) session.get(Bmi.class, id);
		} catch (Exception e) {
			throw e;
		}
		return bmi;
	}

	@SuppressWarnings("unchecked")
	public Clip clip_findByScid(Session session, Long scid) throws Exception {
		Clip clip = null;
		try {
			List<Clip> list = session.createQuery("from Clip where scId = ?")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				clip = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return clip;
	}
	//门诊
	@SuppressWarnings("unchecked")
	public Clip clip_findByScidmenzhen(Session session, Long scid) throws Exception {
		Clip clip = null;
		try {
			List<Clip> list = session.createQuery("from Clip where scId = ? and fiag='Y'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				clip = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return clip;
	}
	//住院
	@SuppressWarnings("unchecked")
	public Clip clip_findByScidzhuyuan(Session session, Long scid) throws Exception {
		Clip clip = null;
		try {
			List<Clip> list = session.createQuery("from Clip where scId = ? and fiag='N'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				clip = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return clip;
	}

	public Clip clip_saveOrUpdate(Session session, Clip clip) throws Exception {
		Long id = clip.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(clip);
			} else {
				session.update(clip);
			}
			clip = (Clip) session.get(Clip.class, id);
		} catch (Exception e) {
			throw e;
		}
		return clip;
	}

	@SuppressWarnings("unchecked")
	public Gcs gcs_findByScid(Session session, Long scid) throws Exception {
		Gcs gcs = null;
		try {
			List<Gcs> list = session.createQuery("from Gcs where scId = ?")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				gcs = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return gcs;
	}
//门诊
	@SuppressWarnings("unchecked")
	public Gcs gcs_findByScidmenzhen(Session session, Long scid) throws Exception {
		Gcs gcs = null;
		try {
			List<Gcs> list = session.createQuery("from Gcs where scId = ? and fiag='Y'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				gcs = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return gcs;
	}
	//住院
	@SuppressWarnings("unchecked")
	public Gcs gcs_findByScidzhuyuan(Session session, Long scid) throws Exception {
		Gcs gcs = null;
		try {
			List<Gcs> list = session.createQuery("from Gcs where scId = ? and fiag='N'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				gcs = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return gcs;
	}
	
	public Gcs gcs_saveOrUpdate(Session session, Gcs gcs) throws Exception {
		Long id = gcs.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(gcs);
			} else {
				session.update(gcs);
			}
			gcs = (Gcs) session.get(Gcs.class, id);
		} catch (Exception e) {
			throw e;
		}
		return gcs;
	}

	public Plc plc_findByScid(Session session, Long scid) throws Exception {
		Plc plc = null;
		try {
			List<Plc> list = session.createQuery("from Plc where scId = ?")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				plc = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return plc;
	}
	//门诊
	public Plc plc_findByScidmenzhen(Session session, Long scid) throws Exception {
		Plc plc = null;
		try {
			List<Plc> list = session.createQuery("from Plc where scId = ? and fiag='Y'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				plc = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return plc;
	}
	//住院
	public Plc plc_findByScidzhuyuan(Session session, Long scid) throws Exception {
		Plc plc = null;
		try {
			List<Plc> list = session.createQuery("from Plc where scId = ? and fiag='N'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				plc = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return plc;
	}
	public Plc plc_saveOrUpdate(Session session, Plc plc) throws Exception {
		Long id = plc.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(plc);
			} else {
				session.update(plc);
			}
			plc = (Plc) session.get(Plc.class, id);
		} catch (Exception e) {
			throw e;
		}
		return plc;
	}

	public Tnm tnm_findByScid(Session session, Long scid) throws Exception {
		Tnm tnm = null;
		try {
			List<Tnm> list = session.createQuery("from Tnm where scId = ?")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				tnm = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return tnm;
	}
   //门诊
	public Tnm tnm_findByScidmenzhen(Session session, Long scid) throws Exception {
		Tnm tnm = null;
		try {
			List<Tnm> list = session.createQuery("from Tnm where scId = ? and fiag='Y'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				tnm = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return tnm;
	}
	//住院
	public Tnm tnm_findByScidzhuyuan(Session session, Long scid) throws Exception {
		Tnm tnm = null;
		try {
			List<Tnm> list = session.createQuery("from Tnm where scId = ? and fiag='N'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				tnm = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return tnm;
	}
	public Tnm tnm_saveOrUpdate(Session session, Tnm tnm) throws Exception {
		Long id = tnm.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(tnm);
			} else {
				session.update(tnm);
			}
			tnm = (Tnm) session.get(Tnm.class, id);
		} catch (Exception e) {
			throw e;
		}
		return tnm;
	}

	public Lctos lctos_findByScid(Session session, Long scid) throws Exception {
		Lctos lctos = null;
		try {
			List<Lctos> list = session.createQuery("from Lctos where scId = ?")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				lctos = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return lctos;
	}
	//门诊 
	public Lctos lctos_findByScidmenzhen(Session session, Long scid) throws Exception {
		Lctos lctos = null;
		try {
			List<Lctos> list = session.createQuery("from Lctos where scId = ? and fiag='Y'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				lctos = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return lctos;
	}
	//住院
	public Lctos lctos_findByScidzhuyuan(Session session, Long scid) throws Exception {
		Lctos lctos = null;
		try {
			List<Lctos> list = session.createQuery("from Lctos where scId = ? and fiag='N'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				lctos = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return lctos;
	}

	public Lctos lctos_saveOrUpdate(Session session, Lctos lctos)
			throws Exception {
		Long id = lctos.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(lctos);
			} else {
				session.update(lctos);
			}
			lctos = (Lctos) session.get(Lctos.class, id);
		} catch (Exception e) {
			throw e;
		}
		return lctos;
	}

	public He he_findByScid(Session session, Long scid) throws Exception {
		He he = null;
		try {
			List<He> list = session.createQuery("from He where scId = ?")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				he = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return he;
	}
//门诊查询
	public He he_findByScidmenzhen(Session session, Long scid) throws Exception {
		He he = null;
		try {
			List<He> list = session.createQuery("from He where scId = ? and fiag='Y'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				he = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return he;
	}
//住院查询
	public He he_findByScidzhuyuan(Session session, Long scid) throws Exception {
		He he = null;
		try {
			List<He> list = session.createQuery("from He where scId = ? and fiag='N'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				he = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return he;
	}
	public He he_saveOrUpdate(Session session, He he) throws Exception {
		Long id = he.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(he);
			} else {
				session.update(he);
			}
			he = (He) session.get(He.class, id);
		} catch (Exception e) {
			throw e;
		}
		return he;
	}

	public Hepatomyocardosis hepatomyocardosis_findByScid(Session session,
			Long scid) throws Exception {
		Hepatomyocardosis hepatomyocardosis = null;
		try {
			List<Hepatomyocardosis> list = session.createQuery(
					"from Hepatomyocardosis where scId = ?").setLong(0, scid)
					.list();
			if (list.size() > 0) {
				hepatomyocardosis = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return hepatomyocardosis;
	}

	//门诊
	public Hepatomyocardosis hepatomyocardosis_findByScidmenzhen(Session session,
			Long scid) throws Exception {
		Hepatomyocardosis hepatomyocardosis = null;
		try {
			List<Hepatomyocardosis> list = session.createQuery(
					"from Hepatomyocardosis where scId = ? and fiag='Y'").setLong(0, scid)
					.list();
			if (list.size() > 0) {
				hepatomyocardosis = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return hepatomyocardosis;
	}
	//住院
	public Hepatomyocardosis hepatomyocardosis_findByScidzhuyuan(Session session,
			Long scid) throws Exception {
		Hepatomyocardosis hepatomyocardosis = null;
		try {
			List<Hepatomyocardosis> list = session.createQuery(
					"from Hepatomyocardosis where scId = ? and fiag='N'").setLong(0, scid)
					.list();
			if (list.size() > 0) {
				hepatomyocardosis = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return hepatomyocardosis;
	}
	public Hepatomyocardosis hepatomyocardosis_saveOrUpdate(Session session,
			Hepatomyocardosis hepatomyocardosis) throws Exception {
		Long id = hepatomyocardosis.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(hepatomyocardosis);
			} else {
				session.update(hepatomyocardosis);
			}
			hepatomyocardosis = (Hepatomyocardosis) session.get(
					Hepatomyocardosis.class, id);
		} catch (Exception e) {
			throw e;
		}
		return hepatomyocardosis;
	}

	public Hps hps_findByScid(Session session, Long scid) throws Exception {
		Hps hps = null;
		try {
			List<Hps> list = session.createQuery("from Hps where scId = ?")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				hps = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return hps;
	}

	//门诊
	public Hps hps_findByScidmenzhen(Session session, Long scid) throws Exception {
		Hps hps = null;
		try {
			List<Hps> list = session.createQuery("from Hps where scId = ? and fiag='Y'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				hps = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return hps;
	}
	//住院
	public Hps hps_findByScidzhuyuan(Session session, Long scid) throws Exception {
		Hps hps = null;
		try {
			List<Hps> list = session.createQuery("from Hps where scId = ? and fiag='N'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				hps = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return hps;
	}
	public Hps hps_saveOrUpdate(Session session, Hps hps) throws Exception {
		Long id = hps.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(hps);
			} else {
				session.update(hps);
			}
			hps = (Hps) session.get(Hps.class, id);
		} catch (Exception e) {
			throw e;
		}
		return hps;
	}

	public Hrs hrs_findByScid(Session session, Long scid) throws Exception {
		Hrs hrs = null;
		try {
			List<Hrs> list = session.createQuery("from Hrs where scId = ?")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				hrs = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return hrs;
	}

	//门诊
	public Hrs hrs_findByScidmenzhen(Session session, Long scid) throws Exception {
		Hrs hrs = null;
		try {
			List<Hrs> list = session.createQuery("from Hrs where scId = ? and fiag='Y'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				hrs = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return hrs;
	}
	//住院
	public Hrs hrs_findByScidzhuyuan(Session session, Long scid) throws Exception {
		Hrs hrs = null;
		try {
			List<Hrs> list = session.createQuery("from Hrs where scId = ? and fiag='N'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				hrs = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return hrs;
	}
	public Hrs hrs_saveOrUpdate(Session session, Hrs hrs) throws Exception {
		Long id = hrs.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(hrs);
			} else {
				session.update(hrs);
			}
			hrs = (Hrs) session.get(Hrs.class, id);
		} catch (Exception e) {
			throw e;
		}
		return hrs;
	}

	public Hc hc_findByScid(Session session, Long scid) throws Exception {
		Hc hc = null;
		try {
			List<Hc> list = session.createQuery("from Hc where scId = ?")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				hc = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return hc;
	}

	//门诊
	public Hc hc_findByScidmenzhen(Session session, Long scid) throws Exception {
		Hc hc = null;
		try {
			List<Hc> list = session.createQuery("from Hc where scId = ? and fiag='Y'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				hc = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return hc;
	}
	//住院
	public Hc hc_findByScidzhuyuan(Session session, Long scid) throws Exception {
		Hc hc = null;
		try {
			List<Hc> list = session.createQuery("from Hc where scId = ? and fiag='N'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				hc = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return hc;
	}
	public Hc hc_saveOrUpdate(Session session, Hc hc) throws Exception {
		Long id = hc.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(hc);
			} else {
				session.update(hc);
			}
			hc = (Hc) session.get(Hc.class, id);
		} catch (Exception e) {
			throw e;
		}
		return hc;
	}

	public Apacheii apacheii_findByScid(Session session, Long scid)
			throws Exception {
		Apacheii apacheii = null;
		try {
			List<Apacheii> list = session.createQuery(
					"from Apacheii where scId = ?").setLong(0, scid).list();
			if (list.size() > 0) {
				apacheii = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return apacheii;
	}
    //门诊
	public Apacheii apacheii_findByScidmenzhen(Session session, Long scid)
			throws Exception {
		Apacheii apacheii = null;
		try {
			List<Apacheii> list = session.createQuery(
					"from Apacheii where scId = ? and fiag='Y'").setLong(0, scid).list();
			if (list.size() > 0) {
				apacheii = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return apacheii;
		}
	//住院
	public Apacheii apacheii_findByScidzhuyuan(Session session, Long scid)
			throws Exception {
		Apacheii apacheii = null;
		try {
			List<Apacheii> list = session.createQuery(
					"from Apacheii where scId = ? and fiag='N'").setLong(0, scid).list();
			if (list.size() > 0) {
				apacheii = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return apacheii;
		}
	
	public Apacheii apacheii_saveOrUpdate(Session session, Apacheii apacheii)
			throws Exception {
		Long id = apacheii.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(apacheii);
			} else {
				session.update(apacheii);
			}
			apacheii = (Apacheii) session.get(Apacheii.class, id);
		} catch (Exception e) {
			throw e;
		}
		return apacheii;
	}

	public Phi phi_findByScid(Session session, Long scid) throws Exception {
		Phi phi = null;
		try {
			List<Phi> list = session.createQuery("from Phi where scId = ?")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				phi = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return phi;
	}
	//门诊
	public Phi phi_findByScidmenzhen(Session session, Long scid) throws Exception {
		Phi phi = null;
		try {
			List<Phi> list = session.createQuery("from Phi where scId = ? and fiag='Y'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				phi = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return phi;
	}
	//住院
	public Phi phi_findByScidzhuyuan(Session session, Long scid) throws Exception {
		Phi phi = null;
		try {
			List<Phi> list = session.createQuery("from Phi where scId = ? and fiag='N'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				phi = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return phi;
	}

	public Phi phi_saveOrUpdate(Session session, Phi phi) throws Exception {
		Long id = phi.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(phi);
			} else {
				session.update(phi);
			}
			phi = (Phi) session.get(Phi.class, id);
		} catch (Exception e) {
			throw e;
		}
		return phi;
	}

	public Ts ts_findByScid(Session session, Long scid) throws Exception {
		Ts ts = null;
		try {
			List<Ts> list = session.createQuery("from Ts where scId = ?")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				ts = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return ts;
	}
//门诊
	public Ts ts_findByScidmenzhen(Session session, Long scid) throws Exception {
		Ts ts = null;
		try {
			List<Ts> list = session.createQuery("from Ts where scId = ? and fiag='Y'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				ts = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return ts;
	}
	//住院
	public Ts ts_findByScidzhuyuan(Session session, Long scid) throws Exception {
		Ts ts = null;
		try {
			List<Ts> list = session.createQuery("from Ts where scId = ? and fiag='N'")
					.setLong(0, scid).list();
			if (list.size() > 0) {
				ts = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return ts;
	}
	public Ts ts_saveOrUpdate(Session session, Ts ts) throws Exception {
		Long id = ts.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(ts);
			} else {
				session.update(ts);
			}
			ts = (Ts) session.get(Ts.class, id);
		} catch (Exception e) {
			throw e;
		}
		return ts;
	}

	public OrganFunc organFunc_findByScid(Session session, Long scid)
			throws Exception {
		OrganFunc organFunc = null;
		try {
			List<OrganFunc> list = session.createQuery(
					"from OrganFunc where scId = ?").setLong(0, scid).list();
			if (list.size() > 0) {
				organFunc = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return organFunc;
	}

	public OrganFunc organFunc_findByScidmenzhen(Session session, Long scid)
			throws Exception {
		OrganFunc organFunc = null;
		try {
			List<OrganFunc> list = session.createQuery(
					"from OrganFunc where scId = ? and fiag='Y'").setLong(0, scid).list();
			if (list.size() > 0) {
				organFunc = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return organFunc;
		}
	
	public OrganFunc organFunc_findByScidzhuyuan(Session session, Long scid)
			throws Exception {
		OrganFunc organFunc = null;
		try {
			List<OrganFunc> list = session.createQuery(
					"from OrganFunc where scId = ? and fiag='N'").setLong(0, scid).list();
			if (list.size() > 0) {
				organFunc = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return organFunc;
		}
	public OrganFunc organFunc_saveOrUpdate(Session session, OrganFunc organFunc)
			throws Exception {
		Long id = organFunc.getId();
		try {
			if (id == null || id <= 0) {
				id = (Long) session.save(organFunc);
			} else {
				session.update(organFunc);
			}
			organFunc = (OrganFunc) session.get(OrganFunc.class, id);
		} catch (Exception e) {
			throw e;
		}
		return organFunc;
	}

	// 器官树（住院）
	public String getScoreSetMeal() throws Exception {
		List<ScoreSetMeal> list = new ArrayList<ScoreSetMeal>();
		String presentHistoryTree = "";
		int tier = 1;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from ScoreSetMeal").list();
			presentHistoryTree = transferToTree(list, tier);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return presentHistoryTree;
	}
	
	// 器官树（门诊）
	public String getScoreSetMealmenzhen() throws Exception {
		List<ScoreSetMealmenzhen> list = new ArrayList<ScoreSetMealmenzhen>();
		String presentHistoryTree = "";
		int tier = 1;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from ScoreSetMealmenzhen").list();
			presentHistoryTree = transferToTreemenzhen(list, tier);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return presentHistoryTree;
	}

	private static String transferToTree(List<ScoreSetMeal> list, int tier) {
		String retStr = "";
		Boolean leaf = false;
		StringBuffer sb = new StringBuffer();
		for (ScoreSetMeal t : list) {
			List<ScoreSetMeal> sublist = new ArrayList<ScoreSetMeal>();
			if (t.getTier() == tier) {
				for (ScoreSetMeal _t : list) {
					if (t.getCode().length() < _t.getCode().length()
							&& t.getCode().equals(_t.getCode().substring(0, 2))) {
						sublist.add(_t);
					}
				}
				if (sublist.size() > 0) {
					sb.append(
							"{text:'" + t.getName() + "',leaf:false,"
									+ "children:[").append(
							transferToTree(sublist, tier + 1)).append("]},");
				} else {
					sb
							.append("{text:'"
									+ t.getName()
									+ "',leaf:true,iconCls:'icon-none','subScoreItems':'"
									+ t.getSubScoreItems() + "',id:'"
									+ t.getId() + "'},");
				}
			}
		}
		String str = sb.toString();
		sb.delete(0, sb.length());
		if (str != null && str.length() != 0) {
			sb.append(str.substring(0, str.length() - 1));
			retStr = sb.toString();
		}
		return retStr;
	}
/*
 * menzhen
 */
	private static String transferToTreemenzhen(List<ScoreSetMealmenzhen> list, int tier) {
		String retStr = "";
		Boolean leaf = false;
		StringBuffer sb = new StringBuffer();
		for (ScoreSetMealmenzhen t : list) {
			List<ScoreSetMealmenzhen> sublist = new ArrayList<ScoreSetMealmenzhen>();
			if (t.getTier() == tier) {
				for (ScoreSetMealmenzhen _t : list) {
					if (t.getCode().length() < _t.getCode().length()
							&& t.getCode().equals(_t.getCode().substring(0, 2))) {
						sublist.add(_t);
					}
				}
				if (sublist.size() > 0) {
					sb.append(
							"{text:'" + t.getName() + "',leaf:false,"
									+ "children:[").append(
							transferToTreemenzhen(sublist, tier + 1)).append("]},");
				} else {
					sb
							.append("{text:'"
									+ t.getName()
									+ "',leaf:true,iconCls:'icon-none','subScoreItems':'"
									+ t.getSubScoreItems() + "',id:'"
									+ t.getId() + "'},");
				}
			}
		}
		String str = sb.toString();
		sb.delete(0, sb.length());
		if (str != null && str.length() != 0) {
			sb.append(str.substring(0, str.length() - 1));
			retStr = sb.toString();
		}
		return retStr;
	}
	
	public static void main(String[] args) {
		try {
			JSONObject data = new JSONObject();
			data.put("kid", 15063);
			data.put("ssmId", 1);
			data.put("scId", 8);
			new ScoreCommentImpl().print(null, data,
					"F:/MyWorkspace3/TCMP5/WebRoot/");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private float dl = 72 / 25.4f;// 1mm的Pdf文档长度

	@SuppressWarnings("unchecked")
	public void print(ServletOutputStream os, JSONObject data, String rootPath)
			throws Exception {

		// File file=new File("c:\\test.pdf");
		// if(file.exists()){
		// file.delete();
		// file.createNewFile();
		// }
		// FileOutputStream fos=new FileOutputStream(file);
		String title = "";
		Map datas = new HashMap();
		for (Object key : data.keySet()) {
			datas.put(key.toString(), data.getString(key.toString()));
		}
		// 获取打印数据
		preparPrintData(datas);
		title = (String) datas.get("title");

		BaseFont font = BaseFont.createFont(rootPath
				+ "PUBLIC/print/SIMSUN.TTC,0", BaseFont.IDENTITY_H,
				BaseFont.NOT_EMBEDDED);
		// 初始化docment对象
		Document doc = new Document(PageSize.A4);
		doc.setMargins(20 * dl, 10 * dl, 5 * dl, 0 * dl);
		// 打开输出流并初始化writer对象
		PdfWriter writer = PdfWriter.getInstance(doc, os);
		// 添加页码事件
		PageNumHelper event = new PageNumHelper(font);
		writer.setPageEvent(event);
		// 设置奇偶页页边距镜像
		doc.setMarginMirroring(true);
		// 开始写入
		doc.open();

		PdfPTable table = new PdfPTable(1);
		table.setSplitLate(false);
		table.setSplitRows(true);
		// 设置表格宽度100%
		table.setWidthPercentage(100);
		// 设置表格自动拉伸最后一行填满页面
		table.setExtendLastRow(true);

		PdfPCell cell;
		// 页眉
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setBorderWidth(0);
		cell.setBorderWidthBottom(0.5f);
		cell.addElement(getHeader(title, (String) datas.get("patientName"),
				(String) datas.get("patientNo"), (String) datas
						.get("inHspTimes"), rootPath, font));
		table.addCell(cell);
		// 页脚
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setBorderWidth(0);
		cell.addElement(new Paragraph(" "));
		table.addCell(cell);
		table.setHeaderRows(2);
		table.setFooterRows(1);
		// 内容
		cell = new PdfPCell();
		PdfPTable dateStageTable = new PdfPTable(2);
		dateStageTable.setSplitLate(false);
		dateStageTable.setSplitRows(true);
		// 设置表格宽度100%
		dateStageTable.setWidthPercentage(100);
		dateStageTable.setWidths(new int[] { 1, 1 });
		Font myFont = new Font(font, 12, Font.NORMAL);
		// 评分时间
		PdfPCell c = new PdfPCell();
		Paragraph p = new Paragraph("评分时间：" + datas.get("scoreTime"), myFont);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorder(Rectangle.NO_BORDER);
		dateStageTable.addCell(c);
		// 评分阶段
		c = new PdfPCell();
		p = new Paragraph(" 评分阶段：" + datas.get("scoreStage"), myFont);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorder(Rectangle.NO_BORDER);
		dateStageTable.addCell(c);
		cell.addElement(dateStageTable);
		table.addCell(cell);

		if (datas.get("printPages") != null
				&& !datas.get("printPages").equals("")) {
			String[] printPages = ((String) datas.get("printPages"))
					.split("\\*");
			for (int i = 0; i < printPages.length; i++) {
				cell = new PdfPCell();
				cell.setPadding(0);
				cell.setBorderWidth(0);
				String printPage = printPages[i];
				if (printPage.equals("Child-Pugh")) {

				} else if (printPage.equals("MELD")) {

				} else if (printPage.equals("BCLC")) {

				} else if (printPage.equals("BMI")) {

				} else if (printPage.equals("CLIP")) {

				} else if (printPage.equals("GCS")) {

				} else if (printPage.equals("PLC")) {

				} else if (printPage.equals("TNM")) {

				} else if (printPage.equals("LCTOS")) {

				} else if (printPage.equals("HE")) {

				} else if (printPage.equals("OrganFunc")) {
					addOrganFuncPrintPageToTable(cell, datas, font);
				} else if (printPage.equals("HC")) {

				} else if (printPage.equals("HRS")) {

				} else if (printPage.equals("HPS")) {

				} else if (printPage.equals("Hepatomyocardosis")) {

				} else if (printPage.equals("APACHEII")) {

				} else if (printPage.equals("PHI")) {

				} else if (printPage.equals("TS")) {

				}
				table.addCell(cell);
			}
		}
		/* 签名 */
		cell = new PdfPCell();
		dateStageTable = new PdfPTable(3);
		dateStageTable.setSplitLate(false);
		dateStageTable.setSplitRows(true);
		// 设置表格宽度100%
		dateStageTable.setWidthPercentage(100);
		dateStageTable.setWidths(new int[] { 1, 1, 1 });
		myFont = new Font(font, 12, Font.NORMAL);
		// 医生签字1
		c = new PdfPCell();
		p = new Paragraph("住院医师："
				+ DictionaryUtil.getIndependentDictionaryText("userName", datas
						.get("doc1")
						+ ""), myFont);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorderWidth(0);
		c.setBorderWidthBottom((float) 0.5);
		dateStageTable.addCell(c);
		// 医生签字2
		c = new PdfPCell();
		p = new Paragraph("主治医师："
				+ DictionaryUtil.getIndependentDictionaryText("userName", datas
						.get("doc2")
						+ ""), myFont);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorderWidth(0);
		c.setBorderWidthBottom((float) 0.5);
		dateStageTable.addCell(c);
		// 医生签字3
		c = new PdfPCell();
		p = new Paragraph("副主任医师："
				+ DictionaryUtil.getIndependentDictionaryText("userName", datas
						.get("doc3")
						+ ""), myFont);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorderWidth(0);
		c.setBorderWidthBottom((float) 0.5);
		dateStageTable.addCell(c);

		cell.addElement(dateStageTable);
		table.addCell(cell);
		doc.add(table);
		// 关闭写入
		doc.close();
		// fos.close();
	}

	private void addOrganFuncPrintPageToTable(PdfPCell cell, Map datas,
			BaseFont font) {
		try {
			PdfPTable table = new PdfPTable(1);
			table.setSplitLate(false);
			table.setSplitRows(true);
			// 设置表格宽度100%
			table.setWidthPercentage(100);
			Font myFontItem = new Font(font, 12, Font.BOLD);
			Font myFont = new Font(font, 12, Font.NORMAL);

			PdfPCell c = new PdfPCell();
			Paragraph p = new Paragraph("肝功能评估", myFontItem);
			c.setMinimumHeight(24);
			c.addElement(p);
			c.setUseAscender(true);
			c.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(c);

			c = new PdfPCell();
			c.setPadding(0);
			PdfPTable subTable = new PdfPTable(4);
			subTable.setSplitLate(false);
			subTable.setSplitRows(true);
			// 设置表格宽度100%
			subTable.setWidthPercentage(100);
			subTable.setWidths(new int[] { 35, 15, 30, 20 });
			PdfPCell subCell = new PdfPCell();
			subCell.setColspan(4);
			p = new Paragraph("Child-Pugh评分", myFontItem);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("肝性脑病(期)：", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String hepaticEp = (String) datas.get("hepaticEp");
			if (hepaticEp.equals(1)) {
				hepaticEp = "无";
			}
			if (hepaticEp.equals(0)) {
				hepaticEp = "";
			}
			if (hepaticEp.equals("2-")) {
				hepaticEp = "1";
			}
			if (hepaticEp.equals("2+")) {
				hepaticEp = "2";
			}
			p = new Paragraph(hepaticEp, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("腹水：", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String ascites = (String) datas.get("ascites");
			if (ascites.equals("1")) {
				ascites = "无";
			} else if (ascites.equals("2")) {
				ascites = "轻度";
			} else if (ascites.equals("3")) {
				ascites = "中重度";
			} else {
				ascites = "";
			}
			p = new Paragraph(ascites, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("胆红素(μmol/L)：", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String cpBilirubin = (String) datas.get("cpBilirubin");
			if (cpBilirubin.equals("1")) {
				cpBilirubin = "<34.2";
			} else if (cpBilirubin.equals("2")) {
				cpBilirubin = "34.2-51.3";
			} else if (cpBilirubin.equals("3")) {
				cpBilirubin = ">51.3";
			} else {
				cpBilirubin = "";
			}
			p = new Paragraph(cpBilirubin, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("白蛋白(g/L)：", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String albumin = (String) datas.get("albumin");
			if (albumin.equals("1")) {
				albumin = ">35";
			} else if (albumin.equals("2")) {
				albumin = "28-35";
			} else if (albumin.equals("3")) {
				albumin = "<28";
			} else {
				albumin = "";
			}
			p = new Paragraph(albumin, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("凝血酶原时间较正常延长(秒)：", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String prothrombinTime = (String) datas.get("prothrombinTime");
			if (prothrombinTime.equals("1")) {
				prothrombinTime = "<4";
			} else if (prothrombinTime.equals("2")) {
				prothrombinTime = "4-6";
			} else if (prothrombinTime.equals("3")) {
				prothrombinTime = ">6";
			} else {
				prothrombinTime = "";
			}
			p = new Paragraph(prothrombinTime, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("Child-Pugh总积分：", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph((String) datas.get("totalScore"), myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("Child-Pugh分级：", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph((String) datas.get("scoreGrade"), myFont);
			subCell.addElement(p);
			subCell.setColspan(3);
			subTable.addCell(subCell);
			// 补白
			// subTable.addCell("");
			// subTable.addCell("");
			c.addElement(subTable);
			table.addCell(c);
			subCell = new PdfPCell();
			subCell.setColspan(4);
			p = new Paragraph("MELD评分", myFontItem);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("肌酐(μmol/L)：", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String creatinine = (String) datas.get("creatinine");
			if (!creatinine.equals("")) {
				creatinine = creatinine + "";
			}
			p = new Paragraph(creatinine, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("胆红素(μmol/L)：", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String meldBilirubin = (String) datas.get("meldBilirubin");
			if (!meldBilirubin.equals("")) {
				meldBilirubin = meldBilirubin + "";
			}
			p = new Paragraph(meldBilirubin, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("INR：", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph((String) datas.get("meldInr"), myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("病因：", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String etiology = (String) datas.get("etiology");
			if (etiology.equals("0-")) {
				hepaticEp = "胆汁淤积性";
			} else if (etiology.equals("0+")) {
				hepaticEp = "酒精性肝硬化";
			} else if (etiology.equals("1")) {
				hepaticEp = "其它";
			} else {
				hepaticEp = "";
			}
			p = new Paragraph(hepaticEp, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("结果：", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph((String) datas.get("meldResult"), myFont);
			subCell.addElement(p);
			subCell.setColspan(3);
			subTable.addCell(subCell);
			// 补白
			// subTable.addCell("");
			// subTable.addCell("");

			c = new PdfPCell();
			p = new Paragraph("心功能评估", myFontItem);
			c.setMinimumHeight(24);
			c.addElement(p);
			c.setUseAscender(true);
			c.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(c);

			c = new PdfPCell();
			c.setPadding(0);
			subTable = new PdfPTable(2);
			subTable.setSplitLate(false);
			subTable.setSplitRows(true);
			// 设置表格宽度100%
			subTable.setWidthPercentage(100);
			subTable.setWidths(new int[] { 30, 70 });
			subCell = new PdfPCell();
			p = new Paragraph("心电图", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String xindian = (String) datas.get("xindian0");
			if (xindian.equals("")) {
				xindian = (String) datas.get("xindian");
			} else {
				xindian = xindian.substring(1, xindian.length() - 1);
			}
			p = new Paragraph(xindian, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("24小时动态心电图", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String dongXindian = (String) datas.get("dongXindian0");
			if (dongXindian.equals("")) {
				dongXindian = (String) datas.get("dongXindian");
			} else {
				dongXindian = dongXindian
						.substring(1, dongXindian.length() - 1);
			}
			p = new Paragraph(dongXindian, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("超声心动检查", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String csXindong = (String) datas.get("csXindong0");
			if (csXindong.equals("")) {
				csXindong = (String) datas.get("csXindong");
			} else {
				csXindong = csXindong.substring(1, csXindong.length() - 1);
			}
			p = new Paragraph(csXindong, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			c.addElement(subTable);
			table.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("肺功能评估", myFontItem);
			c.setMinimumHeight(24);
			c.addElement(p);
			c.setUseAscender(true);
			c.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(c);

			c = new PdfPCell();
			c.setPadding(0);
			subTable = new PdfPTable(2);
			subTable.setSplitLate(false);
			subTable.setSplitRows(true);
			// 设置表格宽度100%
			subTable.setWidthPercentage(100);
			subTable.setWidths(new int[] { 30, 70 });
			subCell = new PdfPCell();
			p = new Paragraph("胸部X线", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String chestX = (String) datas.get("chestX0");
			if (chestX.equals("")) {
				chestX = (String) datas.get("chestX");
			} else {
				chestX = chestX.substring(1, chestX.length() - 1);
			}
			p = new Paragraph(chestX, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("胸部CT", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String chestCT = (String) datas.get("chestCT0");
			if (chestCT.equals("")) {
				chestCT = (String) datas.get("chestCT");
			} else {
				chestCT = chestCT.substring(1, chestCT.length() - 1);
			}
			p = new Paragraph(chestCT, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("肺功能检查", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String chestCheck = (String) datas.get("chestCheck0");
			if (chestCheck.equals("")) {
				chestCheck = (String) datas.get("chestCheck");
			} else {
				chestCheck = chestCheck.substring(1, chestCheck.length() - 1);
			}
			p = new Paragraph(chestCheck, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("血氧分压(mmHg)", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String xyFengya = (String) datas.get("xyFengya0");
			if (xyFengya.equals("")) {
				xyFengya = (String) datas.get("xyFengya");
				if (!xyFengya.equals("")) {
					xyFengya = xyFengya + "";
				}
			} else {
				xyFengya = xyFengya.substring(1, xyFengya.length() - 1);
			}
			p = new Paragraph(xyFengya, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("血二氧化碳分压(mmHg)", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String xeFengya = (String) datas.get("xeFengya0");
			if (xeFengya.equals("")) {
				xeFengya = (String) datas.get("xeFengya");
				if (!xeFengya.equals("")) {
					xeFengya = xeFengya + "";
				}
			} else {
				xeFengya = xeFengya.substring(1, xeFengya.length() - 1);
			}
			p = new Paragraph(xeFengya, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("血氧饱和度(%)", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String xyBaohe = (String) datas.get("xyBaohe0");
			if (xyBaohe.equals("")) {
				xyBaohe = (String) datas.get("xyBaohe");
				if (!xyBaohe.equals("")) {
					xyBaohe = xyBaohe + "";
				}
			} else {
				xyBaohe = xyBaohe.substring(1, xyBaohe.length() - 1);
			}
			p = new Paragraph(xyBaohe, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			c.addElement(subTable);
			table.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("肾功能评估", myFontItem);
			c.setMinimumHeight(24);
			c.addElement(p);
			c.setUseAscender(true);
			c.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(c);

			c = new PdfPCell();
			c.setPadding(0);
			subTable = new PdfPTable(2);
			subTable.setSplitLate(false);
			subTable.setSplitRows(true);
			// 设置表格宽度100%
			subTable.setWidthPercentage(100);
			subTable.setWidths(new int[] { 30, 70 });
			subCell = new PdfPCell();
			p = new Paragraph("尿量(ml/日)", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String niaovolum = (String) datas.get("niaovolum0");
			if (niaovolum.equals("")) {
				niaovolum = (String) datas.get("niaovolum");
				if (!niaovolum.equals("")) {
					niaovolum = xyBaohe + "";
				}
			} else {
				niaovolum = niaovolum.substring(1, niaovolum.length() - 1);
			}
			p = new Paragraph(niaovolum, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("24小时尿蛋白", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String ndanbai = (String) datas.get("ndanbai0");
			if (ndanbai.equals("")) {
				ndanbai = (String) datas.get("ndanbai");
				String ndanbaiYDesc = (String) datas.get("ndanbaiYDesc");
				if (ndanbai.equals("阳性") && !ndanbaiYDesc.equals("")) {
					ndanbai = ndanbai + " " + ndanbaiYDesc + "g/24h";
				}
			} else {
				ndanbai = ndanbai.substring(1, ndanbai.length() - 1);
			}
			p = new Paragraph(ndanbai, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("血尿素氮(mmol/L)", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String xueniaoN = (String) datas.get("xueniaoN0");
			if (xueniaoN.equals("")) {
				xueniaoN = (String) datas.get("xueniaoN");
				if (!xueniaoN.equals("")) {
					xueniaoN = xueniaoN + "";
				}
			} else {
				xueniaoN = ndanbai.substring(1, xueniaoN.length() - 1);
			}
			p = new Paragraph(xueniaoN, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("血肌酐(μmol/L)", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String xuejigan = (String) datas.get("xuejigan0");
			if (xuejigan.equals("")) {
				xuejigan = (String) datas.get("xuejigan");
				if (!xuejigan.equals("")) {
					xuejigan = xuejigan + "";
				}
			} else {
				xuejigan = xuejigan.substring(1, xuejigan.length() - 1);
			}
			p = new Paragraph(xuejigan, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("内生肌酐清除率(Ccr)(ml/min)", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String ccr = (String) datas.get("ccr0");
			if (ccr.equals("")) {
				ccr = (String) datas.get("ccr");
				if (!ccr.equals("")) {
					ccr = ccr + "";
				}
			} else {
				ccr = ccr.substring(1, ccr.length() - 1);
			}
			p = new Paragraph(ccr, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("肾脏超声", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String szChaosheng = (String) datas.get("szChaosheng0");
			if (szChaosheng.equals("")) {
				szChaosheng = (String) datas.get("szChaosheng");
			} else {
				szChaosheng = szChaosheng
						.substring(1, szChaosheng.length() - 1);
			}
			p = new Paragraph(szChaosheng, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			c.addElement(subTable);
			table.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("凝血功能评估", myFontItem);
			c.setMinimumHeight(24);
			c.addElement(p);
			c.setUseAscender(true);
			c.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(c);

			c = new PdfPCell();
			c.setPadding(0);
			subTable = new PdfPTable(2);
			subTable.setSplitLate(false);
			subTable.setSplitRows(true);
			// 设置表格宽度100%
			subTable.setWidthPercentage(100);
			subTable.setWidths(new int[] { 30, 70 });
			subCell = new PdfPCell();
			p = new Paragraph("PLT(×10^9/L)", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String ningxueFunc = (String) datas.get("ningxueFunc0");
			if (ningxueFunc.equals("")) {
				ningxueFunc = (String) datas.get("ningxueFunc");
				if (!ningxueFunc.equals("")) {
					ningxueFunc = ningxueFunc + "";
				}
			} else {
				ningxueFunc = ningxueFunc
						.substring(1, ningxueFunc.length() - 1);
			}
			p = new Paragraph(ningxueFunc, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("PTA(%)", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String pta = (String) datas.get("pta0");
			if (pta.equals("")) {
				pta = (String) datas.get("pta");
				if (!pta.equals("")) {
					pta = pta + "";
				}
			} else {
				pta = pta.substring(1, pta.length() - 1);
			}
			p = new Paragraph(pta, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("INR", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String inr = (String) datas.get("inr0");
			if (inr.equals("")) {
				inr = (String) datas.get("inr");
			} else {
				inr = inr.substring(1, inr.length() - 1);
			}
			p = new Paragraph(inr, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("DIC", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String dic = (String) datas.get("dic0");
			if (dic.equals("")) {
				dic = (String) datas.get("dic");
			} else {
				dic = dic.substring(1, dic.length() - 1);
			}
			p = new Paragraph(dic, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			c.addElement(subTable);
			table.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("胃肠道功能评价", myFontItem);
			c.setMinimumHeight(24);
			c.addElement(p);
			c.setUseAscender(true);
			c.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(c);

			c = new PdfPCell();
			c.setPadding(0);
			subTable = new PdfPTable(2);
			subTable.setSplitLate(false);
			subTable.setSplitRows(true);
			// 设置表格宽度100%
			subTable.setWidthPercentage(100);
			subTable.setWidths(new int[] { 30, 70 });
			subCell = new PdfPCell();
			p = new Paragraph("胃镜", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String weijing = (String) datas.get("weijing0");
			if (weijing.equals("")) {
				weijing = (String) datas.get("weijing");
			} else {
				weijing = weijing.substring(1, weijing.length() - 1);
			}
			p = new Paragraph(weijing, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("胃电图", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String weidiantu = (String) datas.get("weidiantu0");
			if (weidiantu.equals("")) {
				weidiantu = (String) datas.get("weidiantu");
			} else {
				weidiantu = weidiantu.substring(1, weidiantu.length() - 1);
			}
			p = new Paragraph(weidiantu, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("血乳酸(mmol/L)", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String xuerusuan = (String) datas.get("xuerusuan0");
			if (xuerusuan.equals("")) {
				xuerusuan = (String) datas.get("xuerusuan");
				if (!xuerusuan.equals("")) {
					xuerusuan = xuerusuan + "";
				}
			} else {
				xuerusuan = weidiantu.substring(1, xuerusuan.length() - 1);
			}
			p = new Paragraph(xuerusuan, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			c.addElement(subTable);
			table.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("神经系统", myFontItem);
			c.setMinimumHeight(24);
			c.addElement(p);
			c.setUseAscender(true);
			c.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(c);

			c = new PdfPCell();
			c.setPadding(0);
			subTable = new PdfPTable(2);
			subTable.setSplitLate(false);
			subTable.setSplitRows(true);
			// 设置表格宽度100%
			subTable.setWidthPercentage(100);
			subTable.setWidths(new int[] { 30, 70 });
			subCell = new PdfPCell();
			p = new Paragraph("肝性脊髓病", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String gxjisui = (String) datas.get("gxjisui0");
			if (gxjisui.equals("")) {
				gxjisui = (String) datas.get("gxjisui");
			} else {
				gxjisui = gxjisui.substring(1, gxjisui.length() - 1);
			}
			p = new Paragraph(gxjisui, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("头颅CT", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String headCT = (String) datas.get("headCT0");
			if (headCT.equals("")) {
				headCT = (String) datas.get("headCT");
			} else {
				headCT = headCT.substring(1, headCT.length() - 1);
			}
			p = new Paragraph(headCT, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			c.addElement(subTable);
			table.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("营养状态评估", myFontItem);
			c.setMinimumHeight(24);
			c.addElement(p);
			c.setUseAscender(true);
			c.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(c);

			c = new PdfPCell();
			c.setPadding(0);
			subTable = new PdfPTable(2);
			subTable.setSplitLate(false);
			subTable.setSplitRows(true);
			// 设置表格宽度100%
			subTable.setWidthPercentage(100);
			subTable.setWidths(new int[] { 30, 70 });
			subCell = new PdfPCell();
			p = new Paragraph("BMI", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String yingyangBmi = (String) datas.get("yingyangBmi0");
			if (yingyangBmi.equals("")) {
				yingyangBmi = (String) datas.get("yingyangBmi");
			} else {
				yingyangBmi = yingyangBmi
						.substring(1, yingyangBmi.length() - 1);
			}
			p = new Paragraph(yingyangBmi, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("营养风险筛查", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String yingyangNrs = (String) datas.get("yingyangNrs0");
			if (yingyangNrs.equals("")) {
				yingyangNrs = (String) datas.get("yingyangNrs");
			} else {
				yingyangNrs = yingyangNrs
						.substring(1, yingyangNrs.length() - 1);
			}
			p = new Paragraph(yingyangNrs, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("血清白蛋白(g/L)", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String xqbdb = (String) datas.get("xqbdb0");
			if (xqbdb.equals("")) {
				xqbdb = (String) datas.get("xqbdb");
				if (!xqbdb.equals("")) {
					xqbdb = xqbdb + "";
				}
			} else {
				xqbdb = xqbdb.substring(1, xqbdb.length() - 1);
			}
			p = new Paragraph(xqbdb, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("血清前白蛋白(mg/L)", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String xqqbdb = (String) datas.get("xqqbdb0");
			if (xqqbdb.equals("")) {
				xqqbdb = (String) datas.get("xqqbdb");
				if (!xqqbdb.equals("")) {
					xqqbdb = xqqbdb + "";
				}
			} else {
				xqqbdb = xqqbdb.substring(1, xqqbdb.length() - 1);
			}
			p = new Paragraph(xqqbdb, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			p = new Paragraph("淋巴细胞计数(×10^9/L)", myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			subCell = new PdfPCell();
			String lbxbjs = (String) datas.get("lbxbjs0");
			if (lbxbjs.equals("")) {
				lbxbjs = (String) datas.get("lbxbjs");
				if (!lbxbjs.equals("")) {
					lbxbjs = lbxbjs + "";
				}
			} else {
				lbxbjs = lbxbjs.substring(1, lbxbjs.length() - 1);
			}
			p = new Paragraph(lbxbjs, myFont);
			subCell.addElement(p);
			subTable.addCell(subCell);
			c.addElement(subTable);
			table.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("其他器官评价结果", myFontItem);
			c.setMinimumHeight(24);
			c.addElement(p);
			c.setUseAscender(true);
			c.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(c);
			c = new PdfPCell();
			p = new Paragraph((String) datas.get("otherOrgan"), myFont);
			c.addElement(p);
			table.addCell(c);

			cell.addElement(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void preparPrintData(Map map) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			InHspCaseMaster cm = (InHspCaseMaster) session.get(
					InHspCaseMaster.class, Long.parseLong((String) map
							.get("kid")));
			if (cm != null) {
				map.put("inHspTimes", cm.getInHspTimes() + "");
				map.put("inHspDate", cm.getInHspDate()+"");
				Patient p = (Patient) session.get(Patient.class, cm
						.getPatientId());
				if (p != null) {
					map.put("patientName", p.getPatientName());
					map.put("patientNo", p.getPatientNo());
				}
			}

			ScoreComment scoreComment = (ScoreComment) session.get(
					ScoreComment.class, Long
							.parseLong((String) map.get("scId")));
			if (scoreComment != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String scoreTime = sdf.format(scoreComment.getScoreTime());
				map.put("scoreTime", scoreTime);
				Long scoreStageFlag = scoreComment.getScoreStage();
				String scoreStage = "";
				if (scoreStageFlag == 1) {
					scoreStage = "入院";
				} else if (scoreStageFlag == 2) {
					scoreStage = "术前";
				} else if (scoreStageFlag == 3) {
					scoreStage = "术后";
				} else if (scoreStageFlag == 4) {
					scoreStage = "住院期间";
				} else if (scoreStageFlag == 5) {
					scoreStage = "出院";
				}
				map.put("scoreStage", scoreStage);
				map.put("doc1", scoreComment.getDoc1());
				map.put("doc2", scoreComment.getDoc2());
				map.put("doc3", scoreComment.getDoc3());
			}

			ScoreSetMeal scoreSetMeal = (ScoreSetMeal) session.get(
					ScoreSetMeal.class, Long.parseLong((String) map
							.get("ssmId")));
			if (scoreSetMeal != null) {
				map.put("title", scoreSetMeal.getName());
				map.put("printPages", scoreSetMeal.getSubScoreItems());
				String[] printPages = scoreSetMeal.getSubScoreItems().split(
						"\\*");
				for (int i = 0; i < printPages.length; i++) {
					getPringPageData(printPages[i], map, session);
				}
			}

			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	private void getPringPageData(String printPage, Map map, Session session)
			throws Exception {
		Long scid = Long.parseLong((String) map.get("scId"));
		if (printPage.equals("Child-Pugh")) {
			ChildPugh cp = childPugh_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(cp));
		} else if (printPage.equals("MELD")) {
			Meld meld = meld_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(meld));
		} else if (printPage.equals("BCLC")) {
			Bclc bclc = bclc_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(bclc));
		} else if (printPage.equals("BMI")) {
			Bmi bmi = bmi_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(bmi));
		} else if (printPage.equals("CLIP")) {
			Clip clip = clip_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(clip));
		} else if (printPage.equals("GCS")) {
			Gcs gcs = gcs_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(gcs));
		} else if (printPage.equals("PLC")) {
			Plc plc = plc_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(plc));
		} else if (printPage.equals("TNM")) {
			Tnm tnm = tnm_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(tnm));
		} else if (printPage.equals("LCTOS")) {
			Lctos lctos = lctos_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(lctos));
		} else if (printPage.equals("HE")) {
			He he = he_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(he));
		} else if (printPage.equals("OrganFunc")) {
			OrganFunc organFunc = organFunc_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(organFunc));
		} else if (printPage.equals("HC")) {
			Hc hc = hc_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(hc));
		} else if (printPage.equals("HRS")) {
			Hrs hrs = hrs_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(hrs));
		} else if (printPage.equals("HPS")) {
			Hps hps = hps_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(hps));
		} else if (printPage.equals("Hepatomyocardosis")) {
			Hepatomyocardosis hepatomyocardosis = hepatomyocardosis_findByScid(
					session, scid);
			map.putAll(JSONObject.fromObject(hepatomyocardosis));
		} else if (printPage.equals("APACHEII")) {
			Apacheii apacheii = apacheii_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(apacheii));
		} else if (printPage.equals("PHI")) {
			Phi phi = phi_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(phi));
		} else if (printPage.equals("TS")) {
			Ts ts = ts_findByScid(session, scid);
			map.putAll(JSONObject.fromObject(ts));
		}
	}

	private PdfPTable getHeader(String title, String patientName,
			String patientNo, String inHspTimes, String rootPath, BaseFont font)
			throws DocumentException, MalformedURLException, IOException,
			SQLException {
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 8, 23, 38, 10, 21 });
		// 图片
		PdfPCell cell = new PdfPCell();
		cell.setPadding(1);
		cell.setPaddingLeft(2);
		cell.setPaddingTop(5);
		cell.setRowspan(2);
		Image logo = Image.getInstance(rootPath + "PUBLIC/print/youanLogo.jpg");
		cell.addElement(logo);
		table.addCell(cell);
		// 中英文医院名称
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setColspan(4);
		cell.setPaddingTop(12);
		Font myFont = new Font(font, 16, Font.BOLD);
		Paragraph p = new Paragraph("首都医科大学附属北京佑安医院", myFont);
		cell.addElement(p);
		myFont = new Font(font, 9, Font.BOLD);
		p = new Paragraph("Beijing YouAn Hospital,Capital Medical University",
				myFont);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 标题
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setPaddingTop(5);
		cell.setPaddingBottom(5);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		myFont = new Font(font, 16, Font.BOLD);
		p = new Paragraph(title, myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 姓名
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		myFont = new Font(font, 9, Font.NORMAL);
		p = new Paragraph("姓  名：" + patientName, myFont);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 住院次数
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setPaddingBottom(3);
		cell.setPaddingTop(3);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		p = new Paragraph("第 " + inHspTimes + " 次住院", myFont);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 病案号
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		p = new Paragraph("病案号：" + patientNo, myFont);
		cell.addElement(p);
		table.addCell(cell);
		for (PdfPRow row : table.getRows()) {
			for (PdfPCell c : row.getCells()) {
				if (c != null)
					c.setBorder(Rectangle.NO_BORDER);
			}
		}
		return table;
	}

	class PageNumHelper extends PdfPageEventHelper {
		private BaseFont font;

		public PageNumHelper(BaseFont font) {
			this.font = font;
		}

		@Override
		public void onStartPage(PdfWriter writer, Document document) {
			PdfContentByte cb = writer.getDirectContent();
			cb.saveState();
			cb.setFontAndSize(font, 9);
			cb.beginText();
			cb
					.showTextAligned(Element.ALIGN_CENTER, "第 "
							+ writer.getPageNumber() + " 页", document
							.getPageSize().getWidth() / 2, document
							.getPageSize().getHeight() - 95, 0);
			cb.endText();
			cb.restoreState();
			cb = writer.getDirectContent();
			cb.setLineWidth(1);
			if (document.getPageNumber() % 2 == 0) {
				cb.moveTo(document.getPageSize().getWidth() - 20 * dl, 5 * dl);
				cb.lineTo(document.getPageSize().getWidth() - 20 * dl, document
						.getPageSize().getHeight()
						- 10 * dl);
			} else {
				cb.moveTo(20 * dl, 5 * dl);
				cb
						.lineTo(20 * dl, document.getPageSize().getHeight()
								- 10 * dl);
			}
			cb.stroke();
		}
	}
}
