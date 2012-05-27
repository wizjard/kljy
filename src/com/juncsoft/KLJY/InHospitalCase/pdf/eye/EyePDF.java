package com.juncsoft.KLJY.InHospitalCase.pdf.eye;

import java.text.DateFormat;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.juncsoft.KLJY.InHospitalCase.Eye.entity.Eye;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PhysicalExamination;

/**
 * 专科检查中的--眼科检查
 * 
 * @author liugang
 * 
 */
public class EyePDF {
	public void getContent(PdfPTable tableContent, Font fontGeneral,
			Font myFontItem, Long id, Session session, Map map, DateFormat df,
			String filePath) throws Exception {
		Eye fe = null;
		List list = session.createCriteria(Eye.class).add(
				Restrictions.eq("caseId", id)).list();
		if (list.size() > 0) {
			fe = (Eye) list.get(0);
		}
		PdfPCell cellC = new PdfPCell();
		Paragraph p = new Paragraph("专科检查", myFontItem);
		p.setAlignment(Element.ALIGN_CENTER);
		cellC.setColspan(5);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 眼部位置
		cellC = new PdfPCell();
		p = new Paragraph("眼部位置:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 右眼图片
		cellC = new PdfPCell();
		cellC.setColspan(2);
		p = new Paragraph("右眼", fontGeneral);
		p.setIndentationLeft(40);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 左眼图片
		cellC = new PdfPCell();
		cellC.setColspan(2);
		p = new Paragraph("左眼", fontGeneral);
		p.setIndentationLeft(30);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOneT(cellC, p, fontGeneral, tableContent);
		// 右眼图片
		Image logoEye = Image.getInstance(filePath
				+ "module/InHospitalCase/EYE/you_yan.JPG");
		logoEye.scaleAbsolute(100, 70);
		// logoEye.setIndentationLeft(180);
		cellC = new PdfPCell();
		cellC.setColspan(2);
		cellC.setRowspan(2);
		cellC.addElement(logoEye);
		tableContent.addCell(cellC);
		// 左眼图片
		logoEye = Image.getInstance(filePath
				+ "module/InHospitalCase/EYE/zuo_yan.JPG");
		logoEye.scaleAbsolute(100, 70);
		cellC = new PdfPCell();
		logoEye.setIndentationLeft(25);
		cellC.setColspan(2);
		cellC.setRowspan(2);
		cellC.addElement(logoEye);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 右眼描述
		if (!"".equals(fe.getEyeLeftRightContent())
				&& fe.getEyeLeftRightContent() != null) {
			cellC = new PdfPCell();
			cellC.setColspan(2);
			p = new Paragraph(fe.getEyeLeftRightContent(), fontGeneral);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		// 左眼描述
		if (!"".equals(fe.getEyeLeftRightContent2())
				&& fe.getEyeLeftRightContent2() != null) {
			cellC = new PdfPCell();
			cellC.setColspan(2);
			p = new Paragraph(fe.getEyeLeftRightContent2(), fontGeneral);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		// 视功能
		cellC = new PdfPCell();
		p = new Paragraph("视功能:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 远视力R
		if (!"".equals(fe.getSgn_sl_ys()) && fe.getSgn_sl_ys() != null) {
			cellC = new PdfPCell();
			cellC.setColspan(2);
			p = new Paragraph("远视力:" + fe.getSgn_sl_ys(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		// 远视力L
		if (!"".equals(fe.getSgn_sl_ys2()) && fe.getSgn_sl_ys2() != null) {
			cellC = new PdfPCell();
			cellC.setColspan(2);
			p = new Paragraph("远视力:" + fe.getSgn_sl_ys2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 近视力R
		if (!"".equals(fe.getSgn_sl_js()) && fe.getSgn_sl_js() != null) {
			cellC = new PdfPCell();
			cellC.setColspan(2);
			p = new Paragraph("近视力:" + fe.getSgn_sl_js(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		// 近视力L
		if (!"".equals(fe.getSgn_sl_js2()) && fe.getSgn_sl_js2() != null) {
			cellC = new PdfPCell();
			cellC.setColspan(2);
			p = new Paragraph("近视力:" + fe.getSgn_sl_js2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 矫正视力R
		if (!"".equals(fe.getSgn_sl_jz()) && fe.getSgn_sl_jz() != null) {
			cellC = new PdfPCell();
			cellC.setColspan(2);
			p = new Paragraph("矫正视力:" + fe.getSgn_sl_jz(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		// 矫正视力L
		if (!"".equals(fe.getSgn_sl_jz2()) && fe.getSgn_sl_jz2() != null) {
			cellC = new PdfPCell();
			cellC.setColspan(2);
			p = new Paragraph("矫正视力:" + fe.getSgn_sl_jz2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		this.blankOne(cellC, p, fontGeneral, tableContent);
		if (!"".equals(fe.getSgn_sl_jz())
				&& Float.parseFloat(fe.getSgn_sl_jz()) <= 0.02) {
			cellC = new PdfPCell();
			p = new Paragraph("光定位:", fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		// 左眼
		if (!"".equals(fe.getSgn_sl_jz2())
				&& Float.parseFloat(fe.getSgn_sl_jz2()) <= 0.02) {
			cellC = new PdfPCell();
			p = new Paragraph("光定位:", fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		this.blankOne(cellC, p, fontGeneral, tableContent);
		if (!"".equals(fe.getSgn_sl_jz())
				&& Float.parseFloat(fe.getSgn_sl_jz()) <= 0.02) {
			// 光定位R表格
			PdfPTable dw = new PdfPTable(3);
			dw.setSplitLate(false);
			dw.setSplitRows(true);
			// 设置表格宽度100%
			dw.setWidthPercentage(33);
			dw.setWidths(new int[] { 11, 11, 11 });
			// 1
			dw.getDefaultCell().setBorder(0);
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			cellC.setBorder(Rectangle.RIGHT);
			// cellC.setBorder(Rectangle.BOTTOM);
			p = new Paragraph(fe.getSgn_gdwr1(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 2
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			// cellC.setBorder(Rectangle.LEFT);
			cellC.setBorder(Rectangle.RIGHT);
			p = new Paragraph(fe.getSgn_gdwr2(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 3
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			// cellC.setBorder(Rectangle.LEFT);
			cellC.setBorder(Rectangle.BOTTOM);
			p = new Paragraph(fe.getSgn_gdwr3(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 4
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			// cellC.setBorder(Rectangle.RIGHT);
			cellC.setBorder(Rectangle.TOP);
			// cellC.setBorder(Rectangle.BOTTOM);
			p = new Paragraph(fe.getSgn_gdwr4(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 5
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			p = new Paragraph(fe.getSgn_gdwr5(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 6
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			// cellC.setBorder(Rectangle.LEFT);
			// cellC.setBorder(Rectangle.TOP);
			cellC.setBorder(Rectangle.BOTTOM);
			p = new Paragraph(fe.getSgn_gdwr6(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 7
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			// cellC.setBorder(Rectangle.RIGHT);
			cellC.setBorder(Rectangle.TOP);
			p = new Paragraph(fe.getSgn_gdwr7(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 8
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			// cellC.setBorder(Rectangle.RIGHT);
			cellC.setBorder(Rectangle.LEFT);
			// cellC.setBorder(Rectangle.TOP);
			p = new Paragraph(fe.getSgn_gdwr8(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 9
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			cellC.setBorder(Rectangle.LEFT);
			// cellC.setBorder(Rectangle.TOP);
			p = new Paragraph(fe.getSgn_gdwr9(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			cellC = new PdfPCell();
			cellC.setColspan(2);
			cellC.setPaddingLeft(1);
			p = new Paragraph("", fontGeneral);
			p.setAlignment(Element.ALIGN_CENTER);
			p.add(dw);
			cellC.addElement(dw);
			tableContent.addCell(cellC);
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		// 左眼
		if (!"".equals(fe.getSgn_sl_jz2())
				&& Float.parseFloat(fe.getSgn_sl_jz2()) <= 0.02) {
			// 光定位R表格
			PdfPTable dw = new PdfPTable(3);
			dw.setSplitLate(false);
			dw.setSplitRows(true);
			// 设置表格宽度100%
			dw.setWidthPercentage(33);
			dw.setWidths(new int[] { 11, 11, 11 });
			dw.getDefaultCell().setBorder(0);
			// 1
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			cellC.setBorder(Rectangle.RIGHT);
			p = new Paragraph(fe.getSgn_gdwl1(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 2
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			cellC.setBorder(Rectangle.RIGHT);
			p = new Paragraph(fe.getSgn_gdwl2(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 3
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			cellC.setBorder(Rectangle.BOTTOM);
			p = new Paragraph(fe.getSgn_gdwl3(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 4
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			cellC.setBorder(Rectangle.TOP);
			p = new Paragraph(fe.getSgn_gdwl4(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 5
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			p = new Paragraph(fe.getSgn_gdwl5(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 6
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			cellC.setBorder(Rectangle.BOTTOM);
			p = new Paragraph(fe.getSgn_gdwl6(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 7
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			cellC.setBorder(Rectangle.TOP);
			p = new Paragraph(fe.getSgn_gdwl7(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 8
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			cellC.setBorder(Rectangle.LEFT);
			p = new Paragraph(fe.getSgn_gdwl8(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			// 9
			cellC = new PdfPCell();
			cellC.setFixedHeight(25);
			cellC.setBorder(Rectangle.LEFT);
			p = new Paragraph(fe.getSgn_gdwl9(), fontGeneral);
			p.setAlignment(Element.ALIGN_MIDDLE);
			cellC.addElement(p);
			dw.addCell(cellC);
			cellC = new PdfPCell();
			cellC.setColspan(2);
			p = new Paragraph("", fontGeneral);
			p.setAlignment(Element.ALIGN_CENTER);
			p.add(dw);
			cellC.addElement(dw);
			tableContent.addCell(cellC);
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		// 色觉--红
		this.blankOne(cellC, p, fontGeneral, tableContent);
		if (!"".equals(fe.getSgn_sl_jz())
				&& Float.parseFloat(fe.getSgn_sl_jz()) <= 0.02) {
			if (!"".equals(fe.getSgn_gg()) && fe.getSgn_gg() != null) {
				cellC = new PdfPCell();
				p = new Paragraph("色觉:", fontGeneral);
				p.setAlignment(Element.ALIGN_LEFT);
				p.add(" 红: " + fe.getSgn_gg());
				cellC.setColspan(2);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			} else {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		if (!"".equals(fe.getSgn_sl_jz2())
				&& Float.parseFloat(fe.getSgn_sl_jz2()) <= 0.02) {
			if (!"".equals(fe.getSgn_gg2()) && fe.getSgn_gg2() != null) {
				cellC = new PdfPCell();
				p = new Paragraph("色觉:", fontGeneral);
				p.setAlignment(Element.ALIGN_LEFT);
				p.add(" 红: " + fe.getSgn_gg2());
				cellC.setColspan(2);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			} else {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		// 色觉---绿
		this.blankOne(cellC, p, fontGeneral, tableContent);
		if (!"".equals(fe.getSgn_sl_jz())
				&& Float.parseFloat(fe.getSgn_sl_jz()) <= 0.02) {
			if (!"".equals(fe.getSgn_gdw_l()) && fe.getSgn_gdw_l() != null) {
				cellC = new PdfPCell();
				p = new Paragraph("     ", fontGeneral);
				p.setAlignment(Element.ALIGN_LEFT);
				p.add(" 绿: " + fe.getSgn_gdw_l());
				cellC.setColspan(2);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			} else {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		if (!"".equals(fe.getSgn_sl_jz2())
				&& Float.parseFloat(fe.getSgn_sl_jz2()) <= 0.02) {
			if (!"".equals(fe.getSgn_gdw_l2()) && fe.getSgn_gdw_l2() != null) {
				cellC = new PdfPCell();
				p = new Paragraph("     ", fontGeneral);
				p.setAlignment(Element.ALIGN_LEFT);
				p.add(" 绿: " + fe.getSgn_gdw_l2());
				cellC.setColspan(2);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			} else {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		// 视功能描述
		this.blankOne(cellC, p, fontGeneral, tableContent);
		if (!"".equals(fe.getShigongnengRightContent())
				&& fe.getShigongnengRightContent() != null) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getShigongnengRightContent(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		if (!"".equals(fe.getShigongnengLeftContent())
				&& fe.getShigongnengLeftContent() != null) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getShigongnengLeftContent(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		// 眼压
		if (!"".equals(fe.getYy_yanya()) || !"".equals(fe.getYy_yanya2())) {
			cellC = new PdfPCell();
			p = new Paragraph("眼压:", myFontItem);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		}
		if (!"".equals(fe.getYy_yanya())) {
			cellC = new PdfPCell();
			p = new Paragraph("眼压:" + fe.getYy_yanya() + " mmHg", fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			if (!"".equals(fe.getYy_yanya2())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}
		if (!"".equals(fe.getYy_yanya2())) {
			cellC = new PdfPCell();
			p = new Paragraph("眼压:" + fe.getYy_yanya2() + " mmHg", fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			if (!"".equals(fe.getYy_yanya())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}

		// 眼睑
		if (!"".equals(fe.getYs_yanjian()) || !"".equals(fe.getYs_yanjian2())) {
			cellC = new PdfPCell();
			p = new Paragraph("眼睑:", myFontItem);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		}
		String temp = "";
		if (!"".equals(fe.getYs_yanjian())) {
			cellC = new PdfPCell();
			temp = fe.getYs_yanjian();
			if ("自定义".equals(temp)) {
				temp = fe.getYanjian_zidingyi();
			}
			p = new Paragraph("眼睑:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getYs_yanjian2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		if (!"".equals(fe.getYs_yanjian2())) {
			cellC = new PdfPCell();
			temp = fe.getYs_yanjian2();
			if ("自定义".equals(temp)) {
				temp = fe.getYanjian_zidingyi2();
			}
			p = new Paragraph("眼睑:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getYs_yanjian())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 泪器
		if (!"".equals(fe.getLeiqi_chongxi())
				|| !"".equals(fe.getLeiqi_chongxi2())
				|| !"".equals(fe.getLq_fenmiwu())
				|| !"".equals(fe.getLq_fenmiwu2())) {
			cellC = new PdfPCell();
			p = new Paragraph("泪器:", myFontItem);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		}

		// 冲洗
		if (!"".equals(fe.getLq_chongxi())) {
			cellC = new PdfPCell();
			temp = fe.getLq_chongxi();
			if ("自定义".equals(temp)) {
				temp = fe.getLeiqi_chongxi();
			}
			p = new Paragraph("冲洗:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getLq_chongxi2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getLq_chongxi2())) {
			cellC = new PdfPCell();
			temp = fe.getLq_chongxi2();
			if ("自定义".equals(temp)) {
				temp = fe.getLeiqi_chongxi2();
			}
			p = new Paragraph("冲洗:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			if (!"".equals(fe.getLq_chongxi())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}

		if (!"".equals(fe.getLq_fenmiwu()) || !"".equals(fe.getLq_fenmiwu2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}
		// 分泌物
		if (!"".equals(fe.getLq_fenmiwu())) {
			cellC = new PdfPCell();
			temp = fe.getLq_fenmiwu();
			p = new Paragraph("分泌物:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getLq_fenmiwu2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getLq_fenmiwu2())) {
			cellC = new PdfPCell();
			temp = fe.getLq_fenmiwu2();
			p = new Paragraph("分泌物:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getLq_fenmiwu())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getLeiqiRightContent())
				|| !"".equals(fe.getLeiqiLeftContent())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getLeiqiRightContent())) {
			// 分泌物
			cellC = new PdfPCell();
			temp = fe.getLeiqiRightContent();
			p = new Paragraph(temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getLeiqiLeftContent())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getLeiqiLeftContent())) {
			cellC = new PdfPCell();
			temp = fe.getLeiqiLeftContent();
			p = new Paragraph(temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getLeiqiRightContent())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 结膜
		if (!"".equals(fe.getJiemo_jiemo()) || !"".equals(fe.getJiemo_jiemo2())) {
			cellC = new PdfPCell();
			p = new Paragraph("结膜:", myFontItem);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		}

		// 结膜
		if (!"".equals(fe.getJm_jm())) {
			cellC = new PdfPCell();
			temp = fe.getJm_jm();
			if ("自定义".equals(temp)) {
				temp = fe.getJiemo_jiemo();
			}
			p = new Paragraph("结膜:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getJm_jm2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getJm_jm2())) {
			cellC = new PdfPCell();
			temp = fe.getJm_jm2();
			if ("自定义".equals(temp)) {
				temp = fe.getJiemo_jiemo2();
			}
			p = new Paragraph("结膜:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getJm_jm())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 巩膜
		cellC = new PdfPCell();
		p = new Paragraph("巩膜:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		// gm_cbs

		temp = "";
		if (!"".equals(fe.getGm_cbs())) {
			temp = fe.getGm_cbs();
		}
		if (!"".equals(fe.getGm_hr())) {
			if (!"".equals(temp)) {
				temp += "   ";
			}
			temp += fe.getGm_hr();
		}
		if (!"".equals(temp)) {
			temp = temp.replaceAll("<", "").replaceAll(">", "");
		}

		String tempL = "";
		if (!"".equals(fe.getGm_cbs2())) {
			tempL = fe.getGm_cbs2();
		}
		if (!"".equals(fe.getGm_hr2())) {
			if (!"".equals(tempL)) {
				tempL += "   ";
			}
			tempL += fe.getGm_hr2();
		}
		if (!"".equals(tempL)) {
			tempL = tempL.replaceAll("<", "").replaceAll(">", "");
		}

		if (!"".equals(temp)) {
			cellC = new PdfPCell();
			p = new Paragraph(temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(tempL)) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(tempL)) {
			cellC = new PdfPCell();
			p = new Paragraph(tempL, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(temp)) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getGm_ptz()) || !"".equals(fe.getGm_ptz2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		// 葡萄肿
		if (!"".equals(fe.getGm_ptz())) {
			cellC = new PdfPCell();
			p = new Paragraph("葡萄肿:" + fe.getGm_ptz(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getGm_ptz2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getGm_ptz2())) {
			cellC = new PdfPCell();
			p = new Paragraph("葡萄肿:" + fe.getGm_ptz2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getGm_ptz())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getGm_cx()) || !"".equals(fe.getGm_cx2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		// 充血
		if (!"".equals(fe.getGm_cx())) {
			cellC = new PdfPCell();
			p = new Paragraph("充血:" + fe.getGm_cx(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getGm_cx2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getGm_cx2())) {
			cellC = new PdfPCell();
			p = new Paragraph("充血:" + fe.getGm_cx2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getGm_cx())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getGm_jj()) || !"".equals(fe.getGm_jj2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		// 结节
		if (!"".equals(fe.getGm_jj())) {
			cellC = new PdfPCell();
			p = new Paragraph("结节:" + fe.getGm_jj(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getGm_jj2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getGm_jj2())) {
			cellC = new PdfPCell();
			p = new Paragraph("结节:" + fe.getGm_jj2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getGm_jj())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getGm_yt()) || !"".equals(fe.getGm_yt2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		// 压痛
		if (!"".equals(fe.getGm_yt())) {
			cellC = new PdfPCell();
			p = new Paragraph("压痛:" + fe.getGm_yt(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getGm_yt2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getGm_yt2())) {
			cellC = new PdfPCell();
			p = new Paragraph("压痛:" + fe.getGm_yt2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getGm_yt())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getGongmoRightContent())
				|| !"".equals(fe.getGongmoLeftContent())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		// 压痛自述
		if (!"".equals(fe.getGongmoRightContent())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getGongmoRightContent(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getGongmoLeftContent())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getGongmoLeftContent())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getGongmoLeftContent(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getGongmoRightContent())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 角膜
		cellC = new PdfPCell();
		p = new Paragraph("角膜:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		// 角膜
		cellC = new PdfPCell();
		temp = "";
		if (!"".equals(fe.getJm_jm_tm())) {
			temp = fe.getJm_jm_tm();
		}
		if (!"".equals(fe.getJm_jm_hz())) {
			if (!"".equals(temp)) {
				temp += "  ";
			}
			temp += fe.getJm_jm_hz();
		}
		if (!"".equals(fe.getJm_jm_yy())) {
			if (!"".equals(temp)) {
				temp += "  ";
			}
			temp += fe.getJm_jm_yy();
		}
		if (!"".equals(fe.getJm_jm_by())) {
			if (!"".equals(temp)) {
				temp += "  ";
			}
			temp += fe.getJm_jm_by();
		}
		if (!"".equals(fe.getJm_jm_bb())) {
			if (!"".equals(temp)) {
				temp += "  ";
			}
			temp += fe.getJm_jm_bb();
		}
		if (!"".equals(fe.getJm_jm_xsxg())) {
			if (!"".equals(temp)) {
				temp += "  ";
			}
			temp += fe.getJm_jm_xsxg();
		}
		if (!"".equals(temp)) {
			temp = temp.replaceAll("<", "").replaceAll(">", "");
		}

		// 2
		tempL = "";
		if (!"".equals(fe.getJm_jm_tm2())) {
			tempL = fe.getJm_jm_tm2();
		}
		if (!"".equals(fe.getJm_jm_hz2())) {
			if (!"".equals(tempL)) {
				tempL += "  ";
			}
			tempL += fe.getJm_jm_hz2();
		}
		if (!"".equals(fe.getJm_jm_yy2())) {
			if (!"".equals(tempL)) {
				tempL += "  ";
			}
			tempL += fe.getJm_jm_yy2();
		}
		if (!"".equals(fe.getJm_jm_by2())) {
			if (!"".equals(tempL)) {
				tempL += "  ";
			}
			tempL += fe.getJm_jm_by2();
		}
		if (!"".equals(fe.getJm_jm_bb2())) {
			if (!"".equals(tempL)) {
				tempL += "  ";
			}
			tempL += fe.getJm_jm_bb2();
		}
		if (!"".equals(fe.getJm_jm_xsxg2())) {
			if (!"".equals(tempL)) {
				tempL += "  ";
			}
			tempL += fe.getJm_jm_xsxg2();
		}
		if (!"".equals(tempL)) {
			tempL = tempL.replaceAll("<", "").replaceAll(">", "");
		}

		if (!"".equals(temp)) {
			p = new Paragraph(temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(tempL)) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(tempL)) {
			cellC = new PdfPCell();
			p = new Paragraph(tempL, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(temp)) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getJm_kp()) || !"".equals(fe.getJm_kp2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		// KP
		if (!"".equals(fe.getJm_kp())) {
			cellC = new PdfPCell();
			temp = fe.getJm_kp();
			if ("自定义".equals(fe.getJm_kp())) {
				temp = fe.getKpRightContent();
			}
			p = new Paragraph("KP:" + temp + "  " + fe.getJm_kpRight(),
					fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getJm_kp2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// KP
		if (!"".equals(fe.getJm_kp2())) {
			cellC = new PdfPCell();
			tempL = fe.getJm_kp2();
			if ("自定义".equals(fe.getJm_kp2())) {
				tempL = fe.getKpLeftContent();
			}
			p = new Paragraph("KP:" + tempL + "  " + fe.getJm_kpRight2(),
					fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getJm_kp())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		// 角膜自述
		if (!"".equals(fe.getJiaomoRightContent())
				|| !"".equals(fe.getJiaomoLeftContent())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getJiaomoRightContent())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getJiaomoRightContent(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getJiaomoLeftContent())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getJiaomoLeftContent())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getJiaomoLeftContent(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getJiaomoRightContent())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 前房
		cellC = new PdfPCell();
		p = new Paragraph("前房:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 中央前房

		if (!"".equals(fe.getQf_zyqf()) || !"".equals(fe.getQf_zyqf2())) {
			cellC = new PdfPCell();
			temp = fe.getQf_zyqf();
			if ("自定义".equals(fe.getQf_zyqf())) {
				temp = fe.getQianfang_zhongyangqianfang();
			}
			p = new Paragraph("中央前房:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getQf_zyqf2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getQf_zyqf2())) {
			cellC = new PdfPCell();
			temp = fe.getQf_zyqf2();
			if ("自定义".equals(fe.getQf_zyqf2())) {
				temp = fe.getQianfang_zhongyangqianfang2();
			}
			p = new Paragraph("中央前房:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getQf_zyqf())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 周边前房
		if (!"".equals(fe.getQf_zbqf()) || !"".equals(fe.getQf_zbqf2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getQf_zbqf())) {
			cellC = new PdfPCell();
			p = new Paragraph("周边前房:" + fe.getQf_zbqf(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getQf_zbqf2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getQf_zbqf2())) {
			cellC = new PdfPCell();
			p = new Paragraph("周边前房:" + fe.getQf_zbqf2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getQf_zbqf())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 房水闪光
		if (!"".equals(fe.getQf_fssg()) || !"".equals(fe.getQf_fssg2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getQf_fssg())) {
			cellC = new PdfPCell();
			p = new Paragraph("房水闪光:" + fe.getQf_fssg(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getQf_fssg2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getQf_fssg2())) {
			cellC = new PdfPCell();
			p = new Paragraph("房水闪光:" + fe.getQf_fssg2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getQf_fssg())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 积脓
		if (!"".equals(fe.getQf_jl()) || !"".equals(fe.getQf_jl2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getQf_jl())) {
			cellC = new PdfPCell();
			p = new Paragraph("积脓:" + fe.getQf_jl(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getQf_jl2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getQf_jl2())) {
			cellC = new PdfPCell();
			p = new Paragraph("积脓:" + fe.getQf_jl2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getQf_jl())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 积血
		if (!"".equals(fe.getQf_jx()) || !"".equals(fe.getQf_jx2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getQf_jx())) {
			cellC = new PdfPCell();
			p = new Paragraph("积血:" + fe.getQf_jx(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getQf_jx2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getQf_jx2())) {
			cellC = new PdfPCell();
			p = new Paragraph("积血:" + fe.getQf_jx2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getQf_jx())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 自述
		if (!"".equals(fe.getJixueRightContent())
				|| !"".equals(fe.getJixueLeftContent())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getJixueRightContent())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getJixueRightContent(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getJixueLeftContent())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getJixueLeftContent())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getJixueLeftContent(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getJixueRightContent())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 虹膜
		cellC = new PdfPCell();
		p = new Paragraph("虹膜:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);

		// 纹理
		if (!"".equals(fe.getHm_wl())) {
			cellC = new PdfPCell();
			temp = fe.getHm_wl();
			if ("自定义".equals(temp)) {
				temp = fe.getHongmo_wenli();
			}
			p = new Paragraph("纹理:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getHm_wl2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getHm_wl2())) {
			cellC = new PdfPCell();
			temp = fe.getHm_wl2();
			if ("自定义".equals(temp)) {
				temp = fe.getHongmo_wenli2();
			}
			p = new Paragraph("纹理:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getHm_wl())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 萎缩
		if (!"".equals(fe.getHm_ws()) || !"".equals(fe.getHm_ws2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
			if (!"".equals(fe.getHm_ws())) {
				cellC = new PdfPCell();
				p = new Paragraph("萎缩:" + fe.getHm_ws(), fontGeneral);
				p.setAlignment(Element.ALIGN_LEFT);
				cellC.setColspan(2);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			} else if (!"".equals(fe.getHm_ws2())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}

			if (!"".equals(fe.getHm_ws2())) {
				cellC = new PdfPCell();
				p = new Paragraph("萎缩:" + fe.getHm_ws2(), fontGeneral);
				p.setAlignment(Element.ALIGN_LEFT);
				cellC.setColspan(2);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			} else if (!"".equals(fe.getHm_ws())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}


		// 新生血管
		if (!"".equals(fe.getHm_xsxg()) || !"".equals(fe.getHm_xsxg2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
			if (!"".equals(fe.getHm_xsxg())) {
				cellC = new PdfPCell();
				p = new Paragraph("新生血管:" + fe.getHm_xsxg(), fontGeneral);
				p.setAlignment(Element.ALIGN_LEFT);
				cellC.setColspan(2);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			} else if (!"".equals(fe.getHm_xsxg2())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}

			if (!"".equals(fe.getHm_xsxg2())) {
				cellC = new PdfPCell();
				p = new Paragraph("新生血管:" + fe.getHm_xsxg2(), fontGeneral);
				p.setAlignment(Element.ALIGN_LEFT);
				cellC.setColspan(2);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			} else if (!"".equals(fe.getHm_xsxg())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}
		

		// 缺损
		if (!"".equals(fe.getHm_qs()) || !"".equals(fe.getHm_qs2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
			if (!"".equals(fe.getHm_qs())) {
				cellC = new PdfPCell();
				p = new Paragraph("缺损:" + fe.getHm_qs(), fontGeneral);
				p.setAlignment(Element.ALIGN_LEFT);
				cellC.setColspan(2);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			} else if (!"".equals(fe.getHm_qs2())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}

			if (!"".equals(fe.getHm_qs2())) {
				cellC = new PdfPCell();
				p = new Paragraph("缺损:" + fe.getHm_qs2(), fontGeneral);
				p.setAlignment(Element.ALIGN_LEFT);
				cellC.setColspan(2);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			} else if (!"".equals(fe.getHm_qs())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}


		// 粘连
		if (!"".equals(fe.getHm_ln()) || !"".equals(fe.getHm_ln2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
			if (!"".equals(fe.getHm_ln())) {
				cellC = new PdfPCell();
				p = new Paragraph("粘连:" + fe.getHm_ln(), fontGeneral);
				p.setAlignment(Element.ALIGN_LEFT);
				cellC.setColspan(2);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			} else if(!"".equals(fe.getHm_ln2())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
			
			if (!"".equals(fe.getHm_ln2())) {
				cellC = new PdfPCell();
				p = new Paragraph("粘连:" + fe.getHm_ln2(), fontGeneral);
				p.setAlignment(Element.ALIGN_LEFT);
				cellC.setColspan(2);
				cellC.addElement(p);
				tableContent.addCell(cellC);
			} else if (!"".equals(fe.getHm_ln())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);

			}
		}
		

		// 粘连位置
		if (!"".equals(fe.getHm_lnwz()) || !"".equals(fe.getHm_lnwz2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
			if ("(+)".equals(fe.getHm_ln())) {
				if(!"".equals(fe.getHm_lnwz())){
					cellC = new PdfPCell();
					p = new Paragraph("粘连位置:" + fe.getHm_lnwz(), fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(2);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
			} else if ("(+)".equals(fe.getHm_ln2())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}

			if ("(+)".equals(fe.getHm_ln2())) {
				if(!"".equals(fe.getHm_lnwz2())){
					cellC = new PdfPCell();
					p = new Paragraph("粘连位置:" + fe.getHm_lnwz2(), fontGeneral);
					p.setAlignment(Element.ALIGN_LEFT);
					cellC.setColspan(2);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
			} else if ("(+)".equals(fe.getHm_ln())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}


		// 自述

		if (!"".equals(fe.getZhanlianRightContent())
				|| !"".equals(fe.getZhanlianLeftContent())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getZhanlianRightContent())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getZhanlianRightContent(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			if (!"".equals(fe.getZhanlianLeftContent())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}

		if (!"".equals(fe.getZhanlianLeftContent())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getZhanlianLeftContent(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			if (!"".equals(fe.getZhanlianRightContent())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}

		// 瞳孔
		cellC = new PdfPCell();
		p = new Paragraph("瞳孔:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 瞳孔
		cellC = new PdfPCell();
		temp = "";
		if (!"".equals(fe.getTk_tk_y())) {
			temp = fe.getTk_tk_y();
		}
		if (!"".equals(fe.getTk_tk_ywxsx())) {
			if (!"".equals(temp)) {
				temp += "  ";
			}
			temp += fe.getTk_tk_ywxsx();
		}
		if (!"".equals(fe.getTk_tk_by())) {
			if (!"".equals(temp)) {
				temp += "  ";
			}
			temp += fe.getTk_tk_by();
		}
		if (!"".equals(fe.getTk_tk_ywxkd())) {
			if (!"".equals(temp)) {
				temp += "  ";
			}
			temp += fe.getTk_tk_ywxkd();
		}
		if (!"".equals(temp)) {
			temp = temp.replaceAll("<", "").replaceAll(">", "");
		}

		tempL = "";
		if (!"".equals(fe.getTk_tk_y2())) {
			tempL = fe.getTk_tk_y2();
		}
		if (!"".equals(fe.getTk_tk_ywxsx2())) {
			if (!"".equals(tempL)) {
				tempL += "  ";
			}
			tempL += fe.getTk_tk_ywxsx2();
		}
		if (!"".equals(fe.getTk_tk_by2())) {
			if (!"".equals(tempL)) {
				temp += "  ";
			}
			tempL += fe.getTk_tk_by2();
		}
		if (!"".equals(fe.getTk_tk_ywxkd2())) {
			if (!"".equals(tempL)) {
				tempL += "  ";
			}
			tempL += fe.getTk_tk_ywxkd2();
		}
		if (!"".equals(tempL)) {
			tempL = tempL.replaceAll("<", "").replaceAll(">", "");
		}

		if (!"".equals(temp) || !"".equals(tempL)) {
			p = new Paragraph(temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			if (!"".equals(tempL)) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}

		if (!"".equals(tempL)) {
			cellC = new PdfPCell();
			p = new Paragraph(tempL, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			if (!"".equals(temp)) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}

		// 直径
		if (!"".equals(fe.getTk_zj()) || !"".equals(fe.getTk_zj2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getTk_zj())) {
			cellC = new PdfPCell();
			p = new Paragraph("直径:" + fe.getTk_zj() + " mm", fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			if (!"".equals(fe.getTk_zj2())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}

		if (!"".equals(fe.getTk_zj2())) {
			cellC = new PdfPCell();
			p = new Paragraph("直径:" + fe.getTk_zj2() + " mm", fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			if (!"".equals(fe.getTk_zj())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}

		// 粘连
		if (!"".equals(fe.getTk_nl()) || !"".equals(fe.getTk_nl2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getTk_nl())) {
			cellC = new PdfPCell();
			temp = fe.getTk_nl();
			if ("自定义".equals(temp)) {
				temp = fe.getTongkong_zhanlian();
			}
			p = new Paragraph("粘连:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			if (!"".equals(fe.getTk_nl2())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}

		if (!"".equals(fe.getTk_nl2())) {
			cellC = new PdfPCell();
			temp = fe.getTk_nl2();
			if ("自定义".equals(temp)) {
				temp = fe.getTongkong_zhanlian2();
			}
			p = new Paragraph("粘连:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			if (!"".equals(fe.getTk_nl())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}

		// 位置
		if (!"".equals(fe.getTk_wz()) || !"".equals(fe.getTk_wz2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getTk_wz())) {
			cellC = new PdfPCell();
			p = new Paragraph("位置:" + fe.getTk_wz(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			if (!"".equals(fe.getTk_wz2())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}

		if (!"".equals(fe.getTk_wz2())) {
			cellC = new PdfPCell();
			p = new Paragraph("位置:" + fe.getTk_wz2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			if (!"".equals(fe.getTk_wz())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}

		// 移位
		if (!"".equals(fe.getTk_yw()) || !"".equals(fe.getTk_yw2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getTk_yw())) {
			cellC = new PdfPCell();
			temp = fe.getTk_yw();
			if ("自定义".equals(temp)) {
				temp = fe.getTongkong_yiwei();
			}
			p = new Paragraph("移位:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			if (!"".equals(fe.getTk_yw2())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}

		if (!"".equals(fe.getTk_yw2())) {
			cellC = new PdfPCell();
			temp = fe.getTk_yw2();
			if ("自定义".equals(temp)) {
				temp = fe.getTongkong_yiwei2();
			}
			p = new Paragraph("移位:" + temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else {
			if (!"".equals(fe.getTk_yw())) {
				this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
			}
		}

		// 直接光反射
		if (!"".equals(fe.getTk_zjdgfs()) || !"".equals(fe.getTk_zjdgfs2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getTk_zjdgfs())) {
			cellC = new PdfPCell();
			p = new Paragraph("直接光反射:" + fe.getTk_zjdgfs(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getTk_zjdgfs2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getTk_zjdgfs2())) {
			cellC = new PdfPCell();
			temp = fe.getTk_zjdgfs2();
			p = new Paragraph("直接光反射:" + fe.getTk_zjdgfs2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getTk_zjdgfs())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 间接光反射
		if (!"".equals(fe.getTk_jjdgfs()) || !"".equals(fe.getTk_jjdgfs2())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getTk_jjdgfs())) {
			cellC = new PdfPCell();
			p = new Paragraph("间接光反射:" + fe.getTk_jjdgfs(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getTk_jjdgfs2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getTk_jjdgfs2())) {
			cellC = new PdfPCell();
			p = new Paragraph("间接光反射:" + fe.getTk_jjdgfs2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getTk_jjdgfs())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 自述
		if (!"".equals(fe.getTongkongRightContent())
				|| !"".equals(fe.getTongkongLeftContent())) {
			this.blankOne(cellC, p, fontGeneral, tableContent);
		}
		if (!"".equals(fe.getTongkongRightContent())) {
			cellC = new PdfPCell();
			p = new Paragraph("" + fe.getTongkongRightContent(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getTongkongLeftContent())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getTongkongLeftContent())) {
			cellC = new PdfPCell();
			p = new Paragraph("" + fe.getTongkongLeftContent(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getTongkongRightContent())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 晶状体

		// temp = "";
		// if (!"".equals(fe.getJzt_zrtkxjc())) {
		// temp = fe.getJzt_zrtkxjc();
		// }
		// if (!"".equals(fe.getJzt_stxjc())) {
		// if (!"".equals(temp)) {
		// temp += " ";
		// }
		// temp += fe.getJzt_stxjc();
		// }
		// if (!"".equals(temp)) {
		// temp = temp.replaceAll("<", "").replaceAll(">", "");
		// }
		// if (!"".equals(temp)) {
		// cellC = new PdfPCell();
		// p = new Paragraph(temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// } else if (!"".equals(tempL)) {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// tempL = "";
		// if (!"".equals(fe.getJzt_zrtkxjc2())) {
		// tempL = fe.getJzt_zrtkxjc2();
		// }
		// if (!"".equals(fe.getJzt_stxjc2())) {
		// if (!"".equals(tempL)) {
		// tempL += " ";
		// }
		// tempL += fe.getJzt_stxjc2();
		// }
		// if (!"".equals(tempL)) {
		// tempL = tempL.replaceAll("<", "").replaceAll(">", "");
		// }
		// if (!"".equals(tempL)) {
		// cellC = new PdfPCell();
		// p = new Paragraph(tempL, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// } else if (!"".equals(temp)) {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// // 性状
		// if (null != fe.getJzt_xz_hz() || null != fe.getJzt_xz_hz2()) {
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//
		// if (null != fe.getJzt_xz_hz()) {
		// cellC = new PdfPCell();
		// p = new Paragraph("性状:" + fe.getJzt_xz_hz(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// } else if (null != fe.getJzt_xz_hz2()) {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// if (null != fe.getJzt_xz_hz2()) {
		// cellC = new PdfPCell();
		// p = new Paragraph("性状:" + fe.getJzt_xz_hz2(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// } else if (null != fe.getJzt_xz_hz()) {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// // 晶状体脱位
		// if (!"".equals(fe.getJzt_jzttw()) || !"".equals(fe.getJzt_jzttw2()))
		// {
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		// if (!"".equals(fe.getJzt_jzttw())) {
		// if (!"".equals(fe.getJzt_xz_hz())
		// && !"透明".equals(fe.getJzt_xz_hz())) {
		// cellC = new PdfPCell();
		// temp = fe.getJzt_jzttw();
		// if ("自定义".equals(temp)) {
		// temp = fe.getZh_jzttw();
		// }
		// p = new Paragraph("晶状体脱位:" + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		// } else if (!"".equals(fe.getJzt_jzttw2())) {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// if (!"".equals(fe.getJzt_jzttw2())) {
		// if (!"".equals(fe.getJzt_xz_hz2())
		// && !"透明".equals(fe.getJzt_xz_hz2())) {
		// cellC = new PdfPCell();
		// temp = fe.getJzt_jzttw2();
		// if ("自定义".equals(temp)) {
		// temp = fe.getZh_jzttw2();
		// }
		// p = new Paragraph("晶状体脱位:" + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		// } else if (!"".equals(fe.getJzt_jzttw())) {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// // 核硬度分级
		// if (!"".equals(fe.getJzt_hydfj()) || !"".equals(fe.getJzt_hydfj2()))
		// {
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//
		// if (!"".equals(fe.getJzt_hydfj())) {
		// cellC = new PdfPCell();
		// p = new Paragraph("核硬度分级:" + fe.getJzt_hydfj(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// } else if (!"".equals(fe.getJzt_hydfj2())) {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// if (!"".equals(fe.getJzt_hydfj2())) {
		// cellC = new PdfPCell();
		// p = new Paragraph("核硬度分级:" + fe.getJzt_hydfj2(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// } else if (!"".equals(fe.getJzt_hydfj())) {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// // 后囊混浊
		// if (!"".equals(fe.getJzt_hlhz()) || !"".equals(fe.getJzt_hlhz2())) {
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//
		// if (!"".equals(fe.getJzt_hlhz())) {
		// cellC = new PdfPCell();
		// temp = fe.getJzt_hlhz();
		// if ("自定义".equals(temp)) {
		// temp = fe.getJzt_hlhz();
		// }
		// p = new Paragraph("后囊混浊:" + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// } else if (!"".equals(fe.getJzt_hlhz2())) {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// if (!"".equals(fe.getJzt_hlhz2())) {
		// cellC = new PdfPCell();
		// temp = fe.getJzt_hlhz2();
		// if ("自定义".equals(temp)) {
		// temp = fe.getJzt_hlhz2();
		// }
		// p = new Paragraph("后囊混浊:" + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// } else if (!"".equals(fe.getJzt_hlhz())) {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// // 皮质水隙样改变
		// if (!"".equals(fe.getJzt_bzsxygb()) ||
		// !"".equals(fe.getJzt_bzsxygb2())) {
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		// if (!"".equals(fe.getJzt_bzsxygb())) {
		// cellC = new PdfPCell();
		// temp = fe.getJzt_bzsxygb();
		// if ("自定义".equals(temp)) {
		// temp = fe.getJzt_bzsxygb();
		// }
		// p = new Paragraph("皮质水隙样改变:" + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// } else if (!"".equals(fe.getJzt_bzsxygb2())) {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// if (!"".equals(fe.getJzt_bzsxygb2())) {
		// cellC = new PdfPCell();
		// temp = fe.getJzt_bzsxygb2();
		// if ("自定义".equals(temp)) {
		// temp = fe.getJzt_bzsxygb2();
		// }
		// p = new Paragraph("皮质水隙样改变:" + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// } else if (!"".equals(fe.getJzt_bzsxygb())) {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// // 自述
		// if (!"".equals(fe.getJinzhuangtaiRightContent())
		// || !"".equals(fe.getJinzhuangtaiLeftContent())) {
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		// if (!"".equals(fe.getJinzhuangtaiRightContent())) {
		// cellC = new PdfPCell();
		// temp = fe.getJinzhuangtaiRightContent();
		// p = new Paragraph(temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// } else if (!"".equals(fe.getJinzhuangtaiLeftContent())) {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// if (!"".equals(fe.getJinzhuangtaiLeftContent())) {
		// cellC = new PdfPCell();
		// temp = fe.getJinzhuangtaiLeftContent();
		// p = new Paragraph(temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// } else if (!"".equals(fe.getJinzhuangtaiRightContent())) {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		// 晶状体
		if (!"".equals(fe.getJingzhuangtiRight())
				|| !"".equals(fe.getJingzhuangtiLeft())) {
			cellC = new PdfPCell();
			p = new Paragraph("晶状体:", myFontItem);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		}
		if (!"".equals(fe.getJingzhuangtiRight())) {
			cellC = new PdfPCell();
			temp = fe.getJingzhuangtiRight();
			p = new Paragraph(temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getJingzhuangtiLeft())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getJingzhuangtiLeft())) {
			cellC = new PdfPCell();
			temp = fe.getJingzhuangtiLeft();
			p = new Paragraph(temp, fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getJingzhuangtiRight())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 眼球
		if (!"".equals(fe.getYanqiu()) || !"".equals(fe.getYanqiu2())) {
			cellC = new PdfPCell();
			p = new Paragraph("眼球:", myFontItem);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		}

		if (!"".equals(fe.getYanqiu())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getYanqiu(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getYanqiu2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getYanqiu2())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getYanqiu2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getYanqiu())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 眼球运动
		if (!"".equals(fe.getYanqiuyd()) || !"".equals(fe.getYanqiuyd2())) {
			cellC = new PdfPCell();
			p = new Paragraph("眼球运动:", myFontItem);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		}

		if (!"".equals(fe.getYanqiuyd())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getYanqiuyd(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getYanqiuyd2())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getYanqiuyd2())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getYanqiuyd2(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getYanqiuyd())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 性状
		// if (!"".equals(fe.getBlt_hz())){
		// cellC = new PdfPCell();
		// p = new Paragraph("性状:" + fe.getBlt_hz(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getBlt_hz2())){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getBlt_hz2())){
		// cellC = new PdfPCell();
		// p = new Paragraph("性状:" + fe.getBlt_hz2(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getBlt_hz())){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		//
		// // 后脱离
		// if(!"".equals(fe.getBlt_htl()) || !"".equals(fe.getBlt_htl2())){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getBlt_htl())){
		// if (!"".equals(fe.getBlt_hz()) && !"清亮".equals(fe.getBlt_hz())) {
		// cellC = new PdfPCell();
		// temp = fe.getBlt_htl();
		// p = new Paragraph("后脱离:" + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }
		// }else if(!"".equals(fe.getBlt_htl2())){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getBlt_htl2())){
		// if (!"".equals(fe.getBlt_hz2()) && !"清亮".equals(fe.getBlt_hz2())) {
		// cellC = new PdfPCell();
		// temp = fe.getBlt_htl2();
		// p = new Paragraph("后脱离:" + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }
		// }else if(!"".equals(fe.getBlt_htl())){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// // 自述
		// if(!"".equals(fe.getBolitiRightContent()) ||
		// !"".equals(fe.getBolitiLeftContent())){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }

		// 玻璃体
		if ((!"".equals(fe.getBolitiRight()))
				|| (!"".equals(fe.getBolitiLeft()))) {
			cellC = new PdfPCell();
			p = new Paragraph("玻璃体:", myFontItem);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		}
		if (!"".equals(fe.getBolitiRight())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getBolitiRight(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getBolitiLeft())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getBolitiLeft())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getBolitiLeft(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getBolitiRight())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		// 眼底
		if (!"".equals(fe.getYandiRight()) || !"".equals(fe.getYandiLeft())) {
			cellC = new PdfPCell();
			p = new Paragraph("眼底:", myFontItem);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		}
		if (!"".equals(fe.getYandiRight())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getYandiRight(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getYandiLeft())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}

		if (!"".equals(fe.getYandiLeft())) {
			cellC = new PdfPCell();
			p = new Paragraph(fe.getYandiLeft(), fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(2);
			cellC.addElement(p);
			tableContent.addCell(cellC);
		} else if (!"".equals(fe.getYandiRight())) {
			this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		}
		// // 1
		// if(!"".equals(fe.getYd_zrst())){
		// cellC = new PdfPCell();
		// p = new Paragraph(fe.getYd_zrst(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_zrst2())){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getYd_zrst2())){
		// cellC = new PdfPCell();
		// p = new Paragraph(fe.getYd_zrst2(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_zrst())){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// // 2
		//		
		// String tempRight = "";
		// if (!"".equals(fe.getYd_qx())) {
		// tempRight = fe.getYd_qx();
		// }
		// if (!"".equals(fe.getYd_mhkj())) {
		// if (!"".equals(tempRight)) {
		// tempRight += " ";
		// }
		// tempRight += fe.getYd_mhkj();
		// }
		// if (!"".equals(fe.getYd_br())) {
		// if (!"".equals(tempRight)) {
		// tempRight += " ";
		// }
		// tempRight += fe.getYd_br();
		// }
		// if (!"".equals(tempRight)) {
		// tempRight = tempRight.replaceAll("<", "").replaceAll(">", "");
		// }
		//		
		// String tempLeft = "";
		// if (!"".equals(fe.getYd_qx2())) {
		// tempLeft = fe.getYd_qx2();
		// }
		// if (!"".equals(fe.getYd_mhkj2())) {
		// if (!"".equals(tempLeft)) {
		// tempLeft += " ";
		// }
		// tempLeft += fe.getYd_mhkj2();
		// }
		// if (!"".equals(fe.getYd_br2())) {
		// if (!"".equals(tempLeft)) {
		// tempLeft += " ";
		// }
		// tempLeft += fe.getYd_br2();
		// }
		// if (!"".equals(tempLeft)) {
		// tempLeft = tempLeft.replaceAll("<", "").replaceAll(">", "");
		// }
		//		
		// if(!"".equals(tempRight) || !"".equals(tempLeft)){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(tempRight)){
		// cellC = new PdfPCell();
		// p = new Paragraph(tempRight, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(tempLeft)){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(tempLeft)){
		// cellC = new PdfPCell();
		// p = new Paragraph(tempLeft, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(tempRight)){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		//
		// // 视盘
		// if((!"".equals(fe.getYd_sp()) && tempRight.indexOf("不入") == -1) ||
		// (!"".equals(fe.getYd_sp2()) && tempLeft.indexOf("不入") == -1)){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getYd_sp()) && tempRight.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// temp = fe.getYd_sp();
		// if ("自定义".equals(temp)) {
		// temp = fe.getYd_shipan();
		// }
		// p = new Paragraph("视盘:" + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_sp2()) && tempLeft.indexOf("不入") == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getYd_sp2()) && tempLeft.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// temp = fe.getYd_sp2();
		// if ("自定义".equals(temp)) {
		// temp = fe.getYd_shipan2();
		// }
		// p = new Paragraph("视盘:" + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_sp()) && tempRight.indexOf("不入") == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		//
		// // C/D
		// if((!"".equals(fe.getYd_cd()) && tempRight.indexOf("不入") == -1) ||
		// (!"".equals(fe.getYd_cd2()) && tempLeft.indexOf("不入") == -1)){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getYd_cd()) && tempRight.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// p = new Paragraph("C/D=" + fe.getYd_cd(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_cd2()) && tempLeft.indexOf("不入") == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getYd_cd2()) && tempLeft.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// p = new Paragraph("C/D=" + fe.getYd_cd2(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_cd()) && tempRight.indexOf("不入") == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		//
		// // A/V
		// if((!"".equals(fe.getYd_swmxg_AV()) && tempRight.indexOf("不入") == -1)
		// || (!"".equals(fe.getYd_swmxg_AV2()) && tempLeft.indexOf("不入") ==
		// -1)){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getYd_swmxg_AV()) && tempRight.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// p = new Paragraph("A/V=" + fe.getYd_swmxg_AV(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_swmxg_AV2()) && tempLeft.indexOf("不入")
		// == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getYd_swmxg_AV2()) && tempLeft.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// p = new Paragraph("A/V=" + fe.getYd_swmxg_AV2(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_swmxg_AV()) && tempRight.indexOf("不入")
		// == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		//
		// // 视网膜血管
		//		
		//		
		// temp = "";
		// if (!"".equals(fe.getYd_swmxg_jc_kjjcyb())) {
		// temp = fe.getYd_swmxg_jc_kjjcyb();
		// }
		// if (!"".equals(fe.getYd_swmxg_jc_fgdzk())) {
		// if (!"".equals(temp)) {
		// temp += "，";
		// }
		// temp += fe.getYd_swmxg_jc_fgdzk();
		// }
		// if (!"".equals(fe.getYd_swmxg_jc_fgzq())) {
		// if (!"".equals(temp)) {
		// temp += "，";
		// }
		// temp += fe.getYd_swmxg_jc_fgzq();
		// }
		// if (!"".equals(fe.getYd_swmxg_jc_tsz())) {
		// if (!"".equals(temp)) {
		// temp += "，";
		// }
		// temp += fe.getYd_swmxg_jc_tsz();
		// }
		// if (!"".equals(fe.getYd_swmxg_jc_jmyq())) {
		// if (!"".equals(temp)) {
		// temp += "，";
		// }
		// temp += fe.getYd_swmxg_jc_jmyq();
		// }
		// if (!"".equals(fe.getYd_swmxg_jc_jmkj())) {
		// if (!"".equals(temp)) {
		// temp += "，";
		// }
		// temp += fe.getYd_swmxg_jc_jmkj();
		// }
		// if (!"".equals(fe.getYd_swmxg_jc_ydpz())) {
		// if (!"".equals(temp)) {
		// temp += "，";
		// }
		// temp += fe.getYd_swmxg_jc_ydpz();
		// }
		// if (!"".equals(fe.getYd_swmxg_jc_bycsx())) {
		// if (!"".equals(temp)) {
		// temp += "，";
		// }
		// temp += fe.getYd_swmxg_jc_bycsx();
		// }
		// if (!"".equals(fe.getYd_swmxg_qt())) {
		// if (!"".equals(temp)) {
		// temp += "，";
		// }
		// temp += fe.getYd_swmxg_qt();
		// }
		// if (!"".equals(temp)) {
		// temp = temp.replaceAll("<", "").replaceAll(">", "");
		// }
		//
		// tempL = "";
		// if (!"".equals(fe.getYd_swmxg_jc_kjjcyb2())) {
		// tempL = fe.getYd_swmxg_jc_kjjcyb2();
		// }
		// if (!"".equals(fe.getYd_swmxg_jc_fgdzk2())) {
		// if (!"".equals(tempL)) {
		// tempL += "，";
		// }
		// tempL += fe.getYd_swmxg_jc_fgdzk2();
		// }
		// if (!"".equals(fe.getYd_swmxg_jc_fgzq2())) {
		// if (!"".equals(tempL)) {
		// tempL += "，";
		// }
		// tempL += fe.getYd_swmxg_jc_fgzq2();
		// }
		// if (!"".equals(fe.getYd_swmxg_jc_tsz2())) {
		// if (!"".equals(tempL)) {
		// tempL += "，";
		// }
		// tempL += fe.getYd_swmxg_jc_tsz2();
		// }
		// if (!"".equals(fe.getYd_swmxg_jc_jmyq2())) {
		// if (!"".equals(tempL)) {
		// tempL += "，";
		// }
		// tempL += fe.getYd_swmxg_jc_jmyq2();
		// }
		// if (!"".equals(fe.getYd_swmxg_jc_jmkj2())) {
		// if (!"".equals(tempL)) {
		// tempL += "，";
		// }
		// tempL += fe.getYd_swmxg_jc_jmkj2();
		// }
		// if (!"".equals(fe.getYd_swmxg_jc_ydpz2())) {
		// if (!"".equals(tempL)) {
		// tempL += "，";
		// }
		// tempL += fe.getYd_swmxg_jc_ydpz2();
		// }
		// if (!"".equals(fe.getYd_swmxg_jc_bycsx2())) {
		// if (!"".equals(tempL)) {
		// tempL += "，";
		// }
		// tempL += fe.getYd_swmxg_jc_bycsx2();
		// }
		// if (!"".equals(fe.getYd_swmxg_qt2())) {
		// if (!"".equals(tempL)) {
		// tempL += "，";
		// }
		// tempL += fe.getYd_swmxg_qt2();
		// }
		// if (!"".equals(tempL)) {
		// tempL = tempL.replaceAll("<", "").replaceAll(">", "");
		// }
		//		
		// if((!"".equals(temp) && tempRight.indexOf("不入") == -1) ||
		// (!"".equals(tempL) && tempLeft.indexOf("不入") == -1)){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(temp) && tempRight.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// p = new Paragraph("视网膜血管:" + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(tempL) && tempLeft.indexOf("不入") == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(tempL) && tempLeft.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// p = new Paragraph("视网膜血管:" + tempL, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(temp) && tempRight.indexOf("不入") == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		//		
		//		
		//
		// // 视网膜
		// // 1
		// temp = "";
		// if (!"".equals(fe.getYd_swm_cx())) {
		// temp = fe.getYd_swm_cx();
		// }
		// if (!"".equals(fe.getYd_swm_sc())) {
		// if (!"".equals(temp)) {
		// temp += "，";
		// }
		// temp += fe.getYd_swm_sc();
		// }
		// if (!"".equals(fe.getYd_swm_qm())) {
		// if (!"".equals(temp)) {
		// temp += "，";
		// }
		// temp += fe.getYd_swm_qm();
		// }
		// if (!"".equals(temp)) {
		// temp = temp.replaceAll("<", "").replaceAll(">", "");
		// }
		//		
		// tempL = "";
		// if (!"".equals(fe.getYd_swm_cx2())) {
		// tempL = fe.getYd_swm_cx2();
		// }
		// if (!"".equals(fe.getYd_swm_sc2())) {
		// if (!"".equals(tempL)) {
		// tempL += "，";
		// }
		// tempL += fe.getYd_swm_sc2();
		// }
		// if (!"".equals(fe.getYd_swm_qm2())) {
		// if (!"".equals(tempL)) {
		// tempL += "，";
		// }
		// tempL += fe.getYd_swm_qm2();
		// }
		// if (!"".equals(tempL)) {
		// tempL = tempL.replaceAll("<", "").replaceAll(">", "");
		// }
		//		
		//		
		// if(tempRight.indexOf("不入") == -1 || tempLeft.indexOf("不入") == -1){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(tempRight.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// p = new Paragraph("视网膜:" + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }
		//		
		// // 2
		// if(tempLeft.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// p = new Paragraph("视网膜:" + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }
		//		
		// // 脱离范围
		// if((!"".equals(fe.getYd_swm_tl_fw()) && tempRight.indexOf("不入") ==
		// -1) || (!"".equals(fe.getYd_swm_tl_fw2()) && tempLeft.indexOf("不入")
		// == -1)){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getYd_swm_tl_fw()) && tempRight.indexOf("不入") ==
		// -1){
		// cellC = new PdfPCell();
		// p = new Paragraph(" 脱离范围:" + fe.getYd_swm_tl_fw(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_swm_tl_fw2()) && tempLeft.indexOf("不入")
		// == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getYd_swm_tl_fw2()) && tempLeft.indexOf("不入") ==
		// -1){
		// cellC = new PdfPCell();
		// p = new Paragraph(" 脱离范围:" + fe.getYd_swm_tl_fw2(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_swm_tl_fw()) && tempRight.indexOf("不入")
		// == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		//
		// // 脱离活动度
		// if((!"".equals(fe.getYd_swm_tl_hdd()) && tempRight.indexOf("不入") ==
		// -1) || (!"".equals(fe.getYd_swm_tl_hdd2()) && tempLeft.indexOf("不入")
		// == -1)){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//
		// if(!"".equals(fe.getYd_swm_tl_hdd()) && tempRight.indexOf("不入") ==
		// -1){
		// cellC = new PdfPCell();
		// p = new Paragraph(" 脱离活动度:" + fe.getYd_swm_tl_hdd(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.addElement(p);
		// cellC.setColspan(2);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_swm_tl_hdd2()) && tempLeft.indexOf("不入")
		// == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getYd_swm_tl_hdd2()) && tempLeft.indexOf("不入") ==
		// -1){
		// cellC = new PdfPCell();
		// p = new Paragraph(" 脱离活动度:" + fe.getYd_swm_tl_hdd2(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_swm_tl_hdd()) && tempRight.indexOf("不入")
		// == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		//
		// // 裂孔位置
		// if((!"".equals(fe.getYd_swm_lk_wz()) && tempRight.indexOf("不入") ==
		// -1) || (!"".equals(fe.getYd_swm_lk_wz2()) && tempLeft.indexOf("不入")
		// == -1)){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//
		// if(!"".equals(fe.getYd_swm_lk_wz()) && tempRight.indexOf("不入") ==
		// -1){
		// cellC = new PdfPCell();
		// p = new Paragraph(" 裂孔位置:" + fe.getYd_swm_lk_wz(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_swm_lk_wz2()) && tempLeft.indexOf("不入")
		// == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getYd_swm_lk_wz2()) && tempLeft.indexOf("不入") ==
		// -1){
		// cellC = new PdfPCell();
		// p = new Paragraph(" 裂孔位置:" + fe.getYd_swm_lk_wz2(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_swm_lk_wz()) && tempRight.indexOf("不入")
		// == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		//
		// // 视网膜下液
		// if((!"".equals(fe.getYd_swm_swmxy()) && tempRight.indexOf("不入") ==
		// -1) || (!"".equals(fe.getYd_swm_swmxy2()) && tempLeft.indexOf("不入")
		// == -1)){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//
		// if(!"".equals(fe.getYd_swm_swmxy()) && tempRight.indexOf("不入") ==
		// -1){
		// cellC = new PdfPCell();
		// p = new Paragraph(" 视网膜下液:" + fe.getYd_swm_swmxy(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_swm_swmxy2()) && tempLeft.indexOf("不入")
		// == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getYd_swm_swmxy2()) && tempLeft.indexOf("不入") ==
		// -1){
		// cellC = new PdfPCell();
		// p = new Paragraph(" 视网膜下液:" + fe.getYd_swm_swmxy2(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_swm_swmxy()) && tempRight.indexOf("不入")
		// == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		//
		// // 视网膜下增殖
		// if((!"".equals(fe.getYd_swm_swmxzz()) && tempRight.indexOf("不入") ==
		// -1) || (!"".equals(fe.getYd_swm_swmxzz2()) && tempLeft.indexOf("不入")
		// == -1)){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//
		// if(!"".equals(fe.getYd_swm_swmxzz()) && tempRight.indexOf("不入") ==
		// -1){
		// cellC = new PdfPCell();
		// p = new Paragraph(" 视网膜下增殖:" + fe.getYd_swm_swmxzz(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_swm_swmxzz2()) && tempLeft.indexOf("不入")
		// == -1){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//		
		//
		// if(!"".equals(fe.getYd_swm_swmxzz2()) && tempLeft.indexOf("不入") ==
		// -1){
		// cellC = new PdfPCell();
		// p = new Paragraph(" 视网膜下增殖:" + fe.getYd_swm_swmxzz2(),
		// fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_swm_swmxzz()) && tempRight.indexOf("不入")
		// == -1){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//		
		//
		// // 其他
		// if((!"".equals(fe.getYd_swm_qt()) && tempRight.indexOf("不入") == -1)
		// || (!"".equals(fe.getYd_swm_qt2()) && tempLeft.indexOf("不入") == -1)){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if (!"".equals(fe.getYd_swm_qt()) && tempRight.indexOf("不入") == -1) {
		// cellC = new PdfPCell();
		// p = new Paragraph(" 其他:" + fe.getYd_swm_qt(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_swm_qt2()) && tempLeft.indexOf("不入") ==
		// -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getYd_swm_qt2()) && tempLeft.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// p = new Paragraph(" 其他:" + fe.getYd_swm_qt2(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// } else if(!"".equals(fe.getYd_swm_qt()) && tempRight.indexOf("不入") ==
		// -1) {
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// // 黄斑
		// if(tempRight.indexOf("不入") == -1 || tempLeft.indexOf("不入") == -1){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(tempRight.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// p = new Paragraph("黄斑:", fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(tempLeft.indexOf("不入") == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//
		// if(tempLeft.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// p = new Paragraph("黄斑:", fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(tempRight.indexOf("不入") == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		//
		// // 中心凹光反射
		// if((!"".equals(fe.getYd_hb_zxagfs()) && tempRight.indexOf("不入") ==
		// -1) || (!"".equals(fe.getYd_hb_zxagfs2()) &&tempLeft.indexOf("不入") ==
		// -1)){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		// if(!"".equals(fe.getYd_hb_zxagfs()) && tempRight.indexOf("不入") ==
		// -1){
		// cellC = new PdfPCell();
		// temp = fe.getYd_hb_zxagfs();
		// if ("自定义".equals(temp)) {
		// temp = fe.getYd_huangban();
		// }
		// p = new Paragraph(" 中心凹光反射:" + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_hb_zxagfs2()) &&tempLeft.indexOf("不入")
		// == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		//
		// // 中心凹光反射
		// if(!"".equals(fe.getYd_hb_zxagfs2()) &&tempLeft.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// temp = fe.getYd_hb_zxagfs2();
		// if ("自定义".equals(temp)) {
		// temp = fe.getYd_huangban2();
		// }
		// p = new Paragraph(" 中心凹光反射:" + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_hb_zxagfs()) && tempRight.indexOf("不入")
		// == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		//
		//		
		// temp = "";
		// if (!"".equals(fe.getYd_hb_sz())) {
		// temp = fe.getYd_hb_sz();
		// }
		// if (!"".equals(fe.getYd_hb_cx())) {
		// if (!"".equals(temp)) {
		// temp += " ";
		// }
		// temp += fe.getYd_hb_cx();
		// }
		// if (!"".equals(fe.getYd_hb_sc())) {
		// if (!"".equals(temp)) {
		// temp += " ";
		// }
		// temp += fe.getYd_hb_sc();
		// }
		// if (!"".equals(fe.getYd_hb_kl())) {
		// if (!"".equals(temp)) {
		// temp += " ";
		// }
		// temp += fe.getYd_hb_kl();
		// }
		// if (!"".equals(fe.getYd_hb_qm())) {
		// if (!"".equals(temp)) {
		// temp += " ";
		// }
		// temp += fe.getYd_hb_qm();
		// }
		// if (!"".equals(temp)) {
		// temp = temp.replaceAll("<", "").replaceAll(">", "");
		// }
		//		
		// tempL = "";
		// if (!"".equals(fe.getYd_hb_sz2())) {
		// tempL = fe.getYd_hb_sz2();
		// }
		// if (!"".equals(fe.getYd_hb_cx2())) {
		// if (!"".equals(tempL)) {
		// tempL += " ";
		// }
		// tempL += fe.getYd_hb_cx2();
		// }
		// if (!"".equals(fe.getYd_hb_sc2())) {
		// if (!"".equals(tempL)) {
		// tempL += " ";
		// }
		// tempL += fe.getYd_hb_sc2();
		// }
		// if (!"".equals(fe.getYd_hb_kl2())) {
		// if (!"".equals(tempL)) {
		// tempL += " ";
		// }
		// tempL += fe.getYd_hb_kl2();
		// }
		// if (!"".equals(fe.getYd_hb_qm2())) {
		// if (!"".equals(tempL)) {
		// tempL += " ";
		// }
		// tempL += fe.getYd_hb_qm2();
		// }
		// if (!"".equals(tempL)) {
		// tempL = temp.replaceAll("<", "").replaceAll(">", "");
		// }
		//		
		//		
		// if((!"".equals(temp) && tempRight.indexOf("不入") == -1) ||
		// (!"".equals(tempL) && tempLeft.indexOf("不入") == -1)){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(temp) && tempRight.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// p = new Paragraph(" " + temp, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(tempL) &&tempLeft.indexOf("不入") == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(tempL) &&tempLeft.indexOf("不入") == -1){
		// cellC = new PdfPCell();
		// p = new Paragraph(" " + tempL, fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(temp) && tempRight.indexOf("不入") == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		//		
		// // 自述
		// if((!"".equals(fe.getYd_ContentRight()) && tempRight.indexOf("不入") ==
		// -1) || (!"".equals(fe.getYd_ContentLeft()) && tempLeft.indexOf("不入")
		// == -1)){
		// this.blankOne(cellC, p, fontGeneral, tableContent);
		// }

		// if(!"".equals(fe.getYd_ContentRight()) && tempRight.indexOf("不入") ==
		// -1){
		// cellC = new PdfPCell();
		// p = new Paragraph(" " + fe.getYd_ContentRight(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_ContentLeft()) && tempLeft.indexOf("不入")
		// == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }
		//		
		// if(!"".equals(fe.getYd_ContentLeft()) && tempLeft.indexOf("不入") ==
		// -1){
		// cellC = new PdfPCell();
		// p = new Paragraph(" " + fe.getYd_ContentLeft(), fontGeneral);
		// p.setAlignment(Element.ALIGN_LEFT);
		// cellC.setColspan(2);
		// cellC.addElement(p);
		// tableContent.addCell(cellC);
		// }else if(!"".equals(fe.getYd_ContentRight()) &&
		// tempRight.indexOf("不入") == -1){
		// this.blankOneColspan2(cellC, p, fontGeneral, tableContent);
		// }

	}

	private void blankOneT(PdfPCell cellC, Paragraph p, Font fontGeneral,
			PdfPTable tableContent) {
		cellC = new PdfPCell();
		cellC.setRowspan(2);
		p = new Paragraph("", fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
	}

	private void blankOne(PdfPCell cellC, Paragraph p, Font fontGeneral,
			PdfPTable tableContent) {
		cellC = new PdfPCell();
		p = new Paragraph("", fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
	}

	private void blankOneColspan2(PdfPCell cellC, Paragraph p,
			Font fontGeneral, PdfPTable tableContent) {
		cellC = new PdfPCell();
		p = new Paragraph("", fontGeneral);
		cellC.setColspan(2);
		cellC.addElement(p);
		tableContent.addCell(cellC);
	}

}
