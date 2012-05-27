//2011-08-18 liugang 当矫正视力低于0.02时显示
	function showSeJueLeft(_this){
	    if(_this.value ==""){
	    	document.getElementById("showSeLeft1").style.display="none";
	    	document.getElementById("showSeLeft2").style.display="none";
	    	document.getElementById("showSeLeft3").style.display="none";
	    }
		if(parseFloat(_this.value) <= 0.02){
			document.getElementById("showSeLeft1").style.display="block";
			document.getElementById("showSeLeft2").style.display="block";
			document.getElementById("showSeLeft3").style.display="block";
		}else{
			document.getElementById("showSeLeft1").style.display="none";
			document.getElementById("showSeLeft2").style.display="none";
			document.getElementById("showSeLeft3").style.display="none";
		}
	}
	function showSeJueRight(_this){
	    if(_this.value ==""){
	    	document.getElementById("showSeRight1").style.display="none";
	    	document.getElementById("showSeRight2").style.display="none";
	    	document.getElementById("showSeRight3").style.display="none";
	    }
		if(parseFloat(_this.value) <= 0.02){
			document.getElementById("showSeRight1").style.display="block";
			document.getElementById("showSeRight2").style.display="block";
			document.getElementById("showSeRight3").style.display="block";
		}else{
			document.getElementById("showSeRight1").style.display="none";
			document.getElementById("showSeRight2").style.display="none";
			document.getElementById("showSeRight3").style.display="none";
		}
	}
	

//眼睑
function showZidingyi_yanjianRight(_this){
    if(_this.value ==""){
    	document.getElementById("yanjian_zidingyiRight").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("yanjian_zidingyiRight").style.display = "block";
	}else{
		document.getElementById("yanjian_zidingyiRight").style.display = "none";
	}
}

function showZidingyi_yanjianLeft(_this){
    if(_this.value ==""){
    	document.getElementById("yanjian_zidingyiLeft").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("yanjian_zidingyiLeft").style.display = "block";
	}else{
		document.getElementById("yanjian_zidingyiLeft").style.display = "none";
	}
}

//结膜
function showZidingyi_jm_jmRight(_this){
    if(_this.value ==""){
    	document.getElementById("jiemo_jiemoRight").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("jiemo_jiemoRight").style.display = "block";
	}else{
		document.getElementById("jiemo_jiemoRight").style.display = "none";
	}
}

function showZidingyi_jm_jmLeft(_this){
    if(_this.value ==""){
    	document.getElementById("jiemo_jiemoLeft").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("jiemo_jiemoLeft").style.display = "block";
	}else{
		document.getElementById("jiemo_jiemoLeft").style.display = "none";
	}
}
//冲洗
function showleiqi_chongxiLeft(_this){
    if(_this.value ==""){
    	document.getElementById("leiqi_chongxiLeft").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("leiqi_chongxiLeft").style.display = "block";
	}else{
		document.getElementById("leiqi_chongxiLeft").style.display = "none";
	}
}

function showleiqi_chongxiRight(_this){
    if(_this.value ==""){
    	document.getElementById("leiqi_chongxiRight").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("leiqi_chongxiRight").style.display = "block";
	}else{
		document.getElementById("leiqi_chongxiRight").style.display = "none";
	}
}

//中央前方
function showqf_zyqfRight(_this){
	if(_this.value ==""){
    	document.getElementById("qianfang_zhongyangqianfangRight").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("qianfang_zhongyangqianfangRight").style.display = "block";
	}else{
		document.getElementById("qianfang_zhongyangqianfangRight").style.display = "none";
	}
}
function showqf_zyqfLeft(_this){
	if(_this.value ==""){
    	document.getElementById("qianfang_zhongyangqianfangLeft").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("qianfang_zhongyangqianfangLeft").style.display = "block";
	}else{
		document.getElementById("qianfang_zhongyangqianfangLeft").style.display = "none";
	}
}

//纹理
function showhm_wlRight(_this){
	if(_this.value ==""){
    	document.getElementById("hongmo_wenliRight").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("hongmo_wenliRight").style.display = "block";
	}else{
		document.getElementById("hongmo_wenliRight").style.display = "none";
	}
}
function showhm_wlLeft(_this){
	if(_this.value ==""){
    	document.getElementById("hongmo_wenliLeft").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("hongmo_wenliLeft").style.display = "block";
	}else{
		document.getElementById("hongmo_wenliLeft").style.display = "none";
	}
}

//瞳孔粘连
function showtk_nlLeft(_this){
	if(_this.value ==""){
    	document.getElementById("tongkong_zhanlianLeft").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("tongkong_zhanlianLeft").style.display = "block";
	}else{
		document.getElementById("tongkong_zhanlianLeft").style.display = "none";
	}
}
function showtk_nlRight(_this){
	if(_this.value ==""){
    	document.getElementById("tongkong_zhanlianRight").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("tongkong_zhanlianRight").style.display = "block";
	}else{
		document.getElementById("tongkong_zhanlianRight").style.display = "none";
	}
}

//瞳孔移位
function showtk_ywLeft(_this){
	if(_this.value ==""){
    	document.getElementById("tongkong_yiweiLeft").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("tongkong_yiweiLeft").style.display = "block";
	}else{
		document.getElementById("tongkong_yiweiLeft").style.display = "none";
	}
}
function showtk_ywRight(_this){
	if(_this.value ==""){
    	document.getElementById("tongkong_yiweiRight").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("tongkong_yiweiRight").style.display = "block";
	}else{
		document.getElementById("tongkong_yiweiRight").style.display = "none";
	}
}

//晶状体混浊
//function showjzt_xz_hzRight(_this){
//	if(_this.value ==""){
//    	document.getElementById("zh_xingzhuangRight").style.display = "none";
//    }
//	if(_this.value == '自定义'){
//		document.getElementById("zh_xingzhuangRight").style.display = "block";
//	}else{
//		document.getElementById("zh_xingzhuangRight").style.display = "none";
//	}
//}
//function showjzt_xz_hzLeft(_this){
//	if(_this.value ==""){
//    	document.getElementById("zh_xingzhuangLeft").style.display = "none";
//    }
//	if(_this.value == '自定义'){
//		document.getElementById("zh_xingzhuangLeft").style.display = "block";
//	}else{
//		document.getElementById("zh_xingzhuangLeft").style.display = "none";
//	}
//}

//后囊混浊
function showjzt_hlhzRight(_this){
	if(_this.value ==""){
    	document.getElementById("zh_hnhzRight").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("zh_hnhzRight").style.display = "block";
	}else{
		document.getElementById("zh_hnhzRight").style.display = "none";
	}
}

function showjzt_hlhzLeft(_this){
	if(_this.value ==""){
    	document.getElementById("zh_hnhzLeft").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("zh_hnhzLeft").style.display = "block";
	}else{
		document.getElementById("zh_hnhzLeft").style.display = "none";
	}
}

//皮质水隙样改变
function showjzt_bzsxygbRight(_this){
	if(_this.value ==""){
    	document.getElementById("zh_qizhiRight").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("zh_qizhiRight").style.display = "block";
	}else{
		document.getElementById("zh_qizhiRight").style.display = "none";
	}
}
function showjzt_bzsxygbLeft(_this){
	if(_this.value ==""){
    	document.getElementById("zh_qizhiLeft").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("zh_qizhiLeft").style.display = "block";
	}else{
		document.getElementById("zh_qizhiLeft").style.display = "none";
	}
}
//晶状体脱位
function showjzt_jzttwRight(_this){
	if(_this.value ==""){
    	document.getElementById("zh_jzttwRight").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("zh_jzttwRight").style.display = "block";
	}else{
		document.getElementById("zh_jzttwRight").style.display = "none";
	}
}
function showjzt_jzttwLeft(_this){
	if(_this.value ==""){
    	document.getElementById("zh_jzttwLeft").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("zh_jzttwLeft").style.display = "block";
	}else{
		document.getElementById("zh_jzttwLeft").style.display = "none";
	}
}

//视盘
function showyd_spLeft(_this){
	if(_this.value ==""){
    	document.getElementById("yd_shipanLeft").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("yd_shipanLeft").style.display = "block";
	}else{
		document.getElementById("yd_shipanLeft").style.display = "none";
	}
}
function showyd_spRight(_this){
	if(_this.value ==""){
    	document.getElementById("yd_shipanRight").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("yd_shipanRight").style.display = "block";
	}else{
		document.getElementById("yd_shipanRight").style.display = "none";
	}
}
//黄斑
function showyd_hb_zxagfsLeft(_this){
	if(_this.value ==""){
    	document.getElementById("yd_huangbanLeft").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("yd_huangbanLeft").style.display = "block";
	}else{
		document.getElementById("yd_huangbanLeft").style.display = "none";
	}
}
function showyd_hb_zxagfsRight(_this){
	if(_this.value ==""){
    	document.getElementById("yd_huangbanRight").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("yd_huangbanRight").style.display = "block";
	}else{
		document.getElementById("yd_huangbanRight").style.display = "none";
	}
}

//虹膜粘连，阳性时弹出粘连位置，阴性时隐藏
function showhm_lnwzLeft(_this){
	  if(_this.value ==""){
	    	document.getElementById("zhanlianweizhiLeft").style.display="none";
	    }
		if(_this.value == "(+)"){
			document.getElementById("zhanlianweizhiLeft").style.display="block";
		}else{
			document.getElementById("zhanlianweizhiLeft").style.display="none";
		}
}
function showhm_lnwzRight(_this){
	  if(_this.value ==""){
	    	document.getElementById("zhanlianweizhiRight").style.display="none";
	    }
		if(_this.value == "(+)"){
			document.getElementById("zhanlianweizhiRight").style.display="block";
		}else{
			document.getElementById("zhanlianweizhiRight").style.display="none";
		}
}

//KP
function showKPLeft(_this){
	if(_this.value ==""){
    	document.getElementById("kpLeft").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("kpLeft").style.display = "block";
	}else{
		document.getElementById("kpLeft").style.display = "none";
	}
}
function showKPRight(_this){
	if(_this.value ==""){
    	document.getElementById("kpRight").style.display = "none";
    }
	if(_this.value == '自定义'){
		document.getElementById("kpRight").style.display = "block";
	}else{
		document.getElementById("kpRight").style.display = "none";
	}
}

//晶状体混浊时触发
function showtuoweiRight(_this){
	if(_this.value.indexOf("混浊")>-1){
		document.getElementById("jintuoweiRight").style.display = "block";
	}else{
		document.getElementById("jintuoweiRight").style.display = "none";
	}
}
function showtuoweiLeft(_this){
	if(_this.value.indexOf("混浊")>-1){
		document.getElementById("jintuoweiLeft").style.display = "block";
	}else{
		document.getElementById("jintuoweiLeft").style.display = "none";
	}
}
//玻璃体混浊时触发
function showbolitiRight(_this){
	if(_this.value.indexOf("混浊")>-1){
		document.getElementById("bolitiRight").style.display = "block";
	}else{
		document.getElementById("bolitiRight").style.display = "none";
	}
}
function showbolitiLeft(_this){
	if(_this.value.indexOf("混浊")>-1){
		document.getElementById("bolitiLeft").style.display = "block";
	}else{
		document.getElementById("bolitiLeft").style.display = "none";
	}
}

