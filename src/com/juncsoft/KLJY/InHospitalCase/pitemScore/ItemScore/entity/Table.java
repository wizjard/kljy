package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Table {
	private String tableCode;

	private String keyValue;

	private String keyField;

	private String keyFieldType;

	private String createDate;

	private String lastModifyDate;

	private Timestamp currentDate;

	private List<Field> fields = new ArrayList<Field>();

	public Table() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		currentDate = Timestamp.valueOf(c.get(Calendar.YEAR) + "-"
				+ (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE)
				+ " " + c.get(Calendar.HOUR_OF_DAY) + ":"
				+ c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND));
	}

	public void addField(Field field) {
		this.fields.add(field);
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String getKeyField() {
		return keyField;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public String getKeyFieldType() {
		return keyFieldType;
	}

	public void setKeyFieldType(String keyFieldType) {
		this.keyFieldType = keyFieldType;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public Timestamp getCurrentDate() {
		return currentDate;
	}
}
