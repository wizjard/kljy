var tplType=[['0','治疗建议'],['1','生活指导'],['2','检查']];
var tplCourseUnit=['月','周','天'];
$(function(){
	initPage();
});
function initPage(){
	var _div=$('#content-div');
	$.post(
		App.App_Info.BasePath+'/admin/MemberGroupMngAction.do',
		{
			method:'MemberGroup_findAll'
		},
		function(data){
			if(data.success){
				var groups=JSON.parse(data.data);
				var $ul=$('<ul></ul>');
				$.each(groups,function(){
					var $li=$('<li treeId="'+this.id+'"><input type="checkbox" name="treeCheckbox"/><span>'+this.text+'</span></li>');
					if(this.columns.length>0){
						var $sub_div=$('<div class="sub-div"></div>');
						var th='<tr><th width="35">选择</th>';
						$.each(this.columns,function(){
							th+='<th dataIndex="'+this[0]+'">'+this[3]+'</th>';
						});
						th+='</tr>';
						var $table='<table class="datagrid">'+th+'</table>';
						$sub_div.append($table);
					}
					if(this.children.length>0){
						$.each(this.children,function(){
							var tr='<tr><td align="center"><input type="checkbox" value="'+this.id+'"/></td>';
							var child=this;
							$sub_div.find('th').each(function(){
								var dataIndex=$(this).attr('dataIndex');
								if(dataIndex){
									if(dataIndex=='PlanComment'){
										tr+='<td width="70"><span style="cursor:pointer" onclick="viewContent('+child.id+')">查看</span></td>';
									}else if(dataIndex=='SubGroupName'){
										tr+='<td width="120">'+child[dataIndex]+'</td>';
									}else
										tr+='<td>'+child[dataIndex]+'</td>';
								}
							});
							tr+='</tr>';
							$sub_div.find('table').append($(tr));
						});
					}
					$li.append($sub_div);
					$ul.append($li);
				});
				_div.append($ul);
				$('tr').filter(function(){
					return $(this).find('td').size()>0;
				}).mouseover(function(){
					$(this).addClass('hover');
				}).mouseout(function(){
					$(this).removeClass('hover');
				});
				$('table').each(function(){
					$(this).find('tr').each(function(i){
						if(i>0){
							if(i%2==0){
								$(this).addClass('even');
							}else{
								$(this).addClass('odd');
							}
						}
					});
				});
				$('input[type="checkbox"][name="treeCheckbox"]').click(function(){
					var checked=this.checked;
					$('input[type="checkbox"][name="treeCheckbox"]').attr('checked',false);
					$('div.sub-div').css('display','none');
					if(checked){
						this.checked=true;
						$(this).parent().find('div.sub-div').css('display','block');
					}
				});
				$('table').each(function(){
					var table=$(this);
					table.find('input[type="checkbox"]').click(function(){
						table.find('input[type="checkbox"]').attr('checked',false);
						$(this).attr('checked',true);
					});
				});
			}else{
				_div.html('服务器出错。');
			}
		},
		'json'
	);
	$('#content-dialog').dialog({title:'方案建议',autoOpen:false,width:400});
	$('#tpl-dialog').dialog({title:'医嘱随访模板',autoOpen:false,width:600});
}
function viewContent(_id){
	$.post(
		App.App_Info.BasePath+'/common/CommonAction.do',
		{method:'GetSelfQueryList',sql:'select PlanComment from SYS_MemberGroup_Table where id='+_id},
		function(data){
			if(data.length>0){
				$('#content-dialog').html(data[0]['PlanComment']);
				$('#content-dialog').dialog('open');
			}
		},
		'json'
	);
}
function viewTpl(){
	var checked=null;
	$('input[name="treeCheckbox"]').each(function(){
		if(this.checked){
			checked=$(this);
			return false;
		}
	});
	if(!checked){
		alert('未选择分组。');
		return;
	}
	var checked2=null;
	checked.parent().find('table').eq(0).find('input[type="checkbox"]').each(function(){
		if(this.checked){
			checked2=$(this);
			return false;
		}
	});
	if(!checked2){
		alert('未选择分组。');
		return;
	}
	var _id=checked2.val();
	var table='<table class="datagrid" border=0><tr><th>随访类型</th><th>随访名称</th><th>每次用量</th><th>单位</th><th>用法</th><th>疗程</th><th>单位</th><th>备注</th></tr>';
	$.post(
		App.App_Info.BasePath+'/admin/MemberGroupMngAction.do',
		{method:'tpl_findByGroupId',id:_id},
		function(data){
			$.each(data,function(i){
				table+='<tr class="'+(i%2==0?'odd':'even')+'">';
				if(this.type==0){
					table+='<td>治疗建议</td>'+
					'<td>'+this.name+'</td>'+
					'<td>'+this.perValumon+'</td>'+
					'<td>'+this.unit+'</td>'+
					'<td>'+this.useFn+'</td>'+
					'<td>'+this.course+'</td>'+
					'<td>'+tplCourseUnit[this.courseUnit]+'</td>'+
					'<td>'+this.memo+'</td>';
				}else if(this.type==1){
					table+='<td>生活指导</td>'+
					'<td colspan=6>'+this.name+'</td>'+
					'<td>'+this.memo+'</td>';
				}else if(this.type==2){
					table+='<td>检查</td>'+
					'<td colspan=4>'+this.name+'</td>'+
					'<td>'+this.course+'</td>'+
					'<td>'+tplCourseUnit[this.courseUnit]+'</td>'+
					'<td>'+this.memo+'</td>';
				}
				table+='</tr>';
			});
			table+='</table>';
			$('#tpl-dialog').html(table);
			$('#tpl-dialog').dialog('open');
		},
		'json'
	);
}
function OkClick(){
	var checked=null;
	$('input[name="treeCheckbox"]').each(function(){
		if(this.checked){
			checked=$(this);
			return false;
		}
	});
	if(!checked){
		alert('未选择分组。');
		return;
	}
	var checked2=null;
	checked.parent().find('table').eq(0).find('input[type="checkbox"]').each(function(){
		if(this.checked){
			checked2=$(this);
			return false;
		}
	});
	if(!checked2){
		alert('未选择分组。');
		return;
	}
	var json={id:checked2.val(),name:checked2.parent().next().text()};
	window.returnValue=json;
	window.close();
}
