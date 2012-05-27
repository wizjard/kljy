package com.juncsoft.KLJY.biomedical.dao;

import java.util.List;

import com.juncsoft.KLJY.biomedical.entity.MemberChangeWard;

public interface MemberChangeWardDao {
	public Long save(MemberChangeWard mc)throws Exception;
	public void update(MemberChangeWard mc)throws Exception;
	public void delete(MemberChangeWard mc)throws Exception;
	public MemberChangeWard get(Long id)throws Exception;
	public List<MemberChangeWard> getByMemNo(String memNo)throws Exception;
}
