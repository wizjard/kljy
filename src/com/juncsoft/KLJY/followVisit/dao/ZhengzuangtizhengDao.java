package com.juncsoft.KLJY.followVisit.dao;
import java.util.List;

import com.juncsoft.KLJY.followVisit.entity.Ptsymptomst;
import com.juncsoft.KLJY.followVisit.entity.Symptomst;

public interface ZhengzuangtizhengDao {
	public List findSymptomList(Integer pt_id) throws Exception;

	public void deleteSymptom(Symptomst symptomst) throws Exception;

	public Integer saveSymptom(Symptomst symptomst) throws Exception;

	public Symptomst findSymptom(Integer sy_id) throws Exception;

	public void updateSymptom(Symptomst symptomst) throws Exception;

	public List findT_state() throws Exception;

	public Integer savePtsymptomst(Ptsymptomst ptsymptomst) throws Exception;

	public List findT_symptom() throws Exception;

	public Integer getMaxid() throws Exception;

	public Integer getLassptid(Integer pid) throws Exception;

	public Integer getMoreTwo(Integer pid) throws Exception;

	// all
	public String findZhengZuang(Integer pid, Integer ptid, Integer tmid)
			throws Exception;// ״̬

	public List findCount(Integer pid) throws Exception;// �ǼǴ���

	public List findZhengZuangName(Integer pid) throws Exception;// ״̬
}
