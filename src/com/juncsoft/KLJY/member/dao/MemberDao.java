package com.juncsoft.KLJY.member.dao;

import java.util.List;

import net.sf.json.JSONObject;

import com.juncsoft.KLJY.member.entity.Member;
import com.juncsoft.KLJY.member.entity.MemberFirstGroup;
import com.juncsoft.KLJY.patient.entity.Patient;

public interface MemberDao {
	public JSONObject getPatInfo(Long patId) throws Exception;

	public Member mem_save(Member mem) throws Exception;

	public Member mem_update(Member mem) throws Exception;

	public void mem_delete(Member mem) throws Exception;

	public Member mem_findById(Long id) throws Exception;

	public Member mem_findByPatId(Long id) throws Exception;

	public List<Patient> mempat_findAll(Integer start, Integer limit)
			throws Exception;

	public long mempat_getTotal() throws Exception;

	public JSONObject mempat_search(String keyword) throws Exception;

	public MemberFirstGroup firstgroup_findByCaseId(Long id) throws Exception;

	public MemberFirstGroup firstgroup_saveOrUpdate(MemberFirstGroup group)
			throws Exception;
}
