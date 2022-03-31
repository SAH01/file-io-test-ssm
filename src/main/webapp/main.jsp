<%--
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
                <form method="post" action="/DictServlet">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>
                            文件路径
                        </th>
                    </tr>
                    </thead>
                    <tbody id="tbody">
                    <tr class="success">
                        <td>
                            <input class="form-control" type="text" name="filePath" value="" >
                        </td>
                        <td>
                            <input class="form-control" type="submit" value="提交文件" >
                        </td>
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
