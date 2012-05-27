var url = {};
url.baseUrl = App.App_Info.BasePath + '/GradingDiagAction.do?method=';
url.getChildren = url.baseUrl + 'getChildren';
url.save = url.baseUrl + 'saveOrUpdateGradingDiag';
url.get = url.baseUrl + 'findGradingDiagByCaseId';
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
            text: '肝病十二分级诊断编码表'
        });
        Ext.apply(this, {
            root: root,
            title: '肝病十二分级诊断编码表',
            rootVisible: false,
            autoScroll: true,
            useArrows: true,
            onlyLeafCheckable: true,
            containerScroll: true,
            loader: loader,
            tbar: [{
                text: '增加',
                iconCls: 'application_form_add',
                handler: tree.add.createDelegate(tree)
            }, '->', {
                text: '取消选择',
                iconCls: 'application_form_delete',
                handler: tree.cancelChecked.createDelegate(tree)
            }]
        });
        GradingDiag.Register.Tree.superclass.initComponent.call(this, arguments);
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
        if (levels.length == 0) {
            alert('未选择任何诊断。');
            return false;
        }
        if (levels.indexOf(1) != -1) {
            if (levels.indexOf(2) == -1) {
                alert('未选择第2级。');
                return false;
            }
            if (levels.indexOf(3) == -1) {
                alert('未选择第3级。');
                return false;
            }
            if (levels.indexOf(4) == -1) {
                alert('未选择第4级。');
                return false;
            }
            if (levels.length > 4) {
                alert('只能选择第1、2、3、4级。');
                return false;
            }
        }
        else 
            if (levels.indexOf(2) != -1) {
                if (levels.indexOf(1) == -1) {
                    alert('未选择第1级。');
                    return false;
                }
                if (levels.indexOf(3) == -1) {
                    alert('未选择第3级。');
                    return false;
                }
                if (levels.indexOf(4) == -1) {
                    alert('未选择第4级。');
                    return false;
                }
                if (levels.length > 4) {
                    alert('只能选择第1、2、3、4级。');
                    return false;
                }
            }
            else 
                if (levels.indexOf(3) != -1) {
                    if (levels.indexOf(1) == -1) {
                        alert('未选择第1级。');
                        return false;
                    }
                    if (levels.indexOf(2) == -1) {
                        alert('未选择第2级。');
                        return false;
                    }
                    if (levels.indexOf(4) == -1) {
                        alert('未选择第4级。');
                        return false;
                    }
                    if (levels.length > 4) {
                        alert('只能选择第1、2、3、4级。');
                        return false;
                    }
                }
                else 
                    if (levels.indexOf(4) != -1) {
                        if (levels.indexOf(1) == -1) {
                            alert('未选择第1级。');
                            return false;
                        }
                        if (levels.indexOf(2) == -1) {
                            alert('未选择第2级。');
                            return false;
                        }
                        if (levels.indexOf(3) == -1) {
                            alert('未选择第3级。');
                            return false;
                        }
                        if (levels.length > 4) {
                            alert('只能选择第1、2、3、4级。');
                            return false;
                        }
                    }
                    else {
                        if (levels.length > 1) {
                            alert('只能选择一级诊断。');
                            return false;
                        }
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
        calCode();
    },
    cancelChecked: function(){
        Ext.each(this.getChecked(), function(node){
            node.getUI().toggleCheck(false);
        });
    }
});
Ext.onReady(function(){
    new Ext.Viewport({
        layout: 'border',
        items: [{
            region: 'north',
            border: false,
            height: 23,
            html: '<div id="PatSimInfo-DIV"></div>'
        }, new GradingDiag.Register.Tree({
            region: 'west',
            width: 300,
            collapsible: true,
            margins: '0 5 5 5',
            id: 'tree'
        }), {
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
                tbar: [ //					{
                //		                text: '插入',
                //		                iconCls: 'application_link',
                //		                handler: function(){
                //							var select=$('.ul li.select');
                //							if(select.size()>1){
                //								alert('右侧只能选择一个项目进行插入。');
                //								return;
                //							}
                //							var nodes=Ext.getCmp('tree').getChecked();
                //							if(nodes.length>1){
                //								alert('左侧只能选择一个项目进行插入。');
                //								return;
                //							}
                //							var text_indent=parseInt(select.css('text-indent').replace('px', ''));
                //						}
                //		            },'-',
                {
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
                        $('.ul li.select').remove();
                        //计算编码
                        calCode();
                    }
                }, '->', {
                    text: '保存',
                    iconCls: 'page_save',
                    handler: saveData
                }, '-', {
                    text: '打印',
                    iconCls: 'printer',
                    handler: print
                }]
            }, {
                region: 'south',
                title: '诊断编码',
                margins: '0 5 5 0',
                height: 50,
                html: '<div id="code"></div><iframe id="print" src="print.jsp" width=0 height=0></iframe>'
            }]
        }]
    });
});
function tableInit(){
    var $body = $(this.body.dom);
    $('<input type="hidden" id="id" value=""/>').appendTo($body);
    $('<input type="hidden" id="cid" value=""/>').appendTo($body);
    var $table = $('<table class="table" border=0 cellspacing=1 cellpadding=0></table>');
    var $tr = $('<tr><td class="label">诊断时间：</td><td>&nbsp;<input id="date" type="text" readonly/><img onclick="WdatePicker({el:$(\'#date\').get(0),dateFmt:\'yyyy-MM-dd\'});" class="hand" src="' + App.App_Info.BasePath + '/PUBLIC/images/calendar.bmp" /></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">一级-四级：</td><td><div id="diag1"></div></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">五级：</td><td><ul id="diag5" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">六级：</td><td><ul id="diag6" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">七级：</td><td><ul id="diag7" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">八级：</td><td><ul id="diag8" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">九级：</td><td><ul id="diag9" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">十级：</td><td><ul id="diag10" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">十一级：</td><td><ul id="diag11" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">十二级：</td><td><ul id="diag12" class="ul"></ul></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">住院医师：</td><td class="sign">&nbsp;<input id="doc1" type="text" readonly/>&nbsp;<a href="###" onclick="sign(this)">签字</a></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">主治医师：</td><td class="sign">&nbsp;<input id="doc2" type="text" readonly/>&nbsp;<a href="###" onclick="sign(this)">签字</a></td></tr>');
    $tr.appendTo($table);
    $tr = $('<tr><td class="label">副主任/主任医师：</td><td class="sign">&nbsp;<input id="doc3" type="text" readonly/>&nbsp;<a href="###" onclick="sign(this)">签字</a></td></tr>');
    $tr.appendTo($table);
    $table.appendTo($body);
    //页面赋值
    setData();
	//初始化病人信息
	loadPatSimInfo();
}

function addItem(item){
    var processNode = function(root, pcode, node, text_in){
        if (root.find('li[name="' + node.id + '"]').size() > 0) 
            return;
        var con = root;
        var text_indent = text_in;
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
        root.append('<li onclick="liClick(this)" style="text-indent:' + text_indent + 'px" name="' + node.id + '" leaf="' + node.isLeaf() + '">' + text + '</li>');
        
    };
    var preProcessNode = function($ul, node, text_in){
        var nodeId = node.id;
        var multiLine = node.attributes.data.multiLine;
        if (multiLine.length > 0) 
            nodeId = multiLine + '-' + nodeId;
        var pcode = null;
        $.each(nodeId.split('-'), function(i){
            var node = tree.getNodeById(this);
            processNode($ul, pcode, node, text_in || 0);
            pcode = this;
        });
    }
    var tree = Ext.getCmp('tree');
    if (item.n1) {
        var $div = $('#diag1');
        var $ul = $('<ul name="diag1_ul" class="ul"></ul>');
        $ul.appendTo($div);
        $.each(item.n1, function(){
            preProcessNode($ul, this);
        });
        var text_indent = parseInt($div.find('li:last').css('text-indent').replace('px', ''));
        $ul = $('<ul name="diag2_ul" class="ul"></ul>');
        $ul.appendTo($div);
        $.each(item.n2, function(){
            preProcessNode($ul, this, text_indent);
        });
        $ul = $('<ul name="diag3_ul" class="ul"></ul>');
        $ul.appendTo($div);
        $.each(item.n3, function(){
            preProcessNode($ul, this, text_indent);
        });
        $ul = $('<ul name="diag4_ul" class="ul"></ul>');
        $ul.appendTo($div);
        $.each(item.n4, function(){
            preProcessNode($ul, this, text_indent);
        });
    }
    else {
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
    this.code = '';
    this.doc1 = '';
    this.doc2 = '';
    this.doc3 = '';
    this.diag1 = '';
    this.diag5 = '';
    this.diag6 = '';
    this.diag7 = '';
    this.diag8 = '';
    this.diag9 = '';
    this.diag10 = '';
    this.diag11 = '';
    this.diag12 = '';
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
            }
            else {
                alert('保存失败。');
            }
        }
    });
}

function setData(){
    var cid = App.util.getHtmlParameters('KID');
    $('#cid').val(cid);
    Ext.Ajax.request({
        url: url.get,
        params: {
            id: cid
        },
        success: function(response){
            var res = Ext.decode(response.responseText);
            if (res.success) {
                var data = Ext.decode(res.data);
                if (!data) 
                    return;
                for (var key in data) {
                    var $o = $('#' + key);
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

function print(){
    var data = {
        cid: $('#cid').val(),
        date: $('#date').val(),
        doc1: $('#doc1').val(),
        doc2: $('#doc1').val(),
        doc3: $('#doc1').val()
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
    data.diag5 = fn($('#diag5'));
    data.diag6 = fn($('#diag6'));
    data.diag7 = fn($('#diag7'));
    data.diag8 = fn($('#diag8'));
    data.diag9 = fn($('#diag9'));
    data.diag10 = fn($('#diag10'));
    data.diag11 = fn($('#diag11'));
    data.diag12 = fn($('#diag12'));
    
    $('#print').get(0).contentWindow.print(Ext.encode(data));
}

function sign(_this){
    var input = $(_this).parent().find('input');
    var val = input.val();
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
            alert('已经签字，不能再次操作。');
        }
    }
}
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