package com.juncsoft.KLJY.InHospitalCase.GradingDiag.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

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
import com.juncsoft.KLJY.InHospitalCase.GradingDiag.dao.GradingDiagDao;
import com.juncsoft.KLJY.InHospitalCase.GradingDiag.entity.GradingDiag;
import com.juncsoft.KLJY.Public.entity.PageDict;
import com.juncsoft.KLJY.biomedical.contorl.impl.MemberSearchTotalImpl;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyCase;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class GradingDiagDaoImpl implements GradingDiagDao {
	private float dl = 72 / 25.4f;// 1mm的Pdf文档长度
	private SimpleDateFormat dateFormat;
	public void delete(GradingDiag gd) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(gd);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	public GradingDiag findByCaseId(Long id) throws Exception {
		GradingDiag gd = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(GradingDiag.class).add(
					Restrictions.eq("cid", id)).list();
			if (list.size() > 0)
				gd = (GradingDiag) list.get(0);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return gd;
	}
	/*
	 * 十二级分级(住院)
	 *
	 */
	public GradingDiag findByCaseId1(Long id) throws Exception{
		GradingDiag gd = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql="from GradingDiag g where g.fiag='N' and g.id="+id+"";
			Query query=session.createQuery(hql);
			List list=query.list();
			if (list.size() > 0)
				gd = (GradingDiag) list.get(0);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return gd;
	}
	/*
	 * 十二级分级(门诊)
	 *
	 */
	public GradingDiag findByCaseIdmenzhen(Long id) throws Exception{
		GradingDiag gd = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql="from GradingDiag g where g.fiag='Y' and g.id="+id+"";
			Query query=session.createQuery(hql);
			List list=query.list();
			if (list.size() > 0)
				gd = (GradingDiag) list.get(0);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return gd;
	}
	
	
	/**
	 * 十二级分级(门诊、病房) 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	 
	public JSONArray findAllGrading(String  account ) throws Exception{
		Session session = null;
		Transaction tran = null;
		List <Object[]> list = null;
		JSONArray array = new JSONArray();
		/*account="卓玛";*/
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			 /*  String sql = " select * from   GradingDiag gd where cid in ( "
					+ "select omc.id from  OutOrMergencyCase omc , MemberInfo mi where omc.patientId=mi.patient and mi.account = '"
					+ account
					+ "' union "
					+ "select icm.id from  InHsp_CaseMaster icm ,MemberInfo mi1 where icm.patientId=mi1.patient and mi1.account = '"
					+ account + "') order by date DESC  ";*/
			

			  String sql = " select gd.id,gd.cid,gd.date,gd.diagnose from   t_InHsp_Liver_GradingDiag gd where cid in ( "
								+ "select omc.id from  t_OutOrMergencyCase omc , MemberInfo mi where omc.patientId=mi.patient and mi.account = '"
								+ account
								+ "' union "
								+ "select icm.id from  t_InHsp_CaseMaster icm ,MemberInfo mi1 where icm.patientId=mi1.patient and mi1.account = '"
								+ account + "') order by date DESC  ";  
			   
			   
			 list=   session.createSQLQuery(sql).list();
			  
			 DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			 
			 for (Object[] o : list){
				 String  id=o[0].toString();
				 String cid= o[1].toString();
					JSONObject node = new JSONObject();
					node.put("id", id+"_"+cid);
					Date date = (Date)o[2];
					String diagnose=(String)o[3];
					 if (date != null){
							if(!"".equals(diagnose) && diagnose != null){
								node.put("text", f.format(date)+"--"+diagnose );
							}else{
								node.put("text", f.format(date)+"--"+"门诊");
							}
						}else{
							node.put("text", "诊断时间未定义--门诊");
						} 
					
					 node.put("leaf", true);
						node.put("iconCls","none");
						node.put("cls","node");
						array.add(node);
					
					
				}
			 /*for(int i =0;i<list.size();i++){
				 GradingDiag obj = (GradingDiag)list.get(i);
					JSONObject node = new JSONObject();
					node.put("id", obj.getId()+"_"+obj.getCid());
					 if (obj.getDate() != null){
						//liugang 2011-09-14 update
						if(!"".equals(obj.getDiagnose()) && obj.getDiagnose() != null){
							node.put("text", f.format(obj.getDate())+"--"+obj.getDiagnose());
						}else{
							node.put("text", f.format(obj.getDate())+"--"+"门诊");
						}
						//liugang 2011-09-14 update
					}else{
						node.put("text", "诊断时间未定义");
					} 
					node.put("leaf", true);
					node.put("iconCls","none");
					node.put("cls","node");
					array.add(node);
				}
			 */
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return array;
	}
	/**
	public Long saveOrUpdate(GradingDiag gd) throws Exception {
		Long id = gd.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id != null && id > 0)
				session.update(gd);
			else
				id = (Long) session.save(gd);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}
**/
public Long saveOrUpdate(GradingDiag gd) throws Exception {
		Long id = gd.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id != null && id > 0){
				session.update(gd);
			}
			else{
				id = (Long) session.save(gd);
			}
			if(gd != null){
				long cid = gd.getCid();
				if("N".equals(gd.getFiag().trim())){
					InHspCaseMaster casem = (InHspCaseMaster)session.get(InHspCaseMaster.class, cid);
					if(casem != null){
						Patient pat = (Patient)session.get(Patient.class, casem.getPatientId());
						pat.setDiagGrounp(gd.getDiagGrounp());
						session.update(pat);
					}
				}else{
					OutOrMergencyCase outC = (OutOrMergencyCase)session.get(OutOrMergencyCase.class, cid);
					if(outC != null){
						Patient pat = (Patient)session.get(Patient.class, Long.parseLong(outC.getPatientId()));
						pat.setDiagGrounp(gd.getDiagGrounp());
						session.update(pat);
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}
	@SuppressWarnings("unchecked")
	public JSONObject getMenzhenDiag(int start, int limit,GradingDiag tg) throws Exception {
		JSONObject json = new JSONObject();
		List<Map> list = new ArrayList();
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		Session session = null;	
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			conn = DatabaseUtil.getConnection();
			session = DatabaseUtil.getSession();
			String sql = "select tp.patientName,tm.* " +
            " from OutOrMergencyCase tm,patient tp,GradingDiag tg " +
            " where tm.patientId=tp.id  and tg.cid=tm.id and tg.diag1 like "+tg.getDiag1()+" and" +
            " tg.diag2 like "+tg.getDiag2()+" and tg.diag3 like "+tg.getDiag3()+" and tg.diag4 like "+tg.getDiag4()+" " +
            " and tg.diag5 like "+tg.getDiag5()+" and tg.diag6 like "+tg.getDiag6()+" and tg.diag7 like "+tg.getDiag7()+" " +
            " and tg.diag8 like "+tg.getDiag8()+" and tg.diag9 like "+tg.getDiag9()+" and tg.diag10 like "+tg.getDiag10()+" "+
            " and tg.diag11 like "+tg.getDiag11()+" and tg.diag12 like "+tg.getDiag12()+" ";
				List resultCount = session.createQuery(sql).list();
				List result = session.createQuery(sql).setFirstResult(start)
						.setMaxResults(limit).list();	
				if (result != null && result.size() > 0) {
					for (int i = 0, size = result.size(); i < size; i++) {
						Map map = new HashMap();
						Object[] objs = (Object[]) result.get(i);
						map.put("patientaName", objs[0]);
						list.add(map);
					}
					
				}
				json.put("root", list);
				if (resultCount != null && resultCount.size() > 0) {
					json.put("total", resultCount.size());
				} else {
					json.put("total", 0);
				}
				tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("查询出错", e);
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
			DatabaseUtil.closeResource(session);
		}
		return json;
	}
	public void print(OutputStream os, JSONObject data, String rootPath)
			throws Exception {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Map<String, String> datas = new HashMap<String, String>();
		for (Object key : data.keySet()) {
			datas.put(key.toString(), data.getString(key.toString()));
		}
		preparPrintData(datas);
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
		cell.addElement(getHeader(datas.get("patientName"), datas
				.get("patientNo"), datas.get("inHspTimes"), rootPath, font));
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
		cell.setPadding(0);
		cell.setBorderWidth(0);

		PdfPTable ctx = new PdfPTable(3);
		ctx.setSplitLate(false);
		ctx.setSplitRows(true);
		// 设置表格宽度100%
		ctx.setWidthPercentage(100);
		ctx.setWidths(new int[] { 14, 40, 103 });
		
		
		PdfPTable zsjTable = new PdfPTable(2);
		zsjTable.setSplitLate(false);
		zsjTable.setSplitRows(true);
		// 设置表格宽度100%
		zsjTable.setWidthPercentage(100);
		zsjTable.setWidths(new int[] { 1, 1});
		Font myFont = new Font(font, 12, Font.BOLD);
		
		// 诊断时间
		PdfPCell c = new PdfPCell();
		Paragraph p = new Paragraph(" 诊断时间：" + datas.get("date"), myFont);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorder(Rectangle.NO_BORDER);
		zsjTable.addCell(c);
		

		//诊断阶段
		c = new PdfPCell();
		p = new Paragraph(" 诊断阶段：住院(" + datas.get("inHspDate")+")" + datas.get("diagnose"), myFont);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorder(Rectangle.NO_BORDER);
		zsjTable.addCell(c);
		
		
		c = new PdfPCell();
		c.setColspan(3);
		c.addElement(zsjTable);
		ctx.addCell(c);
		// 一级
		addDiagToTable(ctx, datas.get("diag1"), "一级", "肝脏疾病分类", font);
		// 二级
		addDiagToTable(ctx, datas.get("diag2"), "二级", "起病时间状态", font);
		// 三级
		addDiagToTable(ctx, datas.get("diag3"), "三级", "抗原抗体", font);
		// 四级
		addDiagToTable(ctx, datas.get("diag4"), "四级", "基因型", font);
		// 五级
		addDiagToTable(ctx, datas.get("diag5"), "五级", "肝功能分期、分级", font);
		// 六级
		addDiagToTable(ctx, datas.get("diag6"), "六级", "肝纤维化、肝硬化分型", font);
		// 七级
		addDiagToTable(ctx, datas.get("diag7"), "七级", "肝病并发症", font);
		// 八级
		addDiagToTable(ctx, datas.get("diag8"), "八级", "营养状态", font);
		// 九级
		addDiagToTable(ctx, datas.get("diag9"), "九级", "感染情况", font);
		// 十级
		addDiagToTable(ctx, datas.get("diag10"), "十级", "多脏器功能衰竭", font);
		// 十一级
		addDiagToTable(ctx, datas.get("diag11"), "十一级", "肝癌情况", font);
		// 十二级
		addDiagToTable(ctx, datas.get("diag12"), "十二级", "伴随疾病", font);
		// 签字表格
		PdfPTable signTable = new PdfPTable(3);
		signTable.setSplitLate(false);
		signTable.setSplitRows(true);
		// 设置表格宽度100%
		signTable.setWidthPercentage(100);
		signTable.setWidths(new int[] { 1, 1, 1 });
		myFont = new Font(font, 12, Font.NORMAL);
		// 签字1
		c = new PdfPCell();
		p = new Paragraph("住院医师：" + datas.get("doc1"), myFont);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorder(Rectangle.NO_BORDER);
		signTable.addCell(c);
		// 签字2
		c = new PdfPCell();
		p = new Paragraph("主治医师：" + datas.get("doc2"), myFont);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorder(Rectangle.NO_BORDER);
		signTable.addCell(c);
		// 签字3
		c = new PdfPCell();
		p = new Paragraph("主任/副主任医师：" + datas.get("doc3"), myFont);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorder(Rectangle.NO_BORDER);
		signTable.addCell(c);

		c = new PdfPCell();
		c.setColspan(3);
		c.addElement(signTable);
		ctx.addCell(c);
		// 补全表格
		// int pageRowsNum = 31;
		// for (int i = 0, len = pageRowsNum - ctx.getRows().size() %
		// pageRowsNum; i < len; i++) {
		// ctx.addCell("");
		// ctx.addCell("");
		// }
		// 行居中对齐
		for (PdfPRow row : ctx.getRows()) {
			for (PdfPCell cl : row.getCells()) {
				if (cl == null)
					continue;
				cl.setMinimumHeight(23);
				cl.setPadding(0);
				cl.setUseAscender(true);
				cl.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}
		cell.addElement(ctx);
		table.addCell(cell);
		doc.add(table);
		// 关闭写入
		doc.close();
	}

	private void addDiagToTable(PdfPTable table, String data, String label1,
			String label2, BaseFont font) {
		int index = 0;
		PdfPCell c;
		Paragraph p1;
		Paragraph p2;
		Font myFont = new Font(font);
		for (String str : data.split("@")) {
			c = new PdfPCell();
			if (index == 0) {
				p1 = new Paragraph(" "+label1, myFont);
				p2 = new Paragraph(" "+label2, myFont);
			} else {
				p1 = new Paragraph(" ");
				p2 = new Paragraph(" ");
			}
			//p1.setAlignment(Element.ALIGN_RIGHT);
			c.addElement(p1);
			table.addCell(c);
			c = new PdfPCell();
			c.addElement(p2);
			table.addCell(c);
			c = new PdfPCell(); 
			p1 = new Paragraph(" "+str, myFont);
			c.addElement(p1);
			table.addCell(c);
			index++;
		}
	}

	private void preparPrintData(Map<String, String> map) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			InHspCaseMaster cm = (InHspCaseMaster) session.get(
					InHspCaseMaster.class, Long.parseLong(map.get("cid")));
			if (cm != null) {
				map.put("inHspTimes", cm.getInHspTimes() + "");
				map.put("inHspDate", dateFormat.format(cm.getInHspDate())+"");
				Patient p = (Patient) session.get(Patient.class, cm
						.getPatientId());
				if (p != null) {
					map.put("patientName", p.getPatientName());
					map.put("patientNo", p.getPatientNo());
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
	//门诊十二级打印
	@SuppressWarnings("unused")
	private void preparPrintData1(Map<String, String> map) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if(map.get("cid").length() >=10){
				String hql = "from MemberInfo where memberno = '"+map.get("cid")+"'";
				List list = session.createQuery(hql).list();
				if(list != null && list.size() > 0){
					MemberInfo mem = (MemberInfo)list.get(0);
					Patient patient = mem.getPatient();
					if (patient != null) {
						String outHql = "from OutOrMergencyCase where patientId='"+patient.getId()+"' order by id desc";
						List outList = session.createQuery(outHql).list();
						if(outList!= null && outList.size() > 0){
							OutOrMergencyCase outOrMergencyCase = (OutOrMergencyCase)outList.get(0);
							if(outOrMergencyCase != null){
								if(outOrMergencyCase.getOutCount() != null){
									map.put("outCount", outOrMergencyCase.getOutCount() + "");
								}else{
									map.put("outCount", 1+"");
								}
								map.put("outTime", dateFormat.format(outOrMergencyCase.getActdate())+"");
							}
						}
						map.put("patientName", patient.getPatientName());
						map.put("patientNo", patient.getPatientNo());
					}
				}
			}else{
				OutOrMergencyCase mc = (OutOrMergencyCase) session.get(
						OutOrMergencyCase.class, Long.parseLong(map.get("cid")));
				if (mc != null) {
					if(mc.getOutCount() != null){
						map.put("outCount", mc.getOutCount() + "");
					}else{
						map.put("outCount", 1 + "");
					}
					map.put("outTime", dateFormat.format(mc.getOutTime())+"");
					Patient p = (Patient) session.get(Patient.class, Long.parseLong(mc
							.getPatientId()));
					if (p != null) {
						map.put("patientName", p.getPatientName());
						map.put("patientNo", p.getPatientNo());
					}
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
	//门诊十二级打印
	@SuppressWarnings("unused")
	private PdfPTable getHeader1(String patientName, String patientNo,
			String outCount, String rootPath, BaseFont font)
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
		p = new Paragraph("肝病十二级分类诊断系统", myFont);
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
		// 门诊次数
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setPaddingBottom(3);
		cell.setPaddingTop(3);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		p = new Paragraph("第 " + outCount + " 次门诊", myFont);
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
	public void print1(OutputStream os, JSONObject data, String rootPath)
			throws Exception {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Map<String, String> datas = new HashMap<String, String>();
		for (Object key : data.keySet()) {
			datas.put(key.toString(), data.getString(key.toString()));
		}
		preparPrintData1(datas);
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
		cell.addElement(getHeader1(datas.get("patientName"), datas
				.get("patientNo"), datas.get("outCount"), rootPath, font));
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
		cell.setPadding(0);
		cell.setBorderWidth(0);
		
		PdfPTable ctx = new PdfPTable(3);
		ctx.setSplitLate(false);
		ctx.setSplitRows(true);
		// 设置表格宽度100%
		ctx.setWidthPercentage(100);
		ctx.setWidths(new int[] { 14, 40, 103 });
		
		
		PdfPTable zsjTable = new PdfPTable(2);
		zsjTable.setSplitLate(false);
		zsjTable.setSplitRows(true);
		// 设置表格宽度100%
		zsjTable.setWidthPercentage(100);
		zsjTable.setWidths(new int[] { 1, 1});
		Font myFont = new Font(font, 12, Font.BOLD);
		
		// 诊断时间
		PdfPCell c = new PdfPCell();
		Paragraph p = new Paragraph(" 诊断时间：" + datas.get("date"), myFont);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorder(Rectangle.NO_BORDER);
		zsjTable.addCell(c);
		
		
		//诊断阶段
		c = new PdfPCell();
		p = new Paragraph(" 诊断阶段：门诊(" + datas.get("outTime")+")", myFont);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorder(Rectangle.NO_BORDER);
		zsjTable.addCell(c);
		
		
		c = new PdfPCell();
		c.setColspan(3);
		c.addElement(zsjTable);
		ctx.addCell(c);
		// 一级
		addDiagToTable(ctx, datas.get("diag1"), "一级", "肝脏疾病分类", font);
		// 二级
		addDiagToTable(ctx, datas.get("diag2"), "二级", "起病时间状态", font);
		// 三级
		addDiagToTable(ctx, datas.get("diag3"), "三级", "抗原抗体", font);
		// 四级
		addDiagToTable(ctx, datas.get("diag4"), "四级", "基因型", font);
		// 五级
		addDiagToTable(ctx, datas.get("diag5"), "五级", "肝功能分期、分级", font);
		// 六级
		addDiagToTable(ctx, datas.get("diag6"), "六级", "肝纤维化、肝硬化分型", font);
		// 七级
		addDiagToTable(ctx, datas.get("diag7"), "七级", "肝病并发症", font);
		// 八级
		addDiagToTable(ctx, datas.get("diag8"), "八级", "营养状态", font);
		// 九级
		addDiagToTable(ctx, datas.get("diag9"), "九级", "感染情况", font);
		// 十级
		addDiagToTable(ctx, datas.get("diag10"), "十级", "多脏器功能衰竭", font);
		// 十一级
		addDiagToTable(ctx, datas.get("diag11"), "十一级", "肝癌情况", font);
		// 十二级
		addDiagToTable(ctx, datas.get("diag12"), "十二级", "伴随疾病", font);
		// 签字表格
		PdfPTable signTable = new PdfPTable(3);
		signTable.setSplitLate(false);
		signTable.setSplitRows(true);
		// 设置表格宽度100%
		signTable.setWidthPercentage(100);
		signTable.setWidths(new int[] { 1, 1, 1 });
		myFont = new Font(font, 12, Font.NORMAL);
		// 签字1
		c = new PdfPCell();
		p = new Paragraph("门诊医师：" + datas.get("doc1"), myFont);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorder(Rectangle.NO_BORDER);
		signTable.addCell(c);
		// 签字2
		c = new PdfPCell();
		p = new Paragraph("主治医师：" + datas.get("doc2"), myFont);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorder(Rectangle.NO_BORDER);
		signTable.addCell(c);
		// 签字3
		c = new PdfPCell();
		p = new Paragraph("主任/副主任医师：" + datas.get("doc3"), myFont);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorder(Rectangle.NO_BORDER);
		signTable.addCell(c);
		
		c = new PdfPCell();
		c.setColspan(3);
		c.addElement(signTable);
		ctx.addCell(c);
		// 补全表格
		// int pageRowsNum = 31;
		// for (int i = 0, len = pageRowsNum - ctx.getRows().size() %
		// pageRowsNum; i < len; i++) {
		// ctx.addCell("");
		// ctx.addCell("");
		// }
		// 行居中对齐
		for (PdfPRow row : ctx.getRows()) {
			for (PdfPCell cl : row.getCells()) {
				if (cl == null)
					continue;
				cl.setMinimumHeight(23);
				cl.setPadding(0);
				cl.setUseAscender(true);
				cl.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}
		cell.addElement(ctx);
		table.addCell(cell);
		doc.add(table);
		// 关闭写入
		doc.close();
		}
	private PdfPTable getHeader(String patientName, String patientNo,
			String inHspTimes, String rootPath, BaseFont font)
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
		p = new Paragraph("肝病十二级分类诊断系统", myFont);
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

	@SuppressWarnings("unchecked")
	public JSONArray getGradings(Long caseId) throws Exception {
		JSONArray array = new JSONArray();
		Session session = null;
		Transaction tran = null;
		String pid = findPidByCidInHsp(caseId);
		List <Object[]> list = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			 
			

			  String sql = " select gd.id,gd.cid,gd.date,gd.diagnose from   t_InHsp_Liver_GradingDiag gd where cid in ( "
								+ "select omc.id from  t_OutOrMergencyCase omc  where omc.patientId=  '"
								+ pid
								+ "' union "
								+ "select icm.id from  t_InHsp_CaseMaster icm   where icm.patientId=     '"
								+ pid + "') order by date DESC  ";  
			   
			   
					 
			 list=   session.createSQLQuery(sql).list();
			  
			 
			 DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			 for (Object[] o : list){
				 String  id=o[0].toString();
				 String cid= o[1].toString();
					JSONObject node = new JSONObject();
					node.put("id", id+"_"+cid);
					Date date = (Date)o[2];
					String diagnose=(String)o[3];
					 if (date != null){
							if(!"".equals(diagnose) && diagnose != null){
								node.put("text", f.format(date)+"--"+diagnose );
							}else{
								node.put("text", f.format(date)+"--"+"门诊");
							}
						}else{
							node.put("text", "诊断时间未定义--门诊");
						} 
					
					 node.put("leaf", true);
						node.put("iconCls","none");
						node.put("cls","node");
						array.add(node);
					
					
				}
			tran.commit();}
			catch (Exception e) {
				tran.rollback();
				throw e;
			} finally {
				DatabaseUtil.closeResource(session);
			}
		 
		/*
		 try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<GradingDiag> list = session.createCriteria(GradingDiag.class)
					.add(Restrictions.eq("cid", caseId)).addOrder(
							Order.asc("date")).list();
			//System.out.print(list);
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for (GradingDiag g : list) {
				JSONObject node = new JSONObject();
				node.put("id", g.getId());
				if (g.getDate() != null)
					//liugang 2011-09-14 update
					if(!"".equals(g.getDiagnose()) && g.getDiagnose() != null){
						node.put("text", f.format(g.getDate())+g.getDiagnose());
					}else{
						node.put("text", f.format(g.getDate()));
					}
					//liugang 2011-09-14 update
				else
					node.put("text", "诊断时间未定义");
				node.put("leaf", true);
				node.put("iconCls","none");
				node.put("cls","node");
				array.add(node);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		
		*/
		return array;
	}
	public GradingDiag findById(Long id) throws Exception {
		GradingDiag g = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			g = (GradingDiag) session.get(GradingDiag.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return g;
	}
	//由cid 查询pid
	public String  findPidByCidInHsp (Long cid) throws Exception {
		String pid = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
		//	 String sql = "select mi.account from  t_InHsp_CaseMaster icm ,MemberInfo mi where icm.patientId=mi.patient and icm.id =   "+cid;
			String sql = " from  InHspCaseMaster   where id =   "+cid;
			
			 
			 
			List <InHspCaseMaster> list =   session.createQuery(sql).list();
			 
			if(list==null&&list.size()<1){
				DatabaseUtil.closeResource(session);
				return null;
			}else{
				pid= list.get(0).getPatientId().toString();
				tran.commit();
			}
			
			
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return pid;
	}

	
	//由cid 查询pid
	public String  findPidByCidOut (Long cid) throws Exception {
		String pid = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = " from  OutOrMergencyCase   where id =   "+cid;
			
			 
			 
			List <InHspCaseMaster> list =   session.createQuery(sql).list();
			 
			if(list==null&&list.size()<1){
				DatabaseUtil.closeResource(session);
				return null;
			}else{
				pid= list.get(0).getPatientId().toString();
				tran.commit();
			}
			
			
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return pid;
	}
	public void deleteGradingDiag(Long id) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			GradingDiag g=new GradingDiag();
			g.setId(id);
			session.delete(g);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public JSONArray getGradingOutIn(Long caseId) throws Exception {
		JSONArray array = new JSONArray();
		Session session = null;
		Transaction tran = null;
		
		
		
		
		//String pid = findPidByCidInHsp(caseId);
		String pid=caseId.toString();
		List <Object[]> list = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			 
			

			  String sql = " select gd.id,gd.cid,gd.date,gd.diagnose from   t_InHsp_Liver_GradingDiag gd where cid in ( "
								+ "select omc.id from  t_OutOrMergencyCase omc  where omc.patientId=  '"
								+ pid
								+ "' union "
								+ "select icm.id from  t_InHsp_CaseMaster icm   where icm.patientId=     '"
								+ pid + "') order by date DESC  ";  
			   
			   
					 
			 list=   session.createSQLQuery(sql).list();
			  
			 
			 DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			 for (Object[] o : list){
				 String  id=o[0].toString();
				 String cid= o[1].toString();
					JSONObject node = new JSONObject();
					node.put("id", id+"_"+cid);
					Date date = (Date)o[2];
					String diagnose=(String)o[3];
					 if (date != null){
							if(!"".equals(diagnose) && diagnose != null){
								node.put("text", f.format(date)+"--"+diagnose );
							}else{
								node.put("text", f.format(date)+"--"+"门诊");
							}
						}else{
							node.put("text", "诊断时间未定义--门诊");
						} 
					
					 node.put("leaf", true);
						node.put("iconCls","none");
						node.put("cls","node");
						array.add(node);
					
					
				}
			tran.commit();}
			catch (Exception e) {
				tran.rollback();
				throw e;
			} finally {
				DatabaseUtil.closeResource(session);
			}
		/**
		 
		 
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<GradingDiag> list = session.createCriteria(GradingDiag.class)
					.add(Restrictions.eq("cid", caseId)).addOrder(
							Order.asc("date")).list();
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for (GradingDiag g : list) {
				JSONObject node = new JSONObject();
				node.put("id", g.getId());
				if (g.getDate() != null){
					//liugang 2011-09-14 update
					if(!"".equals(g.getDiagnose()) && g.getDiagnose() != null){
						if("N".equals(g.getFiag())){
							node.put("text", f.format("住院"+g.getDate())+g.getDiagnose());
						}else{
							node.put("text", f.format("门诊"+g.getDate())+g.getDiagnose());
						}
					}else{
						node.put("text", f.format(g.getDate()));
					}
					//liugang 2011-09-14 update
				}
				else{
					node.put("text", "诊断时间未定义");
				}
				node.put("leaf", true);
				node.put("iconCls","none");
				node.put("cls","node");
				array.add(node);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		**/
		return array;
	}
}
