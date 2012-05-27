package com.juncsoft.KLJY.InHospitalCase.pdf;

import java.text.DateFormat;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.juncsoft.KLJY.InHospitalCase.Eye.entity.Eye;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.LabDiagnosticExamination;

/**
 * 辅助检查
 * 
 * @author liugang
 * 
 */
public class LabDiagnosticExaminationPDF {
	public void getContent(PdfPTable tableContent,Font fontGeneral,Font myFontItem,Long id,Session session,Map map,DateFormat df) {
		LabDiagnosticExamination lde = null;
		List list = session.createCriteria(LabDiagnosticExamination.class).add(
				Restrictions.eq("caseId", id)).list();
		if (list.size() > 0) {
			lde = (LabDiagnosticExamination) list.get(0);
		}
		PdfPCell cellC = new PdfPCell();
		Paragraph p = new Paragraph("辅助检查:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		cellC.setColspan(4);
		if(lde != null){
			p = new Paragraph(lde.getResult(), fontGeneral);
		}else{
			p = new Paragraph("", fontGeneral);
		}
		cellC.addElement(p);
		tableContent.addCell(cellC);
	}
}
