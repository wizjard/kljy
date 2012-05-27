package com.juncsoft.KLJY.planhis.services;

import java.util.List;

import com.juncsoft.KLJY.planhis.util.NearPlan;

public interface PlanHisServices {
	
	public List<NearPlan>  getNearPlanitemsByPId(String pTenId)throws Exception;
	

}
