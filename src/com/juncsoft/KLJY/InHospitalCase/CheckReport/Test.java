package com.juncsoft.KLJY.InHospitalCase.CheckReport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.SampleType;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.StrModifyVo;

public class Test {

/*	public static void main(String []args){
		List list = new ArrayList();      
        list.add("111");      
        list.add("112");      
        list.add("111");      
        list.add("111");      
        list.add("114");      
        List tempList = removeDuplicateObj(list);      
        for(int i = 0; i < tempList.size(); i++){      
        	System.out.println(tempList.get(i));      
        }      

	}
	
	private static List removeDuplicateObj(List list){ 
		Set set = new HashSet(list);      	       
        //将Set中的集合，放到一个临时的链表中(tempList)      
        Iterator iterator = set.iterator();      
        List tempList = new ArrayList();      
        int i = 0;      
        while(iterator.hasNext()){                          
			tempList.add(iterator.next().toString());      
			i++;      
        }      
        return tempList;  
	} 
	
	
	public static void main(String []args){
		System.out.println(java.sql.Date.valueOf("2010-05-06"));
	}
	
	//一个日期上加天数
	public static String DateAdd(String startDate, int dd) {
		  java.text.DateFormat df = java.text.DateFormat.getDateInstance();
		  java.util.Date date = new java.util.Date();
		  try {
		    date = df.parse(startDate);
		  }
		  catch (Exception ex) {
		    System.out.print(ex);
		  }

		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);

		  calendar.add(Calendar.DATE, dd);

		  String mm = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		  if (mm.length() == 1) {
		    mm = "0" + mm;
		  }
		  String day = String.valueOf(calendar.get(Calendar.DATE));
		  if (day.length() == 1) {
		    day = "0" + day;

		  }
		  String returnDate = String.valueOf(calendar.get(Calendar.YEAR)) + "-" + mm
		      + "-" + day;
		
		  return returnDate;
		}

	public static String DateAdd2(Date date, int dd) {
		  java.text.DateFormat df = java.text.DateFormat.getDateInstance();
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);

		  calendar.add(Calendar.DATE, dd);

		  String mm = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		  if (mm.length() == 1) {
		    mm = "0" + mm;
		  }
		  String day = String.valueOf(calendar.get(Calendar.DATE));
		  if (day.length() == 1) {
		    day = "0" + day;

		  }
		  String returnDate = String.valueOf(calendar.get(Calendar.YEAR)) + "-" + mm
		      + "-" + day;
		
		  return returnDate;
		}
	//计算月:getCurrentTime("yyyy-MM");返回年-月格式的时间.再将数据库中时间substring(),然后比较.

	public static String getCurrentTime(String style) {
	   SimpleDateFormat format = new SimpleDateFormat(style);
	   return format.format(new Timestamp(System.currentTimeMillis()));
	}

	public static void main(String []args){
		String str = "中华人民共和国";
		System.out.println(str.split("").length);
		for(int i = 0; i < str.split("").length; i++){
			System.out.println(str.split("")[i]);
		}
	}

	public static void main(String []args){
		Map<Integer, String> map = new HashMap<Integer, String>(); 
		map.put(11, "my value is 11");
		map.put(22, "my value is 22");
		Iterator iter = map.entrySet().iterator(); 
		while (iter.hasNext()) { 
			Map.Entry entry = (Map.Entry) iter.next(); 
		    Object key = entry.getKey(); 
		    Object val = entry.getValue();
		    int intValue = Integer.parseInt(key.toString());
		    String strValue = val.toString();
		    System.out.println(intValue);
		    System.out.println(strValue);
		} 
	}
*/
	
//	public static void main(String []args){
//		String str = "[{text:'2008-08-08wwwwwwwwwwwwwwwwwwwwwwwwwww',leaf:false,children:"+
//		"[{text:'肝功能FFFFFFFFFFFFFFFFFFF',leaf:true},{text:'脑脊髓',leaf:true},{text:'便常规',leaf:true},"+
//		"{text:'钱学实验',leaf:true},{text:'肝移植',leaf:true},{text:'甲胎蛋白',leaf:true},"+
//		"{text:'抗核抗体谱',leaf:true},{text:'麻疹病毒',leaf:true},{text:'自身抗体',leaf:true}," +
//		"{text:'肿瘤',leaf:true},{text:'乙肝',leaf:true}]}," +
//		
//		"{text:'2008-06-26',leaf:false,children:[" +
//		"{text:'肝功能',leaf:true},{text:'脑脊髓',leaf:true},{text:'便常规',leaf:true}," +
//		"{text:'钱学实验',leaf:true},{text:'肝移植',leaf:true},{text:'甲胎蛋白',leaf:true}," +
//		"{text:'抗核抗体谱',leaf:true},{text:'麻疹病毒',leaf:true},{text:'自身抗体',leaf:true}," +
//		"{text:'肿瘤',leaf:true},{text:'乙肝',leaf:true}]}," +
//		
//		"{text:'2008-09-05',leaf:false,children:[{text:'肝功能',leaf:true},{text:'脑脊髓',leaf:true},{text:'便常规',leaf:true}," +
//		"{text:'钱学实验',leaf:true},{text:'肝移植',leaf:true},{text:'甲胎蛋白',leaf:true},{text:'抗核抗体谱',leaf:true},{text:'麻疹病毒',leaf:true}," +
//		"{text:'自身抗体',leaf:true},{text:'肿瘤',leaf:true},{text:'乙肝',leaf:true}]}," +
//		
//		"{text:'2008-08-08wwwwwwwwwwwwwwwwwwwwwwwwwww',leaf:false," +
//		"children:[{text:'肝功能GGGGGGGGGGGGGGGGGGGGGGGGGGGGGG',leaf:true},{text:'脑脊髓',leaf:true},{text:'便常规',leaf:true},{text:'钱学实验',leaf:true}," +
//		"{text:'肝移植',leaf:true},{text:'甲胎蛋白',leaf:true},{text:'抗核抗体谱',leaf:true},{text:'麻疹病毒',leaf:true}," +
//		"{text:'自身抗体',leaf:true},{text:'肿瘤',leaf:true},{text:'乙肝',leaf:true}]}]";
//		str = "[{text:'2010-07-22',leaf:false,children:[]},{text:'2010-07-22',leaf:false,children:[{text:'心肌酶',leaf:true},{text:'血生化',leaf:true}]},{text:'2010-07-22',leaf:false,children:[]},{text:'2010-07-22',leaf:false,children:[]}]";
//		JSONArray arr = JSONArray.fromObject(str);
//		List<StrModifyVo> list=new ArrayList<StrModifyVo>();
//		for(int i = 0; i < arr.size(); i++){
//			JSONObject object=arr.getJSONObject(i);
//			StrModifyVo sm=new StrModifyVo();
//			sm.setChildren(object.getJSONArray("children"));
//			sm.setText(object.getString("text"));
//			sm.setLeaf(object.getBoolean("leaf"));
//			list.add(sm);
//		}
//		Collections.sort(list,new Comparator<StrModifyVo>(){
//            public int compare(StrModifyVo arg0, StrModifyVo arg1) {
//                return arg0.getText().compareTo(arg1.getText());
//            }
//        });
//		for(int i = 0; i < list.size(); i++){
//			System.out.println(list.get(i).getText());
//			if(list.get(i).getChildren().size() == 0){
//				System.out.println("null,null,null,null");
//			}else{
//				System.out.println(list.get(i).getChildren());
//			}
//		}	
//		StringBuffer sb = new StringBuffer();
//		if(list.size() == 1){
//			sb.append("{text:'").append(list.get(0).getText()).append("',leaf:false,children:").append(list.get(0).getChildren()).append("}");
//		}else{
//			int temp = 0;
//			for(int i = 0; i < list.size() - 1; i++){
//				StrModifyVo sm = list.get(i);
//				StrModifyVo nextsm = list.get(i + 1);
//				if(sm.getChildren().size() == 0) continue;
//				if(sm.getText().equals(nextsm.getText())){
//					temp++;
//					String strsm = sm.getChildren().toString().replace("]", ",");
//					String strnextsm = nextsm.getChildren().toString().replace("[", "");
//					list.get(i + 1).setChildren(JSONArray.fromObject(strsm + strnextsm));
//	//				System.out.println(list.get(i+1).getChildren());
//					continue;
//				}
//				System.out.println("temp:" + temp);
//				
//				if(temp != 0){
//					System.out.println(sm.getChildren());
//					sb.append("{text:'").append(sm.getText()).append("',leaf:false,children:").append(sm.getChildren()).append("},");
//				}else{
//					sb.append("{text:'").append(sm.getText()).append("',leaf:false,children:").append(sm.getChildren()).append("},");
//				}
//				temp = 0;				
//			}
//			sb.append("{text:'").append(list.get(list.size() - 1).getText()).append("',leaf:false,children:").append(list.get(list.size() - 1).getChildren()).append("}");
//		}
//		
//		System.out.println(sb.toString());
//	}
//	public static void main(String[] args){
//		String str = "Oct 10 00:00:00 UTC+0800 2010";   
//		DateFormat df = new SimpleDateFormat("MMM dd HH:mm:ss 'UTC'Z yyyy",Locale.ENGLISH);   
//		Date date;
//		try {
//			date = df.parse(str);
//			System.out.println(date);   
//			System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date)); 
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}   		
//	}
	
	
	public static void main(String[] args){
		System.out.println(DateAdd(new Date(), -1));
	}
	
	public static String DateAdd(Date date, int interval) {
		  java.text.DateFormat df = java.text.DateFormat.getDateInstance();
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);

		  calendar.add(Calendar.DATE, interval);

		  String mm = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		  if (mm.length() == 1) {
		    mm = "0" + mm;
		  }
		  String day = String.valueOf(calendar.get(Calendar.DATE));
		  if (day.length() == 1) {
		    day = "0" + day;
		  }
		  String returnDate = String.valueOf(calendar.get(Calendar.YEAR)) + "-" + mm
		      + "-" + day;
		
		  return returnDate;
		}
}
