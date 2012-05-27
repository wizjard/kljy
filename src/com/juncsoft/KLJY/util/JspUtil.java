package com.juncsoft.KLJY.util;

import java.util.Date;

public class JspUtil {
	public static String tranToBr(String text){
		String r = "";
		String[] sections = text.split("\n");
		for(int i=0;i<sections.length;i++){
			r += sections[i];
			r += "<br/>";
		}
		return r;
	}
	
	public static void main(String args[]){
		String s = "1\n2\n3\n4\n5\n6";
		String rs = tranToBr(s);
		System.out.println(s);
		System.out.println(rs);
	}
}
