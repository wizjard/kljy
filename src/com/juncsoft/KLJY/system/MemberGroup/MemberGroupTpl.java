package com.juncsoft.KLJY.system.MemberGroup;

public class MemberGroupTpl{
	private Long id;
	private Long groupId;
	private int type;
	private String name;
	private String perValumon;
	private String unit;
	private String useFn;
	private int course;
	private int courseUnit;
	private String memo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPerValumon() {
		return perValumon;
	}
	public void setPerValumon(String perValumon) {
		this.perValumon = perValumon;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUseFn() {
		return useFn;
	}
	public void setUseFn(String useFn) {
		this.useFn = useFn;
	}
	public int getCourse() {
		return course;
	}
	public void setCourse(int course) {
		this.course = course;
	}
	public int getCourseUnit() {
		return courseUnit;
	}
	public void setCourseUnit(int courseUnit) {
		this.courseUnit = courseUnit;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}