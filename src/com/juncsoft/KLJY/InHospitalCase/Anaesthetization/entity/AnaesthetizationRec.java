package com.juncsoft.KLJY.InHospitalCase.Anaesthetization.entity;

import java.util.Date;

public class AnaesthetizationRec {

		private Long id;
		private Long patientId;
		private Long caseId;
		private Date time;
		private String title;
		private String content;
		private Long submiter;
		private Date submitTime;
		private Long checker;
		private Date checkTime;
		private int verifyStatus;
		private Date createTime;
		private Date modifyTime;
		public int getVerifyStatus() {
			return verifyStatus;
		}
		public void setVerifyStatus(int verifyStatus) {
			this.verifyStatus = verifyStatus;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Date getModifyTime() {
			return modifyTime;
		}
		public void setModifyTime(Date modifyTime) {
			this.modifyTime = modifyTime;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getPatientId() {
			return patientId;
		}
		public void setPatientId(Long patientId) {
			this.patientId = patientId;
		}
		public Long getCaseId() {
			return caseId;
		}
		public void setCaseId(Long caseId) {
			this.caseId = caseId;
		}
		public Date getTime() {
			return time;
		}
		public void setTime(Date time) {
			this.time = time;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public Long getSubmiter() {
			return submiter;
		}
		public void setSubmiter(Long submiter) {
			this.submiter = submiter;
		}
		public Date getSubmitTime() {
			return submitTime;
		}
		public void setSubmitTime(Date submitTime) {
			this.submitTime = submitTime;
		}
		public Long getChecker() {
			return checker;
		}
		public void setChecker(Long checker) {
			this.checker = checker;
		}
		public Date getCheckTime() {
			return checkTime;
		}
		public void setCheckTime(Date checkTime) {
			this.checkTime = checkTime;
		}

}
