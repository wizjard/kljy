// JavaScript Document
var j = jQuery.noConflict();
j(document).ready(function(){
	slide("#topAdvan");
})

function slide(a){
	var slide=j(a);
	var slideImg=slide.find("img");
	var slideUl=slide.find("ul");
	var len=slideImg.length;
	var timer;
	var index=0;
	var str="";
	for(var i=0;i<len;i++) {
		var n=i+1;
		str=str+"<li>"+n+"</li>";
	}
	slideUl.eq(0).html(str);
	var slideLi=slideUl.find("li");
	function mySlide(n){
		slideImg.hide().eq(n).fadeIn("fast"); 
		slideLi.removeClass().eq(n).addClass("cur");
	}
	mySlide("0");
	var next=function(){
		index++;
		if(index>=len){index=0}
		mySlide(index);
	}
	slideLi.each(function(i,o){
		j(o).click(function(){
			mySlide(i);
			index=i;
		})
	})
	timer=setInterval(next,4000);
}