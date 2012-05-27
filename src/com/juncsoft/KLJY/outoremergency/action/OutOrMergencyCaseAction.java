package com.juncsoft.KLJY.outoremergency.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.juncsoft.KLJY.outoremergency.dao.OutOrMergencyCaseDao;
import com.juncsoft.KLJY.outoremergency.dao.OutOrMergencyPatientDao;
import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyCase;
import com.juncsoft.KLJY.outoremergency.impl.OutOrMergencyCaseDaoImpl;
import com.juncsoft.KLJY.outoremergency.impl.OutOrMergencyPatientDaoImpl;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class OutOrMergencyCaseAction extends DispatchAction {
	private OutOrMergencyCaseDao dao = new OutOrMergencyCaseDaoImpl();
	private OutOrMergencyPatientDao pDao = new OutOrMergencyPatientDaoImpl();

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String data = request.getParameter("data");
			JSONArray array = JSONArray.fromObject(data);
			dao.saveOrUpdateOutOrMergencyCase(array);
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

	public ActionForward queryAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Long id = Long.parseLong(request.getParameter("id"));
			String data = JSONArray.fromObject(dao.queryAll(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward outOrMergencyCase_treeNodes(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		try {
			Long patientId = Long.parseLong(request.getParameter("id"));
			out.println(dao.outOrMergencyCase_treeNodes(patientId));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
		return null;
	}
	
	public ActionForward updateSubmiterById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Long submiter = Long.parseLong(request.getParameter("outSubmiter"));
			res.setData(JSONObject.fromObject(dao.updateSubmiterById(id, submiter),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
					.toString());
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
	
	public ActionForward updateSubmiterById_cancelSubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(dao.updateSubmiterById_cancelSubmit(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
					.toString());
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
	
	public ActionForward updateOutOrMergencyCourse_resubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Long submitId = Long.parseLong(request.getParameter("outSubmiter"));
			res.setData(JSONObject.fromObject(dao.updateOutOrMergencyCourse_resubmit(id, submitId),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
					.toString());
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
	
	public ActionForward updateOutOrMergencyCourse_check(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Long checkerId = Long.parseLong(request.getParameter("outChecker"));
			int verifyStatus = Integer.parseInt(request
					.getParameter("outVerifyStatus"));
			res.setData(JSONObject.fromObject(dao.updateOutOrMergencyCourse_check(id, checkerId, verifyStatus),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
					.toString());
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
	
	public ActionForward findAllOutOrMergencyCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String id = request.getParameter("id");
			String outNameList = request.getParameter("outNameList");
			String outRegno = request.getParameter("outRegno");
			String data = JSONArray.fromObject(dao.findAllOutOrMergencyCase(id,outNameList,outRegno),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	//查找HIS该会员的挂号记录  liugang 2011-08-06新增
	public ActionForward executeOnePatientOutCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Long id = Long.parseLong(request.getParameter("id"));
			String hisPID = request.getParameter("hisPID");
			String data = JSONArray.fromObject(pDao.executeOnePatientOutCase(id,hisPID),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward findAllOutOrMergencyCaseByPatientId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String id = request.getParameter("id");
			String data = JSONArray.fromObject(dao.findAllOutOrMergencyCaseByPatientId(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd"))
					.toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	
	public static float dl = 72 / 25.4f;// 1mm的Pdf文档长度
	public ActionForward outcase_Print(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("PID"));
		Long printId = Long.parseLong(request.getParameter("printId"));
		response.setContentType("application/pdf");
		String filePath = request.getRealPath("/");
		Document doc = null;
		Session session = null;
		Transaction tran = null;
		try {
			doc = new Document(PageSize.A5,20 * dl,
					10 * dl, 5 * dl, 5 * dl);
			// 设置字体
			BaseFont bfEngLish = BaseFont.createFont(filePath
					+ "PUBLIC/print/SIMKAI.TTF", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			BaseFont bfChinese = BaseFont.createFont(filePath
					+ "PUBLIC/print/SIMSUN.TTC,0", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			Font fontChinese; // 大标题
			Font fontEngLish;// 英文标题
			Font smallFont;// 小标题
			Font fontGeneral;// 一般内容
			Font fontBig;// 大标题
			Font fontEveryRecordTitle;// 每次记录标题比一般内容大一号字
			Image logo;
			PdfWriter writer = PdfWriter.getInstance(doc, response
					.getOutputStream());
			logo = Image.getInstance(filePath + "PUBLIC/print/youanLogo.jpg");
			fontChinese = new Font(bfChinese, 16, Font.BOLD); // 大标题
			fontEngLish = new Font(bfEngLish, 9, Font.BOLD);// 英文标题
			smallFont = new Font(bfChinese, 9, Font.NORMAL);// 小标题
			fontBig = new Font(bfChinese, 15, Font.BOLD);// 大标题
			fontEveryRecordTitle = new Font(bfChinese, 13, Font.BOLD);// 每次记录标题比一般内容大一号字
			fontGeneral = new Font(bfChinese, 10, Font.NORMAL);// 一般内容
			Font myFontItem = new Font(bfChinese, 12, Font.NORMAL);
//			PageNumHelper event = new PageNumHelper(bfChinese);
//			writer.setPageEvent(event);
			// 设置奇偶页页边距镜像
			doc.setMarginMirroring(true);
			// 打开document
			doc.open();
			PdfPTable table = new PdfPTable(1);
			table.setSplitLate(false);
			table.setSplitRows(true);
			// 设置表格宽度100%
			table.setWidthPercentage(100);
			// 设置表格自动拉伸最后一行填满页面
			table.setExtendLastRow(true);

			//获取数据
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient patient = (Patient)session.get(Patient.class, id);
			OutOrMergencyCase outOrMergencyCase = (OutOrMergencyCase)session.get(OutOrMergencyCase.class,printId);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			// 标题
			PdfPCell cell = new PdfPCell();
			Paragraph p = new Paragraph("首都医科大学附属北京佑安医院", fontChinese);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			table.addCell(cell);
		
			cell = new PdfPCell();
			p = new Paragraph("门诊记录", fontEveryRecordTitle);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			table.addCell(cell);
			
			cell = new PdfPCell();
			String sex = "";
			if("1".equals(patient.getGender())){
				sex = "男";
			}else{
				sex = "女";
			}
			p = new Paragraph("姓名："+patient.getPatientName()+"   性别："+sex+"  门诊卡号："+patient.getPatientId()+"", myFontItem);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			table.addCell(cell);
			
			if(outOrMergencyCase != null){
				cell = new PdfPCell();
				p = new Paragraph("记录日期："+sdf.format(outOrMergencyCase.getOutWriteTime()), myFontItem);
				p.setAlignment(Element.ALIGN_CENTER);
				cell.addElement(p);
				table.addCell(cell);
				
				cell = new PdfPCell();
				p = new Paragraph(outOrMergencyCase.getOutContent(), fontGeneral);
				cell.addElement(p);
				table.addCell(cell);
				
				if(outOrMergencyCase.getOutSubmiter() !=null && !"".equals(outOrMergencyCase.getOutSubmiter())){
					User userS = (User)session.get(User.class, outOrMergencyCase.getOutSubmiter());
					cell = new PdfPCell();
					p = new Paragraph("医生签字："+userS.getName(), myFontItem);
					p.setAlignment(Element.ALIGN_RIGHT);
					cell.addElement(p);
					table.addCell(cell);
				}
			}
			
			tran.commit();
			for (PdfPRow row : table.getRows()) {
				for (PdfPCell c : row.getCells()) {
					if (c != null)
						c.setBorder(Rectangle.NO_BORDER);
				}
			}
			doc.add(table);
		} catch (Exception ioe) {
			if(tran != null){
				tran.rollback();
			}
			ioe.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			doc.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
		return null;
	}
	
	public ActionForward checkIsSubmiter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setSuccess(dao.checkIsSubmiter(id));
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
}
