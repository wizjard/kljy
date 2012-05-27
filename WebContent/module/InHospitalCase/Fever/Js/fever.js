	//取消事件
	function backToPreIllWindow(){
		parent.document.getElementById('FerverHistory').contentWindow.dbclickedSpan = null;
		parent.showPreHis();	
	}
	function show(){
		//保存信息
		var msg='';
		//获取name:fare被选中单选按钮的值
		var fr= $("input[name='fare']:checked").val(); 
		if(fr==1){
			//显示发热类型值
			$("#tr1").show();
			$("#fareDiv").show();
			//发热方式			
			var fr_type= $("input[name='fareType']:checked").val();
			if(fr_type== 0){
				msg+="发热方式：急性起病,";
			}else if(fr_type==1){
				msg+="发热方式：缓慢起病,";
			}else if(fr_type==2){
				msg+="发热方式：急骤起病，且病情凶险, ";
			}		
			//热型
			var fr_xing=$("input[name='fareXing']:checked").val();	
			if(fr_xing==1){
				msg+="热型：无规律性,";
			}else if(fr_xing==2){
				msg+="热型：稽留热,";
			}
			else if(fr_xing==3){
				msg+="热型：弛张热,";
			}else if(fr_xing==4){
				msg+="热型：间歇热,";
			}else if(fr_xing==5){
				msg+="热型：波状热,";
			}else if(fr_xing==6){
				msg+="热型：回归热,";
			}else if(fr_xing==7){
				msg+="热型：午后低热,";
			}else if(fr_xing==0){
				if($("#fareXingZdy").val().length!=0){
					msg+="热型："+$("#fareXingZdy").val()+",";	
				}									
			}
			//体温
			var fr_temp=$("input[name='fareTemperature']:checked").val();	
			var temperature='';
			if(fr_temp==0){
				msg+="体温不详,";
			}else if(fr_temp==1){
				$("#tem_start").focus();	
				var startTemp=$("#temStart").val();
				var endTemp=$("#temEnd").val();
				if(startTemp != '' && endTemp != ''){
					msg+="体温波动于"+startTemp+"至"+endTemp+"摄氏度之间,";
				}else{
					alert('体温波动的值不能为空！');
					return false;
				}
				
			}else if(fr_temp==2){
				$("#temMax").focus();
				var maxTemp=$("#temMax").val();
				msg+="最高体温:"+maxTemp+"摄氏度,";
			}else if(fr_temp==3){
				$("#features").focus();
				msg+="发作特点:体温高峰于"+$("#features").val()+"出现,呈现"+$("#show").val()+",";
			}
		}else if(fr==0){
			$("#fareDiv").hide();
			$("#tr1").hide();
			msg+="无发热,";
		}
		//获取name:weihan被选中单选按钮的值
		var wh= $("input[name='weihan']:checked").val(); 
		if(wh==0){
			msg+="无畏寒,";
		}else if(wh==1){
			msg+="有畏寒,";
		}
		//获取name:hanzhan被选中单选按钮的值
		var hz= $("input[name='hanzhan']:checked").val(); 
		if(hz==1){
			//显示寒战类型值
			$("#tr2").show();
			$("#hanzhanDiv").show();			
			//获取寒战持续值
			var hz_value=$("input[name='hanzhanContinue']:checked").val(); 
			if(hz_value==0){
				msg+="有寒战,寒战持续:1~5分钟,";
			}else if(hz_value==1){
				msg+="有寒战,寒战持续:5~10分钟,";
			}else if(hz_value==2){
				msg+="有寒战,寒战持续:>10分钟,";
			}else if(hz_value==3){
				msg+="有寒战,寒战持续:时间不详,";
			}						
		}else if(hz==0){
			$("#tr2").hide();
			$("#hanzhanDiv").hide();
			msg+="无寒战,";
		}
		//大汗
		var sweat= $("input[name='sweat']:checked").val(); 
		if(sweat==0){
			msg+="无大汗,";
		}else if(sweat==1){
			msg+="大汗,";
		}
		//盗汗
		var daohan= $("input[name='daohan']:checked").val(); 
		if(daohan==0){
			$("#tr3").hide();
			$("#daohanDiv").hide();
			msg+="无盗汗,";
		}else if(daohan==1){
			$("#tr3").show();
			$("#daohanDiv").show();
			//获取多选按钮的值
			var Check = '';
			var checkedObj = $("input[name='daohanShowTime']:checked");//获取当前checked的value值 如果选中多个则循环
			checkedObj.each(function(){
				var isCheck = this.value;
				Check += isCheck+" ";
			});
			if(Check.length!=0){
				msg+=Check+',';	
			}
			else{
				msg+=$("input[name='daohanShowTime']").val()+',';	
				}
		}
		//乏力
		var fali= $("input[name='fali']:checked").val(); 
		if(fali==0){
			$("#tr4").hide();
			$("#faliDiv").hide();
		}else if(fali==1){
			$("#tr4").show();
			$("#faliDiv").show();
			//获取值
			var fl=$("input[name='faliShowTime']:checked").val();
			if(fl==0){ 
				msg+="时有乏力,";
			}else if(fl==1){
				msg+="偶有乏力,";
			}else if(fl==2){
				msg+="持续乏力,";
			}else if(fl==3){
				msg+="肢体稍倦,可坚持轻体力工作,";
			}else if(fl==4){
				msg+="四肢乏力,勉强坚持日常活动,";
			}else if(fl==5){
				msg+="全身无力,不能坚持日常活动,";
			}				
		}
		//全身不适
		var qsbs= $("input[name='qsbs']:checked").val(); 
		if(qsbs==0){
			$("#tr5").hide();
			$("#qsbsDiv").hide();
		}else if(qsbs==1){
			$("#tr5").show();
			$("#qsbsDiv").show();
			//获取值var hz_value=$("input[name='hanzhan_continue']:checked").val();
			var qsbs_value=$("input[name='qsbsStatue']:checked").val();
			if(qsbs_value==0){ 
				msg+="时有全身不适,";
			}else if(qsbs_value==1){
				msg+="偶有全身不适,";
			}else if(qsbs_value==2){
				msg+="持续全身不适,";
			}			
		}
		//皮肤异常
		var pfex= $("input[name='pfex']:checked").val(); 
		if(pfex==0){
			$("#tr6").hide();
			$("#pfexDiv").hide();
		}else if(pfex==1){
			$("#tr6").show();
			$("#pfexDiv").show();
			//异常部位
			var Check = '';
			var checkedObj = $("input[name='pfexParts']:checked");//获取当前checked的value值 如果选中多个则循环
			checkedObj.each(function(){
				var isCheck = this.value;
				//判断是否为自定义
				if(isCheck == 0){
					var pfex_parts_zidingyi=$("#pfexPartsZidingyi").val();
					Check+=pfex_parts_zidingyi;
				}
					Check += isCheck+" ";
			
			});
			if(Check.length!=0){
				msg+="异常部位："+Check+",";
			}else{
				msg+="异常部位："+$("input[name='pfexParts']").val()+",";
			}	
			//皮肤颜色
			var pfex_color=$("input[name='pfexColor']:checked").val();
			if(pfex_color==0){
				msg+="皮肤苍白,";
			}else if(pfex_color == 1){
				msg+="皮肤发红,";
			}else if(pfex_color == 2){
				var prex_color_value=$("#pfexColorZidingyi").val();
				if(prex_color_value.length!=0){
						msg+="皮肤"+prex_color_value+",";					
				}
			}
			//获皮疹值
			var pz_value=$("input[name='prexPizhen']:checked").val();
			if(pz_value==0){
				$("#tr6_1").hide();
				$("#pfex_pizhenDiv").hide();
				msg+="无皮疹,";
			}else if(pz_value==1){
				$("#tr6_1").show();
				$("#pfex_pizhenDiv").show();
				//部位
				var prex_parts=$("#prexParts").val();		
				if(prex_parts.length!=0){
					msg+="部位："+prex_parts;
				}
				//颜色
				var prex_color=$("#prexColor").val();
				if(prex_color.length!=0){
					msg+=",颜色："+prex_color;
				}
				//大小
				var prex_size=$("#prexSize").val();	
				if(prex_size.length!=0){
					msg+=prex_size+"大小 ";
				}
				//形态
				var prex_xt=$("input[name='prexStatue']:checked").val();
				if(prex_xt==0){
					msg+=",形态：充血性斑疹,";
				}else if(prex_xt==1){
					msg+=",形态：丘疹,";
				}else if(prex_xt==2){
					msg+=",形态：环形红斑,";
				}else if(prex_xt==3){
					msg+=",形态：游走性红斑,";
				}else if(prex_xt==4){
					msg+=",形态：疱疹,";
				}else if(prex_xt==5){
					msg+=",形态：脓疱疹,";
				}else if(prex_xt==6){
					msg+=",形态：瘀点瘀斑,";
				}else if(prex_xt==7){
					msg+=",形态：出血点,";
				}else if(prex_xt==8){
					msg+=",形态：水疱,";
				}else if(prex_xt==9){
					msg+=",形态：溃疡,";
				}else if(prex_xt==10){
					msg+=",形态：结痂,";
				}else if(prex_xt==11){
					$("#prexStatueZidingyi").focus();
					var prex_xt_zidingyi=$("#prexStatueZidingyi").val();
					if(prex_xt_zidingyi.length!=0){
						msg+=",形态：自定义："+prex_xt_zidingyi;
					}
				}
				//压之
				var prex_yz=$("input[name='prexYazhi']:checked").val();
				if(prex_yz==0){
					msg+="压之褪色,";
				}else if(prex_yz==1){
					msg+="压之不褪色,";
				}
				//疹间皮肤
				var prex_zjpf=$("input[name='prexZhenfu']:checked").val();
				if(prex_zjpf==0){
					msg+="疹间皮肤正常,";
				}else if(prex_zjpf==1){
					msg+="疹间皮肤充血,";
				}
				//伴随
				var prex_bansui=$("input[name='prexBansui']:checked").val();
				if(prex_bansui == 0){
					msg+="伴随疼痛,";
				}else if(prex_bansui == 1){
					msg+="伴随瘙痒,";
				}else if(prex_bansui == 2){
					msg+="伴随麻木,";
				}else if(prex_bansui == 3){
					msg+="伴随蚁行感,";
				}else if(prex_bansui == 4){
					msg+="伴随脱屑,";
				}else if(prex_bansui == 5){
					msg+="伴随脱皮,";
				}else if(prex_bansui == 6){
					msg+="伴随搔抓痕,";
				}		
				//出诊顺序	
				var prex_sort=$("#prexSort").val();
				if(prex_sort.length!=0){
				$("#prexSort").click();
					msg+="出诊顺序为："+prex_sort+",";
				}
				//规律	
				var prex_guilv=$("#prexGuilv").val();
				if(prex_guilv.length!=0){
					$("#prexGuilv").click();
					msg+=prex_guilv+",";
				}			
			}
			//其他
			var prex_other=$("#prexOther").val();
			if(prex_other.length!=0){
				$("#prex_other").click();
				msg+=prex_other+"。";
			}
		}
		//鼻塞
		var bs= $("input[name='bisai']:checked").val(); 
		if(bs==0){
			$("#tr7").hide();
			$("#bisaiDiv").hide();
		}else if(bs==1){
			$("#tr7").show();
			$("#bisaiDiv").show();
			var bisai_statue1 = $("input[name='bisaiStatue1']:checked").val();
			var bisai_statue2 = $("input[name='bisaiStatue2']:checked").val();
			if(bisai_statue1 == 0){
				msg+='偶有鼻塞';
			}else if(bisai_statue1 == 1){
				msg+='时有鼻塞 ';
			}else if(bisai_statue1 == 1){
				msg+='持续鼻塞';
			}
			if(bisai_statue1 == 0){
				msg+='轻度,';
			}else if(bisai_statue1 == 1){
				msg+='中度,';
			}else if(bisai_statue1 == 2){
				msg+='重度,';
			}else if(bisai_statue1 == 3){
				msg+='鼻音声重,';
			}
		}
		//流涕
		var lt= $("input[name='liuti']:checked").val(); 
		if(lt==0){
			$("#tr8").hide();
			$("#liutiDiv").hide();
		}else if(lt==1){
			$("#tr8").show();
			$("#liutiDiv").show();
			var ltValue= $("input[name='liutiStatue']:checked").val(); 
			if(ltValue == 0){
				msg+="流涕稀薄状,";
			}else if(ltValue == 1){
				msg+="流涕粘稠状,";
			}		
		}
		//流泪
		var ll= $("input[name='liulei']:checked").val(); 
		if(ll==0){
			$("#tr9").hide();
			$("#liuleiDiv").hide();
		}else if(ll==1){
			$("#tr9").show();
			$("#liuleiDiv").show();
			var llValue= $("input[name='liuleiStatue']:checked").val(); 
			if(llValue == 0){
				msg+="流泪程度：偶有,";
			}else if(llValue == 1){
				msg+="流泪程度：时有,";
			}else if(llValue == 2){
				msg+="流泪程度：持续,";
			}else if(llValue == 3){
				msg+="流泪程度：轻度,";
			}else if(llValue == 4){
				msg+="流泪程度：中度,";
			}else if(llValue == 5){
				msg+="流泪程度：重度,";
			}			
		}
		//咽部不适
		var yanhou= $("input[name='yanhou']:checked").val(); 
		if(yanhou==0){
			$("#tr10").hide();
			$("#yanhouDiv").hide();
		}else if(yanhou==1){
			$("#tr10").show();
			$("#yanhouDiv").show();
			//取值 
			var yanhou_value= $("input[name='yanhouStatue']:checked").val();
			if(yanhou_value == 0){
				msg+="咽干,";
			}else if(yanhou_value == 1){
				msg+="咽痒,";
			}else if(yanhou_value == 2){
				msg+="咽痛,";
			}else if(yanhou_value == 3){
				msg+="咽部烧灼感,";
			}else if(yanhou_value == 4){
				msg+="喉痛,";
			}else if(yanhou_value == 5){
				msg+="喉痒,";
			}else if(yanhou_value == 6){
				msg+="声音嘶哑,";
			}else if(yanhou_value == 7){
				msg+="失声,";
			}
		}
		//咳嗽
		var kesou= $("input[name='kesou']:checked").val(); 
		if(kesou==0){
			$("#tr11").hide();
			$("#kesouDiv").hide();
		}else if(kesou==1){
			$("#tr11").show();
			$("#kesouDiv").show();
			//取发作特点值 
			var kesou_feature_value= $("input[name='kesouFeature']:checked").val();
			if(kesou_feature_value == 0){
				msg+="发作特点：频繁咳嗽,";
			}else if(kesou_feature_value == 1){
				msg+="发作特点：间断咳嗽,";
			}else if(kesou_feature_value == 2){
				msg+="发作特点：偶尔咳嗽,";
			}else if(kesou_feature_value == 3){
				msg+="发作特点：阵发性咳嗽,";
			}else if(kesou_feature_value == 4){
				msg+="发作特点：突发性咳嗽,";
			}else if(kesou_feature_value == 5){
				msg+="发作特点：发作性咳嗽,";
			}else if(kesou_feature_value == 6){
				msg+="发作特点：长期慢性咳嗽,";
			}
			//程度
			var kesou_level_value= $("input[name='kesouLevel']:checked").val();
			if(kesou_level_value == 0){
				msg+="为轻度";
			}else if(kesou_level_value == 1){
				msg+="为中度";
			}else if(kesou_level_value == 2){
				msg+="为重度";
			}
			//性质
			var kesou_nature_value= $("input[name='kesouNature']:checked").val();
			if(kesou_nature_value == 0){
				msg+="干咳,";
			}else if(kesou_nature_value == 1){
				msg+="刺激性干咳,";
			}else if(kesou_nature_value == 2){
				msg+="咳嗽,";
			}else if(kesou_nature_value == 3){
				msg+="痉挛性咳嗽,";
			}else if(kesou_nature_value == 4){
				msg+="咳后鸡鸣样吸气声,";
			}else if(kesou_nature_value == 5){
				msg+="犬吠样咳嗽,";
			}
			//呈
			var kesou_sound_value= $("input[name='kesouSound']:checked").val();
			if(kesou_sound_value == 0){
				msg+="声音嘶哑,";
			}else if(kesou_sound_value == 1){
				msg+="呈鸡鸣样,";
			}else if(kesou_sound_value == 2){
				msg+="呈金属音,";
			}else if(kesou_sound_value == 3){
				msg+="声音低微,";
			}else if(kesou_sound_value == 4){
				msg+="声音无力,";
			}
			//发作时间
			var kesou_time_value= $("input[name='kesouTime']:checked").val();
			if(kesou_time_value == 0){
				msg+="全天,";
			}else if(kesou_time_value == 1){
				msg+="晨起为重,";
			}else if(kesou_time_value == 2){
				msg+="夜间为重,";
			}
			
		}
		//咳嗽
		var ketan= $("input[name='ketan']:checked").val(); 
		if(ketan==0){
			$("#tr12").hide();
			$("#ketanDiv").hide();
		}else if(ketan==1){
			$("#tr12").show();
			$("#ketanDiv").show();
			//获取频度信息
			var pd=$("input[name='ketanPd']:checked").val();
			if(pd == 0){
				msg+="频繁咳";
			}else if(pd == 1){
				msg+="间断咳";
			}else if(pd == 2){
				msg+="偶尔咳";
			}else if(pd == 2){
				msg+="阵发性咳";
			}
			//获取痰量信息
			var volume=$("input[name='kesouVolume']:checked").val();
			if(volume == 0){
				//获取填写信息
				var kesou_volume_value1=$("#kesouVolumeValue1").val();
				if(kesou_volume_value1.length != 0){
					msg+="少量,量约"+kesou_volume_value1+"毫升/日,";
				}else{
					msg+="少量,";
				}
			}else if(volume == 1){
				//获取填写信息
				var kesou_volume_value2=$("#kesouVolumeValue2").val();
				if(kesou_volume_value2.length != 0){
					msg+="中等量,量约"+kesou_volume_value2+"毫升/日,";
				}else{
					msg+="中等量,";
				}
			}else if(volume == 2){
				//获取填写信息
				var kesou_volume_value3=$("#kesouVolumeValue3").val();
				if(kesou_volume_value3.length != 0){
					msg+="大量,量约"+kesou_volume_value3+"毫升/日,";
				}else{
					msg+="大量,";
				}
			}
			//获取颜色信息
			var kesou_color=$("input[name='kesouColor']:checked").val();
			if(kesou_color == 0){
				msg+="黄色";
			}else if(kesou_color == 1){
				msg+="白色";
			}else if(kesou_color == 2){
				msg+="无色透明";
			}else if(kesou_color == 3){
				msg+="粉红色";
			}else if(kesou_color == 4){
				msg+="血色";
			}else if(kesou_color == 5){
				msg+="红色";
			}else if(kesou_color == 6){
				msg+="棕红色";
			}else if(kesou_color == 7){
				msg+="巧克力样";
			}else if(kesou_color == 8){
				msg+="绿色";
			}else if(kesou_color == 9){
				msg+="黄绿色";
			}else if(kesou_color == 10){
				msg+="翠绿色";
			}else if(kesou_color == 11){
				msg+="铁锈色";
			}else if(kesou_color == 12){
				//获取自定义信息
				var kesou_color_value=$("#kesouColorValue").val();
				msg+=kesou_color_value;
			}
			//获取性质信息
			var kesou_xz=$("input[name='kesouXz']:checked").val();
			if(kesou_xz == 0){
				msg+="稀薄痰,";
			}else if(kesou_xz == 1){
				msg+="粘液痰,";
			}else if(kesou_xz == 2){
				msg+="泡沫痰,";
			}else if(kesou_xz == 3){
				msg+="浆液痰,";
			}else if(kesou_xz == 4){
				msg+="粘液脓痰,";
			}else if(kesou_xz == 5){
				msg+="脓性痰,";
			}else if(kesou_xz == 6){
				msg+="胶冻样痰,";
			}else if(kesou_xz == 7){
				msg+="烂桃样痰,";
			}else if(kesou_xz == 8){
				msg+="果酱样痰,";
			}else if(kesou_xz == 9){
				msg+="血脓混合痰,";
			}else if(kesou_xz == 10){
				msg+="粘稠，牵拉成丝,";
			}else if(kesou_xz == 11){
				msg+="可分层,";
			}else if(kesou_xz == 12){
				msg+="不分层,";
			}else if(kesou_xz == 13){
				//获取自定义
				var kesou_xz_value=$("#kesouXzValue").val();
				msg+=kesou_xz_value+",";
			}
			//气味异常
			var kesou_ex=$("input[name='kesouEx']:checked").val();
			if(kesou_ex == 0){
				msg+="无气味,";
			}else if(kesou_ex == 1){
				msg+="有气味,";
			}else if(kesou_ex == 2){
				msg+="有恶臭,";
			}
			var kesou_kechu=$("input[name='kesouKechu']:checked").val();
			if(kesou_kechu == 0){
			 	msg+="易咳出,";
			}else if(kesou_kechu == 1){
				msg+="不易咳出,";
			}
		}
		//心悸
		var xinji= $("input[name='xinji']:checked").val(); 
		if(xinji==0){
			$("#tr13").hide();
			$("#xinjiDiv").hide();
		}else if(xinji==1){
			$("#tr13").show();
			$("#xinjiDiv").show();
			//获取诱因信息
			var xj_because=$("input[name='xinjiBecause']:checked").val(); 
			if(xj_because == 0){
				msg+="劳累后";
			}else if(xj_because == 1){
				msg+="生气后";
			}else if(xj_because == 2){
				msg+="运动后";
			}else if(xj_because == 3){
				msg+="受凉后";
			}else if(xj_because == 4){
				msg+="紧张后";
			}else if(xj_because == 5){
				msg+="焦虑后";
			}else if(xj_because == 6){
				msg+="注意力集中后";
			}else if(xj_because == 7){
				msg+="情绪激动后";
			}else if(xj_because == 8){
				msg+="饮酒后";
			}else if(xj_because == 9){
				msg+="喝茶后";
			}else if(xj_because == 10){
				//获取文本框的值
				var xinji_because_yy=$("#xinjiBecauseYy").val();
				msg+="应用"+xinji_because_yy+',';
			}else if(xj_because == 11){
				var xinji_because_value=$("#xinjiBecauseValue").val();
				msg+=xinji_because_value+"后";
			}
			//程度
			var xinji_level=$("input[name='xinjiLevel']:checked").val(); 
			if(xinji_level == 0){
				msg+="稍有心悸";
			}else if(xinji_level == 1){
				msg+="明显心悸";
			}else if(xinji_level == 2){
				msg+="轻度心悸";
			}else if(xinji_level == 3){
				msg+="中度心悸";
			}else if(xinji_level == 4){
				msg+="重度心悸";
			}
			//频度
			var xinji_pd=$("input[name='xinjiPd']:checked").val();
			if(xinji_pd == 0){
				msg+="偶有发作,";
			}else if(xinji_pd == 1){
				msg+="经常发作,";
			}else if(xinji_pd == 2){
				msg+="持续发作,";
			}
			//取时分秒年月日的值
			var units=$("#continueTime").val();
			var xinji_pd_continueTime=$("#xinjiPdContinueTime").val();
			if(xinji_pd_continueTime.length != 0){
				msg+="每次发作约持续"+xinji_pd_continueTime+units;
			}
			
		}
		//呼吸困难
		var huxikunnan= $("input[name='huxikunnan']:checked").val(); 
		if(huxikunnan==0){
			$("#tr143").hide();
			$("#huxikunnanDiv").hide();
		}else if(huxikunnan==1){
			$("#tr14").show();
			$("#huxikunnanDiv").show();
			//诱因
			var huxikunnan_because=$("input[name='huxikunnanBecause']:checked").val()
			if(huxikunnan_because == 0){
				msg+="劳累";
			}else if(huxikunnan_because == 1){
				msg+="生气";
			}else if(huxikunnan_because == 2){
				msg+="运动";
			}else if(huxikunnan_because == 3){
				msg+="受凉";
			}else if(huxikunnan_because == 4){
				msg+="吸入异物";
			}else if(huxikunnan_because == 5){
				var huxikunnan_because_value=$("#huxikunnanBecauseValue").val();
				msg+="接触"+huxikunnan_because_value;
			}else if(huxikunnan_because == 6){
				msg+="外伤";
			}else if(huxikunnan_because == 7){
				var huxikunnan_because_value1=$("#huxikunnanBecauseValue1").val();
				msg+=huxikunnan_because_value1;
			}
			//发作特点
			var huxikunnan_features=$("input[name='huxikunnanFeatures']:checked").val()
			if(huxikunnan_features == 0){
				msg+="偶有";
			}else if(huxikunnan_features == 1){
				msg+="时有";
			}else if(huxikunnan_features == 2){
				msg+="持续";
			}else if(huxikunnan_features == 3){
				msg+="急性";
			}else if(huxikunnan_features == 4){
				msg+="慢性";
			}else if(huxikunnan_features == 5){
				msg+="反复发作性";
			}
			//程度xinji_level
			var huxikunnan_level=$("input[name='huxikunnanLevel']:checked").val();
			if(huxikunnan_level == 0){
				msg+="轻度";
			}else if(huxikunnan_level == 1){
				msg+="中度";
			}else if(huxikunnan_level == 2){
				msg+="重度";
			}else if(huxikunnan_level == 3){
				var huxikunnan_level_value=$("#huxikunnanLevelValue").val();
				msg+=huxikunnan_level_value;
			}
			//类型
			var huxikunnan_type=$("input[name='huxikunnanType']:checked").val();
			if(huxikunnan_type == 0){
				msg+="吸气性呼吸困难,";
			}else if(huxikunnan_type == 1){
				msg+="呼气性呼吸困难,";
			}else if(huxikunnan_type == 2){
				msg+="混合性呼吸困难,";
			}else if(huxikunnan_type == 3){
				msg+="端坐呼吸呼吸困难,";
			}
			//发作时间xinji_time
			var huxikunnan_time=$("input[name='huxikunnanTime']:checked").val();
			if(huxikunnan_time == 0){
				msg+="全天为重,";
			}else if(huxikunnan_time == 1){
				msg+="晨起为重,";
			}else if(huxikunnan_time == 2){
				msg+="夜间为重,";
			}
			//加重 缓解因素
			var yinsu1=$("#yinsu1").val();
			if(yinsu1!=''){
				msg+="多于"+yinsu1+"明显,";
			}
			var yinsu2=$("#yinsu2").val();
			if(yinsu2!=''){
				msg+="于"+yinsu2+"减轻,";
			}
			var yinsu3=$("#yinsu3").val();
			if(yinsu3!=''){
				msg+="与"+yinsu3+"有明显关系";
			}
		}
		//胸痛
		var xiongtong=$("input[name='xiongtong']:checked").val();
		if(xiongtong == 0){
			$("#tr15").hide();
			$("#xiongtongDiv").hide();
		}else if(xiongtong == 1){
			$("#tr15").show();
			$("#xiongtongDiv").show();
			//获取诱因
			var xiongtong_because=$("input[name='xiongtongBecause']:checked").val();
				if(xiongtong_because == 0){
					msg+='劳累';
				}else if(xiongtong_because == 1){
					msg+='精神紧张时';
				}else if(xiongtong_because == 2){
					msg+='神紧张时后';
				}else if(xiongtong_because == 3){
					msg+='深吸气时';
				}else if(xiongtong_because == 4){
					msg+='深吸气时后';
				}else if(xiongtong_because == 5){
					msg+='局部压迫时';
				}else if(xiongtong_because == 6){
					msg+='体力劳动 ';
				}else if(xiongtong_because == 7){
					msg+='情绪激动时';
				}else if(xiongtong_because == 8){
					msg+='饱食';
				}else if(xiongtong_because == 9){
					msg+='情绪激动后';
				}else if(xiongtong_because == 10){
					msg+='受凉后';
				}else if(xiongtong_because == 11){
					msg+='饮酒';
				}else if(xiongtong_because == 12){
					msg+='咳嗽后';
				}else if(xiongtong_because == 13){
					msg+='吸烟';
				}else if(xiongtong_because == 14){
					var xiongtong_because_value=$("#xiongtongBecauseValue").val();
					msg+=xiongtong_because_value;
				}
				//获取部位值
			var Check = '';
			var checkedObj = $("input[name='xiongtongParts']:checked");//获取当前checked的value值 如果选中多个则循环
			checkedObj.each(function(){
				var isCheck = this.value;
				if(isCheck == 0){
					var xiongtong_parts_zdy=$('#xiongtongPartsZdy').val();
					Check+=xiongtong_parts_zdy;
				}
					Check += isCheck;
				
			});
			if(Check.length != 0){
				msg+='出现'+Check+',';
			}else{
				msg+='出现'+$("input[name='xiongtongParts']").val()+',';
			}
			//获取程度
			var xiongtong_level=$("input[name='xiongtongLevel']:checked").val();
				if(xiongtong_level == 0){
					msg+='轻度';
				}else if(xiongtong_level == 1){
					msg+='中度';
				}else if(xiongtong_level == 2){
					msg+='重度';
				}else if(xiongtong_level == 3){
					msg+='剧痛';
				}else if(xiongtong_level == 4){
					msg+='时轻时重';
				}
			
			//获取性质
			var xiongtong_features=$("input[name='xiongtongFeatures']:checked").val();
				if(xiongtong_features == 0){
					msg+='刺痛,';
				}else if(xiongtong_features == 1){
					msg+='撕裂痛,';
				}else if(xiongtong_features == 2){
					msg+='隐痛,';
				}else if(xiongtong_features == 3){
					msg+='钝痛,';
				}else if(xiongtong_features == 4){
					msg+='刀割样疼痛,';
				}
				else if(xiongtong_features == 5){
					msg+='针刺样疼痛,';
				}else if(xiongtong_features == 6){
					msg+='胀痛,';
				}else if(xiongtong_features == 7){
					msg+='烧灼痛,';
				}else if(xiongtong_features == 8){
					msg+='压迫痛,';
				}else if(xiongtong_features == 9){
					msg+='紧缩性痛,';
				}else if(xiongtong_features == 10){
					msg+='绞榨样痛,';
				}else if(xiongtong_features == 11){
					msg+='闷痛,';
				}else if(xiongtong_features == 12){
					msg+='酸痛,';
				}
				else if(xiongtong_features == 13){
					msg+='锐痛,';
				}else if(xiongtong_features == 14){
					msg+='锥痛,';
				}else if(xiongtong_features == 15){
					msg+='钻顶样痛,';
				}else if(xiongtong_features == 16){
					msg+='紧缩性痛,';
				}else if(xiongtong_features == 17){
					msg+='紧箍感,';
				}else if(xiongtong_features == 18){
					msg+='压迫痛,';
				}else if(xiongtong_features == 19){
					msg+='串痛,';
				}else if(xiongtong_features == 20){
					msg+='转移性疼痛,';
				}else if(xiongtong_features == 21){
					msg+='搏动性痛,';
				}else if(xiongtong_features == 22){
					msg+='电击样痛,';
				}else if(xiongtong_features == 23){
					msg+='钳夹样痛,';
				}else if(xiongtong_features == 24){
					var xiongtong_features_zdy=$("#xiongtongFeaturesZdy").val();
					if(xiongtong_features_zdy.length!=0){
						msg+=xiongtong_features_zdy+',';
					}
				}
			//取位置的值
			var xiongtong_location=$("input[name='xiongtonLocation']:checked").val();
			if(xiongtong_location == 0){
				msg+='位置固定,';
			}else if(xiongtong_location == 1){
				msg+='位置不固定,';
			}
			//获取放射值
			var xiongtong_fs=$("input[name='xiongtongFs']:checked").val();
			if(xiongtong_fs == 0){
				$("#tr15_0").hide();
				$("#xt_15_0").hide();
				msg+='不向其他部位放射';
			}else if(xiongtong_fs == 1){
				$("#tr15_0").show();
				$("#xt_15_0").show();
				//放射部位
				var xiongtong_fs_parts=$("input[name='xiongtongFsParts']:checked").val();
				if(xiongtong_fs_parts == 0){
					msg+='放射部位：心前区,';
				}else if(xiongtong_fs_parts == 1){
					msg+='放射部位：左肩程度,';
				}else if(xiongtong_fs_parts == 2){
					msg+='放射部位：右肩,';
				}else if(xiongtong_fs_parts == 3){
					msg+='放射部位：左臂内侧,';
				}else if(xiongtong_fs_parts == 4){
					msg+='放射部位：手指,';
				}else if(xiongtong_fs_parts == 5){
					msg+='放射部位：颈,';
				}else if(xiongtong_fs_parts == 6){
					msg+='放射部位：咽,';
				}else if(xiongtong_fs_parts == 7){
					msg+='放射部位：下颌部,';
				}else if(xiongtong_fs_parts == 8){
					if($('#xiongtongFsPartsZdy').val().length!=0){
						msg+=$('#xiongtongFsPartsZdy').val()+',';
					}
					
				}
			}
			//取发作特点的值
			var xiongtong_fs_td=$("input[name='xiongtongFsTd']:checked").val();
			if(xiongtong_fs_td == 0){
				msg+='胸痛急性,';
			}else if(xiongtong_fs_td == 1){
				msg+='胸痛慢性,';
			}else if(xiongtong_fs_td == 2){
				msg+='胸痛持续性,';
			}else if(xiongtong_fs_td == 3){
				msg+='胸痛阵发性,';
			}else if(xiongtong_fs_td == 4){
				msg+='胸痛偶尔,';
			}else if(xiongtong_fs_td == 5){
				msg+='胸痛反复发作,';
			}else if(xiongtong_fs_td == 6){
				msg+='胸痛间断发作,';
			}else if(xiongtong_fs_td == 7){
				msg+='胸痛逐渐加重,';
			}else if(xiongtong_fs_td == 8){
				msg+='胸痛逐渐减轻,';
			}else if(xiongtong_fs_td == 9){
				msg+='胸痛加剧,';
			}else if(xiongtong_fs_td == 10){
				msg+='胸痛减轻,';
			}else if(xiongtong_fs_td == 11){
				msg+='胸痛突然出现,';
			}else if(xiongtong_fs_td == 12){
				if($('#xiongtongFsTdZdy').val().length!=0){
					msg+='胸痛'+$('#xiongtongFsTdZdy').val()+',';
				}				
			}
			//取持续时间的值
			var xiongtong_fs_time=$("#xiongtongFsTime").val();
			if(xiongtong_fs_time.length != 0){
				var xiongtong_fs_time_value=$("#xiongtongFsTimeValue").val();
				msg+='持续时间:'+xiongtong_fs_time+xiongtong_fs_time_value+',';
			}
			//缓解及加重因素
			var xt_yinsu1=$("#xtYinsu1").val();
			if(xt_yinsu1!=''){
				msg+=xt_yinsu1+'加重,';
			}
			var xt_yinsu2= $('#xtYinsu2').val();
			if(xt_yinsu2!=''){
				msg+=xt_yinsu2+'减轻。';
			}
		}
		//食欲减退
		var syjt=$("input[name='syjt']:checked").val();
		if(syjt == 0){
			$("#tr16").hide();
			$("#syjtDiv").hide();
		}else if(syjt == 1){
			$("#tr16").show();
			$("#syjtDiv").show();
			//获取程度的值
			var syjt_level=$("input[name='syjLevel']:checked").val();
			if(syjt_level == 0 ){
				msg+='轻度食欲减退,';
			}else if(syjt_level == 1){
				msg+='中度食欲减退,';
			}else if(syjt_level == 2){
				msg+='重度食欲减退,';
			}
			//是否食欲减少
			var syjt_desc=$("input[name='syjtDesc']:checked").val();
			if(syjt_desc == 0){
				msg+='进食量未减少。';
			}else if(syjt_desc == 1){
				msg+='进食量减少至原来的'+$('#slPer').val();
			}else if(syjt_desc == 2){
				msg+='进食量略有减少。';
			}else if(syjt_desc == 3){
				msg+='进少许流食。';
			}else if(syjt_desc == 4){
				msg+='未进食。';
			}
		}
		//恶心
		var exin=$("input[name='exin']:checked").val();
		if(exin == 0){
			$("#tr17").hide();
			$("#exinDiv").hide();
		}else if(exin == 1){
			$("#tr17").show();
			$("#exinDiv").show();
			//取值
			var exin_level=$("input[name='exinLevel']:checked").val();
			if(exin_level == 0){
				msg+='轻度恶心。';
			}else if(exin_level == 1){
				msg+='中度恶心。';
			}else if(exin_level == 2){
				msg+='重度恶心。';
			}
		}
		//呕吐
		var outu=$("input[name='outu']:checked").val();
		if(outu == 0){
			$("#tr18").hide();
			$("#outuDiv").hide();
		}else if(outu == 1){
			$("#tr18").show();
			$("#outuDiv").show();
			//取呕吐性质值
			var outu_level=$("input[name='outuLevel']:checked").val();
			if(outu_level == 0){
				msg+='喷射状呕吐,';
			}else if(outu_level == 1){
				msg+='非喷射状呕吐,';
			}
			//取呕吐物值
			var outu_thing=$("input[name='outuThing']:checked").val();
			if(outu_thing == 0){
				msg+='为胃内容物,';
			}else if(outu_thing == 1){
				msg+='淘米水样物,';
			}else if(outu_thing == 2){
				msg+='咖啡色物,';
			}else if(outu_thing == 3){
				msg+='咖啡色物伴血块,';
			}else if(outu_thing == 4){
				msg+='暗红色胃内容物,';
			}else if(outu_thing == 5){
				msg+='暗红色胃内容物伴血块	,';
			}
			//
			var outu_c=$("#outuC").val();
			var outu_c_num=$("#outuNum").val();
			if(outu_c .length!=0){
				msg+='每次'+outu_c+'ml,';
			}
			if(outu_c_num.length!=0){
				msg+='每天'+outu_c_num+'次。';
			}
		}
		//腹痛
		var ft=$("input[name='ft']:checked").val();
		if(ft == 0){
			$("#tr19").hide();
			$("#ftDiv").hide();
		}else if(ft == 1){
			$("#tr19").show();
			$("#ftDiv").show();
			//诱因
			var ft_because=$("input[name='ftBecause']:checked").val();
			if(ft_because == 0){
				$("#ft19_0").hide();
				$("#ft_19_0").hide();
			}else if(ft_because == 1){
				$("#ft19_0").show();
				$("#ft_19_0").show();
				//
				var ft_because_parts=$("input[name='ftBecauseParts']:checked").val();
				if(ft_because_parts == 0){
					msg+='进食油腻食,';
				}else if(ft_because_parts == 1){
					msg+='酗酒,';
				}else if(ft_because_parts == 2){
					msg+='暴饮暴食,';
				}else if(ft_because_parts == 3){
					msg+='腹部手术后,';
				}else if(ft_because_parts == 4){
					msg+='腹部受暴力作用,';
				}else if(ft_because_parts == 5){
					var ft_because_parts_zdy=$("#ftBecausePartsZdy").val();
					if(ft_because_parts_zdy.length != 0){
						msg+=ft_because_parts_zdy+',';
					}
				}				
			}
			//发作特点
			var ft_feature=$("input[name='ftFeature']:checked").val();
			if(ft_feature == 0){
				msg+='急性';
			}else if(ft_feature == 1){
				msg+='慢性';
			}else if(ft_feature == 2){
				msg+='持续性';
			}else if(ft_feature == 3){
				msg+='阵发性';
			}else if(ft_feature == 4){
				msg+='偶尔';
			}else if(ft_feature == 5){
				msg+='反复发作';
			}else if(ft_feature == 6){
				msg+='间断发作';
			}else if(ft_feature == 7){
				msg+='逐渐加重';
			}else if(ft_feature == 8){
				msg+='逐渐减轻';
			}else if(ft_feature == 9){
				msg+='加剧';
			}else if(ft_feature == 10){
				msg+='减轻';
			}else if(ft_feature == 11){
				msg+='突然出现';
			}else if(ft_feature == 12){
				msg+='时轻时重';
			}else if(ft_feature == 13){
				if($('#ftFeatureZdy').val().length!=0){
					msg+=$('#ftFeatureZdy').val();
				}				
			}
			//获取部位值
			var Check = '';
			var checkedObj = $("input[name='ftPart']:checked");
			checkedObj.each(function(){
				var isCheck = this.value;
					Check +=isCheck+' ';
			});
			if(Check.length != 0){
				msg+=Check+',';
			}else{
				msg+=$("input[name='ftPart']").val()+',';
			}
			//性质
			var ft_xz=$("input[name='ftXz']:checked").val();
				if(ft_xz == 0){
					msg+='刺痛,';
				}else if(ft_xz == 1){
					msg+='撕裂痛,';
				}else if(ft_xz == 2){
					msg+='隐痛,';
				}else if(ft_xz == 3){
					msg+='钝痛,';
				}else if(ft_xz == 4){
					msg+='刀割样疼痛,';
				}
				else if(ft_xz == 5){
					msg+='针刺样疼痛,';
				}else if(ft_xz == 6){
					msg+='胀痛,';
				}else if(ft_xz == 7){
					msg+='烧灼痛,';
				}else if(ft_xz == 8){
					msg+='压迫痛,';
				}else if(ft_xz == 9){
					msg+='紧缩性痛,';
				}else if(ft_xz == 10){
					msg+='绞榨样痛,';
				}else if(ft_xz == 11){
					msg+='闷痛,';
				}else if(ft_xz == 12){
					msg+='酸痛,';
				}
				else if(ft_xz == 13){
					msg+='锐痛,';
				}else if(ft_xz == 14){
					msg+='锥痛,';
				}else if(ft_xz == 15){
					msg+='钻顶样痛,';
				}else if(ft_xz == 16){
					msg+='紧缩性痛,';
				}else if(ft_xz == 17){
					msg+='紧箍感,';
				}else if(ft_xz == 18){
					msg+='压迫痛,';
				}else if(ft_xz == 19){
					msg+='串痛,';
				}else if(ft_xz == 20){
					msg+='转移性疼痛,';
				}else if(ft_xz == 21){
					msg+='搏动性痛,';
				}else if(ft_xz == 22){
					msg+='电击样痛,';
				}else if(ft_xz == 23){
					msg+='钳夹样痛,';
				}else if(ft_xz == 24){
					var ft_xz_zdy=$("#ftXzZdy").val();
					if(ft_xz_zdy.length!=0){
						msg+=ft_xz_zdy+',';
					}
				}
			//程度
			var ft_level=$("input[name='ftLevel']:checked").val();
			if(ft_level == 0){
				msg+='为剧痛,';
			}else if(ft_level==1){
				msg+='为中度腹痛,';
			}else if(ft_level==2){
				msg+='为轻度腹痛,';
			}
			//位置
			var ft_location=$("input[name='ftLocation']:checked").val();
			if(ft_location == 0){
				 msg+='位置固定,';
			}else if(ft_location == 1){
				 msg+='位置不固定,';
			}
			//放射部位
			var ft_fs=$("input[name='ftFs']:checked").val();
			if(ft_fs == 0){
				$("#ft19_1").hide();
				$("#ft_19_1").hide();
			}else if(ft_fs == 1){
				$("#ft19_1").show();
				$("#ft_19_1").show();
				//放射部位的值ft_fs_parts
				var ft_fs_parts = $("#ftFsParts").val(); 
				if(ft_fs_parts.length!=0)
				{
					msg+='放射部位：'+ft_fs_parts+',';
				}
			}
			//发作时间
			var ft_show=$("#ftShow").val();
			if(ft_show!=''){
				msg+="腹痛常于"+ft_show+'出现,';
			}
			//缓解及加重因素
			var ft_yinsu=$("#ftYinsu").val();
			var ft_desc=$("#ftDesc").val();
			var ft_guanxi=$("#ftGuanxi").val();
			if(ft_yinsu!=''){
				msg+='多于'+ft_yinsu+'明显,'
			}
			if(ft_desc!=''){
				msg+='于'+ft_desc+'减轻,';
			}
			if(ft_guanxi!=''){
				msg+='与'+ft_guanxi+'有明显关系。';
			}
		}
		//腹泻及大便改变
		var fxjchange=$("input[name='fxjchange']:checked").val();
		if(fxjchange == 0){
			$("#tr20").hide();
			$("#fxjchangeDiv").hide();
		}else if(fxjchange == 1){
			$("#tr20").show();
			$("#fxjchangeDiv").show();
			//腹泻
			var fxjchange_fx=$("input[name='fxjchangeFx']:checked").val();
			if(fxjchange_fx == 0){
				msg+='有急性腹泻,';
			}else if(fxjchange_fx == 1){
				msg+='有慢性腹泻,';
			}else if(fxjchange_fx == 2){
				msg+='<3次/日,,';
			}else if(fxjchange_fx == 3){
				msg+='3~5次/日腹泻,';
			}else if(fxjchange_fx == 4){
				msg+='5~10次/日腹泻,';
			}else if(fxjchange_fx == 5){
				msg+='>10次/日腹泻,';
			}else if(fxjchange_fx == 6){
				var fxjchange_fx_num=$("#fxjchangeFxNum").val();
				var fxjchange_fx_day=$("#fxjchangeFxDay").val();
				if(fxjchange_fx_num.length!=0){
					msg+=fxjchange_fx_num+'次/';
				}
				if(fxjchange_fx_day.length!=0){
					msg+=fxjchange_fx_day+'日.';				
				}				
			}
			var fxjchange_bm_day=$("#fxjchangeBmDay").val();
			var fxjchange_bm_num=$("#fxjchangeBmNum").val();
			if(fxjchange_bm_day.length!=0){
				msg+=fxjchange_bm_day+'日';
			}
			if(fxjchange_bm_num.length!=0){
				msg+=fxjchange_bm_num+'次.';
			}
			var fx_bm_huhuan=$("input[name='fxBmHuhuan']:checked").val();
			if(fx_bm_huhuan == 0){
				msg+='便秘与腹泻交替';
			}
			//量fx_bm_num  fx_bm_sum
			var fx_bm_num=$("#fxBmNum").val();
			var fx_bm_sum=$("#fxBmSum").val();
			if(fx_bm_num.length!=0){
				msg+=fxBmNum+'ml或g/次。';
			}
			if(fx_bm_sum.length!=0){
				msg+='总量'+fxBmSum+'ml或g/日。';
			}
			//颜色fx_bm_color
			var fx_bm_color=$("input[name='fxBmColor']:checked").val();
			if(fx_bm_color == 0){
				msg+='为黄色';
			}else if(fx_bm_color == 1){
				msg+='为金黄色';
			}else if(fx_bm_color == 2){
				msg+='为黑便';
			}else if(fx_bm_color == 3){
				msg+='为褐色';
			}else if(fx_bm_color == 4){
				msg+='为黄褐色';
			}else if(fx_bm_color == 5){
				msg+='为绿色';
			}else if(fx_bm_color == 6){
				msg+='为黄绿色';
			}else if(fx_bm_color == 7){
				msg+='为白陶土样';
			}else if(fx_bm_color == 8){
				msg+='为暗红色';
			}else if(fx_bm_color == 9){
				msg+='为鲜红色';
			}else if(fx_bm_color == 10){
				msg+='为柏油样';
			}else if(fx_bm_color == 11){
				msg+='为果酱样';
			}else if(fx_bm_color == 12){
				msg+='为米泔样';
			}else if(fx_bm_color == 13){
				msg+='为蛋花汤样';
			}else if(fx_bm_color == 14){
				msg+='为黄白色';
			}else if(fx_bm_color == 15){
				msg+='为红豆汤样';
			}
			//性状
			var fx_bm_xz=$("input[name='fxBmXz']:checked").val();
			if(fx_bm_xz == 0){
				msg+='成形软便,';
			}else if(fx_bm_xz == 1){
				msg+='不成形软便,';
			}else if(fx_bm_xz == 2){
				msg+='稀便,';
			}else if(fx_bm_xz == 3){
				msg+='糊状便,';
			}else if(fx_bm_xz == 4){
				msg+='水样便	,';
			}else if(fx_bm_xz == 5){
				msg+='粘液便,';
			}else if(fx_bm_xz == 6){
				msg+='稀水样,';
			}else if(fx_bm_xz == 7){
				msg+='稀汁样,';
			}else if(fx_bm_xz == 8){
				msg+='脓性便,';
			}else if(fx_bm_xz == 9){
				msg+='脓血便,';
			}else if(fx_bm_xz == 10){
				msg+='粘液脓血便,';
			}else if(fx_bm_xz == 11){
				msg+='干燥大便,';
			}else if(fx_bm_xz == 12){
				msg+='团块样大便,';
			}else if(fx_bm_xz == 13){
				msg+='细条样大便,';
			}else if(fx_bm_xz == 14){
				msg+='扁片样大便,';
			}else if(fx_bm_xz == 15){
				msg+='乳凝块,';
			}else if(fx_bm_xz == 16){
				msg+='洗肉水样,';
			}
			//
			var fx_bm_zz=$("input[name='fxBmZz']:checked").val();
			if(fx_bm_zz==0){
				msg+='伴里急后重,';
			}else if(fx_bm_zz==1){
				msg+='排便后腹痛好转,';
			}
			//气味异常
			var fx_bm_ex=$("input[name='fxBmEx']:checked").val();			
			if(fx_bm_ex == 0){
				$("#ft20_0").hide();
				$("#ft_20_0").hide();
			}else if(fx_bm_ex == 1){
				$("#ft20_0").show();
				$("#ft_20_0").show();
				var fx_bm_ex_parts=$("input[name='fxBmExParts']:checked").val();
				if(fx_bm_ex_parts == 0){
					msg+='有臭味,';
				}else if(fx_bm_ex_parts == 1){
					msg+='有恶臭,';
				}else if(fx_bm_ex_parts == 2){
					msg+='有血腥臭味,';
				}else if(fx_bm_ex_parts == 3){
					msg+='有酸臭味,';
				}
			}
			//是否有虫体fx_bm_thing
			var fx_bm_thing=$("input[name='fxBmThing']:checked").val();
			if(fx_bm_thing == 0){
				msg+='其内混有虫体。';
			}else if(fx_bm_thing == 1){
				msg+='其内混有结石。';
			}else if(fx_bm_thing == 2){
				msg+='其内混有膜状物。';
			}
		}
		//尿频
		var niaopin=$("input[name='niaopin']:checked").val();
		if(niaopin == 0){
			$("#tr21").hide();
			$("#niaopinDiv").hide();
		}else if(niaopin == 1){
			$("#tr21").show();
			$("#niaopinDiv").show();
			//niaopin_level
			var niaopin_level=$("input[name='niaopinLevel']:checked").val();
			if(niaopin_level == 0){
				msg+='有轻度尿频,';
			}else if(niaopin_level == 1){
				msg+='有中度尿频,';
			}else if(niaopin_level ==2){
				msg+='有重度尿频,';
			} 
			//诱因
			var niaopin_because=$("input[name='niaopinBecause']:checked").val();
			if(niaopin_because == 0){
				msg+='劳累,';
			}else if(niaopin_because == 1){
				msg+='受凉,';
			}else if(niaopin_because == 2){
				msg+='月经期,';
			}else if(niaopin_because == 3){
				msg+='接受导尿 尿路器械检查或流产术,';
			}else if(niaopin_because == 4){
				if($('#niaopinBecauseZdy').val().length!=0){
					msg+=$('#niaopinBecauseZdy').val()+',';
				}
			}
			//频度
			var niaopin_pl=$("input[name='niaopinPl']:checked").val();
			if(niaopin_pl == 0){
				if($("#niaopinPlZdy").val().length!=0){
					msg+=$("#niaopinPlZdy").val()+'次/天。';
				}
			}else if(niaopin_pl == 1){
				msg+='次数不详。';
			}else if(niaopin_pl == 2){
				msg+='次数较前增多	。';
			}
		}
		//尿急
		var niaoji=$("input[name='niaoji']:checked").val();
		if(niaoji == 0){
		
		}else if(niaoji == 1){
			msg+='轻度尿急,';
		}else if(niaoji == 2){
			msg+='中度尿急,';
		}else if(niaoji == 3){
			msg+='重度尿急,';
		}
		//尿痛
		var niaotong=$("input[name='niaotong']:checked").val();
		if(niaotong == 0){
			$("#tr22").hide();
			$("#niaotongDiv").hide();
			msg+='无尿痛,';
		}else if(niaotong == 1){
			$("#tr22").show();
			$("#niaotongDiv").show();
			//程度
			var niaotong_level=$("input[name='niaotongLevel']:checked").val();
			if(niaotong_level == 0){
				msg+='轻度尿痛,';
			}else if(niaotong_level == 1){
				msg+='中度尿痛,';
			}else if(niaotong_level == 2){
				msg+='重度尿痛,';
			}
			//时间
			var niaotong_time=$("input[name='niaotongTime']:checked").val();
			if(niaotong_time == 0){
				msg+='排尿时';
			}else if(niaotong_time==1){
				msg+='排尿完';			
			}
			//部位
			var niaotong_parts=$("input[name='niaotongParts']:checked").val();
			if(niaotong_parts == 0){
				msg+='出现在耻骨上区部位,';
			}else if(niaotong_parts == 1){
				msg+='出现在尿道内部位,';
			}else if(niaotong_parts == 2){
				msg+='出现在尿道口部位,';
			}else if(niaotong_parts == 3){
				msg+='出现在会阴部部位,';
			}else if(niaotong_parts == 4){
				msg+='出现在腹股沟部位,';
			}else if(niaotong_parts == 5){
				msg+='出现在睾丸部位,';
			}
		}
		//尿液改变
		var nyChange=$("input[name='nyChange']:checked").val();
		if(nyChange == 0){
			$("#tr23").hide();
			$("#nyChangeDiv").hide();
		}else if(nyChange == 1){
			$("#tr23").show();
			$("#nyChangeDiv").show();
			//尿量改变
			var nyChange_change=$("input[name='nyChangeChange']:checked").val();
			if(nyChange_change==0){
				msg+='尿量增多,';
			}else if(nyChange_change==1){
				msg+='尿量减少,';
			}else if(nyChange_change==2){
				msg+='少尿,';
			}else if(nyChange_change==3){
				msg+='无尿,';
			}else if(nyChange_change==4){
				if($("#nyChange_change_zdy").val().length!=0){
					msg+='量约：'+$("#nyChangeChangeZdy").val()+'ml/日，';
				}
			}
			//尿色异常
			var nyChange_ex=$("input[name='nyChangeEx']:checked").val();
			if(nyChange_ex == 0){
				$("#ft23_1").hide();
				$("#ft_23_1").hide();
			}else if(nyChange_ex == 1){
				$("#ft23_1").show();
				$("#ft_23_1").show();
				//
				var nyChange_color=$("input[name='nyChangeColor']:checked").val();
				if(nyChange_color == 0){
					msg+='为淡黄色';
				}else if(nyChange_color == 1){
					msg+='为深黄色';
				}else if(nyChange_color == 2){
					msg+='为洗肉水样';
				}else if(nyChange_color == 3){
					msg+='为酱油色';
				}else if(nyChange_color == 4){
					msg+='为血尿';
				}else if(nyChange_color == 5){
					msg+='为米汤样';
				}else if(nyChange_color == 6){
					msg+='为浓茶色';
				}else if(nyChange_color == 7){
					msg+='为混有血凝块';
				}else if(nyChange_color == 8){
					msg+='为豆油样';
				}else if(nyChange_color == 9){
					msg+='为白色';
				}else if(nyChange_color == 10){
					msg+='为尿后滴血';
				}else if(nyChange_color == 11){
					msg+='为泡沫样尿';
				}else if(nyChange_color == 12){
					msg+='为乳糜尿';
				}else if(nyChange_color == 13){
					msg+='为乳糜血尿';
				}
			}
			//清浊度
			var nyChange_du=$("input[name='nyChangeDu']:checked").val();
			if(nyChange_du == 0){
				msg+='澄清尿液,';
			}else{
				msg+='浑浊尿液,';
			}
			//气味异常
			var nyChange_weidao_ex=$("input[name='nyChangeWeidaoEx']:checked").val();
			if(nyChange_weidao_ex == 0){
				$("#ft23_2").hide();
				$("#ft_23_2").hide();
			}else if(nyChange_weidao_ex == 1){
				$("#ft23_2").show();
				$("#ft_23_2").show();
				//
				var nyChange_weidao_ex1=$("input[name='nyChangeWeidaoEx1']:checked").val();
				if(nyChange_weidao_ex1 == 0){
					msg+='有恶臭,';
				}else if(nyChange_weidao_ex1 == 1){
					msg+='有烂苹果味,';
				}else if(nyChange_weidao_ex1 == 2){
					msg+='有鼠尿味,';
				}else if(nyChange_weidao_ex1 == 3){
					msg+='有氨臭味,';
				}else{
					msg+='有蒜臭味,';
				}
			}
		}
		//水肿
		var sz=$("input[name='sz']:checked").val();
		if(sz==0){
			$("#tr24").hide();
			$("#szDiv").hide();
		}else if(sz==1){
			$("#tr24").show();
			$("#szDiv").show();
			//起病
			var sz_shibing=$("input[name='szShibing']:checked").val();
			if(sz_shibing == 0){
				msg+='起病为急性,';
			}else{
				msg+='起病为慢性,';
			}
			//部位
			var Check = '';
			var checkedObj = $("input[name='szParts']:checked");//获取当前checked的value值 如果选中多个则循环
			checkedObj.each(function(){
				var isCheck = this.value;
				if(isCheck == 0){
					Check+=$("#szPartsZdy").val()+',';
				}
				
					Check += isCheck+' ';
				
			});
			if(Check != ''){
				msg+=Check+',';
		    }	
		    else{
		    	msg+=$("input[name='szParts']").val()+',';
		    }
			//是否对称
			var sz_duichen=$("input[name='szDuichen']:checked").val();
			if(sz_duichen == 0){
				msg+='对称';
			}else{
				msg+='不对称';
			}
			//程度
			var sz_level=$("input[name='szLevel']:checked").val();
			if(sz_level == 0){
				msg+='轻度';
			}else if(sz_level == 1){
				msg+='中度';
			}else if(sz_level == 2){
				msg+='重度';
			}
			//sz_aotuxing
			var sz_aotuxing=$("input[name='szAotuxing']:checked").val();
			if(sz_aotuxing == 0){
				msg+='凹凸性水肿,';
			}else if(sz_aotuxing == 1){
				msg+='非凹凸性水肿,';
			}
			var sz_mingxian=$("#szMingxian").val();
			if(sz_mingxian!=''){
				msg+='多于'+sz_mingxian+'明显,';
			}
			var sz_jianqing=$("#szJianqing").val();
			if(sz_jianqing!=''){
				msg+='于'+sz_jianqing+'减轻,';
			}
			var sz_guanxi=$("#szGuanxi").val();
			if(sz_guanxi!=''){
				msg+='于'+sz_guanxi+'有明显关系。';
			}
		}
		//头痛
		var tt=$("input[name='tt']:checked").val();
		if(tt==0){
			$("#tr25").hide();
			$("#ttDiv").hide();
		}else if(tt==1){
			$("#tr25").show();
			$("#ttDiv").show();
			//诱因tt_because
			var tt_because=$("input[name='ttBecause']:checked").val();
			if(tt_because==0){
				$("#tr25_0").hide();
				$("#xt_25_0").hide();
			}else if(tt_because==1){
				$("#tr25_0").show();
				$("#xt_25_0").show();
				//tt_feature
				var tt_features=$("input[name='ttFeatures']:checked").val();
				if(tt_features == 0){
					msg+='精神紧张';
				}else if(tt_features == 1){
					msg+='劳累';
				}else if(tt_features == 2){
					msg+='受凉';
				}else if(tt_features == 3){
					msg+='生气';
				}else if(tt_features == 4){
					msg+=$('#ttFeaturesZdy').val();
				}
			}
			//频度
			var tt_pindu=$("input[name='ttPindu']:checked").val();
			if(tt_pindu == 0){
				msg+='间歇性';
			}else if(tt_pindu == 1){
				msg+='持续性';
			}else if(tt_pindu == 2){
				msg+='频繁';
			}else if(tt_pindu == 3){
				msg+='偶尔';
			}
			//程度
			
			var tt_level=$("input[name='ttLevel']:checked").val();
			if(tt_level == 0){
				msg+='出现轻度';
			}else if(tt_level == 1){
				msg+='出现中度';
			}else if(tt_level == 2){
				msg+='出现重度';
			}
			//部位			
			var tt_parts=$("input[name='ttParts']:checked").val();
			if(tt_parts == 0){
				msg+='单侧';
			}else if(tt_parts == 1){
				msg+='双侧';
			}else if(tt_parts == 2){
				msg+='前额';
			}else if(tt_parts == 3){
				msg+='枕部';
			}else if(tt_parts == 4){
				msg+='颞部';
			}else if(tt_parts == 5){
				msg+='局部';
			}else if(tt_parts == 6){
				msg+='弥散';
			}
			//性质
			var tt_feature=$("input[name='ttFeature']:checked").val();
				if(tt_feature == 0){
					msg+='搏动性头痛,';
				}else if(tt_feature == 1){
					msg+='电击样头痛,';
				}else if(tt_feature == 2){
					msg+='刺痛头痛,';
				}else if(tt_feature == 3){
					msg+='重压感头痛,';
				}else if(tt_feature == 4){
					msg+='紧箍感头痛,';
				}else if(tt_feature == 5){
					msg+='钳夹样头痛,';
				}else if(tt_feature == 6){
					msg+='隐痛头痛,';
				}else if(tt_feature == 7){
					msg+='钝痛头痛,';
				}else if(tt_feature == 8){
					msg+='胀痛头痛,';
				}else if(tt_feature == 9){
					msg+='酸痛头痛,';
				}else if(tt_feature == 10){
					msg+='烧灼样痛头痛,';
				}else if(tt_feature == 11){
					msg+='针刺样疼痛头痛,';
				}else if(tt_feature == 12){
					msg+='刀割样痛头痛,';
				}else if(tt_feature == 13){
					msg+='撕裂痛头痛,';
				}else if(tt_feature == 14){
					msg+='锐痛头痛,';
				}else if(tt_feature == 15){
					msg+='锥痛头痛,';
				}else if(tt_feature == 16){
					msg+='钻顶样痛头痛,';
				}else if(tt_feature == 17){
					msg+='绞痛头痛,';
				}else if(tt_feature == 18){
					msg+='绞榨样痛头痛,';
				}else if(tt_feature == 19){
					msg+='紧缩性痛头痛,';
				}else if(tt_feature == 20){
					msg+='紧箍感头痛,';
				}else if(tt_feature == 21){
					msg+='压迫痛头痛,';
				}else if(tt_feature == 22){
					msg+='串痛头痛,';
				}else if(tt_feature == 23){
					msg+='转移性疼痛头痛,';
				}else if(tt_feature == 24){
					msg+='搏动性痛头痛,';
				}else if(tt_feature == 25){
					msg+='电击样痛头痛,';
				}else if(tt_feature == 26){
					msg+='钳夹样痛头痛,';
				}else if(tt_feature == 27){
					var tt_feature_zdy=$("#ttFeatureZdy").val();
					if(tt_feature_zdy.length!=0){
						msg+=tt_feature_zdy+',';
					}
				}
				//规律
				var tt_guilv=$("#ttGuilv").val();
				if(tt_guilv!=''){
					msg+='多于'+tt_guilv+'明显,';
				}
				
				var tt_jianqing=$("#ttJianqing").val();
				if(tt_jianqing!=''){
					msg+='于'+tt_jianqing+'减轻,';
				}
				
				var tt_guanxi=$("#ttGuanxi").val();
				if(tt_guanxi!=''){
					msg+='于'+tt_guanxi+'有/无明显关系。';
				}
				
				var tt_yinsu1=$("#ttYinsu1").val();
				if(tt_yinsu1!=''){
					msg+='多于'+tt_yinsu1+'加重,';
				}
				var tt_yinsu2=$("#ttYinsu2").val();
				if(tt_yinsu2!=''){
					msg+='于'+tt_yinsu2+'减轻。';
				}
				
		}
		//神志改变
		var szChange=$("input[name='szChange']:checked").val();
		if(szChange==0){
			$("#tr26").hide();
			$("#szChangeDiv").hide();
		}else if(szChange==1){
			$("#tr26").show();
			$("#szChangeDiv").show();
			//程度
			var szChange_level=$("input[name='szChangeLevel']:checked").val();
			if(szChange_level==0){
				msg+='患者神志清楚,';
			}else if(szChange_level == 1){
				msg+='患者神志欠清,';
			}else if(szChange_level == 2){
				msg+='患者神志不清,';
			}else if(szChange_level == 3){
				msg+='患者嗜睡,';
			}else if(szChange_level == 4){
				msg+='患者意识模糊,';
			}else if(szChange_level == 5){
				msg+='患者昏睡,';
			}else if(szChange_level == 6){
				msg+='患者昏迷,';
			}else if(szChange_level == 7){
				msg+='患者言语杂乱,';
			}else if(szChange_level == 8){
				msg+='患者躁动不安,';
			}else if(szChange_level == 9){
				if($("#szChangeLevelZdy").val().length!=0){
					msg+=$("#szChangeLevelZdy").val()+',';
				}
			}
			//对答情况
			var szChange_answer=$("input[name='szChangeAnswer']:checked").val();
			if(szChange_answer == 0){
				msg+='呼之不应,';
			}else if(szChange_answer ==1){
				msg+='呼之能应,';
			}else if(szChange_answer ==2){
				msg+='回答切题,';
			}else if(szChange_answer ==3){
				msg+='答非所问,';
			}
			//szChange_xgChange
			var szChange_xgChange=$("input[name='szChangeXgChange']:checked").val();
			if(szChange_xgChange == 0){
				
			}else if(szChange_xgChange == 1){
				if($("#szChangeXgChangeZdy").val().length!=0){
					msg+=$("#szChangeXgChangeZdy").val()+',';
				}
			}
			//行为异常
			var szChange_xwEx=$("input[name='szChangeXwEx']:checked").val();
			if(szChange_xwEx == 0){
				msg+='无行为异常,';
			}else if(szChange_xwEx == 1){
				if($("#szChangeXwExZdy").val().length!=0){
					msg+=$("#szChangeXwExZdy").val()+',';
				}
			}
			//计算了
			var szChange_compute=$("input[name='szChangeCompute']:checked").val();
			if(szChange_compute == 0){
				msg+='计算了正常,';
			}else if(szChange_compute == 1){
				if($("#szChangeComputeZdy").val().length!=0){
					msg+=$("#szChangeComputeZdy").val()+',';
				}
			}
			//定向力
			var szChange_orientation=$("input[name='szChangeOrientation']:checked").val();
			if(szChange_orientation == 0){
				msg+='定向力正常,';
			}else if(szChange_orientation == 1){
				if($("#szChangeOrientationZdy").val().length!=0){
					msg+=$("#szChangeOrientationZdy").val()+',';
				}
			}
			//幻觉
			var szChange_huanjue=$("input[name='szChangeHuanjue']:checked").val();
			if(szChange_huanjue == 0){
				$("#tr26_0").hide();
				$("#xt_26_0").hide();
			}else if(szChange_huanjue == 1){
				$("#tr26_0").show();
				$("#xt_26_0").show();
				var szChange_huanjue1=$("input[name='szChangeHuanjue1']:checked").val();
				if(szChange_huanjue1 == 0){
					msg+='幻视,';
				}else if(szChange_huanjue1 == 1){
					msg+='幻听,';
				}else if(szChange_huanjue1==2){
					var szChange_huanjue1_zdy=$("#szChangeHuanjue1Zdy").val();
					if(szChange_huanjue1_zdy.length!=0){
						msg+=szChange_huanjue1_zdy+',';
					}
				}
			}
		}
		//抽搐及惊厥
		var ccJingjue=$("input[name='ccJingjue']:checked").val();
		if(ccJingjue == 0){
			$("#tr27").hide();
			$("#ccJingjueDiv").hide();
		}else if(ccJingjue == 1){
			$("#tr27").show();
			$("#ccJingjueDiv").show();
			//部位
			var ccJingjue_parts=$("input[name='ccJingjueParts']:checked").val();
			if(ccJingjue_parts ==0){
				msg+='全身性';
			}else if(ccJingjue_parts == 1){
				msg+='局限性';
			}else if(ccJingjue_parts == 2){
				msg+='角';
			}else if(ccJingjue_parts == 3){
				msg+='眼睑';
			}else if(ccJingjue_parts == 4){
				msg+='手足';
			}else if(ccJingjue_parts == 5){
				msg+='上肢';
			}else if(ccJingjue_parts == 6){
				msg+='下肢';
			}else if(ccJingjue_parts == 7){
				msg+='左';
			}else if(ccJingjue_parts == 8){
				msg+='右';
			}else if(ccJingjue_parts == 9){
				msg+='双';
			}else if(ccJingjue_parts == 10){
				msg+='对称';
			}else if(ccJingjue_parts == 11){
				msg+='不对称';
			}
			//
			var ccJingjue_statue=$("input[name='ccJingjueStatue']:checked").val();
			if(ccJingjue_statue == 0){
				msg+='抽搐,';
			}else if(ccJingjue_statue == 1){
				msg+='惊厥,';
			}
			//伴随症状
			var ccJingjue_bansui=$("input[name='ccJingjueBansui']:checked").val();
			if(ccJingjue_bansui == 0){
				msg+='意识模糊,';
			}else if(ccJingjue_bansui == 1){
				msg+='意识丧失,';
			}else if(ccJingjue_bansui == 2){
				msg+='呼吸暂停,';
			}else if(ccJingjue_bansui == 3){
				msg+='尿便失控,';
			}else if(ccJingjue_bansui == 4){
				if($("#ccJingjueBansuiZdy").val().length!=0){
					msg+=$("#ccJingjueBansuiZdy").val()+',';
				}
			}
			//发作时间
			var ccJingjue_time=$("input[name='ccJingjueTime']:checked").val();
			if(ccJingjue_time == 0){
				msg+='反复发作,';
			}else if(ccJingjue_time == 1){
				msg+='持续,';
			}else if(ccJingjue_time == 2){
				if($("#ccJingjueBansuiZdy").val().length!=0){
					msg+='持续约'+$("#ccJingjueBansuiZdy").val()+$("#ccJingjueTimeValue").val()+'。';
				}				
			}			
		}
		//腰痛
		var yaotong=$("input[name='yaotong']:checked").val();
		if(yaotong == 0){
			$("#tr28").hide();
			$("#yaotongDiv").hide();
		}else if(yaotong == 1){
			$("#tr28").show();
			$("#yaotongDiv").show();
			//诱因
			var yaotong_because=$("input[name='yaotongBecause']:checked").val();
			if(yaotong_because == 0){
				msg+='外伤后';
			}else if(yaotong_because == 1){
				msg+='搬重物后';
			}else if(yaotong_because == 2){
				msg+='潮湿后';
			}else if(yaotong_because == 3){
				msg+='寒冷后';
			}else if(yaotong_because == 4){
				msg+='扭伤后';
			}else if(yaotong_because == 5){
				msg+='过度活动后';
			}else if(yaotong_because == 6){
				if($("#yaotongBecauseZdy").val().length!=0){
					msg+=$("#yaotongBecauseZdy").val()+',';
				}
			}
			//发作状态yaotong_statue
			var yaotong_statue=$("input[name='yaotongStatue']:checked").val();
			if(yaotong_statue == 0){
				msg+='急性';
			}else if(yaotong_statue == 1){
				msg+='慢性';
			}else if(yaotong_statue == 2){
				msg+='持续性';
			}else if(yaotong_statue == 3){
				msg+='阵发性';
			}else if(yaotong_statue == 4){
				msg+='偶尔';
			}else if(yaotong_statue == 5){
				msg+='反复发作';
			}else if(yaotong_statue == 6){
				msg+='间断发作';
			}else if(yaotong_statue == 7){
				msg+='逐渐加重';
			}else if(yaotong_statue == 8){
				msg+='逐渐减轻';
			}else if(yaotong_statue == 9){
				msg+='加剧';
			}else if(yaotong_statue == 10){
				msg+='减轻';
			}else if(yaotong_statue == 11){
				msg+='突然出现';
			}else if(yaotong_statue == 12){
				if($('#yaotongStatueZdy').val().length!=0){
					msg+=$('#yaotongStatueZdy').val();
				}				
			}
			//程度
			var yaotong_level=$("input[name='yaotongLevel']:checked").val();
			if(yaotong_level == 0){
				msg+='轻度';
			}else if(yaotong_level == 1){
				msg+='中度';
			}else if(yaotong_level == 2){
				msg+='重度';
			}else if(yaotong_level == 3){
				msg+='剧烈';
			}else if(yaotong_level == 4){
				msg+='时轻时重';
			}
			//部位
			var yaotong_parts=$("input[name='yaotongParts']:checked").val();
			if(yaotong_parts == 0){
				msg+='腰部';
			}else if(yaotong_parts == 1){
				msg+='背部';
			}else if(yaotong_parts == 2){
				msg+='骶部';
			}else if(yaotong_parts == 3){
				msg+='腰骶部';
			}else if(yaotong_parts == 4){
				msg+='腰背部';
			}else if(yaotong_parts == 5){
				msg+='左侧';
			}else if(yaotong_parts == 6){
				msg+='右侧';
			}else if(yaotong_parts == 7){
				msg+='双侧';
			}else if(yaotong_parts == 8){
					msg+=$("#yaotongPartsZdy").val();	
			}
			//性质
			//性质
			var yaotong_feature=$("input[name='yaotongFeature']:checked").val();
				if(yaotong_feature == 0){
					msg+='搏动性,';
				}else if(yaotong_feature == 1){
					msg+='电击样,';
				}else if(yaotong_feature == 2){
					msg+='刺痛,';
				}else if(yaotong_feature == 3){
					msg+='重压感,';
				}else if(yaotong_feature == 4){
					msg+='紧箍感,';
				}else if(yaotong_feature == 5){
					msg+='钳夹样,';
				}else if(yaotong_feature == 6){
					msg+='隐痛,';
				}else if(yaotong_feature == 7){
					msg+='钝痛,';
				}else if(yaotong_feature == 8){
					msg+='胀痛,';
				}else if(yaotong_feature == 9){
					msg+='酸痛,';
				}else if(yaotong_feature == 10){
					msg+='烧灼样痛,';
				}else if(yaotong_feature == 11){
					msg+='针刺样疼痛,';
				}else if(yaotong_feature == 12){
					msg+='刀割样痛,';
				}else if(yaotong_feature == 13){
					msg+='撕裂痛,';
				}else if(yaotong_feature == 14){
					msg+='锐痛,';
				}else if(yaotong_feature == 15){
					msg+='锥痛,';
				}else if(yaotong_feature == 16){
					msg+='钻顶样痛,';
				}else if(yaotong_feature == 17){
					msg+='绞痛,';
				}else if(yaotong_feature == 18){
					msg+='绞榨样痛,';
				}else if(yaotong_feature == 19){
					msg+='紧缩性痛,';
				}else if(yaotong_feature == 20){
					msg+='转移性疼痛,';
				}else if(yaotong_feature == 21){
					msg+='压迫痛,';
				}else if(yaotong_feature == 22){
					msg+='串痛,';
				}else if(yaotong_feature == 23){
					msg+='转移性疼痛,';
				}else if(yaotong_feature == 24){
					msg+='搏动性痛,';
				}else if(yaotong_feature == 25){
					msg+='电击样痛,';
				}else if(yaotong_feature == 26){
					msg+='钳夹样痛,';
				}else if(yaotong_feature == 27){
					var yaotong_feature_zdy=$("#yaotongFeatureZdy").val();
					if(yaotong_feature_zdy.length!=0){
						msg+=yaotong_feature_zdy+',';
					}
				}
			//位置
			var yaotong_weizhi=$("input[name='yaotongWeizhi']:checked").val();
			if(yaotong_weizhi == 0){
				msg+='位置固定,';
			}else if(yaotong_weizhi == 1){
				msg+='位置不固定,';
			}else if(yaotong_weizhi == 2){
				msg+='位置对称,';
			}else if(yaotong_weizhi == 3){
				msg+='位置不对称,';
			}
			//发作特点
			var yaotong_statues=$("input[name='yaotongStatues']:checked").val();
			if(yaotong_statues == 0){
				msg+='持续性发作,';
			}else if(yaotong_statues == 1){
				msg+='阵发性发作,';
			}else if(yaotong_statues == 2){
				msg+='偶尔发作,';
			}
			//伴随症状
			var yaotong_bansui=$("input[name='yaotongBansui']:checked").val();
			if(yaotong_bansui == 0){
				$("#tr28_0").hide();
				$("#xt_28_0").hide();
			}else if(yaotong_bansui == 1){
				$("#tr28_0").show();
				$("#xt_28_0").show();
				//伴随
				var yaotong_bansui1=$("input[name='yaotongBansui1']:checked").val();
				if(yaotong_bansui1 == 0){
					msg+='伴随活动障碍,';
				}else if(yaotong_bansui1 == 1){
					msg+='伴随感觉障碍	,';
				}else if(yaotong_bansui1 == 2){
					msg+='伴随僵直,';
				}else if(yaotong_bansui1 == 3){
					msg+='伴随间歇跛行,';
				}else if(yaotong_bansui1 == 4){
					msg+='伴随压痛,';
				}else if(yaotong_bansui1 == 5){
					msg+='伴随扣痛,';
				}else if(yaotong_bansui1 == 6){
					msg+='伴随排尿困难,';
				}else if(yaotong_bansui1 == 7){
					msg+='伴随下腹坠胀感,';
				}else if(yaotong_bansui1 == 8){
					msg+='伴随痛经,';
				}else if(yaotong_bansui1 == 9){
					msg+='伴随白带过多	,';
				}else if(yaotong_bansui1 == 10){
					if($("#yaotongBansui1Zdy").val().length!=0){
						msg+=$("#yaotongBansui1Zdy").val()+',';
					}
				}
			}
			
			//加重及缓解因素  yaotong_jianqing
			var yaotong_jiazhong=$("#yaotongJiazhong").val();
			if(yaotong_jiazhong!=''){
				msg+=yaotong_jiazhong+'加重,';
			}
			var yaotong_jianqing=$("#yaotongJianqing").val();
			if(yaotong_jianqing!=''){
				msg+=yaotong_jianqing+'减轻。';
			}
		}
		//肌肉关节痛
		var jrgjt=$("input[name='jrgjt']:checked").val();
		if(jrgjt == 0){
			$("#tr29").hide();
			$("#jrgjtDiv").hide();
		}else if(jrgjt == 1){
			$("#tr29").show();
			$("#jrgjtDiv").show();
			//诱因
			var jrgjt_because=$("input[name='jrgjtBecause']:checked").val();
			if(jrgjt_because == 0){
				msg+='外伤后';
			}else if(jrgjt_because == 1){
				msg+='搬重物后';
			}else if(jrgjt_because == 2){
				msg+='潮湿后';
			}else if(jrgjt_because == 3){
				msg+='寒冷后';
			}else if(jrgjt_because == 4){
				msg+='扭伤后';
			}else if(jrgjt_because == 5){
				msg+='过度活动后';
			}else if(jrgjt_because == 6){
				if($("#jrgjtBecauseZdy").val().length!=0){
					msg+=$("#jrgjtBecauseZdy").val()+',';
				}
			}
			//发作状态
			var jrgjt_statue=$("input[name='jrgjtStatue']:checked").val();
			if(jrgjt_statue == 0){
				msg+='急性';
			}else if(jrgjt_statue == 1){
				msg+='慢性';
			}else if(jrgjt_statue == 2){
				msg+='持续性';
			}else if(jrgjt_statue == 3){
				msg+='阵发性';
			}else if(jrgjt_statue == 4){
				msg+='偶尔';
			}else if(jrgjt_statue == 5){
				msg+='反复发作';
			}else if(jrgjt_statue == 6){
				msg+='间断发作';
			}else if(jrgjt_statue == 7){
				msg+='逐渐加重';
			}else if(jrgjt_statue == 8){
				msg+='逐渐减轻';
			}else if(jrgjt_statue == 9){
				msg+='加剧';
			}else if(jrgjt_statue == 10){
				msg+='减轻';
			}else if(jrgjt_statue == 11){
				msg+='突然出现';
			}else if(jrgjt_statue == 12){
				if($('#jrgjtStatueZdy').val().length!=0){
					msg+=$('#jrgjtStatueZdy').val();
				}				
			}
			//程度
			var jrgjt_level=$("input[name='jrgjtLevel']:checked").val();
			if(jrgjt_level == 0){
				msg+='轻度';
			}else if(jrgjt_level == 1){
				msg+='中度';
			}else if(jrgjt_level == 2){
				msg+='重度';
			}else if(jrgjt_level == 3){
				msg+='剧烈';
			}else if(jrgjt_level == 4){
				msg+='时轻时重';
			}else if(jrgjt_level == 5){
				msg+='减轻';
			}else if(jrgjt_level == 6){
				msg+='加重';
			}else if(jrgjt_level == 7){
				msg+='逐渐减轻';
			}else if(jrgjt_level == 8){
				msg+='逐渐加重';
			}
			else if(jrgjt_level == 10){
				msg+=$("#jrgjtLevelValue").val();
			}
			//部位
			var jrgjt_parts=$("input[name='jrgjtParts']:checked").val();
			if(jrgjt_parts == 0){
				msg+='左侧';
			}else if(jrgjt_parts == 1){
				msg+='右侧';
			}else if(jrgjt_parts == 2){
				msg+='双侧';
			}else if(jrgjt_parts == 3){
				msg+='对称';
			}else if(jrgjt_parts == 4){
				msg+='不对称';
			}else if(jrgjt_parts == 5){
				msg+='全身关节';
			}else if(jrgjt_parts == 6){
				msg+='四肢大关节';
			}else if(jrgjt_parts == 7){
				msg+='小关节';
			}else if(jrgjt_parts == 8){
				msg+='肩关节';
			}else if(jrgjt_parts == 9){
				msg+='肘关节';
			}else if(jrgjt_parts == 10){
				msg+='腕关节';
			}else if(jrgjt_parts == 11){
				msg+='掌指关节';
			}else if(jrgjt_parts == 12){
				msg+='髋关节';
			}else if(jrgjt_parts == 13){
				msg+='膝关节';
			}else if(jrgjt_parts == 14){
				msg+='踝关节';
			}else if(jrgjt_parts == 15){
				msg+='趾关节';
			}else if(jrgjt_parts == 16){
				msg+='脊柱';
			}else if(jrgjt_parts == 17){
				msg+='颈部';
			}else if(jrgjt_parts == 18){
				msg+='胸部';
			}else if(jrgjt_parts == 19){
				msg+='腰部';
			}else if(jrgjt_parts == 20){
				msg+='骶部';
			}else if(jrgjt_parts == 21){
					msg+=$("#jrgjtPartsValue").val();	
			}
			//性质
			var jrgjt_feature=$("input[name='jrgjtFeature']:checked").val();
				if(jrgjt_feature == 0){
					msg+='搏动性,';
				}else if(jrgjt_feature == 1){
					msg+='电击样,';
				}else if(jrgjt_feature == 2){
					msg+='刺痛,';
				}else if(jrgjt_feature == 3){
					msg+='重压感,';
				}else if(jrgjt_feature == 4){
					msg+='紧箍感,';
				}else if(jrgjt_feature == 5){
					msg+='钳夹样,';
				}else if(jrgjt_feature == 6){
					msg+='隐痛,';
				}else if(jrgjt_feature == 7){
					msg+='钝痛,';
				}else if(jrgjt_feature == 8){
					msg+='胀痛,';
				}else if(jrgjt_feature == 9){
					msg+='酸痛,';
				}else if(jrgjt_feature == 10){
					msg+='烧灼样痛,';
				}else if(jrgjt_feature == 11){
					msg+='针刺样疼痛,';
				}else if(jrgjt_feature == 12){
					msg+='刀割样痛,';
				}else if(jrgjt_feature == 13){
					msg+='撕裂痛,';
				}else if(jrgjt_feature == 14){
					msg+='锐痛,';
				}else if(jrgjt_feature == 15){
					msg+='锥痛,';
				}else if(jrgjt_feature == 16){
					msg+='钻顶样痛,';
				}else if(jrgjt_feature == 17){
					msg+='绞痛,';
				}else if(jrgjt_feature == 18){
					msg+='绞榨样痛,';
				}else if(jrgjt_feature == 19){
					msg+='紧缩性痛,';
				}else if(jrgjt_feature == 20){
					msg+='转移性疼痛,';
				}else if(jrgjt_feature == 21){
					msg+='压迫痛,';
				}else if(jrgjt_feature == 22){
					msg+='串痛,';
				}else if(jrgjt_feature == 23){
					msg+='转移性疼痛,';
				}else if(jrgjt_feature == 24){
					msg+='搏动性痛,';
				}else if(jrgjt_feature == 25){
					msg+='电击样痛,';
				}else if(jrgjt_feature == 26){
					msg+='钳夹样痛,';
				}else if(jrgjt_feature == 27){
					var jrgjt_feature_zdy=$("#jrgjtFeatureZdy").val();
					if(jrgjt_feature_zdy.length!=0){
						msg+=jrgjt_feature_zdy+',';
					}
				}
			//位置
			var jrgjt_weizhi=$("input[name='jrgjtWeizhi']:checked").val();
			if(jrgjt_weizhi == 0){
				msg+='位置固定,';
			}else if(jrgjt_weizhi == 1){
				msg+='位置不固定,';
			}else if(jrgjt_weizhi == 2){
				msg+='位置对称,';
			}else if(jrgjt_weizhi == 3){
				msg+='位置不对称,';
			}else if(jrgjt_weizhi == 4){
				msg+='位置游走性,';
			}else if(jrgjt_weizhi == 5){
				msg+='位置描述不清,';
			}else if(jrgjt_weizhi == 6){
				if($('#jrgjtWeizhiZdy').val().length!=0){
					msg+=$('#jrgjtWeizhiZdy').val()+',';
				}
				
			}   
			//发作特点
			var jrgjt_statues=$("input[name='jrgjtStatues']:checked").val();
			if(jrgjt_statues == 0){
				msg+='持续性发作,';
			}else if(jrgjt_statues == 1){
				msg+='阵发性发作,';
			}else if(jrgjt_statues == 2){
				msg+='偶尔发作,';
			}
			//伴随症状
			var jrgjt_bansui=$("input[name='jrgjtBansui']:checked").val();
			if(jrgjt_bansui == 0){
				$("#tr29_0").hide();
				$("#xt_29_0").hide();
			}else if(jrgjt_bansui == 1){
				$("#tr29_0").show();
				$("#xt_29_0").show();
				//伴随
				var jrgjt_bansui1=$("input[name='jrgjtBansui1']:checked").val();
				if(jrgjt_bansui1 == 0){
					msg+='伴随肿胀,';
				}else if(jrgjt_bansui1 == 1){
					msg+='伴随红肿,';
				}else if(jrgjt_bansui1 == 2){
					msg+='伴随灼热,';
				}else if(jrgjt_bansui1 == 3){
					msg+='伴随活动障碍,';
				}else if(jrgjt_bansui1 == 4){
					msg+='伴随僵直,';
				}else if(jrgjt_bansui1 == 5){
					msg+='伴随关节畸形,';
				}else if(jrgjt_bansui1 == 6){
					msg+='伴随感觉异常,';
				}else if(jrgjt_bansui1 == 7){
					msg+='伴随跛行,';
				}else if(jrgjt_bansui1 == 8){
					if($("#jrgjtBansui1Zdy").val().length!=0){
						msg+=$("#jrgjtBansui1Zdy").val()+',';
					}
				}
			}
			//  
			//加重及缓解因素  
			var jrgjt_jiazhong=$("#jrgjtJiazhong").val();
			if(jrgjt_jiazhong!=''){
				msg+=jrgjt_jiazhong+'加重,';
			}
			var jrgjt_jianqing=$("#jrgjtJianqing").val();
			if(jrgjt_jianqing!=''){
				msg+=jrgjt_jianqing+'减轻。';
			}
		}
		$("#newContent").html(msg);
		return msg;
	}