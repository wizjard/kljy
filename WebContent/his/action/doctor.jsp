<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.juncsoft.KLJY.util.DatabaseUtil" %>
<%@ page import="java.sql.*" %>
<%
String ward=request.getParameter("WARDCODE");
String root="";
if(ward==null||ward.length()==0){
	root="[]";
}else{
	try{
		Connection conn=DatabaseUtil.getConnetion_His();
		String sql="select DOCTORID,DOCTORNAME,ORGID from "
					+DatabaseUtil.Oracel_table_doc+" where ORGID='"
					+ward+"'";
		Statement sm=conn.createStatement();
		ResultSet rs=sm.executeQuery(sql);
		while(rs.next()){
			root+="{value:'"+rs.getString(1).trim()+"',text:'"+rs.getString(2).trim()+"'},";
		}
		DatabaseUtil.closeResource(conn,sm,rs);
		if(root.length()>0){
			root=root.substring(0,root.length()-1);
		}
		root="["+root+"]";
	}catch(Exception e){
		e.printStackTrace();
	}
}
out.println(root);
%>