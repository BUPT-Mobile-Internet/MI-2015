$(function(){
	
	var index = 1;
	var handle = setInterval(function(){
		var array =['../image/01-1.jpg','../image/01-2.jpg'];
		$("#lamp").attr('src',array[index]);
		index = index == 1?0:1;
	},2000);
	
	console.log(minDepth(null));
})