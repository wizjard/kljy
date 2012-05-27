<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>康乐家园-会员登录</title>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/PUBLIC/Scripts/common.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader_ext.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader_jquery.js">
		</script>
		<script type="text/javascript">
		
		function checkName(_this){
			var inputValue = _this.value.trim().toString();
			var firstCode = inputValue.charAt(0);
			if(firstCode >="0" && firstCode <="9"){
				if(inputValue.length < 6 && inputValue.length == 4){
					var result = "00"+inputValue;
				}else if(inputValue.length <6){
					var result="";
					for(var i=0;i<6-inputValue.length;i++){
						result +="0";
					}
					result +=inputValue;
				}
			}
			var _name=$('#user-login-form input[name="account"]').val(result);
		}
	
		function createWin(){
		    if (!this.sw) {
                    this.initSw();
                }
            this.toggleSw();
		}
		
        function toggleSw(){
            if (this.sw.isVisible()) {
                this.sw.hide();
            }else {
                this.sw.show();
                this.setSwPosition();
            }
        }
        /*Init Search Window*/
        function initSw(){
            var o = this;
            o.sw = new Ext.Window({
                draggable: false,
                resizable: false,
                width: 300,
                autoHeight: true,
                closable: false,
                buttonAlign: 'center',
                frame: true,
                items: {
                    xtype: 'form',
                    border: false,
                    bodyStyle: 'padding-top:5px',
                    labelAlign: 'right',
                    labelWidth: 80,
                    defaults: {
                        xtype: 'textfield',
                        anchor: '95%'
                    },
                    items: [{
                        fieldLabel: '您的姓名',
                        name: 'patientName' 
                    }, {
                       fieldLabel: '您的身份证号',
                        name: 'idNo'
                    }]
                },
                buttons: [{
                    text: '找回用户名和密码',
                    handler: function(){
                        var values = o.sw.items.get(0).getForm().getValues();
                        if(values.patientName==""){
                           alert("请输入您的姓名");
		                   return;
                         }
                        if(values.idNo==""){
                           alert("请输入您的身份证号");
		                   return;
                         }
                         Ext.Ajax.request({
        					url:App.App_Info.BasePath+'/module/biomedical/member.do',
        					params:{
        						method:'findUserNameAndPwd',
        						idNo:values.idNo,
        						patientName:values.patientName
        					},
        					success:function(_response){
        						if(Ext.decode(_response.responseText).success){
        						    var account = Ext.decode(_response.responseText).data.split(",")[0];
        						    var password = Ext.decode(_response.responseText).data.split(",")[1];
        							alert('您的用户名是:'+account+'  密码是:'+password);
        							
        						}else{
        							alert('您输入的验证信息有误或您还不是会员');
        						}
        					}
        				});
                        o.sw.hide();
                       $('input[name="patientName"]').val("");
                       $('input[name="idNo"]').val("");
                    }
                }, {
                    text: '取消',
                    handler: function(){
                        o.sw.hide();
                        $('input[name="patientName"]').val("");
                        $('input[name="idNo"]').val("");
                    }
                }]
            });
        }
        /*ReSet Window's Position*/
         function setSwPosition(){
            var el = this.el.dom;
            var pos = getElementPos(el);
            var oPos = [pos.x, pos.y];
            var oSize = {
                width: el.offsetWidth,
                height: el.offsetHeight
            };
            var bodySize = Ext.getBody().getSize();
            var sSize = this.sw.getSize();
            var x = oPos[0];
            var y = oPos[1] + oSize.height;
            if ((x + sSize.width) > bodySize.width) {
                x = x - sSize.width + oSize.width;
            }
            if ((y + sSize.height) > bodySize.height) {
                y = y - sSize.height - oSize.height;
            }
            this.sw.setPosition(x, y);
        }
    
		
		</script>
			
		<style type="text/css">
body,form,p,div {
	margin: 0;
	font-size: 12px;
}

#main {
	margin:0 auto;
	position:relative;
	background: url(resources/login_bg.jpg) no-repeat;
	width: 1003px;
	height: 580px;
	overflow: hidden;
}

#member-login-form {
	position: absolute;
	overflow: hidden;
	left: 580px;
	top: 305px;
	height: 80px;
}

input.text {
	width: 120px;
	border: solid 1px #7b869a;
	background: #f1f9ff;
}

input.button {
	BORDER-RIGHT: #000000 1px solid;
	PADDING-RIGHT: 2px;
	BORDER-TOP: #2c59aa 1px solid;
	PADDING-LEFT: 2px;
	FONT-SIZE: 12px;
	FILTER: progid : DXImageTransform . Microsoft .
		Gradient(GradientType = 0, StartColorStr = #ffffff, EndColorStr =
		#C3DAF5);
	BORDER-LEFT: #2c59aa 1px solid;
	CURSOR: hand;
	COLOR: #00368f;
	PADDING-TOP: 2px;
	BORDER-BOTTOM: #2c59aa 1px solid;
}

#user-login-form {
	position: absolute;
	overflow: hidden;
	left: 580px;
	top: 425px;
	height: 60px;
}

a:link { text-decoration: none;color: blue}
a:active { text-decoration:blink}
a:hover { text-decoration:underline;color: red} 
a:visited { text-decoration: none;color: green}

</style>
		<script type="text/javascript">
Ext.onReady(function(){
	
});
function UserLogin(){
	var _name=$('#user-login-form input[name="account"]').val().trim();
	var _password=$('#user-login-form input[name="password"]').val().trim();
	if(_name.length==0){
		alert('请输入账号。');
		return;
	}
	if(_password.length==0){
		alert('请输入密码。');
		return;
	}
	if(_password.length<6){
		alert('密码长度不能少于6位，请先修改密码然后再登录！');
		return;
	}
	$.post(
		App.App_Info.BasePath+'/user.do',
		{
			method:'login',
			account:_name,
			password:_password
		},function(data){
			if(data.success){
				window.open(App.App_Info.BasePath+'/mainface/main.html');
			}else{
				alert(data.msg);
			}
		},'json');
}

function openChangePassword(){
	var _name=$('#user-login-form input[name="account"]').val().trim();
	if(_name.length ==0){
		alert('请先输入账号，再修改密码。');
		return;
	}
	window.open(App.App_Info.BasePath+'/module/User/WaiChangePassword.html?userId='+_name);
}

</script>
	</head>
	<body>
		<div id="main">
			<div id="member-login-form">
				<form
					action="<%=request.getContextPath()%>/module/biomedical/member.do?method=login"
					method="POST">
					<table border=0 cellpadding=0 cellspacing=0>
						<tr>
							<td height="30" width="55" align="right">
								用户名：
							</td>
							<td width="130">
								<input type="text" class="text" name="account" />
							</td>
							
							<td>
								<!-- 
								<input type="button" value="注册" class="button" />
								 -->
							</td>
						</tr>
						<tr>
							<td height="30" align="right">
								密&nbsp;&nbsp;&nbsp;&nbsp;码：
							</td>
							<td>
								<input type="password" class="text" name="password" />
							</td>
							<td>
								<input type="submit" value="登录" class="button" />
							</td>
						</tr>
						<tr><td height="15"></td><td valign="bottom"><a href="javascript:createWin()" style="cursor:hand;">忘记用户名或密码？</a></td></tr>
					</table>
				</form>
			</div>
			<div id="user-login-form" style="top: 425px; height: 73px;">
				<form action="<%=request.getContextPath()%>/module/biomedical/member.do?method=login"
					method="POST">
					<table border=0 cellpadding=0 cellspacing=0 style="top: 426px;">
						<tr>
							<td height="30" width="55" align="right">
								用户名：
							</td>
							<td width="130">
								<input type="text" class="text"  name="account"  onblur="checkName(this)"/>
							</td>
							<td>
								<!-- 
								<input type="button" value="注册" class="button" />
								 -->
							</td>
						</tr>
						<tr>
							<td height="30" align="right">
								密&nbsp;&nbsp;&nbsp;&nbsp;码：
							</td>
							<td>
								<input type="password" class="text" 
									name="password" />
							</td>
							<td>
								<input type="button" value="登录" class="button"
									onclick="UserLogin()" />
							</td>
						</tr>
						<tr>
							<td height="15"></td><td valign="top"><a href="javascript:openChangePassword()" style="cursor:hand;">修改密码(最小6位长度)</a></td>
						</tr>
					</table>
				</form>
			</div>

			<div
				style="position: absolute; overflow: hidden; left: 214px; top: 305px; height: 172px; width: 322px;"
				id="noticeList">
				<script type="text/javascript">
       		Ext.onReady(function(){
       			Ext.Ajax.request({
       				url:App.App_Info.BasePath+'/biomedical.do?method=getSixNoticeList',
	       			params:{
	       				typeName:'生物医学信息中心家园活动',
	       				limit:6
	       			},
	       			success:function(response){
	       				var list = Ext.util.JSON.decode(response.responseText);
	       				var resultData="<table  border='0'>";
	       				for(var i=0,size=list.length;i<size;i++)
	       				{
	       					resultData+="<tr>";
	       					switch(i%2){
	       						case 0:
	       							resultData+="<td style='height:38px'>";
	       						    break;
	       						case 1:
	       							resultData+="<td style='height:16px'>";
	       						    break;
	       					}
	       					var titleLength = list[i].noticeNam.length;
	       					var noticeTitle = list[i].noticeNam;
	       					if(titleLength > 23)
	       					{
	       						noticeTitle = list[i].noticeNam.substring(0,22)+"......";
	       					}
	       					resultData+="<a target='_blank' href='../../SystemAdmin/Notice/customerMessageShow.html?id="+list[i].id+"'><font size='2'>"+noticeTitle+"</font></a></td></tr>";
	       				}
	       				
	       				document.getElementById('noticeList').innerHTML=resultData+"</table>";
	       			}
       			});
       		});
       </script>
			</div>
			<!--  
			<div style='position: absolute; left: 40px; top: 300px; width: 121px'>
				<b> <font color="#268A47"> <a href="#"
						onclick="javascript:window.open('intro/about_kljy.jsp','关于康乐家园','height=1000, width=1000')"> <font
							color="#008000">关于康乐家园</font>
					</a>
				</font>
				</b>
			</div>
			-->

			<div
				style='position: absolute; left: 870px; top: 426px; width: 101px'>
				<a href="#"
					onclick="javascript:window.open('intro/my_cureplan.htm#ZXYS','咨询我的医生','height=1000, width=1000')"> <font
					size="2" color="#5986AF"><b>咨询我的医生</b>
				</font>
				</a>
			</div>


			<div
				style='position: absolute; left: 871px; top: 391px; width: 101px'>
				<a href="#"
					onclick="javascript:window.open('intro/my_cureplan.htm#SFTZ','我的随访通知','height=1000, width=1000')"> <font
					size="2" color="#5986AF"><b>我的随访通知</b>
				</font>
				</a>
			</div>


			<div
				style='position: absolute; left: 872px; top: 356px; width: 101px'>
				<a href="#"
					onclick="javascript:window.open('intro/my_cureplan.htm#JKRJ','我的健康日记','height=1000, width=1000')"> <font
					size="2" color="#5986AF"><b>我的健康日记</b>
				</font>
				</a>
			</div>


			<div
				style='position: absolute; left: 872px; top: 323px; width: 101px'>
				<a href="#"
					onclick="javascript:window.open('intro/my_cureplan.htm#JCJH','我的检查计划','height=1000, width=1000')"> <font
					size="2" color="#5986AF"><b>我的检查计划</b>
				</font>
				</a>
			</div>


			<div
				style='position: absolute; left: 871px; top: 287px; width: 101px'>
				<a href="#"
					onclick="javascript:window.open('intro/my_cureplan.htm#BJFA','我的保健方案','height=1000, width=1000')"> <font
					size="2" color="#5986AF"><b>我的保健方案</b>
				</font>
				</a>
			</div>


			<div
				style='position: absolute; left: 872px; top: 252px; width: 101px'>
				<a href="#"
					onclick="javascript:window.open('intro/my_cureplan.htm#ZLFA','我的治疗方案','height=1000, width=1000')"><font
					size="2" color="#5986AF"><b>我的治疗方案</b>
				</font>
				</a>
			</div>


			<div
				style='position: absolute; left: 874px; top: 218px; width: 101px'>
				<a href="#" onclick="javascript:window.open('intro/my_archive.htm','我的健康档案','height=1000, width=1000')">
					<font size="2" color="#5986AF"><b>我的健康档案</b>
				</font>
				</a>
			</div>
<!-- 
			<div style='position: absolute; left: 38px; top: 340px; width: 121px'>
				<font color="#FFFFFF" style="font-size: 11pt"><b> <a
						href="#" onclick="javascript:window.open('intro/join_kljy.jsp','加入康乐家园','height=1000, width=1000')">
							<span><font color="#FFFFFF">加入康乐家园</font>
						</span>
					</a>
				</b>
				</font>
			</div>

			<div style='position: absolute; left: 38px; top: 372px; width: 121px'>
				<b> <font color="#FFFFFF" style="font-size: 11pt"> <a
						href="#" onclick="javascript:window.open('intro/serv_guide.jsp','会员服务指南','height=1000, width=1000')">
							<span style="font-size: 11pt"><font color="#FFFFFF">会员服务指南</font>
						</span>
					</a>
				</font>
			</div>

			<div style='position: absolute; left: 38px; top: 402px; width: 121px'>
				<font color="#FFFFFF" style="font-size: 11pt"> <a href="#"
					onclick="javascript:window.open('intro/speciality.jsp','佑安肝病专长','height=1000, width=1000')"> <span
						style="font-size: 11pt"><font color="#FFFFFF">佑安肝病专长</font>
					</span>
				</a>
				</font>
			</div>

			<div style='position: absolute; left: 38px; top: 433px; width: 121px'>
				<a href="http://www.bjyah.com/jyzn_menzhen.asp" target="_blank"><font
					color="#FFFFFF"> <span style="font-size: 11pt"><b>佑安专家荟萃</b>
					</span>
				</font>
				</a>
			</div>


			<div style='position: absolute; left: 38px; top: 463px; width: 121px'>
				<a href="http://www.bjyah.com/mzb.asp" target="_blank"><font
					color="#FFFFFF"> <span style="font-size: 11pt"><b>肝病门诊时间</b>
					</span>
				</font>
				</a>
			</div>


			<div style='position: absolute; left: 38px; top: 496px; width: 121px'>
				<a href="http://www.bjyah.com/jbbk.asp" target="_blank"><font
					color="#FFFFFF"> <span style="font-size: 11pt"><b>健康教育园地</b>
					</span>
				</font>
				</a>
			</div>
			-->
		</div>
	</body>
</html>