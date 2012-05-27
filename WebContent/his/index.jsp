<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.juncsoft.KLJY.util.*"%>
<%@ page language="java" import="java.sql.*"%>
<%
	Connection conn=DatabaseUtil.getConnetion_His();
	Statement sm=conn.createStatement();
	String sql="select * from "+DatabaseUtil.Oracle_table_patient+" where INWARDCODE ='S011'";
	ResultSet rs=sm.executeQuery(sql);
	ResultSetMetaData meta=rs.getMetaData();
	while(rs.next()){
		for(int i=0;i<meta.getColumnCount();i++){
			out.println(meta.getColumnName(i+1)+"="+rs.getString(i+1));
		}
		out.println("===============================================");
	}
	rs.close();
	sm.close();
	conn.close();
%>
