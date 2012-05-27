	function setPageValue(_id){
		$.post(
			App.App_Info.BasePath+'/InHospitalCase/Fever.do',
			{
				method:'FindPreByCaseId',
				id:_id
			},
			function(data){
				if(data.success){
					var _json=JSON.parse(data.data);
					if(_json){
						FormUtil.setFormValues('form',_json);
						/*发病途径赋值*/
						$('select[name="predisposingfactors"]').val(_json['predisposingfactors']);
						var o = _json['predisposingfactors'];
						/*发病途径效果*/
						if(o==1){
							$('select[name="predisposingfactors"]').parent().parent().next().show();
						}else{
							$('select[name="predisposingfactors"]').parent().parent().next().hide();
						}
						$('input[type="checkbox"]').each(function(){
							if(_json[this.name]==1){
								$(this).attr('checked',true);
								if($(this).attr('class')=='noActive'){
								}
								else{
									$(this).parent().parent().next().show();
								}
							}
						});
					}
				}else{
					alert('页面赋值错误。');
				}
			},
			'json'
		);
	}
	
	function getPageValues(){
		alert('值！！！');
		var json=FormUtil.getFormValues('form');
		$('input[type="checkbox"]').each(function(){
			if(this.checked){
				json[this.name]=1;
			}else{
				json[this.name]=0;
			}
		});
		var composeContext = ComposeData(json);
		json.composeContent = composeContext;
		return json;
	}
	
	function ComposeData(json){
		var _rst = '';
		var _temp='    患者于';
		
		if(json.illTime.length>0){
			_temp += json.illTime + getRowValue('illTimeUnit',json.illTimeUnit);
		}
		
		var p = $('select[name="predisposingfactors"]').val();
		if(p==1){
			if(json.causes.length>0){
				_temp += '因'+json.causes + '，出现';
			}
			json.predisposingfactors=1;
		}else if(p==2){
			var p_v= '体检时发现';
			_temp += p_v;
			json.predisposingfactors=2;
		}else if(p==0){
			var p_v= '无明显诱因出现';
			_temp += p_v;
			json.predisposingfactors=0;
		}else{
			json.predisposingfactors='';
		}
		/*主要症状*/
		var _va = mainSymptom(json);
		_temp += _va.length > 0 ? _va : '';
		
		_va = Symptom(json);
		_temp += _va.length >0 ? _va : '';
		
		if(json.evolution==99){
			if(json.evolution0.length>0)
				_temp += json.evolution0+'，';
		}else{
			var str = getRowValue('evolution',json.evolution);
			if(str.length>0)
				_temp+=str+'，';
		}
		if(json.seeDoct==99){
			if(json.seeDoct0.length>0)
				_temp+=json.seeDoct0+'，';
		}else{
			var str = getRowValue('seeDoct',json.seeDoct);
			if(str.length>0)
				_temp+=str+'，';
		}
		if(json.examination.length>0){
			_temp+=json.examination+'，';
		}
		if(json.diagnosis==99){
			if(json.diagnosis0.length>0)
				_temp+=json.diagnosis0+'，';
		}else{
			var str = getRowValue('diagnosis',json.diagnosis);
			if(str.length>0)
				_temp+=str+'，';
		}
		if(json.treatment.length > 0){
			_temp+=json.treatment+'，';
		}
		
		_rst = _temp.length>0 ? _temp.substr(0,_temp.length-1)+'。\n' : '';
		return _rst;
	}
	
	/*获取纯checkbox值*/
	function getCheckbox(_t,_v){
		var _te = '';
		if(_t==1){
			_te = _v = '、';
		}else {
			_te = '';
		}
		return  _te;
	}
	
	/*主要/伴随症状_主要症状*/
	function mainSymptom(json){
		var _temp = '';
		var _tV = '';
		var _t = '';
		var rst = '';
		_temp = json.cardinalSymptom;
		if(_temp == 1){
			rst += getCheckbox(json.fali,'乏力');
			rst += getCheckbox(json.fansuan,'反酸');
			rst += getCheckbox(json.yanyou,'厌油');
			
			rst += getCheckbox(json.tunyan,'吞咽困难');
			rst += getCheckbox(json.exin,'恶心');
			rst += getCheckbox(json.xiaohua,'消化不良');
		
			rst += getCheckbox(json.aiqi,'嗳气');
			rst += getCheckbox(json.fuzhang,'腹胀');
			rst += getCheckbox(json.shiyu,'食欲减退');
			
			rst += getCheckbox(json.dapentiFlag,'打喷嚏');
			rst += getCheckbox(json.biseFlag,'鼻塞');
			rst += getCheckbox(json.yantongFlag,'咽痛');
			
			rst += getCheckbox(json.htFlag,'喉痛');
			rst += getCheckbox(json.qiduanFlag,'气短');
			rst += getCheckbox(json.ttFlag,'头痛');
			
			rst += getCheckbox(json.biniuFlag,'鼻衄');
			rst += getCheckbox(json.yjfhFlag,'眼睛发红');
			rst += getCheckbox(json.wgllFlag,'畏光/流泪');
			
			rst += getCheckbox(json.ouxue,'呕血');
			rst += getCheckbox(json.hemorrhinia,'鼻出血');
			rst += getCheckbox(json.stethalgia,'胸痛');
			
			rst += getCheckbox(json.dyspnea,'呼吸困难');
			rst += getCheckbox(json.cyanopathy,'发绀');
			rst += getCheckbox(json.tetter,'皮疹');
			
			rst += getCheckbox(json.petechia,'出血点');
			rst += getCheckbox(json.fbbushiFlag,'腹部不适');
			rst += getCheckbox(json.dabian,'大便异常');
			
			rst += getCheckbox(json.dysaudia,'听力障碍');
			rst += getCheckbox(json.zhijuezhanai,'知觉障碍');
			rst += getCheckbox(json.yundongzhanai,'运动障碍');
			
			rst += getCheckbox(json.chouchu,'抽搐');
			rst += getCheckbox(json.jingjue,'惊厥');
			rst += getCheckbox(json.zhanwang,'谵妄');
			
			rst += getCheckbox(json.kuangzao,'狂躁不安');
			rst += getCheckbox(json.shishui,'嗜睡');
			rst += getCheckbox(json.hunmi,'昏迷');
			
			rst +=rst.substr(0,rst.length-1)+'，';
			
			_t = json.shishao;
			if(_t==1){
				rst='食少、';
				_tV = getRowValue('shishao_performance',json.shishao_performance);
				rst += _tV.length >0 ? (_tV+'、'):'';
				_tV = getRowValue('shishao_reduce',json.shishao_reduce);
				rst+= temp.length>0?('减少至正常食量的'+temp+'、'):'';
				rst=rst.substr(0,rst.length-1)+'，';
			}else{
				rst +='食少，';
			}
			
			_t = json.inappetence;
			if(_t==1){
				rst +='食欲不振、';
				_tV = getRowValue('it_performance',json.it_performance);
				rst += _tV.length >0 ? (_tV+'、'):'';
				_tV = getRowValue('it_reduce',json.it_reduce);
				rst+= _tV.length>0?('减少至正常食量的'+temp+'、'):'';
				rst=rst.substr(0,rst.length-1)+'，';
			}else {
				rst +='食欲不振，';
			}
			_t = json.outu;
			if(_t==1){
				rst +='呕吐、';
				_tV = getRowValue('outu_shape',json.outu_shape);
				rst+=_tV.length>0?(_tV+'、'):'';
				_tV=getRowValue('outu_thing',json.outu_thing);
				rst+=_tV.length>0?('呕吐物为'+_tV+'、'):'';
				_tV=json.outu_countDay;
				rst+=_tV.length>0?('每天'+_tV+'次、'):'';
				_tV=json.outu_volumeDay;
				rst+=_tV.length>0?('每次'+_tV+'ml、'):'';
				_tV=json.outu_countTotal;
				rst+=_tV.length>0?('总共'+_tV+'次、'):'';
				_tV=json.outu_volumeTotal;
				rst+=_tV.length>0?('总量'+_tV+'ml、'):'';
				rst += rst.substr(0,rst.length-1)+'，';
			}else{
				rst +='呕吐，';
			}
			_t = json.futong;
			if(_t==1){
				rst +='腹痛、';
				var t_tV=getRowValue('futong_position',json.futong_position);
				t_tV+=getRowValue('futong_performance',json.futong_performance);
				rst+=t_tV.length>0?(t_tV+'、'):'';
				_tV=getRowValue('futong_rate',json.futong_rate);
				rst+=_tV.length>0?(_tV+'、'):'';
				_tV=getRowValue('futong_change',json.futong_change);
				rst+=_tV.length>0?('病情'+_tV+'、'):'';
				rst +=rst.substr(0,rst.length-1)+'，';
			}else{
				rst +='腹痛，';
			}
			_t = json.niaoye;
			if(_t==1){
				_tV =getRowValue('niaoye_color',json.niaoye_color);
				rst+= _tV.length>0?(_tV+'、'):'';
				_tV= getRowValue('niaoye_volume',json.niaoye_volume);
				var total = json.niaoye_volumeTotal;
				if(_tV=='无变化'){
					rst+='尿量无变化、';
					rst+=total.length>0?('总量为'+total+'ml/天、'):'';
				}else if(_tV=='多尿'){
					rst+=total.length>0?('尿量增加至'+total+'ml/天、'):'多尿、';
				}else if(_tV=='少尿'){
					rst+=total.length>0?('尿量减少至'+total+'ml/天、'):'少尿、';
				}else if(_tV=='无尿'){
					rst+='无尿、';
				}
				rst= rst.length>0?(rst.substr(0,rst.length-1)+'，'):'小便异常，';
			}else{
				rst +='尿液改变，';
			}
			_t = json.xiazhi;
			if(_t==1){
				_tV = getRowValue('xiazhi_position',json.xiazhi_position);
				var t_tV=getRowValue('xiazhi_degree',json.xiazhi_degree);
				if(t_tV.length>0){
					rst += _tV + t_tV+'，';
				}else{
					if(_tV.length>0){
						rst=_tV+'浮肿，'
					}else{
						rst +='下肢浮肿';
					}
				}
			}else{
				rst +='下肢浮肿，';
			}
			_t = json.fare;
			if(_t==1){
				rst +='发热、';
				_tV = getRowValue('fare_type',json.fare_type);
				rst+=_tV.length>0?(_tV+'、'):'';
				_tV = json.fare_topt;
				rst += _tV.length>0?('最高体温'+_tV+'℃、'):'';
				_tV= getRowValue('fare_performance',json.fare_performance);
				_tv = json.fare_durationTime;
				rst += _tv.length>0 ? '持续' + _tv : '';
				rst+=_tV.length>0?('呈'+_tV+'、') : '';
				_tV= getRowValue('fare_side',json.fare_side);
				rst +=_tV.length>0?(_tV+'、'):'';
				rst +=rst.substr(0,rst.length-1)+'，';
			}else{
				rst +='发热，';
			}
			_t = json.huangran;
			if(value==1){
				_tV = getRowValue('huangran_degree',json.huangran_degree);
				var t_tV= getRowValue('huangran_performance',json.huangran_performance);
				if(t_tV.length==0){
					rst=_tV+'黄染，';
				}else{
					rst=_tV+t_tV+'，';
				}
			}else{
				rst +='黄染，';
			}
			_t = json.shenzhi;
			if(_t==1){
				var a =getRowValue('shenzhi_performance',json.shenzhi_performance);
				rst += a.length > 0?(a+'、'):'';
				_tV = getRowValue('shenzhi_answer',json.shenzhi_answer);
				rst += temp.length>0?(temp+'、'):'';
				rst += rst.length>0?(rst.substr(0,rst.length-1)+'，'):'神志改变，';
			}else{
				rst +='神志改变，';
			}
			_t = json.xingwei;
			if(value==1){
				_tV= getRowValue('xingwei_performance',json.xingwei_performance);
				rst +=_tV.length>0?_tV+'，':'行为异常，';
			}else{
				rst +='行为异常，';
			}
			_t = json.liutiFlag;
			if(_t == 1){
				_tV = getRowValue('liutiXZH',json.liutiXZH);
				rst += _tV.length>0 ? _tV +'，': '';
			}
			_t = json.kesFlag;
			if(_t == 1){
				_tV = getRowValue('kesXZH',json.kesXZH);
				rst += _tv.length>0 ?_tV +'、':'';
				_tV = getRowValue('kesPinDu',json.kesPinDu);
				rst += _tv.length>0 ?_tV +'':'';
				rst += '咳嗽';
			}
			_t = json.ketFlag;
			if(_t==1){
				_tV = getRowValue('ketQW',json.ketQW);
				rst += _tV.length >0 ?_tV +'气味' : '';
				_tV = getRowValue('ketTL',json.ketTL);
				rst += _tV.length >0 ?_tV +'、' : '';
				_tV = getRowValue('ketXZH',json.ketXZH);
				rst +=_tV.length >0 ?_tV +'、' : '';
				rst +=rst.substr(0,rst.length-1)+'，';
			}
			_t = json.jrstFlag;
			if(_t==1){
				_tV = getRowValue('jrstXZH',json.jrstXZH);
				rst += _tV.length > 0 ? '肌肉酸痛、' + _tV + '，':'';
			}
			_t = json.stttFlag;
			if(_t == 1){
				_tV =  getRowValue('stttWZH',json.stttWZH);
				rst += _tV.length > 0 ? _tV + '疼痛' +'，' : '';
			}
		}
		return rst.length>0?rst.substr(0,rst.length-1)+'。':'';
	}
	
	/*主要/伴随症状_伴随症状*/
	function Symptom(json){
		var _compose=json;
		var rst='';
		var _flag='';
		var tempValue=_compose.side_xiaohua;
		rst+=tempValue.length>0?tempValue+'，':'';
		_flag=_compose.side_xunhuan;
		if(_flag==0){
			rst+='无胸痛、胸闷、心悸，';
		}else if(_flag==1){
			tempValue=_compose.side_xunhuan0;
			rst+=tempValue.length>0?'伴'+tempValue+'，':'';
		}
		_flag=_compose.side_huxi;
		if(_flag==0){
			rst+='无咳嗽、咳痰、咯血、呼吸困难，';
		}else if(_flag==1){
			tempValue=_compose.side_huxi0;
			rst+=tempValue.length>0?'伴'+tempValue+'，':'';
		}
		_flag=_compose.side_miniao;
		if(_flag==0){
			rst+='无尿频、尿急、尿痛，无排尿困难，';
		}else if(_flag==1){
			tempValue=_compose.side_miniao0;
			rst+=tempValue.length>0?'伴'+tempValue+'，':'';
		}
		_flag=_compose.side_xueye;
		if(_flag==0){
			rst+='无皮肤粘膜瘀点、瘀斑及出血，';
		}else if(_flag==1){
			tempValue=_compose.side_xueye0;
			rst+=tempValue.length>0?'伴'+tempValue+'，':'';
		}
		_flag=_compose.side_nei;
		if(_flag==0){
		}else if(_flag==1){
			tempValue=_compose.side_nei0;
			rst+=tempValue.length>0?'伴'+tempValue+'，':'';
		}
		_flag=_compose.side_shenjing;
		if(_flag==0){
			rst+='无头昏、头痛、意识障碍，';
		}else if(_flag==1){
			tempValue=_compose.side_shenjing0;
			rst+=tempValue.length>0?'伴'+tempValue+'，':'';
		}
		_flag=_compose.side_jirou;
		if(_flag==0){
			rst+='无关节肿痛，';
		}else if(_flag==1){//肌肉
			tempValue=$('input[name="side_jirou_seat"]').val();
			rst+=tempValue.length>0?tempValue:'';
			tempValue=_compose.side_jirou0;
			rst+=tempValue.length>0?'伴'+tempValue+'，':'';
		}
		_flag=_compose.side_qita;
		if(_flag==0){
		}else if(_flag==1){
			tempValue=_compose.side_qita0;
			rst+=tempValue.length>0?'伴'+tempValue+'，':'';
		}
		return rst.length>0?rst.substr(0,rst.length-1)+'。':'';
	}
	
	