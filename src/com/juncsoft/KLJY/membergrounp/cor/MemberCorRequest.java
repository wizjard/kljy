package com.juncsoft.KLJY.membergrounp.cor;

import com.juncsoft.KLJY.membergrounp.entity.MemberApplicationRecord;

public class MemberCorRequest extends CorRequest {

	@Override
	public void execute(MemberApplicationRecord memApp) {
		memApp.setFlag(flag);
		memApp.setOptrole(optrole);
		if(memApp.getId() == null) {
	    	memApp.setApplicationStateContent("申请中");
	    	memApp.setResult(0);// 转科结果初始化进行中。。。。
	    	memApp.setAutoFlag(0);// 会员发起，初始化为0，仅责任科室可以看到
	    	memApp.setCurrentFlag(0);// 会员发起，初始化为0，表示责任科室意见
	    	memApp.setApplicationFlag(0);// 会员发起，初始化为0，仅申请科室可以看到
	    	dao.saveApplicationRecord(memApp);
		} else {
			dao.updateApplicationRecord(memApp);
		}
		System.out.println("会员申请转科处理");
	}

}
