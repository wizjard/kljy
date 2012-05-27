var url = {};
url.baseUrl = App.App_Info.BasePath + '/GradingDiagAction.do?method=';
url.getChildren = url.baseUrl + 'getChildren';
url.save = url.baseUrl + 'saveOrUpdateGradingDiag';
url.get = url.baseUrl + 'findGradingDiagById';
url.getFind = url.baseUrl + 'findGradingDiagByCaseIdmenzhen';
var caseId=App.util.getHtmlParameters('case');
var gdid=App.util.getHtmlParameters('id');
Ext.ns('GradingDiag.Register.Tree');
GradingDiag.Register.Tree = Ext.extend(Ext.tree.TreePanel, {
    initComponent: function(){
        var tree = this;
        var loader = new Ext.tree.TreeLoader({
            dataUrl: url.getChildren,
            baseAttrs: {
                uiProvider: Ext.ux.TreeCheckNodeUI
            }
        });
        loader.on('beforeload', function(loader, node){
            loader.baseParams.code = node.id;
        });
        var root = new Ext.tree.AsyncTreeNode({
            id: 'root',
            text: '肝病十二级分类诊断表'
        });
        Ext.apply(this, {
            root: root,
            title: '肝病十二级分类诊断表',
            rootVisible: false,
            autoScroll: true,
            collapsible:true,
            useArrows: false,
            iconCls:'icon-none',
            onlyLeafCheckable: true,
            containerScroll: true,
            singleClickExpand : false,
            split:true,
            loader: loader,
            tbar: [{
                text: '添加',
                iconCls: 'application_form_add',
                handler: tree.add.createDelegate(tree)
            }, '->', {
                text: '取消选择',
                iconCls: 'application_form_delete',
                handler: tree.cancelChecked.createDelegate(tree)
            }]
        });  
        //右键事件
		var contextmenu = new Ext.menu.Menu({
            items: [{
                text: '诊断标准',
                handler: tree.viewDocs.createDelegate(tree)
            }]
        });
		tree.on("contextmenu", function(node, e){
            e.preventDefault();
            node.select();
            contextmenu.showAt(e.getXY());
        });
		//注册check事件
		tree.on("check",checkAction); 
        GradingDiag.Register.Tree.superclass.initComponent.call(this, arguments);
    },
    viewDocs:function(){
		var node=this.getSelectionModel().getSelectedNode();
		var title=node.text;
		var txt=node.attributes.data.standard;
		window.showModalDialog(
			'docs/dlg.html',
			{title:title,data:txt},
			'dialogWidth:720px;dialogHeight:530px;status:no;scroll:no;resizable:yes;center:yes'
		);
	},
    getRootLevel: function(node){
        var tempNode = node;
        var level = null;
        while (true) {
            tempNode = tempNode.parentNode;
            if (tempNode.parentNode.id == 'root') {
                level = tempNode.text.substr(1, tempNode.text.indexOf('级') - 1);
                break;
            }
        }
        return parseInt(level);
    },
    validChecked: function(){
        var tree = this;
        //验证级别
        var levels = [];
        Ext.each(this.getChecked(), function(node, i){
            var level = tree.getRootLevel(node);
            if (levels.indexOf(level) == -1) 
                levels.push(level);
        });
        if (levels.length > 1) {
            alert('最多只能选择一级诊断。');
            return false;
        }
        return true;
    },
    prepareNode: function(){
        var tree = this;
        var rst = {};
        Ext.each(this.getChecked(), function(node){
            var level = tree.getRootLevel(node);
            if (!rst['n' + level]) 
                rst['n' + level] = [];
            rst['n' + level].push(node);
        });
        return rst;
    },
    add: function(){
        if (!this.validChecked()) 
            return;
        addItem(this.prepareNode());
        //撤销选择
        Ext.each(this.getChecked(), function(node){
            node.getUI().toggleCheck(false);
        });
        //计算编码
        //calCode();
       
    },
    cancelChecked: function(){
        Ext.each(this.getChecked(), function(node){
            node.getUI().toggleCheck(false);
        });
    }
});

var div = null;
Ext.onReady(function(){
    new Ext.Viewport({
        layout: 'border',
        items: [ new GradingDiag.Register.Tree({
            region: 'west',
            width: 200,
            collapsible: true,
            margins: '0 5 5 5',
            id: 'tree'
        }),{
            region: 'center',
            layout: 'border',
            border: false,
            items: [{
                region: 'center',
                margins: '0 5 5 0',
                title: '肝病十二级分类诊断系统结果',
                autoScroll: true,
                listeners: {
                    'render': tableInit
                },
                tbar: [{
                    text: '增加缩进',
                    iconCls: 'arrow_right',
                    handler: function(){
                        $('.ul li.select').each(function(){
                            var text_indent = parseInt($(this).css('text-indent').replace('px', '')) + 24;
                            $(this).css('text-indent', text_indent + 'px');
                        });
                    }
                }, '-', {
                    text: '减少缩进',
                    iconCls: 'arrow_left',
                    handler: function(){
                        $('.ul li.select').each(function(){
                            var text_indent = parseInt($(this).css('text-indent').replace('px', '')) - 24;
                            if (text_indent < 0) 
                                text_indent = 0;
                            $(this).css('text-indent', text_indent + 'px');
                        });
                    }
                }, '-', {
                    text: '删除',
                    iconCls: 'application_form_delete',
                    handler: function(){
                    
                    //加入签名校验 （苏勇）
					if($('#doc1').val()!=""){
					        alert('请撤销"住院医师"签名后再删除！');
					        return ;
					    }
					    if($('#doc2').val()!=""){
					        alert('请撤销"主治医师"签名后再删除！');
					        return ;
					    }
					    if($('#doc3').val()!=""){
					        alert('请撤销"主任/副主任医师"签名后再删除！');
					        return ;
					    }
                    
                    
                        $('.ul li.select').remove();
                                                 diagGrounp = "";
                        //计算编码
                        //calCode();
                    }
                }, '-', {
                    text: '保存',
                    iconCls: 'page_save',
                    handler: saveData
                }, '-', {
                    text: '打印',
                    iconCls: 'printer',
                    handler: print
                }]
            }
            /*, {
                region: 'south',
                title: '诊断编码',
                margins: '0 5 5 0',
                height: 50,
                html: '<div id="code"></div><iframe id="print" src="print.jsp" width=0 height=0></iframe>'
            }*/
            
            ]
        }]
    });
});
function tableInit(){
    var $body = $(this.body.dom);
    $('<iframe id="print" src="printmenzhen.jsp" width=0 height=0></iframe><input type="hidden" id="id" value=""/>').appendTo($body);
    $('<input type="hidden" id="cid" value=""/>').appendTo($body);
    $('<input type="hidden" id="fiag" value="Y"/>').appendTo($body);
    
    var $table = $('<table class="table" border=0 cellspacing=1 cellpadding=0></table>');
    var $tr = $('<tr><td class="label" width="140">评价级别：</td><td class="title" width="600">&nbsp;&nbsp;&nbsp;&nbsp;评价时间：&nbsp;&nbsp;&nbsp;<input id="date" type="text" readonly style="width:120px"/><img onclick="WdatePicker({el:$(\'#date\').get(0),dateFmt:\'yyyy-MM-dd HH:mm\'});" class="hand" src="' + App.App_Info.BasePath + '/PUBLIC/images/calendar.bmp" />&nbsp;&nbsp;&nbsp;&nbsp;评价阶段：门诊&nbsp;&nbsp;&nbsp;<input name="inOutTimesDate" type="text" class="text" readOnly="readOnly" style="width:150px"/></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">一级：</td><td class="padd"><ul id="diag1" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
	$tr = $('<tr><td class="label">二级：</td><td class="padd"><ul id="diag2" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
	$tr = $('<tr><td class="label">三级：</td><td class="padd"><ul id="diag3" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
	$tr = $('<tr><td class="label">四级：</td><td class="padd"><ul id="diag4" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">五级：</td><td class="padd"><ul id="diag5" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">六级：</td><td class="padd"><ul id="diag6" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">七级：</td><td class="padd"><ul id="diag7" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">八级：</td><td class="padd"><ul id="diag8" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">九级：</td><td class="padd"><ul id="diag9" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">十级：</td><td class="padd"><ul id="diag10" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">十一级：</td><td class="padd"><ul id="diag11" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">十二级：</td><td class="padd"><ul id="diag12" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">住院医师：</td><td class="sign">&nbsp;<input id="doc1" type="text" readonly/>&nbsp;<a href="###" onclick="sign(this)">签字</a></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">主治医师：</td><td class="sign">&nbsp;<input id="doc2" type="text" readonly/>&nbsp;<a href="###" onclick="sign(this)">签字</a></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">主任/副主任医师：</td><td class="sign">&nbsp;<input id="doc3" type="text" readonly/>&nbsp;<a href="###" onclick="sign(this)">签字</a></td></tr>');
    $tr.appendTo($table);
    $table.appendTo($body);
    //医生字典选择
	$table.find('#doc1,#doc2,#doc3').each(function(){
		FormUtil.toDictSelect({
			url:App.App_Info.BasePath+'/PUBLIC/Modaldialog/MyDoctorSelect.html',
			//hiddenEl:$('input[name="'+$(this).attr('name').replace('doc','')+'"]'),
			showEl:$('#'+$(this).attr('id'))
		});
	});
	
    //页面赋值
    setData();
	//初始化病人信息
	//loadPatSimInfo();
	//获取门诊时间
	getoutDate();
}
//疾病分组
var diagGrounp = "";

function addItem(item){


if($('#doc1').val()!=""){
		
			var name = top.USER.name;
	        if ($('#doc1').val()== name) {
	        	return;
	        }else{
	            return;
	        }
			
    }else{
	 	//处理疾病分组
		 var nLivel1 = document.getElementById("diag1").innerHTML;
	
	    var processNode = function(root, pcode, node){
	        if (root.find('li[name="' + node.id + '"]').size() > 0) 
	            return;
	        var con = root;
	        var text_indent = 0;
	        if (pcode != null) {
	            con = root.find('li[pcode="' + pcode + '"]');
	            if (con.size() > 0) {
	                con = con.eq(con.size() - 1);
	                text_indent = parseInt(con.css('text-indent').replace('px', ''));
	            }
	            else {
	                con = root.find('li[name="' + pcode + '"]');
	                text_indent = parseInt(con.css('text-indent').replace('px', '')) + 24;
	            }
	        }
	        var text = node.attributes.data.printText;
	        if (text.length == 0) 
	            text = node.text;
	                    //疾病分组处理,默认读取第一级即医生书写的第一行记录
	       	if(nLivel1.length ==0){
	        	diagGrounp +=(" "+text);
	        }
	        root.append('<li onclick="liClick(this)" style="text-indent:' + text_indent + 'px" name="' + node.id + '" leaf="' + node.isLeaf() + '">' + text + '</li>');
	    };
	    var preProcessNode = function($ul, node){
	        var nodeId = node.id;
	        var multiLine = node.attributes.data.multiLine;
	        if (multiLine.length > 0) 
	            nodeId = multiLine + '-' + nodeId;
	        var pcode = null;
	        $.each(nodeId.split('-'), function(i){
	            var node = tree.getNodeById(this);
	            processNode($ul, pcode, node);
	            pcode = this;
	        });
	    }
	    var tree = Ext.getCmp('tree');
	    $.each(item, function(key){
	        var $ul = $('#diag' + key.replace('n', ''));
	        $.each(this, function(){
	            preProcessNode($ul, this);
	        });
	    });	
    }
}

function liClick(_this){
    if (!window.event.ctrlKey) {
        $('.ul li').removeClass('select');
        
    }
    $(_this).addClass('select');
}

function calCode(){
    var code = [];
    $('.ul').each(function(){
        var temp = [];
        $(this).find('li[leaf="true"]').each(function(){
            temp.push($(this).attr('name'));
        });
        if (temp.length > 0) 
            code.push(temp.join('/'));
    });
    $('#code').html(code.join('-'));
}

function GradingDiag(){
    this.id = null;
    this.cid = null;
    this.date = null;
    //this.code = '';
    this.doc1 = '';
    this.doc2 = '';
    this.doc3 = '';
    this.diag1 = '';
	this.diag2 = '';
	this.diag3 = '';
	this.diag4 = '';
    this.diag5 = '';
    this.diag6 = '';
    this.diag7 = '';
    this.diag8 = '';
    this.diag9 = '';
    this.diag10 = '';
    this.diag11 = '';
    this.diag12 = '';
    this.fiag = '';
    
}

function saveData(){
    var gd = new GradingDiag();
    
    for (var key in gd) {
        var $o = $('#' + key);
        if ($o.get(0).tagName.toLowerCase() == 'input') 
            gd[key] = $o.val();
        else 
            gd[key] = $o.html();
    }
    
     if($('#doc1').val()==""){
        alert('请在住院医师处签名后再保存！！');
        return ;
    }
     gd['diagnose']=$('#diagnose').val();
     gd['diagGrounp']=diagGrounp;
     gd['id']=gdid;
    // alert(caseId+"*************");
     gd['cid']=caseId;
    // alert(gd['cid']);
	 	if($('#date').val()==""){
        	alert('请填写评价时间！！');
        	return ;
	    }
	    Ext.Ajax.request({
	        url: url.save,
	        params: {
	            data: Ext.encode(gd)
	        },
	        success: function(response){
	            var res = Ext.decode(response.responseText);
	            if (res.success) {
	                $('#id').val(res.data);
	                alert('保存成功。');
	//                if(!App.util.getHtmlParameters('id'))
	                parent.refreshNode(res.data);
	            }
	            else {
	                alert('保存失败。');
	            }
	        }
	    });
}

function setData(){
    var id = App.util.getHtmlParameters('id');
    var caseId=App.util.getHtmlParameters('case');
    $('#cid').val(caseId);
    if(!id){
    	var caseId=App.util.getHtmlParameters('case');
    	$('#cid').val(caseId);
    }
    else{
	    Ext.Ajax.request({
	        url: url.getFind,
	        params: {
	            id: id
	        },
	        success: function(response){
	            var res = Ext.decode(response.responseText);
	            if (res.success) {
	                var data = Ext.decode(res.data);
	                if (!data) 
	                    return;
	                    //十二分级诊断系统疾病分组
	                diagGrounp = data['diagGrounp'];
	                for (var key in data) {
	                    var $o = $('#' + key);
	                    if($o.size()==0)
	                    	continue;
	                    if(key=='diagnose'){
	                		$o.val(data[key] || '');
	                		continue ;
	                	}
	                    if ($o.get(0).tagName.toLowerCase() == 'input') 
	                        $o.val(data[key] || '');
	                    else 
	                        $o.html(data[key] || '');
	                }
	            }
	            else {
	                alert('从服务器获取数据失败。');
	            }
	        }
	    });
    }
}

function getoutDate(){
		$.post(
			App.App_Info.BasePath+'/OutOrMergencyPatientAction.do',
			{
				method:'findById',
				id:caseId
			},
			function(data){
				if(data.success){
					var json=JSON.parse(data.data);
					if(json != null){
					  $('input[name="inOutTimesDate"]').val(json.outTime);
					}else{
					  
					  return;
					}
				}
			},'json'
		);
	}
function print(){
if($('#date').val()==""){
        alert('请填写评价时间！！');
        return ;
    }
    if($('#diagnose').val()==""){
    	 alert('请填写评价阶段！！');
    	return;
    }
    if($('#doc1').val()==""){
        alert('请签名在住院医师处签名！！');
        return ;
    }
    var data = {
        cid: $('#cid').val(),
        date: $('#date').val(),
        doc1: $('#doc1').val(),
        doc2: $('#doc2').val(),
        doc3: $('#doc3').val()
    };
    var fn = function(root){
        var array = [];
        root.find('li').each(function(){
            var text_indent = parseInt($(this).css('text-indent').replace('px', '')) / 24;
            var space = '';
            for (var i = 0; i < text_indent; i++) {
                space += '    ';
            }
            array.push(space + $(this).text());
        });
        if (array.length > 0) 
            return array.join('@');
        else 
            return '';
    }
    data.diag1 = fn($('#diag1'));
	data.diag2 = fn($('#diag2'));
	data.diag3 = fn($('#diag3'));
	data.diag4 = fn($('#diag4'));
	data.diag5 = fn($('#diag5'));
	data.diag6 = fn($('#diag6'));
	data.diag7 = fn($('#diag7'));
	data.diag8 = fn($('#diag8'));
	data.diag9 = fn($('#diag9'));
	data.diag10 = fn($('#diag10'));
	data.diag11 = fn($('#diag11'));
	data.diag12 = fn($('#diag12'));
    if(data.diag1==""||data.diag2==""||data.diag3==""||data.diag4==""||data.diag5==""||data.diag6==""||data.diag7==""||data.diag8==""||data.diag9==""||data.diag10==""||data.diag11==""||data.diag12==""){
         Ext.Msg.alert('提示信息','请将一到十二级信息填写完整再打印！');
        return;
    }
   
    $('#print').get(0).contentWindow.print(Ext.encode(data));
}

function sign(_this){
    var input = $(_this).parent().find('input');
    var val = input.val();
	if(!top.USER)
		return;
    var name = top.USER.name;
    if (!name || name.length == 0) 
        return;
    if (val.length == 0) {
        if (confirm('确定要签入您的名字 【 ' + name + ' 】？')) {
            input.val(name);
        }
    }
    else {
        if (name == val) {
            if (confirm('您已签字，是否要撤销签字？')) {
                input.val('');
            }
        }
        else {
            alert('其他大夫已经签字，不能再次操作。');
        }
    }
}
/**
function loadPatSimInfo(){
	var _div=Ext.get('PatSimInfo-DIV');
	if (!_div) {
		Ext.DomHelper.append(Ext.getBody(), {
			tag: 'div',
			id: 'PatSimInfo-DIV'
		});
		_div=Ext.get('PatSimInfo-DIV');
	}
	_div.load({
		url:App.App_Info.BasePath+'/module/Patient/PatSimpleInfo.jsp?id='+App.util.getHtmlParameters('PID'),
		scripts:true,
		text:'正在获取病人基本信息......'
	});
}
**/
function checkAction(node,checked){
//加入签名校验 （苏勇）
if($('#doc1').val()!=""){
		
			var name = top.USER.name;
	        if ($('#doc1').val()== name) {
	             alert('请撤销"住院医师"签名后再做更改！');
	        	return;
	        }else{
	            alert('其他大夫已经签字，不能再次操作。');
	            return;
	        }
			
    }else{
    /**  此处删除对 非住院医师的校验
    if($('#doc2').val()!=""){
        alert('请撤销"主治医师"签名后再做更改！');
        return ;
    }
    if($('#doc3').val()!=""){
        alert('请撤销"主任/副主任医师"签名后再做更改！');
        return ;
    }
    
**/
   
	var c=node.attributes.data.checkAction;
	if(c==1){//第五级MELD评分标准计算
		Check.Action.ValueNode(node,checked);
	}else if(c==2){//第十二级伴随疾病中的其它某某疾病选中之后弹出输入框
		Check.Action12.ValueNode(node,checked);
	}
}
}
Ext.ns('Check.Action');
Check.Action={
	ValueNode:function(node,checked){
		if(checked){
			if (!node.attributes.pText) {
				node.attributes.pText = node.attributes.data.printText;
				node.attributes.sText = node.text;
			}
			Ext.MessageBox.prompt('提示','请在下框内输入数据：',function(btn,txt){
				if(btn=='ok'&&txt.length>0){
					var temp=node.attributes.pText;
					temp=temp.replace('{1}',txt);
					if(temp.indexOf('@')!=-1){
						var str=temp.substring(temp.indexOf('@')+1,temp.lastIndexOf('@'));
						var offsetArray=Ext.decode(str);
						if(!isNaN(txt)){
							for(var i=0,len=offsetArray.length;i<len;i++){
								var flag=true;
								var flagStr=[];
								
								var tempArray=offsetArray[i];
								var offsetLeft=tempArray[0].substring(0,1);
								var offset=tempArray[0].substring(1,tempArray[0].length-1);
								var offsetRight=tempArray[0].substring(tempArray[0].length-1,tempArray[0].length);
								//左侧范围值
								{
									var valueLeft=offset.split(',')[0];
									if(valueLeft!='-∞'){
										if(offsetLeft=='['){
											flagStr.push(txt+'>='+valueLeft);
										}else if(offsetLeft=='('){
											flagStr.push(txt+'>'+valueLeft);
										}
									}
								}
								//右侧范围值
								{
									var valueRight=offset.split(',')[1];
									if(valueRight!='+∞'){
										if(offsetRight==']'){
											flagStr.push(txt+'<='+valueRight);
										}else if(offsetRight==')'){
											flagStr.push(txt+'<'+valueRight);
										}
									}
								}
								if(eval(flagStr.join('&&'))){
									temp=temp.replace('@'+str+'@',tempArray[1]);
									break;
								}
							}
						}
					}
					node.attributes.data.printText=temp;
					node.setText(txt);
				}else{
					node.getUI().toggleCheck(false);
				}
			});
		}else{
			node.setText(node.attributes.sText);
			node.attributes.data.printText=node.attributes.pText;
		}
	}
};

Ext.ns('Check.Action12');
Check.Action12={
	ValueNode:function(node,checked){
		if(checked){
			if (!node.attributes.pText) {
				node.attributes.pText = node.attributes.data.printText;
				node.attributes.sText = node.text;
			}
			Ext.MessageBox.prompt('提示','请在下框内输入其它疾病的名称：',function(btn,txt){
				if(btn=='ok'&&txt.length>0){
					var temp=node.attributes.pText;
					node.attributes.data.printText=temp;
					node.setText(txt);
					addItem(this.prepareNode());
				}else{
					node.getUI().toggleCheck(false);
				}
			});
		}else{
			node.setText(node.attributes.sText);
			node.attributes.data.printText=node.attributes.pText;
		}
	}
};

//function loadPatSimInfo(){
//	var _div=Ext.get('PatSimInfo-DIV');
//	if (!_div) {
//		Ext.DomHelper.append(Ext.getBody(), {
//			tag: 'div',
//			id: 'PatSimInfo-DIV'
//		});
//		_div=Ext.get('PatSimInfo-DIV');
//	}
//	_div.load({
//		url:App.App_Info.BasePath+'/module/Patient/PatSimpleInfo.jsp?timestamp='+new Date().getTime()+'&id='+PID,
//		scripts:true,
//		text:'正在获取病人基本信息......'
//	});
//}
