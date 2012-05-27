package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao;

import java.io.OutputStream;

public interface ScoreCommentPrint1 {
	/**
	 * 评分系统打印函数
	 * 
	 * @param os
	 *            输出流
	 * @param root
	 *            项目根路径绝对地址
	 * @param kid
	 *            病历主键
	 * @param ssmid
	 *            ScoreSetMeal主键，获取评分子页面
	 * @param scid
	 *            评分子页面外键ScoreComment主键
	 * @throws Exception
	 */
	public void print(OutputStream os, String root, String kid, String ssmid,
			String scid) throws Exception;
}
