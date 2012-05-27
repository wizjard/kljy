package com.juncsoft.KLJY.InHospitalCase.CheckReport.dao;

import java.util.Date;

public interface CopyDataDao {

	public boolean copyReportInfo();
	public boolean copyReportInfoByDate(String date, String dateEnd);
}
