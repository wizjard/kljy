package com.juncsoft.KLJY.membergrounp.cor;

import com.juncsoft.KLJY.membergrounp.entity.MemberApplicationRecord;

public class AdminCorRequest extends CorRequest {

	@Override
	public void execute(MemberApplicationRecord memApp) {
		System.out.println("管理员处理转科");
	}

}
