package com.juncsoft.KLJY.InHospitalCase.pdf;

import java.util.Map;

import net.sf.json.JSONObject;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;

/*
 * 页头工具类，
 * 参数：titleParame：病人基本信息
 * logo:医院lago
 * fontChinese：中文字体
 * fontEngLish：英文字体
 * smallFont：小字体
 * fontBig :大字体
 * title：标题
 */
public class PDFHeader {
	public static float dl = 72 / 25.4f;// 1mm的Pdf文档长度
	
	public PdfPTable getHeader(Map titleParame, Image logo,
			Font fontChinese, Font fontEngLish, Font smallFont, Font fontBig,String title)
			throws DocumentException {
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 8, 23, 38, 10, 21 });
		// 图片
		PdfPCell cell = new PdfPCell();
		cell.setPadding(1);
		cell.setPaddingLeft(2);
		cell.setRowspan(2);
		cell.addElement(logo);
		table.addCell(cell);
		// 中英文医院名称
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setColspan(4);
		cell.setPaddingTop(12);
		Paragraph p = new Paragraph("首都医科大学附属北京佑安医院", fontChinese);
		cell.addElement(p);
		p = new Paragraph("Beijing YouAn Hospital,Capital Medical University",
				fontEngLish);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 标题
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setPaddingTop(5);
		cell.setPaddingBottom(5);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		p = new Paragraph(title, fontBig);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 姓名
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		p = new Paragraph("姓  名：" + titleParame.get("PatientName"),
				smallFont);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 住院次数
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setPaddingBottom(3);
		cell.setPaddingTop(3);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		p = new Paragraph("第" + titleParame.get("InHspTimes") + "次住院",
				smallFont);
		cell.addElement(p);
		table.addCell(cell);
		// 页码
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		p = new Paragraph("", smallFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 病案号
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		p = new Paragraph("病案号：" + titleParame.get("PatientNo"),
				smallFont);
		cell.addElement(p);
		table.addCell(cell);
		for (PdfPRow row : table.getRows()) {
			for (PdfPCell c : row.getCells()) {
				if (c != null)
					c.setBorder(Rectangle.NO_BORDER);
			}
		}
		return table;
	}
}
