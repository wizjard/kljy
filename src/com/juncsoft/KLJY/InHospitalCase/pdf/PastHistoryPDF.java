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
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.EpidemicDisHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory;

/**
 * 既往史
 * @author liugang
 *
 */
public class PastHistoryPDF {
	public void getContent(PdfPTable tableContent,Font fontGeneral,Font myFontItem,Long id,Session session,Map map,DateFormat df){
		PastHistory ph = null;
		List list = session.createCriteria(PastHistory.class).add(
				Restrictions.eq("caseId", id)).list();
		if (list.size() > 0) {
			ph = (PastHistory) list.get(0);
		}
		PdfPCell cellC = new PdfPCell();
		Paragraph p = new Paragraph("既往史:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		cellC.setColspan(3);
		if(ph != null){
			p = new Paragraph(ph.getPastHistoryDesc(), fontGeneral);
		}else{
			p = new Paragraph("", fontGeneral);
		}
		
		cellC.addElement(p);
		tableContent.addCell(cellC);
	}
}
