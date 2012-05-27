package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.util;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;

public class ConvertSubSupUtil {
	private static final String SUP_FlAG = "^";   //上标标示符
	private static final String SUB_FlAG = "$";	//下标标示符
	private static final char SUP_FlAG_CHAR = '^';   //上标标示符
	private static final char SUB_FlAG_CHAR = '$';	//下标标示符
	
	private static final int SUP_FONTSIZE = 6;
	private static final int SUB_FONTSIZE = -6;
	

	public static Paragraph   parseSubOrSup(Paragraph pra) throws DocumentException, IOException{
		//5.PaO$2$(mmHg)(Fio$2$<50%)
		ArrayList indexs = new ArrayList();
		String content = pra.getContent();
		String contentClone = content ;
		Paragraph p = new Paragraph();
		String str = "";
		
		Font myFont = new Font(pra.getFont().getBaseFont(), 8, Font.NORMAL,BaseColor.BLACK);
		
		if(content.contains(SUP_FlAG)||content.contains(SUB_FlAG)){
			for(int i = 0;i<content.length();i++){
				int sign = returnSign(content);
				if(sign == 1){
					int subIndex = content.indexOf(SUP_FlAG_CHAR);
					indexs.add(subIndex);
					if(indexs.size() == 1){
						str = content.substring(0,Integer.parseInt(indexs.get(0)+""));
						content = content.substring(Integer.parseInt(indexs.get(0)+"")+1);
					}
					if(indexs.size()==2){
						int start = Integer.parseInt(indexs.get(0)+"");
						int end   = Integer.parseInt(indexs.get(1)+"");
						String startStr = str;
						String middleStr  = content.substring(0,end);
						
						Chunk chunk = new Chunk(middleStr,myFont);
						chunk.setTextRise(SUP_FONTSIZE);
						
						p.add(startStr);
						p.add(chunk);
						if(end == content.length()-2)break;
						content = content.substring(end+1);
						indexs.clear();
						str = "";
					}
				}
				if(sign == 2){
					int subIndex = content.indexOf(SUB_FlAG_CHAR);
					indexs.add(subIndex);
					if(indexs.size() == 1){
						str = content.substring(0,Integer.parseInt(indexs.get(0)+""));
						content = content.substring(Integer.parseInt(indexs.get(0)+"")+1);
					}
					if(indexs.size()==2){
						int start = Integer.parseInt(indexs.get(0)+"");
						int end   = Integer.parseInt(indexs.get(1)+"") ;
						String startStr = str;
						String middleStr  = content.substring(0,end);
						
						Chunk chunk = new Chunk(middleStr,myFont);
						chunk.setTextRise(SUB_FONTSIZE);
						
						p.add(startStr);
						p.add(chunk);
						if(end == content.length()-2)break;
						content = content.substring(end+1);
						indexs.clear();
						str = "";
					}
				}
			}
		}
		p.add(content);
		return p;
	}
	
	private static int returnSign(String content){
		int sign = 0;
		for(int j = 0 ;j<content.length();j++){
			if(content.charAt(j)==SUP_FlAG_CHAR){
				sign = 1;
				break;
			}
			if(content.charAt(j)==SUB_FlAG_CHAR){
				sign = 2;
				break;
			}
		}
		return sign;
	}
}
