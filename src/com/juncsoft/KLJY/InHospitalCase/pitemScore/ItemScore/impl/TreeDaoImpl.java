package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.TreeDao;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Table;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Tree;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.util.TableConig;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class TreeDaoImpl implements TreeDao {

	/**
	 * 获取节点
	 * 
	 * @param patientId
	 *            病人Id 实体类String类型
	 * @return Tree tree
	 * @throws Exception
	 */
	public List<Tree> getTreeNode(Integer patientId) throws Exception {
		List<Tree> treeList = new ArrayList<Tree>();
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "select * from ItemScore_Tree order by TreeID";
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				Tree tree = new Tree();
				tree.setPId(rs.getString("PID"));
				tree.setTreeId(rs.getString("TreeID"));
				tree.setNodeName(rs.getString("NodeName"));
				tree.setHref("#");
				tree.setPageName(rs.getString("PageName"));
				treeList.add(tree);
				if (rs.getInt("PID") > 0) {
					getTreeNodeEx(treeList, rs.getString("RelTable"),
							patientId, rs.getString("TreeID"));
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return treeList;
	}

	/**
	 * 新增
	 * 
	 * @param treeList
	 *            List<Tree>
	 * @param tableCode
	 *            relTable String
	 * @param patientId
	 *            病人ID 强转为Integer
	 * @param PID
	 *            TreeID String
	 * @throws Exception
	 */
	private void getTreeNodeEx(List<Tree> treeList, String tableCode,
			Integer patientId, String PID) throws Exception {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		Tree add = new Tree();
		add.setPId(PID);
		add.setTreeId(PID + "_-1");
		add.setHref("javascript:nodeClick('" + PID + "_-1','" + PID + "');");
		add.setNodeName("新增");
		treeList.add(add);
		try {
			Table table = TableConig.getTableConfig(tableCode);
			conn = DatabaseUtil.getConnection();
			
			String sql = "select opeDate," + table.getKeyField() + " from "
					+ table.getTableCode() + " where PatientID=" + patientId
					+ " order by opeDate desc";
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				Tree tree = new Tree();
				tree.setPId(PID);
				tree.setTreeId(PID + "_" + rs.getInt(2));
				Date date = rs.getDate(1);
				if (date != null) {
					tree.setNodeName((date.getYear() + 1900) + "年"
							+ (date.getMonth() + 1) + "月" + date.getDate()
							+ "日");
				}
				tree.setHref("javascript:nodeClick('" + PID + "_"
						+ rs.getInt(2) + "','" + PID + "')");
				treeList.add(tree);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
	}

	public Tree getRelPageName(Double treeId) throws Exception {
		Tree rst = new Tree();
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "select PageName,NodeName,SimpleName from ItemScore_Tree where TreeID="+ treeId;
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next()) {
				String pageName = rs.getString(1);
				String nodeName = rs.getString(2);
				rst.setPageName(pageName);
				rst.setNodeName(nodeName);
				rst.setSimpleName(rs.getString(3));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return rst;
	}
}
