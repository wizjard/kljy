package com.juncsoft.KLJY.InHospitalCase.CaseMaster.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;

public interface InHspCaseMasterDao {
	public InHspCaseMaster saveOrUpdate(InHspCaseMaster cm) throws Exception;
	public InHspCaseMaster findById(Long id) throws Exception;
	public void delete(InHspCaseMaster cm) throws Exception;
	public List<InHspCaseMaster> queryAllByPatient(Long id) throws Exception;
	public JSONObject queryPersonalInfoByCaseID(Long id)throws Exception;
	public String getAge(Long pid, Date inHspDate )throws Exception;
	public JSONObject getAgeAndSex(int pid,int scId) throws Exception;
}
