package com.juncsoft.KLJY.InHospitalCase.pdf.aids;

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
import com.juncsoft.KLJY.InHospitalCase.Liver.PresentHistory.PresentIllnessHistoryN;
import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory;

/**
 * 现病史(艾滋病)
 * @author liugang
 *
 */
public class PresentIllnessHistoryByAidsPDF {
	public void getContent(PdfPTable tableContent,Font fontGeneral,Font myFontItem,Long id,Session session,Map map,DateFormat df) {
		AidsHistory n = null;
		List list = session.createCriteria(AidsHistory.class).add(
				Restrictions.eq("caseId", id)).list();
		if (list.size() > 0) {
			n = (AidsHistory) list.get(0);
		}
		PdfPCell cellC = new PdfPCell();
		Paragraph p = new Paragraph("现病史：", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		cellC.setColspan(3);
		if( n!= null && n.getContent() != null){
			p = new Paragraph(n.getContent(), fontGeneral);
		}else{
			p = new Paragraph("", fontGeneral);
		}
		cellC.addElement(p);
		tableContent.addCell(cellC);
	}
}
