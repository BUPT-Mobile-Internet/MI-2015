$(function(){
	$("#a").mouseover(function(event){
		var item = event.target;
		var top = $(item).offset().top;
		var items ='[{"tag": "html","url": "movie.html"},{"tag": "css","url": "http://www.baidu.com"},{"tag": "javascript",'
		+'"url": "http://www.baidu.com"}]'
        items = JSON.parse(items);
        $("#menucontentcontainer").empty();	
		rendermenucontent(top,items);
	});
})
$(function(){
	$("#b").mouseover(function(event){
		var item = event.target;
		var top = $(item).offset().top;
		var items ='[{"tag": "python","url": "http://www.baidu.com"},{"tag": "php","url": "http://www.baidu.com"},{"tag": "ruby",'
		+'"url": "http://www.baidu.com"}]'
        items = JSON.parse(items);
        $("#menucontentcontainer").empty();	
		rendermenucontent(top,items);
	});
})
$(function(){
	$("#c").mouseover(function(event){
		var item = event.target;
		var top = $(item).offset().top;
		var items ='[{"tag": "android","url": "http://www.baidu.com"},{"tag": "ios","url": "http://www.baidu.com"},{"tag": "cocos",'
		+'"url": "http://www.baidu.com"}]'
        items = JSON.parse(items);
        $("#menucontentcontainer").empty();	
		rendermenucontent(top,items);
	});
})
function rendermenucontent(top,items){
	var html = '<div id ="menucontent">'+'</div>'
	$("#menucontentcontainer").append(html);
	$("#menucontent").css("top",(top-9)+"px")
	console.log(items);
	for(var i = 0;i<items.length;i++){
		var tag = '<p class = "menuoptions"><a href="'+items[i].url+'"">'+items[i].tag+'</a></p>'
		$("#menucontent").append(tag);
	}

}