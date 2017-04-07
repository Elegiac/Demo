<%@page contentType="text/html; charset=UTF-8"  errorPage="error.jsp"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script src="js/jquery-3.1.1.min.js"></script>
<script src="js/hammer.min.js"></script>
<script type="text/javascript">
	$(function(){
		//创建一个新的hammer对象并且在初始化时指定要处理的dom元素
		 var hammertime = new Hammer(document.getElementById("area"));
		//为该dom元素指定触屏移动事件
		hammertime.add(new Hammer.Pinch());
		//添加事件
		hammertime.on("pinchin", function (e) {
			e.preventDefault();
			$("#area").text(e.scale);
			changeSize(e.scale);
		});
		hammertime.on("pinchmove", function (e) {
			e.preventDefault();
			$("#area").text(e.scale);
			changeSize(e.scale);
		});
		hammertime.on("pinchout", function (e) {
			e.preventDefault();
			$("#area").text(e.scale);
			changeSize(e.scale);
		});
	});
	
	function changeSize(pre){
		var width = $("#box").width();
		var height = $("#box").height();
		width*=pre;
		height*=pre;
		if(width>=180){
			width=180;
		}
		if(width<=10){
			width=10;
		}
		if(height>=180){
			height=180;
		}
		if(height<=10){
			height=10;
		}
		$("#box").width(width);
		$("#box").height(height);
	}
</script>
</head>
<body>
<div id="box" style="background-color:lightblue;width:50px;height: 50px;"></div>
<div id="area" style="background-color:lightgreen;width:100%;height: 300px;"></div>
</body>
</html>
