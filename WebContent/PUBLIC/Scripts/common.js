App={
	App_Info:{
		AppName:'康乐家园',
		BasePath:'/TCMP'
	},
	util:{
		getRootWin:function(){
			var win = window;   
		    while (win != win.parent){   
		        win = win.parent;   
		    }   
		    return win;
		},
		getHtmlParameters:function(val){ 
			var uri = window.location.search; 
			var re = new RegExp("" +val+ "=([^&?]*)", "ig"); 
			return ((uri.match(re))?(uri.match(re)[0].substr(val.length+1)):null); 
		},
		addNewMainTab:function(_src,_name,_params){
			this.getRootWin().loadNewTab(_src,_name,_params);
		},
		initGlobalAjax:function(){
			var _loading='<div id="loading-div" style="border:solid 1px #6593CF;padding:2px;background:#A7C9F5;width:160px;">'+
				'<div style="border:solid 1px #A3BAD9;background:#FFF;height:22px;width:154px;padding:2px;">'+
				'<img src="'+App.App_Info.BasePath+'/PUBLIC/images/loading_img.gif" style="float:left;width:20px;height:20px;"/>'+
				'<p style="margin:0;padding:0;font-size:12px;float:left;padding-left:.5em;line-height:22px;">'+
				'数据传输中，请稍候...</p></div></div>';
			var _loading_div=document.createElement('div');
			_loading_div.style.cssText="position:absolute;left:40%;top:40%;display:none";
			_loading_div.innerHTML=_loading;
			document.body.appendChild(_loading_div);
			if(typeof Ext!='undefined'){
				Ext.Ajax.on('requestcomplete',function(conn,response,options){
					Ext.get(_loading_div).hide();
					if(typeof response!='undefined'&&response.getResponseHeader&&response.getResponseHeader.sessionstatus == 'timeout'){
						alert('登录超时或未登录，请重新登录。');
						App.util.getRootWin().location.href=App.App_Info.BasePath+'/PUBLIC/CommonPage/timeout.html';
					}
				},this);
				Ext.Ajax.on('beforerequest',function(){
					Ext.get(_loading_div).show();
				},this)
			}else if(typeof $!='undefined'){
				$().ajaxComplete(function(event,request,settings){
					$(_loading_div).hide();
					if(typeof response!='undefined'&&response.getResponseHeader&&request.getResponseHeader('sessionstatus')=='timeout'){
						alert('登录超时或未登录，请重新登录。');
						App.util.getRootWin().location.href=App.App_Info.BasePath+'/PUBLIC/CommonPage/timeout.html';
					}
				}).ajaxStart(function(){
					$(_loading_div).show();
				});
			} 
		}
	}
}
String.prototype.replaceAll  = function(s1,s2){    
	return this.replace(new RegExp(s1,"gm"),s2);    
}
String.prototype.trim  = function(){    
	return this.replace(/(^\s*)|(\s*$)/g, "");   
}
Date.prototype.format=function(fmt) {         
    var o = {         
    "M+" : this.getMonth()+1, //月份         
    "d+" : this.getDate(), //日         
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
    "H+" : this.getHours(), //小时         
    "m+" : this.getMinutes(), //分         
    "s+" : this.getSeconds(), //秒         
    "q+" : Math.floor((this.getMonth()+3)/3), //季度         
    "S" : this.getMilliseconds() //毫秒         
    };         
    var week = {         
    "0" : "\u65e5",         
    "1" : "\u4e00",         
    "2" : "\u4e8c",         
    "3" : "\u4e09",         
    "4" : "\u56db",         
    "5" : "\u4e94",         
    "6" : "\u516d"        
    };         
    if(/(y+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
    }         
    if(/(E+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);         
    }         
    for(var k in o){         
        if(new RegExp("("+ k +")").test(fmt)){         
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
        }         
    }         
    return fmt;         
}
window.onload=function(){
	App.util.initGlobalAjax();
}
function getElementPos(el){
    var ua = navigator.userAgent.toLowerCase();
    var isOpera = (ua.indexOf('opera') != -1);
    var isIE = (ua.indexOf('msie') != -1 && !isOpera); // not opera spoof
    if (el.parentNode === null || el.style.display == 'none') {
        return false;
    }
    var parent = null;
    var pos = [];
    var box;
    if (el.getBoundingClientRect) //IE
    {
        box = el.getBoundingClientRect();
        var scrollTop = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
        var scrollLeft = Math.max(document.documentElement.scrollLeft, document.body.scrollLeft);
        return {
            x: box.left + scrollLeft,
            y: box.top + scrollTop
        };
    }
    else 
        if (document.getBoxObjectFor) // gecko    
        {
            box = document.getBoxObjectFor(el);
            var borderLeft = (el.style.borderLeftWidth) ? parseInt(el.style.borderLeftWidth) : 0;
            var borderTop = (el.style.borderTopWidth) ? parseInt(el.style.borderTopWidth) : 0;
            pos = [box.x - borderLeft, box.y - borderTop];
        }
        else // safari & opera    
        {
            pos = [el.offsetLeft, el.offsetTop];
            parent = el.offsetParent;
            if (parent != el) {
                while (parent) {
                    pos[0] += parent.offsetLeft;
                    pos[1] += parent.offsetTop;
                    parent = parent.offsetParent;
                }
            }
            if (ua.indexOf('opera') != -1 || (ua.indexOf('safari') != -1 && el.style.position == 'absolute')) {
                pos[0] -= document.body.offsetLeft;
                pos[1] -= document.body.offsetTop;
            }
        }
    if (el.parentNode) {
        parent = el.parentNode;
    }
    else {
        parent = null;
    }
    while (parent && parent.tagName != 'BODY' && parent.tagName != 'HTML') { // account for any scrolled ancestors
        pos[0] -= parent.scrollLeft;
        pos[1] -= parent.scrollTop;
        if (parent.parentNode) {
            parent = parent.parentNode;
        }
        else {
            parent = null;
        }
    }
    return {
        x: pos[0],
        y: pos[1]
    };
}