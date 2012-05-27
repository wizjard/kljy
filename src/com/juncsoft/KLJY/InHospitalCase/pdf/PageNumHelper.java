package com.juncsoft.KLJY.InHospitalCase.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 页码工具类
 * @author liugang
 *
 */
public class PageNumHelper extends PdfPageEventHelper{
	private BaseFont fontGeneral;

	public PageNumHelper(BaseFont fontGeneral) {
		this.fontGeneral = fontGeneral;
	}
	
	/**
	 * 页头
	 */
	private PDFHeader pdfHeader = new PDFHeader();
	
	@Override
	public void onStartPage(PdfWriter writer, Document document) {
			PdfContentByte cb = writer.getDirectContent();
			cb.saveState();
			cb.setFontAndSize(fontGeneral, 9);
			cb.beginText();
			cb.showTextAligned(Element.ALIGN_CENTER, "第 "
					+ writer.getPageNumber() + " 页", document.getPageSize()
					.getWidth() / 2,
					document.getPageSize().getHeight() - 98, 0);
			cb.endText();
			cb.restoreState();
			cb = writer.getDirectContent();
			cb.setLineWidth(1);
			if (document.getPageNumber() % 2 == 0) {
				cb.moveTo(document.getPageSize().getWidth() - 20 * pdfHeader.dl,
						5 * pdfHeader.dl);
				cb.lineTo(document.getPageSize().getWidth() - 20 * pdfHeader.dl,
						document.getPageSize().getHeight() - 10 * pdfHeader.dl);
			} else {
				cb.moveTo(20 * pdfHeader.dl, 5 * pdfHeader.dl);
				cb.lineTo(20 * pdfHeader.dl, document.getPageSize().getHeight() - 10
						* pdfHeader.dl);
			}
			cb.stroke();
	}

}
