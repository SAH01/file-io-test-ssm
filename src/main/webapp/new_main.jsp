<%@ page import="com.reliable.bean.FileDict" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021-11-12
  Time: 8:57
  To change this template use File | Settings | File Templates.
--%>
<link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.0.1/css/bootstrap.css" rel="stylesheet">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-2 column">
            </div>
            <div class="col-md-6 column">
                <form method="post" action="/CreateServlet">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>
                            序号
                        </th>
                        <th>
                            字段名称
                        </th>
                        <th>
                            字段描述
                        </th>
                        <th>
                            字段单位
                        </th>
                        <th>
                            字段类型
                        </th>
                        <th>
                            字段长度
                        </th>
                    </tr>
                    </thead>
                    <tbody id="tbody">

                    <%
                        FileDict fileDict = (FileDict) request.getAttribute("dict");
                        ArrayList<String> fieldName;
                        ArrayList<String> tableName;
                        ArrayList<String> newFieldName;
                        ArrayList<String> fieldDescribe;
                        ArrayList<String> fieldType;
                        ArrayList<String> fieldSize;
                        fieldName=fileDict.getFieldName();
                        tableName=fileDict.getTableName();
                        fieldType=fileDict.getFieldType();
                        fieldSize=fileDict.getFieldSize();
                        %>
                    <tr class="error">
                        <td>
                            <input type="text" name="tableName" value=<%=tableName.get(0)%> readonly="readonly" class="form-control">
                        </td>
                        <td>
                            <input type="text" name="tableName" value=<%=tableName.get(1)%> readonly="readonly" class="form-control">
                        </td>
                    </tr>
                    <% for(int i=0;i<fieldName.size();i++)
                        {
                        	if(fieldName.get(i).equals(" ")||fieldName.get(i).equals("  ")){
                                fieldName.set(i,"error_default");
                            }else{
                        		String tempValue=fieldName.get(i).replace(" ","");
                                fieldName.set(i,tempValue.replace("?",""));
                            }
                    %>
                    <tr class="success">
                        <td>
                            <%=String.valueOf(i+1)%>
                        </td>
                        <td>
                            <input class="form-control" name="fieldName" type="text" readonly="readonly" value="<%=fieldName.get(i)%>" >
                        </td>
                        <td>
                            <input class="form-control" type="text" name="fieldDescribe" value="无" placeholder="默认为“无”">
                        </td>
                        <td>
                            <input class="form-control" type="text" name="fieldUnit" value="无" placeholder="默认为“无”">
                        </td>
                        <td>
                            <input class="form-control" type="text" name="fieldType" value="<%=fieldType.get(0)%>" readonly="readonly">
                        </td>
                        <td>
                            <input class="form-control" type="text" name="fieldSize" value="<%=fieldSize.get(0)%>" readonly="readonly">
                        </td>
                    </tr>
                    <%} %>
                    <tr>
                        <td><input type="submit" class="form-control"></td>
                    </tr>
                    </tbody>
                </table>
                </form>
            </div>
            <div class="col-md-4 column">
            </div>
        </div>
    </div>
</table>
</body>
</html>
<script src="js/jquery-2.2.3.min.js"></script>
