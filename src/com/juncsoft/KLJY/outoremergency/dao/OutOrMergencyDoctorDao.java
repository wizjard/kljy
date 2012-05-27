package com.juncsoft.KLJY.outoremergency.dao;

import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyDoctor;

/**
 * 加载医生登录信息
 * @author liugang
 *
 */
public interface OutOrMergencyDoctorDao {
	public OutOrMergencyDoctor executeHISDoctorByIdnumberAndPassword(String idnumber);
}
