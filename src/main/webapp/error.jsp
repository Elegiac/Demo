<%@page contentType="text/html; charset=UTF-8"  isErrorPage="true"%>
<html>
<head>
</head>
<body>
出错了！<br>
发生了以下的错误：
<br><hr><font color=red>
<% 

StackTraceElement[] es = exception.getStackTrace();
for(StackTraceElement se:es){
	out.println(se);
}



%><br>
<br>
</font>
</body>
</html>
