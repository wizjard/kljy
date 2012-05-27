package com.juncsoft.KLJY.system.dict.dao;

import java.util.List;

import com.juncsoft.KLJY.system.dict.entity.DictItem;

public interface DictItemDao {
	public List<DictItem> findByIndexId(Long indexId) throws Exception;
	public void delete(List<DictItem> items) throws Exception;
}
