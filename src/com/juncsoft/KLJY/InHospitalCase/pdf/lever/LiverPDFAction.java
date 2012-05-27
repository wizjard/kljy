package com.juncsoft.KLJY.InHospitalCase.pdf.lever;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.itextpdf.text.Document;
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
import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.InHospitalCase.pdf.ChiefComplaintPDF;
import com.juncsoft.KLJY.InHospitalCase.pdf.DiagnosisPDF;
import com.juncsoft.KLJY.InHospitalCase.pdf.EpidemicDisHistoryPDF;
import com.juncsoft.KLJY.InHospitalCase.pdf.FamilyHistoryPDF;
import com.juncsoft.KLJY.InHospitalCase.pdf.LabDiagnosticExaminationPDF;
import com.juncsoft.KLJY.InHospitalCase.pdf.LiverPhysicalExaminationPDF;
import com.juncsoft.KLJY.InHospitalCase.pdf.MarritalChildbearingHistoryPDF;
import com.juncsoft.KLJY.InHospitalCase.pdf.MenstrualHistoryPDF;
import com.juncsoft.KLJY.InHospitalCase.pdf.PDFHeader;
import com.juncsoft.KLJY.InHospitalCase.pdf.PageNumHelper;
import com.juncsoft.KLJY.InHospitalCase.pdf.PastHistoryPDF;
import com.juncsoft.KLJY.InHospitalCase.pdf.PersonalHistoryPDF;
import com.juncsoft.KLJY.InHospitalCase.pdf.PresentIllnessHistoryNPDF;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.DictionaryUtil;

public class LiverPDFAction extends DispatchAction {

	private PDFHeader pdfHeader = new PDFHeader();
	private ChiefComplaintPDF chief = new ChiefComplaintPDF();
	private PresentIllnessHistoryNPDF pre = new PresentIllnessHistoryNPDF();
	private PastHistoryPDF past = new PastHistoryPDF();
	private EpidemicDisHistoryPDF epid = new EpidemicDisHistoryPDF();
	private PersonalHistoryPDF per = new PersonalHistoryPDF();
	private MenstrualHistoryPDF men = new MenstrualHistoryPDF();
	private MarritalChildbearingHistoryPDF mar = new MarritalChildbearingHistoryPDF();
	private FamilyHistoryPDF family = new FamilyHistoryPDF(); 
	//调用肝病体格检查
	private LiverPhysicalExaminationPDF phy = new LiverPhysicalExaminationPDF();
	private LabDiagnosticExaminationPDF lab = new LabDiagnosticExaminationPDF();
	private DiagnosisPDF diag = new DiagnosisPDF();
	
	public ActionForward liverPDF_Print(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long id = Long.parseLong(request.getParameter("id"));
		JSONObject json = null;
		response.setContentType("application/pdf");
		String filePath = request.getRealPath("/");
		Document doc = null;
		Session session = null;
		Transaction tran = null;
		try {
			doc = new Document(PageSize.A4, 20 * pdfHeader.dl,
					10 * pdfHeader.dl, 5 * pdfHeader.dl, 5 * pdfHeader.dl);

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
			fontGeneral = new Font(bfChinese, 12, Font.NORMAL);// 一般内容
			Font myFontItem = new Font(bfChinese, 12, Font.BOLD);
			PageNumHelper event = new PageNumHelper(bfChinese);
			writer.setPageEvent(event);

			// 设置奇偶页页边距镜像
			doc.setMarginMirroring(true);
			// 打开document
			doc.open();

			PdfPTable table = new PdfPTable(1);
			// 表格跨页显示
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
			// 基本信息加载
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Map map = new HashMap();
			InHspCaseMaster master = (InHspCaseMaster) session.get(
					InHspCaseMaster.class, id);
			map.put("Age", master.getAge());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = master.getInHspDate();
			if (date != null)
				map.put("InHspDate", df.format(date));
			map.put("InHspTimes", master.getInHspTimes() + "");
			if (master.getPatientId() != null && master.getPatientId() > 0)
				PatientBasicInfo(map, master.getPatientId(),session);
			//表头加载
			cell.addElement(pdfHeader.getHeader(map, logo, fontChinese,
					fontEngLish, smallFont, fontBig, "入院记录"));
			table.addCell(cell);
			// 页脚
			cell = new PdfPCell();
			cell.setPadding(0);
			cell.setBorderWidth(0);
			cell.setBorderWidthTop(0.5f);
			cell.addElement(new Paragraph(" "));
			table.addCell(cell);
			table.setHeaderRows(2);
			table.setFooterRows(1);
			// 内容
			cell = new PdfPCell();
			cell.setPadding(0);
			cell.setBorderWidth(0);

			//主诉
			chief.getContent(cell, fontGeneral, myFontItem,id, session, map, df);
			
			PdfPTable tableContent = new PdfPTable(2);
			tableContent.setSplitLate(false);
			tableContent.setSplitRows(true);
			// 设置表格宽度100%
			tableContent.setWidthPercentage(100);
			tableContent.setWidths(new int[] {15,75});
			//现病史
			pre.getContent(tableContent, fontGeneral, myFontItem, id, session, map, df);
			//流行病史
			epid.getContent(tableContent, fontGeneral, myFontItem, id, session, map, df);
			//既往史
			past.getContent(tableContent, fontGeneral, myFontItem, id, session, map, df);
			//个人史
			per.getContent(tableContent, fontGeneral, myFontItem, id, session, map, df);
			//月经史
			men.getContent(tableContent, fontGeneral, myFontItem, id, session, map, df);
			//婚育史
			mar.getContent(tableContent, fontGeneral, myFontItem, id, session, map, df);
			//家族史
			family.getContent(tableContent, fontGeneral, myFontItem, id, session, map, df);
			for (PdfPRow row : tableContent.getRows()) {
				for (PdfPCell c : row.getCells()) {
					if (c != null)
						c.setBorder(Rectangle.NO_BORDER);
				}
			}
			cell.addElement(tableContent);
			
			//上述完成后签字
			PdfPTable tableSecond = new PdfPTable(2);
			tableSecond.setSplitLate(false);
			tableSecond.setSplitRows(true);
			// 设置表格宽度100%
			tableSecond.setWidthPercentage(100);
			tableSecond.setWidths(new int[] {60,40});
			
			PdfPCell cellC = new PdfPCell();
			Paragraph p = new Paragraph("患者或家属对以上病史确认无误签字：", fontGeneral);
//			p.setAlignment(Element.ALIGN_LEFT);
			p.setIndentationLeft(83);
			cellC.addElement(p);
			tableSecond.addCell(cellC);
			
			cellC = new PdfPCell();
//			cellC.setLeft(20);
			p = new Paragraph("日期：", fontGeneral);
			p.setIndentationLeft(55);
			cellC.addElement(p);
			tableSecond.addCell(cellC);
			
			cellC = new PdfPCell();
			p = new Paragraph("住院医师：", fontGeneral);
//			p.setAlignment(Element.ALIGN_LEFT);
			p.setIndentationLeft(83);
			cellC.addElement(p);
			tableSecond.addCell(cellC);
			
			cellC = new PdfPCell();
//			cellC.setLeft(20);
			p = new Paragraph("主治医师：", fontGeneral);
			p.setIndentationLeft(55);
			cellC.addElement(p);
			tableSecond.addCell(cellC);
			cell.addElement(tableSecond);
			
			for (PdfPRow row : tableSecond.getRows()) {
				for (PdfPCell c : row.getCells()) {
					if (c != null)
						c.setBorder(Rectangle.NO_BORDER);
				}
			}
			//体格检查
			PdfPTable tableThree = new PdfPTable(5);
			tableThree.setSplitLate(false);
			tableThree.setSplitRows(true);
			// 设置表格宽度100%
			tableThree.setWidthPercentage(100);
			tableThree.setWidths(new int[] {13,19,25,19,15});
			phy.getContent(tableThree, fontGeneral, myFontItem, id, session, map, df);
			//辅助检查
			lab.getContent(tableThree, fontGeneral, myFontItem, id, session, map, df);
			//确定诊断/初步诊断/订正诊断
			diag.getContent(tableThree, fontGeneral, myFontItem, id, session, map, df);
			cell.addElement(tableThree);
			
			for (PdfPRow row : tableThree.getRows()) {
				for (PdfPCell c : row.getCells()) {
					if (c != null)
						c.setBorder(Rectangle.NO_BORDER);
				}
			}
			table.addCell(cell);
			doc.add(table);
			tran.commit();
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

	/**
	 * 病人基本信息，及表头
	 * @param map
	 * @param key
	 * @param session
	 * @throws Exception
	 */
	private void PatientBasicInfo(Map<String, String> map, Long key,
			Session session) throws Exception {

		Patient patient = (Patient) session.get(Patient.class, key);
		if (patient != null) {
			map.put("PatientName", patient.getPatientName());
			map.put("PatientNo", patient.getPatientNo());
			map.put("Gender", DictionaryUtil.getIndependentDictionaryText(
					"gender_gb", patient.getGender()));
			String people = DictionaryUtil.getIndependentDictionaryText(
					"people", patient.getPeople());
			if (people.equals("其它")) {
				people = patient.getPeople0();
			}
			map.put("People", people);
			String province = DictionaryUtil.getIndependentDictionaryText(
					"province", patient.getProvince());
			if (province.equals("其它")) {
				province = patient.getProvince0();
			}
			map.put("Province", province);
			String marrageStatus = DictionaryUtil.getIndependentDictionaryText(
					"marrageStatus", patient.getMarrageStatus());
			if (marrageStatus.equals("其它")) {
				marrageStatus = patient.getMarrageStatus0();
			}
			map.put("MarrageStatus", marrageStatus);
			String occupation = DictionaryUtil.getIndependentDictionaryText(
					"occupation", patient.getOccupation());
			if (occupation.equals("其他")) {
				occupation = patient.getOccupation0();
			}
			map.put("Occupation", occupation);
			map.put("Address", patient.getHomeAddr());
		}
	}

}
