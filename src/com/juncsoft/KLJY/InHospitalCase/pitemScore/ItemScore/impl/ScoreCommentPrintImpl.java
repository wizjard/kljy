package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.ScoreCommentPrint;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.ScoreComment;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.ScoreSetMeal;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.util.ConvertSubSupUtil;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.DictionaryUtil;

public class ScoreCommentPrintImpl implements ScoreCommentPrint {
	private BaseFont font;// 基础字体
	private String root;// WEB根路径
	private SimpleDateFormat dateFormat;
	private Document doc;// 打印Docment对象
	private PdfWriter writer;// PdfWriter对象
	private float dl;// 1mm的Pdf文档长度
	private Map<String, String> data;// 数据中心
	private ScoreCommentImpl impl;// 评分实体查询工具

	public ScoreCommentPrintImpl() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		dl = 72 / 25.4f;
		data = new HashMap<String, String>();
		impl = new ScoreCommentImpl();
	}

	/**
	 * 评分系统打印函数
	 * 
	 * @param os
	 *            输出流
	 * @param root
	 *            项目根路径绝对地址
	 * @param kid
	 *            病历主键
	 * @param ssmid
	 *            ScoreSetMeal主键，获取评分子页面
	 * @param scid
	 *            评分子页面外键ScoreComment主键
	 * @throws Exception
	 */
	public void print(OutputStream os, String root, String kid, String ssmid,
			String scid) throws Exception {
		this.root = root;
		// 向数据中心压入部分数据
		data.put("kid", kid);
		data.put("ssmid", ssmid);
		data.put("scid", scid);
		// 初始化字体
		font = BaseFont.createFont(root + "PUBLIC/print/SIMSUN.TTC,0",
				BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		// 准备数据
		preparData();
		// 初始化docment对象
		doc = new Document(PageSize.A4);
		doc.setMargins(20 * dl, 10 * dl, 5 * dl, 0 * dl);
		// 打开输出流并初始化writer对象
		writer = PdfWriter.getInstance(doc, os);
		// 添加页码事件
		PageNumHelper event = new PageNumHelper(font);
		writer.setPageEvent(event);
		// 设置奇偶页页边距镜像
		doc.setMarginMirroring(true);
		// 开始写入
		doc.open();

		PdfPTable table = new PdfPTable(1);
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
		cell.addElement(getHeader());
		table.addCell(cell);
		// 页脚
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setBorderWidth(0);
		cell.addElement(new Paragraph(" "));
		table.addCell(cell);

		table.setHeaderRows(2);
		table.setFooterRows(1);

		// 内容
		cell = new PdfPCell();
		PdfPTable dateStageTable = new PdfPTable(2);
		dateStageTable.setSplitLate(false);
		dateStageTable.setSplitRows(true);
		// 设置表格宽度100%
		dateStageTable.setWidthPercentage(100);
		dateStageTable.setWidths(new int[] { 1, 1 });
		Font myFont = new Font(font, 12, Font.NORMAL);
		// 评分时间
		PdfPCell c = new PdfPCell();
		Paragraph p = new Paragraph("评价时间：" + data.get("评分时间"), myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(22);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorder(Rectangle.NO_BORDER);
		dateStageTable.addCell(c);
		// 评分阶段
		c = new PdfPCell();
		p = new Paragraph(" 评价阶段：住院(" + data.get("住院时间")+")" + data.get("scoreStage"), myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorder(Rectangle.NO_BORDER);
		dateStageTable.addCell(c);
		cell.addElement(dateStageTable);
		table.addCell(cell);

		if (data.get("printPages") != null
				&& data.get("printPages").length() > 0) {
			String[] printPages = data.get("printPages").split("\\*");
			for (int i = 0; i < printPages.length; i++) {
				cell = new PdfPCell();
				cell.setPadding(0);
				cell.setBorderWidth(0);
				String printPage = printPages[i];
				if (printPage.equals("Child-Pugh")) {
					layoutChildPugh(cell, printPage);
				} else if (printPage.equals("MELD")) {
					layoutMELD(cell, printPage);
				} else if (printPage.equals("BCLC")) {
					layoutBCLC(cell, printPage);
				} else if (printPage.equals("BMI")) {
					layoutBMI(cell, printPage);
				} else if (printPage.equals("CLIP")) {
					layoutCLIP(cell, printPage);
				} else if (printPage.equals("GCS")) {
					layoutGCS(cell, printPage);
				} else if (printPage.equals("PLC")) {
					layoutPLC(cell, printPage);
				} else if (printPage.equals("TNM")) {
					layoutTNM(cell, printPage);
				} else if (printPage.equals("LCTOS")) {
					layoutLCTOS(cell, printPage);
				} else if (printPage.equals("HE")) {
					layoutHE(cell, printPage);
				} else if (printPage.equals("OrganFunc")) {
					layoutOrganFunc(cell, printPage);
				} else if (printPage.equals("HC")) {
					layoutHC(cell, printPage);
				} else if (printPage.equals("HRS")) {
					layoutHRS(cell, printPage);
				} else if (printPage.equals("HPS")) {
					layoutHPS(cell, printPage);
				} else if (printPage.equals("Hepatomyocardosis")) {
					layoutHepatomyocardosis(cell, printPage);
				} else if (printPage.equals("APACHEII")) {
					layoutAPACHEII(cell, printPage);
				} else if (printPage.equals("PHI")) {
					layoutPHI(cell, printPage);
				} else if (printPage.equals("TS")) {
					layoutTS(cell, printPage);
				}
				table.addCell(cell);
			}
		}
		/* 签名 */
		cell = new PdfPCell();
		dateStageTable = new PdfPTable(3);
		dateStageTable.setSplitLate(false);
		dateStageTable.setSplitRows(true);
		// 设置表格宽度100%
		dateStageTable.setWidthPercentage(100);
		dateStageTable.setWidths(new int[] { 1, 1, 1 });
		myFont = new Font(font, 12, Font.NORMAL);
		// 医生签字1
		c = new PdfPCell();
		p = new Paragraph("住院医师：" + data.get("doc1"), myFont);
		p.setIndentationLeft(5);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorderWidth(0);
		c.setPaddingTop(15);
		// c.setBorderWidthBottom((float) 0.5);
		dateStageTable.addCell(c);
		// 医生签字2
		c = new PdfPCell();
		p = new Paragraph("主治医师：" + data.get("doc2"), myFont);
		// p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorderWidth(0);
		c.setPaddingTop(15);
		// c.setBorderWidthBottom((float) 0.5);
		dateStageTable.addCell(c);
		// 医生签字3
		c = new PdfPCell();
		p = new Paragraph("主任/副主任医师：" + data.get("doc3"), myFont);
		p.setIndentationRight(15);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setBorderWidth(0);
		// c.setBorderWidthBottom((float) 0.5);
		c.setPaddingTop(15);
		dateStageTable.addCell(c);

		cell.addElement(dateStageTable);
		table.addCell(cell);
		doc.add(table);

		// 关闭写入
		doc.close();
	}

	/**
	 * 危重病人综合评价（外）
	 * 
	 * @param cell
	 * @param sname
	 * @throws Exception
	 */
	private void layoutTS(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(1);
		table.setSplitLate(false);
		table.setSplitRows(true);
		table.setWidthPercentage(100);
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		{
			PdfPCell myCell = new PdfPCell();
			// 危重综合症与危重症分类
			PdfPTable t = new PdfPTable(6);
			t.setSplitLate(false);
			t.setSplitRows(true);
			t.setWidthPercentage(100);
			t.setWidths(new int[] { 1, 3, 3, 1, 3, 3 });

			PdfPCell c = new PdfPCell();
			c.setRowspan(6);
			Paragraph p = new Paragraph("危", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("重", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("综", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("合", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("症", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			t.addCell(c);
			p = new Paragraph("休克"
					+ ("1".equals(data.get(sname + ".weizhong1")) ? "√" : " "),
					fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph("急性肝功能衰竭"
					+ ("1".equals(data.get(sname + ".weizhong2")) ? "√" : " "),
					fontNormal);
			t.addCell(p);
			c = new PdfPCell();
			c.setRowspan(6);
			p = new Paragraph("危", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("重", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("症", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("分", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("类", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			t.addCell(c);
			p = new Paragraph(
					"心内"
							+ (("1").equals(data.get(sname + ".weizhong12")) ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph(
					"内分泌"
							+ (("1").equals(data.get(sname + ".weizhong13")) ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p = new Paragraph("DIC"
					+ (("1").equals(data.get(sname + ".weizhong3")) ? "√" : " "),
					fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph("急性脑功能衰竭"
					+ (("1").equals(data.get(sname + ".weizhong4")) ? "√" : " "),
					fontNormal);
			t.addCell(p);
			p = new Paragraph(
					"神内"
							+ (("1").equals(data.get(sname + ".weizhong14")) ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph(
					"结缔病"
							+ (("1").equals(data.get(sname + ".weizhong15"))? "√"
									: " "), fontNormal);
			t.addCell(p);
			p = new Paragraph("心脏骤停"
					+ (("1").equals(data.get(sname + ".weizhong5")) ? "√" : " "),
					fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph("急性消化功能衰竭"
					+ (("1").equals(data.get(sname + ".weizhong6")) ? "√" : " "),
					fontNormal);
			t.addCell(p);
			p = new Paragraph(
					"呼吸"
							+ (("1").equals(data.get(sname + ".weizhong16")) ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph(
					"职业科"
							+ (("1").equals(data.get(sname + ".weizhong17"))? "√"
									: " "), fontNormal);
			t.addCell(p);
			p = new Paragraph("急性呼吸衰竭"
					+ (("1").equals(data.get(sname + ".weizhong7")) ? "√" : " "),
					fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph("昏迷"
					+ (("1").equals(data.get(sname + ".weizhong8"))? "√" : " "),
					fontNormal);
			t.addCell(p);
			p = new Paragraph(
					"消化"
							+ (("1").equals(data.get(sname + ".weizhong18")) ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph(
					"高压氧"
							+ (("1").equals(data.get(sname + ".weizhong19")) ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p = new Paragraph("急性左心衰竭"
					+ (("1").equals(data.get(sname + ".weizhong9"))? "√" : " "),
					fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph(
					"大出血"
							+ (("1").equals(data.get(sname + ".weizhong10"))? "√"
									: " "), fontNormal);
			t.addCell(p);
			p = new Paragraph(
					"血液"
							+ (("1").equals(data.get(sname + ".weizhong20")) ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph(
					"综合科"
							+ (("1").equals(data.get(sname + ".weizhong21")) ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p = new Paragraph(
					"急性肾功能衰竭"
							+ (("1").equals(data.get(sname + ".weizhong11"))? "√"
									: " "), fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph("", fontNormal);
			t.addCell(p);
			p = new Paragraph(
					"肾内"
							+ (("1").equals(data.get(sname + ".weizhong22"))? "√"
									: " "), fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph("", fontNormal);
			t.addCell(p);

			for (PdfPRow row : t.getRows()) {
				for (PdfPCell ce : row.getCells()) {
					if (ce == null)
						continue;
					ce.setPadding(0);
					ce.setUseAscender(true);
					ce.setMinimumHeight(25);
					ce.setBorder(Rectangle.NO_BORDER);
					ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
				}
			}

			t.getRow(0).getCells()[0].setBorderWidthRight(0.5f);
			t.getRow(0).getCells()[3].setBorderWidthLeft(0.5f);
			t.getRow(0).getCells()[3].setBorderWidthRight(0.5f);

			myCell.setPadding(0);
			myCell.addElement(t);
			table.addCell(myCell);
		}
		{
			// 诊断
			PdfPCell myCell1 = new PdfPCell();
			// myCell1.setBorder(Rectangle.NO_BORDER);
			PdfPTable myTable1 = new PdfPTable(3);
			myTable1.setSplitLate(false);
			myTable1.setSplitRows(true);
			myTable1.setWidthPercentage(100);
			myTable1.setWidths(new int[] {9, 11, 80 });

			PdfPCell c = new PdfPCell();
			// c.setBorder(Rectangle.RIGHT);
			c.setRowspan(4);
			Paragraph p = new Paragraph("诊断", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable1.addCell(c);

			c = new PdfPCell();
			c.setRowspan(2);
			p = new Paragraph("主要诊断", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			// c.setBorder(Rectangle.BOTTOM);
			c.addElement(p);
			myTable1.addCell(c);

			c = new PdfPCell();
			if(data.get(sname + ".zhuyao1") != null){
				p = new Paragraph("1、" + data.get(sname + ".zhuyao1"), fontNormal);
			}else{
				p = new Paragraph("1、", fontNormal);
			}
			
			p.setIndentationLeft(5);
			c.addElement(p);
			// c.setBorder(Rectangle.BOTTOM);
			myTable1.addCell(c);

			c = new PdfPCell();
			if(data.get(sname + ".zhuyao2") !=null){
				p = new Paragraph("2、" +data.get(sname + ".zhuyao2") , fontNormal);
			}else{
				p = new Paragraph("2、" , fontNormal);
			}
			
			p.setIndentationLeft(5);
			c.addElement(p);
			// c.setBorder(Rectangle.BOTTOM);
			myTable1.addCell(c);

			c = new PdfPCell();
			c.setRowspan(2);
			p = new Paragraph("合并诊断", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			// c.setBorder(Rectangle.TOP);
			c.addElement(p);
			myTable1.addCell(c);

			c = new PdfPCell();
			if(data.get(sname + ".bebing1") != null){
				p = new Paragraph("1、" + data.get(sname + ".bebing1"), fontNormal);
			}else{
				p = new Paragraph("1、" , fontNormal);
			}
			
			p.setIndentationLeft(5);
			c.addElement(p);
			// c.setBorder(Rectangle.LEFT);
			// c.setBorder(Rectangle.BOTTOM);
			myTable1.addCell(c);

			c = new PdfPCell();
			if(data.get(sname + ".bebing2") != null){
				p = new Paragraph("2、" + data.get(sname + ".bebing2"), fontNormal);
			}else{
				p = new Paragraph("2、" , fontNormal);
			}
			
			// c.setBorder(Rectangle.LEFT);
			p.setIndentationLeft(5);
			c.addElement(p);
			myTable1.addCell(c);
			for (PdfPRow row : myTable1.getRows()) {
				for (PdfPCell ce : row.getCells()) {
					if (ce == null)
						continue;
//					ce.setPadding(0);
					ce.setPaddingTop(5);
					ce.setPaddingBottom(5);
					ce.setUseAscender(true);				
					ce.setMinimumHeight(25);
					ce.setVerticalAlignment(Element.ALIGN_MIDDLE);					
					// ce.setBorder(Rectangle.NO_BORDER);
				}
			}
			myCell1.addElement(myTable1);
			table.addCell(myCell1);

		}

		{
			// TS总积分
			PdfPCell myCell2 = new PdfPCell();
			// myCell1.setBorder(Rectangle.NO_BORDER);
			PdfPTable myTable2 = new PdfPTable(6);
			myTable2.setSplitLate(false);
			myTable2.setSplitRows(true);
			myTable2.setWidthPercentage(100);
			myTable2.setWidths(new int[] { 12, 23, 20, 20, 10, 10 });

			PdfPCell c = new PdfPCell();
			c.setRowspan(9);
			Paragraph p = new Paragraph("TS总积分", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("检验项目", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			c.setColspan(3);
			p = new Paragraph("检验结果", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("积分", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("A.呼吸", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			c.setColspan(3);
			String huxiS = data.get(sname + ".huxi0");
			if(huxiS == null){
				p = new Paragraph("", fontNormal);
			}else{
				int res = Integer.parseInt(huxiS);
				String result = "";
				switch (res) {
				case 0:
					result = "0";
					break;
				case 1:
					result = "<10";
					break;
				case 2:
					result = ">35";
					break;
				case 3:
					result = "25-35";
					break;
				case 4:
					result = "10-24";
					break;
				}
				p = new Paragraph(result, fontNormal);
			}
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);

			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph(data.get(sname + ".huxi"), fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("B.呼吸幅度", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			c.setColspan(3);
			String huxifuduS = data.get(sname + ".huxifudu0");
			if(huxifuduS == null){
				p = new Paragraph("", fontNormal);
			}else{
				int huxifudu0 = Integer.parseInt(huxifuduS);
				String huxifudu = "";
				switch (huxifudu0) {
				case 1:
					huxifudu = "正常";
					break;
				case 0:
					huxifudu = "浅或困难";
					break;
				}
				p = new Paragraph(huxifudu, fontNormal);
			}
			
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph(data.get(sname + ".huxifudu"), fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("C.收缩压", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			c.setColspan(3);
			String shousuoyaS = data.get(sname + ".shousuoya0");
			if(shousuoyaS == null){
				p = new Paragraph("", fontNormal);
			}else{
				int shousuoya0 = Integer.parseInt(shousuoyaS);
				String shousuoya = "";
				switch (shousuoya0) {
				case 0:
					shousuoya = "0";
					break;
				case 1:
					shousuoya = "<50";
					break;
				case 2:
					shousuoya = "50-69";
					break;
				case 3:
					shousuoya = "70-90";
					break;
				case 4:
					shousuoya = ">90";
					break;
				}
				p = new Paragraph(shousuoya, fontNormal);
			}
			
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph(data.get(sname + ".shousuoya"), fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("D.毛细血管充盈", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			// c.setBorder(Rectangle.TOP);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			c.setColspan(3);
			String maoxiS = data.get(sname + ".maoxi0");
			if(maoxiS == null){
				p = new Paragraph("", fontNormal);
			}else{
				int maoxi0 = Integer.parseInt(maoxiS);
				String maoxi = "";
				switch (maoxi0) {
				case 0:
					maoxi = "无";
					break;
				case 1:
					maoxi = "迟缓";
					break;
				case 2:
					maoxi = "正常";
					break;
				}
				p = new Paragraph(maoxi, fontNormal);
			}
			
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph(data.get(sname + ".maoxi"), fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("E.GCS总分", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.setRowspan(4);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("项目", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("结果", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("积分", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph(data.get(sname + ".ec"), fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.setRowspan(4);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("睁眼", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			String eyeS = data.get(sname + ".eye0");
			if(eyeS == null){
				p = new Paragraph("", fontNormal);
			}else{
				int eye0 = Integer.parseInt(eyeS);
				String eye = "";
				switch (eye0) {
				case 4:
					eye = "自动睁眼";
					break;
				case 3:
					eye = "呼唤睁眼";
					break;
				case 2:
					eye = "刺痛睁眼";
					break;
				case 1:
					eye = "不睁眼";
					break;
				}
				p = new Paragraph(eye, fontNormal);
			}
			
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph(data.get(sname + ".eye"), fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("语言反应", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			String languageS = data.get(sname + ".language0");
			if(languageS == null){
				p = new Paragraph("", fontNormal);
			}else{
				int language0 = Integer.parseInt(languageS);
				String language = "";
				switch (language0) {
				case 5:
					language = "回答切题";
					break;
				case 4:
					language = "回答不切题";
					break;
				case 3:
					language = "答非所问";
					break;
				case 2:
					language = "只能发音";
					break;
				case 1:
					language = "不能言语";
					break;
				}
				p = new Paragraph(language, fontNormal);
			}
			
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph(data.get(sname + ".language"), fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("运动反应", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			String moveS = data.get(sname + ".move0");
			if(moveS == null){
				p = new Paragraph("", fontNormal);
			}else{
				int move0 = Integer.parseInt(moveS);
				String move = "";
				switch (move0) {
				case 6:
					move = "按吩咐动作";
					break;
				case 5:
					move = "刺痛能定位";
					break;
				case 4:
					move = "刺痛能躲避";
					break;
				case 3:
					move = "刺痛肢体屈曲";
					break;
				case 2:
					move = "刺痛肢体伸展";
					break;
				case 1:
					move = "不能活动";
					break;
				}
				p = new Paragraph(move, fontNormal);
			}
			
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph(data.get(sname + ".move"), fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("TS总积分", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.setColspan(5);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph(data.get(sname + ".tc"), fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			for (PdfPRow row : myTable2.getRows()) {
				for (PdfPCell ce : row.getCells()) {
					if (ce == null)
						continue;
					ce.setPadding(0);
					ce.setUseAscender(true);
					ce.setMinimumHeight(25);
					// ce.setBorder(Rectangle.NO_BORDER);
					ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
				}
			}
			myCell2.addElement(myTable2);
			table.addCell(myCell2);
		}

		cell.addElement(table);
	}

	/**
	 * 危重病人综合评价（内）
	 * 
	 * @param cell
	 * @param sname
	 * @throws Exception
	 */
	private void layoutPHI(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(1);
		table.setSplitLate(false);
		table.setSplitRows(true);
		table.setWidthPercentage(100);
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		{
			PdfPCell myCell = new PdfPCell();
			// 危重综合症与危重症分类
			PdfPTable t = new PdfPTable(6);
			t.setSplitLate(false);
			t.setSplitRows(true);
			t.setWidthPercentage(100);
			t.setWidths(new int[] { 7,21,23,7,21,21});
			PdfPCell c = new PdfPCell();
			c.setRowspan(6);
			Paragraph p = new Paragraph("危", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("重", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("综", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("合", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("症", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			t.addCell(c);
			
			p = new Paragraph("休克"
					+ (data.get(sname + ".weizhong1").equals("1") ? "√" : " "),
					fontNormal);
			
			t.addCell(p);
			
			p.setIndentationLeft(5);
			p = new Paragraph("急性肝功能衰竭"
					+ (data.get(sname + ".weizhong2").equals("1") ? "√" : " "),
					fontNormal);
			t.addCell(p);
			c = new PdfPCell();
			c.setRowspan(6);
			p = new Paragraph("危", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("重", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("症", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("分", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			p = new Paragraph("类", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			t.addCell(c);
			p = new Paragraph(
					"心内"
							+ (data.get(sname + ".weizhong12").equals("1") ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph(
					"内分泌"
							+ (data.get(sname + ".weizhong13").equals("1") ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p = new Paragraph("DIC"
					+ (data.get(sname + ".weizhong3").equals("1") ? "√" : " "),
					fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph("急性脑功能衰竭"
					+ (data.get(sname + ".weizhong4").equals("1") ? "√" : " "),
					fontNormal);
			t.addCell(p);
			p = new Paragraph(
					"神内"
							+ (data.get(sname + ".weizhong14").equals("1") ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph(
					"结缔病"
							+ (data.get(sname + ".weizhong15").equals("1") ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p = new Paragraph("心脏骤停"
					+ (data.get(sname + ".weizhong5").equals("1") ? "√" : " "),
					fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph("急性消化功能衰竭"
					+ (data.get(sname + ".weizhong6").equals("1") ? "√" : " "),
					fontNormal);
			t.addCell(p);
			p = new Paragraph(
					"呼吸"
							+ (data.get(sname + ".weizhong16").equals("1") ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph(
					"职业科"
							+ (data.get(sname + ".weizhong17").equals("1") ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p = new Paragraph("急性呼吸衰竭"
					+ (data.get(sname + ".weizhong7").equals("1") ? "√" : " "),
					fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph("昏迷"
					+ (data.get(sname + ".weizhong8").equals("1") ? "√" : " "),
					fontNormal);
			t.addCell(p);
			p = new Paragraph(
					"消化"
							+ (data.get(sname + ".weizhong18").equals("1") ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph(
					"高压氧"
							+ (data.get(sname + ".weizhong19").equals("1") ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p = new Paragraph("急性左心衰竭"
					+ (data.get(sname + ".weizhong9").equals("1") ? "√" : " "),
					fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph(
					"大出血"
							+ (data.get(sname + ".weizhong10").equals("1") ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p = new Paragraph(
					"血液"
							+ (data.get(sname + ".weizhong20").equals("1") ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph(
					"综合科"
							+ (data.get(sname + ".weizhong21").equals("1") ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p = new Paragraph(
					"急性肾功能衰竭"
							+ (data.get(sname + ".weizhong11").equals("1") ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph("", fontNormal);
			t.addCell(p);
			p = new Paragraph(
					"肾内"
							+ (data.get(sname + ".weizhong22").equals("1") ? "√"
									: " "), fontNormal);
			t.addCell(p);
			p.setIndentationLeft(5);
			p = new Paragraph("", fontNormal);
			t.addCell(p);

			for (PdfPRow row : t.getRows()) {
				for (PdfPCell ce : row.getCells()) {
					if (ce == null)
						continue;
//					ce.setPadding(0);
					ce.setUseAscender(true);
					ce.setMinimumHeight(25);
					ce.setBorder(Rectangle.NO_BORDER);
					ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
				}
			}

			t.getRow(0).getCells()[0].setBorderWidthRight(0.5f);
			t.getRow(0).getCells()[3].setBorderWidthLeft(0.5f);
			t.getRow(0).getCells()[3].setBorderWidthRight(0.5f);

			myCell.setPadding(0);
			myCell.addElement(t);
			table.addCell(myCell);
		}
		{
			// 诊断
			PdfPCell myCell1 = new PdfPCell();
//			myCell1.setBorder(Rectangle.NO_BORDER);
			PdfPTable myTable1 = new PdfPTable(3);
			myTable1.setSplitLate(false);
			myTable1.setSplitRows(true);
			myTable1.setWidthPercentage(100);
			myTable1.setWidths(new int[] {9, 11, 80 });
			

			PdfPCell c = new PdfPCell();
			// c.setBorder(Rectangle.RIGHT);
			c.setRowspan(4);
			Paragraph p = new Paragraph("诊断", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable1.addCell(c);

			c = new PdfPCell();
			c.setRowspan(2);
			p = new Paragraph("主要诊断", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			// c.setBorder(Rectangle.BOTTOM);
			c.addElement(p);
			myTable1.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("1、" + data.get(sname + ".zhuyao1"), fontNormal);
			p.setIndentationLeft(5);
			c.addElement(p);
//			c.setPaddingTop(2);
//			c.setPaddingBottom(2);
//			c.setUseAscender(true);
//			c.setMinimumHeight(25);
//			c.setVerticalAlignment(Element.ALIGN_MIDDLE);
			// c.setBorder(Rectangle.BOTTOM);
			myTable1.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("2、" + data.get(sname + ".zhuyao2"), fontNormal);
			p.setIndentationLeft(5);
			c.addElement(p);
			// c.setBorder(Rectangle.BOTTOM);
			myTable1.addCell(c);

			c = new PdfPCell();
			c.setRowspan(2);
			p = new Paragraph("合并诊断", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			// c.setBorder(Rectangle.TOP);
			c.addElement(p);
			myTable1.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("1、" + data.get(sname + ".ciyao1"), fontNormal);
			p.setIndentationLeft(5);
			c.addElement(p);
			// c.setBorder(Rectangle.LEFT);
			// c.setBorder(Rectangle.BOTTOM);
			myTable1.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("2、" + data.get(sname + ".ciyao2"), fontNormal);
			// c.setBorder(Rectangle.LEFT);
			p.setIndentationLeft(5);
			c.addElement(p);
			myTable1.addCell(c);
			for (PdfPRow row : myTable1.getRows()) {
				for (PdfPCell ce : row.getCells()) {
					if (ce == null)
						continue;
//					ce.setPadding(0);
					ce.setPaddingTop(5);
					ce.setPaddingBottom(5);
					ce.setUseAscender(true);
					ce.setUseAscender(true);
					ce.setMinimumHeight(25);
					ce.setVerticalAlignment(Element.ALIGN_MIDDLE);	
//					ce.setBorder(Rectangle.NO_BORDER);
					
				}
			}
			myCell1.addElement(myTable1);
			table.addCell(myCell1);

		}

		{
			// PHI总积分
			PdfPCell myCell2 = new PdfPCell();
			// myCell1.setBorder(Rectangle.NO_BORDER);
			PdfPTable myTable2 = new PdfPTable(4);
			myTable2.setSplitLate(false);
			myTable2.setSplitRows(true);
			myTable2.setWidthPercentage(100);
			myTable2.setWidths(new int[] { 15, 35, 35, 15 });

			PdfPCell c = new PdfPCell();
			c.setRowspan(5);
			Paragraph p = new Paragraph("PHI总积分", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("检验项目", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("检验结果", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("得分", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("A.收缩压(mmHg)", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			int res = Integer.parseInt(data.get(sname + ".shousuo0"));
			String result = "";
			switch (res) {
			case 5:
				result = "0-74";
				break;
			case 2:
				result = "75-84";
				break;
			case 1:
				result = "85-100";
				break;
			case 0:
				result = ">100";
				break;
			}
			p = new Paragraph(result, fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);

			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph(data.get(sname + ".shousuo"), fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("B.脉搏(次/分)", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			int maibo0 = Integer.parseInt(data.get(sname + ".maibo0"));
			String maibo = "";
			switch (maibo0) {
			case 5:
				maibo = "<50";
				break;
			case 0:
				maibo = "51-119";
				break;
			case 3:
				maibo = "≥120";
				break;
			}
			p = new Paragraph(maibo, fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph(data.get(sname + ".maibo"), fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("C.呼吸(次/分)", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			int huxi0 = Integer.parseInt(data.get(sname + ".huxi0"));
			String huxi = "";
			switch (huxi0) {
			case 0:
				huxi = "正常";
				break;
			case 3:
				huxi = "困难";
				break;
			case 5:
				huxi = "<10或需插管";
				break;
			}
			p = new Paragraph(huxi, fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph(data.get(sname + ".huxi"), fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("D.神志", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			// c.setBorder(Rectangle.TOP);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			int shenzhi0 = Integer.parseInt(data.get(sname + ".shenzhi0"));
			String shenzhi = "";
			switch (shenzhi0) {
			case 0:
				shenzhi = "正常";
				break;
			case 3:
				shenzhi = "混乱";
				break;
			case 5:
				shenzhi = "不可理解的语言";
				break;
			}
			p = new Paragraph(shenzhi, fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			// c.setBorder(Rectangle.LEFT);
			// c.setBorder(Rectangle.BOTTOM);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph(data.get(sname + ".shenzhi"), fontNormal);
			// c.setBorder(Rectangle.LEFT);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			c.setColspan(2);
			p = new Paragraph("PHI总积分值分组", fontNormal);
			// c.setBorder(Rectangle.LEFT);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("0-3", fontNormal);
			// c.setBorder(Rectangle.LEFT);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph("4-20", fontNormal);
			// c.setBorder(Rectangle.LEFT);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			c.setColspan(2);
			p = new Paragraph("PHI总积分=A+B+C+D", fontNormal);
			// c.setBorder(Rectangle.LEFT);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph(data.get(sname + ".phi1"), fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			// c.setBorder(Rectangle.LEFT);
			// c.setBorder(Rectangle.BOTTOM);
			myTable2.addCell(c);

			c = new PdfPCell();
			p = new Paragraph(data.get(sname + ".phi2"), fontNormal);
			// c.setBorder(Rectangle.LEFT);
			p.setAlignment(Element.ALIGN_CENTER);
			c.addElement(p);
			myTable2.addCell(c);

			for (PdfPRow row : myTable2.getRows()) {
				for (PdfPCell ce : row.getCells()) {
					if (ce == null)
						continue;
					ce.setPadding(0);
					ce.setUseAscender(true);
					ce.setMinimumHeight(25);
					// ce.setBorder(Rectangle.NO_BORDER);
					ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
				}
			}
			myCell2.addElement(myTable2);
			table.addCell(myCell2);
		}

		cell.addElement(table);
	}

	/**
	 * 危重病人综合评价
	 * 
	 * @param cell
	 * @param sname
	 * @throws Exception
	 */
	private void layoutAPACHEII(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(9);
		table.setSplitLate(false);
		table.setSplitRows(true);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 15, 15, 10, 10, 10, 10, 10,10, 10 });
		Font fontBold = new Font(font, 12, Font.BOLD);
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		PdfPCell c = new PdfPCell();
		// 抢救诊 断
		Paragraph p = new Paragraph("抢救诊断", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setRowspan(6);
		c.addElement(p);
		table.addCell(c);
		// 1
		c = new PdfPCell();
		p = new Paragraph("抢救疾病诊断", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("1、" + data.get(sname + ".qiangjiu1"), fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(7);
		c.addElement(p);
		table.addCell(c);
		// 2
		c = new PdfPCell();
		p = new Paragraph("主要疾病诊断", fontNormal);
		c.setRowspan(2);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("1、" + data.get(sname + ".zhuyao1"), fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(7);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("2、" + data.get(sname + ".zhuyao2"), fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(7);
		c.addElement(p);
		table.addCell(c);
		// 3
		c = new PdfPCell();
		p = new Paragraph("合并伴随诊断", fontNormal);
		c.setRowspan(3);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		table.addCell(c);

		
		c = new PdfPCell();
		p = new Paragraph("1、" + data.get(sname + ".hebing1"), fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(7);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("2、" + data.get(sname + ".hebing2"), fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(7);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("3、" + data.get(sname + ".hebing3"), fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(7);
		c.addElement(p);
		table.addCell(c);
		// 4
		c = new PdfPCell();
		p = new Paragraph("A.年龄", fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String ascore0Str = data.get(sname + ".ascore0");
		if("".equals(ascore0Str)){
			p = new Paragraph("", fontNormal);
		}else{
			int ascore0 = Integer.parseInt(data.get(sname + ".ascore0"));
			String ascore = "";
			switch (ascore0) {
			case 0:
				ascore = "≤44";
				break;
			case 2:
				ascore = "45-54";
				break;
			case 3:
				ascore = "55-64";
				break;
			case 5:
				ascore = "65-70";
				break;
			case 6:
				ascore = "≥75";
				break;
			}
			p = new Paragraph(ascore+"岁", fontNormal);
		}
		
		c.setColspan(6);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("A积分", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".ascore"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		// 4
		c = new PdfPCell();
		p = new Paragraph("B.有严重器官系统功能不全或免疫损害", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(4);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String bscoreStr= data.get(sname + ".bscore0");
		if("".equals(bscoreStr)){
			p = new Paragraph(bscoreStr, fontNormal);
		}else{
			int bscore0 = Integer.parseInt(data.get(sname + ".bscore0"));
			String bscore = "";
			switch (bscore0) {
			case 0:
				bscore = "无上述情况";
				break;
			case 2:
				bscore = "非手术或择期手术";
				break;
			case 5:
				bscore = "不能手术或急诊手术";
				break;
			}
			p = new Paragraph(bscore, fontNormal);
		}
		c.setColspan(3);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("B积分", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".bscore"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("GCS评分", fontNormal);
		c.setRowspan(4);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("检验项目", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("检验结果", fontNormal);
		c.setColspan(3);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("积分", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("GCS积分", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("C积分", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setRowspan(4);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".cscore"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setRowspan(4);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("睁眼", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String eyeStr = data.get(sname + ".eye0");
		if("".equals(eyeStr)){
			p = new Paragraph(eyeStr, fontNormal);
		}else{
			int eye0 = Integer.parseInt(data.get(sname + ".eye0"));
			String eye = "";
			switch (eye0) {
			case 4:
				eye = "自动睁眼";
				break;
			case 3:
				eye = "呼唤睁眼";
				break;
			case 2:
				eye = "刺痛睁眼";
				break;
			case 1:
				eye = "不睁眼";
				break;
			}
			p = new Paragraph(eye, fontNormal);
		}
		
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".eye"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".gcsScore"), fontNormal);
		c.setRowspan(3);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("语言反应", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String lanStr = data.get(sname + ".language0");
		if("".equals(lanStr)){
			p = new Paragraph(lanStr, fontNormal);
		}else{
			int language0 = Integer.parseInt(data.get(sname + ".language0"));
			String language = "";
			switch (language0) {
			case 5:
				language = "回答切题";
				break;
			case 4:
				language = "回答不切题";
				break;
			case 3:
				language = "答非所问";
				break;
			case 2:
				language = "只能发音";
				break;
			case 1:
				language = "不能言语";
				break;
			}
			p = new Paragraph(language, fontNormal);
		}
		c.setColspan(3);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".language"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("运动反应", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String moveStr = data.get(sname + ".move0");
		if("".equals(moveStr)){
			p = new Paragraph(moveStr, fontNormal);
		}else{
			int move0 = Integer.parseInt(data.get(sname + ".move0"));
			String move = "";
			switch (move0) {
			case 6:
				move = "按吩咐动作";
				break;
			case 5:
				move = "刺痛能定位";
				break;
			case 4:
				move = "刺痛能躲避";
				break;
			case 3:
				move = "刺痛肢体屈曲";
				break;
			case 2:
				move = "刺痛肢体伸展";
				break;
			case 1:
				move = "不能活动";
				break;
			}
			p = new Paragraph(move, fontNormal);
		}
		
		c.setColspan(3);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".move"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		// 6
		c = new PdfPCell();
		p = new Paragraph("D.生理指标", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("结果", fontNormal);
		c.setColspan(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("积分", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		// 7
		

		// 8
		c = new PdfPCell();
		p = new Paragraph("1.体温(腋下℃)", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String tiwenStr = data.get(sname + ".tiwen0");
		if("".equals(tiwenStr)){
			p = new Paragraph(tiwenStr, fontNormal);
		}else{
			int tiwen0 = Integer.parseInt(data.get(sname + ".tiwen0"));
			String tiwen = "";
			switch (tiwen0) {
			case 4:
				tiwen = "≥41";
				break;
			case 3:
				tiwen = "39-40.9";
				break;
			case 1:
				tiwen = "38.5-38.9";
				break;
			case 0:
				tiwen = "36-38.4";
				break;
			case -1:
				tiwen = "34-35.9";
				break;
			case -2:
				tiwen = "32-33.9";
				break;
			case -3:
				tiwen = "30-31.9";
				break;
			case -4:
				tiwen = "≤29.9";
				break;
			}
			p = new Paragraph(tiwen, fontNormal);
		}
		
		c.setColspan(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".tiwen"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		// 8
		c = new PdfPCell();
		p = new Paragraph("2.平均血压(mmHg)", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String xueStr = data.get(sname + ".xueya0");
		if("".equals(xueStr)){
			p = new Paragraph(xueStr, fontNormal);
		}else{
			int xueya0 = Integer.parseInt(data.get(sname + ".xueya0"));
			String xueya = "";
			switch (xueya0) {
			case 4:
				xueya = "≥160";
				break;
			case 3:
				xueya = "130-159";
				break;
			case 2:
				xueya = "110-129";
				break;
			case 0:
				xueya = "70-109";
				break;
			case -2:
				xueya = "50-69";
				break;
			case -4:
				xueya = "≤49";
				break;
			}
			p = new Paragraph(xueya, fontNormal);
		}
		
		c.setColspan(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".xueya"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		// 9
		c = new PdfPCell();
		p = new Paragraph("3.心率(次/分)", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String xinStr = data.get(sname + ".xinlv0");
		if("".equals(xinStr)){
			p = new Paragraph(xinStr, fontNormal);
		}else{
			int xinlv0 = Integer.parseInt(data.get(sname + ".xinlv0"));
			String xinlv = "";
			switch (xinlv0) {
			case 4:
				xinlv = "≥180";
				break;
			case 3:
				xinlv = "140-179";
				break;
			case 2:
				xinlv = "110-139";
				break;
			case 0:
				xinlv = "70-109";
				break;
			case -2:
				xinlv = "55-69";
				break;
			case -3:
				xinlv = "40-54";
				break;
			case -4:
				xinlv = "≤39";
				break;
			}
			p = new Paragraph(xinlv, fontNormal);
		}
		
		c.setColspan(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".xinlv"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		// 9
		c = new PdfPCell();
		p = new Paragraph("4.呼吸频率(次/分)", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String huxiStr = data.get(sname + ".huxi0");
		if("".equals(huxiStr)){
			p = new Paragraph(huxiStr, fontNormal);
		}else{
			int huxi0 = Integer.parseInt(data.get(sname + ".huxi0"));
			String huxi = "";
			switch (huxi0) {
			case 4:
				huxi = "≥50";
				break;
			case 3:
				huxi = "35-49";
				break;
			case 1:
				huxi = "25-34";
				break;
			case 0:
				huxi = "12-24";
				break;
			case -1:
				huxi = "10-11";
				break;
			case -2:
				huxi = "6-9";
				break;
			case -4:
				huxi = "≤5";
				break;
			}
			p = new Paragraph(huxi, fontNormal);
		}
		
		c.setColspan(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".huxi"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		// 10
		c = new PdfPCell();
		p = ConvertSubSupUtil.parseSubOrSup(new Paragraph("5.PaO$2$(mmHg)(Fio$2$<50%)", fontNormal));
		String st = p.getContent();
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String paoStr = data.get(sname + ".paoFio0");
		if("".equals(paoStr)){
			p = new Paragraph(paoStr, fontNormal);
		}else{
			int paoFio0 = Integer.parseInt(data.get(sname + ".paoFio0"));
			String paoFio = "";
			switch (paoFio0) {
			case 0:
				paoFio = "≥70";
				break;
			case 1:
				paoFio = "61-70";
				break;
			case 3:
				paoFio = "55-60";
				break;
			case 4:
				paoFio = "<55";
				break;
			}
			p = new Paragraph(paoFio, fontNormal);
		}
		c.setColspan(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".paoFio"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		
		
		c = new PdfPCell();
		p = ConvertSubSupUtil.parseSubOrSup(new Paragraph("   A-aDO$2$(Fio$2$>50%) ",fontNormal));
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String addStr = data.get(sname + ".aado0");
		if("".equals(addStr)){
			p = new Paragraph(addStr, fontNormal);
		}else{
			int aado0 = Integer.parseInt(data.get(sname + ".aado0"));
			String aado = "";
			switch (aado0) {
			case 4:
				aado = "≥500";
				break;
			case 3:
				aado = "350-499";
				break;
			case 2:
				aado = "200-349";
				break;
			case 0:
				aado = "<200";
				break;
			}
			p = new Paragraph(aado, fontNormal);
		}
		
		c.setColspan(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".aado"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		
		// 11
		c = new PdfPCell();
		p = new Paragraph("6.动脉血PH(mmol/L)(无血气时用)", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String dpnStr = data.get(sname + ".dongmaixue0");
		if("".equals(dpnStr)){
			p = new Paragraph(dpnStr, fontNormal);
		}else{
			int dongmaixue0 = Integer.parseInt(data.get(sname + ".dongmaixue0"));
			String dongmaixue = "";
			switch (dongmaixue0) {
			case 4:
				dongmaixue = "≥7.7";
				break;
			case 3:
				dongmaixue = "7.6-7.69";
				break;
			case 1:
				dongmaixue = "7.5-7.59";
				break;
			case 0:
				dongmaixue = "7.33-7.49";
				break;
			case -2:
				dongmaixue = "7.25-7.32";
				break;
			case -3:
				dongmaixue = "7.15-7.24";
				break;
			case -4:
				dongmaixue = "<7.15";
				break;
			
			}
			p = new Paragraph(dongmaixue, fontNormal);
		}
		
		c.setColspan(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".dongmaixue"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		
		// 11
		c = new PdfPCell();
		p = ConvertSubSupUtil.parseSubOrSup(new Paragraph("  血清血 HCO$3$(mmol/L)(无血气时用)", fontNormal));
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String hcoStr = data.get(sname + ".hco0");
		if("".equals(hcoStr)){
			p = new Paragraph(hcoStr, fontNormal);
		}else{
			int hco0 = Integer.parseInt(data.get(sname + ".hco0"));
			String hco = "";
			switch (hco0) {
			case 4:
				hco = "≥52";
				break;
			case 3:
				hco = "41-51.9";
				break;
			case 1:
				hco = "32-40.9";
				break;
			case 0:
				hco = "23-31.9";
				break;
			case -2:
				hco = "18-21.9";
				break;
			case -3:
				hco = "15-17.9";
				break;
			case -4:
				hco = "<15";
				break;
			
			}
			p = new Paragraph(hco, fontNormal);
		}
		c.setColspan(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".hco"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		

		// 12
		c = new PdfPCell();
		p = new Paragraph("7.血清Na(mmol/L)", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String xueqingStr = data.get(sname + ".xueqingNa0");
		if("".equals(xueqingStr)){
			p = new Paragraph(xueqingStr, fontNormal);
		}else{
			int xueqingNa0 = Integer.parseInt(data.get(sname + ".xueqingNa0"));
			String xueqingNa = "";
			switch (xueqingNa0) {
			case 4:
				xueqingNa = "≥180";
				break;
			case 3:
				xueqingNa = "160-179";
				break;
			case 2:
				xueqingNa = "155-159";
				break;
			case 1:
				xueqingNa = "150-154";
				break;
			case 0:
				xueqingNa = "130-149";
				break;
			case -2:
				xueqingNa = "120-129";
				break;
			case -3:
				xueqingNa = "111-119";
				break;
			case -4:
				xueqingNa = "≤110";
				break;
			
			}
			p = new Paragraph(xueqingNa, fontNormal);
		}
		
		c.setColspan(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".xueqingNa"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		// 13
		c = new PdfPCell();
		p = new Paragraph("8.血清K(mmol/L)", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String xueqStr = data.get(sname + ".xueqingK0");
		if("".equals(xueqStr)){
			p = new Paragraph(xueqStr, fontNormal);
		}else{
			int xueqingK0 = Integer.parseInt(data.get(sname + ".xueqingK0"));
			String xueqingK = "";
			switch (xueqingK0) {
			case 4:
				xueqingK = "≥7";
				break;
			case 3:
				xueqingK = "6-6.9";
				break;
			case 1:
				xueqingK = "5.5-5.9";
				break;
			case 0:
				xueqingK = "3.5-5.4";
				break;
			case -1:
				xueqingK = "3-3.4";
				break;
			case -2:
				xueqingK = "2.5-2.9";
				break;
			case -4:
				xueqingK = "<2.5";
				break;
			}
			p = new Paragraph(xueqingK, fontNormal);
		}
		
		c.setColspan(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".xueqingK"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		// 14
		c = new PdfPCell();
		p = new Paragraph("9.血清肌酐(mg/dl)", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String xqjStr = data.get(sname + ".xqjiqian0");
		if("".equals(xqjStr)){
			p = new Paragraph(xqjStr, fontNormal);
		}else{
			int xqjiqian0 = Integer.parseInt(data.get(sname + ".xqjiqian0"));
			String xqjiqian = "";
			switch (xqjiqian0) {
			case 4:
				xqjiqian = "≥3.5";
				break;
			case 3:
				xqjiqian = "2-3.4";
				break;
			case 2:
				xqjiqian = "1.5-1.9";
				break;
			case 0:
				xqjiqian = "0.6-1.4";
				break;
			case -2:
				xqjiqian = "<0.6";
				break;
			}
			p = new Paragraph(xqjiqian, fontNormal);
		}
		
		c.setColspan(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".xqjiqian"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		// 15
		c = new PdfPCell();
		p = new Paragraph("10.血球压积(%)", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String xueqiuStr = data.get(sname + ".xueqiuJiya0");
		if("".equals(xueqiuStr)){
			p = new Paragraph(xueqiuStr, fontNormal);
		}else{
			int xueqiuJiya0 = Integer.parseInt(data.get(sname + ".xueqiuJiya0"));
			String xueqiuJiya = "";
			switch (xueqiuJiya0) {
			case 4:
				xueqiuJiya = "≥60";
				break;
			case 2:
				xueqiuJiya = "50-59.9";
				break;
			case 1:
				xueqiuJiya = "46-49.9";
				break;
			case 0:
				xueqiuJiya = "30-45.9";
				break;
			case -2:
				xueqiuJiya = "20-29.9";
				break;
			case -4:
				xueqiuJiya = "<20";
				break;
			}
			p = new Paragraph(xueqiuJiya, fontNormal);
		}
		
		c.setColspan(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".xueqiuJiya"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		// 16
		c = new PdfPCell();
		p = new Paragraph("11.WBC(X1000)", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String wbcStr = data.get(sname + ".wbc0");
		if("".equals(wbcStr)){
			p = new Paragraph(wbcStr, fontNormal);
		}else{
			int wbc0 = Integer.parseInt(data.get(sname + ".wbc0"));
			String wbc = "";
			switch (wbc0) {
			case 4:
				wbc = "≥40";
				break;
			case 2:
				wbc = "20-39.9";
				break;
			case 1:
				wbc = "15-19.9";
				break;
			case 0:
				wbc = "3-14.9";
				break;
			case -2:
				wbc = "1-2.9";
				break;
			case -4:
				wbc = "<1";
				break;
			}
			p = new Paragraph(wbc, fontNormal);
		}
		
		c.setColspan(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".wbc"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("12.急性肾衰竭", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		String hasShen = data.get(sname + ".hasShen");
		if("<1>".equals(hasShen)){
			p = new Paragraph("急性肾衰竭", fontNormal);
		}else{
			p = new Paragraph("无急性肾衰竭", fontNormal);
		}
		c.setColspan(6);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		
		// 17
		c = new PdfPCell();
		//c.setBorder(Rectangle.NO_BORDER);
		p = new Paragraph("备注：", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(7);
		c.addElement(p);
		table.addCell(c);
		
		c = new PdfPCell();
		p = new Paragraph("D积分", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setRowspan(8);
		c.addElement(p);
		table.addCell(c);
		
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".dscore"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setRowspan(8);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("1、急性肾衰竭时第9项分值加倍", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(7);
		c.addElement(p);
		table.addCell(c);
		
		c = new PdfPCell();
		p = new Paragraph("2、严重器官功能不全：", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(7);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("(1)心:心功能IV级;", fontNormal);
		p.setIndentationLeft(10);
		c.setColspan(7);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("(2)肺:慢性缺氧,阻塞性或限制性通气障碍,运动耐受差;", fontNormal);
		p.setIndentationLeft(10);
		c.setColspan(7);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("(3)肾:慢性透析者;", fontNormal);
		p.setIndentationLeft(10);
		c.setColspan(7);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("(4)肝:肝硬化,门脉高压,有上消化道出血史,肝昏迷,肝功能衰竭史; ", fontNormal);
		p.setIndentationLeft(10);
		c.setColspan(7);
		c.addElement(p);
		table.addCell(c);
		for (PdfPRow row : table.getRows()) {
			for (PdfPCell ce : row.getCells()) {
				if (ce == null)
					continue;
//				ce.setPadding(0);
				ce.setPaddingTop(5);
				ce.setPaddingBottom(5);
				ce.setUseAscender(true);
				ce.setMinimumHeight(25);			
				ce.setVerticalAlignment(Element.ALIGN_MIDDLE);			
//				ce.setBorder(Rectangle.NO_BORDER);				
			}
		}
		c = new PdfPCell();
		p = new Paragraph("3、免疫损害:如接受放疗、化疗、长期或大量激素治疗,有白血病、淋巴瘤、艾滋病等。",
				fontNormal);
		p.setIndentationLeft(5);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setColspan(7);
		c.setMinimumHeight(40);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("APACHE II 总积分分组", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setColspan(2);
		c.setMinimumHeight(25);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("0-9", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("10-14", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("15-19", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("20-24", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("25-29", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("30-34", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("35+", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("APACHE II 总积分", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.setColspan(2);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".apas1"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".apas2"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".apas3"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".apas4"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".apas5"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".apas6"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".apas7"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		
		cell.addElement(table);

	}

	/**
	 * 肝门部胆管癌分型
	 * 
	 * @param cell
	 * @param sname
	 * @throws Exception
	 */
	private void layoutHC(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(2);
		table.setSplitLate(false);
		table.setSplitRows(true);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 50, 50 });
		Font fontBold = new Font(font, 12, Font.BOLD);
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		PdfPCell c = new PdfPCell();
		// 表头
		Paragraph p = new Paragraph("肝门部胆管癌分型", fontBold);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(2);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("肝门部胆管癌Bismuth分型", fontBold);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("肝门部胆管癌T分期", fontBold);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		for (PdfPRow row : table.getRows()) {
			for (PdfPCell ce : row.getCells()) {
				if (ce == null)
					continue;
				ce.setPadding(0);
				ce.setPaddingTop(1);
				ce.setPaddingBottom(1);
				ce.setUseAscender(true);
				ce.setMinimumHeight(25);
				ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}

		String[] array;
		String[] array2;
		String value = "";

		array = new String[] { "肿瘤位于肝总管，未侵犯汇合部", "肿瘤已累及汇合部,但未侵犯左右肝管",
				"肿瘤已侵犯右肝管", "肿瘤已侵犯左肝管", "肿瘤已侵犯左右肝管" };
		array2 = new String[] { "BismuthⅠ型", "BismuthⅡ型", "BismuthⅢa型",
				"BismuthⅢb型", "BismuthⅣ型" };
		if (data.get(sname + ".bismuthRst") != null
				&& data.get(sname + ".hcbismuth").length() > 0) {
			for (int i = 0, len = array2.length; i < len; i++) {
				if (data.get(sname + ".hcbismuth").equals(array2[i])) {
					value = array[i];
					break;
				}
			}
		}
		c = new PdfPCell();
		p = new Paragraph(value, fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(40);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);
		
		array = new String[] { "肿瘤累及肝管汇合部和（或）扩展到单侧二级胆管",
				"肿瘤累及肝管汇合部和（或）扩展到单侧二级胆管合并同侧门静脉受侵和（或）同侧肝叶萎缩",
				"肿瘤累及肝管汇合部并扩展到双侧二级胆管；或扩展到单侧二级胆管合并对侧门静脉受侵；或扩展到单侧二级胆管合并对侧肝叶萎缩；或门静脉主干受侵" };
		array2 = new String[] { "T1", "T2", "T3" };
		if (data.get(sname + ".hct") != null
				&& data.get(sname + ".hct").length() > 0) {
			for (int i = 0, len = array2.length; i < len; i++) {
				if (data.get(sname + ".hct").equals(array2[i])) {
					value = array[i];
					break;
				}
			}
		}
		c = new PdfPCell();
		p = new Paragraph(value, fontNormal);
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(40);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("分型结果：" + data.get(sname + ".bismuthRst"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("分期结果：" + data.get(sname + ".hctRst") + "期",
				fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		
		cell.addElement(table);
	}

	/**
	 * 肝癌肝移植手术标准
	 * 
	 * @param cell
	 * @param sname
	 * @throws Exception
	 */
	private void layoutLCTOS(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(2);
		table.setSplitLate(false);
		table.setSplitRows(true);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 15, 85 });
		Font fontBold = new Font(font, 12, Font.BOLD);
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		PdfPCell c = new PdfPCell();
		// 表头
		Paragraph p = new Paragraph("肝癌肝移植手术标准", fontBold);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(2);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("米兰标准：", fontNormal);
		p.setAlignment(Element.ALIGN_RIGHT);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		String milan = data.get(sname + ".milanStandard");
		if("".equals(milan)){
			p = new Paragraph("", fontNormal);
		}else{
			p = new Paragraph(("1").equals(milan) ? "符合"
					: "不符合", fontNormal);
		}
		
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);
		table.addCell("");
		c = new PdfPCell();
		p = new Paragraph("米兰标准要求：", fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);
		table.addCell("");
		c = new PdfPCell();
		p = new Paragraph("1、单个肿瘤直径≤5 cm或多发肿瘤少于3个且最大直径≤3 cm，无大血管浸润", fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);
		table.addCell("");
		c = new PdfPCell();
		p = new Paragraph("2、无淋巴结或肝外转移", fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("UCSF标准：", fontNormal);
		p.setAlignment(Element.ALIGN_RIGHT);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		String ucsf = data.get(sname + ".ucsfStandard");
		if(("").equals(ucsf)){
			p = new Paragraph("", fontNormal);
		}else{
			p = new Paragraph(("1").equals(ucsf) ? "符合"
					: "不符合", fontNormal);
		}
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);
		table.addCell("");
		c = new PdfPCell();
		p = new Paragraph("UCSF标准要求：", fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);
		for (PdfPRow row : table.getRows()) {
			for (PdfPCell ce : row.getCells()) {
				if (ce == null)
					continue;
				ce.setPadding(0);
				ce.setPaddingTop(1);
				ce.setPaddingBottom(1);
				ce.setUseAscender(true);
				ce.setMinimumHeight(25);
				ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}
		table.addCell("");
//		c.setPadding(0);
//		c.setPaddingTop(1);
//		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(40);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c = new PdfPCell();
		p = new Paragraph(
				"1、单个肿瘤直径不超过6.5cm,或肿瘤数目不超过3个，最大直径不超过4.5cm，总的肿瘤直径不超过8cm",
				fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
//		c.setPadding(0);
//		c.setPaddingTop(1);
//		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(40);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);
		table.addCell("");
		c = new PdfPCell();
		p = new Paragraph("2、不伴有血管及淋巴结的侵犯", fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);

		
		cell.addElement(table);
	}

	/**
	 * BMI指数
	 * 
	 * @param cell
	 * @param sname
	 * @throws Exception
	 */
	private void layoutBMI(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(4);
		table.setSplitLate(false);
		table.setSplitRows(true);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 25, 25, 25, 25 });
		Font fontBold = new Font(font, 12, Font.BOLD);
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		PdfPCell c = new PdfPCell();
		// 表头
		Paragraph p = new Paragraph("BMI指数", fontBold);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(4);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("体重(Kg)", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".weight"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("身高(m)", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".height"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("体质指数(BMI)", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".bodyMi"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("国内标准", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".clinicalValue"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("国际标准", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".whoClinicalValue"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		for (PdfPRow row : table.getRows()) {
			for (PdfPCell ce : row.getCells()) {
				if (ce == null)
					continue;
//				ce.setPadding(0);
//				ce.setPaddingTop(1);
//				ce.setPaddingBottom(1);
				ce.setUseAscender(true);
				ce.setMinimumHeight(25);
				ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}

		cell.addElement(table);
	}

	/**
	 * 巴塞罗那（BCLC）肝癌分期系统
	 * 
	 * @param cell
	 * @param sname
	 * @throws Exception
	 */
	private void layoutBCLC(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(4);
		table.setSplitLate(false);
		table.setSplitRows(true);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 20, 35, 15, 30 });
		Font fontBold = new Font(font, 12, Font.BOLD);
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		PdfPCell c = new PdfPCell();
		// 表头
		Paragraph p = new Paragraph("巴塞罗那（BCLC）肝癌分期系统", fontBold);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(4);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);

		String[] array;
		String value;

		c = new PdfPCell();
		p = new Paragraph("PST病情评分", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "正常活动", "有症状，但几乎不影响下床活动", "白天卧床时间少于50％",
				"白天卧床时间多于50％", "完全卧床" };
		if (data.get(sname + ".pstIllScore") != null
				&& data.get(sname + ".pstIllScore").length() > 0
				&& !data.get(sname + ".pstIllScore").equals("-1")) {
			value = array[Integer.parseInt(data.get(sname + ".pstIllScore"))];
		} else {
			value = "";
		}
		c = new PdfPCell();
		p = new Paragraph(value, fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		c.setMinimumHeight(40);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("肿瘤分期", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "单一肿瘤", "3个小于3cm", "大，多个结节", "血管侵入", "肝外转移" };
		if (data.get(sname + ".tumorStage") != null
				&& data.get(sname + ".tumorStage").length() > 0
				&& !data.get(sname + ".tumorStage").equals("-1")) {
			value = array[Integer.parseInt(data.get(sname + ".tumorStage"))];
		} else {
			value = "";
		}
		c = new PdfPCell();
		p = new Paragraph(value, fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("Okuda分期", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);
		array = new String[] { "I", "II", "III" };
		if (data.get(sname + ".okudaStage") != null
				&& data.get(sname + ".okudaStage").length() > 0
				&& !data.get(sname + ".okudaStage").equals("-1")) {
			value = array[Integer.parseInt(data.get(sname + ".okudaStage"))];
		} else {
			value = "";
		}
		c = new PdfPCell();
		p = new Paragraph(value, fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("肝脏功能", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "没有门脉高压，正常胆红素", "门脉高压，正常胆红素", "门脉高压，不正常胆红素",
				"Child-Pugh A", "Child-Pugh B", "Child-Pugh C" };
		if (data.get(sname + ".liverFun") != null
				&& data.get(sname + ".liverFun").length() > 0
				&& !data.get(sname + ".liverFun").equals("-1")) {
			value = array[Integer.parseInt(data.get(sname + ".liverFun"))];
		} else {
			value = "";
		}
		c = new PdfPCell();
		p = new Paragraph(value, fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("BCLC分期", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".bclcStage"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		for (PdfPRow row : table.getRows()) {
			for (PdfPCell ce : row.getCells()) {
				if (ce == null)
					continue;
//				ce.setPadding(0);
//				ce.setPaddingTop(1);
//				ce.setPaddingBottom(1);
				ce.setUseAscender(true);
//				ce.setMinimumHeight(25);
				ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}

		cell.addElement(table);
	}

	/**
	 * 意大利肿瘤计划（CLIP）分期系统
	 * 
	 * @param cell
	 * @param sname
	 * @throws Exception
	 */
	private void layoutCLIP(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(5);
		table.setSplitLate(false);
		table.setSplitRows(true);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 20, 22, 24, 24, 10 });
		Font fontBold = new Font(font, 12, Font.BOLD);
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		PdfPCell c = new PdfPCell();
		// 表头
		Paragraph p = new Paragraph("意大利肿瘤计划（CLIP）分期系统", fontBold);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(5);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("指       标", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(2);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("分值", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(3);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("得分", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(2);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("0", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("1", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("2", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("Child-Pugh分级", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("A级"
				+ (data.get(sname + ".cpGrade").equals("0") ? " √" : ""),
				fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("B级"
				+ (data.get(sname + ".cpGrade").equals("1") ? " √" : ""),
				fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("C级"
				+ (data.get(sname + ".cpGrade").equals("2") ? " √" : ""),
				fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".cpGrade2"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("肿瘤形态", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("单一且≤肝脏50%"
				+ (data.get(sname + ".knubType").equals("0") ? " √" : ""),
				fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("多发且≤肝脏50%"
				+ (data.get(sname + ".knubType").equals("1") ? " √" : ""),
				fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("巨块型＞肝脏50%"
				+ (data.get(sname + ".knubType").equals("2") ? " √" : ""),
				fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".knubType2"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("甲胎蛋白", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("＜400"
				+ (data.get(sname + ".jtAlbumen").equals("0") ? " √" : ""),
				fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("≥400"
				+ (data.get(sname + ".jtAlbumen").equals("1") ? " √" : ""),
				fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		table.addCell("");
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".jtAlbumen2"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("门静脉栓塞", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("无"
				+ (data.get(sname + ".pvEmbolism").equals("0") ? " √" : ""),
				fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("有"
				+ (data.get(sname + ".pvEmbolism").equals("1") ? " √" : ""),
				fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		table.addCell("");
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".pvEmbolism2"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("总       积       分", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(4);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".totalScore"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

//		c = new PdfPCell();
//		p = new Paragraph("分          期", fontNormal);
//		p.setAlignment(Element.ALIGN_CENTER);
//		c.setColspan(4);
//		c.addElement(p);
//		table.addCell(c);
//
//		c = new PdfPCell();
//		p = new Paragraph(data.get(sname + ".stage"), fontNormal);
//		p.setAlignment(Element.ALIGN_CENTER);
//		c.addElement(p);
//		table.addCell(c);

		for (PdfPRow row : table.getRows()) {
			for (PdfPCell ce : row.getCells()) {
				if (ce == null)
					continue;
//				ce.setPadding(0);
//				ce.setPaddingTop(1);
//				ce.setPaddingBottom(1);
				ce.setUseAscender(true);
//				ce.setMinimumHeight(25);
				ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}

		cell.addElement(table);
	}

	/**
	 * 肝癌TNM分期
	 * 
	 * @param cell
	 * @param sname
	 * @throws Exception
	 */
	private void layoutTNM(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(2);
		table.setSplitLate(false);
		table.setSplitRows(true);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 25, 75 });
		Font fontBold = new Font(font, 12, Font.BOLD);
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		PdfPCell c = new PdfPCell();
		// 表头
		Paragraph p = new Paragraph("肝癌TNM分期", fontBold);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(2);
		c.addElement(p);
		table.addCell(c);

		String[] array;
		String value;

		c = new PdfPCell();
		p = new Paragraph("原发肿瘤(T)分期", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "没有原发肿瘤的证据", "单个肿瘤结节，无血管侵润",
				"单个肿瘤结节，并伴血管侵润；或多个肿瘤结节，最大径均≤5cm",
				"多个肿瘤结节，最大径＞5cm；或肿瘤侵犯门静脉或肝静脉的主要分支", "肿瘤直接侵犯除胆囊以外的附近脏器；或穿破内脏腹膜" };
		if (data.get(sname + ".tnmt") != null
				&& data.get(sname + ".tnmt").length() > 0) {
			value = array[Integer.parseInt(data.get(sname + ".tnmt")) - 1];
		} else {
			value = "";
		}
		c = new PdfPCell();
		p = new Paragraph(value, fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("淋巴结转移(N)分期", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "无局部淋巴结转移", "有局部淋巴结转移" };
		if (data.get(sname + ".tnmn") != null
				&& data.get(sname + ".tnmn").length() > 0) {
			value = array[Integer.parseInt(data.get(sname + ".tnmn")) - 1];
		} else {
			value = "";
		}
		c = new PdfPCell();
		p = new Paragraph(value, fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("远处转移(M)分期", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "无远处转移",
				"有远处转移，包括不同肺叶散播和除前斜角肌窝、锁骨上区淋巴结转移以外的其他部位的淋巴结转移" };
		if (data.get(sname + ".tnmm") != null
				&& data.get(sname + ".tnmm").length() > 0) {
			value = array[Integer.parseInt(data.get(sname + ".tnmm")) - 1];
		} else {
			value = "";
		}
		c = new PdfPCell();
		p = new Paragraph(value, fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("肝癌TNM分期", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + ".tnmStage"), fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);

		for (PdfPRow row : table.getRows()) {
			for (PdfPCell ce : row.getCells()) {
				if (ce == null)
					continue;
				ce.setPadding(0);
				ce.setPaddingTop(1);
				ce.setPaddingBottom(1);
				ce.setUseAscender(true);
				ce.setMinimumHeight(25);
				ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}

		cell.addElement(table);
	}

	/**
	 * 格拉斯哥昏迷评分
	 * 
	 * @param cell
	 * @param sname
	 * @throws Exception
	 */
	private void layoutGCS(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(5);
		table.setSplitLate(false);
		table.setSplitRows(true);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 20, 20, 20, 20, 20 });
		Font fontBold = new Font(font, 12, Font.BOLD);
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		// 表头
		PdfPCell c = new PdfPCell();
		Paragraph p = new Paragraph("格拉斯哥昏迷评分", fontBold);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(5);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("检验项目", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("检验结果", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("积分", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("GCS积分", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("GCS意义", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		String[] array;
		String value;

		c = new PdfPCell();
		p = new Paragraph("睁    眼", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "不睁眼", "刺疼睁眼", "呼唤睁眼", "自动睁眼" };
		if (data.get(sname + ".openEyeResult") != null
				&& data.get(sname + ".openEyeResult").length() > 0) {
			value = array[Integer.parseInt(data.get(sname + ".openEyeResult")) - 1];
		} else {
			value = "";
		}
		c = new PdfPCell();
		p = new Paragraph(value, fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "openEyeScore"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "gcsScore"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "gcsMeaning"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("语言反应", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "不能言语", "只能发音", "答非所问", "回答不切题", "回答切题" };
		if (data.get(sname + ".languageRespResult") != null
				&& data.get(sname + ".languageRespResult").length() > 0) {
			value = array[Integer.parseInt(data.get(sname
					+ ".languageRespResult")) - 1];
		} else {
			value = "";
		}
		c = new PdfPCell();
		p = new Paragraph(value, fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "languageRespScore"),
				fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("运动反应", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "不能活动", "刺痛肢体伸展", "刺痛肢体屈曲", "刺痛能躲避", "刺痛能定位",
				"按吩咐动作" };
		if (data.get(sname + ".moveRespResult") != null
				&& data.get(sname + ".moveRespResult").length() > 0) {
			value = array[Integer.parseInt(data.get(sname + ".moveRespResult")) - 1];
		} else {
			value = "";
		}
		c = new PdfPCell();
		p = new Paragraph(value, fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "moveRespScore"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		for (PdfPRow row : table.getRows()) {
			for (PdfPCell ce : row.getCells()) {
				if (ce == null)
					continue;
				ce.setPadding(0);
				ce.setPaddingTop(1);
				ce.setPaddingBottom(1);
				ce.setUseAscender(true);
				ce.setMinimumHeight(25);
				ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}

		cell.addElement(table);
	}

	/**
	 * 原发性肝癌临床分期
	 * 
	 * @param cell
	 * @throws Exception
	 */
	private void layoutPLC(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(2);
		table.setSplitLate(false);
		table.setSplitRows(true);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 25, 75 });
		Font fontBold = new Font(font, 12, Font.BOLD);
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		// 表头
		PdfPCell c = new PdfPCell();
		Paragraph p = new Paragraph("原发性肝癌临床分期", fontBold);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(2);
		c.addElement(p);
		table.addCell(c);

		String[] array;
		String value;

		c = new PdfPCell();
		p = new Paragraph("肿瘤", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "单个，直径≤3cm", "单个，直径≤5cm",
				"两个，位于同侧半肝，且最大直径之和≤5cm", "单个，直径≤10cm",
				"两个，位于同侧半肝，且最大直径之和≤10cm", "两个，分别位于左、右半肝，且最大直径之和≤5cm",
				"单个，直径＞10cm", "两个，位于同侧半肝，且最大直径之和＞10cm",
				"两个，在左、右两半肝，且最大直径之和＞5cm", "多个肿瘤" };
		if (data.get(sname + ".tumor") != null
				&& data.get(sname + ".tumor").length() > 0) {
			value = array[Integer.parseInt(data.get(sname + ".tumor")) - 1];
		} else {
			value = "";
		}
		c = new PdfPCell();
		p = new Paragraph(value, fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("癌栓", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		value = "";
		if (data.get(sname + ".ce1") != null
				&& data.get(sname + ".ce1").equals("1")) {
			value += "无、";
		}
		if (data.get(sname + ".ce2") != null
				&& data.get(sname + ".ce2").equals("1")) {
			value += "门静脉分支癌栓、";
		}
		if (data.get(sname + ".ce3") != null
				&& data.get(sname + ".ce3").equals("1")) {
			value += "肝静脉癌栓、";
		}
		if (data.get(sname + ".ce4") != null
				&& data.get(sname + ".ce4").equals("1")) {
			value += "胆管癌栓、";
		}
		if (data.get(sname + ".ce5") != null
				&& data.get(sname + ".ce5").equals("1")) {
			value += "门静脉主干癌栓、";
		}
		if (data.get(sname + ".ce6") != null
				&& data.get(sname + ".ce6").equals("1")) {
			value += "下腔静脉癌栓、";
		}
		if (value.length() > 0)
			value = value.substring(0, value.length() - 1);
		c = new PdfPCell();
		p = new Paragraph(value, fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("腹腔淋巴结转移", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "cln"))) {
			p = new Paragraph(data.get(sname + "." + "cln"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "cln").equals("2") ? "有"
					: "无", fontNormal);
		}

		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("远处转移", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pm"))) {
			p = new Paragraph(data.get(sname + "." + "pm"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "pm").equals("2") ? "有"
					: "无", fontNormal);
		}

		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("Child分级", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "Child-Pugh A", "Child-Pugh B", "Child-Pugh C" };
		c = new PdfPCell();
		if (data.get(sname + ".cgrade") != null
				&& data.get(sname + ".cgrade").length() > 0) {
			value = array[Integer.parseInt(data.get(sname + ".cgrade")) - 1];
		} else {
			value = "";
		}
		p = new Paragraph(value, fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("原发性肝癌临床分期", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "stage"), fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);

		for (PdfPRow row : table.getRows()) {
			for (PdfPCell ce : row.getCells()) {
				if (ce == null)
					continue;
//				ce.setPadding(0);
//				ce.setPaddingTop(1);
//				ce.setPaddingBottom(1);
				ce.setUseAscender(true);
				ce.setMinimumHeight(25);
				ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}

		cell.addElement(table);
	}

	/**
	 * MELD评分
	 * 
	 * @param cell
	 * @throws Exception
	 */
	private void layoutMELD(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(4);
		table.setSplitLate(false);
		table.setSplitRows(true);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 35, 15, 30, 20 });
		Font fontBold = new Font(font, 12, Font.BOLD);
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		// 表头
		PdfPCell c = new PdfPCell();
		Paragraph p = new Paragraph("MELD评分", fontBold);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(4);
		c.addElement(p);
		table.addCell(c);

		String[] array;
		String value;

		c = new PdfPCell();
		p = new Paragraph("肌酐(μmol/L)", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "creatinine"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("胆红素(μmol/L)", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "bilirubin"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("INR", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "inr"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("病因", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "胆汁淤积性", "酒精性肝硬化", "其它" };
		c = new PdfPCell();
		if (data.get(sname + "." + "etiology") != null
				&& data.get(sname + "." + "etiology").length() > 0) {
			value = array[Integer.parseInt(data.get(sname + "." + "etiology"))];
		} else {
			value = "";
		}
		p = new Paragraph(value, fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("MELD评分结果", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "result"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		for (PdfPRow row : table.getRows()) {
			for (PdfPCell ce : row.getCells()) {
				if (ce == null)
					continue;
//				ce.setPadding(0);
//				ce.setPaddingTop(1);
//				ce.setPaddingBottom(1);
				ce.setUseAscender(true);
				ce.setMinimumHeight(25);
				ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}

		cell.addElement(table);
	}

	/**
	 * Child-Pugh评分系统
	 * 
	 * @param cell
	 * @throws Exception
	 */
	private void layoutChildPugh(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(4);
		table.setSplitLate(false);
		table.setSplitRows(true);
		// 设置表格宽度100%
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 35, 20, 30, 15 });
		Font fontBold = new Font(font, 12, Font.BOLD);
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		// 表头
		PdfPCell c = new PdfPCell();
		Paragraph p = new Paragraph("Child-Pugh评分系统", fontBold);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(4);
		c.addElement(p);
		table.addCell(c);

		String[] array;
		String value="";

		c = new PdfPCell();
		p = new Paragraph("肝性脑病(期)", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
//		array = new String[] { "", "无", "1", "2", "3", "4" };
//		if (data.get(sname + "." + "hepaticEp") != null
//				&& data.get(sname + "." + "hepaticEp").length() > 0) {
//			value = array[Integer.parseInt(data.get(sname + "." + "hepaticEp"))];
//		} else {
//			value = "";
//		}
		
		c = new PdfPCell();
		if (data.get(sname + "." + "hepaticEp") != null
				&& data.get(sname + "." + "hepaticEp").length() > 0) {
			int res = Integer.parseInt(data.get(sname + ".hepaticEp"));
			switch (res) {
			case 0:
				value = "";
				break;
			case 1:
				value = "无";
				break;
			case -2:
				value = "Ⅰ";
				break;
			case 2:
				value = "Ⅱ";
				break;
			case -3:
				value = "Ⅲ";
				break;
			case 3:
				value = "Ⅳ";
				break;
			}
		}
		p = new Paragraph(value, fontNormal);
//		p.setIndentationLeft(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("腹水", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
//		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "", "无", "轻度", "中重度" };
		c = new PdfPCell();
		if (data.get(sname + "." + "ascites") != null
				&& data.get(sname + "." + "ascites").length() > 0) {
			value = array[Integer.parseInt(data.get(sname + "." + "ascites"))];
		} else {
			value = "";
		}
		p = new Paragraph(value, fontNormal);
//		p.setIndentationLeft(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("胆红素(μmol/L)", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
//		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "", "<34.2", "34.2-51.3", ">51.3" };
		c = new PdfPCell();
		if (data.get(sname + "." + "bilirubin") != null
				&& data.get(sname + "." + "bilirubin").length() > 0) {
			value = array[Integer.parseInt(data.get(sname + "." + "bilirubin"))];
		} else {
			value = "";
		}
		p = new Paragraph(value, fontNormal);
//		p.setIndentationLeft(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("白蛋白(g/L)", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
//		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "", ">35", "28-35", "<28" };
		c = new PdfPCell();
		if (data.get(sname + "." + "albumin") != null
				&& data.get(sname + "." + "albumin").length() > 0) {
			value = array[Integer.parseInt(data.get(sname + "." + "albumin"))];
		} else {
			value = "";
		}
		p = new Paragraph(value, fontNormal);
//		p.setIndentationLeft(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("凝血酶原时间较正常延长(秒)", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
//		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		array = new String[] { "", "<4", "4-6", ">6" };
		c = new PdfPCell();
		if (data.get(sname + "." + "prothrombinTime") != null
				&& data.get(sname + "." + "prothrombinTime").length() > 0) {
			value = array[Integer.parseInt(data.get(sname + "."
					+ "prothrombinTime"))];
		} else {
			value = "";
		}
		p = new Paragraph(value, fontNormal);
//		p.setIndentationLeft(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("Child-Pugh总积分", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
//		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "totalScore"), fontNormal);
//		p.setIndentationLeft(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("Child-Pugh分级", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
//		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "scoreGrade"), fontNormal);
//		p.setIndentationLeft(5);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		for (PdfPRow row : table.getRows()) {
			for (PdfPCell ce : row.getCells()) {
				if (ce == null)
					continue;
//				ce.setPadding(0);
//				ce.setPaddingTop(1);
//				ce.setPaddingBottom(1);
				ce.setUseAscender(true);
				ce.setMinimumHeight(25);
				ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}

		cell.addElement(table);
	}

	/**
	 * 肝肾综合征诊断标准
	 * 
	 * @param cell
	 * @throws Exception
	 */
	private void layoutHRS(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(3);
		table.setSplitLate(false);
		table.setSplitRows(true);
		// 设置表格宽度100%
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 7, 83, 7 });
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		// 表头
		PdfPCell c = new PdfPCell();
		Paragraph p = new Paragraph("序号", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("标准", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("判断", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		// 1
		c = new PdfPCell();
		p = new Paragraph("1", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("肝硬化腹水", fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "gyhfs"))) {
			p = new Paragraph(data.get(sname + "." + "gyhfs"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "gyhfs").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		// 2
		c = new PdfPCell();
		p = new Paragraph("2", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("血肌酐＞1.5mg/dl (133 µmol/L)", fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "xjs"))) {
			p = new Paragraph(data.get(sname + "." + "xjs"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "xjs").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		// 3
		c = new PdfPCell();
		p = new Paragraph("3", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("无休克", fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "wxk"))) {
			p = new Paragraph(data.get(sname + "." + "wxk"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "wxk").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		for (PdfPRow row : table.getRows()) {
			for (PdfPCell ce : row.getCells()) {
				if (ce == null)
					continue;
				ce.setPadding(0);
				ce.setPaddingTop(1);
				ce.setPaddingBottom(1);
				ce.setUseAscender(true);
				ce.setMinimumHeight(25);
				ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}
		// 4
		c = new PdfPCell();
		p = new Paragraph("4", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(40);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(
				"无低血容量，定义为至少停用2天利尿剂（假如使用利尿剂）并且白蛋白1g/kg/天直到最大100g/天扩容后，肾功能无持续性改善（血肌酐＜133µmol/L）",
				fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(40);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "wdxrl"))) {
			p = new Paragraph(data.get(sname + "." + "wdxrl"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "wdxrl").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}
		c.addElement(p);
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(40);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);
		// 5
		c = new PdfPCell();
		p = new Paragraph("5", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("目前或近期无肾毒性药物使用史", fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "wsdywsy"))) {
			p = new Paragraph(data.get(sname + "." + "wsdywsy"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "wsdywsy").equals("1") ? "是" : "否",
					fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}
		c.addElement(p);
		
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);
		
		// 6
		c = new PdfPCell();
		p = new Paragraph("6", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(40);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("无肾实质疾病，定义为蛋白尿＜500mg/天，无镜下血尿（每高倍镜视野＜50个红细胞）和肾脏超声正常",
				fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(40);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "wssj"))) {
			p = new Paragraph(data.get(sname + "." + "wssj"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "wssj").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		// 结果
		c = new PdfPCell();
		p = new Paragraph("结果", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(2);
		c.addElement(p);
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("请临床医生根据患者临床具体情况判定是否符合肝肾综合征诊断", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "result"))) {
			p = new Paragraph(data.get(sname + "." + "result"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "result").equals("1") ? "是" : "否",
					fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("肝肾综合征分型", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		p.setIndentationLeft(5);
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		String[] grade = new String[] { "Ⅰ型", "Ⅱ型" };
		String rst = "";
		if (data.get(sname + "." + "gszf") != null
				&& data.get(sname + "." + "gszf").length() > 0)
			rst = grade[Integer.parseInt(data.get(sname + "." + "gszf")) - 1];
		p = new Paragraph(rst, fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);
		

		cell.addElement(table);
	}

	/**
	 * 肝肺综合征诊断标准
	 * 
	 * @param cell
	 * @throws Exception
	 */
	private void layoutHPS(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(5);
		table.setSplitLate(false);
		table.setSplitRows(true);
		// 设置表格宽度100%
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 7, 15, 51, 17, 10 });
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		// 表头
		PdfPCell c = new PdfPCell();
		Paragraph p = new Paragraph("序号", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("标准", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(2);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("实际值", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("判断", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		// 1
		c = new PdfPCell();
		p = new Paragraph("1", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("有肝病史", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "hepatosisHis"))) {
			p = new Paragraph(data.get(sname + "." + "hepatosisHis"),
					fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "hepatosisHis")
					.equals("1") ? "有" : "无", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);

		// 2
		c = new PdfPCell();
		p = new Paragraph("2", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(2);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("低氧血症", fontNormal);
		p.setIndentationLeft(5);
		c.setRowspan(2);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		//lhf
		p=new Paragraph("动脉血氧分压" , fontNormal);
		Paragraph p1=new Paragraph("PaO$2$(mmHg)", fontNormal);
		p1= ConvertSubSupUtil.parseSubOrSup(p1);
		p.add(p1);
		
//		p = ConvertSubSupUtil.parseSubOrSup(new Paragraph("动脉血氧分压PaO$2$(mmHg)", fontNormal));
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		if(data.get(sname + "." + "pao2").equals(""))p = new Paragraph("---",fontNormal);
		else{p = new Paragraph(data.get(sname + "." + "pao2"), fontNormal);};
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "isPao2"))) {
			p = new Paragraph(data.get(sname + "." + "isPao2"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "isPao2").equals("1") ? "是" : "否",
					fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		//lhf
		p=new Paragraph("肺泡-动脉血氧分压差", fontNormal);
		Paragraph p2=new Paragraph("AaDO$2$(mmHg)", fontNormal);
		p2= ConvertSubSupUtil.parseSubOrSup(p2);
		p.add(p2);
		
//		p = ConvertSubSupUtil.parseSubOrSup(new Paragraph("肺泡-动脉血氧分压差(AaDO$2$)(mmHg)", fontNormal));
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);

		c = new PdfPCell();
		if(data.get(sname + "." + "aado2").equals(""))p = new Paragraph("---",fontNormal);
		else{p = new Paragraph(data.get(sname + "." + "aado2"), fontNormal);};
		//p = new Paragraph(data.get(sname + "." + "aado2") + "mmHg", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "isAado2"))) {
			p = new Paragraph(data.get(sname + "." + "isAado2"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "isAado2").equals("1") ? "是" : "否",
					fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}
		c.addElement(p);
		table.addCell(c);

		// 3
		c = new PdfPCell();
		p = new Paragraph("3", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(2);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("肺血管扩张", fontNormal);
		p.setIndentationLeft(5);
		c.setRowspan(2);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		//lhf修改显示中中文问题
		p=new Paragraph("核素扫描:",fontNormal);
		//Paragraph p1=new Paragraph("^99m^TC",fontNormal);
		//p1 = ConvertSubSupUtil.parseSubOrSup(p1);
		//Paragraph p2=new Paragraph("巨聚白蛋白",fontNormal);
		//Paragraph p3=new Paragraph("(^99m^TC-MAA)",fontNormal);
		//p3= ConvertSubSupUtil.parseSubOrSup(p3);
		//Paragraph p4=new Paragraph("肺灌注扫描显示肺灌注比例(%)",fontNormal);
		//p.add(p1);p.add(p2);p.add(p3);p.add(p4);//现在的问题是回车
		
		String str="核素扫描：99mTC巨聚白蛋白（99mTC-MAA）肺灌注扫描显示肺灌注比例：";
		Chunk chunk = new Chunk("99m",fontNormal);
		chunk.setTextRise(3);
		Font myFont = new Font(font, 8, Font.NORMAL);
		chunk.setFont(myFont);
		p.add(chunk);p.add("TC巨聚白蛋白（");p.add(chunk);p.add("TC-MAA）肺灌注扫描显示肺灌注比例：");
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(40);
		table.addCell(c);

		c = new PdfPCell();
		if(data.get(sname + "." + "maa").equals(""))p = new Paragraph("---",fontNormal);
		else{p = new Paragraph(data.get(sname + "." + "maa"), fontNormal);};
		//p = new Paragraph(data.get(sname + "." + "maa"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "isMaa"))) {
			p = new Paragraph(data.get(sname + "." + "isMaa"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "isMaa").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		c.setColspan(2);
		p = new Paragraph("对比超声心动描记术(微泡造影)", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "ce"))) {
			p = new Paragraph(data.get(sname + "." + "ce"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "ce").equals("1") ? "阳性"
					: "阴性", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		
		
//		 结果
		c = new PdfPCell();
		p = new Paragraph("结果", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(2);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("请临床医生根据患者临床具体情况判定是否符合肝肺综合征诊断", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(3);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "result"))) {
			p = new Paragraph(data.get(sname + "." + "result"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "result").equals("1") ? "是" : "否",
					fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("肝肺综合征分级", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(3);
		c.addElement(p);
		c.setMinimumHeight(25);
		table.addCell(c);
		c = new PdfPCell();
		String[] grade = new String[] { "轻度", "中度", "重度", "极重度" };
		String rst = "";
		if (data.get(sname + "." + "grade") != null
				&& data.get(sname + "." + "grade").length() > 0)
			rst = grade[Integer.parseInt(data.get(sname + "." + "grade")) - 1];
		p = new Paragraph(rst, fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		for (PdfPRow row : table.getRows()) {
			for (PdfPCell ce : row.getCells()) {
				if (ce == null)
					continue;
//				ce.setPadding(0);
//				ce.setPaddingTop(1);
//				ce.setPaddingBottom(1);
				ce.setUseAscender(true);
//				ce.setMinimumHeight(25);
				ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}
		
		// 4
		c = new PdfPCell();
		p = new Paragraph(
				"附:当患者出现以下症状时，临床应考虑是否有肝肺综合征的可能。呼吸困难与发绀（进行性呼吸困难是最常见的肺部症状，直立性缺氧是最重要的特征性表现）",
				fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(5);
		c.addElement(p);
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(40);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);
//		c = new PdfPCell();
//		if ("".equals(data.get(sname + "." + "bdc"))) {
//			p = new Paragraph(data.get(sname + "." + "bdc"), fontNormal);
//		} else {
//			p = new Paragraph(data.get(sname + "." + "bdc").equals("1") ? "是"
//					: "否", fontNormal);
//			p.setAlignment(Element.ALIGN_CENTER);
//		}
//
//		c.addElement(p);
//		table.addCell(c);
		
		

		cell.addElement(table);
	}

	/**
	 * 肝硬化心肌病诊断标准表
	 * 
	 * @param cell
	 * @throws Exception
	 */
	private void layoutHepatomyocardosis(PdfPCell cell, String sname)
			throws Exception {
		PdfPTable table = new PdfPTable(5);
		table.setSplitLate(false);
		table.setSplitRows(true);
		// 设置表格宽度100%
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 7, 18, 40, 25, 10 });
		Font fontNormal = new Font(font, 12, Font.NORMAL);
		// 表头
		PdfPCell c = new PdfPCell();
		Paragraph p = new Paragraph("序号", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("标准", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(2);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("实际值", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("判断", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		// 1
		c = new PdfPCell();
		p = new Paragraph("1", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("收缩功能障碍", fontNormal);
		p.setIndentationLeft(5);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("左心室缩短分数(FS)<30%", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if(data.get(sname + "." + "lvs0").equals(""))p = new Paragraph("---",fontNormal);
		else{p = new Paragraph(data.get(sname + "." + "lvs0"), fontNormal);};
		//p = new Paragraph(data.get(sname + "." + "lvs0")+"%", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "lvs"))) {
			p = new Paragraph(data.get(sname + "." + "lvs"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "lvs").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("左室后壁(LVPWA)<10mm", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if(data.get(sname + "." + "lvpwa0").equals(""))p = new Paragraph("---",fontNormal);
		else{p = new Paragraph(data.get(sname + "." + "lvpwa0"), fontNormal);};
		//p = new Paragraph(data.get(sname + "." + "lvpwa0"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "lvpwa"))) {
			p = new Paragraph(data.get(sname + "." + "lvpwa"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "lvpwa").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("室间隔(IVSA)<4mm", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if(data.get(sname + "." + "ivsa0").equals(""))p = new Paragraph("---",fontNormal);
		else{p = new Paragraph(data.get(sname + "." + "ivsa0"), fontNormal);};
		//p = new Paragraph(data.get(sname + "." + "ivsa0")+"mm", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "ivsa"))) {
			p = new Paragraph(data.get(sname + "." + "ivsa"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "ivsa").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		// 2
		c = new PdfPCell();
		p = new Paragraph("2", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("静息状态下的射血分数(EF)< 55%", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		p.setIndentationLeft(5);
		c.setColspan(2);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if(data.get(sname + "." + "ef0").equals(""))p = new Paragraph("---",fontNormal);
		else{p = new Paragraph(data.get(sname + "." + "ef0"), fontNormal);};
		//p = new Paragraph(data.get(sname + "." + "ef0"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "ef"))) {
			p = new Paragraph(data.get(sname + "." + "ef"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "ef").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		// 3
		c = new PdfPCell();
		p = new Paragraph("3", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("舒张功能障碍", fontNormal);
		p.setIndentationLeft(5);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("E /A 比率< 1或>2", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if(data.get(sname + "." + "eaRatio0").equals(""))p = new Paragraph("---",fontNormal);
		else{p = new Paragraph(data.get(sname + "." + "eaRatio0"), fontNormal);};
		//p = new Paragraph(data.get(sname + "." + "eaRatio0"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "eaRatio"))) {
			p = new Paragraph(data.get(sname + "." + "eaRatio"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "eaRatio").equals("1") ? "是" : "否",
					fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("减速时间延长>200ms", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if(data.get(sname + "." + "dtl0").equals(""))p = new Paragraph("---",fontNormal);
		else{p = new Paragraph(data.get(sname + "." + "eaRatio0"), fontNormal);};
		//p = new Paragraph(data.get(sname + "." + "dtl0")+"ms", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "dtl"))) {
			p = new Paragraph(data.get(sname + "." + "dtl"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "dtl").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("等容舒张时间延长>80ms", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if(data.get(sname + "." + "irtl0").equals(""))p = new Paragraph("---",fontNormal);
		else{p = new Paragraph(data.get(sname + "." + "irtl0"), fontNormal);};
		//p = new Paragraph(data.get(sname + "." + "irtl0"), fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "irtl"))) {
			p = new Paragraph(data.get(sname + "." + "irtl"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "irtl").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		// 4
		c = new PdfPCell();
		p = new Paragraph("4", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("左心房扩大", fontNormal);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("左心房内径(LA)>40mm", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		p.setIndentationLeft(5);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if(data.get(sname + "." + "laid0").equals(""))p = new Paragraph("---",fontNormal);
		else{p = new Paragraph(data.get(sname + "." + "laid0"), fontNormal);};
		//p = new Paragraph(data.get(sname + "." + "laid0")+"mm", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "laid"))) {
			p = new Paragraph(data.get(sname + "." + "laid"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "laid").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		// 5
		c = new PdfPCell();
		p = new Paragraph("5", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("心电图QT间期延长：男性>0.45s，女性>0.46s", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(2);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if(data.get(sname + "." + "ecgl0").equals(""))p = new Paragraph("---",fontNormal);
		else{p = new Paragraph(data.get(sname + "." + "ecgl0"), fontNormal);};
		//p = new Paragraph(data.get(sname + "." + "ecgl0")+"s", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "ecgl"))) {
			p = new Paragraph(data.get(sname + "." + "ecgl"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "ecgl").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);

		// 6
		c = new PdfPCell();
		p = new Paragraph("6", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("心肌质量增加", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);

		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "myoqa"))) {
			p = new Paragraph(data.get(sname + "." + "myoqa"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "myoqa").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);

		// 7
		c = new PdfPCell();
		p = new Paragraph("7", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("B-型钠尿肽(BNP)和B-型钠尿肽前体(pro-BNP)升高", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "bnp"))) {
			p = new Paragraph(data.get(sname + "." + "bnp"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "bnp").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		// 8
		c = new PdfPCell();
		p = new Paragraph("8", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("肌钙蛋白Ⅰ水平升高", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "teup"))) {
			p = new Paragraph(data.get(sname + "." + "teup"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "teup").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		// 9
		c = new PdfPCell();
		p = new Paragraph("9", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("电生理异常", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "epabn"))) {
			p = new Paragraph(data.get(sname + "." + "epabn"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "epabn").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		// 10
		c = new PdfPCell();
		p = new Paragraph("10", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("变时性反应的异常", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "crabn"))) {
			p = new Paragraph(data.get(sname + "." + "crabn"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "crabn").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		// 11
		c = new PdfPCell();
		p = new Paragraph("11", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("机电解偶联", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "mec"))) {
			p = new Paragraph(data.get(sname + "." + "mec"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "mec").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		// 12
		c = new PdfPCell();
		p = new Paragraph("12", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("在运动、容量负荷增加及血管活性药物等应激情况下心输出量不能充分增加", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "unadd"))) {
			p = new Paragraph(data.get(sname + "." + "unadd"), fontNormal);
		} else {
			p = new Paragraph(data.get(sname + "." + "unadd").equals("1") ? "是"
					: "否", fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		// 结果
		c = new PdfPCell();
		p = new Paragraph("结果", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("请临床医生根据患者临床具体情况判定是否符合肝硬化心肌病诊断", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "ismyoc"))) {
			p = new Paragraph(data.get(sname + "." + "ismyoc"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "ismyoc").equals("1") ? "是" : "否",
					fontNormal);
			p.setAlignment(Element.ALIGN_CENTER);
		}

		c.addElement(p);
		table.addCell(c);
		// 备注
		c = new PdfPCell();
		p = new Paragraph("备注", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("\"左心房内径\"说明：", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(4);
		c.addElement(p);
		table.addCell(c);
		
		for (PdfPRow row : table.getRows()) {
			for (PdfPCell ce : row.getCells()) {
				if (ce == null)
					continue;
				ce.setPadding(0);
				ce.setPaddingTop(1);
				ce.setPaddingBottom(1);
				ce.setUseAscender(true);
				ce.setMinimumHeight(25);
				ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}
		
		c = new PdfPCell();
		p = new Paragraph(
				"1、一般人左心房内径超过35mm即为左心房扩大，若患者身材高大，心脏体积也会相应扩大，请临床医生根据患者体型对左心房内径是否增大做出判断",
				fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(4);
		c.addElement(p);
		
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(40);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		table.addCell(c);
		
		c = new PdfPCell();
		p = new Paragraph(
				"2、一般患者采用M型测量左心室内径，如遇特殊患者，使用2D型测量四腔心，横径≥40mm和/或纵径≥50mm，即为左心房扩大",
				fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(4);
		c.addElement(p);
		
		c.setPadding(0);
		c.setPaddingTop(1);
		c.setPaddingBottom(1);
		c.setUseAscender(true);
		c.setMinimumHeight(40);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		table.addCell(c);
		

		cell.addElement(table);
	}

	/**
	 * 肝性脑病诊断标准
	 * 
	 * @param cell
	 * @throws Exception
	 */
	private void layoutHE(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(8);
		table.setSplitLate(false);
		table.setSplitRows(true);
		// 设置表格宽度100%
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 10,10,18, 8, 25, 8, 13, 8 });
		Font fontNormal = new Font(font, 12, Font.NORMAL);

		// 表头
		PdfPCell c = new PdfPCell();
		Paragraph p = new Paragraph("分期", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("神志改变", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("神经系统体征", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(2);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("脑电图", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(2);
		c.addElement(p);
		table.addCell(c);

		// 前驱期
		c = new PdfPCell();
		p = new Paragraph("前驱期", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(2);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("轻度性格改变和行为异常如欣快、激动或抑郁寡言，衣冠不整或随地便溺", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(2);
		c.addElement(p);
//		c.setUseAscender(true);
		c.setMinimumHeight(60);
//		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pds_ams1"))) {
			p = new Paragraph(data.get(sname + "." + "pds_ams1"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "pds_ams1").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("可出现扑翼样震颤", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(2);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pds_ns"))) {
			p = new Paragraph(data.get(sname + "." + "pds_ns"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "pds_ns").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(2);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("轻度变化或正常", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(2);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pds_eeg"))) {
			p = new Paragraph(data.get(sname + "." + "pds_eeg"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "pds_eeg").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(2);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("应答尚准确，但吐词不清且较缓慢", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(2);
		c.addElement(p);
		c.setMinimumHeight(40);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pds_ams2"))) {
			p = new Paragraph(data.get(sname + "." + "pds_ams2"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "pds_ams2").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		// 昏迷前期
		c = new PdfPCell();
		p = new Paragraph("昏迷前期", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(6);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("嗜睡，昼睡夜醒", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(2);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pcs_ams1"))) {
			p = new Paragraph(data.get(sname + "." + "pcs_ams1"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "pcs_ams1").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("存在扑翼样震颤", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pcs_ns1"))) {
			p = new Paragraph(data.get(sname + "." + "pcs_ns1"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "pcs_ns1").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("脑电图有特征性异常", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(6);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pcs_eeg"))) {
			p = new Paragraph(data.get(sname + "." + "pcs_eeg"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "pcs_eeg").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(6);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("定向力丧失", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(2);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pcs_ams2"))) {
			p = new Paragraph(data.get(sname + "." + "pcs_ams2"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "pcs_ams2").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("腱反射亢进", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pcs_ns2"))) {
			p = new Paragraph(data.get(sname + "." + "pcs_ns2"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "pcs_ns2").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("理解力、计算力下降", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(2);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pcs_ams3"))) {
			p = new Paragraph(data.get(sname + "." + "pcs_ams3"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "pcs_ams3").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("肌张力增高", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pcs_ns3"))) {
			p = new Paragraph(data.get(sname + "." + "pcs_ns3"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "pcs_ns3").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("行为失常，幻觉、恐惧、狂躁。精神错乱，意识模糊", fontNormal);
		p.setIndentationLeft(5);
		c.setColspan(2);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pcs_ams4"))) {
			p = new Paragraph(data.get(sname + "." + "pcs_ams4"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "pcs_ams4").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("踝阵挛", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pcs_ns4"))) {
			p = new Paragraph(data.get(sname + "." + "pcs_ns4"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "pcs_ns4").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("Babinski征阳性", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pcs_ns5"))) {
			p = new Paragraph(data.get(sname + "." + "pcs_ns5"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "pcs_ns5").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("不随意运动及运动失调", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "pcs_ns6"))) {
			p = new Paragraph(data.get(sname + "." + "pcs_ns6"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "pcs_ns6").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		// 昏睡期
		c = new PdfPCell();
		p = new Paragraph("昏睡期", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(5);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("昏睡但能唤醒", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(2);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "ls_ams1"))) {
			p = new Paragraph(data.get(sname + "." + "ls_ams1"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "ls_ams1").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("扑翼样震颤", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "ls_ns1"))) {
			p = new Paragraph(data.get(sname + "." + "ls_ns1"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "ls_ns1").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("脑电图异常", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(5);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "ls_eeg"))) {
			p = new Paragraph(data.get(sname + "." + "ls_eeg"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "ls_eeg").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(5);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("意识不清及幻觉", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(2);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "ls_ams2"))) {
			p = new Paragraph(data.get(sname + "." + "ls_ams2"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "ls_ams2").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("肌张力增高", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "ls_ns2"))) {
			p = new Paragraph(data.get(sname + "." + "ls_ns2"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "ls_ns2").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("理解力及计算力丧失", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(2);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "ls_ams3"))) {
			p = new Paragraph(data.get(sname + "." + "ls_ams3"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "ls_ams3").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("腱反射亢进", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "ls_ns3"))) {
			p = new Paragraph(data.get(sname + "." + "ls_ns3"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "ls_ns3").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("踝阵挛", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "ls_ns4"))) {
			p = new Paragraph(data.get(sname + "." + "ls_ns4"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "ls_ns4").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("Babinski征", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "ls_ns5"))) {
			p = new Paragraph(data.get(sname + "." + "ls_ns5"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "ls_ns5").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		// 昏迷期
		c = new PdfPCell();
		p = new Paragraph("昏迷期", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(9);
		c.addElement(p);
		table.addCell(c);
		
		c = new PdfPCell();
		p = new Paragraph("浅昏迷", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);
		
		c = new PdfPCell();
		p = new Paragraph("神志完全丧失，不能唤醒", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "cs_ams1"))) {
			p = new Paragraph(data.get(sname + "." + "cs_ams1"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "cs_ams1").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("对痛刺激和不适体位尚有反应", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(40);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "cs_ns1"))) {
			p = new Paragraph(data.get(sname + "." + "cs_ns1"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "cs_ns1").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("脑电图明显异常", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "cs_eeg1"))) {
			p = new Paragraph(data.get(sname + "." + "cs_eeg1"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "cs_eeg1").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(3);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("腱反射", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "cs_ns2"))) {
			p = new Paragraph(data.get(sname + "." + "cs_ns2"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "cs_ns2").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("肌张力亢进", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "cs_ns3"))) {
			p = new Paragraph(data.get(sname + "." + "cs_ns3"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "cs_ns3").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		
		c = new PdfPCell();
		p = new Paragraph("深昏迷", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(6);
		c.addElement(p);
		table.addCell(c);
		
		
		c = new PdfPCell();
		p = new Paragraph("神志完全丧失，不能唤醒", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(6);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "cs_ams2"))) {
			p = new Paragraph(data.get(sname + "." + "cs_ams2"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "cs_ams2").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(6);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("各种反射消失", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "cs_ns4"))) {
			p = new Paragraph(data.get(sname + "." + "cs_ns4"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "cs_ns4").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("脑电图明显异常", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(6);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "cs_eeg2"))) {
			p = new Paragraph(data.get(sname + "." + "cs_eeg2"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "cs_eeg2").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(6);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("肌张力降低", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "cs_ns5"))) {
			p = new Paragraph(data.get(sname + "." + "cs_ns5"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "cs_ns5").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("瞳孔常散大", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "cs_ns6"))) {
			p = new Paragraph(data.get(sname + "." + "cs_ns6"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "cs_ns6").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("阵发性惊厥", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "cs_ns7"))) {
			p = new Paragraph(data.get(sname + "." + "cs_ns7"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "cs_ns7").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("踝阵挛", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "cs_ns8"))) {
			p = new Paragraph(data.get(sname + "." + "cs_ns8"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "cs_ns8").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("换气过度", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "cs_ns9"))) {
			p = new Paragraph(data.get(sname + "." + "cs_ns9"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "cs_ns9").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		// 结果
		c = new PdfPCell();
		p = new Paragraph("结果", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setRowspan(2);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("请临床医生根据患者临床具体情况判定是否符合肝性脑病诊断", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(6);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		if ("".equals(data.get(sname + "." + "result"))) {
			p = new Paragraph(data.get(sname + "." + "result"), fontNormal);
		} else {
			p = new Paragraph(
					data.get(sname + "." + "result").equals("1") ? "是" : "否",
					fontNormal);
		}
		p.setAlignment(Element.ALIGN_CENTER);
		c.addElement(p);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph("肝性脑病分期", fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(2);
		c.addElement(p);
		c.setMinimumHeight(23);
		table.addCell(c);
		c = new PdfPCell();
		String[] hesRst = new String[] { "亚临床期","前驱期", "昏迷前期", "昏睡期", "浅昏迷期", "深昏迷期" };
		String rst = "";
		if (data.get(sname + "." + "hesRst") != null
				&& data.get(sname + "." + "hesRst").length() > 0)
			rst = hesRst[Integer.parseInt(data.get(sname + "." + "hesRst")) - 1];
		p = new Paragraph(rst, fontNormal);
		p.setAlignment(Element.ALIGN_CENTER);
		c.setColspan(5);
		c.addElement(p);
		table.addCell(c);
		for (PdfPRow row : table.getRows()) {
			for (PdfPCell ce : row.getCells()) {
				if (ce == null)
					continue;
//				ce.setPadding(0);
//				ce.setPaddingTop(1);
//				ce.setPaddingBottom(1);
				ce.setUseAscender(true);
//				ce.setMinimumHeight(25);
				ce.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}
		}

		cell.addElement(table);
	}

	/**
	 * 器官功能评价系统
	 * 
	 * @param cell
	 * @throws Exception
	 */
	private void layoutOrganFunc(PdfPCell cell, String sname) throws Exception {
		PdfPTable table = new PdfPTable(1);
		table.setSplitLate(false);
		table.setSplitRows(true);
		// 设置表格宽度100%
		table.setWidthPercentage(100);
		Font myFontItem = new Font(font, 12, Font.BOLD);
		Font myFont = new Font(font, 12, Font.NORMAL);

		PdfPCell c = new PdfPCell();
		Paragraph p = new Paragraph("肝功能评价", myFontItem);
		p.setIndentationLeft(5);
		c.setMinimumHeight(25);
		c.addElement(p);
		c.setUseAscender(true);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);
		c = new PdfPCell();
		c.setPadding(0);
		
		
		
		PdfPTable subTable = new PdfPTable(4);
		subTable.setSplitLate(false);
		subTable.setSplitRows(true);
		// 设置表格宽度100%
		subTable.setWidthPercentage(100);
		subTable.setWidths(new int[] { 35, 15, 30, 20 });
		
		PdfPCell subCell = new PdfPCell();
		subCell.setColspan(4);
		p = new Paragraph("Child-Pugh评分", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);	
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		
		subCell = new PdfPCell();
		p = new Paragraph("肝性脑病(期)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);		
		subTable.addCell(subCell);
		
		subCell = new PdfPCell();
		String hepaticEp = data.get(sname + "." + "hepaticEp");
//		if (hepaticEp.equals(1)) {
//			hepaticEp = "无";
//		}
//		if (hepaticEp.equals("0")) {
//			hepaticEp = "";
//		}
//		if (hepaticEp.equals("2-")) {
//			hepaticEp = "1";
//		}
//		if (hepaticEp.equals("2+")) {
//			hepaticEp = "2";
//		}
		String value="";
		if (data.get(sname + "." + "hepaticEp") != null
				&& data.get(sname + "." + "hepaticEp").length() > 0) {
			int res = Integer.parseInt(data.get(sname + ".hepaticEp"));
			switch (res) {
			case 0:
				value = "";
				break;
			case 1:
				value = "无";
				break;
			case -2:
				value = "Ⅰ";
				break;
			case 2:
				value = "Ⅱ";
				break;
			case -3:
				value = "Ⅲ";
				break;
			case 3:
				value = "Ⅳ";
				break;
			}
		}
		p = new Paragraph(value, myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);		
		subTable.addCell(subCell);
		
		subCell = new PdfPCell();
		p = new Paragraph("腹水", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String ascites = data.get(sname + "." + "ascites");
		if (ascites.equals("1")) {
			ascites = "无";
		} else if (ascites.equals("2")) {
			ascites = "轻度";
		} else if (ascites.equals("3")) {
			ascites = "中重度";
		} else {
			ascites = "";
		}
		p = new Paragraph(ascites, myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("胆红素(μmol/L)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String cpBilirubin = data.get(sname + "." + "cpBilirubin");
		if (cpBilirubin.equals("1")) {
			cpBilirubin = "<34.2";
		} else if (cpBilirubin.equals("2")) {
			cpBilirubin = "34.2-51.3";
		} else if (cpBilirubin.equals("3")) {
			cpBilirubin = ">51.3";
		} else {
			cpBilirubin = "";
		}
		p = new Paragraph(cpBilirubin, myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("白蛋白(g/L)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String albumin = data.get(sname + "." + "albumin");
		if (albumin.equals("1")) {
			albumin = ">35";
		} else if (albumin.equals("2")) {
			albumin = "28-35";
		} else if (albumin.equals("3")) {
			albumin = "<28";
		} else {
			albumin = "";
		}
		p = new Paragraph(albumin, myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("凝血酶原时间较正常延长(秒)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String prothrombinTime = data.get(sname + "." + "prothrombinTime");
		if (prothrombinTime.equals("1")) {
			prothrombinTime = "<4";
		} else if (prothrombinTime.equals("2")) {
			prothrombinTime = "4-6";
		} else if (prothrombinTime.equals("3")) {
			prothrombinTime = ">6";
		} else {
			prothrombinTime = "";
		}
		p = new Paragraph(prothrombinTime, myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("Child-Pugh总积分", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "totalScore"), myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("Child-Pugh分级", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "scoreGrade"), myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		subCell.setColspan(3);
		subTable.addCell(subCell);
		// 补白
		// subTable.addCell("");
		// subTable.addCell("");
		c.addElement(subTable);
		table.addCell(c);
		subCell = new PdfPCell();
		subCell.setColspan(4);
		p = new Paragraph("MELD评分", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("肌酐(μmol/L)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String creatinine = data.get(sname + "." + "creatinine");
		if (!creatinine.equals("")) {
			creatinine = creatinine + "";
		}
		p = new Paragraph(creatinine, myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("胆红素(μmol/L)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String meldBilirubin = data.get(sname + "." + "meldBilirubin");
		if (!meldBilirubin.equals("")) {
			meldBilirubin = meldBilirubin + "";
		}
		p = new Paragraph(meldBilirubin, myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("INR", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "meldInr"), myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("病因", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String etiology = data.get(sname + "." + "etiology");
		p.setIndentationLeft(5);
		if (etiology.equals("0-")) {
			hepaticEp = "胆汁淤积性";
		} else if (etiology.equals("0+")) {
			hepaticEp = "酒精性肝硬化";
		} else if (etiology.equals("1")) {
			hepaticEp = "其它";
		} else {
			hepaticEp = "";
		}
		p = new Paragraph(hepaticEp, myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("MELD评分结果", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "meldResult"), myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		subCell.setColspan(3);
		subTable.addCell(subCell);
		// 补白
		// subTable.addCell("");
		// subTable.addCell("");

		c = new PdfPCell();
		p = new Paragraph("心功能评价", myFontItem);
		p.setIndentationLeft(5);
		c.addElement(p);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);

		c = new PdfPCell();
		c.setPadding(0);
		subTable = new PdfPTable(2);
		subTable.setSplitLate(false);
		subTable.setSplitRows(true);
		// 设置表格宽度100%
		subTable.setWidthPercentage(100);
		subTable.setWidths(new int[] { 30, 70 });
		subCell = new PdfPCell();
		p = new Paragraph("心电图", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setPaddingTop(5);
		subCell.setPaddingBottom(5);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String xindian = data.get(sname + "." + "xindian0");
		if (xindian.equals("")) {
			xindian = data.get(sname + "." + "xindian");
		} else {
			xindian = xindian.substring(1, xindian.length() - 1);
		}
		p = new Paragraph(xindian, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setPaddingTop(5);
		subCell.setPaddingBottom(5);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("24小时动态心电图", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String dongXindian = data.get(sname + "." + "dongXindian0");
		if (dongXindian.equals("")) {
			dongXindian = data.get(sname + "." + "dongXindian");
		} else {
			dongXindian = dongXindian.substring(1, dongXindian.length() - 1);
		}
		p = new Paragraph(dongXindian, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setPaddingTop(5);
		subCell.setPaddingBottom(5);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("超声心动检查", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String csXindong = data.get(sname + "." + "csXindong0");
		if (csXindong.equals("")) {
			csXindong = data.get(sname + "." + "csXindong");
		} else {
			csXindong = csXindong.substring(1, csXindong.length() - 1);
		}
		p = new Paragraph(csXindong, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setPaddingTop(5);
		subCell.setPaddingBottom(5);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		subTable.addCell(subCell);
		
		subCell = new PdfPCell();
		p = new Paragraph("心功能分级(NYHA分级)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String ntha = data.get(sname + "." + "ntha0");
		if (ntha.equals("")) {
			ntha = data.get(sname + "." + "ntha");
		} else {
			ntha = ntha.substring(1, ntha.length() - 1);
		}
		p = new Paragraph(ntha, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setPaddingTop(5);
		subCell.setPaddingBottom(5);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		subTable.addCell(subCell);
		
		c.addElement(subTable);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("肺功能评价", myFontItem);
		p.setIndentationLeft(5);
		c.addElement(p);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);

		c = new PdfPCell();
		c.setPadding(0);
		subTable = new PdfPTable(2);
		subTable.setSplitLate(false);
		subTable.setSplitRows(true);
		// 设置表格宽度100%
		subTable.setWidthPercentage(100);
		subTable.setWidths(new int[] { 30, 70 });
		subCell = new PdfPCell();
		p = new Paragraph("胸部X线", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String chestX = data.get(sname + "." + "chestX0");
		if (chestX.equals("")) {
			chestX = data.get(sname + "." + "chestX");
		} else {
			chestX = chestX.substring(1, chestX.length() - 1);
		}
		p = new Paragraph(chestX, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setPaddingTop(5);
		subCell.setPaddingBottom(5);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("胸部CT", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String chestCT = data.get(sname + "." + "chestCT0");
		if (chestCT.equals("")) {
			chestCT = data.get(sname + "." + "chestCT");
		} else {
			chestCT = chestCT.substring(1, chestCT.length() - 1);
		}
		p = new Paragraph(chestCT, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setPaddingTop(5);
		subCell.setPaddingBottom(5);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("肺功能检查", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String chestCheck = data.get(sname + "." + "chestCheck0");
		if (chestCheck.equals("")) {
			chestCheck = data.get(sname + "." + "chestCheck");
		} else {
			chestCheck = chestCheck.substring(1, chestCheck.length() - 1);
		}
		p = new Paragraph(chestCheck, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setPaddingTop(5);
		subCell.setPaddingBottom(5);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("血氧分压(mmHg)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String xyFengya = data.get(sname + "." + "xyFengya0");
		if (xyFengya.equals("")) {
			xyFengya = data.get(sname + "." + "xyFengya");
			if (!xyFengya.equals("")) {
				xyFengya = xyFengya + "";
			}
		} else {
			xyFengya = xyFengya.substring(1, xyFengya.length() - 1);
		}
		p = new Paragraph(xyFengya, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("血二氧化碳分压(mmHg)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String xeFengya = data.get(sname + "." + "xeFengya0");
		if (xeFengya.equals("")) {
			xeFengya = data.get(sname + "." + "xeFengya");
			if (!xeFengya.equals("")) {
				xeFengya = xeFengya + "";
			}
		} else {
			xeFengya = xeFengya.substring(1, xeFengya.length() - 1);
		}
		p = new Paragraph(xeFengya, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("血氧饱和度(%)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String xyBaohe = data.get(sname + "." + "xyBaohe0");
		if (xyBaohe.equals("")) {
			xyBaohe = data.get(sname + "." + "xyBaohe");
			if (!xyBaohe.equals("")) {
				xyBaohe = xyBaohe + "";
			}
		} else {
			xyBaohe = xyBaohe.substring(1, xyBaohe.length() - 1);
		}
		p = new Paragraph(xyBaohe, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		subTable.addCell(subCell);
		c.addElement(subTable);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("肾功能评价", myFontItem);
		p.setIndentationLeft(5);

		c.addElement(p);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);

		c = new PdfPCell();
		c.setPadding(0);
		subTable = new PdfPTable(2);
		subTable.setSplitLate(false);
		subTable.setSplitRows(true);
		// 设置表格宽度100%
		subTable.setWidthPercentage(100);
		subTable.setWidths(new int[] { 30, 70 });
		subCell = new PdfPCell();
		p = new Paragraph("尿量(ml/日)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String niaovolum = data.get(sname + "." + "niaovolum0");
		if (niaovolum.equals("")) {
			niaovolum = data.get(sname + "." + "niaovolum");
			if (!niaovolum.equals("")) {
				niaovolum = niaovolum + "";
			}
		} else {
			niaovolum = niaovolum.substring(1, niaovolum.length() - 1);
		}
		p = new Paragraph(niaovolum, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("24小时尿蛋白", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String ndanbai = data.get(sname + "." + "ndanbai0");
		if (ndanbai.equals("")) {
			ndanbai = data.get(sname + "." + "ndanbai");
			String ndanbaiYDesc = data.get(sname + "." + "ndanbaiYDesc");
			if (ndanbai.equals("阳性") && !ndanbaiYDesc.equals("")) {
				ndanbai = ndanbai + " " + ndanbaiYDesc + "g/24h";
			}
		} else {
			ndanbai = ndanbai.substring(1, ndanbai.length() - 1);
		}
		p = new Paragraph(ndanbai, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("血尿素氮(mmol/L)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String xueniaoN = data.get(sname + "." + "xueniaoN0");
		if (xueniaoN.equals("")) {
			xueniaoN = data.get(sname + "." + "xueniaoN");
			if (!xueniaoN.equals("")) {
				xueniaoN = xueniaoN + "";
			}
		} else {
			xueniaoN = xueniaoN.substring(1, xueniaoN.length() - 1);
		}
		p = new Paragraph(xueniaoN, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("血肌酐(μmol/L)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String xuejigan = data.get(sname + "." + "xuejigan0");
		if (xuejigan.equals("")) {
			xuejigan = data.get(sname + "." + "xuejigan");
			if (!xuejigan.equals("")) {
				xuejigan = xuejigan + "";
			}
		} else {
			xuejigan = xuejigan.substring(1, xuejigan.length() - 1);
		}
		p = new Paragraph(xuejigan, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("内生肌酐清除率(ml/min)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String ccr = data.get(sname + "." + "ccr0");
		if (ccr.equals("")) {
			ccr = data.get(sname + "." + "ccr");
			if (!ccr.equals("")) {
				ccr = ccr + "";
			}
		} else {
			ccr = ccr.substring(1, ccr.length() - 1);
		}
		p = new Paragraph(ccr, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("肾脏超声", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String szChaosheng = data.get(sname + "." + "szChaosheng0");
		if (szChaosheng.equals("")) {
			szChaosheng = data.get(sname + "." + "szChaosheng");
		} else {
			szChaosheng = szChaosheng.substring(1, szChaosheng.length() - 1);
		}
		p = new Paragraph(szChaosheng, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setPaddingTop(5);
		subCell.setPaddingBottom(5);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		c.addElement(subTable);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("凝血功能评价", myFontItem);
		p.setIndentationLeft(5);

		c.addElement(p);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);

		c = new PdfPCell();
		c.setPadding(0);
		subTable = new PdfPTable(2);
		subTable.setSplitLate(false);
		subTable.setSplitRows(true);
		// 设置表格宽度100%
		subTable.setWidthPercentage(100);
		subTable.setWidths(new int[] { 30, 70 });
		subCell = new PdfPCell();
		
		//p = new Paragraph("PLT(×10^9/L)", myFont);
		// lhf调用类对格式转换
		//p = new Paragraph("PLT(×10<sup>9</sup>/L)", myFont);
		p = new Paragraph("PLT(×10^9^L)", myFont);
		p=ConvertSubSupUtil.parseSubOrSup(p);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String ningxueFunc = data.get(sname + "." + "ningxueFunc0");
		if (ningxueFunc.equals("")) {
			ningxueFunc = data.get(sname + "." + "ningxueFunc");
			if (!ningxueFunc.equals("")) {
				ningxueFunc = ningxueFunc + "";
			}
		} else {
			ningxueFunc = ningxueFunc.substring(1, ningxueFunc.length() - 1);
		}
		p = new Paragraph(ningxueFunc, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("PTA(%)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String pta = data.get(sname + "." + "pta0");
		if (pta.equals("")) {
			pta = data.get(sname + "." + "pta");
			if (!pta.equals("")) {
				pta = pta + "";
			}
		} else {
			pta = pta.substring(1, pta.length() - 1);
		}
		p = new Paragraph(pta, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("INR", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String inr = data.get(sname + "." + "inr0");
		if (inr.equals("")) {
			inr = data.get(sname + "." + "inr");
		} else {
			inr = inr.substring(1, inr.length() - 1);
		}
		p = new Paragraph(inr, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("DIC", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String dic = data.get(sname + "." + "dic0");
		if (dic.equals("")) {
			dic = data.get(sname + "." + "dic");
		} else {
			dic = dic.substring(1, dic.length() - 1);
		}
		p = new Paragraph(dic, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		subTable.addCell(subCell);
		c.addElement(subTable);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("胃肠道功能评价", myFontItem);
		p.setIndentationLeft(5);

		c.addElement(p);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);

		c = new PdfPCell();
		c.setPadding(0);
		subTable = new PdfPTable(2);
		subTable.setSplitLate(false);
		subTable.setSplitRows(true);
		// 设置表格宽度100%
		subTable.setWidthPercentage(100);
		subTable.setWidths(new int[] { 30, 70 });
		subCell = new PdfPCell();
		p = new Paragraph("胃镜", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String weijing = data.get(sname + "." + "weijing0");
		if (weijing.equals("")) {
			weijing = data.get(sname + "." + "weijing");
		} else {
			weijing = weijing.substring(1, weijing.length() - 1);
		}
		p = new Paragraph(weijing, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setPaddingTop(5);
		subCell.setPaddingBottom(5);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("胃电图", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setPaddingTop(5);
		subCell.setPaddingBottom(5);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String weidiantu = data.get(sname + "." + "weidiantu0");
		if (weidiantu.equals("")) {
			weidiantu = data.get(sname + "." + "weidiantu");
		} else {
			weidiantu = weidiantu.substring(1, weidiantu.length() - 1);
		}
		p = new Paragraph(weidiantu, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("血乳酸(mmol/L)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String xuerusuan = data.get(sname + "." + "xuerusuan0");
		if (xuerusuan.equals("")) {
			xuerusuan = data.get(sname + "." + "xuerusuan");
			if (!xuerusuan.equals("")) {
				xuerusuan = xuerusuan + "";
			}
		} else {
			xuerusuan = xuerusuan.substring(1, xuerusuan.length() - 1);
		}
		p = new Paragraph(xuerusuan, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		c.addElement(subTable);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("神经系统功能评价", myFontItem);
		p.setIndentationLeft(5);

		c.addElement(p);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);

		c = new PdfPCell();
		c.setPadding(0);
		subTable = new PdfPTable(2);
		subTable.setSplitLate(false);
		subTable.setSplitRows(true);
		// 设置表格宽度100%
		subTable.setWidthPercentage(100);
		subTable.setWidths(new int[] { 30, 70 });
		subCell = new PdfPCell();
		p = new Paragraph("肝性脊髓病", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String gxjisui = data.get(sname + "." + "gxjisui0");
		if (gxjisui.equals("")) {
			gxjisui = data.get(sname + "." + "gxjisui");
		} else {
			gxjisui = gxjisui.substring(1, gxjisui.length() - 1);
		}
		p = new Paragraph(gxjisui, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setPaddingTop(5);
		subCell.setPaddingBottom(5);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("头颅CT", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String headCT = data.get(sname + "." + "headCT0");
		if (headCT.equals("")) {
			headCT = data.get(sname + "." + "headCT");
		} else {
			headCT = headCT.substring(1, headCT.length() - 1);
		}
		p = new Paragraph(headCT, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setPaddingTop(5);
		subCell.setPaddingBottom(5);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		c.addElement(subTable);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("营养状态评价", myFontItem);
		p.setIndentationLeft(5);
		c.addElement(p);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);

		c = new PdfPCell();
		c.setPadding(0);
		subTable = new PdfPTable(2);
		subTable.setSplitLate(false);
		subTable.setSplitRows(true);
		// 设置表格宽度100%
		subTable.setWidthPercentage(100);
		subTable.setWidths(new int[] { 30, 70 });
		subCell = new PdfPCell();
		p = new Paragraph("BMI", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String yingyangBmi = data.get(sname + "." + "yingyangBmi0");
		String weight = data.get(sname + "." + "weight");
		String height = data.get(sname + "." + "height");
		if (yingyangBmi.equals("")) {
			yingyangBmi = data.get(sname + "." + "yingyangBmi") + "  (体重："
					+ weight + "kg，身高：" + height + "m)";
		} else {
			yingyangBmi = yingyangBmi.substring(1, yingyangBmi.length() - 1)
					+ "  (体重：" + weight + "kg，身高：" + height + "m)";
		}
		p = new Paragraph(yingyangBmi, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("营养风险筛查", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String yingyangNrs = data.get(sname + "." + "yingyangNrs0");
		if (yingyangNrs.equals("")) {
			yingyangNrs = data.get(sname + "." + "yingyangNrs");
		} else {
			yingyangNrs = yingyangNrs.substring(1, yingyangNrs.length() - 1);
		}
		p = new Paragraph(yingyangNrs, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("血清白蛋白(g/L)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String xqbdb = data.get(sname + "." + "xqbdb0");
		if (xqbdb.equals("")) {
			xqbdb = data.get(sname + "." + "xqbdb");
			if (!xqbdb.equals("")) {
				xqbdb = xqbdb + "";
			}
		} else {
			xqbdb = xqbdb.substring(1, xqbdb.length() - 1);
		}
		p = new Paragraph(xqbdb, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		p = new Paragraph("血清前白蛋白(mg/L)", myFont);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String xqqbdb = data.get(sname + "." + "xqqbdb0");
		if (xqqbdb.equals("")) {
			xqqbdb = data.get(sname + "." + "xqqbdb");
			if (!xqqbdb.equals("")) {
				xqqbdb = xqqbdb + "";
			}
		} else {
			xqqbdb = xqqbdb.substring(1, xqqbdb.length() - 1);
		}
		p = new Paragraph(xqqbdb, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		//p = new Paragraph("淋巴细胞计数(×10^9/L)", myFont);
		//lhf调用相关类进行格式转换
		p = new Paragraph("淋巴细胞计数", myFont);
		Paragraph p2 = new Paragraph("(×10^9^/L)", myFont);
		p2=ConvertSubSupUtil.parseSubOrSup(p2);
		p.add(p2);
		p.setAlignment(Element.ALIGN_CENTER);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		subTable.addCell(subCell);
		subCell = new PdfPCell();
		String lbxbjs = data.get(sname + "." + "lbxbjs0");
		if (lbxbjs.equals("")) {
			lbxbjs = data.get(sname + "." + "lbxbjs");
			if (!lbxbjs.equals("")) {
				lbxbjs = lbxbjs + "";
			}
		} else {
			lbxbjs = lbxbjs.substring(1, lbxbjs.length() - 1);
		}
		p = new Paragraph(lbxbjs, myFont);
		p.setIndentationLeft(5);
		subCell.addElement(p);
		subCell.setUseAscender(true);
		subCell.setMinimumHeight(25);
		subCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		subTable.addCell(subCell);
		c.addElement(subTable);
		table.addCell(c);

		c = new PdfPCell();
		p = new Paragraph("其他器官评价", myFontItem);
		p.setIndentationLeft(5);
		c.addElement(p);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c);
		c = new PdfPCell();
		p = new Paragraph(data.get(sname + "." + "otherOrgan"), myFont);
		p.setIndentationLeft(5);
		c.addElement(p);
		c.setUseAscender(true);
		c.setMinimumHeight(25);
		c.setVerticalAlignment(Element.ALIGN_MIDDLE);		
		table.addCell(c);
//		for (PdfPRow row : table.getRows()) {
//			for (PdfPCell ce : row.getCells()) {
//				if (ce == null)
//					continue;
//				ce.setPadding(0);
				c.setPaddingTop(5);
				c.setPaddingBottom(5);
				c.setUseAscender(true);
				c.setMinimumHeight(25);
				c.setVerticalAlignment(Element.ALIGN_MIDDLE);
//			}
//		}
		cell.addElement(table);
	}

	private PdfPTable getHeader() throws DocumentException,
			MalformedURLException, IOException, SQLException {
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 8, 23, 38, 10, 21 });
		// 图片
		PdfPCell cell = new PdfPCell();
		cell.setPadding(1);
		cell.setPaddingLeft(2);
		cell.setPaddingTop(5);
		cell.setRowspan(2);
		Image logo = Image.getInstance(root + "PUBLIC/print/youanLogo.jpg");
		cell.addElement(logo);
		table.addCell(cell);
		// 中英文医院名称
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setColspan(4);
		cell.setPaddingTop(12);
		Font myFont = new Font(font, 16, Font.BOLD);
		Paragraph p = new Paragraph("首都医科大学附属北京佑安医院", myFont);
		cell.addElement(p);
		myFont = new Font(font, 9, Font.BOLD);
		p = new Paragraph("Beijing YouAn Hospital,Capital Medical University",
				myFont);
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
		myFont = new Font(font, 14, Font.BOLD);
		p = new Paragraph(data.get("title"), myFont);
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
		myFont = new Font(font, 9, Font.NORMAL);
		p = new Paragraph("姓  名：" + data.get("姓名"), myFont);
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
		p = new Paragraph("第 " + data.get("住院次数") + " 次住院", myFont);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 病案号
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		p = new Paragraph("病案号：" + data.get("病案号"), myFont);
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

	/**
	 * 准备打印数据
	 * 
	 * @throws Exception
	 */
	private void preparData() throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			// 病人住院信息
			InHspCaseMaster cm = (InHspCaseMaster) session.get(
					InHspCaseMaster.class, Long.parseLong(data.get("kid")));
			if (cm != null) {
				data.put("住院次数", cm.getInHspTimes() + "");
				data.put("住院时间", dateFormat.format(cm.getInHspDate()));
				Patient p = (Patient) session.get(Patient.class, cm
						.getPatientId());
				if (p != null) {
					data.put("姓名", p.getPatientName());
					data.put("病案号", p.getPatientNo());
				}
			}
			// 总评分信息
			ScoreComment scoreComment = (ScoreComment) session.get(
					ScoreComment.class, Long.parseLong(data.get("scid")));
			if (scoreComment != null) {
				data
						.put("评分时间", dateFormat.format(scoreComment.getScoreTime()));
				Long scoreStageFlag = scoreComment.getScoreStage();
				String scoreStage = "";
				if (scoreStageFlag == 1) {
					scoreStage = "入院";
				} else if (scoreStageFlag == 2) {
					scoreStage = "术前";
				} else if (scoreStageFlag == 3) {
					scoreStage = "术后";
				} else if (scoreStageFlag == 4) {
					scoreStage = "住院期间";
				} else if (scoreStageFlag == 5) {
					scoreStage = "出院";
				}
				data.put("scoreStage", scoreStage);
				data.put("doc1", DictionaryUtil.getIndependentDictionaryText(
						"userName", scoreComment.getDoc1() + ""));
				data.put("doc2", DictionaryUtil.getIndependentDictionaryText(
						"userName", scoreComment.getDoc2() + ""));
				data.put("doc3", DictionaryUtil.getIndependentDictionaryText(
						"userName", scoreComment.getDoc3() + ""));
			}
			// 子页面评分信息
			ScoreSetMeal scoreSetMeal = (ScoreSetMeal) session.get(
					ScoreSetMeal.class, Long.parseLong(data.get("ssmid")));
			if (scoreSetMeal != null) {
				data.put("title", scoreSetMeal.getName());
				data.put("printPages", scoreSetMeal.getSubScoreItems());
				String[] printPages = scoreSetMeal.getSubScoreItems().split(
						"\\*");
				for (int i = 0; i < printPages.length; i++) {
					// 压入子页面数据
					preparPageData(printPages[i], session);
				}
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	/**
	 * 准备子页面数据
	 * 
	 * @param pageflag
	 * @param session
	 * @throws Exception
	 */
	private void preparPageData(String pageflag, Session session)
			throws Exception {
		Long scid = Long.parseLong(data.get("scid"));
		try {
			Object entity = null;
			if (pageflag.equals("Child-Pugh")) {
				entity = impl.childPugh_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("MELD")) {
				entity = impl.meld_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("BCLC")) {
				entity = impl.bclc_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("BMI")) {
				entity = impl.bmi_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("CLIP")) {
				entity = impl.clip_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("GCS")) {
				entity = impl.gcs_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("PLC")) {
				entity = impl.plc_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("TNM")) {
				entity = impl.tnm_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("LCTOS")) {
				entity = impl.lctos_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("HE")) {
				entity = impl.he_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("OrganFunc")) {
				entity = impl.organFunc_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("HC")) {
				entity = impl.hc_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("HRS")) {
				entity = impl.hrs_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("HPS")) {
				entity = impl.hps_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("Hepatomyocardosis")) {
				entity = impl.hepatomyocardosis_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("APACHEII")) {
				entity = impl.apacheii_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("PHI")) {
				entity = impl.phi_findByScidzhuyuan(session, scid);
			} else if (pageflag.equals("TS")) {
				entity = impl.ts_findByScidzhuyuan(session, scid);
			}
			processEntityField(entity, pageflag);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 将实体中的数据压入data数据中心
	 * 
	 * @param entity
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void processEntityField(Object entity, String pageflag)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (entity == null)
			return;
		Field[] flds = entity.getClass().getDeclaredFields();
		for (Field f : flds) {
			String name = f.getName();
			String methodName = "get" + name.substring(0, 1).toUpperCase()
					+ name.substring(1, name.length());
			try {
				Object value = entity.getClass().getDeclaredMethod(methodName,
						new Class[] {}).invoke(entity, new Object[] {});
				if (f.getType().equals(java.sql.Date.class)
						|| f.getType().equals(java.util.Date.class)) {

				} else {
					String temp = "";
					if (value != null)
						temp = value.toString();
					data.put(pageflag + "." + name, temp);
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	class PageNumHelper extends PdfPageEventHelper {
		private BaseFont font;

		public PageNumHelper(BaseFont font) {
			this.font = font;
		}

		@Override
		public void onStartPage(PdfWriter writer, Document document) {
			PdfContentByte cb = writer.getDirectContent();
			cb.saveState();
			cb.setFontAndSize(font, 9);
			cb.beginText();
			cb
					.showTextAligned(Element.ALIGN_CENTER, "第 "
							+ writer.getPageNumber() + " 页", document
							.getPageSize().getWidth() / 2, document
							.getPageSize().getHeight() - 95, 0);
			cb.endText();
			cb.restoreState();
			cb = writer.getDirectContent();
			cb.setLineWidth(1);
			if (document.getPageNumber() % 2 == 0) {
				cb.moveTo(document.getPageSize().getWidth() - 20 * dl, 5 * dl);
				cb.lineTo(document.getPageSize().getWidth() - 20 * dl, document
						.getPageSize().getHeight()
						- 10 * dl);
			} else {
				cb.moveTo(20 * dl, 5 * dl);
				cb
						.lineTo(20 * dl, document.getPageSize().getHeight()
								- 10 * dl);
			}
			cb.stroke();
		}
	}
}
