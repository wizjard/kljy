<%@ page language="java" pageEncoding="UTF-8"%>
<div  id="Diagnosis">
<table class="conTable" border=0 cellpadding=0 cellspacing=0>
	<tr>
		<td class="td2" width="25%"><b>确定诊断：</b></td>
		<td class="td2" width="25%">日期：<span name="queding_diagnosis_time">&nbsp;</span></td>
		<td class="td2" width="25%"><b>初步诊断：</b></td>
		<td class="td2" width="25%">日期：<span name="chubu_diagnosis_time">&nbsp;</span></td>
	</tr>
	<tr>
		<td colspan="2" rowspan="3" class="td2" name="queding_diagnosis">&nbsp;</td>
		<td colspan="2" class="td2" name="chubu_diagnosis">&nbsp;</td>
		
	</tr>
	<tr class="hidden">
		<td colspan="2" class="td2" name="chubu_bianbing">&nbsp;</td>
	</tr>
	<tr class="hidden">
		<td colspan="2" class="td2" name="chubu_zhizefangyao">&nbsp;</td>
	</tr>
</table>
</div>