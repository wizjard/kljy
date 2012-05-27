package com.juncsoft.KLJY.system.dict.entity;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
		private String id;
		private String text;
		private boolean leaf;
		private List<TreeNode> children=new ArrayList<TreeNode>();
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public boolean isLeaf() {
			return leaf;
		}
		public void setLeaf(boolean leaf) {
			this.leaf = leaf;
		}
		public List<TreeNode> getChildren() {
			return children;
		}
		public void setChildren(List<TreeNode> children) {
			this.children = children;
		}
}
