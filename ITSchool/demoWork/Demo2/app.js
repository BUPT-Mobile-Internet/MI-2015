$( function(){
	if(!localStorage.getItem("mywork")){
		init();
	}
	renderwork();
});
function renderwork(){
	$("#workboard").empty();
	var date = new Date();
	var html ='<div class="job"><p class="time">'+date.toLocaleString()+'</p><input class="newworkcontent">';
	$("#workboard").append(html);
	var mywork=$.parseJSON(localStorage.getItem("mywork"))
	var items=mywork.work;
	var count=mywork.count;
	for(var i =0;i<items.length;i++){
		var html ='<div class="job" count="'+items[i].count+'"><p class="time">'+items[i].time+
		'</p><input class="workcontent" disabled="true">'+
	'<div class="edit"><p name="edit">Edit</p><p name="del">Del</p></div></div>';
	$("#workboard").append(html);
	$("[count ="+items[i].count+"] input").attr("value",items[i].content);
	}
	$("[name = edit] ").click(function(){
		var editbox =$(this).parent().prev();
		var alleditbox =$(".workcontent");
		for(var i=0;i<alleditbox.length;i++){
			$(alleditbox[i]).attr("disabled","disabled");
		}
		editbox.removeAttr("disabled");
		editbox.focus();
	});
	$("[name = del] ").click(function(){
		//$(this).parent().parent().remove();
		items=deletework(items,$(this).parent().parent().attr("count"));
		mywork.work=items;
		var tmpmywork=JSON.stringify(mywork);
		localStorage.setItem("mywork",tmpmywork);
		renderwork();	
	});
	$(".newworkcontent").focus(function(){
		var alleditbox =$(".workcontent");
		for(var i=0;i<alleditbox.length;i++){
			$(alleditbox[i]).attr("disabled","disabled");
		}
	});
	$(".newworkcontent").keydown(function(event){
		if(13==event.which){
			if($(this).val()){
				count++;
				mywork.count=count;
				var newschedule = '{"count":"'+count+'","time":"'+$(this).prev().html()+'","content":"'+$(this).val()+'"}'
				newschedule = $.parseJSON(newschedule);
				mywork.work.push(newschedule);
				mywork = JSON.stringify(mywork);
				localStorage.setItem("mywork",mywork);
				renderwork();
			}else{alert("计划不能为空");}
		}
	})
	$(".workcontent").keydown(function(event){
		if(13==event.which){
			var count = $(this).parent().attr("count")
			items=deletework(items,count,$(this).val())
			mywork.work=items;
			var tmpmywork=JSON.stringify(mywork);
			localStorage.setItem("mywork",tmpmywork);
			renderwork();
		}
	})

}
function init(){
	var storage ='{"count":"0","work":[]}'
	localStorage.setItem("mywork",storage);
}

function deletework(array,count,value){
	var index=0;
	for(var i = 0;i < array.length;i ++){
		if(array[i].count == count){
			index = i;
			break;
		}
	}
	if(value!=null){
		var item =array[index]
		item.content=value;
     	array.splice(index,1,item);
	}else{
		array.splice(index,1);
	}
	return array;
}