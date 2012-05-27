package com.juncsoft.KLJY.ResearchFollowup.dao;

import com.juncsoft.KLJY.ResearchFollowup.entity.ChronicLiver;
import com.juncsoft.KLJY.ResearchFollowup.entity.HealthSurvey;
import com.juncsoft.KLJY.ResearchFollowup.entity.OutPutientCase;
import com.juncsoft.KLJY.ResearchFollowup.entity.TCMSysptomScore;

public interface FollowReginDao{
	//门诊病历
	public OutPutientCase OutPutientCase_SaveOrUpdate(OutPutientCase op)throws Exception;
	public OutPutientCase OutPutientCase_findByFollowupId(Long id)throws Exception;
	//健康调查简表
	public HealthSurvey HealthSurvey_SaveOrUpdate(HealthSurvey hs)throws Exception;
	public HealthSurvey HealthSurvey_findByFollowupId(Long id)throws Exception;
	//慢性肝量表
	public ChronicLiver ChronicLiver_SaveOrUpdate(ChronicLiver cl)throws Exception;
	public ChronicLiver ChronicLiver_findByFollowupId(Long id)throws Exception;
	//中医症候积分
	public TCMSysptomScore TCMSysptomScore_SaveOrUpdate(TCMSysptomScore tcm)throws Exception;
	public TCMSysptomScore TCMSysptomScore_findByFollowupId(Long id)throws Exception;
}