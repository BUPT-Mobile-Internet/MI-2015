$(
function(){initial();})
function initial(){
	var path = ["image/weed.jpg","image/teafarm.png","image/teafarm2.jpg"];
	$("#displayer").attr("src",path[0]);
	var show = roundshow(path);
	$("#bttnwr div").mouseover(function(){
		
		clearInterval(show);//图片停止
		$("#displayer").attr("src",path[$(this).text()-1]);
	});
	$("#bttnwr div").mouseout(function(){
		show = roundshow(path);
	});
	partselect();
}
//调整字体大小
function resizeFont() { 
	var font_size = $(".nav ul li a").width()/4.3;
	$("li").css("font-size",font_size+"px");
}
//2秒轮播函数roundshow
function roundshow(path){
	var i=1;
	var result = setInterval(function(){
		$("#displayer").attr("src",path[i]);
		i=i==2?0:(i+1);
	},2000);
	return result;
}
//文字选择和图片选择
function partselect(){
	$("#floatopt"+1+" "+"div").css("background-color","#860000");
				$("#floatopt"+1).css("background-color","#FFFFFF");
	$(".floatopt div").mouseover(function(){
		var id =$(this).attr("id");
		var index = id[id.length-1];
		for(var i=0+1;i<$(".floatopt div").length+1;i++){
			if(i == index){
				$("#floatblock"+i).css("display","block");
				$("#floatopt"+i+" "+"div").css("background-color","#860000");
				$("#floatopt"+i).css("background-color","#FFFFFF");
			}else{
				$("#floatblock"+i).css("display","none");
				$("#floatopt"+i+" "+"div").css("background-color","#808080");
				$("#floatopt"+i).css("background-color","#808080");
			}
		}
	});
	
	
}