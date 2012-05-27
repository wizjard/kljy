package com.juncsoft.KLJY.InHospitalCase.pdf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.Diagnosis;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.LabDiagnosticExamination;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.RevisedDiagnosis;
import com.juncsoft.KLJY.util.DictionaryUtil;

/**
 * 确定诊断\初步诊断\订正诊断
 * @author liugang
 *
 */
public class DiagnosisPDF {
	public void getContent(PdfPTable tableContent,Font fontGeneral,Font myFontItem,Long id,Session session,Map map,DateFormat df) throws Exception {
		Diagnosis diag = null;
		List list = session.createCriteria(Diagnosis.class).add(
				Restrictions.eq("caseId", id)).list();
		if (list.size() > 0) {
			diag = (Diagnosis) list.get(0);
		}
		if(diag != null){
			PdfPCell cellC = new PdfPCell();
			Paragraph p = new Paragraph("确定诊断:", myFontItem);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			//确定诊断
			DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
			Date date = diag.getQueding_checkdate();
			String temp ="";
			if (date != null)
				temp = dfs.format(date);
			cellC = new PdfPCell();
			p = new Paragraph("日期:"+temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			//初步诊断
			date = diag.getChubu_checkdate();
			if (date != null)
				temp= dfs.format(date);
			cellC = new PdfPCell();
			p = new Paragraph("初步诊断:", myFontItem);
			p.setAlignment(Element.ALIGN_LEFT);
			p.setFont(fontGeneral);
			p.add("日期:"+temp);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			//诊断内容(确定诊断和初步诊断)
			
			boolean quFlag = false;
			PdfPTable tableInner = new PdfPTable(1);
			tableInner.setSplitLate(false);
			tableInner.setSplitRows(true);
			// 设置表格宽度100%
			tableInner.setWidthPercentage(100);
			tableInner.setWidths(new int[] {50});
			//确定诊断第一级开始处理
			if(!"".equals(diag.getQueding_diagnosis1())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis1(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis2())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis2(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis3())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis3(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis4())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis4(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis5())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis5(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis6())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis6(), fontGeneral);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis7())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis7(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis8())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis8(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis9())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis9(), fontGeneral);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis10())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis10(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis11())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis11(), fontGeneral);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis12())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis12(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis13())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis13(), fontGeneral);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis14())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis14(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis15())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis15(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis16())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis16(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis17())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis17(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis18())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis18(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis19())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis19(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			if(!"".equals(diag.getQueding_diagnosis20())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getQueding_diagnosis20(), fontGeneral);
				cellC.addElement(p);
				tableInner.addCell(cellC);
			}
			//确定诊断医生签字
			cellC = new PdfPCell();
			temp = DictionaryUtil.getIndependentDictionaryText(
					"userName", diag.getQueding_inhspDoctorId()
							+ "");
			p = new Paragraph("住院医师:"+temp, fontGeneral);
			cellC.addElement(p);
			tableInner.addCell(cellC);
			
			
			//确定诊断医生签字
			cellC = new PdfPCell();
			temp = DictionaryUtil.getIndependentDictionaryText(
					"userName", diag.getQueding_treatDoctorId()
					+ "");
			p = new Paragraph("主治医师:"+temp, fontGeneral);
			cellC.addElement(p);
			tableInner.addCell(cellC);
			
			//确定诊断医生签字
			cellC = new PdfPCell();
			temp = DictionaryUtil.getIndependentDictionaryText(
					"userName", diag
					.getQueding_directorDoctorId()
					+ "");
			p = new Paragraph("副主任医师:"+temp, fontGeneral);
			cellC.addElement(p);
			tableInner.addCell(cellC);
			
			
			PdfPTable tableInnerC = new PdfPTable(1);
			tableInnerC.setSplitLate(false);
			tableInnerC.setSplitRows(true);
			// 设置表格宽度100%
			tableInnerC.setWidthPercentage(100);
			tableInnerC.setWidths(new int[] {50});
			//初步诊断第一级开始处理
			if(!"".equals(diag.getChubu_diagnosis1())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getChubu_diagnosis1(), fontGeneral);
				cellC.addElement(p);
				tableInnerC.addCell(cellC);
			}
			if(!"".equals(diag.getChubu_diagnosis2())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getChubu_diagnosis2(), fontGeneral);
				cellC.addElement(p);
				tableInnerC.addCell(cellC);
			}
			if(!"".equals(diag.getChubu_diagnosis3())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getChubu_diagnosis3(), fontGeneral);
				cellC.addElement(p);
				tableInnerC.addCell(cellC);
			}
			if(!"".equals(diag.getChubu_diagnosis4())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getChubu_diagnosis4(), fontGeneral);
				cellC.addElement(p);
				tableInnerC.addCell(cellC);
			}
			if(!"".equals(diag.getChubu_diagnosis5())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getChubu_diagnosis5(), fontGeneral);
				cellC.addElement(p);
				tableInnerC.addCell(cellC);
			}
			if(!"".equals(diag.getChubu_diagnosis6())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getChubu_diagnosis6(), fontGeneral);
				cellC.addElement(p);
				tableInnerC.addCell(cellC);
			}
			if(!"".equals(diag.getChubu_diagnosis7())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getChubu_diagnosis7(), fontGeneral);
				cellC.addElement(p);
				tableInnerC.addCell(cellC);
			}
			if(!"".equals(diag.getChubu_diagnosis8())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getChubu_diagnosis8(), fontGeneral);
				cellC.addElement(p);
				tableInnerC.addCell(cellC);
			}
			if(!"".equals(diag.getChubu_diagnosis9())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getChubu_diagnosis9(), fontGeneral);
				cellC.addElement(p);
				tableInnerC.addCell(cellC);
			}
			if(!"".equals(diag.getChubu_diagnosis10())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getChubu_diagnosis10(), fontGeneral);
				cellC.addElement(p);
				tableInnerC.addCell(cellC);
			}
			if(!"".equals(diag.getChubu_diagnosis11())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getChubu_diagnosis11(), fontGeneral);
				cellC.addElement(p);
				tableInnerC.addCell(cellC);
			}
			if(!"".equals(diag.getChubu_diagnosis12())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getChubu_diagnosis12(), fontGeneral);
				cellC.addElement(p);
				tableInnerC.addCell(cellC);
			}
			if(!"".equals(diag.getChubu_diagnosis13())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getChubu_diagnosis13(), fontGeneral);
				cellC.addElement(p);
				tableInnerC.addCell(cellC);
			}
			if(!"".equals(diag.getChubu_diagnosis14())){
				cellC = new PdfPCell();
				p = new Paragraph(diag.getChubu_diagnosis14(), fontGeneral);
				cellC.addElement(p);
				tableInnerC.addCell(cellC);
			}
			//初步诊断医生签字
			cellC = new PdfPCell();
			temp = DictionaryUtil.getIndependentDictionaryText(
					"userName", diag.getChubu_inhspDoctorId()+ "");
			p = new Paragraph("住院医师:"+temp, fontGeneral);
			cellC.addElement(p);
			tableInnerC.addCell(cellC);
			//初步诊断医生签字
			cellC = new PdfPCell();
			temp = DictionaryUtil.getIndependentDictionaryText(
					"userName", diag.getChubu_treatDoctorId()
					+ "");
			p = new Paragraph("主治医师:"+temp, fontGeneral);
			cellC.addElement(p);
			tableInnerC.addCell(cellC);
			
			for (PdfPRow row : tableInner.getRows()) {
				for (PdfPCell c : row.getCells()) {
					if (c != null)
						c.setBorder(Rectangle.NO_BORDER);
				}
			}
			//确定诊断插入，首先插入一个表格
			cellC = new PdfPCell();
			cellC.setColspan(3);
			cellC.addElement(tableInner);
			cellC.setPaddingLeft(13);
			tableContent.addCell(cellC);
			
			for (PdfPRow row : tableInnerC.getRows()) {
				for (PdfPCell c : row.getCells()) {
					if (c != null)
						c.setBorder(Rectangle.NO_BORDER);
				}
			}
			//初步诊断插入，首先插入一个表格
			cellC = new PdfPCell();
			cellC.setColspan(2);
			cellC.addElement(tableInnerC);
			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			p = new Paragraph("订正诊断:", myFontItem);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			temp = "";
			RevisedDiagnosis  diags = null;
			List lists = session.createCriteria(RevisedDiagnosis.class).add(
					Restrictions.eq("caseId", id)).list();
			if (lists.size() > 0) {
				diags = (RevisedDiagnosis) lists.get(0);
			}
			if(diags != null){
				date = diags.getDz_checkdate();
			}
			if (date != null)
				temp = dfs.format(date);
			cellC = new PdfPCell();
			p = new Paragraph("日期:"+temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(4);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			//确定诊断
			if(diags != null){
				boolean flag = false;
				if(!"".equals(diags.getDz_diagnosis1())){
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					p = new Paragraph(diags.getDz_diagnosis1(), fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
					flag = true;
				}
				if(!"".equals(diags.getDz_diagnosis2())){
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					p = new Paragraph(diags.getDz_diagnosis2(), fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
				if(!"".equals(diags.getDz_diagnosis3())){
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					p = new Paragraph(diags.getDz_diagnosis3(), fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
				if(!"".equals(diags.getDz_diagnosis4())){
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					p = new Paragraph(diags.getDz_diagnosis4(), fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
				if(!"".equals(diags.getDz_diagnosis5())){
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					p = new Paragraph(diags.getDz_diagnosis5(), fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
				if(!"".equals(diags.getDz_diagnosis6())){
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					p = new Paragraph(diags.getDz_diagnosis6(), fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
				if(!"".equals(diags.getDz_diagnosis7())){
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					p = new Paragraph(diags.getDz_diagnosis7(), fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
				if(!"".equals(diags.getDz_diagnosis8())){
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					p = new Paragraph(diags.getDz_diagnosis8(), fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
				if(!"".equals(diags.getDz_diagnosis9())){
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					p = new Paragraph(diags.getDz_diagnosis9(), fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
				if(!"".equals(diags.getDz_diagnosis10())){
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					p = new Paragraph(diags.getDz_diagnosis10(), fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
				if(!"".equals(diags.getDz_diagnosis11())){
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					p = new Paragraph(diags.getDz_diagnosis11(), fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
				if(!"".equals(diags.getDz_diagnosis12())){
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					p = new Paragraph(diags.getDz_diagnosis12(), fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
				if(!"".equals(diags.getDz_diagnosis13())){
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					p = new Paragraph(diags.getDz_diagnosis13(), fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
				if(flag){
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					temp = DictionaryUtil.getIndependentDictionaryText(
							"userName", diags.getDz_inhspDoctorId() + "");
					p = new Paragraph("住院医师:"+temp, fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
					
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					temp = DictionaryUtil.getIndependentDictionaryText(
							"userName", diags.getDz_treatDoctorId() + "");
					p = new Paragraph("主治医师:"+temp, fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
					
					this.blankOne(cellC, p, fontGeneral, tableContent);
					cellC = new PdfPCell();
					temp = DictionaryUtil
					.getIndependentDictionaryText("userName", diags
							.getDz_directorDoctorId() + "");
					p = new Paragraph("副主任医师:"+temp, fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(4);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
			}
		}
		
	}
	//空白两列 
	private void blankThree(PdfPCell cellC, Paragraph p,Font fontGeneral,PdfPTable tableContent){
		cellC = new PdfPCell();
		p = new Paragraph("", fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(3);
		cellC.addElement(p);
		tableContent.addCell(cellC);
	}
	private void blankOne(PdfPCell cellC, Paragraph p,Font fontGeneral,PdfPTable tableContent){
		cellC = new PdfPCell();
		p = new Paragraph("", fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
	}
	private void blankTwo(PdfPCell cellC, Paragraph p,Font fontGeneral,PdfPTable tableContent){
		cellC = new PdfPCell();
		p = new Paragraph("", fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(2);
		cellC.addElement(p);
		tableContent.addCell(cellC);
	}
}
