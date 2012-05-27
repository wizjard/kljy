package com.juncsoft.KLJY.membergrounp.cor;

import com.juncsoft.KLJY.membergrounp.dao.DepartmentGrounpDao;
import com.juncsoft.KLJY.membergrounp.entity.MemberApplicationRecord;
import com.juncsoft.KLJY.membergrounp.impl.DepartmentGrounpImpl;

public abstract class CorRequest {
	DepartmentGrounpDao dao = new DepartmentGrounpImpl();

	public int flag; //操作标志 0：未操作 1：已同意 2：未同意
	public String optrole;
	
	public String getOptrole() {
		return optrole;
	}

	public void setOptrole(String optrole) {
		this.optrole = optrole;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public void checkSend(MemberApplicationRecord memApp) {
		execute(memApp);
	}

	public abstract void execute(MemberApplicationRecord memApp);
}
