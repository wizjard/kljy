package com.juncsoft.KLJY.membergrounp.cor;

import com.juncsoft.KLJY.membergrounp.entity.MemberApplicationRecord;

public class ConcreteHandler implements Handler {

	public Handler successor;
	
	public void handlerRequest(CorRequest request, MemberApplicationRecord memApp) {
		request.setFlag(memApp.getFlag());
		if(request instanceof MemberCorRequest) {
			request.setOptrole("member");
			request.checkSend(memApp);
		} else if(request instanceof DoctorCorRequest) {
			request.setOptrole("doctor");
			request.checkSend(memApp);
		} else if(request instanceof AdminCorRequest) {
			request.setOptrole("admin");
			request.checkSend(memApp);
		} else {
			System.out.println("未操作转科转组");
//			successor.handlerRequest(request);
		}
	}

}
