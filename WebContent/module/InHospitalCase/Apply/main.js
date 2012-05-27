var pid=App.util.getHtmlParameters('PID');
var kid=App.util.getHtmlParameters('KID');
var title=decodeURIComponent(App.util.getHtmlParameters('title'));
var entity=App.util.getHtmlParameters('entity');
var action=App.util.getHtmlParameters('action');
var thisId=null;
Ext.onReady(function(){
	new Ext.Viewport({
		layout:'fit',
		items:{
			title:title,
			id:'main',
			autoScroll:true,
			tbar:[
				'-',{
					text:'索引',id:'index-btn',menu:[]
				},'-','->','-',{
					text:'保存',id:'save-btn',handler:function(){
						document.getElementById('iframe').contentWindow.SaveData();
					}
				},'-',{
						text:'删除',id:'delete-btn',handler:function(){
						document.getElementById('iframe').contentWindow.deleteData();
					}
				},'-',{
					text:'打印',id:'print-btn',handler:function(){
						document.getElementById('iframe').contentWindow.print();
					}
				},'-',{
					text:'返回',handler:function(){
						//location.href='../CaseList.html?id='+pid;
						parent.backToHistory();
					}
				},'-'
			],
			html:'',
			listeners:{
				render:function(){
					var url=createIndex();
					this.body.dom.innerHTML=
					'<iframe id="iframe" src="'+url+'" width="100%" height="100%" frameborder="0" srcoll="true"></iframe>';
				}
			}
		}
	});
});


function createIndex(){
	var url='';
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/'+action+'.do',
		params:{
			method:'getCheckTime',
			kid:kid,
			pid:pid,
			entityName:entity
		},
		sync:true,
		success:function(_response){
			//alert("dsa dsa dsa dsa sda asd sd"+_response);
			var btn=Ext.getCmp('index-btn');
			btn.menu=new Ext.menu.Menu({items:[]});
			btn.menu.add(new Ext.menu.Item({text:'新增',id:'-1',handler:indexClick}));
			var res=Ext.decode(_response.responseText);
			if(res.success){
				var menus=Ext.decode(res.data);
				Ext.each(menus,function(_item){
					btn.menu.add(new Ext.menu.Item({text:_item.checkDate,id:_item.id+"",handler:indexClick}));
				});
				var panel=Ext.getCmp('main');
				var t=title;	
				if(btn.menu.items.length==1){
					t=title+'(新增)';
					url=entity+'.html?pid='+pid+'&kid='+kid+'&this=-1&timestamp='+new Date().getTime();
				}else{
					var lastMenu=btn.menu.items.get(btn.menu.items.length-1);
					t=title+'('+lastMenu.text+')';
					url=entity+'.html?pid='+pid+'&kid='+kid+'&this='+lastMenu.id+'&timestamp='+new Date().getTime();
				}
				panel.setTitle(t);
			}else{
				alert('初始化索引菜单失败。');
			}
		}
	});
	return url;
}
function setAuth(data){
	thisId=data.id;
	Ext.getCmp('main').setTitle(data.title);
	//changeVerifyStatus(data);
}

function indexClick(){
	thisId=this.id;
	if(this.id==-1){
		setAuth({
			title:title+'(新增)'
		});
	}else{
		setAuth({
			id:thisId,
			title:title+'('+this.text+')'
		});
	}
	var url=entity+'.html?pid='+pid+'&kid='+kid+'&this='+this.id+'&timestamp='+new Date().getTime();
	document.getElementById('iframe').src=url;
}
