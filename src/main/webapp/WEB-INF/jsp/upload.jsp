<%@page contentType="text/html; charset=UTF-8"  errorPage="error.jsp"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<base href="${requestScope['javax.servlet.forward.context_path']}/" >
<style type="text/css">
.progress_box{height:30px;border:1px solid #333333;margin:0;}
.progress_bar{width:0%;height:30px;background-color:royalblue;margin:0;}
</style>
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript">
	//文件信息 文件索引(1开始)——>文件大小
	var files = {};
	//已上传文件大小
	var uploadAllReady = 0;
	//当前上传文件索引
	var currentFile = 1;
	var progressBar="<span>#name#上传进度</span>"
		+"<div class='progress_box'>"
		+"<div class='progress_bar' data-size='#size#'></div>"
		+"</div>"
		+"<div><span class='content'></span></div>";

	
    var id;
    //文件上传进度(小数)
    var progress;
    var running = false;
	function recordingProgress(){
		id = setInterval("requestAndParseProgress()",500);
	}
	
	function requestAndParseProgress(){
		if(running){
			return;
		}
		running = true;
		
		$.ajax({
            type: "POST",
            url: "progress",
            dataType: "json",
            success: function(result){
            	//当前上传文件索引 (1开始)
            	var pItems=result.pItems;
            	//当前文件对应的上传进度条
            	var progressBar = $(".progress_bar:eq("+(pItems-1)+")");
            	//当前文件对应的上传进度信息框
            	var contentSpan = progressBar.parents(".progress_box").next().find(".content");

            	if(currentFile!=pItems){
            		//上一个文件上传完成
            		//开始上传其他文件
            		//重置上一个文件上传进度条为100%
            		$(".progress_bar:eq("+(currentFile-1)+")").animate({"width":"100%"},"fast");
            		$(".progress_bar:eq("+(currentFile-1)+")").parents(".progress_box").next().find(".content").text("100.00% 上传完成，等待业务处理中");
            		currentFile=pItems;
            		//累加已上传文件大小
            		uploadAllReady+=files[currentFile];	
            	}
            	
            	
            	var currentUploadSize = result.pBytesRead-uploadAllReady;
            	var currentUploadTotal = files[currentFile];
            	
            	
            	progress=parseFloat(currentUploadSize/currentUploadTotal);
            	progressBar.animate({"width":(progress*100).toFixed(2)+"%"},500);
            	contentSpan.text((progress*100).toFixed(2)+"%");
            },
		    complete:function(){
		    	running = false;
		    }
        });
	}
	
	
	function upload(){
		var filesInput = $("input[type='file']");
		for(var i=0;i<filesInput.length;i++){
			var file = filesInput[i].files[0];

			var name = file.name;
			var size = file.size;

			files[i+1]=size;
			
			$("#box").append(progressBar.replace("#name#",name).replace("#size#",size));
			
		}

		
		recordingProgress();
		$("form").ajaxSubmit({
		    dataType:"json",
		    success:function(data){
		    	$(".progress_bar").each(function(){
		    		$(this).animate({"width":"100%"},"fast");
		    	});
		    	
		    	$(".content").each(function(){
		    		$(this).text("100.00% 上传完成");
		    	});
		    	
		    },
		    error:function(){
		    	alert("error");
		    },
		    complete:function(){
		    	clearInterval(id);
		    }
		});
	}

</script>
</head>
<body>
	<form method="post" action="upload" enctype="multipart/form-data">
		<ul>
			<li><input type="file" name="file"/></li>
			<li><input type="file" name="file2"/></li>
			<li><input type="file" name="file3"/></li>
		</ul>
		<input type="button" onclick ="upload();" value="上传文件"/>
	</form>
	
	<div id="box"></div>
	<!-- <span>上传进度</span>
	<div class="progress_box">
		<div id="progress" class="progress_bar"></div>
	</div>
	<span id="content"></span> -->
</body>
</html>
