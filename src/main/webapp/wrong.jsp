<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021-11-15
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误信息</title>
</head>
<body>
    <h3>错误信息：  <%=request.getAttribute("wrongData").toString()%>
        请检查文件该行是否有单元格的内容存在逗号，如有将其替换为“_”或“-”再次上传文件！</h3>
</body>
</html>
