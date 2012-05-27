package com.juncsoft.KLJY.util;

import java.lang.reflect.Field;

import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.InHspRec24;

public class ORMUtil {
	public static void main(String[] args){
		Field[] flds=InHspRec24.class.getDeclaredFields();
		for(Field fld:flds){
//			if(fld.getType().equals(Integer.TYPE)){
//				System.out.println("update t_InHsp_Mouth_PresentIllnessHistory_This set "+fld.getName()+"=0;");
//			}
//			else if(fld.getType().equals(Date.class)){
//				System.out.println("alter table t_InHsp_Mouth_PresentIllnessHistory_This add "+fld.getName()+" datetime;");
//			}else{
//				int len=50;
//				if(fld.getName().startsWith("place")){
//					len=300;
//				}else if(fld.getName().endsWith("0")){
//					len=500;
//				}
//				System.out.println("alter table t_InHsp_Mouth_PresentIllnessHistory_This add "+fld.getName()+" varchar("+len+");");
//			}
			System.out.println(Integer.parseInt("03"));
		//	System.out.println("{name:'"+fld.getName()+"'},");
			
		}
	}
}
