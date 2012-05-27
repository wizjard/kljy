<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.juncsoft.KLJY.patient.entity.Patient"%>
<%@page import="com.juncsoft.KLJY.util.*"%>
<%@page import="org.hibernate.*"%>
<%@page import="java.util.*"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%

	Patient pat=new Patient();
	Session sess=null;
	Transaction tran=null;
	String memGrounp="";
	String memberType ="";
	Connection conn = null;
	ResultSet rs = null;
	try{
		sess=DatabaseUtil.getSession();
		tran=sess.beginTransaction();
		pat=(Patient)sess.get(Patient.class,
				Long.parseLong(request.getParameter("id")));
		String hql="select mem.memberType,his.deptName,gr.grounpName from MemberInfo mem"
			 +" left join SYS_HIS_DepartmentHISEntity his on mem.deptCode = his.deptCode "
			 +" left join t_DepartmentGrounp gr on gr.id = mem.grounpId where mem.patient ="+pat.getId();
		List listMem = sess.createSQLQuery(hql).list();
		if(listMem != null && listMem.size() >0){
			Object[] obj = (Object[])listMem.get(0);
			memGrounp = obj[1].toString()+obj[2].toString();
			memberType = obj[0].toString();
		}
		tran.commit();
	}catch(Exception e){
		tran.rollback();
		out.println(e.getMessage());
	}finally{
		DatabaseUtil.closeResource(sess);
	}
%>

<ul id="pat-info-conn">
	<li class="label">姓名</li>
	<li class="input"><%=pat.getPatientName()%></li>
	<li class="label">病案号</li>
	<li class="input"><%=pat.getPatientNo()%></li>
	<li class="label">性别</li>
	<li class="input"><%out.print(DictionaryUtil.getIndependentDictionaryText("gender_gb",pat.getGender()));%></li>
	<li class="label">生日</li>
	<li class="input"><%
							java.util.Date birthday=pat.getBirthday();
							if(birthday!=null){
								out.print(new SimpleDateFormat("yyyy-MM-dd").format(birthday));
							}
						%></li>
	<li class="label">会员</li>
	<li class="input"><%
		out.print(memberType);
	 %></li>
	
</ul>
<script type="text/javascript">
var _ul_css='font-size:12px;padding:0;list-style:none;margin:0';
var _li_label_css='float:left;'+
'background:url(<%=request.getContextPath()%>/PUBLIC/images/label_line_bg.gif) center repeat;'+
'border:solid 1px #7D7D7D;line-height:18px;padding:3px 10px 1px 10px;'+
'font-weight:bold;color:#200F4E;border-left:none;';
var _li_input_css='float:left;border:solid 1px #7D7D7D;background:#FFF;'+
'line-height:18px;padding:2px 5px 2px 5px;border-left:none;color:#002394;';
var _ul=document.getElementById('pat-info-conn');
_ul.style.cssText=_ul_css;
var _lis=_ul.childNodes;
for(var i=0,len=_lis.length;i<len;i++){
	var _li=_lis[i];
	if(_li.className=='label'){
		if(i==0)
			_li_label_css+='border-left:solid 1px #7D7D7D;';
		_li.style.cssText=_li_label_css;
	}else{
		_li.style.cssText=_li_input_css;
	}
}
</script>