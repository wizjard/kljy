<%@page import="com.juncsoft.KLJY.util.DatabaseUtil"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	Connection conn=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	String root="";
	try{
		String sql="select * from SYS_ZD_UserBelong where PCODE='40068648-6' order by CODE";
		conn=DatabaseUtil.getConnection();
		ps=conn.prepareStatement(sql);
		rs=ps.executeQuery();
		while(rs.next()){
			root+="{value:'"+rs.getString("CODE")+"',text:'("+rs.getString("CODE")+")"+rs.getString("NAME")+"'},";
		}
		if(root.length()>0){
			root=root.substring(0,root.length()-1);
		}
		root="["+root+"]";
	}catch(Exception e){
		root="[]";
		e.printStackTrace();
	}finally{
		out.println(root);
		DatabaseUtil.closeResource(conn,ps,rs);
	}
%>