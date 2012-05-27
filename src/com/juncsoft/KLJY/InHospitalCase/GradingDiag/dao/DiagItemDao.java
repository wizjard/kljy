package com.juncsoft.KLJY.InHospitalCase.GradingDiag.dao;

import java.util.List;
import com.juncsoft.KLJY.InHospitalCase.GradingDiag.entity.DiagItem;

public interface DiagItemDao {
	public void addDiagItem(DiagItem item)throws Exception;
	public void updateDiagItem(DiagItem item)throws Exception;
	public void deleteDiagItem(DiagItem item)throws Exception;
	public boolean isCodeExist(String code)throws Exception;
	public DiagItem getDiagItem(String code)throws Exception;
	public List<DiagItem> getChildren(DiagItem item)throws Exception;
}
