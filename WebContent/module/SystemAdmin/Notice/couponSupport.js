CouponSupport = function(){};

var query = jQuery.noConflict();





//控制显示所有的优惠券分类(一)
CouponSupport.showCouponTypeList = function()
{
	var bean = {loadCouponType:"wx3c",delFlag:"1",parentId:"____",type:"3"};//广告位设置
	ECCouponTypeBean.findCouponType(bean,function(data){
		
		//显示所有的优惠券分类(二)
		CouponSupport.showAllCouponTypeList(data);
		//显示所有的优惠券(三)
		CouponSupport.showAllCouponList(data);
		//广告
		CouponSupport.showAdvanAll(data);
	});
}






//广告
CouponSupport.showAdvanAll = function(data)
{
	var advanList = data.indexAdvanAll;
	if(advanList != null && advanList.length > 0)
	{
		//顶部单个广告
		var listFileName = null;
		var listSbLink = null;
		var advanListTop = "<ul></ul>";
		if(null != advanList[0].map.sbFileName && undefined != advanList[0].map.sbFileName)
		{
			listFileName = advanList[0].map.sbFileName.split(",");
		}
		if(null != advanList[0].map.sbLink && undefined != advanList[0].map.sbLink)
		{
			listSbLink  = advanList[0].map.sbLink.split(",");
		}
		if(listFileName != null && listSbLink.length > 0)
		{
			for(var j=0;j<listFileName.length;j++)
			{
				if(listSbLink != null && listSbLink.length > 0)
				{
					advanListTop +="<a href ='"+listSbLink[j]+"'>";
				}
				else
				{
					advanListTop +="<a href ='#'>";
				}
				if(listFileName[j] != undefined)
				{
					advanListTop +="<img src='../../../"+listFileName[j]+"' style='width:752px;max-height:262px'/>";
				}
				advanListTop += "</a>";
				
			}
		}
		$("topAdvan").innerHTML = advanListTop;
		slide("#topAdvan");
		
		
		
		$("leftAdvanList").innerHTML = leftAdvanList;
	}
}

JsSupport.importPageBean("ECCouponTypeBean");

JsSupport.onload = function()
{
	window.parent.document.getElementById("link").innerHTML = document.getElementById("title").innerHTML;
	window.parent.document.getElementById("divContent").className="";
//	contentNode
	window.parent.document.getElementById("divContent").style.width="955px";
	//加载页面广告、优惠券分类、优惠券列表
	CouponSupport.showCouponTypeList();
}