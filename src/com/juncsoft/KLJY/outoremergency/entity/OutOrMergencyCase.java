package com.juncsoft.KLJY.outoremergency.entity;

import java.util.Date;

/**
 * 门诊记录
 * 2011-05-02
 * @author liugang
 *
 */
public class OutOrMergencyCase {
	private Long id;//主键自增1
	private Integer outCount;//门诊次数
	private Integer outType;//门诊类型0：非会员门诊、1：会员门诊、2：会员课题随访门诊1
	private Date outTime;//门诊时间1（自动生成一条门诊记录的时间）
	private Integer outFollowTimes;//随访次数
	private Integer outFollowCycle;//随访周期(月)1
	private String outFollowContent;//随访内容1
	private Date outNoticeDate;//随访通知日期(暂时不管)
	private Date outReserveDate;//预约日期(暂时不管)
	private Date outPlanDate;//计划随访时间1
	private String biaoben;//预留标本 0：是 1：否(由于预留标本应该在门诊病历中设置，但是为了查询效率，所以将次设置在了门诊记录中)1
	private String outMainDoctor;//门诊医生(医生签字后更新)
	private String patientId ;//基表中的病人信息1
	private Date actdate;//挂号日期1
	private String outRegno;//门诊记录流水号1
	private String outDeptcode;//门诊科室编号1
	private String outDeptname;//门诊科室名称1
	private String groupName;//会员分组组名1
	private String memo;//备注1
	private String outTitle;//门诊病历标题
	private String outContent;//门诊病历内容
	private Long outSubmiter;//门诊提交者
	private Date outSubmitTime;//门诊签字时间
	private Long outChecker;//门诊审核
	private Date outCheckTime;//门诊审核时间
	private Integer outVerifyStatus;//是否可以修改
	private String outOldOperation;//修改 操作
	private Date outModifyTime;//修改 时间
	private Date outWriteTime;//门诊书写时间
	private Date outSuiFangStartTime;//门诊随访书写时间
	//新岸线新加字段
	private String planIds;
	private String planItemIds;
	private String planItems;
	
	public String getPlanIds() {
		return planIds;
	}
	public void setPlanIds(String planIds) {
		this.planIds = planIds;
	}
	public String getPlanItemIds() {
		return planItemIds;
	}
	public void setPlanItemIds(String planItemIds) {
		this.planItemIds = planItemIds;
	}
	public String getPlanItems() {
		return planItems;
	}
	public void setPlanItems(String planItems) {
		this.planItems = planItems;
	}
	
	public Integer getOutCount() {
		return outCount;
	}
	public void setOutCount(Integer outCount) {
		this.outCount = outCount;
	}
	public Integer getOutType() {
		return outType;
	}
	public void setOutType(Integer outType) {
		this.outType = outType;
	}
	public Integer getOutFollowTimes() {
		return outFollowTimes;
	}
	public void setOutFollowTimes(Integer outFollowTimes) {
		this.outFollowTimes = outFollowTimes;
	}
	public String getOutMainDoctor() {
		return outMainDoctor;
	}
	public void setOutMainDoctor(String outMainDoctor) {
		this.outMainDoctor = outMainDoctor;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getOutTime() {
		return outTime;
	}
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public Date getActdate() {
		return actdate;
	}
	public void setActdate(Date actdate) {
		this.actdate = actdate;
	}
	public String getOutRegno() {
		return outRegno;
	}
	public void setOutRegno(String outRegno) {
		this.outRegno = outRegno;
	}
	public String getOutDeptcode() {
		return outDeptcode;
	}
	public void setOutDeptcode(String outDeptcode) {
		this.outDeptcode = outDeptcode;
	}
	public String getOutDeptname() {
		return outDeptname;
	}
	public void setOutDeptname(String outDeptname) {
		this.outDeptname = outDeptname;
	}
	public Integer getOutFollowCycle() {
		return outFollowCycle;
	}
	public void setOutFollowCycle(Integer outFollowCycle) {
		this.outFollowCycle = outFollowCycle;
	}
	public String getOutFollowContent() {
		return outFollowContent;
	}
	public void setOutFollowContent(String outFollowContent) {
		this.outFollowContent = outFollowContent;
	}
	public Date getOutNoticeDate() {
		return outNoticeDate;
	}
	public void setOutNoticeDate(Date outNoticeDate) {
		this.outNoticeDate = outNoticeDate;
	}
	public Date getOutReserveDate() {
		return outReserveDate;
	}
	public void setOutReserveDate(Date outReserveDate) {
		this.outReserveDate = outReserveDate;
	}
	public Date getOutPlanDate() {
		return outPlanDate;
	}
	public void setOutPlanDate(Date outPlanDate) {
		this.outPlanDate = outPlanDate;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getBiaoben() {
		return biaoben;
	}
	public void setBiaoben(String biaoben) {
		this.biaoben = biaoben;
	}
	public String getOutTitle() {
		return outTitle;
	}
	public void setOutTitle(String outTitle) {
		this.outTitle = outTitle;
	}
	public String getOutContent() {
		return outContent;
	}
	public void setOutContent(String outContent) {
		this.outContent = outContent;
	}
	public Long getOutSubmiter() {
		return outSubmiter;
	}
	public void setOutSubmiter(Long outSubmiter) {
		this.outSubmiter = outSubmiter;
	}
	public Integer getOutVerifyStatus() {
		return outVerifyStatus;
	}
	public void setOutVerifyStatus(Integer outVerifyStatus) {
		this.outVerifyStatus = outVerifyStatus;
	}
	public Date getOutSubmitTime() {
		return outSubmitTime;
	}
	public void setOutSubmitTime(Date outSubmitTime) {
		this.outSubmitTime = outSubmitTime;
	}
	public Long getOutChecker() {
		return outChecker;
	}
	public void setOutChecker(Long outChecker) {
		this.outChecker = outChecker;
	}
	public Date getOutCheckTime() {
		return outCheckTime;
	}
	public void setOutCheckTime(Date outCheckTime) {
		this.outCheckTime = outCheckTime;
	}
	public String getOutOldOperation() {
		return outOldOperation;
	}
	public void setOutOldOperation(String outOldOperation) {
		this.outOldOperation = outOldOperation;
	}
	public Date getOutModifyTime() {
		return outModifyTime;
	}
	public void setOutModifyTime(Date outModifyTime) {
		this.outModifyTime = outModifyTime;
	}
	public Date getOutWriteTime() {
		return outWriteTime;
	}
	public void setOutWriteTime(Date outWriteTime) {
		this.outWriteTime = outWriteTime;
	}
	public Date getOutSuiFangStartTime() {
		return outSuiFangStartTime;
	}
	public void setOutSuiFangStartTime(Date outSuiFangStartTime) {
		this.outSuiFangStartTime = outSuiFangStartTime;
	}
}
