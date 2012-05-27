package com.juncsoft.KLJY.InHospitalCase.pitemScore.hisInport.entity;

import java.util.ArrayList;
import java.util.List;

public class Field {
	private String code;
	private String name;
	private String type;
	private int size;
	private String fieldValue;
	private String sourceFieldCode;
	private List fieldvalue = new ArrayList();

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getSourceFieldCode() {
		return sourceFieldCode;
	}

	public void setSourceFieldCode(String sourceFieldCode) {
		this.sourceFieldCode = sourceFieldCode;
	}

	public void addFieldValue(String str) {
		fieldvalue.add(str);
	}

	public List getFieldvalue() {
		return fieldvalue;
	}

	public void setFieldvalue(List fieldvalue) {
		this.fieldvalue = fieldvalue;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		Field f = (Field) obj;
		if (this.code.equals(f.getCode()) && this.name.equals(f.getName())
				&& this.type.equals(f.getType()) && this.size == f.getSize()) {
			return true;
		} else {
			return false;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}