<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.juncsoft.KLJY.util.DatabaseUtil" %>
<%@ page import="java.sql.*" %>
<%
try{
String ward=request.getParameter("WARDCODE");;
if(ward==null||ward.length()==0){
	out.println("{totalProperty:0,root:[]}");
	return;
}
Connection conn=DatabaseUtil.getConnetion_His();
String sql="select * from "+DatabaseUtil.Oracle_table_patient+" where INWARDCODE='"+ward+"' order by INDATE desc";	
Statement sm=conn.createStatement();
ResultSet rs=sm.executeQuery(sql);
String root="";
while(rs.next()){
	root+="{'PATIENTID':'"+rs.getString("PATIENTID")+"',";
	root+="'PATIENTNO':'"+rs.getString("PATIENTNO")+"',";
	root+="'PATIENTNAME':'"+rs.getString("PATIENTNAME")+"',";
	root+="'SEXID':'"+rs.getInt("SEXID")+"',";
	root+="'AGE':'"+rs.getInt("AGE")+"',";
	root+="'BIRTHDAY':'"+rs.getDate("BIRTHDAY")+"',";
	root+="'SOCIETYNO':'"+rs.getString("SOCIETYNO")+"',";
	root+="'NATIONALITYID':'"+rs.getString("NATIONALITYID")+"',";
	root+="'PEOPLEID':'"+rs.getString("PEOPLEID")+"',";
	root+="'HOMEADDR':'"+rs.getString("HOMEADDR")+"',";
	root+="'HOMEPOSTCODE':'"+rs.getString("HOMEPOSTCODE")+"',";
	root+="'HOMETEL':'"+rs.getString("HOMETEL")+"',";
	root+="'WORKORG':'"+rs.getString("WORKORG")+"',";
	root+="'ORGADDR':'"+rs.getString("ORGADDR")+"',";
	root+="'ORGPOSTCODE':'"+rs.getString("ORGPOSTCODE")+"',";
	root+="'CONTACTNAME':'"+rs.getString("CONTACTNAME")+"',";
	root+="'CONTACTTEL':'"+rs.getString("CONTACTTEL")+"',";
	root+="'INDATE':'"+rs.getDate("INDATE")+"',";
	root+="'INTIME':'"+rs.getString("INTIME")+"',";
	root+="'INWARDCODE':'"+rs.getString("INWARDCODE")+"',";
	root+="'INILLS':'"+rs.getString("INILLS")+"',";
	root+="'DRCODE':'"+rs.getString("DRCODE")+"',";
	root+="'ADMSEQNO':'"+rs.getInt("ADMSEQNO")+"',";
	root+="'INSTATUS':'"+rs.getInt("INSTATUS")+"'},";
}
root=root.length()>0?"["+root.substring(0,root.length()-1)+"]":"";
conn.close();
out.println("{totalProperty:0,root:"+root+"}");
}catch(Exception e){
	e.printStackTrace();
}
%>