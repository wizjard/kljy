package com.juncsoft.KLJY.biomedical.entity;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.util.DatabaseUtil;

public class Test {
	public static void main(String[] args) {
		try {
			System.out.println("=========================================");
			Session session = DatabaseUtil.getSession();
			Transaction tran=session.beginTransaction();
			List<MemberInfo> list=session.createCriteria(MemberInfo.class).list();
			for(MemberInfo mem:list){
				if(mem.getInWard()==null||mem.getInWard().trim().equals("")||mem.getInWard().equals("门诊")){
					mem.setInWard("生物医学中心");
				}
				mem.setCurrentWard(mem.getInWard());
				session.update(mem);
				System.out.println(mem.getMemberNo()+"==========");
			}
			tran.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
