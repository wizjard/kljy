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
	background: url(resources/login_bg1.jpg) no-repeat;
	width: 1003px;
	height: 580px;
	overflow: hidden;
}

#member-login-form {
	position: absolute;
	overflow: hidden;
	left: 590px;
	top: 323px;
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
function MemberLogin(){
	var _name=$('#member-login-form input[name="account"]').val().trim();
	var _password=$('#member-login-form input[name="password"]').val().trim();
	if(_name.length==0){
		alert('请输入账号。');
		return;
	}
	if(_password.length==0){
		alert('请输入密码。');
		return;
	}
	$.post(
		App.App_Info.BasePath+'/module/biomedical/member.do',
		{
			method:'login',
			account:_name,
			password:_password
		},function(data){
			if(data.success){
				window.open(App.App_Info.BasePath+'/module/biomedical/member/main.html');
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
			<div id="member-login-form" style="top: 324px; height: 140px;">
				<form>
					<table border=0 cellpadding=0 cellspacing=0 style="top: 320px; height: 140px; left: 591px; width: 223px;">
						<tr>
							<td height="50" width="50" align="right">
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
								<input type="button" value="登录" class="button" onclick="MemberLogin()"/>
							</td>
						</tr>
						<tr>
						<td height="30"></td><td valign="top">
							<a href="javascript:createWin()" style="cursor:hand;">忘记用户名或密码？</a>
						</td>
						</tr>
					</table>
				</form>
			</div>
			<div
				style="position: absolute; overflow: hidden; left: 214px; top: 305px; height: 172px; width: 322px;"
				id="noticeList">
			</div>
		</div>
	</body>
</html>