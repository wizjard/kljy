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
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.FamilyHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.MarritalChildbearingHistory;

/*
 * 家族史
 */
public class FamilyHistoryPDF {
	public void getContent(PdfPTable tableContent,Font fontGeneral,Font myFontItem,Long id,Session session,Map map,DateFormat df){
		FamilyHistory fh = null;
		List list = session.createCriteria(FamilyHistory.class).add(
				Restrictions.eq("caseId", id)).list();
		if (list.size() > 0) {
			fh = (FamilyHistory) list.get(0);
		}else{
			List _list = session.createCriteria(MarritalChildbearingHistory.class).add(
					Restrictions.eq("caseId", id)).list();
			MarritalChildbearingHistory mcbh = new MarritalChildbearingHistory();
			if(_list.size() > 0){
				mcbh = (MarritalChildbearingHistory) _list.get(0);
				fh = new FamilyHistory();//liugang 2011-05-13 add
				fh.setCaseId(id);
				fh.setSon(mcbh.getSunCount());
				fh.setDaughter(mcbh.getDaughterCount());
			}
		}
		PdfPCell cellC = new PdfPCell();
		Paragraph p = new Paragraph("家族史:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		cellC = new PdfPCell();
		cellC.setColspan(3);
		if(fh != null){
			p = new Paragraph(fh.getFaimlyHistoryDesc(), fontGeneral);
		}else{
			p = new Paragraph("", fontGeneral);
		}
		cellC.addElement(p);
		tableContent.addCell(cellC);
	}
}
