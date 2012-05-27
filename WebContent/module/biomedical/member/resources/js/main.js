Ext.onReady(function(){
    new Ext.Viewport({
        layout: 'border',
        items: [new Member.Main.Top({
            margins: '0 0 3 0'
        }), new Member.Main.Left({
            margins: '0 0 0 3'
        }), new Member.Main.Center({
            id: 'center-panel',
			margins:'0 3 0 0'
        })]
    });
});
Ext.ns('Member.Main.Top');
Member.Main.Top = Ext.extend(Ext.Panel, {
    initComponent: function(){
        Ext.apply(this, {
            region: 'north',
			height:110
        });
        Member.Main.Top.superclass.initComponent.call(this, arguments);
    },
    afterRender: function(ct, position){
        Ext.DomHelper.append(this.body, {
            tag: 'h1',
            html: '<iframe src="../../../PUBLIC/CommonPage/waiTop.html" frameborder="0" width="100%" height="110" scroll="no"></iframe>',
            style: 'text-align:center;line-height:40px;font-size:16px'
        });
        Member.Main.Top.superclass.afterRender.call(this, arguments);
    }
});
Ext.ns('Member.Main.Left');
Member.Main.Left = Ext.extend(Ext.Panel, {
    initComponent: function(){
        Ext.apply(this, {
            region: 'west',
            title: '<div style="text-indent:0.2em">系统菜单</div>',
            split: true,
            width: 120,
            minSize: 100,
            maxSize: 400,
            collapsible: true,
            border: true,
            layout: 'accordion',
            layoutConfig: {
                animate: true
            },
            defaults: {
                autoScroll: true
            }
        });
        Member.Main.Left.superclass.initComponent.call(this, arguments);
    },
    afterRender: function(ct, position){
        var panel = this;
        Ext.Ajax.request({
            url: 'resources/menu.json',
            success: function(response){
                var tpl = new Ext.XTemplate('<tpl for=".">', '<div class="left-menu-item-con">', '<table width="100%" border=0 cellspacing=0 cellpadding=0>', '<tr>', '<td class="dropShadow">', '<img class="img-normal" src="' + App.App_Info.BasePath + '{icon}" ', 'onmouseover="this.className=\'img-over\'" ', 'onmouseout="this.className=\'img-normal\'" ', 'onclick="{action}"/>', '</td>', '</tr>', '</table>', '<p>{title}</p>', '</div></tpl>');
                Ext.each(Ext.decode(response.responseText), function(){
                    var _panel = new Ext.Panel({
                        title: '<div class="left-menu-item-title">' + this.title + '</div>',
                        border: false
                    });
                    panel.add(_panel);
                    panel.doLayout();
                    tpl.overwrite(_panel.body, this.children);
                });
            }
        });
        Member.Main.Left.superclass.afterRender.call(this, arguments);
    }
});
Ext.ns('Member.Main.Center');
Member.Main.Center = Ext.extend(Ext.TabPanel, {
    initComponent: function(){
        Ext.apply(this, {
            region: 'center',
            activeTab: 0,
            items: {
                title: '主页(Home)',
                html: '<iframe src="../../../module/SystemAdmin/Notice/customerMain.html" frameborder="0" width="100%" height="100%" scroll="auto"></iframe>'
            }
        });
        Member.Main.Center.superclass.initComponent.call(this, arguments);
    },
    afterRender: function(ct, position){
        Member.Main.Center.superclass.afterRender.call(this, arguments);
    }
});
function loadNewTab(_src, _title){
    var _tab = Ext.getCmp('center-panel');
    var _panel = Ext.getCmp(_src);
    if (!_panel) {
        _panel = new Ext.Panel({
            title: _title,
            id: _src,
            closable: true,
            html: '<iframe frameborder=0 scrolling=auto width=100% height=100% src="' +
            App.App_Info.BasePath +
            _src +
            '"></iframe>'
        });
        _tab.add(_panel);
    }
    _tab.setActiveTab(_panel);
}

/*
,{
	title:'我的随访通知',
	children:[
		{
			title:'我的随访通知',
			icon:'/PUBLIC/images/system_icon/myPatient.jpg',
			action:'loadNewTab(\'/module/biomedical/member/ctx/MySfNotice.jsp\',\'我的随访通知\')'
		}
	]
}

*/
