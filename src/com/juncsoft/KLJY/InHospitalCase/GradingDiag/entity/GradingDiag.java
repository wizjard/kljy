package com.juncsoft.KLJY.InHospitalCase.GradingDiag.entity;

import java.util.Date;

public class GradingDiag {
	private Long id;  //���
	private Long cid;  //既是住院记录也是门诊记录����ID
	private Date date;  //���ʱ��
	private String code; //��ϱ���
	private String doc1; // סԺҽʦ
	private String doc2; // ����ҽʦ
	private String doc3;  //����/������ҽʦ
	private String diag1;//һ�����
	private String diag2;//�������
	private String diag3; //�����
	private String diag4;//�ļ����
	private String diag5;//�弶���
	private String diag6;//�����
	private String diag7;//�߼����
	private String diag8;//�˼����
	private String diag9;//�ż����
	private String diag10;//ʮ�����
	private String diag11;//ʮһ�����
	private String diag12;//ʮ�������
	private String diagnose;//
	private String diagGrounp;//当前疾病分组��Ͻ׶�
	private String fiag;   //�ж���住院是N，门诊是Y
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDoc1() {
		return doc1;
	}

	public void setDoc1(String doc1) {
		this.doc1 = doc1;
	}

	public String getDoc2() {
		return doc2;
	}

	public void setDoc2(String doc2) {
		this.doc2 = doc2;
	}

	public String getDoc3() {
		return doc3;
	}

	public void setDoc3(String doc3) {
		this.doc3 = doc3;
	}

	public String getDiag1() {
		return diag1;
	}

	public void setDiag1(String diag1) {
		this.diag1 = diag1;
	}

	public String getDiag2() {
		return diag2;
	}

	public void setDiag2(String diag2) {
		this.diag2 = diag2;
	}

	public String getDiag3() {
		return diag3;
	}

	public void setDiag3(String diag3) {
		this.diag3 = diag3;
	}

	public String getDiag4() {
		return diag4;
	}

	public void setDiag4(String diag4) {
		this.diag4 = diag4;
	}

	public String getDiag5() {
		return diag5;
	}

	public void setDiag5(String diag5) {
		this.diag5 = diag5;
	}

	public String getDiag6() {
		return diag6;
	}

	public void setDiag6(String diag6) {
		this.diag6 = diag6;
	}

	public String getDiag7() {
		return diag7;
	}

	public void setDiag7(String diag7) {
		this.diag7 = diag7;
	}

	public String getDiag8() {
		return diag8;
	}

	public void setDiag8(String diag8) {
		this.diag8 = diag8;
	}

	public String getDiag9() {
		return diag9;
	}

	public void setDiag9(String diag9) {
		this.diag9 = diag9;
	}

	public String getDiag10() {
		return diag10;
	}

	public void setDiag10(String diag10) {
		this.diag10 = diag10;
	}

	public String getDiag11() {
		return diag11;
	}

	public void setDiag11(String diag11) {
		this.diag11 = diag11;
	}

	public String getDiag12() {
		return diag12;
	}

	public void setDiag12(String diag12) {
		this.diag12 = diag12;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public String getFiag() {
		return fiag;
	}

	public void setFiag(String fiag) {
		this.fiag = fiag;
	}

	public String getDiagGrounp() {
		return diagGrounp;
	}

	public void setDiagGrounp(String diagGrounp) {
		this.diagGrounp = diagGrounp;
	}

	
}
