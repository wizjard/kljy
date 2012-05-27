package com.juncsoft.KLJY.InHospitalCase.Fever.tool;

import java.util.ArrayList;
import java.util.List;

public class DispageTool {
	private int currentPage;
	private int totalRec;
	private int pageNum;
	private int totalPage;
	private boolean isHavePre = true;
	private boolean isHaveNext = true;
	private List data = new ArrayList();

	public DispageTool() {

	}

	public DispageTool(int currentPage, int pageNum, int totalRec, List data) {
		this.currentPage = currentPage;
		this.pageNum = pageNum;
		this.totalRec = totalRec;
		this.data = data;

		totalPage = (int) Math.ceil(totalRec * 1.00 / pageNum);
		if (currentPage == 1)
			isHavePre = false;
		if (currentPage == totalPage)
			isHaveNext = false;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public boolean isHaveNext() {
		return isHaveNext;
	}

	public void setHaveNext(boolean isHaveNext) {
		this.isHaveNext = isHaveNext;
	}

	public boolean isHavePre() {
		return isHavePre;
	}

	public void setHavePre(boolean isHavePre) {
		this.isHavePre = isHavePre;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRec() {
		return totalRec;
	}

	public void setTotalRec(int totalRec) {
		this.totalRec = totalRec;
	}
}
