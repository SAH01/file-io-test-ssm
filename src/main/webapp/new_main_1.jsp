<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021-11-13
  Time: 13:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String msg= (String) request.getAttribute("msg");
%>
<html>
<head>
    <title>建表结果</title>
</head>
<body>
    <h3><%=request.getAttribute("msg")%></h3>
</body>
</html>
<script>
    var msg=msg;
</script>