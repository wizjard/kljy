<%@ page language="java" pageEncoding="UTF-8"%>
<%
String doctorId=request.getParameter("DoctorID");
String wardCode=request.getParameter("OrgID");
String patientId=request.getParameter("PatientID");
String admseqNo=request.getParameter("Admseqno");
if(doctorId==null||doctorId.length()==0){
	out.println("无登录用户。");
}else{
	String url=request.getContextPath()
				+"/user.do?method=login_his&DoctorID="
				+doctorId
				+"&WardCode="
				+wardCode
				+"&PatientID="
				+patientId
				+"&Admseqno="
				+admseqNo;
	response.sendRedirect(url);	
}
%>