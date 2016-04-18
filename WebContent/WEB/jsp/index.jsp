<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>输入页面</title>
</head>
<body>
<script type="text/javascript"> 
function checkForm() {
	var keyWord=document.getElementById("keyWord");
	var pageNum=document.getElementById("pageNum");
	var fileUri=document.getElementById("fileUri");
	var num=new RegExp("^[0-9]*$");//判断正整数 /^[1-9]+[0-9]*]*$/    
	if(!num.test(pageNum.value)) {
		alert("请输入数字");
		return false;
	} else if(keyWord.value==""||pageNum.value==""||fileUri==""){
		alert("请填完整内容");
		return false;
	}
	return true;
}
</script>
<%
String message=(String)request.getAttribute("message");
if(message!=null) {
	out.print(message);
}
%>
	<form action="CatchPicture" method="post" onsubmit="return checkForm()"> 
		<label for="keyWord">关键字</label>
		<input type="text" value="萌妹子" id="keyWord" name="keyWord"/>
		<label for="pageNum">获得页数</label>
		<input type="text" value="1" name="pageNum" id="pageNum"/><br/>
		<label for="file">保存到</label>
		<input type="text" name="fileUri" id="fileUri" value="C:\Users\CHEN\Desktop\save"/>
		<input type=button value="选择文件夹"/>
		<input type="submit" value="提交"/>
	</form>
</body>
</html>