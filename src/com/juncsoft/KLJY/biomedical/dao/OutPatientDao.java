package com.juncsoft.KLJY.biomedical.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.juncsoft.KLJY.biomedical.entity.OutpatientGenerator;
import com.juncsoft.KLJY.biomedical.entity.OutpatientRecord;
import com.stongnet.sms.http.Sms;

public interface OutPatientDao {
	public List<OutpatientRecord> getAll(Long patientId) throws Exception;

	public OutpatientRecord get(Long id) throws Exception;

	public void update(OutpatientRecord record) throws Exception;
	
	public String sendSms(List<Sms> smss) throws Exception;

	public Long saveGenerator(OutpatientGenerator gene) throws Exception;

	public OutpatientGenerator getGenerator(Long id) throws Exception;

	public void updateGenerator(OutpatientGenerator gene) throws Exception;

	public List<OutpatientGenerator> getAllGenerator(Integer start,
			Integer limit, Criterion... criterions) throws Exception;

	public long getTotalGenerator(Criterion... criterions) throws Exception;
}
