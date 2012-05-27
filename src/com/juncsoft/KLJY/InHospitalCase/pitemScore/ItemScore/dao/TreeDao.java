package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao;

import java.util.List;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Tree;

public interface TreeDao {
	public List<Tree> getTreeNode(Integer patientId) throws Exception;

	public Tree getRelPageName(Double treeId) throws Exception;
}
