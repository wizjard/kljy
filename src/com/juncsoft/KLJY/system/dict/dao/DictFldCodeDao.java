package com.juncsoft.KLJY.system.dict.dao;

import java.util.List;
import com.juncsoft.KLJY.system.dict.entity.DictFldcode;

public interface DictFldCodeDao {
	public List<DictFldcode> findByModuleId(Long moduleId) throws Exception;
	public void delete(List<DictFldcode> codes) throws Exception;
}
