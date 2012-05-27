package com.juncsoft.KLJY.membergrounp.cor;

import com.juncsoft.KLJY.membergrounp.entity.MemberApplicationRecord;

public class DoctorCorRequest extends CorRequest {

	@Override
	public void execute(MemberApplicationRecord memApp) {
		memApp.setOptrole(optrole);
		memApp.setFlag(flag);
		if(memApp.getId()!=null) {//医生审批
	    	dao.updateApplicationRecord(memApp);
		} else {//医生替会员申请
			memApp.setApplicationStateContent("申请中");
			memApp.setResult(0);// 转科结果初始化进行中。。。。
			memApp.setAutoFlag(0);// 会员发起，初始化为0，仅责任科室可以看到
			memApp.setCurrentFlag(0);// 会员发起，初始化为0，表示责任科室意见
			memApp.setApplicationFlag(0);// 会员发起，初始化为0，仅申请科室可以看到
			dao.saveApplicationRecord(memApp);
		}
		System.out.println("医生申请转科处理");
	}

}
