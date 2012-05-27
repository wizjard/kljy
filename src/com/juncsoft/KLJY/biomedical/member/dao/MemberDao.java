package com.juncsoft.KLJY.biomedical.member.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.hibernate.criterion.Criterion;

import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.biomedical.member.entity.HealthRecord;
import com.juncsoft.KLJY.biomedical.member.entity.MemberMsg;
import com.juncsoft.KLJY.biomedical.member.entity.MemberUploadFile;
import com.juncsoft.KLJY.patient.entity.Patient;

public interface MemberDao {
	public MemberInfo login(String account, String password) throws Exception;

	public List<InHspCaseMaster> getInHspCase(Patient pat) throws Exception;

	public List<HealthRecord> getHealthRecords(int start, int limit,
			Criterion... criterions) throws Exception;

	public long getHealthRecordTotal(Criterion... criterions) throws Exception;

	public Long saveHealthRecord(HealthRecord record) throws Exception;

	public void updateHealthRecord(HealthRecord record) throws Exception;

	public void deleteHealthRecord(HealthRecord record) throws Exception;

	public HealthRecord getHealthRecord(Long id) throws Exception;

	public List<MemberMsg> getMemberMsgs(int start, int limit,
			Criterion... criterions) throws Exception;
	
	public JSONObject getMemberMsgs2(int start, int limit,String ward,int flag) throws Exception;

	public long getMemberMsgsTotal(Criterion... criterions) throws Exception;

	public MemberMsg getMemberMsg(Long id) throws Exception;

	public Long saveOrUpdateMemberMsg(MemberMsg msg) throws Exception;

	public List<MemberUploadFile> getMemberUploadFiles(int start, int limit,
			Criterion... criterions) throws Exception;

	public long getMemberUploadFilesTotal(Criterion... criterions)
			throws Exception;

	public Long saveMemberUploadFile(MemberUploadFile file) throws Exception;
	
	public void updateMemberUploadFile(MemberUploadFile file) throws Exception;
	
	public MemberUploadFile getMemberUploadFile(Long id) throws Exception;
	
	public List<MemberUploadFile> getMemberUploadFileByMember(MemberInfo mem,Integer s,Integer l)throws Exception;

	public long getMemberUploadFileByMemberTotal(MemberInfo mem) throws Exception;
	
	public void deleteMemberUploadFile(MemberUploadFile file) throws Exception;
	
	public JSONObject getMyNotices(MemberInfo mem,Integer start,Integer limit)throws Exception;
	public JSONObject getLoginMemberInfo(HttpServletRequest reqeust);
	
	/**
	 * 会员密码校验
	 * @param memberNo
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean validOldPassword(String memberNo,String password)throws Exception;
	/**
	 * 会员修改密码
	 * @param memberNo
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public void changePassword(String memberNo,String password)throws Exception;
	/**
	 * 增加传递Pid参数直接查找病人的住院病历
	 * @param pat
	 * @return
	 * @throws Exception
	 */
	public List<InHspCaseMaster> getInHspCasePid(Long pid) throws Exception;
	
	public MemberInfo findUserNameAndPwd(String idNo, String patientName) throws Exception;
}
