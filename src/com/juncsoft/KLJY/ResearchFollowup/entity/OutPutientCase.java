package com.juncsoft.KLJY.ResearchFollowup.entity;

import java.util.Date;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.LabExamination;

public class OutPutientCase {
	private Long id;
	private Long patientId;
	private Long followupId;
	private Date reginDate;
	private String chiefComplaint;
	private String pressentIllness;
	private String otherDisHistroy;
	private String physicalExam;
	private String labExam;
	private String chubu_diag;
	private String treatAd;
	private String otherMemo;
	private Long doctorId;
	private String oldOperation;
	
	private LabExamination lab=new LabExamination();
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
	public Long getFollowupId() {
		return followupId;
	}
	public void setFollowupId(Long followupId) {
		this.followupId = followupId;
	}
	public Date getReginDate() {
		return reginDate;
	}
	public void setReginDate(Date reginDate) {
		this.reginDate = reginDate;
	}
	public String getChiefComplaint() {
		return chiefComplaint;
	}
	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}
	public String getPressentIllness() {
		return pressentIllness;
	}
	public void setPressentIllness(String pressentIllness) {
		this.pressentIllness = pressentIllness;
	}
	public String getOtherDisHistroy() {
		return otherDisHistroy;
	}
	public void setOtherDisHistroy(String otherDisHistroy) {
		this.otherDisHistroy = otherDisHistroy;
	}
	public String getPhysicalExam() {
		return physicalExam;
	}
	public void setPhysicalExam(String physicalExam) {
		this.physicalExam = physicalExam;
	}
	public String getLabExam() {
		return labExam;
	}
	public void setLabExam(String labExam) {
		this.labExam = labExam;
	}
	public String getChubu_diag() {
		return chubu_diag;
	}
	public void setChubu_diag(String chubuDiag) {
		chubu_diag = chubuDiag;
	}
	public String getTreatAd() {
		return treatAd;
	}
	public void setTreatAd(String treatAd) {
		this.treatAd = treatAd;
	}
	public String getOtherMemo() {
		return otherMemo;
	}
	public void setOtherMemo(String otherMemo) {
		this.otherMemo = otherMemo;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public LabExamination getLab() {
		return lab;
	}
	public void setLab(LabExamination lab) {
		this.lab = lab;
	}
	public String getOldOperation() {
		return oldOperation;
	}
	public void setOldOperation(String oldOperation) {
		this.oldOperation = oldOperation;
	}
	
}
