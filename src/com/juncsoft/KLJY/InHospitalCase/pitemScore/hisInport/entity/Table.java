package com.juncsoft.KLJY.InHospitalCase.pitemScore.hisInport.entity;

import java.util.ArrayList;
import java.util.List;


public class Table {
	private String tableCode;
	private String sourceTableCode;
	private String keyValue;
	private String tableName;
	private String sourceKeyCode;
	private String keyCode;
	private String keyName;
	private String keyType;
	private String delControl;
	private int keySize;
	private List keyvalue = new ArrayList();
	private List fields = new ArrayList();

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getSourceTableCode() {
		return sourceTableCode;
	}

	public void setSourceTableCode(String sourceTableCode) {
		this.sourceTableCode = sourceTableCode;
	}

	public String getSourceKeyCode() {
		return sourceKeyCode;
	}

	public void setSourceKeyCode(String sourceKeyCode) {
		this.sourceKeyCode = sourceKeyCode;
	}

	public String getDelControl() {
		return delControl;
	}

	public void setDelControl(String delControl) {
		this.delControl = delControl;
	}

	public void addKeyValue(String str) {
		keyvalue.add(str);
	}

	public List getKeyvalue() {
		return keyvalue;
	}

	public void setKeyvalue(List keyvalue) {
		this.keyvalue = keyvalue;
	}

	public void addField(Field f) {
		fields.add(f);
	}

	public List getFields() {
		return fields;
	}

	public void setFields(List fields) {
		this.fields = fields;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public int getKeySize() {
		return keySize;
	}

	public void setKeySize(int keySize) {
		this.keySize = keySize;
	}
}
