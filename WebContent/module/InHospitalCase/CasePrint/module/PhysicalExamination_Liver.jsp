<%@ page language="java" pageEncoding="UTF-8"%>
<div id="PhysicalExamination">
<table class="conTable" border=0 cellpadding=0 cellspacing=0>
	<tr>
		<caption style="font-weight:bold;letter-spacing:1em">体格检查</caption>
	</tr>
	<tr>
		<td class="td1" width="85">&nbsp;</td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td style="font-size:16px;text-align:left" width="8%">体温:</td>
						<td class="td2" width="14%" name="smtz_tiwen">&nbsp;</td>
						<td style="font-size:16px;text-align:left" width="8%">血压:</td>
						<td class="td2" width="21%"><span name="smtz_xueya"></span><span name="smtz_xueya2"></span></td>
						<td style="font-size:16px;text-align:left" width="8%">脉搏:</td>
						<td class="td2" width="16%" name="smtz_maibo">&nbsp;</td>
						<td style="font-size:16px;text-align:left" width="8%">呼吸:</td>
						<td class="td2" width="16%" name="smtz_huxi">&nbsp;</td>
					</tr>
		    </table> 
		</td>
	</tr>
	<tr>
		<td class="td1"><b>一般情况：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="33%">发育：<span name="ybzc_fayu">&nbsp;</span></td>
						<td class="td2" width="33%">神志：<span name="ybzc_shenzhi">&nbsp;</span></td>
						<td class="td2" width="*">表情：<span name="ybzc_biaoqing">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">面容：<span name="ybzc_mianrong">&nbsp;</span></td>
						<td class="td2">步态：<span name="ybzc_butai">&nbsp;</span></td>
						<td class="td2">查体：<span name="ybzc_chati">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">营养：<span name="ybzc_yingyang">&nbsp;</span></td>
						<td class="td2" colspan="2">体位：<span name="ybzc_tiwei">&nbsp;</span></td>
					</tr>
		    </table> 
		</td>
	</tr>
	<tr>
		<td class="td1"><b>皮肤粘膜：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="33%">色泽：<span name="pfnm_seze">&nbsp;</span></td>
						<td class="td2" width="33%">温度与湿度：<span name="pfnm_wenshi">&nbsp;</span></td>
						<td class="td2" width="33%">弹性：<span name="pfnm_tanxing">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">肝掌：<span name="pfnm_ganzhang">&nbsp;</span></td>
						<td class="td2">毛细血管扩张征：<span name="pfnm_maoxi">&nbsp;</span></td>
						<td class="td2">毛发分布：<span name="pfnm_maofa">&nbsp;</span></td>
					</tr>
					<!-- =====新增09-12============== -->
					<tr>
						<td class="td2" colspan="3">蜘蛛痣：<span name="pfnm_zhizhu"></span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">皮疹：<span name="pfnm_pizhen">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">皮下出血：<span name="pfnm_pixia">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">瘢痕：<span name="pfnm_banhen">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">水肿：<span name="pfnm_shuizhong">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">其它异常：<span name="pfnm_qita">&nbsp;</span></td>
					</tr>
		    </table>
			
		</td>
	</tr>
	<tr>
		<td class="td1"><b>淋巴结：</b></td>
		<td class="td2" name="linbajie_zhongda">&nbsp;</td>
	</tr>
	<tr>
		<td class="td1"><b>淋巴管：</b></td>
		<td class="td2" name="linbajie_jieyan">&nbsp;</td>
	</tr>
	<tr>
		<td class="td1"><b>头部五官：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="50%">头颅大小：<span name="head_daxiao">&nbsp;</span></td>
						<td class="td2" width="50%">头颅畸形：<span name="head_jixing">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="2">其它异常：<span name="head_other">&nbsp;</span></td>
					</tr>
		    </table> 
		</td>
	</tr>
	<tr>
		<td class="td1"><b>眼：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="50%">眼睑：<span name="eyes_yanjian">&nbsp;</span></td>
						<td class="td2" width="50%">结膜：<span name="eyes_jiemo">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">巩膜：<span name="eyes_gongmo">&nbsp;</span></td>
						<td class="td2">角膜：<span name="eyes_jiaomo">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">眼球：<span name="eyes_yanqiu">&nbsp;</span></td>
						<td class="td2">瞳孔：<span name="eyes_tongkong">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">直接对光反射：<span name="eyes_zhijie">&nbsp;</span></td>
						<td class="td2">间接对光反射：<span name="eyes_jianjie">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="2">其他异常：<span name="eyes_other">&nbsp;</span></td>
					</tr>
		    </table> 
		</td>
	</tr>
	<tr>
		<td class="td1"><b>耳：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="50%">听力粗试障碍：<span name="ear_tingli">&nbsp;</span></td>
						<td class="td2" width="50%">外耳道分泌物：<span name="ear_waier">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">乳突压痛：<span name="ear_rutu">&nbsp;</span></td>
						<td class="td2">耳廓：<span name="ear_erkuo">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="2">其他异常：<span name="ear_other">&nbsp;</span></td>
					</tr>
		    </table> 
		</td>
	</tr>
	<tr>
		<td class="td1"><b>鼻：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="50%">鼻中隔：<span name="nose_zhongge">&nbsp;</span></td>
						<td class="td2" width="50%">外观：<span name="nose_waiguan">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="2">鼻窦压痛：<span name="nose_bidou">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="2">其他异常：<span name="nose_other">&nbsp;</span></td>
					</tr>
		    </table> 
		</td>
	</tr>
	<tr>
		<td class="td1"><b>口：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="33%">口唇：<span name="mouth_kouchun">&nbsp;</span></td>
						<td class="td2" width="33%">伸舌：<span name="mouth_shenshe">&nbsp;</span></td>
						<td class="td2" width="33%">口腔粘膜：<span name="mouth_nianmo">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">牙龈：<span name="mouth_yayin">&nbsp;</span></td>
						<td class="td2">咽部：<span name="mouth_yanbu">&nbsp;</span></td>
						<td class="td2">口腔气味：<span name="mouth_qiwei">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">齿列：<span name="mouth_chilie">&nbsp;</span></td>
						<td class="td2">声音：<span name="mouth_shenyin">&nbsp;</span></td>
						<td class="td2">扁桃体：<span name="mouth_biantaoti">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">腮腺肿大：<span name="saix_zhongda">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">其他异常：<span name="mouth_other">&nbsp;</span></td>
					</tr>
		    </table> 
		</td>
	</tr>
	<tr>
		<td class="td1"><b>颈部：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="33%">抵触感：<span name="neck_dichu">&nbsp;</span></td>
						<td class="td2" width="33%">气管位置：<span name="neck_qiguan">&nbsp;</span></td>
						<td class="td2" width="33%">颈静脉：<span name="neck_jingmai">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">颈动脉搏动：<span name="neck_dongmai">&nbsp;</span></td>
						<td class="td2" colspan="2">肝颈静脉回流征：<span name="neck_jmhuiliu">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">甲状腺肿大：<span name="neck_jiazhx">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">其他异常：<span name="neck_other">&nbsp;</span></td>
					</tr>
		    </table>
		</td>
	</tr>
	<tr>
		<td class="td1"><b>胸部：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="33%">胸廓：<span name="xiong_kuo">&nbsp;</span></td>
						<td class="td2" width="33%">胸骨压痛：<span name="xiong_yatong">&nbsp;</span></td>
						<td class="td2" width="33%">双侧乳房：<span name="xiong_rufangDc">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">乳房异常：<span name="xiong_fufang">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">其他异常：<span name="xiong_other">&nbsp;</span></td>
					</tr>
		    </table>
		</td>
	</tr>
	<tr>
		<td class="td1"><b>肺部：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="50%">呼吸运动：<span name="fei_huxi">&nbsp;</span></td>
						<td class="td2" width="50%">触觉语颤：<span name="fei_yuchan">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">呼吸音：<span name="fei_huxiyin">&nbsp;</span></td>
						<td class="td2">胸膜摩擦音：<span name="fei_xiongmo">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">左肺叩诊：<span name="fei_zuokou">&nbsp;</span></td>
						<td class="td2">右肺叩诊：<span name="fei_youkou">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">左肺下界：位于肩胛线第<span name="fei_zuoxiajie">&nbsp;</span>肋间</td>
						<td class="td2">右肺下界：位于肩胛线第<span name="fei_youxiajie">&nbsp;</span>肋间</td>
					</tr>
					<tr>
						<td class="td2" colspan="2">啰音：<span name="fei_luoyin">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="2">其他异常：<span name="fei_other">&nbsp;</span></td>
					</tr>
		    </table>
		</td>
	</tr>
	<tr>
		<td class="td1"><b>心脏：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="33%">心前区膨隆：<span name="xinz_penglong">&nbsp;</span></td>
						<td class="td2" width="33%">心尖搏动：<span name="xinz_bodong">&nbsp;</span></td>
						<td class="td2" width="33%">心音：<span name="xinz_xinyin">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">叩诊心界：<span name="xinz_xinjie">&nbsp;</span></td>
						<td class="td2">心率：<span name="xinz_xinlv">&nbsp;</span>次/分</td>
						<td class="td2">心律：<span name="xinz_xinrate">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">心包摩擦感：<span name="xinz_xinbao">&nbsp;</span></td>
						<td class="td2" colspan="2">心尖搏动位置：<span name="xinz_bodongPosi">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">杂音：<span name="xinz_zayin">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">其他异常：<span name="xinz_other">&nbsp;</span></td>
					</tr>
		    </table>
		</td>
	</tr>
	<tr>
		<td class="td1"><b>周围血管：</b></td>
		<td class="td2" name="zhouweixg_zheng">&nbsp;</td>
	</tr>
	<tr title="腹部开始">
		<td class="td1"><b>腹部视诊：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="33%">外形：<span name="fubu_waixing">&nbsp;</span></td>
						<td class="td2" width="33%">腹壁静脉曲张：<span name="fubu_jingmai">&nbsp;</span></td>
						<td class="td2" width="33%">腹式呼吸：<span name="fubu_huxi">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">其它异常：<span name="fubu_shizhen_o">&nbsp;</span></td>
					</tr>
		    </table>
		</td>
	</tr>
	<tr>
		<td class="td1"><b>触诊：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="25%">腹壁：<span name="fubu_fubi">&nbsp;</span></td>
						<td class="td2" width="25%">振水音：<span name="fubu_zhenshui">&nbsp;</span></td>
						<td class="td2" width="25%">液波震颤：<span name="fubu_yebo">&nbsp;</span></td>
						<td class="td2" width="25%">肌紧张：<span name="fubu_jijzh">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">压痛：<span name="fubu_yatong">&nbsp;</span></td>
						<td class="td2">反跳痛：<span name="fubu_fantt">&nbsp;</span></td>
						<td class="td2">Murphy's征：<span name="fubu_murphy">&nbsp;</span></td>
						<td class="td2">胆囊：<span name="fubu_dannang">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="4">腹部包块：<span name="fubu_baokuai">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="4">肝脏：<span name="fubu_ganzang">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="4">脾脏：<span name="fubu_pi">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="4">肾脏：<span name="fubu_shen">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="4">其他异常：<span name="fubu_chu_other">&nbsp;</span></td>
					</tr>
		    </table>
		</td>
	</tr>
	<tr>
		<td class="td1"><b>叩诊：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="33%">肝浊音界：<span name="fubu_ganzhuo">&nbsp;</span></td>
						<td class="td2" width="33%">移动性浊音：<span name="fubu_yidong">&nbsp;</span></td>
						<td class="td2" width="33%">腹水：<span name="fubu_fushui">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">肝区叩痛：<span name="fubu_ganqukt">&nbsp;</span></td>
						<td class="td2" colspan="2">肾区叩痛：<span name="fubu_shenkt">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">肝上界：肝上界位于右锁骨中线第<span name="fubu_ganshangjie">&nbsp;</span>肋间</td>
					</tr>
					<tr>
						<td class="td2" colspan="3">其他异常：<span name="fubu_k_other">&nbsp;</span></td>
					</tr>
		    </table>
		</td>
	</tr>
	<tr title="腹部结束">
		<td class="td1"><b>听诊：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="33%">肠鸣音：<span name="fubu_changming">&nbsp;</span>次/分</td>
						<td class="td2" width="33%">气过水声：<span name="fubu_qishui">&nbsp;</span></td>
						<td class="td2" width="33%">血管杂音：<span name="fubu_xueguan">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">其他异常：<span name="fubu_tz_other">&nbsp;</span></td>
					</tr>
		    </table>
		</td>
	</tr>
	<tr>
		<td class="td1"><b>肛门及外生</b></td>
		<td class="td2"><b>殖器：</b><span name="shengzhiqi">&nbsp;</span></td>
	</tr>
	<tr>
		<td class="td1"><b>脊柱：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="50%">外形：<span name="jisi_waixing">&nbsp;</span></td>
						<td class="td2" width="50%">压痛：<span name="jisi_yatong">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">叩击痛：<span name="jisi_kouji">&nbsp;</span></td>
						<td class="td2">活动度：<span name="jisi_huodong">&nbsp;</span></td>
					</tr>
		    </table>
		</td>
	</tr>
	<tr>
		<td class="td1"><b>四肢：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2">下肢水肿：<span name="jisi_xiazhi">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">其它异常：<span name="jisi_other">&nbsp;</span></td>
					</tr>
		    </table>
		</td>
	</tr>
	<tr>
		<td class="td1"><b>神经系统：</b></td>
		<td class="td2">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="99%">
					<tr>
						<td class="td2" width="33%">腹壁反射：<span name="shenjing_fubi">&nbsp;</span></td>
						<td class="td2" width="33%">角膜反射：<span name="shenjing_jiaomo">&nbsp;</span></td>
						<td class="td2" width="33%">膝腱反射：<span name="shenjing_xijian">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">跟腱反射：<span name="shenjing_genjian">&nbsp;</span></td>
						<td class="td2">Babinski征：<span name="shenjing_babinski">&nbsp;</span></td>
						<td class="td2">Kernig征：<span name="shenjing_kerning">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2">踝阵挛：<span name="shenjing_huai">&nbsp;</span></td>
						<td class="td2">扑翼样振颤：<span name="shenjing_puyi">&nbsp;</span></td>
						<td class="td2">Brudzinski征：<span name="shenjing_brudzinski">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">肌力：<span name="shenjing_jili">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">肌张力：<span name="shenjing_jizhang">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="td2" colspan="3">其他异常：<span name="shenjing_other">&nbsp;</span></td>
					</tr>
		    </table>
		</td>
	</tr>
</table>
</div>