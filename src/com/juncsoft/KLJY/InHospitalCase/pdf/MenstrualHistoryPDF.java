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
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.MenstrualHistory;

/**
 * 月经史
 * @author liugang
 *
 */
public class MenstrualHistoryPDF {
	public void getContent(PdfPTable tableContent,Font fontGeneral,Font myFontItem,Long id,Session session,Map map,DateFormat df){
		MenstrualHistory mh = null;
		List list = session.createCriteria(MenstrualHistory.class).add(
				Restrictions.eq("caseId", id)).list();
		if (list.size() > 0) {
			mh = (MenstrualHistory) list.get(0);
		}
		if(mh != null){
			if(!("".equals(mh.getMenstrualDesc()))&& mh.getMenstrualDesc() != null){
				PdfPCell cellC = new PdfPCell();
				Paragraph p = new Paragraph("月经史:", myFontItem);
				p.setAlignment(Element.ALIGN_RIGHT);
				cellC.addElement(p);
				tableContent.addCell(cellC);

				cellC = new PdfPCell();
				cellC.setColspan(3);
				p = new Paragraph(mh.getMenstrualDesc(), fontGeneral);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			}
		}
		
	}
}
