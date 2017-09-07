<%@page contentType="text/html; charset=UTF-8"  errorPage="error.jsp"%>
<html>
<head>
<script src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
$(function(){
	var ws = new WebSocket("ws://"+window.location.host+"/demo/websocket");
	ws.onopen = function() {
		// 当websocket创建成功时，即会触发onopen事件
		//alert("open");
	};
	ws.onmessage = function(evt) {
		// 当客户端收到服务端发来的消息时，会触发onmessage事件
		// 参数evt.data中包含server传输过来的数据
		//console.log(evt.data);
		$("#text_container").empty();
		$("#text_container").html(evt.data);
	};
	ws.onclose = function(evt) {
		// 当客户端收到服务端发送的关闭连接的请求时，触发onclose事件
		alert("WebSocketClosed!");
	};
	ws.onerror = function(evt) {
		// 如果出现连接，处理，接收，发送数据失败的时候就会触发onerror事件
		alert("WebSocketError!");
	};
	
	
	$("#send").click(function(){
		ws.send($("#text").val());
	});
});

</script>
</head>
<body style="background-color: black;">
<!-- <input id="text"/><input id="send" type="button" /> -->
<div id="text_container" style="margin:0 auto;width: 1000px;color: white;"></div>
</body>
</html>
