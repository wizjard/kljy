package com.juncsoft.KLJY.InHospitalCase.pdf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.ChiefComplaint;
import com.juncsoft.KLJY.util.DictionaryUtil;

/**
 * 病人基本信息和主诉
 * @author liugang
 *
 */
public class ChiefComplaintPDF {
	public void getContent(PdfPCell cell,Font fontGeneral,Font myFontItem,Long id,Session session,Map map,DateFormat df) throws Exception{
		//基本信息
		PdfPTable tableContent = new PdfPTable(3);
		tableContent.setSplitLate(false);
		tableContent.setSplitRows(true);
		// 设置表格宽度100%
		tableContent.setWidthPercentage(100);
		tableContent.setWidths(new int[] { 25, 23, 52 });
		
		PdfPCell cellC = new PdfPCell();
		Paragraph p = new Paragraph("姓名:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);

		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		p = new Paragraph(map.get("PatientName").toString(), fontGeneral);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		p = new Paragraph("职业:", myFontItem);
		p.setAlignment(Element.ALIGN_LEFT);
		p.setFont(fontGeneral);
		p.add(map.get("Occupation").toString());
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		p = new Paragraph("性别:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		p = new Paragraph(map.get("Gender").toString(), fontGeneral);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		p = new Paragraph("住址:", myFontItem);
		p.setAlignment(Element.ALIGN_LEFT);
		p.setFont(fontGeneral);
		p.add(map.get("Address").toString());
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		p = new Paragraph("年龄:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		p = new Paragraph(map.get("Age").toString(), fontGeneral);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		p = new Paragraph("入院日期:", myFontItem);
		p.setAlignment(Element.ALIGN_LEFT);
		p.setFont(fontGeneral);
		p.add(map.get("InHspDate").toString());
		cellC.addElement(p);
		tableContent.addCell(cellC);


		cellC = new PdfPCell();
		p = new Paragraph("民族:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		p = new Paragraph(map.get("People").toString(), fontGeneral);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		ChiefComplaint cc = null;
		List list = session.createCriteria(ChiefComplaint.class).add(
				Restrictions.eq("caseId", id)).list();
		if (list.size() > 0) {
			cc = (ChiefComplaint) list.get(0);
		}
		
		cellC = new PdfPCell();
		p = new Paragraph("病史采集日期:", myFontItem);
		p.setAlignment(Element.ALIGN_LEFT);
		p.setFont(fontGeneral);
		Date date = null;
		if(cc != null){
			date = cc.getDataGetDate();
		}
		if (date != null) {
			p.add(df.format(date));
		}else{
			p.add("");
		}
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		p = new Paragraph("籍贯:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		p = new Paragraph(map.get("Province").toString(), fontGeneral);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		p = new Paragraph("叙述者:", myFontItem);
		p.setAlignment(Element.ALIGN_LEFT);
		p.setFont(fontGeneral);
		String pageCode = "EMR-liver-ChiefComplaint";
		if(cc != null){
			String narrator = DictionaryUtil.getPublicDictionaryText(
					pageCode, "narrator", cc.getNarrator());
			
			if (narrator.equals("其它")) {
				narrator = cc.getNarrator0();
			}
			p.add(narrator);
		}else{
			p.add("");
		}
		
		cellC.addElement(p);
		tableContent.addCell(cellC);
		
		cellC = new PdfPCell();
		p = new Paragraph("婚姻:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		p = new Paragraph(map.get("MarrageStatus").toString(), fontGeneral);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		p = new Paragraph("可靠性:", myFontItem);
		p.setAlignment(Element.ALIGN_LEFT);
		p.setFont(fontGeneral);
		if(cc != null){
			String reliability = DictionaryUtil.getPublicDictionaryText(
					pageCode, "reliability", cc.getReliability());
			p.add(reliability);
		}else{
			p.add("");
		}
		cellC.addElement(p);
		tableContent.addCell(cellC);
		
		for (PdfPRow row : tableContent.getRows()) {
			for (PdfPCell c : row.getCells()) {
				if (c != null)
					c.setBorder(Rectangle.NO_BORDER);
			}
		}
		cell.addElement(tableContent);
		
		tableContent = new PdfPTable(2);
		tableContent.setSplitLate(false);
		tableContent.setSplitRows(true);
		// 设置表格宽度100%
		tableContent.setWidthPercentage(100);
		tableContent.setWidths(new int[] { 15, 75 });
		
		cellC = new PdfPCell();
		p = new Paragraph("主诉:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		if(cc != null){
			p = new Paragraph(cc.getCcContent(), fontGeneral);
		}else{
			p = new Paragraph("", fontGeneral);
		}
		cellC.addElement(p);
		tableContent.addCell(cellC);
		cell.addElement(tableContent);
		for (PdfPRow row : tableContent.getRows()) {
			for (PdfPCell c : row.getCells()) {
				if (c != null)
					c.setBorder(Rectangle.NO_BORDER);
			}
		}
	}
}
