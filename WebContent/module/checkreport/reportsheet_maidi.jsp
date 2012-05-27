<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.juncsoft.KLJY.checkreport.dao.CheckReportDao" %>
<%@page import="com.juncsoft.KLJY.checkreport.entity.Pacs" %>
<%@page import="com.juncsoft.KLJY.checkreport.entity.Maidi" %>
<%@page import="com.juncsoft.KLJY.checkreport.impl.CheckReportDaoImpl" %>
<%@page import="com.juncsoft.KLJY.patient.dao.PatientDao" %>
<%@page import="com.juncsoft.KLJY.patient.entity.Patient" %>
<%@page import="com.juncsoft.KLJY.patient.impl.PatientDaoImpl" %>
<%@page import="com.juncsoft.KLJY.util.DateUtil" %>
<%@page import="com.juncsoft.KLJY.util.JspUtil" %>
<%@page import="com.juncsoft.KLJY.biomedical.entity.MemberInfo" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;

		PatientDao patientDao = new PatientDaoImpl();
		CheckReportDao checkReportDao = new CheckReportDaoImpl();
		Long id = Long.parseLong(request.getParameter("id"));
		Maidi report = null;
		Patient patient = null;
		String gender = "";
		String birthday = "";
		String inHspNum = "";
		String outNum = "";
		String imageView = "";
		String reportResult = "";
		String checkPart = "";
		String reportName = "";
		String reportFileName = null;
		String checkItem = "";
		Long paId = null;
		boolean isMemberAccount = false;
		MemberInfo mem = null;
		try {
			mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			if(mem != null){
				isMemberAccount = true;
			}
			report = checkReportDao.getMaidiReport(id);
			String patientId = report.getPatientId();
			patient = patientDao.findByPatientId(patientId);
			paId = patient.getId();
			if(patient.getGender().equals("2")){
				gender = "女";
			}else{
				gender = "男";
			}
			birthday = DateUtil.formatDate(patient.getBirthday(),1);
			inHspNum = report.getInHspNum() == null? "" : report.getInHspNum();
			outNum = report.getOutNum() == null? "" : report.getOutNum();
			imageView = report.getImageView()==null ? "" : report.getImageView();
			reportResult = report.getReportResult()==null ? "" : report.getReportResult();
			checkPart = report.getCheckPart()==null ? "" : report.getCheckPart();
			reportName = report.getReportName()==null? "综合医学影像检查报告单" : report.getReportName();
			checkItem = report.getCheckItem()==null ? "" : report.getCheckItem();
			reportFileName = report.getReportFileName()==null ? "" : report.getReportFileName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		imageView = JspUtil.tranToBr(imageView);
		reportResult = JspUtil.tranToBr(reportResult);
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>检查报告</title>
		<style type="text/css">
			.block{
				width:600px;
				font-size:medium;
				font-family:"宋体";
				text-align:left;
			}
			#sheet{
				border:solid 1px #cccccc;
				margin:10px;
				padding:30px;
				width:600px;
				background-color:#ffffee;
			}
		</style>
		<script type="text/javascript"><!--
			function openImg(fileName,item){
				if(item=="" || fileName==""){
					alert("对不起，此检查报告单尚未添加图像。");
					return;
				}
				window.open(
					"medexImg.jsp?file="+fileName+"&item="+item,
					"_blank",
					"scrollbars=yes,resizable=yes,location=no,height=500,width=600"
				);
			}
		--></script>
	</head>
	<body>
	  <div id="sheet">
		<div class="block">
				<table border=0 cellpadding=5 cellspacing=0>
					<tr>
						<td width="150" align="right" rowspan=2>
							<img src="<%=basePath %>/module/checkreport/youanlogo.png" height=70>
						</td>
						<td align="left" valign="middle">
							<div style="text-align:center;">
							<table border=0 cellpadding=5 cellspacing=0>
								<tr><td style="font-size:24px;"><b>首都医科大学附属北京佑安医院</b></td></tr>
								<tr><td style="font-size:20px;"><%=reportName %></td></tr>
							</table></div>
						</td>
					</tr>
				</table>
		</div>
		<div class="block">
			<table border=0 cellpadding=5 cellspacing=0 style="border-bottom:solid 1px #aaaaaa;">
				<tr>
					<td width=300>病人号：<span><%=patient.getPatientNo() %></span></td>
					<td width=300>检查号：<span><%=report.getCheckNum()==null?"":report.getCheckNum() %></span></td>
				</tr>
				<tr>
					<td>检查日期：<span><%=report.getCheckDate() %></span></td>
					<td></td>
				</tr>
			</table>
		</div>
		<div class="block">
			<table border=0 cellpadding=5 cellspacing=0 style="border-bottom:solid 1px #aaaaaa;">
				<tr>
					<td width=200>姓名：<span><%=patient.getPatientName() %></span></td>
					<td width=160>性别：<span><%=gender %></span></td>
					<td width=240>出生日期：<span><%=birthday %></span></td>
				</tr>
				<tr>
					
					<td>门诊号：<span><%=outNum %></span></td>
					<td>住院号：<span><%=inHspNum %></span></td>
					<td>检查项目：<span><%=checkItem %></span></td>
				</tr>
				<tr>
					<td colspan=3>检查部位：<span><%=checkPart %></span></td>
				</tr>
			</table>
		</div>
		<div class="block">
			<table border=0 cellpadding=5 cellspacing=0>
				<tr>
					<td colspan=2>
					<%if(!isMemberAccount){ %>
					<input id="imageView" type="checkbox"></input>
					<%} %>
					检查所见：
					</td>
				</tr>
				<tr>
					<td width=50></td>
					<td width=550><%=imageView %></td>
				</tr>
				<tr>
					<td width=50></td>
					<td colspan="2" align="left">
						<input type="button" value="查看图像" onclick="openImg('<%=reportFileName %>','<%=checkItem %>');"></input>
					</td>
				</tr>
			</table>
		</div>
		<div class="block">
			<table border=0 cellpadding=5 cellspacing=0>
				<tr>
					<td colspan=2>&nbsp;</td>
				</tr>
				<tr>
					<td colspan=2>
					<%if(!isMemberAccount){ %>
					<input id="reportResult" type="checkbox"></input>
					<%} %>
					检查结果：
					</td>
				</tr>
				<tr>
					<td width=50></td>
					<td width=550><%=reportResult %></td>
				</tr>
			</table>
		</div>
		<div class="block">
			<table border=0 cellpadding=5 cellspacing=0 style="border-bottom:solid 1px #aaaaaa;">
				<tr>
					<td width=600>&nbsp;</td>
				</tr>
			</table>
		</div>
		<div class="block">
			<table border=0 cellpadding=8 cellspacing=0>
				<tr>
					<td style="font-size:smaller">本报告结果仅供参考，请以医院签发的纸质版检查报告结果为准</td>
				</tr>
			</table>
		</div>
	  </div>
	  <script type="text/javascript">
	function showPlan(paid, repid) {
	   var url = "../../module/plan/selectPlan.html?paId=" + paid + "&repId=" + repid+ "&type=maidi";
	   window.open(url, 'plan', 'toolbar=no,scrollbar=yes, top=200, left=100,width=1200,height=500');
	}
	</script>
	  <%if(!isMemberAccount){ %>
	  <div style="width:600px; text-align:center; ">
	  		<input type="button" value="关联" onclick="showPlan(<%= paId%>,<%=id %>)"/>
	  		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  		<input type="button" value="导入" />
	  </div>
	  <%} %>
	</body>
</html>
