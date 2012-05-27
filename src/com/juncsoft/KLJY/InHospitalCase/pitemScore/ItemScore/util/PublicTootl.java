package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.util;

public class PublicTootl {

	private void getHiberXML() {
		for (int i = 1; i < 23; i++) {
			System.out.println("   <property name=\"weizhong" + i + "\" />");
		}

	}

	public static void main(String[] args) {
		// for(int i=1;i<23;i++){
		// System.out.println(" private int weizhong" + i +";");
		// }
		PublicTootl p = new PublicTootl();
		p.getHiberXML();

		int b[] = new int[3];

		// String a[3] = new String[3]{"aa","bb","cc"};
		String a[] = { "aa", "bb", "cc" };
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}

}
