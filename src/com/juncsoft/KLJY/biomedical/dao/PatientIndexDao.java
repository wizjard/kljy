package com.juncsoft.KLJY.biomedical.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.juncsoft.KLJY.biomedical.entity.PatientIndex;

/**
 * 病人索引DAO
 * 
 * @author xl 2010年12月8日9:31:22
 */
public interface PatientIndexDao {
	public List<PatientIndex> getAll(Integer start, Integer limit,
			Criterion... criterias) throws Exception;

	public long getTotal(Criterion... criterias) throws Exception;
}
