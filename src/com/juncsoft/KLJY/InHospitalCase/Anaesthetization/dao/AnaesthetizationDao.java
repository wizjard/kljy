package com.juncsoft.KLJY.InHospitalCase.Anaesthetization.dao;

import java.util.List;

import com.juncsoft.KLJY.InHospitalCase.Anaesthetization.entity.AnaesthetizationRec;

import net.sf.json.JSONArray;

public interface AnaesthetizationDao {

	public AnaesthetizationRec anaesthetizationRec_saveOrUpdate(AnaesthetizationRec record) throws Exception;
	public JSONArray anaesthetizationRec_treeNodes(long id) throws Exception;
	public List<AnaesthetizationRec> anaesthetizationRec_findAllByCaseID(long id) throws Exception;
	public void anaesthetizationRec_Delete(AnaesthetizationRec record)throws Exception;
	public AnaesthetizationRec anaesthetizationRec_findById(Long id)throws Exception;
//	public String anaesthetizationRec_NewpageNum_find(Long id)throws Exception;
//	public void anaesthetizationRec_NewpageNum_saveOrUpdate(Long id,String npCfg)throws Exception;
}
