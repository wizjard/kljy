package com.juncsoft.KLJY.system.dict.dao;

import java.util.List;
import com.juncsoft.KLJY.system.dict.entity.DictModule;

public interface DictModuleDao {
	public List<DictModule> queryAllModules() throws Exception;
	public String queryAllModulesExtTree() throws Exception;
	public void treeModule_delete(Long id) throws Exception;
	public void copyNode2Node(Long oldId,Long newId) throws Exception;
	public void exportNode(String path,Long id)throws Exception;
	public void importNode(String path,Long pid)throws Exception;
}
