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
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PhysicalExamination;
import com.juncsoft.KLJY.util.DictionaryUtil;

/**
 * 体格检查
 * @author liugang
 *
 */
public class PhysicalExaminationPDF {
	public void getContent(PdfPTable tableContent,Font fontGeneral,Font myFontItem,Long id,Session session,Map map,DateFormat df) throws Exception{
		PhysicalExamination exam = null;
		String pageCode = "EMR-liver-PhysicalExamination";
		List list = session.createCriteria(PhysicalExamination.class).add(
				Restrictions.eq("caseId", id)).list();
		if (list.size() > 0) {
			exam = (PhysicalExamination) list.get(0);
		}
		if(exam != null){
			
		
		PdfPCell cellC = new PdfPCell();
		Paragraph p = new Paragraph("体格检查", myFontItem);
		p.setAlignment(Element.ALIGN_CENTER);
		cellC.setColspan(5);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		
		//空白列
		this.blankOne(cellC,p, fontGeneral, tableContent);
		//体温
		cellC = new PdfPCell();
		p = new Paragraph("体温:"+exam.getSmtz_tiwen() + "℃", fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//血压
		cellC = new PdfPCell();
		if (exam.getSmtz_xueya2() != null
				&& exam.getSmtz_xueya2().trim().length() > 0) {
			p = new Paragraph("血压:"+exam.getSmtz_xueya() + "/"+exam.getSmtz_xueya2() + "mmHg", fontGeneral);
		} else {
			p = new Paragraph("血压:"+exam.getSmtz_xueya() + "mmHg", fontGeneral);
		}
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//脉搏
		cellC = new PdfPCell();
		p = new Paragraph("脉搏:"+exam.getSmtz_maibo() + "次/分", fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//呼吸
		cellC = new PdfPCell();
		p = new Paragraph("呼吸:"+exam.getSmtz_huxi() + "次/分", fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//一般情况
		cellC = new PdfPCell();
		p = new Paragraph("一般情况:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//发育
		String ybzc_fayu = DictionaryUtil.getPublicDictionaryText(pageCode,
				"ybzc_fayu", exam.getYbzc_fayu());
		if (ybzc_fayu.equals("其它"))
			ybzc_fayu = exam.getYbzc_fayu0();
		cellC = new PdfPCell();
		p = new Paragraph("发育:"+ybzc_fayu, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//神志
		String ybzc_shenzhi = DictionaryUtil.getPublicDictionaryText(
				pageCode, "ybzc_shenzhi", exam.getYbzc_shenzhi());
		if (ybzc_shenzhi.equals("其它"))
			ybzc_shenzhi = exam.getYbzc_shenzhi0();
		cellC = new PdfPCell();
		p = new Paragraph("神志:"+ybzc_shenzhi, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//表情
		String ybzc_biaoqing = DictionaryUtil.getPublicDictionaryText(
				pageCode, "ybzc_biaoqing", exam.getYbzc_biaoqing());
		if (ybzc_biaoqing.equals("其它"))
			ybzc_biaoqing = exam.getYbzc_biaoqing0();
		cellC = new PdfPCell();
		p = new Paragraph("表情:"+ybzc_biaoqing, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		
		//空白列
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//面容
		String ybzc_mianrong = DictionaryUtil.getPublicDictionaryText(
				pageCode, "ybzc_mianrong", exam.getYbzc_mianrong());
		if (ybzc_mianrong.equals("其它"))
			ybzc_mianrong = exam.getYbzc_mianrong0();
		cellC = new PdfPCell();
		p = new Paragraph("面容:"+ybzc_mianrong, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//步态
		String ybzc_butai = DictionaryUtil.getPublicDictionaryText(
				pageCode, "ybzc_butai", exam.getYbzc_butai());
		if (ybzc_butai.equals("其它"))
			ybzc_butai = exam.getYbzc_butai0();
		cellC = new PdfPCell();
		p = new Paragraph("步态:"+ybzc_butai, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//查体
		String ybzc_chati = DictionaryUtil.getPublicDictionaryText(
				pageCode, "ybzc_chati", exam.getYbzc_chati());
		if (ybzc_chati.equals("其它"))
			ybzc_chati = exam.getYbzc_chati0();
		cellC = new PdfPCell();
		p = new Paragraph("查体:"+ybzc_chati, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		//空白列
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//营养
		String ybzc_yingyang = DictionaryUtil.getPublicDictionaryText(
				pageCode, "ybzc_yingyang", exam.getYbzc_yingyang());
		if (ybzc_yingyang.equals("其它"))
			ybzc_yingyang = exam.getYbzc_yingyang0();
		cellC = new PdfPCell();
		p = new Paragraph("营养:"+ybzc_yingyang, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//体位
		String ybzc_tiwei = DictionaryUtil.getPublicDictionaryText(
				pageCode, "ybzc_tiwei", exam.getYbzc_tiwei());
		if (ybzc_tiwei.equals("其它"))
			ybzc_tiwei = exam.getYbzc_tiwei0();
		cellC = new PdfPCell();
		p = new Paragraph("体位:"+ybzc_tiwei, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(3);
		tableContent.addCell(cellC);
		
		//皮肤粘膜
		cellC = new PdfPCell();
		p = new Paragraph("皮肤粘膜:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		
		//色泽
		String temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"pfnm_seze", exam.getPfnm_seze());
		if (temp.equals("其它")) {
			temp = exam.getPfnm_seze0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("色泽:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//温度与湿度
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"pfnm_wenshi", exam.getPfnm_wenshi());
		if (temp.equals("其它")) {
			temp = exam.getPfnm_wenshi0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("温度与湿度:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//弹性
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"pfnm_tanxing", exam.getPfnm_tanxing());
		if (temp.equals("其它")) {
			temp = exam.getPfnm_tanxing0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("弹性:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		//空白列
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//肝掌
		if (exam.getPfnm_ganzhang() == 1) {
			temp = "阳性";
		} else {
			temp = "阴性";
		}
		cellC = new PdfPCell();
		p = new Paragraph("肝掌:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//毛细血管扩张征
		if (exam.getPfnm_maoxi() == 1) {
			temp = "阳性";
		} else {
			temp = "阴性";
		}
		cellC = new PdfPCell();
		p = new Paragraph("毛细血管扩张征:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//毛发分布
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"pfnm_maofa", exam.getPfnm_maofa());
		if (temp.equals("其它")) {
			temp = exam.getPfnm_maofa0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("毛发分布:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		
		//空白列
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//蜘蛛痣
		if (exam.getPfnm_zhizhu() != null && exam.getPfnm_zhizhu() == 1) {
			temp = "有";
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("蜘蛛痣:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		
		//空白列
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//皮疹
		if (exam.getPfnm_pizhen() == 1) {
			temp = exam.getPfnm_pizhenDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("皮疹:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		//空白列
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//皮下出血
		if (exam.getPfnm_pixia() == 1) {
			temp = exam.getPfnm_pixiaDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("皮下出血:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		//空白列
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//瘢痕
		if (exam.getPfnm_banhen() == 1) {
			temp = exam.getPfnm_banhenDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("瘢痕:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		//空白列
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//水肿
		if (exam.getPfnm_shuizhong() == 1) {
			temp = exam.getPfnm_shuizhongDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("水肿:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		//空白列
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//其他异常
		if (exam.getPfnm_qita() == 1) {
			temp = exam.getPfnm_qitaDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("其他异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		//淋巴结
		cellC = new PdfPCell();
		p = new Paragraph("淋巴结:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		if (exam.getLinbajie_zhongda() == 1) {
			temp = exam.getLinbajie_zhongdaDesc();
		} else {
			temp = "未触及";
		}
		cellC = new PdfPCell();
		p = new Paragraph(temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//淋巴管
		cellC = new PdfPCell();
		p = new Paragraph("淋巴管:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		if (exam.getLinbajie_jieyan() == 1) {
			String str = DictionaryUtil.getPublicDictionaryText(pageCode,
					"linbajie_jieyantd", exam.getLinbajie_jieyantd());
			if (exam.getLinbajie_jieyanbs() == 0) {
				str += "、不";
			}
			temp = str + "伴全身性高热寒战";
		} else {
			temp = "未触及";
		}
		cellC = new PdfPCell();
		p = new Paragraph(temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//头部五官
		cellC = new PdfPCell();
		p = new Paragraph("头部五官:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//头颅大小
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"head_daxiao", exam.getHead_daxiao());
		if (temp.equals("其它")) {
			temp = exam.getHead_daxiao0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("头颅大小:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		//头颅畸形
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"head_jixing", exam.getHead_jixing());
		if (temp.equals("其它")) {
			temp = exam.getHead_jixing0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("头颅畸形:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//其它异常
		if (exam.getHead_other() == 1) {
			temp = exam.getHead_otherDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("其它异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//眼
		cellC = new PdfPCell();
		p = new Paragraph("眼:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		
		cellC = new PdfPCell();
		p = new Paragraph("见专科检查", myFontItem);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//耳
		cellC = new PdfPCell();
		p = new Paragraph("耳:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//听力粗试障碍
		String ear_tingli = DictionaryUtil.getPublicDictionaryText(
				pageCode, "ear_rutu", exam.getEar_tingli());
		if (ear_tingli.equals("其它"))
			ear_tingli = exam.getEar_tingli0();
		cellC = new PdfPCell();
		p = new Paragraph("听力粗试障碍:"+ear_tingli, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		//外耳道分泌物
		if (exam.getEar_waier() == 1) {
			String posi = DictionaryUtil.getPublicDictionaryText(pageCode,
					"ear_waierPosi", exam.getEar_waierPosi());
			String xz = DictionaryUtil.getPublicDictionaryText(pageCode,
					"ear_waierxingzhi", exam.getEar_waierxingzhi());
			if (xz.equals("其它") || xz.equals("其他")) {
				xz = exam.getEar_waierxingzhi0();
			}
			temp = posi + xz;
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("外耳道分泌物:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//乳突压痛
		String ear_rutu = DictionaryUtil.getPublicDictionaryText(pageCode,
				"ear_rutu", exam.getEar_rutu());
		if (ear_rutu.equals("其它"))
			ear_rutu = exam.getEar_rutu0();
		cellC = new PdfPCell();
		p = new Paragraph("乳突压痛:"+ear_rutu, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		//耳廓
		if ("1".equals(exam.getEar_erkuo())) {
			temp = exam.getEar_erkuoDesc();
		} else {
			temp = "正常";
		}
		cellC = new PdfPCell();
		p = new Paragraph("耳廓:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//其它异常
		if (exam.getEar_other() != null && exam.getEar_other().equals("1")) {
			temp = exam.getEar_otherDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("其它异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//鼻
		cellC = new PdfPCell();
		p = new Paragraph("鼻:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//鼻中隔
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "nose_zhongge", exam.getNose_zhongge());
		cellC = new PdfPCell();
		p = new Paragraph("鼻中隔:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		//外观
		if ("1".equals(exam.getNose_waiguan())) {
			temp = exam.getNose_waiguanDesc();
		} else {
			temp = "正常";
		}
		cellC = new PdfPCell();
		p = new Paragraph("外观:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//鼻窦压痛
		if (exam.getNose_bidou() == 1) {
			temp = exam.getNose_bidouPosi();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("鼻窦压痛:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//其它异常
		if (exam.getNose_other() == 1) {
			temp = exam.getNose_otherDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("其它异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//口
		cellC = new PdfPCell();
		p = new Paragraph("口:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//口唇
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"mouth_kouchun", exam.getMouth_kouchun());
		if (temp.equals("其它")) {
			temp = exam.getMouth_kouchun0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("口唇:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//伸舌
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"mouth_shenshe", exam.getMouth_shenshe());
		if (temp.equals("其它")) {
			temp = exam.getMouth_shenshe0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("伸舌:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//口腔粘膜
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"mouth_nianmo", exam.getMouth_nianmo());
		if (temp.equals("其它")) {
			temp = exam.getMouth_nianmo0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("口腔粘膜:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//牙龈
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"mouth_yayin", exam.getMouth_yayin());
		if (temp.equals("其它")) {
			temp = exam.getMouth_yayin0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("牙龈:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//咽部
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"mouth_yanbu", exam.getMouth_yanbu());
		if (temp.equals("其它")) {
			temp = exam.getMouth_yanbu0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("咽部:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//口腔气味
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"mouth_qiwei", exam.getMouth_qiwei());
		if (temp.equals("其它")) {
			temp = exam.getMouth_qiwei0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("口腔气味:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//齿列
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"mouth_chilie", exam.getMouth_chilie());
		if (temp.equals("其它")) {
			temp = exam.getMouth_chilie0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("齿列:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//声音
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"mouth_shenyin", exam.getMouth_shenyin());
		if (temp.equals("其它")) {
			temp = exam.getMouth_shenyin0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("声音:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//扁桃体
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"mouth_biantaoti", exam.getMouth_biantaoti());
		if (temp.equals("其它")) {
			temp = exam.getMouth_biantaoti0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("扁桃体:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//腮腺肿大
		if (exam.getSaix_zhongda() == 1) {
			temp = exam.getSaix_zhongdaDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("腮腺肿大:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//其他异常
		if (exam.getMouth_other() != null
				&& "1".equals(exam.getMouth_other())) {
			temp = exam.getMouth_otherDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("其他异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		//颈部
		cellC = new PdfPCell();
		p = new Paragraph("颈部:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//抵触感
		if (exam.getNeck_dichu() == 1) {
			temp = "有";
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("抵触感:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//气管位置
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "neck_qiguan", exam.getNeck_qiguan());
		if (temp.equals("其它"))
			temp = exam.getNeck_qiguan0();
		
		cellC = new PdfPCell();
		p = new Paragraph("气管位置:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//颈静脉
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "neck_jingmai", exam.getNeck_jingmai());
		if (temp.equals("其它"))
			temp = exam.getNeck_jingmai0();
		cellC = new PdfPCell();
		p = new Paragraph("颈静脉:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//颈动脉搏动
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "neck_dongmai", exam.getNeck_dongmai());
		if (temp.equals("其它"))
			temp = exam.getNeck_dongmai0();
		cellC = new PdfPCell();
		p = new Paragraph("颈动脉搏动:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//肝颈静脉回流征
		if (exam.getNeck_jmhuiliu() == 1) {
			temp = "阳性";
		} else {
			temp = "阴性";
		}
		cellC = new PdfPCell();
		p = new Paragraph("肝颈静脉回流征:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(3);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//甲状腺肿大
		if (exam.getNeck_jiazhx() == 1) {
			temp = exam.getNeck_jiazhxDesc();
		} else {
			temp = "未触及";
		}
		cellC = new PdfPCell();
		p = new Paragraph("甲状腺肿大:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//其他异常
		if (exam.getNeck_other() != null
				&& "1".equals(exam.getNeck_other())) {
			temp = exam.getNeck_otherDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("其他异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		//胸部
		cellC = new PdfPCell();
		p = new Paragraph("胸部:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 胸廓 
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"xiong_kuo", exam.getXiong_kuo());
		if (temp.equals("其它"))
			temp = exam.getXiong_kuo0();
		cellC = new PdfPCell();
		p = new Paragraph("胸廓:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 胸骨压痛 
		if (exam.getXiong_yatong() == 1) {
			temp = "有";
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("胸骨压痛:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 双侧乳房 
		if (exam.getXiong_rufangDc() == 1) {
			temp = "不对称";
		} else {
			temp = "对称";
		}
		cellC = new PdfPCell();
		p = new Paragraph("双侧乳房:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 乳房异常 
		if (exam.getXiong_fufang() == 1) {
			temp = exam.getXiong_rufangDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("乳房异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 其他异常 
		if (exam.getXiong_other() != null
				&& "1".equals(exam.getXiong_other())) {
			temp = exam.getXiong_otherDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("其他异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		//肺部
		cellC = new PdfPCell();
		p = new Paragraph("肺部:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 呼吸运动 
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"fei_huxi", exam.getFei_huxi());
		if (temp.equals("其它"))
			temp = exam.getFei_huxi0();
		cellC = new PdfPCell();
		p = new Paragraph("呼吸运动:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		// 触觉语颤
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "fei_huxi", exam.getFei_yuchan());
		if (temp.equals("其它"))
			temp = exam.getFei_yuchan0();
		cellC = new PdfPCell();
		p = new Paragraph("触觉语颤:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 呼吸音 
		if (exam.getFei_huxiyin() == 1) {
			temp = exam.getFei_huxiyinDesc();
		} else {
			temp = "正常";
		}
		cellC = new PdfPCell();
		p = new Paragraph("呼吸音:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		// 胸膜摩擦音 
		if (exam.getFei_xiongmo() == 1) {
			temp = exam.getFei_xiongmoDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("胸膜摩擦音:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 左肺叩诊 
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "fei_zuokou", exam.getFei_zuokou());
		if (temp.equals("其它"))
			temp = exam.getFei_zuokou0();
		cellC = new PdfPCell();
		p = new Paragraph("左肺叩诊:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		// 右肺叩诊 
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "fei_zuokou", exam.getFei_youkou());
		if (temp.equals("其它"))
			temp = exam.getFei_youkou0();
		cellC = new PdfPCell();
		p = new Paragraph("右肺叩诊:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 左肺下界
		cellC = new PdfPCell();
		p = new Paragraph("左肺下界:"+exam.getFei_zuoxiajie(), fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		// 右肺下界
		cellC = new PdfPCell();
		p = new Paragraph("右肺下界:"+exam.getFei_youxiajie(), fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 啰音
		if (exam.getFei_luoyin() == 1) {
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"ear_waierPosi", exam.getFei_luoyinPosi())
					+ exam.getFei_luoyinxzhi();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("啰音:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 其他异常
		if (exam.getFei_other() != null && "1".equals(exam.getFei_other())) {
			temp = exam.getFei_otherDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("其他异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		// 心脏
		cellC = new PdfPCell();
		p = new Paragraph("心脏:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//心前区膨隆
		if (exam.getXinz_penglong() == 1) {
			temp = "有";
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("心前区膨隆:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//心尖搏动
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "xinz_bodong", exam.getXinz_bodong());
		if (temp.equals("其它"))
			temp = exam.getXinz_bodong0();
		cellC = new PdfPCell();
		p = new Paragraph("心尖搏动:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//心音
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "xinz_xinyin", exam.getXinz_xinyin());
		if (temp.equals("其它")){
			temp = exam.getXinz_xinyin0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("心音:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//叩诊心界
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "xinz_xinjie", exam.getXinz_xinjie());
		if (temp.equals("其它"))
			temp = exam.getXinz_xinjie0();
		cellC = new PdfPCell();
		p = new Paragraph("叩诊心界:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//心率
		cellC = new PdfPCell();
		p = new Paragraph("心率:"+exam.getXinz_xinlv(), fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//心律
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "xinz_xinrate", exam.getXinz_xinrate());
		if (temp.equals("其它"))
			temp = exam.getXinz_xinrate0();
		cellC = new PdfPCell();
		p = new Paragraph("心律:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(2);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//心包摩擦感
		if (exam.getXinz_xinbao() == 1) {
			temp = "有";
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("心包摩擦感:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//心尖搏动位置
		if (exam.getXinz_bodongPosi() == 0) {
			temp = "正常";
		} else {
			temp = "移位，距左锁骨中线" + exam.getXinz_bodongCM() + "cm";
		}
		cellC = new PdfPCell();
		p = new Paragraph("心尖搏动位置:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(3);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//杂音
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "xinz_zayin", exam.getXinz_zayin());
		if (temp.equals("其它"))
			temp = exam.getXinz_zayin0();
		cellC = new PdfPCell();
		p = new Paragraph("杂音:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//其他异常
		if (exam.getXinz_other() != null
				&& "1".equals(exam.getXinz_other())) {
			temp = exam.getXinz_otherDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("其他异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		// 周围血管
		cellC = new PdfPCell();
		p = new Paragraph("周围血管:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		if (exam.getZhouweixg_zheng() == 1) {
			temp = exam.getZhouweixg_zhengDesc();
		} else {
			temp = "正常";
		}
		cellC = new PdfPCell();
		p = new Paragraph(temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		// 腹部视诊
		cellC = new PdfPCell();
		p = new Paragraph("腹部视诊:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//外形
		cellC = new PdfPCell();
		p = new Paragraph("外形:"+exam.getFubu_waixing(), fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//腹壁静脉曲张
		if (exam.getFubu_jingmai() == 1) {
			temp = "有";
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("腹壁静脉曲张:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//腹式呼吸
		if (exam.getFubu_huxi() == 1) {
			temp = "消失";
		} else {
			temp = "存在";
		}
		cellC = new PdfPCell();
		p = new Paragraph("腹式呼吸:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(2);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//其它异常
		if (exam.getFubu_shizhen_o() == 1) {
			temp = exam.getFubu_shizhen_oDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("其它异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		cellC.setColspan(4);
		tableContent.addCell(cellC);
		
		// 触诊
		cellC = new PdfPCell();
		p = new Paragraph("触诊:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 腹壁 
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"fubu_fubi", exam.getFubu_fubi());
		if (temp.equals("其它")) {
			temp = exam.getFubu_fubi0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("腹壁:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 振水音
		if (exam.getFubu_zhenshui() == 1) {
			temp = "有";
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("振水音:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 液波震颤
		if (exam.getFubu_yebo() == 1) {
			temp = "有";
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("液波震颤:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 肌紧张
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"fubu_jijzh", exam.getFubu_jijzh());
		if (temp.equals("其它")) {
			temp = exam.getFubu_jijzh0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("肌紧张:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 压痛
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"fubu_jijzh", exam.getFubu_yatong());
		if (temp.equals("其它")) {
			temp = exam.getFubu_yatong0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("压痛:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 反跳痛
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"fubu_jijzh", exam.getFubu_fantt());
		if (temp.equals("其它")) {
			temp = exam.getFubu_fantt0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("反跳痛:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// Murphy's征
		temp =DictionaryUtil.getPublicDictionaryText(
				pageCode, "fubu_murphy", exam.getFubu_murphy());
		cellC = new PdfPCell();
		p = new Paragraph("Murphy’s征:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 胆囊
		if (exam.getFubu_dannang() == 1) {
			temp = exam.getFubu_dannangDesc();
		} else {
			temp = "未触及";
		}
		cellC = new PdfPCell();
		p = new Paragraph("胆囊:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 腹部包块
		if (exam.getFubu_baokuai() == 1) {
			temp = exam.getFubu_baokuaiDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("腹部包块:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 肝脏
		if (exam.getFubu_ganzang() == 1) {
			temp = exam.getFubu_ganzangDesc();
		} else {
			temp = "未触及";
		}
		cellC = new PdfPCell();
		p = new Paragraph("肝脏:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 脾脏
		if (exam.getFubu_pi() == 1) {
			temp = exam.getFubu_piDesc();
		} else {
			temp = "未触及";
		}
		cellC = new PdfPCell();
		p = new Paragraph("脾脏:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 肾脏
		if (exam.getFubu_shen() == 1) {
			temp = exam.getFubu_shenDesc();
		} else {
			temp = "未触及";
		}
		cellC = new PdfPCell();
		p = new Paragraph("肾脏:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 其他异常
		if (exam.getFubu_chu_other() != null
				&& exam.getFubu_chu_other().equals("1")) {
			temp = exam.getFubu_chu_otherDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("其他异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 叩诊
		cellC = new PdfPCell();
		p = new Paragraph("叩诊:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//  肝浊音界 
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "fubu_ganzhuo", exam.getFubu_ganzhuo());
		cellC = new PdfPCell();
		p = new Paragraph("肝浊音界:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//  移动性浊音 
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "fubu_murphy", exam.getFubu_yidong());
		cellC = new PdfPCell();
		p = new Paragraph("移动性浊音:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//腹水
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "fubu_fushui", exam.getFubu_fushui());
		cellC = new PdfPCell();
		p = new Paragraph("腹水:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(2);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//肝区叩痛
		if (exam.getFubu_ganqukt() == 1) {
			temp = "有";
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("肝区叩痛:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//肾区叩痛
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "fubu_shenkt", exam.getFubu_shenkt());
		cellC = new PdfPCell();
		p = new Paragraph("肾区叩痛:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(3);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//肝上界
		cellC = new PdfPCell();
		p = new Paragraph("肝上界:"+exam.getFubu_ganshangjie(), fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//其他异常
		if (exam.getFubu_k_other() != null
				&& exam.getFubu_k_other().equals("1")) {
			temp = exam.getFubu_k_otherDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("其他异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 听诊
		cellC = new PdfPCell();
		p = new Paragraph("听诊:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//肠鸣音
		cellC = new PdfPCell();
		p = new Paragraph("肠鸣音:"+exam.getFubu_changming(), fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//气过水声
		if (exam.getFubu_qishui() == 1) {
			temp = "有";
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("气过水声:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//血管杂音
		if (exam.getFubu_xueguan() == 1) {
			temp = exam.getFubu_xueguanDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("血管杂音:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(2);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//其他异常
		if (exam.getFubu_tz_other() != null
				&& exam.getFubu_tz_other().equals("1")) {
			temp = exam.getFubu_tz_otherDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("其他异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 肛门及外生 殖器 
		cellC = new PdfPCell();
		p = new Paragraph("肛门及外生殖器:", myFontItem);
		p.setIndentationLeft(18);
		if (exam.getShengzhiqi() == 1) {
			temp = exam.getShengzhiqiDesc();
		} else if (exam.getShengzhiqi() == 0) {
			temp = "正常";
		} else if (exam.getShengzhiqi() == 3) {
			temp = "拒查";
		} else if (exam.getShengzhiqi() == 4) {
			temp = "未查";
		}
		p.setFont(fontGeneral);
		p.add(temp);
		cellC.setColspan(5);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 脊柱 
		cellC = new PdfPCell();
		p = new Paragraph("脊柱:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//外形
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "jisi_waixing", exam.getJisi_waixing());
		if (temp.equals("其它"))
			temp = exam.getJisi_waixing0();
		cellC = new PdfPCell();
		p = new Paragraph("外形:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(2);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//压痛
		if (exam.getJisi_yatong() == 1) {
			temp = "有";
		} else if (exam.getJisi_yatong() == 0) {
			temp = "无";
		} else if (exam.getJisi_yatong() == 2) {
			temp = "不合作";
		} else if (exam.getJisi_yatong() == 3) {
			temp = "无法配合";
		}
		cellC = new PdfPCell();
		p = new Paragraph("压痛:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(2);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//叩击痛
		if (exam.getJisi_kouji() == 1) {
			temp = "有";
		} else if (exam.getJisi_kouji() == 0) {
			temp = "无";
		} else if (exam.getJisi_kouji() == 2) {
			temp = "不合作";
		} else if (exam.getJisi_kouji() == 3) {
			temp = "无法配合";
		}
		cellC = new PdfPCell();
		p = new Paragraph("叩击痛:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(2);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		//活动度
		temp= DictionaryUtil.getPublicDictionaryText(
				pageCode, "jisi_huodong", exam.getJisi_huodong());
		cellC = new PdfPCell();
		p = new Paragraph("活动度:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(2);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 四肢 
		cellC = new PdfPCell();
		p = new Paragraph("四肢:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 下肢水肿 
		temp = DictionaryUtil.getPublicDictionaryText(pageCode,
				"jisi_xiazhi", exam.getJisi_xiazhi());
		if (temp.equals("其它")) {
			temp = exam.getJisi_xiazhi0();
		}
		cellC = new PdfPCell();
		p = new Paragraph("下肢水肿:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 其它异常 
		if (exam.getJisi_other() == 1) {
			temp = exam.getJisi_otherDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("其它异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 神经系统 
		cellC = new PdfPCell();
		p = new Paragraph("神经系统:", myFontItem);
		p.setAlignment(Element.ALIGN_RIGHT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 腹壁反射 
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "shenjing_fubi", exam.getShenjing_fubi());
		if (temp.equals("其它"))
			temp = exam.getShenjing_fubi0();
		cellC = new PdfPCell();
		p = new Paragraph("腹壁反射:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 角膜反射 
		String shenjing_jiaomo = DictionaryUtil.getPublicDictionaryText(
				pageCode, "shenjing_fubi", exam.getShenjing_jiaomo());
		if (shenjing_jiaomo.equals("其它"))
			shenjing_jiaomo = exam.getShenjing_jiaomo0();
		cellC = new PdfPCell();
		p = new Paragraph("角膜反射:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 膝腱反射 
		if (exam.getShenjing_xijian() == 1) {
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"ear_waierPosi", exam.getShenjing_xijianPosi())
					+ DictionaryUtil.getPublicDictionaryText(pageCode,
							"shenjing_xijianXZ", exam
									.getShenjing_xijianXZ());
		} else {
			temp = "正常";
		}
		cellC = new PdfPCell();
		p = new Paragraph("膝腱反射:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(2);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 跟腱反射 
		if (exam.getShenjing_genjian() == 1) {
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"ear_waierPosi", exam.getShenjing_genjianPosi())
					+ DictionaryUtil.getPublicDictionaryText(pageCode,
							"shenjing_xijianXZ", exam
									.getShenjing_genjianXZ());
		} else {
			temp = "正常";
		}
		cellC = new PdfPCell();
		p = new Paragraph("跟腱反射:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// Babinski征 
		String shenjing_kerning = DictionaryUtil.getPublicDictionaryText(
				pageCode, "shenjing_babinski", exam.getShenjing_kerning());
		if (shenjing_kerning.equals("其它"))
			shenjing_kerning = exam.getShenjing_kerning0();
		cellC = new PdfPCell();
		p = new Paragraph("Babinski征:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// Kernig征 
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "shenjing_babinski", exam.getShenjing_kerning());
		if (temp.equals("其它"))
			temp = exam.getShenjing_kerning0();
		cellC = new PdfPCell();
		p = new Paragraph("Kernig征:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(2);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 踝阵挛 
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "shenjing_huai", exam.getShenjing_huai());
		if (temp.equals("其它"))
			temp = exam.getShenjing_huai0();
		cellC = new PdfPCell();
		p = new Paragraph("踝阵挛:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// 扑翼样振颤 
		temp = DictionaryUtil.getPublicDictionaryText(
				pageCode, "shenjing_huai", exam.getShenjing_puyi());
		if (temp.equals("其它"))
			temp = exam.getShenjing_puyi0();
		cellC = new PdfPCell();
		p = new Paragraph("扑翼样振颤:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		// Brudzinski征 
		temp = DictionaryUtil
		.getPublicDictionaryText(pageCode, "shenjing_babinski",
				exam.getShenjing_brudzinski());
		if (temp.equals("其它"))
			temp = exam.getShenjing_brudzinski0();
		cellC = new PdfPCell();
		p = new Paragraph("Brudzinski征:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(2);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		// 肌力 
		if (exam.getShenjing_jili() == 1) {
			temp = "";
			String str = DictionaryUtil.getPublicDictionaryText(pageCode,
					"shenjing_jilitl", exam.getShenjing_jilitl());
			if (str.length() > 0) {
				temp += "上肢左侧" + str + "、";
			}
			str = DictionaryUtil.getPublicDictionaryText(pageCode,
					"shenjing_jilitl", exam.getShenjing_jilitr());
			if (str.length() > 0) {
				temp += "上肢右侧" + str + "、";
			}
			str = DictionaryUtil.getPublicDictionaryText(pageCode,
					"shenjing_jilitl", exam.getShenjing_jilibl());
			if (str.length() > 0) {
				temp += "下肢左侧" + str + "、";
			}
			str = DictionaryUtil.getPublicDictionaryText(pageCode,
					"shenjing_jilitl", exam.getShenjing_jilibr());
			if (str.length() > 0) {
				temp += "下肢右侧" + str + "、";
			}
			if (temp.length() > 0) {
				temp = temp.substring(0, temp.length() - 1);
			}
		} else if (exam.getShenjing_jili() == 2) {
			temp = "不合作";
		} else if (exam.getShenjing_jili() == 3) {
			temp = "无法配合";
		} else {
			temp = "正常";
		}
		cellC = new PdfPCell();
		p = new Paragraph("肌力:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//肌张力
		if (exam.getShenjing_jizhang() == 1) {
			temp = "";
			String str = DictionaryUtil.getPublicDictionaryText(pageCode,
					"shenjing_jilitl", exam.getShenjing_jizhangtl());
			if (str.length() > 0) {
				temp += "上肢左侧" + str + "、";
			}
			str = DictionaryUtil.getPublicDictionaryText(pageCode,
					"shenjing_jilitl", exam.getShenjing_jizhangtr());
			if (str.length() > 0) {
				temp += "上肢右侧" + str + "、";
			}
			str = DictionaryUtil.getPublicDictionaryText(pageCode,
					"shenjing_jilitl", exam.getShenjing_jizhangbl());
			if (str.length() > 0) {
				temp += "下肢左侧" + str + "、";
			}
			str = DictionaryUtil.getPublicDictionaryText(pageCode,
					"shenjing_jilitl", exam.getShenjing_jizhangbr());
			if (str.length() > 0) {
				temp += "下肢右侧" + str + "、";
			}
			if (temp.length() > 0) {
				temp = temp.substring(0, temp.length() - 1);
			}
		} else {
			temp = "正常";
		}
		cellC = new PdfPCell();
		p = new Paragraph("肌张力:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		this.blankOne(cellC, p, fontGeneral, tableContent);
		//其他异常
		if (exam.getShenjing_other() != null
				&& exam.getShenjing_other().equals("1")) {
			temp = exam.getShenjing_otherDesc();
		} else {
			temp = "无";
		}
		cellC = new PdfPCell();
		p = new Paragraph("其他异常:"+temp, fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
		}
	}
	
	
	
	
	
	
	//空白一列
	private void blankOne(PdfPCell cellC, Paragraph p,Font fontGeneral,PdfPTable tableContent){
		cellC = new PdfPCell();
		p = new Paragraph("", fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
	}
	//空白三列
	private void blankThree(PdfPCell cellC, Paragraph p,Font fontGeneral,PdfPTable tableContent){
		cellC = new PdfPCell();
		p = new Paragraph("", fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(3);
		cellC.addElement(p);
		tableContent.addCell(cellC);
	}
	//空白两列 
	private void blankTwo(PdfPCell cellC, Paragraph p,Font fontGeneral,PdfPTable tableContent){
		cellC = new PdfPCell();
		p = new Paragraph("", fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(2);
		cellC.addElement(p);
		tableContent.addCell(cellC);
	}
	//空白两列 
	private void blankFour(PdfPCell cellC, Paragraph p,Font fontGeneral,PdfPTable tableContent){
		cellC = new PdfPCell();
		p = new Paragraph("", fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.setColspan(4);
		cellC.addElement(p);
		tableContent.addCell(cellC);
	}
}
